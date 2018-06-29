package net.jonhopkins.fractals;

public class FractalTree {
	private void tree(float x1, float y1, float angle, float length, int i) {
		float X2 = x1 + (length * Math.cos(angle * (3.14159265358979 / 180)));
		float Y2 = y1 - (length * Math.sin(angle * (3.14159265358979 / 180)));
		
		if (Check1.Value == 1) {
			Randomize;
			r = Int(Rnd * 255);
			g = Int(Rnd * 255);
			b = Int(Rnd * 255);
		} else {
			//r = 0
			//g = 0
			//b = 0
		}
		if (Check2.Value == 1) {
			if (i <= Val(txtIterations)) {
				r = 128;
				g = 64;
				b = 50;
			}
			if (i > 0) {
				frmFractalTree.DrawWidth = (Val(i) / 2) + 1;
			}
			if (i < Val(txtIterations) / 4) {
				r = 0;
				g = 120;
				b = 0;
			}
			if (txtIterations >= 12) {
				if (i == 0) {
					f = Int(Rnd * 5);
					if (f == 1) {
						r = 175;
						g = 0;
						b = 255;
					}
				}
			}
		}
		Line((x1, y1)-(X2, Y2), RGB(r, g, b));
		
		if (Check2.Value == 1) {
			Randomize;
			a = Int(Rnd * 30);
			if (i > 0) {
				tree(Val(X2), Val(Y2), Val(angle + a), Val(length * Val(txtPercentage / 100)), Val(i - 1));
				tree(Val(X2), Val(Y2), Val(angle - a), Val(length * Val(txtPercentage / 100)), Val(i - 1));
			}
		} else {
			if (i > 0) {
				tree(Val(X2), Val(Y2), Val(angle + Val(txtAngle)), Val(length * Val(txtPercentage / 100)), Val(i - 1));
				tree(Val(X2), Val(Y2), Val(angle - Val(txtAngle)), Val(length * Val(txtPercentage / 100)), Val(i - 1));
			}
		}
	}
	
	private void Command1_Click() {
		frmFractalTree.Cls;
		tree(600, 600, 90, Val(txtLength), Val(txtIterations));
	}
	
	private void Command2_Click() {
		frmSierpinski.Visible = true;
		frmFractalTree.Visible = false;
	}
	
	private void Command3_click() {
		frmKochSnowflake.Visible = true;
		frmFractalTree.Visible = false;
	}
	
	private void Form_MouseDown(int Button, int Shift, float X, float Y) {
		if (Button == 1) {
			frmFractalTree.Cls;
			
			tree(X, Y, 90, Val(txtLength), Val(txtIterations));
		}
		if (Button == 2) {
			End;
		}
	}
}
