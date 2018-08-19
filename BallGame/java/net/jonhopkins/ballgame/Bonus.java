package net.jonhopkins.ballgame;

import java.awt.Color;

class Bonus extends Obj {
	protected int timer = 0;
	private int onFieldTimer = 0;
	protected double speedBoost = 0D;
	protected int healthBoost = 0;
	private boolean isActive = false;
	private boolean isOnField = false;
	
	public Bonus(double x, double y, Color c) {
		super(x, y, c);
		
		this.radius = 5;
		this.isOnField = true;
		this.onFieldTimer = 150;
	}
	
	public void activate(Ball b) {
		b.incSpeed(this.speedBoost);
		b.incHealth(this.healthBoost);
		if (b.getHealth() > 100) {
			b.incHealth(100 - b.getHealth());
		}
		this.isOnField = false;
		this.isActive = true;
	}
	
	public void deactivate(Ball b) {
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
	
	public void decTime(int counter) {
		if ((this.timer > 0) && (counter % 5 == 0)) this.timer -= 1;
	}
	
	public void decOnFieldTime(int counter) {
		if ((this.timer > 0) && (counter % 5 == 0)) this.onFieldTimer -= 1;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isOnField() {
		return this.isOnField;
	}
	
	public void setOnField(boolean isOnField) {
		this.isOnField = isOnField;
	}
}
