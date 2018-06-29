package net.jonhopkins.fractals;

public class SierpinskiTriangle {
	private class vertex {
		int X;
		int Y;
	}
	
	vertex[] v = new vertex[3];
	
	private vertex midpoint(vertex p1, vertex p2) {
		vertex midpoint = new vertex();
		midpoint.X = (p1.X + p2.X) / 2;
		midpoint.Y = (p1.Y + p2.Y) / 2;
		return midpoint;
	}
	
	private void draw_line(vertex p1, vertex p2) {
		frmSierpinski.Line (p1.X, p1.Y)-(p2.X, p2.Y)
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
		
		for (int i = 0; i < 100000; i++) {
			Randomize;
			
			m = (v[0].Y - v[1].Y) / (v[0].X - v[1].X);
			
			vert[0].Y = Int(Rnd * (v[1].Y - v[0].Y)) + v[0].Y; //(Int(Rnd * 610) + 50)
			
			vert[0].X = Int(Rnd * (vert[0].Y - (v[0].Y) / m));
			
			//vert(1).X = Int(Rnd * (v(3).X - v(2).X)) + v(2).X '(Int(Rnd * 800) + 25)
			
			rndvert = Int(Rnd * 3 + 1);
			
			vert[1] = midpoint(vert[0], v(rndvert));
			
			PSet(vert[0].X, vert[0].Y); //, RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255))
			PSet(vert[1].X, vert[1].Y); //, RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255))
		}
	}
	
	private void cmdDraw_Click() {
		frmSierpinski.Cls;
		
		v[0].X = 425;
		v[0].Y = 50;
		v[1].X = 25;
		v[1].Y = 660;
		v[2].X = 825;
		v[2].Y = 660;
		
		draw_triangle(v[0], v[1], v[2], Val(txtIterations));
	}
	
	private void cmdDrawRandom_Click() {
		frmSierpinski.Cls;
		
		v[0].X = 425; v[0].Y = 50;
		v[1].X = 25; v[1].Y = 660;
		v[2].X = 825; v[2].Y = 660;
		
		draw_triangle_random;
	}
	
	private void Command1_Click() {
		frmFractalTree.Visible = true;
		frmSierpinski.Visible = false;
	}
	
	private void Form_MouseMove(int Button, int Shift, float X, float Y) {
		if (Button == 1) {
			lblX = X;
			lblY = Y;
		}
	}
}
