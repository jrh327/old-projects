package net.jonhopkins.stars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class RGBStars extends JFrame implements Runnable {
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
		new RGBStars().init();
	}
	
	public void init() {
		final RGBStars stars = this;
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
	
	private enum Drawing {
		RED,
		GREEN,
		BLUE
	}
	
	@Override
	public void run() {
		int red = 0;
		int green = 0;
		int blue = 0;
		int direction = 1;
		Drawing drawing = Drawing.RED;
		
		while (true) {
			switch (drawing) {
			case RED:
				red += direction;
				if (red > 255) {
					red = 254;
					direction = -direction;
				} else if (red < 0) {
					red = 0;
					direction = -direction;
					drawing = Drawing.GREEN;
				}
				break;
			case GREEN:
				green += direction;
				if (green > 255) {
					green = 254;
					direction = -direction;
				} else if (green < 0) {
					green = 0;
					direction = -direction;
					drawing = Drawing.BLUE;
				}
				break;
			case BLUE:
				blue += direction;
				if (blue > 255) {
					blue = 254;
					direction = -direction;
				} else if (blue < 0) {
					blue = 0;
					direction = -direction;
					drawing = Drawing.RED;
				}
				break;
			}
			
			graphics.setColor(new Color(red, green, blue));
			for (int xpos = 21; xpos > -1; xpos--) {
				int ypos = Math.abs(xpos - 21);
				drawLine(graphics, xpos, ypos);
			}
			for (int xpos = 0; xpos > -22; xpos--) {
				int ypos = Math.abs(xpos + 21);
				drawLine(graphics, xpos, ypos);
			}
			for (int xpos = -21; xpos < 1; xpos++) {
				int ypos = -Math.abs(xpos + 21);
				drawLine(graphics, xpos, ypos);
			}
			for (int xpos = 0; xpos < 22; xpos++) {
				int ypos = -Math.abs(xpos - 21);
				drawLine(graphics, xpos, ypos);
			}
			
			repaint();
			try {
				Thread.sleep(5);
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
