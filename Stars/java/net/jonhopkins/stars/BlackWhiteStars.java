package net.jonhopkins.stars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BlackWhiteStars extends JFrame implements Runnable {
	private static final long serialVersionUID = -640481485929655288L;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private final int PIXELS_PER_X_UNIT = WIDTH / 40;
	private final int PIXELS_PER_Y_UNIT = HEIGHT / 40;
	private final int ORIGIN_X = WIDTH / 2;
	private final int ORIGIN_Y = HEIGHT / 2;
	
	private Image buffer;
	private Graphics graphics;
	
	public static void main(String[] args) {
		new BlackWhiteStars().init();
	}
	
	public void init() {
		final BlackWhiteStars stars = this;
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
		boolean clearing = false;
		int xpos = 21;
		int ypos = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		
		while (true) {
			if (!clearing) {
				red = red + 255 / 80;
				green = green + 255 / 80;
				blue = blue + 255 / 80;
			} else {
				red = 0;
				blue = 0;
				green = 0;
			}
			
			synchronized (buffer) {
				graphics.setColor(new Color(red, green, blue));
				drawLine(graphics, xpos, ypos);
				repaint();
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
					clearing = !clearing;
				}
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void drawLine(Graphics graphics, int xpos, int ypos) {
		int x1 = ORIGIN_X + (xpos * PIXELS_PER_X_UNIT);
		int y1 = ORIGIN_Y;
		int x2 = ORIGIN_X;
		int y2 = ORIGIN_Y - (ypos * PIXELS_PER_Y_UNIT);
		
		synchronized (buffer) {
			graphics.drawLine(x1, y1, x2, y2);
		}
	}
}
