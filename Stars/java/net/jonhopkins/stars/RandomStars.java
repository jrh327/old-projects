package net.jonhopkins.stars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class RandomStars extends JFrame implements Runnable {
	private static final long serialVersionUID = -640481485929655288L;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private Image buffer;
	private Graphics graphics;
	
	public static void main(String[] args) {
		new RandomStars().init();
	}
	
	public void init() {
		final RandomStars stars = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpWindow();
				buffer = createImage(WIDTH, HEIGHT);
				graphics = buffer.getGraphics();
				
				Thread thread = new Thread(stars, "Stars");
				thread.start();
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		synchronized (buffer) {
			g.drawImage(buffer, 0, 0, this);
		}
	}
	
	public void setUpWindow() {
		setTitle("Stars");
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void run() {
		boolean quad1 = true;
		boolean quad2 = false;
		boolean quad3 = false;
		boolean quad4 = false;
		int xpos = 21;
		int ypos = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		
		int pixelx = WIDTH / 40;
		int pixely = HEIGHT / 40;
		int originx = WIDTH / 2;
		int originy = HEIGHT / 2;
		
		Random rand = new Random();
		
		while (true) {
			red = rand.nextInt(256);
			green = rand.nextInt(256);
			blue = rand.nextInt(256);
			
			synchronized (buffer) {
				graphics.setColor(new Color(red, green, blue));
				graphics.drawLine(originx + (xpos * pixelx), originy, originx, originy - (ypos * pixely));
			}
			
			if (quad1) {
				xpos = xpos - 1;
				ypos = Math.abs(xpos - 21);
				if (xpos == -1) {
					xpos = 0;
					quad1 = false;
					quad2 = true;
				}
			}
			if (quad2) {
				xpos = xpos - 1;
				ypos = Math.abs(xpos + 21);
				if (xpos == -22) {
					quad2 = false;
					quad3 = true;
				}
			}
			if (quad3) {
				xpos = xpos + 1;
				ypos = -Math.abs(xpos + 21);
				if (xpos == 1) {
					xpos = 0;
					quad3 = false;
					quad4 = true;
				}
			}
			if (quad4) {
				xpos = xpos + 1;
				ypos = -Math.abs(xpos - 21);
				if (xpos == 21) {
					ypos = 0;
					quad4 = false;
					quad1 = true;
					repaint();
				}
			}
		}
	}
}
