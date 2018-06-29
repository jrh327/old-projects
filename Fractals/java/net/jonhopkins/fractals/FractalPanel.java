package net.jonhopkins.fractals;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class FractalPanel extends JPanel {
	private static final long serialVersionUID = -1813873174138867867L;
	
	protected final int RENDER_WIDTH = 900;
	protected final int RENDER_HEIGHT = 700;
	
	protected Graphics graphics;
	
	public void setGraphics(Graphics g) {
		graphics = g;
	}
	
	public abstract void mouseClick(int button, int shift, int x, int y);
}
