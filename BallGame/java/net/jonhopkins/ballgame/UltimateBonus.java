package net.jonhopkins.ballgame;

import java.awt.Color;

public class UltimateBonus extends Bonus {
	public UltimateBonus(double x, double y) {
		super(x, y, Color.yellow);
		
		this.healthBoost = 100;
		this.speedBoost += 2.0D;
		this.timer += 150;
	}
}
