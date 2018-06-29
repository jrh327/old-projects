package net.jonhopkins.fractals;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FractalTree extends FractalPanel {
	private static final long serialVersionUID = 8225087688090778302L;
	
	private JCheckBox Check1;
	private JCheckBox Check2;
	private JLabel Label1;
	private JTextField txtLength;
	private JLabel Label2;
	private JTextField txtAngle;
	private JLabel Label3;
	private JTextField txtIterations;
	private JLabel Label4;
	private JTextField txtPercentage;
	private JButton Command1;
	
	public FractalTree() {
		Check1 = new JCheckBox("Colors :D");
		Check2 = new JCheckBox("Randomness");
		
		Label1 = new JLabel("Length");
		Label1.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtLength = new JTextField("150");
		txtLength.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label2 = new JLabel("Angle");
		Label2.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtAngle = new JTextField("30");
		txtAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label3 = new JLabel("Iterations");
		Label3.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtIterations = new JTextField("15");
		txtIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label4 = new JLabel("Percentage");
		Label4.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtPercentage = new JTextField("75");
		txtPercentage.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Command1 = new JButton("Draw");
		Command1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Command1_Click();
			}
		});
		
		setLayout(new GridLayout(0, 1));
		
		add(Check1);
		add(Check2);
		add(Label1);
		add(txtLength);
		add(Label2);
		add(txtAngle);
		add(Label3);
		add(txtIterations);
		add(Label4);
		add(txtPercentage);
		add(Command1);
	}
	
	private void tree(int x1, int y1, float angle, float length, int i) {
		int X2 = (int)(x1 + (length * Math.cos(angle * (3.14159265358979 / 180))));
		int Y2 = (int)(y1 - (length * Math.sin(angle * (3.14159265358979 / 180))));
		
		int r = 0;
		int g = 0;
		int b = 0;
		if (Check1.isSelected()) {
			Random rand = new Random();
			r = rand.nextInt(256);
			g = rand.nextInt(256);
			b = rand.nextInt(256);
		} else {
			//r = 0
			//g = 0
			//b = 0
		}
		if (Check2.isSelected()) {
			if (i <= Integer.parseInt(txtIterations.getText())) {
				r = 128;
				g = 64;
				b = 50;
			}
			if (i > 0) {
				//frmFractalTree.DrawWidth = (Val(i) / 2) + 1;
			}
			if (i < Integer.parseInt(txtIterations.getText()) / 4) {
				r = 0;
				g = 120;
				b = 0;
			}
			if (Integer.parseInt(txtIterations.getText()) >= 12) {
				if (i == 0) {
					Random rand = new Random();
					int f = rand.nextInt(5);
					if (f == 1) {
						r = 175;
						g = 0;
						b = 255;
					}
				}
			}
		}
		
		graphics.setColor(new Color(r, g, b));
		graphics.drawLine(x1, y1, X2, Y2);
		
		if (Check2.isSelected()) {
			Random rand = new Random();
			int a = rand.nextInt(30);
			if (i > 0) {
				tree(X2, Y2, angle + a, length * Integer.parseInt(txtPercentage.getText()) / 100, i - 1);
				tree(X2, Y2, angle - a, length * Integer.parseInt(txtPercentage.getText()) / 100, i - 1);
			}
		} else {
			if (i > 0) {
				tree(X2, Y2, angle + Integer.parseInt(txtAngle.getText()), length * Integer.parseInt(txtPercentage.getText()) / 100, i - 1);
				tree(X2, Y2, angle - Integer.parseInt(txtAngle.getText()), length * Integer.parseInt(txtPercentage.getText()) / 100, i - 1);
			}
		}
	}
	
	private void Command1_Click() {
		graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		tree(RENDER_WIDTH / 2, 600, 90, Integer.parseInt(txtLength.getText()), Integer.parseInt(txtIterations.getText()));
		getRootPane().repaint();
	}
	
	@Override
	public void mouseClick(int button, int shift, int x, int y) {
		if (button == 1) {
			graphics.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
			
			tree(x, y, 90, Integer.parseInt(txtLength.getText()), Integer.parseInt(txtIterations.getText()));
			getRootPane().repaint();
		}
		if (button == 2) {
			System.exit(0);
		}
	}
}
