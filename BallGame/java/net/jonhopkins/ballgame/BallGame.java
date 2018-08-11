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
	Player player;
	Enemy enemy;
	
	public void init() {
		setBackground(Color.blue);
		resize(400, 400);
		
		BallGame.dim = getSize();
		
		this.buffer = createImage(BallGame.dim.width, BallGame.dim.height);
		this.bufferGraphics = this.buffer.getGraphics();
		
		this.player = new Player();
		this.enemy = new Enemy(player);
		this.bonus = new Bonus(getRandomXPos(), getRandomYPos(), Color.orange, "speed");
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
			
			if ((!this.bonus.onField) && (!this.bonus.isActive)) {
				if ((int)(Math.random() * 250.0D) == 37) {
					int b = (int)(Math.random() * 20.0D);
					
					if ((b >= 0) && (b < 10)) {
						this.bonus = new Bonus(getRandomXPos(), getRandomYPos(), Color.orange, "speed");
					} else if ((b > 11) && (b <= 20)) {
						this.bonus = new Bonus(getRandomXPos(), getRandomYPos(), Color.red, "health");
					} else if (b == 11) {
						this.bonus = new Bonus(getRandomXPos(), getRandomYPos(), Color.yellow, "ultimatebonus");
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
		final int width = BallGame.dim.width;
		final int height = BallGame.dim.height;
		
		g.setColor(Color.black);
		g.fillRect(0, dim.height - 50, width, 50);
		g.setColor(Color.red);
		g.fillRect(10, height - 35, this.player.getHealth(), 20);
		if ((this.bonus.isActive) && (this.bonus.getTime() > 0)) {
			g.setColor(Color.orange);
			g.fillRect(width - 10 - this.bonus.getTime(), height - 35, this.bonus.getTime(), 20);
		}
		g.setColor(Color.white);
		g.drawString("Health: " + this.player.getHealth() + "%", 20, height - 20);
		if ((this.bonus.isActive) && (this.bonus.getTime() > 0)) {
			g.drawString("Speed Left: " + this.bonus.getTime(), width - 100, height - 20);
		}
		if (!this.player.isAlive()) {
			g.drawString("You are dead", width / 2 - 30, height / 3);
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
	
	public static double getRandomXPos() {
		return Math.random() * BallGame.dim.width;
	}
	
	public static double getRandomYPos() {
		return Math.random() * BallGame.dim.height - 50;
	}
	
	Bonus bonus;
	int counter;
	boolean enemyAttacking = false;
	Image buffer;
	Graphics bufferGraphics;
	public static Dimension dim;
}
