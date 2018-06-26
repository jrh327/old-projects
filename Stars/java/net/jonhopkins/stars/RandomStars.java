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
	private final int PIXELS_PER_X_UNIT = WIDTH / 40;
	private final int PIXELS_PER_Y_UNIT = HEIGHT / 40;
	private final int ORIGIN_X = WIDTH / 2;
	private final int ORIGIN_Y = HEIGHT / 2;
	
	private Image buffer;
	private Graphics graphics;
	private Random rand;
	
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
		rand = new Random();
		
		while (true) {
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
		}
	}
	
	private void drawLine(Graphics graphics, int xpos, int ypos) {
		int red = rand.nextInt(256);
		int green = rand.nextInt(256);
		int blue = rand.nextInt(256);
		
		int x1 = ORIGIN_X + (xpos * PIXELS_PER_X_UNIT);
		int y1 = ORIGIN_Y;
		int x2 = ORIGIN_X;
		int y2 = ORIGIN_Y - (ypos * PIXELS_PER_Y_UNIT);
		
		synchronized (buffer) {
			graphics.setColor(new Color(red, green, blue));
			graphics.drawLine(x1, y1, x2, y2);
		}
	}
}
