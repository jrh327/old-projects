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
		
		this.player = new Ball(20.0D, 100.0D, Color.green, this.dim);
		this.enemy = new Ball(100.0D, 175.0D, Color.yellow, this.dim);
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
			
			if (!this.player.isAlive()) {
				break;
			}
			
			this.player.update(counter);
			this.enemy.update(counter);
			
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
				if (this.player.checkCollision(this.bonus)) {
					this.bonus.activate(this.player);
				} else {
					this.bonus.decOnFieldTime(counter);
					
					if (this.bonus.getOnFieldTime() <= 0) {
						this.bonus.onField = false;
					}
				}
			} else if (this.bonus.isActive) {
				this.bonus.decTime(counter);
				
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
	
	public void checkAttack() {
		if (this.enemy.checkCollision(this.player)) {
			this.player.incHealth(-(int)(Math.random() * 15.0D));
			this.enemyAttacking = false;
			this.enemy.setColor(Color.yellow);
			this.enemy.changeDirection(Math.random() * this.dim.width, Math.random() * this.dim.height - 50);
		}
	}
	
	@Override
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
		if (!this.player.isAlive()) {
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
	
	Bonus bonus;
	int counter;
	boolean enemyAttacking = false;
	Image buffer;
	Graphics bufferGraphics;
	Dimension dim;
}
