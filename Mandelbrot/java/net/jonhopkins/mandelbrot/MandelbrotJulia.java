package net.jonhopkins.mandelbrot;
 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
 
public class MandelbrotJulia extends Applet implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private int width, height;
	private Graphics bg;
	private Image backbuffer;
	private double K_real = -0.796, K_imaginary = -0.1353;
	private boolean julia;
	private boolean drawing;
	private double MinReal = -2.0;
	private double MaxReal = 1.0;
	private double MinImaginary = -1.5;
	private double MaxImaginary;
	private double Real_factor;
	private double Imaginary_factor;
	private long lastDrawTime = Calendar.getInstance().getTimeInMillis();
	
	public void init() {
		setSize(1000, 500);
		width = getSize().width;
		height = getSize().height;
		MaxImaginary = MinImaginary + (MaxReal - MinReal) * height / (width / 2);
		Real_factor = (MaxReal - MinReal) / (width / 2 - 1);
		Imaginary_factor = (MaxImaginary - MinImaginary) / (height - 1);
		backbuffer = createImage(width, height);
		bg = backbuffer.getGraphics();
		bg.setColor(Color.black);
		bg.fillRect(0, 0, width, height);
		setBackground(Color.black);
		julia = false;
		drawing = true;
		draw();
		lastDrawTime = Calendar.getInstance().getTimeInMillis();
		drawing = false;
		addMouseMotionListener(this);
	}
	
	public void destroy() {
		System.exit(0);
	}
	
	public void draw() {
		int MaxIterations = 25;
		
		int minx, maxx;
		if (julia) {
			minx = width / 2; maxx = width;
		} else {
			minx = 0; maxx = width / 2;
		}
		bg.setColor(Color.black);
		bg.fillRect(minx, 0, maxx - 1, height);
		
		int y, x, iteration;
		double c_imaginary, c_real, Z_imaginary, Z_real, Z_imaginary2, Z_real2;
		for (y = 0; y < height; y++) {
			c_imaginary = MaxImaginary - y * Imaginary_factor;
			for (x = minx; x < maxx; x++) {
				c_real = MinReal + (x - minx) * Real_factor;
				
				Z_real = c_real;
				Z_imaginary = c_imaginary;
				for (iteration = 0; iteration < MaxIterations; iteration++) {
					Z_real2 = Z_real * Z_real;
					Z_imaginary2 = Z_imaginary * Z_imaginary;
					if(Z_real2 + Z_imaginary2 > 4) {
						break;
					}
					Z_imaginary = 2 * Z_real * Z_imaginary + (julia ? K_imaginary : c_imaginary);
					Z_real = Z_real2 - Z_imaginary2 + (julia ? K_real : c_real);
				}
				if (iteration < MaxIterations) {
					if (iteration < MaxIterations / 2) {
						bg.setColor(new Color((int)(255 / MaxIterations / 2 * iteration + 1), 0, 0));
					} else {
						bg.setColor(new Color(255, (int)(255 / MaxIterations * iteration + 1), (int)(255 / MaxIterations * iteration + 1)));
					}
					bg.drawLine(x, y, x, y);
				}
			}
		}
		
		bg.setColor(Color.white);
		if (julia) {
			String k_real = String.valueOf(K_real);
			if (k_real.length() > 6) {
				k_real = k_real.substring(0, 6);
			} else if (k_real.length() < 6) {
				k_real = k_real.concat(("000000").substring(0, 6 - k_real.length()));
			}
			String k_imaginary = String.valueOf(Math.abs(K_imaginary));
			if (k_imaginary.length() > 6) {
				k_imaginary = k_imaginary.substring(0, 6);
			} else if (k_imaginary.length() < 6) {
				k_imaginary = k_imaginary.concat(("000000").substring(0, 6 - k_imaginary.length()));
			}
			bg.drawString("Julia set - K=" + k_real + (K_imaginary >= 0 ? "+" : "-") + k_imaginary + "i", width / 2 + 20, 10);
		} else {
			bg.drawString("Mandelbrot set", 20, 10);
		}
		
		repaint();
	}
	
	public void mouseMoved(MouseEvent me) {
		if (Calendar.getInstance().getTimeInMillis() - lastDrawTime < 20) return;
		synchronized(this) {
			if (me.getX() <= width / 2 && !drawing) {
				drawing = true;
				MinReal = -2.0;
				MaxReal = 1.0;
				Real_factor = (MaxReal - MinReal) / (width / 2 - 1);
				K_real = MinReal + me.getX() * Real_factor;
				K_imaginary = MaxImaginary - me.getY() * Imaginary_factor;
				julia = true;
				MinReal = -1.5;
				MaxReal = 1.5;
				Real_factor = (MaxReal - MinReal) / (width / 2 - 1);
				draw();
				drawing = false;
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
