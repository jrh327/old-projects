package net.jonhopkins.fractals;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KochSnowflake extends FractalPanel {
	private static final long serialVersionUID = 1356252655398118147L;
	
	private JButton cmdDraw;
	private JTextField txtIterations;
	
	private class vertex {
		int X;
		int Y;
	}
	
	private vertex[] v = new vertex[3];
	
	public KochSnowflake() {
		for (int i = 0; i < v.length; i++) {
			v[i] = new vertex();
		}
		
		cmdDraw = new JButton("Draw");
		cmdDraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdDraw_Click();
			}
		});
		
		txtIterations = new JTextField("2");
		txtIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		
		setLayout(new GridLayout(0, 1));
		
		add(cmdDraw);
		add(txtIterations);
	}
	
	private void cmdDraw_Click() {
		graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		v[0].X = 150;
		v[0].Y = 550;
		v[1].X = 650;
		v[1].Y = 550;
		v[2].X = (int)(150 + 500 * Math.cos(3.14159265358979 / 3));
		v[2].Y = (int)(550 - 500 * Math.sin(3.14159265358979 / 3));
		
		draw_vertices(v[0], v[1], v[2]);
		
		draw_snowflake(v[0], v[1], v[2], Integer.parseInt(txtIterations.getText()));
		
		getRootPane().repaint();
	}
	
	private void draw_snowflake(vertex v1, vertex v2, vertex v3, int i) {
		if (i > 0) {
			
			// third of way up the side, third of the way from other side
			// connect at midpoint of side a (length of side) / 3 * 3 ^ .5 away
			
			int x1 = v[0].X + ((v[2].X - v[0].X) / 3);
			int y1 = v[0].Y - ((v[0].Y - v[2].Y) / 3);
			int x2 = (int)(((v[0].X + v[2].X) / 2) + ((250 * Math.sqrt(3) / 2) * Math.cos(150 * (3.14159265358979 / 180))));
			int y2 = (int)(((v[0].Y + v[2].Y) / 2) - ((250 * Math.sqrt(3) / 2) * Math.sin(150 * (3.14159265358979 / 180))));
			graphics.drawLine(x1, y1, x2, y2);
			
			x1 = (int)(v[0].X + ((v[2].X - v[0].X) * (2.0 / 3.0)));
			y1 = (int)(v[0].Y - ((v[0].Y - v[2].Y) * (2.0 / 3.0)));
			x2 = (int)(((v[0].X + v[2].X) / 2) + ((250 * Math.sqrt(3) / 2) * Math.cos(150 * (3.14159265358979 / 180))));
			y2 = (int)(((v[0].Y + v[2].Y) / 2) - ((250 * Math.sqrt(3) / 2) * Math.sin(150 * (3.14159265358979 / 180))));
			graphics.drawLine(x1, y1, x2, y2);
		}
	}
	
	private vertex thirdify(vertex p1, vertex p2) {
		vertex thirdify = new vertex();
		thirdify.X = (p1.X + p2.X) / 3 * 2;
		thirdify.Y = (p1.Y + p2.Y) / 3 * 2;
		return thirdify;
	}
	
	private void draw_line(vertex p1, vertex p2) {
		graphics.drawLine(p1.X, p1.Y, p2.X, p2.Y);
	}
	
	private void draw_vertices(vertex v1, vertex v2, vertex v3) {
		draw_line(v1, v2);
		draw_line(v2, v3);
		draw_line(v3, v1);
	}
	
	@Override
	public void mouseClick(int button, int shift, int x, int y) {
		if (button == 1) {
			graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
			
			v[0].X = x;
			v[0].Y = y;
			v[1].X = x + 500;
			v[1].Y = y;
			v[2].X = (int)(x + 500 * Math.cos(3.14159265358979 / 3));
			v[2].Y = (int)(y - 500 * Math.sin(3.14159265358979 / 3));
			
			draw_vertices(v[0], v[1], v[2]);
			getRootPane().repaint();
		}
		if (button == 2) {
			System.exit(0);
		}
	}
}
