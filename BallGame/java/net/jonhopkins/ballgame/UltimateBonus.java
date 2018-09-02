package net.jonhopkins.ballgame;

import java.awt.Color;

public class UltimateBonus extends Bonus {
	public UltimateBonus() {
		super(Color.yellow);
		
		this.healthBoost = 100;
		this.speedBoost += 2.0;
		this.timer += 150;
	}
}
