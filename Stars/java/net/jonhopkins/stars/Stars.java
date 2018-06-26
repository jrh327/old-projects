package net.jonhopkins.stars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Stars extends JFrame implements Runnable {
	private static final long serialVersionUID = -640481485929655288L;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private Image buffer;
	private Graphics graphics;
	
	public static void main(String[] args) {
		new Stars().init();
	}
	
	public void init() {
		final Stars stars = this;
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
	
	private void Form_Activate() {
		boolean quad1 = true;
		boolean quad2 = false;
		boolean quad3 = false;
		boolean quad4 = false;
		int clearing = -1;
		int xpos = 21;
		int ypos = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		
		int pixelx = WIDTH / 40;
		int pixely = HEIGHT / 40;
		int originx = WIDTH / 2;
		int originy = HEIGHT / 2;
		
		while (true) {
			if (clearing == -1) {
				red = red + 255 / 80;
				green = green + 255 / 80;
				blue = blue + 255 / 80;
			}
			//If clearing = -1 Then red = Int(Rnd * 255): green = Int(Rnd * 255): blue = Int(Rnd * 255):
			if (clearing == 1) {
				red = 0;
				blue = 0;
				green = 0;
			}
			
			synchronized (buffer) {
				graphics.setColor(new Color(red, blue, green));
				graphics.drawLine(originx + (xpos * pixelx), originy, originx, originy - (ypos * pixely));
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
					clearing = -clearing;
				}
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		Form_Activate();
	}
}
