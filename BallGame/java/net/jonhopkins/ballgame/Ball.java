package net.jonhopkins.ballgame;

import java.awt.Color;
import java.awt.Graphics;

class Ball extends Obj {
	private double velocityX;
	private double velocityY;
	private double speed = 1D;
	private int health;
	private boolean isAlive = true;
	
	public Ball(double x, double y, Color c) {
		super(x, y, c);
		
		this.radius = 20;
		
		double direction = Math.random() * 6.283185307179586D;
		this.velocityX = (Math.cos(direction) * this.speed);
		this.velocityY = (Math.sin(direction) * this.speed);
		
		this.health = 100;
	}
	
	public void changeDirection(double x2, double y2) {
		double x = x2 - this.positionX;
		double y = y2 - this.positionY;
		
		if ((x == 0D) || (y == 0D)) {
			return;
		}
		
		double hyp = Math.pow(Math.pow(x, 2.0D) + Math.pow(y, 2.0D), 0.5D);
		
		if (hyp == 0D) {
			return;
		}
		
		this.velocityX = (x / hyp * this.speed);
		this.velocityY = (y / hyp * this.speed);
	}
	
	public void update(int counter) {
		this.positionX += this.velocityX;
		this.positionY += this.velocityY;
		
		if ((this.positionX - this.radius <= 0D) || (this.positionX + this.radius >= BallGame.dim.width)) {
			this.velocityX *= -1.0D;
		}
		if ((this.positionY - this.radius <= 0D) || (this.positionY + this.radius >= BallGame.dim.height - 50)) {
			this.velocityY *= -1.0D;
		}
		
		if (counter == 100) {
			if (getHealth() > 100) {
				incHealth(-1);
			} else if (getHealth() < 100) {
				incHealth(1);
			}
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int)this.positionX - this.radius, (int)this.positionY - this.radius, 2 * this.radius, 2 * this.radius);
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
	
	public boolean isAlive() {
		return this.isAlive;
	}
}
