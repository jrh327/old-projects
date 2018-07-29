package net.jonhopkins.ballgame;

import java.awt.Color;
import java.awt.Graphics;

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
	
	public boolean checkCollision(Obj o2) {
		double distance = Math.pow(
				Math.pow(this.x_pos - o2.x_pos, 2.0) + Math.pow(this.y_pos - o2.y_pos, 2.0),
				0.5);
		return distance < this.radius + o2.radius * 0.9D;
	}
	
	protected double x_pos;
	protected double y_pos;
	protected int radius;
	protected Color color;
}
