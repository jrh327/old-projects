package net.jonhopkins.fractals;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class FractalFlower extends FractalPanel {
	private static final long serialVersionUID = -5935270177504352498L;
	
	private int maxIterations = -1;
	private int intraIterations = 1;
	private double theta = 0;
	private Color stem = new Color(34, 139, 34);
	private Color flower = new Color(255, 0, 255);
	private double shrinkFactor = 0.8;
	private int stemHeight = RENDER_HEIGHT / 25;
	private int iterationsBeforePetals = 8;
	private int framesToPause = 40;
	private int pausedFrames = 0;
	private Timer timer;
	
	public FractalFlower() {
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pausedFrames <= 0) {
					graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
					
					theta = Math.PI / 24.0;
					
					tree(RENDER_WIDTH / 2, RENDER_HEIGHT - 25, stemHeight, shrinkFactor, Math.PI / 2.0, maxIterations, intraIterations);
					
					intraIterations++;
					if (intraIterations > 5) {
						intraIterations = 1;
						maxIterations++;
						if (maxIterations > 12) {
							maxIterations = -1;
							pausedFrames = framesToPause;
						}
					}
				} else {
					pausedFrames--;
				}
				
				getRootPane().repaint();
			}
		});
		
		timer.start();
	}
	
	private void tree(int x1, int y1, double length, double shrinkFactor, double angle, int iterations, int intra) {
		int x2 = (int)(x1 + length * 5.0 * Math.cos(angle));
		int y2 = (int)(y1 - length * 5.0 * Math.sin(angle));
		
		double drawLength;
		if (iterations < 0) {
			drawLength = (length * 0.2 * intraIterations) * 5.0;
		} else {
			drawLength = length * 5.0;
		}
		x2 = (int)(x1 + drawLength * Math.cos(angle));
		y2 = (int)(y1 - drawLength * Math.sin(angle));

		if (maxIterations - iterations > iterationsBeforePetals) {
			graphics.setColor(flower);
		} else {
			graphics.setColor(stem);
		}
		
		graphics.drawLine(x1, y1, x2, y2);
		
		if (iterations > 0) {
			tree(x2, y2, length * shrinkFactor, shrinkFactor, angle + theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
			tree(x2, y2, length * shrinkFactor, shrinkFactor, angle - theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
		} else if (iterations == 0) {
			tree(x2, y2, length * shrinkFactor / 2, shrinkFactor, angle + theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
			tree(x2, y2, length * shrinkFactor / 2, shrinkFactor, angle - theta * ((iterations + 1) / 12.0), iterations - 1, intraIterations);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (!enabled) {
			graphics.setColor(Color.BLACK);
			timer.stop();
		}
	}
	
	@Override
	public void mouseClick(int button, int shift, int x, int y) {
		
	}
}
