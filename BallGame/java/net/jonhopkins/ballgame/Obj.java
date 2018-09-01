package net.jonhopkins.ballgame;

import java.awt.Color;
import java.awt.Graphics;

class Obj {
	protected double positionX;
	protected double positionY;
	protected int radius;
	protected Color color;
	
	public Obj(double x, double y, Color c) {
		this.positionX = x;
		this.positionY = y;
		this.color = c;
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int)this.positionX - this.radius, (int)this.positionY - this.radius, 2 * this.radius, 2 * this.radius);
	}
	
	public double getX() {
		return this.positionX;
	}
	
	public double getY() {
		return this.positionY;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public boolean checkCollision(Obj o2) {
		double distance = Math.pow(
				Math.pow(this.positionX - o2.positionX, 2.0) + Math.pow(this.positionY - o2.positionY, 2.0),
				0.5);
		return distance < this.radius + o2.radius * 0.9;
	}
}
