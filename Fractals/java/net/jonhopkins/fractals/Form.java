package net.jonhopkins.fractals;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Form extends JFrame {
	private static final long serialVersionUID = 5015868234928007551L;
	private JPanel renderArea;
	private Image buffer;
	private Graphics pctRobot;
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpComponents();
				setUpWindow();
			}
		});
	}
	
	public void setUpWindow() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}
	
	public void setUpComponents() {
		renderArea = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				if (buffer == null) {
					buffer = renderArea.createImage(RENDER_WIDTH, RENDER_HEIGHT);
					pctRobot = buffer.getGraphics();
				}
				synchronized (buffer) {
					g.drawImage(buffer, 0, 0, this);
				}
			}
		};
		renderArea.setPreferredSize(new Dimension(RENDER_WIDTH, RENDER_HEIGHT));
	}
}
