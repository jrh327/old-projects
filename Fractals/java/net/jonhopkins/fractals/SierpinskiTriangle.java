package net.jonhopkins.fractals;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class SierpinskiTriangle extends FractalPanel {
	private static final long serialVersionUID = -3554833250804966008L;
	
	private JLabel lblX;
	private JLabel lblY;
	private JButton cmdDraw;
	private JTextField txtIterations;
	private JButton cmdDrawRandom;
	
	private class vertex {
		int X;
		int Y;
	}
	
	private vertex[] v = new vertex[3];
	
	public SierpinskiTriangle() {
		for (int i = 0; i < v.length; i++) {
			v[i] = new vertex();
		}
		
		lblX = new JLabel("");
		lblX.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		lblY = new JLabel("");
		lblY.setHorizontalAlignment(SwingConstants.RIGHT);
		lblY.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		cmdDraw = new JButton("Draw");
		cmdDraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdDraw_Click();
			}
		});
		
		txtIterations = new JTextField("5");
		txtIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cmdDrawRandom = new JButton("Random");
		cmdDrawRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdDrawRandom_Click();
			}
		});
		
		setLayout(new GridLayout(0, 1));
		
		add(lblX);
		add(lblY);
		add(cmdDraw);
		add(txtIterations);
		add(cmdDrawRandom);
	}
	
	private vertex midpoint(vertex p1, vertex p2) {
		vertex midpoint = new vertex();
		midpoint.X = (p1.X + p2.X) / 2;
		midpoint.Y = (p1.Y + p2.Y) / 2;
		return midpoint;
	}
	
	private void draw_line(vertex p1, vertex p2) {
		graphics.drawLine(p1.X, p1.Y, p2.X, p2.Y);
	}
	
	private void draw_vertices(vertex v1, vertex v2, vertex v3) {
		draw_line(v1, v2);
		draw_line(v2, v3);
		draw_line(v3, v1);
	}
	
	private void draw_triangle(vertex v1, vertex v2, vertex v3, int i) {
		draw_line(v1, v2);
		draw_line(v2, v3);
		draw_line(v3, v1);
		
		if (i > 0) {
			draw_triangle(v1, midpoint(v1, v2), midpoint(v1, v3), i - 1);
			draw_triangle(v2, midpoint(v1, v2), midpoint(v2, v3), i - 1);
			draw_triangle(v3, midpoint(v1, v3), midpoint(v2, v3), i - 1);
		}
	}
	
	private void draw_triangle_random() {
		vertex[] vert = new vertex[2];
		int rndvert;
		
		for (int i = 0; i < vert.length; i++) {
			vert[i] = new vertex();
		}
		
		Random rand = new Random();
		for (int i = 0; i < 100000; i++) {
			float m = (v[0].Y - v[1].Y) / (v[0].X - v[1].X);
			
			vert[0].Y = (int)(rand.nextFloat() * (v[1].Y - v[0].Y)) + v[0].Y; //(Int(Rnd * 610) + 50)
			
			vert[0].X = (int)(rand.nextFloat() * (vert[0].Y - (v[0].Y) / m));
			
			//vert(1).X = Int(Rnd * (v(3).X - v(2).X)) + v(2).X '(Int(Rnd * 800) + 25)
			
			rndvert = (int)(rand.nextFloat() * 3);
			
			vert[1] = midpoint(vert[0], v[rndvert]);
			
			graphics.drawRect(vert[0].X, vert[0].Y, 0, 0); //, RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255))
			graphics.drawRect(vert[1].X, vert[1].Y, 0, 0); //, RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255))
		}
		
		this.getRootPane().repaint();
	}
	
	private void cmdDraw_Click() {
		graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		v[0].X = 425;
		v[0].Y = 50;
		v[1].X = 25;
		v[1].Y = 660;
		v[2].X = 825;
		v[2].Y = 660;
		
		draw_triangle(v[0], v[1], v[2], Integer.parseInt(txtIterations.getText()));
		
		getRootPane().repaint();
	}
	
	private void cmdDrawRandom_Click() {
		graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		v[0].X = 425;
		v[0].Y = 50;
		v[1].X = 25;
		v[1].Y = 660;
		v[2].X = 825;
		v[2].Y = 660;
		
		draw_triangle_random();
	}
	
	@Override
	public void mouseClick(int button, int shift, int x, int y) {
		if (button == 1) {
			lblX.setText(String.valueOf(x));
			lblY.setText(String.valueOf(y));
		}
	}
}
