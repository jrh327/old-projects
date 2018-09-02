package net.jonhopkins.ballgame;

import java.awt.Color;

public class HealthBonus extends Bonus {

	public HealthBonus() {
		super(Color.red);
		
		this.healthBoost = ((int)(Math.random() * 10.0) + 10);
	}
}
