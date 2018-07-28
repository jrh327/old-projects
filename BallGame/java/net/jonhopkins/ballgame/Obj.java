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
	
	protected double x_pos;
	protected double y_pos;
	protected int radius;
	protected Color color;
}
