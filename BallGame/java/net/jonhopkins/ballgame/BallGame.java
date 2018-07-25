package net.jonhopkins.ballgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class BallGame extends Applet implements Runnable, MouseMotionListener {
	private static final long serialVersionUID = 4478108492322269640L;
	Ball player;
	Ball enemy;
	
	public void init() {
		setBackground(Color.blue);
		resize(400, 400);
		
		this.dim = getSize();
		
		this.buffer = createImage(this.dim.width, this.dim.height);
		this.bufferGraphics = this.buffer.getGraphics();
		
		this.player = new Ball(20.0D, 100.0D, Color.green);
		this.enemy = new Ball(100.0D, 175.0D, Color.yellow);
		this.bonus = new Bonus(Math.random() * this.dim.width, Math.random() * this.dim.height - 50, Color.orange, "speed");
		this.bonus.onField = false;
		
		addMouseMotionListener(this);
		
		setVisible(true);
	}
	
	public void start() {
		Thread thread = new Thread(this, "Main thread");
		thread.start();
	}
	
	public void stop() {}
	public void destroy() {}
	
	public void run() {
		Thread.currentThread().setPriority(1);
		
		while (true) {
			repaint();
			
			if (!this.player.isAlive) {
				break;
			}
			
			this.player.update();
			this.enemy.update();
			
			if (this.enemyAttacking) {
				if ((int)(Math.random() * 500.0D) == 37) {
					this.enemyAttacking = false;
					this.enemy.setColor(Color.yellow);
				} else {
					this.enemy.changeDirection(this.player.getX(), this.player.getY());
					checkAttack();
				}
			} else if ((int)(Math.random() * 100.0D) == 37) {
				this.enemy.setColor(Color.red);
				this.enemyAttacking = true;
			} else if ((int)(Math.random() * 50.0D) == 5) {
				this.enemy.changeDirection(Math.random() * this.dim.width, Math.random() * this.dim.height - 50);
			}
			
			if ((!this.bonus.onField) && (!this.bonus.isActive)) {
				if ((int)(Math.random() * 250.0D) == 37) {
					int b = (int)(Math.random() * 20.0D);
					
					if ((b >= 0) && (b < 10)) {
						this.bonus = new Bonus(Math.random() * this.dim.width, Math.random() * this.dim.height - 50, Color.orange, "speed");
					} else if ((b > 11) && (b <= 20)) {
						this.bonus = new Bonus(Math.random() * this.dim.width, Math.random() * this.dim.height - 50, Color.red, "health");
					} else if (b == 11) {
						this.bonus = new Bonus(Math.random() * this.dim.width, Math.random() * this.dim.height - 50, Color.yellow, "ultimatebonus");
					}
				}
			} else if (this.bonus.onField) {
				if (checkCollision(this.bonus, this.player)) {
					this.bonus.activate(this.player);
				} else {
					this.bonus.decOnFieldTime();
					
					if (this.bonus.getOnFieldTime() <= 0) {
						this.bonus.onField = false;
					}
				}
			} else if (this.bonus.isActive) {
				this.bonus.decTime();
				
				if (this.bonus.getTime() <= 0) {
					this.bonus.deactivate(this.player);
				}
			}
			
			this.counter += 1;
			
			if (this.counter > 100) {
				this.counter = 0;
			}
			
			try {
				Thread.sleep(20L);
			} catch (InterruptedException localInterruptedException) {}
			
			Thread.currentThread().setPriority(10);
		}
	}
	
	public boolean checkCollision(Obj o1, Obj o2) {
		return Math.pow(Math.pow(o1.getX() - o2.getX(), 2.0D) + Math.pow(o1.getY() - o2.getY(), 2.0D), 0.5D) < o1.getRadius() + o2.getRadius() * 0.9D;
	}
	
	public void checkAttack() {
		if (checkCollision(this.enemy, this.player)) {
			this.player.incHealth(-(int)(Math.random() * 15.0D));
			this.enemyAttacking = false;
			this.enemy.setColor(Color.yellow);
			this.enemy.changeDirection(Math.random() * this.dim.width, Math.random() * this.dim.height - 50);
		}
	}
	
	public void update(Graphics g) {
		if (this.buffer == null) {
			this.buffer = createImage(getSize().width, getSize().height);
			this.bufferGraphics = this.buffer.getGraphics();
		}
		
		this.bufferGraphics.setColor(getBackground());
		this.bufferGraphics.fillRect(0, 0, getSize().width, getSize().height);
		this.bufferGraphics.setColor(getForeground());
		paint(this.bufferGraphics);
		g.drawImage(this.buffer, 0, 0, this);
	}
	
	public void drawHUD(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, this.dim.height - 50, this.dim.width, 50);
		g.setColor(Color.red);
		g.fillRect(10, this.dim.height - 35, this.player.getHealth(), 20);
		if ((this.bonus.isActive) && (this.bonus.getTime() > 0)) {
			g.setColor(Color.orange);
			g.fillRect(this.dim.width - 10 - this.bonus.getTime(), this.dim.height - 35, this.bonus.getTime(), 20);
		}
		g.setColor(Color.white);
		g.drawString("Health: " + this.player.getHealth() + "%", 20, this.dim.height - 20);
		if ((this.bonus.isActive) && (this.bonus.getTime() > 0)) {
			g.drawString("Speed Left: " + this.bonus.getTime(), this.dim.width - 100, this.dim.height - 20);
		}
		if (!this.player.isAlive) {
			g.drawString("You are dead", this.dim.width / 2 - 30, this.dim.height / 3);
		}
	}
	
	public void paint(Graphics g) {
		if (this.bonus.onField) this.bonus.draw(g);
		this.player.draw(g);
		this.enemy.draw(g);
		drawHUD(g);
	}
	
	public void mouseMoved(MouseEvent event) {
		this.player.changeDirection(event.getX(), event.getY());
	}
	
	public void mouseDragged(MouseEvent event) {}
	
	class Obj {
		public Obj(double x, double y, Color c) {
			this.x_pos = x;
			this.y_pos = y;
			this.color = c;
		}
		
		public void draw(Graphics g) {
			g.setColor(this.color);
			g.fillOval((int)this.x_pos - this.radius, (int)this.y_pos - this.radius, 2 * this.radius, 2 * this.radius);
		}
		
		public double getX() {
			return this.x_pos;
		}
		
		public double getY() {
			return this.y_pos;
		}
		
		public int getRadius() {
			return this.radius;
		}
		
		public void setColor(Color c) {
			this.color = c;
		}
		
		protected double x_pos;
		protected double y_pos;
		protected int radius;
		protected Color color;
	}
	
	class Ball extends BallGame.Obj {
		private double x_vel;
		
		public Ball(double x, double y, Color c) {
			super(x, y, c);
			
			this.radius = 20;
			
			double direction = Math.random() * 6.283185307179586D;
			this.x_vel = (Math.cos(direction) * this.speed);
			this.y_vel = (Math.sin(direction) * this.speed);
			
			this.health = 100;
		}
		
		public void changeDirection(double x2, double y2) {
			double x = x2 - this.x_pos;
			double y = y2 - this.y_pos;
			
			if ((x == 0D) || (y == 0D)) {
				return;
			}
			
			double hyp = Math.pow(Math.pow(x, 2.0D) + Math.pow(y, 2.0D), 0.5D);
			
			if (hyp == 0D) {
				return;
			}
			
			this.x_vel = (x / hyp * this.speed);
			this.y_vel = (y / hyp * this.speed);
		}
		
		public void update() {
			this.x_pos += this.x_vel;
			this.y_pos += this.y_vel;
			
			if ((this.x_pos - this.radius <= 0D) || (this.x_pos + this.radius >= BallGame.this.dim.width)) {
				this.x_vel *= -1.0D;
			}
			if ((this.y_pos - this.radius <= 0D) || (this.y_pos + this.radius >= BallGame.this.dim.height - 50)) {
				this.y_vel *= -1.0D;
			}
			
			if (BallGame.this.counter == 100) {
				if (getHealth() > 100) {
					incHealth(-1);
				} else if (getHealth() < 100) {
					incHealth(1);
				}
			}
		}
		
		public void draw(Graphics g) {
			g.setColor(this.color);
			g.fillOval((int)this.x_pos - this.radius, (int)this.y_pos - this.radius, 2 * this.radius, 2 * this.radius);
		}
		
		public double getSpeed() {
			return this.speed;
		}
		
		public int getHealth() {
			return this.health;
		}
		
		public void incHealth(int h) {
			this.health += h;
			
			if (this.health <= 0) {
				this.isAlive = false;
			}
		}
		
		public void incSpeed(double s) {
			this.speed += s;
		}
		
		private double y_vel;
		private double speed = 1D;
		private int health;
		private boolean isAlive = true;
	}
	
	class Bonus extends BallGame.Obj {
		public Bonus(double x, double y, Color c, String s) {
			super(x, y, c);
			
			if (s == "health") {
				this.healthBoost = ((int)(Math.random() * 10.0D) + 10);
			} else if (s == "speed") {
				this.speedBoost += Math.random() * 1.7D + 0.3D;
				this.timer += (int)(Math.random() * 60.0D) + 40;
			} else if (s == "ultimatebonus") {
				this.healthBoost = 100;
				this.speedBoost += 2.0D;
				this.timer += 150;
			}
			
			this.radius = 5;
			this.onField = true;
			this.onFieldTimer = 150;
		}
		
		public void activate(BallGame.Ball b) {
			b.incSpeed(this.speedBoost);
			b.incHealth(this.healthBoost);
			if (b.getHealth() > 100) {
				b.incHealth(100 - b.getHealth());
			}
			this.onField = false;
			this.isActive = true;
		}
		
		public void deactivate(BallGame.Ball b) {
			b.incSpeed(-this.speedBoost);
			this.speedBoost = 0D;
			this.healthBoost = 0;
			this.isActive = false;
		}
		
		public int getTime() {
			return this.timer;
		}
		
		public int getOnFieldTime() {
			return this.onFieldTimer;
		}
		
		public void decTime() {
			if ((this.timer > 0) && (BallGame.this.counter % 5 == 0)) this.timer -= 1;
		}
		
		public void decOnFieldTime() {
			if ((this.timer > 0) && (BallGame.this.counter % 5 == 0)) this.onFieldTimer -= 1;
		}
		
		private int timer = 0;
		private int onFieldTimer = 0;
		private double speedBoost = 0D;
		private int healthBoost = 0;
		public boolean isActive = false;
		public boolean onField = false;
	}
	
	Bonus bonus;
	int counter;
	boolean enemyAttacking = false;
	Image buffer;
	Graphics bufferGraphics;
	Dimension dim;
}
