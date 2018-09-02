package net.jonhopkins.ballgame;

import java.awt.Color;

public class SpeedBonus extends Bonus {
	public SpeedBonus() {
		super(Color.orange);
		
		this.speedBoost += Math.random() * 1.7 + 0.3;
		this.timer += (int)(Math.random() * 60.0) + 40;
	}
}
