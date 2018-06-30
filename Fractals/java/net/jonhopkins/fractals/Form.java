package net.jonhopkins.fractals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Form extends JFrame {
	private static final long serialVersionUID = 5015868234928007551L;
	private FractalPanel currentFractal;
	private JButton btnSierpinski;
	private JButton btnTree;
	private JButton btnKoch;
	private JPanel renderArea;
	private Image buffer;
	private Graphics pctFractal;
	
	private final int RENDER_WIDTH = 900;
	private final int RENDER_HEIGHT = 700;
	
	public static void main(String[] args) {
		new Form().init();
	}
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpComponents();
				setUpWindow();
				
				buffer = renderArea.createImage(RENDER_WIDTH, RENDER_HEIGHT);
				pctFractal = buffer.getGraphics();
				currentFractal.setGraphics(pctFractal);
			}
		});
	}
	
	public void setUpWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
		buffer = renderArea.createImage(RENDER_WIDTH, RENDER_HEIGHT);
		pctFractal = buffer.getGraphics();
	}
	
	public void setUpComponents() {
		renderArea = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				if (buffer == null) {
					buffer = renderArea.createImage(RENDER_WIDTH, RENDER_HEIGHT);
					pctFractal = buffer.getGraphics();
				}
				synchronized (buffer) {
					g.drawImage(buffer, 0, 0, this);
				}
			}
		};
		renderArea.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				currentFractal.mouseClick(e.getButton(), e.getModifiers(), e.getX(), e.getY());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		});
		renderArea.setPreferredSize(new Dimension(RENDER_WIDTH, RENDER_HEIGHT));
		currentFractal = new SierpinskiTriangle();
		
		JFrame frame = this;
		JPanel topPanel = new JPanel();
		btnSierpinski = new JButton("Sierpinski");
		btnSierpinski.setEnabled(false);
		btnSierpinski.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(currentFractal);
				currentFractal = new SierpinskiTriangle();
				currentFractal.setGraphics(pctFractal);
				pctFractal.setColor(Color.BLACK);
				pctFractal.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
				
				btnSierpinski.setEnabled(false);
				btnTree.setEnabled(true);
				btnKoch.setEnabled(true);
				
				frame.add(currentFractal, BorderLayout.EAST);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		btnTree = new JButton("Tree");
		btnTree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(currentFractal);
				currentFractal = new FractalTree();
				currentFractal.setGraphics(pctFractal);
				pctFractal.setColor(Color.BLACK);
				pctFractal.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
				
				btnSierpinski.setEnabled(true);
				btnTree.setEnabled(false);
				btnKoch.setEnabled(true);
				
				frame.add(currentFractal, BorderLayout.EAST);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		btnKoch = new JButton("Koch");
		btnKoch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(currentFractal);
				currentFractal = new KochSnowflake();
				currentFractal.setGraphics(pctFractal);
				pctFractal.setColor(Color.BLACK);
				pctFractal.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
				
				btnSierpinski.setEnabled(true);
				btnTree.setEnabled(true);
				btnKoch.setEnabled(false);
				
				frame.add(currentFractal, BorderLayout.EAST);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		topPanel.add(btnSierpinski);
		topPanel.add(btnTree);
		topPanel.add(btnKoch);
		
		setTitle("Fractals");
		setLayout(new BorderLayout());
		add(renderArea, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		add(currentFractal, BorderLayout.EAST);
	}
}
