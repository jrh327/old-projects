package net.jonhopkins.ballgame;

import java.awt.Color;

public class HealthBonus extends Bonus {

	public HealthBonus(double x, double y) {
		super(x, y, Color.red);
		
		this.healthBoost = ((int)(Math.random() * 10.0) + 10);
	}
}
