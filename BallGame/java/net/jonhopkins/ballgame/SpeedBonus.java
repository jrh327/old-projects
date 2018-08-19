package net.jonhopkins.ballgame;

import java.awt.Color;

public class SpeedBonus extends Bonus {
	public SpeedBonus(double x, double y) {
		super(x, y, Color.orange);
		
		this.speedBoost += Math.random() * 1.7D + 0.3D;
		this.timer += (int)(Math.random() * 60.0D) + 40;
	}
}
