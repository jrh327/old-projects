package net.jonhopkins.ballgame;

import java.awt.Color;

public class Enemy extends Ball {
	private Player target;
	private boolean isAttacking = false;
	
	public Enemy(Player target) {
		super(100.0D, 175.0D, Color.yellow);
		this.target = target;
	}
	
	@Override
	public void update(int counter) {
		super.update(counter);
		
		if (isAttacking) {
			if ((int)(Math.random() * 500.0D) == 37) {
				isAttacking = false;
				setColor(Color.yellow);
			} else {
				changeDirection(target.getX(), target.getY());
				checkAttack();
			}
		} else if ((int)(Math.random() * 100.0D) == 37) {
			setColor(Color.red);
			isAttacking = true;
		} else if ((int)(Math.random() * 50.0D) == 5) {
			double newX = BallGame.getRandomXPos();
			double newY = BallGame.getRandomYPos();
			changeDirection(newX, newY);
		}
	}
	
	public void checkAttack() {
		if (checkCollision(target)) {
			target.incHealth(-(int)(Math.random() * 15.0D));
			isAttacking = false;
			setColor(Color.yellow);
			double newX = BallGame.getRandomXPos();
			double newY = BallGame.getRandomYPos();
			changeDirection(newX, newY);
		}
	}
}
