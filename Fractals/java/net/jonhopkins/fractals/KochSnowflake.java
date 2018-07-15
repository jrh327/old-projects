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
		v[2].X = (int)(150 + 500 * Math.cos(Math.PI / 3));
		v[2].Y = (int)(550 - 500 * Math.sin(Math.PI / 3));
		
		draw_snowflake(v[0], v[1], 180, Integer.parseInt(txtIterations.getText()));
		draw_snowflake(v[1], v[2], -60, Integer.parseInt(txtIterations.getText()));
		draw_snowflake(v[2], v[0], 60, Integer.parseInt(txtIterations.getText()));
		
		getRootPane().repaint();
	}
	
	private void draw_snowflake(vertex v1, vertex v2, int angle, int i) {
		if (i > 0) {
			int length = (int)Math.sqrt((v2.X - v1.X) * (v2.X - v1.X) + (v2.Y - v1.Y) * (v2.Y - v1.Y));
			// third of way up the side, third of the way from other side
			// connect at midpoint of side a (length of side) / 3 * 3 ^ .5 away
			
			vertex firstThird = new vertex();
			firstThird.X = v1.X + ((v2.X - v1.X) / 3);
			firstThird.Y = v1.Y - ((v1.Y - v2.Y) / 3);
			
			vertex midpoint = new vertex();
			midpoint.X = (int)(((v1.X + v2.X) / 2) + (((length / 3) * Math.sqrt(3) / 2) * Math.cos(radians(angle + 90))));
			midpoint.Y = (int)(((v1.Y + v2.Y) / 2) - (((length / 3) * Math.sqrt(3) / 2) * Math.sin(radians(angle + 90))));
			
			vertex secondThird = new vertex();
			secondThird.X = (int)(v1.X + ((v2.X - v1.X) * (2.0 / 3.0)));
			secondThird.Y = (int)(v1.Y - ((v1.Y - v2.Y) * (2.0 / 3.0)));
			
			draw_snowflake(v1, firstThird, angle, i - 1);
			draw_snowflake(firstThird, midpoint, angle - 60, i - 1);
			draw_snowflake(midpoint, secondThird, angle + 60, i - 1);
			draw_snowflake(secondThird, v2, angle, i - 1);
		} else {
			draw_line(v1, v2);
		}
	}
	
	private void draw_line(vertex p1, vertex p2) {
		graphics.drawLine(p1.X, p1.Y, p2.X, p2.Y);
	}
	
	@Override
	public void mouseClick(int button, int shift, int x, int y) {
		if (button == 1) {
			graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
			
			v[0].X = x;
			v[0].Y = y;
			v[1].X = x + 500;
			v[1].Y = y;
			v[2].X = (int)(x + 500 * Math.cos(Math.PI / 3));
			v[2].Y = (int)(y - 500 * Math.sin(Math.PI / 3));
			
			draw_snowflake(v[0], v[1], 180, Integer.parseInt(txtIterations.getText()));
			draw_snowflake(v[1], v[2], -60, Integer.parseInt(txtIterations.getText()));
			draw_snowflake(v[2], v[0], 60, Integer.parseInt(txtIterations.getText()));
			
			getRootPane().repaint();
		}
		if (button == 2) {
			System.exit(0);
		}
	}
}
