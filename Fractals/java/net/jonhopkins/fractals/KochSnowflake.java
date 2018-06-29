package net.jonhopkins.fractals;

public class KochSnowflake {
	private class vertex {
		int X;
		int Y;
	}
	
	vertex[] v = new vertex[3];
	
	private void cmdDraw_Click() {
		frmKochSnowflake.Cls;
		
		v[0].X = 150;
		v[0].Y = 550;
		v[1].X = 650;
		v[1].Y = 550;
		v[2].X = 150 + 500 * Math.cos(3.14159265358979 / 3);
		v[2].Y = 550 - 500 * Math.sin(3.14159265358979 / 3);
		
		draw_vertices(v[0], v[1], v[2]);
		
		draw_snowflake(v[0], v[1], v[2], Val(txtIterations));
	}
	
	private void draw_snowflake(vertex v1, vertex v2, vertex v3, int i) {
		if (i > 0) {
			
			// third of way up the side, third of the way from other side
			// connect at midpoint of side a (length of side) / 3 * 3 ^ .5 away
			
			Line((v[0].X + ((v[2].X - v[0].X) / 3), v[0].Y - ((v[0].Y - v[2].Y) / 3))-(((v[0].X + v[2].X) / 2) + ((250 * (3 ^ 0.5) / 2) * Math.cos(150 * (3.14159265358979 / 180))), ((v[0].Y + v[2].Y) / 2) - ((250 * (3 ^ 0.5) / 2) * Math.sin(150 * (3.14159265358979 / 180)))));
			Line((v[0].X + ((v[2].X - v[0].X) * (2 / 3)), v[0].Y - ((v[0].Y - v[2].Y) * (2 / 3)))-(((v[0].X + v[2].X) / 2) + ((250 * (3 ^ 0.5) / 2) * Math.cos(150 * (3.14159265358979 / 180))), ((v[0].Y + v[2].Y) / 2) - ((250 * (3 ^ 0.5) / 2) * Math.sin(150 * (3.14159265358979 / 180)))));
		}
	}
	
	private vertex thirdify(vertex p1, vertex p2) {
		vertex thirdify = new vertex();
		thirdify.X = (p1.X + p2.X) / 3 * 2;
		thirdify.Y = (p1.Y + p2.Y) / 3 * 2;
		return thirdify;
	}
	
	private void draw_line(vertex p1, vertex p2) {
		Line((p1.X, p1.Y)-(p2.X, p2.Y));
	}
	
	private void draw_vertices(vertex v1, vertex v2, vertex v3) {
		draw_line(v1, v2);
		draw_line(v2, v3);
		draw_line(v3, v1);
	}
	
	private void Command1_Click() {
		frmKochSnowflake.Visible = false;
		frmFractalTree.Visible = true;
	}
	
	private void Form_MouseDown(int Button, int Shift, float X, float Y) {
		if (Button == 1) {
			Cls;
			
			v[0].X = X;
			v[0].Y = Y;
			v[1].X = X + 500;
			v[1].Y = Y;
			v[2].X = X + 500 * Math.cos(3.14159265358979 / 3);
			v[2].Y = Y - 500 * Math.sin(3.14159265358979 / 3);
			
			draw_vertices(v[0], v[1], v[2]);
		}
		if (Button == 2) {
			End;
		}
	}
}
