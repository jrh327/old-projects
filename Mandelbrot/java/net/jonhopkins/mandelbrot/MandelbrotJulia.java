package net.jonhopkins.mandelbrot;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MandelbrotJulia extends Applet implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private final int WIDTH;
	private final int HEIGHT;
	private Graphics bg;
	private Image backbuffer;
	private double kReal = -0.796;
	private double kImaginary = -0.1353;
	private boolean julia;
	private double minReal = -2.0;
	private double maxReal = 1.0;
	private final double MIN_IMAGINARY = -1.5;
	private final double MAX_IMAGINARY;
	private double realFactor;
	private final double IMAGINARY_FACTOR;
	private long lastDrawTime;
	
	public MandelbrotJulia() {
		WIDTH = 1000;
		HEIGHT = 500;
		MAX_IMAGINARY = MIN_IMAGINARY + (maxReal - minReal) * HEIGHT / (WIDTH / 2);
		realFactor = (maxReal - minReal) / (WIDTH / 2 - 1);
		IMAGINARY_FACTOR = (MAX_IMAGINARY - MIN_IMAGINARY) / (HEIGHT - 1);
	}
	
	public void init() {
		setSize(WIDTH, HEIGHT);
		backbuffer = createImage(WIDTH, HEIGHT);
		bg = backbuffer.getGraphics();
		bg.setColor(Color.black);
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		setBackground(Color.black);
		julia = false;
		draw();
		lastDrawTime = System.currentTimeMillis();
		addMouseMotionListener(this);
	}
	
	public void destroy() {
		System.exit(0);
	}
	
	public void draw() {
		int maxIterations = 25;
		
		int minX;
		int maxX;
		if (julia) {
			minX = WIDTH / 2;
			maxX = WIDTH;
		} else {
			minX = 0;
			maxX = WIDTH / 2;
		}
		bg.setColor(Color.black);
		bg.fillRect(minX, 0, maxX - 1, HEIGHT);
		
		for (int y = 0; y < HEIGHT; y++) {
			double cImaginary = MAX_IMAGINARY - y * IMAGINARY_FACTOR;
			for (int x = minX; x < maxX; x++) {
				double cReal = minReal + (x - minX) * realFactor;
				
				double zReal = cReal;
				double zImaginary = cImaginary;
				int iteration;
				for (iteration = 0; iteration < maxIterations; iteration++) {
					double zReal2 = zReal * zReal;
					double zImaginary2 = zImaginary * zImaginary;
					if (zReal2 + zImaginary2 > 4) {
						break;
					}
					zImaginary = 2 * zReal * zImaginary + (julia ? kImaginary : cImaginary);
					zReal = zReal2 - zImaginary2 + (julia ? kReal : cReal);
				}
				if (iteration < maxIterations) {
					if (iteration < maxIterations / 2) {
						bg.setColor(new Color((int)(255 / maxIterations / 2 * iteration + 1), 0, 0));
					} else {
						bg.setColor(new Color(255, (int)(255 / maxIterations * iteration + 1), (int)(255 / maxIterations * iteration + 1)));
					}
					bg.drawLine(x, y, x, y);
				}
			}
		}
		
		bg.setColor(Color.white);
		if (julia) {
			String strKReal = String.valueOf(kReal);
			if (strKReal.length() > 6) {
				strKReal = strKReal.substring(0, 6);
			} else if (strKReal.length() < 6) {
				strKReal = strKReal.concat(("000000").substring(0, 6 - strKReal.length()));
			}
			String strKImaginary = String.valueOf(Math.abs(kImaginary));
			if (strKImaginary.length() > 6) {
				strKImaginary = strKImaginary.substring(0, 6);
			} else if (strKImaginary.length() < 6) {
				strKImaginary = strKImaginary.concat(("000000").substring(0, 6 - strKImaginary.length()));
			}
			bg.drawString("Julia set - K=" + strKReal + (kImaginary >= 0 ? "+" : "-") + strKImaginary + "i", WIDTH / 2 + 20, 10);
		} else {
			bg.drawString("Mandelbrot set", 20, 10);
		}
		
		repaint();
	}
	
	public void mouseMoved(MouseEvent mouseEvent) {
		if (System.currentTimeMillis() - lastDrawTime < 20) {
			return;
		}
		
		synchronized (backbuffer) {
			if (mouseEvent.getX() <= WIDTH / 2) {
				minReal = -2.0;
				maxReal = 1.0;
				realFactor = (maxReal - minReal) / (WIDTH / 2 - 1);
				kReal = minReal + mouseEvent.getX() * realFactor;
				kImaginary = MAX_IMAGINARY - mouseEvent.getY() * IMAGINARY_FACTOR;
				julia = true;
				minReal = -1.5;
				maxReal = 1.5;
				realFactor = (maxReal - minReal) / (WIDTH / 2 - 1);
				draw();
				lastDrawTime = System.currentTimeMillis();
			}
		}
	}
	
	public void mouseDragged(MouseEvent mouseEvent) { }
	
	public void update(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public void paint(Graphics g ) {
		update(g);
	}
}
