package net.jonhopkins.mandelbrot;
 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
 
public class MandelbrotJulia extends Applet implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private Graphics bg;
	private Image backbuffer;
	private double kReal = -0.796;
	private double kImaginary = -0.1353;
	private boolean julia;
	private double minReal = -2.0;
	private double maxReal = 1.0;
	private double minImaginary = -1.5;
	private double maxImaginary;
	private double realFactor;
	private double imaginaryFactor;
	private long lastDrawTime = Calendar.getInstance().getTimeInMillis();
	
	public void init() {
		setSize(1000, 500);
		width = getSize().width;
		height = getSize().height;
		maxImaginary = minImaginary + (maxReal - minReal) * height / (width / 2);
		realFactor = (maxReal - minReal) / (width / 2 - 1);
		imaginaryFactor = (maxImaginary - minImaginary) / (height - 1);
		backbuffer = createImage(width, height);
		bg = backbuffer.getGraphics();
		bg.setColor(Color.black);
		bg.fillRect(0, 0, width, height);
		setBackground(Color.black);
		julia = false;
		draw();
		lastDrawTime = Calendar.getInstance().getTimeInMillis();
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
			minX = width / 2;
			maxX = width;
		} else {
			minX = 0;
			maxX = width / 2;
		}
		bg.setColor(Color.black);
		bg.fillRect(minX, 0, maxX - 1, height);
		
		for (int y = 0; y < height; y++) {
			double cImaginary = maxImaginary - y * imaginaryFactor;
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
			bg.drawString("Julia set - K=" + strKReal + (kImaginary >= 0 ? "+" : "-") + strKImaginary + "i", width / 2 + 20, 10);
		} else {
			bg.drawString("Mandelbrot set", 20, 10);
		}
		
		repaint();
	}
	
	public void mouseMoved(MouseEvent me) {
		if (Calendar.getInstance().getTimeInMillis() - lastDrawTime < 20) return;
		synchronized(backbuffer) {
			if (me.getX() <= width / 2) {
				minReal = -2.0;
				maxReal = 1.0;
				realFactor = (maxReal - minReal) / (width / 2 - 1);
				kReal = minReal + me.getX() * realFactor;
				kImaginary = maxImaginary - me.getY() * imaginaryFactor;
				julia = true;
				minReal = -1.5;
				maxReal = 1.5;
				realFactor = (maxReal - minReal) / (width / 2 - 1);
				draw();
			}
		}
		lastDrawTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public void mouseDragged(MouseEvent me) { }
	
	public void update(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public void paint(Graphics g ) {
		update(g);
	}
}
