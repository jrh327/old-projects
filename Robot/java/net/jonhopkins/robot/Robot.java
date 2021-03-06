package net.jonhopkins.robot;

import java.awt.Graphics;

public class Robot {
	private class point_c {
		double x;
		double y;
		double z;
	}
	
	private class line {
		int p1;
		int p2;
	}
	
	private point_c[] vertex = new point_c[116];
	private line[] lines = new line[153];
	private point_c[] joint = new point_c[4];
	
	private int vertex_count;
	private int line_count;
	
	private double rotate_factorX;
	public double rotate_factorY;
	private double rotate_factorZ;
	private double rotate_factorZ2;
	private double rotate_factorY3;
	
	private double camera_x;
	private double camera_y;
	
	public double middle_claw_x;
	public double middle_claw_y;
	public double middle_claw_z;
	
	public double joint_x0;
	public double joint_y0;
	public double joint_z0;
	public double joint_x1;
	public double joint_y1;
	public double joint_z1;
	public double joint_x2;
	public double joint_y2;
	public double joint_z2;
	public double joint_x3;
	public double joint_y3;
	public double joint_z3;
	
	private int width = 800;
	private int height = 800;
	
	public Robot() {
		initialize();
	}
	
	public void initialize() {
		vertex_count = 0;
		line_count = 0;
		
		rotate_factorX = 0;
		rotate_factorY = 0;
		rotate_factorZ = 0;
		rotate_factorZ2 = 0;
		rotate_factorY3 = 0;
		camera_x = 0;
		camera_y = 0;
		
		for (int i = 0; i < vertex.length; i++) {
			vertex[i] = new point_c();
		}
		
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new line();
		}
		
		for (int i = 0; i < joint.length; i++) {
			joint[i] = new point_c();
		}
	}
	
	public void base() {
		// 1-16
		for (int i = 0; i < 16; i++) {
			vertex[vertex_count + i].x = 4.0 * Math.cos(radians(45 * i));
			vertex[vertex_count + i].z = 4.0 * Math.sin(radians(45 * i));
			if (i < 8) {
				vertex[vertex_count + i].y = 0;
			} else { 
				vertex[vertex_count + i].y = -1.4;
			}
		}
		
		for (int i = 0; i < 16; i++) {
			lines[line_count + i].p1 = vertex_count + i;
			lines[line_count + i].p2 = vertex_count + i + 1;
			if (i == 7) {
				lines[line_count + i].p1 = vertex_count + i;
				lines[line_count + i].p2 = vertex_count;
			} else if (i == 15) {
				lines[line_count + i].p1 = vertex_count + i;
				lines[line_count + i].p2 = vertex_count + 8;
			}
		}
		
		for (int i = 16; i < 24; i++) {
			lines[line_count + i].p1 = vertex_count - 16 + i;
			lines[line_count + i].p2 = vertex_count - 16 + i + 8;
		}
		
		vertex_count = vertex_count + 16;
		line_count = line_count + 24;
		
		// 16 vertices, 24 lines
	}
	
	public void arm() {
		// 17-24
		vertex[vertex_count].x = -0.4;
		vertex[vertex_count].z = -0.4;
		vertex[vertex_count].y = 0.0;
		vertex[vertex_count + 1].x = 0.4;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = 0.0;
		vertex[vertex_count + 2].x = 0.4;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = 0.0;
		vertex[vertex_count + 3].x = -0.4;
		vertex[vertex_count + 3].z = 0.4;
		vertex[vertex_count + 3].y = 0.0;
		vertex[vertex_count + 4].x = -0.4;
		vertex[vertex_count + 4].z = -0.4;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = 0.4;
		vertex[vertex_count + 5].z = -0.4;
		vertex[vertex_count + 5].y = 5.0;
		vertex[vertex_count + 6].x = 0.4;
		vertex[vertex_count + 6].z = 0.4;
		vertex[vertex_count + 6].y = 5.0;
		vertex[vertex_count + 7].x = -0.4;
		vertex[vertex_count + 7].z = 0.4;
		vertex[vertex_count + 7].y = 5.0;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 3;
		lines[line_count + 3].p2 = vertex_count;
		lines[line_count + 4].p1 = vertex_count + 4;
		lines[line_count + 4].p2 = vertex_count + 5;
		lines[line_count + 5].p1 = vertex_count + 5;
		lines[line_count + 5].p2 = vertex_count + 6;
		lines[line_count + 6].p1 = vertex_count + 6;
		lines[line_count + 6].p2 = vertex_count + 7;
		lines[line_count + 7].p1 = vertex_count + 7;
		lines[line_count + 7].p2 = vertex_count + 4;
		lines[line_count + 8].p1 = vertex_count;
		lines[line_count + 8].p2 = vertex_count + 4;
		lines[line_count + 9].p1 = vertex_count + 1;
		lines[line_count + 9].p2 = vertex_count + 5;
		lines[line_count + 10].p1 = vertex_count + 2;
		lines[line_count + 10].p2 = vertex_count + 6;
		lines[line_count + 11].p1 = vertex_count + 3;
		lines[line_count + 11].p2 = vertex_count + 7;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 12;
		
		// 25-32
		vertex[vertex_count].x = -0.4;
		vertex[vertex_count].z = -0.4;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = -0.2;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = 5.6;
		vertex[vertex_count + 2].x = 0.2;
		vertex[vertex_count + 2].z = -0.4;
		vertex[vertex_count + 2].y = 5.6;
		vertex[vertex_count + 3].x = 0.4;
		vertex[vertex_count + 3].z = -0.4;
		vertex[vertex_count + 3].y = 5.0;
		vertex[vertex_count + 4].x = -0.4;
		vertex[vertex_count + 4].z = -0.15;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = -0.2;
		vertex[vertex_count + 5].z = -0.15;
		vertex[vertex_count + 5].y = 5.6;
		vertex[vertex_count + 6].x = 0.2;
		vertex[vertex_count + 6].z = -0.15;
		vertex[vertex_count + 6].y = 5.6;
		vertex[vertex_count + 7].x = 0.4;
		vertex[vertex_count + 7].z = -0.15;
		vertex[vertex_count + 7].y = 5.0;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 33-40
		vertex[vertex_count].x = -0.4;
		vertex[vertex_count].z = 0.4;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = -0.2;
		vertex[vertex_count + 1].z = 0.4;
		vertex[vertex_count + 1].y = 5.6;
		vertex[vertex_count + 2].x = 0.2;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = 5.6;
		vertex[vertex_count + 3].x = 0.4;
		vertex[vertex_count + 3].z = 0.4;
		vertex[vertex_count + 3].y = 5.0;
		vertex[vertex_count + 4].x = -0.4;
		vertex[vertex_count + 4].z = 0.15;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = -0.2;
		vertex[vertex_count + 5].z = 0.15;
		vertex[vertex_count + 5].y = 5.6;
		vertex[vertex_count + 6].x = 0.2;
		vertex[vertex_count + 6].z = 0.15;
		vertex[vertex_count + 6].y = 5.6;
		vertex[vertex_count + 7].x = 0.4;
		vertex[vertex_count + 7].z = 0.15;
		vertex[vertex_count + 7].y = 5.0;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 24 vertices, 30 lines
	}
	
	public void arm2() {
		// 41-48
		vertex[vertex_count].x = 0.4;
		vertex[vertex_count].z = -0.4;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = 0.4;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = 5.8;
		vertex[vertex_count + 2].x = 0.4;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = 5.8;
		vertex[vertex_count + 3].x = 0.4;
		vertex[vertex_count + 3].z = 0.4;
		vertex[vertex_count + 3].y = 5.0;
		vertex[vertex_count + 4].x = 7.4;
		vertex[vertex_count + 4].z = -0.4;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = 7.4;
		vertex[vertex_count + 5].z = -0.4;
		vertex[vertex_count + 5].y = 5.8;
		vertex[vertex_count + 6].x = 7.4;
		vertex[vertex_count + 6].z = 0.4;
		vertex[vertex_count + 6].y = 5.8;
		vertex[vertex_count + 7].x = 7.4;
		vertex[vertex_count + 7].z = 0.4;
		vertex[vertex_count + 7].y = 5.0;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 3;
		lines[line_count + 3].p2 = vertex_count;
		lines[line_count + 4].p1 = vertex_count + 4;
		lines[line_count + 4].p2 = vertex_count + 5;
		lines[line_count + 5].p1 = vertex_count + 5;
		lines[line_count + 5].p2 = vertex_count + 6;
		lines[line_count + 6].p1 = vertex_count + 6;
		lines[line_count + 6].p2 = vertex_count + 7;
		lines[line_count + 7].p1 = vertex_count + 7;
		lines[line_count + 7].p2 = vertex_count + 4;
		lines[line_count + 8].p1 = vertex_count;
		lines[line_count + 8].p2 = vertex_count + 4;
		lines[line_count + 9].p1 = vertex_count + 1;
		lines[line_count + 9].p2 = vertex_count + 5;
		lines[line_count + 10].p1 = vertex_count + 2;
		lines[line_count + 10].p2 = vertex_count + 6;
		lines[line_count + 11].p1 = vertex_count + 3;
		lines[line_count + 11].p2 = vertex_count + 7;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 12;
		
		// 49-56
		vertex[vertex_count].x = 0.4;
		vertex[vertex_count].z = -0.15;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = -0.2;
		vertex[vertex_count + 1].z = -0.15;
		vertex[vertex_count + 1].y = 5.2;
		vertex[vertex_count + 2].x = -0.2;
		vertex[vertex_count + 2].z = -0.15;
		vertex[vertex_count + 2].y = 5.6;
		vertex[vertex_count + 3].x = 0.4;
		vertex[vertex_count + 3].z = -0.15;
		vertex[vertex_count + 3].y = 5.8;
		vertex[vertex_count + 4].x = 0.4;
		vertex[vertex_count + 4].z = 0.15;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = -0.2;
		vertex[vertex_count + 5].z = 0.15;
		vertex[vertex_count + 5].y = 5.2;
		vertex[vertex_count + 6].x = -0.2;
		vertex[vertex_count + 6].z = 0.15;
		vertex[vertex_count + 6].y = 5.6;
		vertex[vertex_count + 7].x = 0.4;
		vertex[vertex_count + 7].z = 0.15;
		vertex[vertex_count + 7].y = 5.8;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 57-64
		vertex[vertex_count].x = 7.4;
		vertex[vertex_count].z = -0.4;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = 8.0;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = 5.2;
		vertex[vertex_count + 2].x = 8.0;
		vertex[vertex_count + 2].z = -0.4;
		vertex[vertex_count + 2].y = 5.6;
		vertex[vertex_count + 3].x = 7.4;
		vertex[vertex_count + 3].z = -0.4;
		vertex[vertex_count + 3].y = 5.8;
		vertex[vertex_count + 4].x = 7.4;
		vertex[vertex_count + 4].z = -0.15;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = 8.0;
		vertex[vertex_count + 5].z = -0.15;
		vertex[vertex_count + 5].y = 5.2;
		vertex[vertex_count + 6].x = 8.0;
		vertex[vertex_count + 6].z = -0.15;
		vertex[vertex_count + 6].y = 5.6;
		vertex[vertex_count + 7].x = 7.4;
		vertex[vertex_count + 7].z = -0.15;
		vertex[vertex_count + 7].y = 5.8;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 65-72
		vertex[vertex_count].x = 7.4;
		vertex[vertex_count].z = 0.4;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = 8.0;
		vertex[vertex_count + 1].z = 0.4;
		vertex[vertex_count + 1].y = 5.2;
		vertex[vertex_count + 2].x = 8.0;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = 5.6;
		vertex[vertex_count + 3].x = 7.4;
		vertex[vertex_count + 3].z = 0.4;
		vertex[vertex_count + 3].y = 5.8;
		vertex[vertex_count + 4].x = 7.4;
		vertex[vertex_count + 4].z = 0.15;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = 8.0;
		vertex[vertex_count + 5].z = 0.15;
		vertex[vertex_count + 5].y = 5.2;
		vertex[vertex_count + 6].x = 8.0;
		vertex[vertex_count + 6].z = 0.15;
		vertex[vertex_count + 6].y = 5.6;
		vertex[vertex_count + 7].x = 7.4;
		vertex[vertex_count + 7].z = 0.15;
		vertex[vertex_count + 7].y = 5.8;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 32 vertices, 39 lines
	}
	
	public void arm3() {
		// 73-80
		vertex[vertex_count].x = 7.4;
		vertex[vertex_count].z = -0.4;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = 7.4;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = 0.2;
		vertex[vertex_count + 2].x = 7.4;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = 0.2;
		vertex[vertex_count + 3].x = 7.4;
		vertex[vertex_count + 3].z = 0.4;
		vertex[vertex_count + 3].y = 5.0;
		vertex[vertex_count + 4].x = 8.2;
		vertex[vertex_count + 4].z = -0.4;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = 8.2;
		vertex[vertex_count + 5].z = -0.4;
		vertex[vertex_count + 5].y = 0.2;
		vertex[vertex_count + 6].x = 8.2;
		vertex[vertex_count + 6].z = 0.4;
		vertex[vertex_count + 6].y = 0.2;
		vertex[vertex_count + 7].x = 8.2;
		vertex[vertex_count + 7].z = 0.4;
		vertex[vertex_count + 7].y = 5.0;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 3;
		lines[line_count + 3].p2 = vertex_count;
		lines[line_count + 4].p1 = vertex_count + 4;
		lines[line_count + 4].p2 = vertex_count + 5;
		lines[line_count + 5].p1 = vertex_count + 5;
		lines[line_count + 5].p2 = vertex_count + 6;
		lines[line_count + 6].p1 = vertex_count + 6;
		lines[line_count + 6].p2 = vertex_count + 7;
		lines[line_count + 7].p1 = vertex_count + 7;
		lines[line_count + 7].p2 = vertex_count + 4;
		lines[line_count + 8].p1 = vertex_count;
		lines[line_count + 8].p2 = vertex_count + 4;
		lines[line_count + 9].p1 = vertex_count + 1;
		lines[line_count + 9].p2 = vertex_count + 5;
		lines[line_count + 10].p1 = vertex_count + 2;
		lines[line_count + 10].p2 = vertex_count + 6;
		lines[line_count + 11].p1 = vertex_count + 3;
		lines[line_count + 11].p2 = vertex_count + 7;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 12;
		
		// 81-88
		vertex[vertex_count].x = 7.4;
		vertex[vertex_count].z = -0.15;
		vertex[vertex_count].y = 5.0;
		vertex[vertex_count + 1].x = 7.6;
		vertex[vertex_count + 1].z = -0.15;
		vertex[vertex_count + 1].y = 5.6;
		vertex[vertex_count + 2].x = 8.0;
		vertex[vertex_count + 2].z = -0.15;
		vertex[vertex_count + 2].y = 5.6;
		vertex[vertex_count + 3].x = 8.2;
		vertex[vertex_count + 3].z = -0.15;
		vertex[vertex_count + 3].y = 5.0;
		vertex[vertex_count + 4].x = 7.4;
		vertex[vertex_count + 4].z = 0.15;
		vertex[vertex_count + 4].y = 5.0;
		vertex[vertex_count + 5].x = 7.6;
		vertex[vertex_count + 5].z = 0.15;
		vertex[vertex_count + 5].y = 5.6;
		vertex[vertex_count + 6].x = 8.0;
		vertex[vertex_count + 6].z = 0.15;
		vertex[vertex_count + 6].y = 5.6;
		vertex[vertex_count + 7].x = 8.2;
		vertex[vertex_count + 7].z = 0.15;
		vertex[vertex_count + 7].y = 5.0;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 89-96
		vertex[vertex_count].x = 7.4;
		vertex[vertex_count].z = -0.15;
		vertex[vertex_count].y = 0.2;
		vertex[vertex_count + 1].x = 7.6;
		vertex[vertex_count + 1].z = -0.15;
		vertex[vertex_count + 1].y = -0.1;
		vertex[vertex_count + 2].x = 8.0;
		vertex[vertex_count + 2].z = -0.15;
		vertex[vertex_count + 2].y = -0.1;
		vertex[vertex_count + 3].x = 8.2;
		vertex[vertex_count + 3].z = -0.15;
		vertex[vertex_count + 3].y = 0.2;
		vertex[vertex_count + 4].x = 7.4;
		vertex[vertex_count + 4].z = 0.15;
		vertex[vertex_count + 4].y = 0.2;
		vertex[vertex_count + 5].x = 7.6;
		vertex[vertex_count + 5].z = 0.15;
		vertex[vertex_count + 5].y = -0.1;
		vertex[vertex_count + 6].x = 8.0;
		vertex[vertex_count + 6].z = 0.15;
		vertex[vertex_count + 6].y = -0.1;
		vertex[vertex_count + 7].x = 8.2;
		vertex[vertex_count + 7].z = 0.15;
		vertex[vertex_count + 7].y = 0.2;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 4;
		lines[line_count + 3].p2 = vertex_count + 5;
		lines[line_count + 4].p1 = vertex_count + 5;
		lines[line_count + 4].p2 = vertex_count + 6;
		lines[line_count + 5].p1 = vertex_count + 6;
		lines[line_count + 5].p2 = vertex_count + 7;
		lines[line_count + 6].p1 = vertex_count + 7;
		lines[line_count + 6].p2 = vertex_count + 4;
		lines[line_count + 7].p1 = vertex_count + 1;
		lines[line_count + 7].p2 = vertex_count + 5;
		lines[line_count + 8].p1 = vertex_count + 2;
		lines[line_count + 8].p2 = vertex_count + 6;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 9;
		
		// 24 vertices, 30 lines
	}
	
	public void thingy_with_the_grabby() {
		// 97-104
		vertex[vertex_count].x = 6.9;
		vertex[vertex_count].z = -0.4;
		vertex[vertex_count].y = -0.1;
		vertex[vertex_count + 1].x = 6.9;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = -0.3;
		vertex[vertex_count + 2].x = 6.9;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = -0.3;
		vertex[vertex_count + 3].x = 6.9;
		vertex[vertex_count + 3].z = 0.4;
		vertex[vertex_count + 3].y = -0.1;
		vertex[vertex_count + 4].x = 8.7;
		vertex[vertex_count + 4].z = -0.4;
		vertex[vertex_count + 4].y = -0.1;
		vertex[vertex_count + 5].x = 8.7;
		vertex[vertex_count + 5].z = -0.4;
		vertex[vertex_count + 5].y = -0.3;
		vertex[vertex_count + 6].x = 8.7;
		vertex[vertex_count + 6].z = 0.4;
		vertex[vertex_count + 6].y = -0.3;
		vertex[vertex_count + 7].x = 8.7;
		vertex[vertex_count + 7].z = 0.4;
		vertex[vertex_count + 7].y = -0.1;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 2;
		lines[line_count + 2].p1 = vertex_count + 2;
		lines[line_count + 2].p2 = vertex_count + 3;
		lines[line_count + 3].p1 = vertex_count + 3;
		lines[line_count + 3].p2 = vertex_count;
		lines[line_count + 4].p1 = vertex_count + 4;
		lines[line_count + 4].p2 = vertex_count + 5;
		lines[line_count + 5].p1 = vertex_count + 5;
		lines[line_count + 5].p2 = vertex_count + 6;
		lines[line_count + 6].p1 = vertex_count + 6;
		lines[line_count + 6].p2 = vertex_count + 7;
		lines[line_count + 7].p1 = vertex_count + 7;
		lines[line_count + 7].p2 = vertex_count + 4;
		lines[line_count + 8].p1 = vertex_count;
		lines[line_count + 8].p2 = vertex_count + 4;
		lines[line_count + 9].p1 = vertex_count + 1;
		lines[line_count + 9].p2 = vertex_count + 5;
		lines[line_count + 10].p1 = vertex_count + 2;
		lines[line_count + 10].p2 = vertex_count + 6;
		lines[line_count + 11].p1 = vertex_count + 3;
		lines[line_count + 11].p2 = vertex_count + 7;
		
		vertex_count = vertex_count + 8;
		line_count = line_count + 12;
		
		// 105-110
		vertex[vertex_count].x = 7.4;
		vertex[vertex_count].z = 0.4;
		vertex[vertex_count].y = -0.3;
		vertex[vertex_count + 1].x = 7.4;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = -0.3;
		vertex[vertex_count + 2].x = 7.8;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = -0.3;
		vertex[vertex_count + 3].x = 7.8;
		vertex[vertex_count + 3].z = -0.4;
		vertex[vertex_count + 3].y = -0.3;
		vertex[vertex_count + 4].x = 7.8;
		vertex[vertex_count + 4].z = 0.4;
		vertex[vertex_count + 4].y = -1.4;
		vertex[vertex_count + 5].x = 7.8;
		vertex[vertex_count + 5].z = -0.4;
		vertex[vertex_count + 5].y = -1.4;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 3;
		lines[line_count + 2].p1 = vertex_count + 3;
		lines[line_count + 2].p2 = vertex_count + 2;
		lines[line_count + 3].p1 = vertex_count + 2;
		lines[line_count + 3].p2 = vertex_count;
		lines[line_count + 4].p1 = vertex_count + 4;
		lines[line_count + 4].p2 = vertex_count;
		lines[line_count + 5].p1 = vertex_count + 4;
		lines[line_count + 5].p2 = vertex_count + 2;
		lines[line_count + 6].p1 = vertex_count + 5;
		lines[line_count + 6].p2 = vertex_count + 3;
		lines[line_count + 7].p1 = vertex_count + 5;
		lines[line_count + 7].p2 = vertex_count + 1;
		lines[line_count + 8].p1 = vertex_count + 5;
		lines[line_count + 8].p2 = vertex_count + 4;
		
		vertex_count = vertex_count + 6;
		line_count = line_count + 9;
		
		// 111-116
		vertex[vertex_count].x = 8.2;
		vertex[vertex_count].z = 0.4;
		vertex[vertex_count].y = -0.3;
		vertex[vertex_count + 1].x = 8.2;
		vertex[vertex_count + 1].z = -0.4;
		vertex[vertex_count + 1].y = -0.3;
		vertex[vertex_count + 2].x = 7.8;
		vertex[vertex_count + 2].z = 0.4;
		vertex[vertex_count + 2].y = -0.3;
		vertex[vertex_count + 3].x = 7.8;
		vertex[vertex_count + 3].z = -0.4;
		vertex[vertex_count + 3].y = -0.3;
		vertex[vertex_count + 4].x = 7.8;
		vertex[vertex_count + 4].z = 0.4;
		vertex[vertex_count + 4].y = -1.4;
		vertex[vertex_count + 5].x = 7.8;
		vertex[vertex_count + 5].z = -0.4;
		vertex[vertex_count + 5].y = -1.4;
		
		lines[line_count].p1 = vertex_count;
		lines[line_count].p2 = vertex_count + 1;
		lines[line_count + 1].p1 = vertex_count + 1;
		lines[line_count + 1].p2 = vertex_count + 3;
		lines[line_count + 2].p1 = vertex_count + 3;
		lines[line_count + 2].p2 = vertex_count + 2;
		lines[line_count + 3].p1 = vertex_count + 2;
		lines[line_count + 3].p2 = vertex_count;
		lines[line_count + 4].p1 = vertex_count + 4;
		lines[line_count + 4].p2 = vertex_count;
		lines[line_count + 5].p1 = vertex_count + 4;
		lines[line_count + 5].p2 = vertex_count + 2;
		lines[line_count + 6].p1 = vertex_count + 5;
		lines[line_count + 6].p2 = vertex_count + 3;
		lines[line_count + 7].p1 = vertex_count + 5;
		lines[line_count + 7].p2 = vertex_count + 1;
		lines[line_count + 8].p1 = vertex_count + 5;
		lines[line_count + 8].p2 = vertex_count + 4;
		
		vertex_count = vertex_count + 6;
		line_count = line_count + 9;
		
		// 20 vertices, 30 lines
	}
	
	public void initialize_joints() {
		joint[0].x = 0.0;
		joint[0].y = 0.0;
		joint[0].z = 0.0;
		joint[1].x = 0.0;
		joint[1].y = 5.4;
		joint[1].z = 0.0;
		joint[2].x = 7.8;
		joint[2].y = 5.4;
		joint[2].z = 0.0;
		joint[3].x = 7.8;
		joint[3].y = -0.3;
		joint[3].z = 0.0;
	}
	
	public void rotate_arm1(Graphics pct, double theta) {
		for (int i = 16; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(theta)) - tempx * Math.sin(radians(theta));
			vertex[i].x = tempz * Math.sin(radians(theta)) + tempx * Math.cos(radians(theta));
		}
		
		for (int i = 1; i < joint.length; i++) {
			double tempx = joint[i].x;
			double tempz = joint[i].z;
			
			joint[i].z = tempz * Math.cos(radians(theta)) - tempx * Math.sin(radians(theta));
			joint[i].x = tempz * Math.sin(radians(theta)) + tempx * Math.cos(radians(theta));
		}
		
		rotate_factorY = theta;
	}
	
	public void rotate_arm2(Graphics pct, double theta) {
		for (int i = 40; i < vertex_count; i++) {
			double tempx = vertex[i].x - joint[1].x;
			double tempy = vertex[i].y - joint[1].y;
			
			vertex[i].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[1].x;
			vertex[i].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[1].y;
		}
		
		for (int i = 1; i < joint.length; i++) {
			double tempx = joint[i].x - joint[1].x;
			double tempy = joint[i].y - joint[1].y;
			
			joint[i].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[1].x;
			joint[i].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[1].y;
		}
		
		rotate_factorZ = theta;
	}
	
	public void rotate_arm3(Graphics pct, double theta) {
		for (int i = 72; i < vertex_count; i++) {
			double tempx = vertex[i].x - joint[2].x;
			double tempy = vertex[i].y - joint[2].y;
			
			vertex[i].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[2].x;
			vertex[i].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[2].y;
		}
		
		double tempx = joint[3].x - joint[2].x;
		double tempy = joint[3].y - joint[2].y;
		
		joint[3].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[2].x;
		joint[3].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[2].y;
		
		rotate_factorZ2 = theta;
	}
	
	public void rotate_claw(Graphics pct, double theta) {
		for (int i = 96; i < vertex_count; i++) {
			double tempy = vertex[i].y - joint[3].y;
			double tempz = vertex[i].z - joint[3].z;
			
			vertex[i].y = tempy * Math.cos(radians(theta)) - tempz * Math.sin(radians(theta)) + joint[3].y;
			vertex[i].z = tempy * Math.sin(radians(theta)) + tempz * Math.cos(radians(theta)) + joint[3].z;
		}
		
		rotate_factorY3 = theta;
	}
	
	public void rawr(Graphics pct, double openwide) {
		for (int i = 104; i < 110; i++) {
			vertex[i].y = vertex[i].y - openwide;
		}
		
		for (int i = 110; i < 116; i++) {
			vertex[i].y = vertex[i].y + openwide;
		}
	}
	
	public void super_rotate(Graphics pct, int arm_num, double ry, double rz, double rz2, double ry3, double openclaw) {
		if (arm_num == 1) rotate_factorY = ry;
		if (arm_num == 2) rotate_factorZ = rz;
		if (arm_num == 3) rotate_factorZ2 = rz2;
		if (arm_num == 4) rotate_factorY3 = ry3;
		if (arm_num == 5) rawr(pct, openclaw);
		
		// initialize
		vertex_count = 0;
		line_count = 0;
		
		double tempTranslateX = joint[0].x;
		double tempTranslateZ = joint[0].z;
		
		base();
		arm();
		arm2();
		arm3();
		thingy_with_the_grabby();
		initialize_joints();
		
		rotate_claw(pct, rotate_factorY3);
		rotate_arm3(pct, rotate_factorZ2);
		rotate_arm2(pct, rotate_factorZ);
		rotate_arm1(pct, rotate_factorY);
		
		// redo translation
		joint[0].x = tempTranslateX;
		joint[0].z = tempTranslateZ;
		for (int i = 0; i < vertex_count; i++) {
			vertex[i].x = vertex[i].x + joint[0].x;
			vertex[i].z = vertex[i].z + joint[0].z;
			if (i > 0 && i < joint.length) {
				joint[i].x = joint[i].x + joint[0].x;
				joint[i].z = joint[i].z + joint[0].z;
			}
		}
		
		// redo camera rotation
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(camera_y)) - tempx * Math.sin(radians(camera_y));
			vertex[i].x = tempz * Math.sin(radians(camera_y)) + tempx * Math.cos(radians(camera_y));
		}
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y;
			double tempz = vertex[i].z;
			
			vertex[i].y = tempy * Math.cos(radians(camera_x)) - tempz * Math.sin(radians(camera_x));
			vertex[i].z = tempy * Math.sin(radians(camera_x)) + tempz * Math.cos(radians(camera_x));
		}
		
		draw(pct);
	}
	
	public void camera_rotate(Graphics pct, int x_theta, int y_theta) {
		//__________________________________
		//*******Rotate about x-axis********
		//----------------------------------
		
		// x-rotation
		if (x_theta != 0) {
			for (int i = 0; i < vertex_count; i++) {
				double tempy = vertex[i].y;
				double tempz = vertex[i].z;
				
				vertex[i].y = tempy * Math.cos(radians(x_theta - camera_x)) - tempz * Math.sin(radians(x_theta - camera_x));
				vertex[i].z = tempy * Math.sin(radians(x_theta - camera_x)) + tempz * Math.cos(radians(x_theta - camera_x));
			}
			
			// **Note** undoing the y-rotation is unnecessary because the camera rotates up and down relative
			// to the x-axis that goes across the screen, not the one relative to the robot. moving the camera
			// relative to the robot's x-axis causes unwanted rotations that actually make the camera go under
			// the ground, which is both unrealistic and annoying
			
			camera_x = x_theta;
		}
		
		//___________________________________
		//********Rotate about y-axis********
		//-----------------------------------
		
		if (y_theta != 0) {
			// undo x-rotation
			for (int i = 0; i < vertex_count; i++) {
				double tempy = vertex[i].y;
				double tempz = vertex[i].z;
				
				vertex[i].y = tempy * Math.cos(radians(-camera_x)) - tempz * Math.sin(radians(-camera_x));
				vertex[i].z = tempy * Math.sin(radians(-camera_x)) + tempz * Math.cos(radians(-camera_x));
			}
			
			// y-rotation
			for (int i = 0; i < vertex_count; i++) {
				double tempx = vertex[i].x;
				double tempz = vertex[i].z;
				
				vertex[i].z = tempz * Math.cos(radians(y_theta - camera_y)) - tempx * Math.sin(radians(y_theta - camera_y));
				vertex[i].x = tempz * Math.sin(radians(y_theta - camera_y)) + tempx * Math.cos(radians(y_theta - camera_y));
			}
			
			// redo x-rotation
			for (int i = 0; i < vertex_count; i++) {
				double tempy = vertex[i].y;
				double tempz = vertex[i].z;
				
				vertex[i].y = tempy * Math.cos(radians(camera_x)) - tempz * Math.sin(radians(camera_x));
				vertex[i].z = tempy * Math.sin(radians(camera_x)) + tempz * Math.cos(radians(camera_x));
			}
			
			// undoing the x-rotation here is necessary because this rotates the camera relative to the y-axis
			// that goes up the screen, not the robot's y-axis. without undoing the x-rotation, the whole robot
			// is rotated around an axis that would make the camera go under it and the ground. by undoing the
			// x-rotation so that the robot is standing straight up in its original position and then rotating it
			// around the y-axis, it is possible to create the illusion that the camera itself is moving around it
			
			camera_y = y_theta;
		}
		
		draw(pct);
	}
	
	public void translate_x(Graphics pct, int x_change) {
		// undo camera rotation
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y;
			double tempz = vertex[i].z;
			
			vertex[i].y = tempy * Math.cos(radians(-camera_x)) - tempz * Math.sin(radians(-camera_x));
			vertex[i].z = tempy * Math.sin(radians(-camera_x)) + tempz * Math.cos(radians(-camera_x));
		}
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(-camera_y)) - tempx * Math.sin(radians(-camera_y));
			vertex[i].x = tempz * Math.sin(radians(-camera_y)) + tempx * Math.cos(radians(-camera_y));
		}
		
		// translate
		for (int i = 0; i < vertex_count; i++) {
			vertex[i].x = vertex[i].x + x_change;
			if (i < joint.length) {
				joint[i].x = joint[i].x + x_change;
			}
		}
		
		// redo camera rotation
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(camera_y)) - tempx * Math.sin(radians(camera_y));
			vertex[i].x = tempz * Math.sin(radians(camera_y)) + tempx * Math.cos(radians(camera_y));
		}
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y;
			double tempz = vertex[i].z;
			
			vertex[i].y = tempy * Math.cos(radians(camera_x)) - tempz * Math.sin(radians(camera_x));
			vertex[i].z = tempy * Math.sin(radians(camera_x)) + tempz * Math.cos(radians(camera_x));
		}
		
		draw(pct);
	}
	
	public void translate_z(Graphics pct, int z_change) {
		// undo camera rotation
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y;
			double tempz = vertex[i].z;
			
			vertex[i].y = tempy * Math.cos(radians(-camera_x)) - tempz * Math.sin(radians(-camera_x));
			vertex[i].z = tempy * Math.sin(radians(-camera_x)) + tempz * Math.cos(radians(-camera_x));
		}
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(-camera_y)) - tempx * Math.sin(radians(-camera_y));
			vertex[i].x = tempz * Math.sin(radians(-camera_y)) + tempx * Math.cos(radians(-camera_y));
		}
		
		// translate
		for (int i = 0; i < vertex_count; i++) {
			vertex[i].z = vertex[i].z + z_change;
			if (i < joint.length) {
				joint[i].z = joint[i].z + z_change;
			}
		}
		
		// redo camera rotation
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(camera_y)) - tempx * Math.sin(radians(camera_y));
			vertex[i].x = tempz * Math.sin(radians(camera_y)) + tempx * Math.cos(radians(camera_y));
		}
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y;
			double tempz = vertex[i].z;
			
			vertex[i].y = tempy * Math.cos(radians(camera_x)) - tempz * Math.sin(radians(camera_x));
			vertex[i].z = tempy * Math.sin(radians(camera_x)) + tempz * Math.cos(radians(camera_x));
		}
		
		draw(pct);
	}
	
	public void draw(Graphics pct) {
		int pixelx = width / 30;
		int pixely = height / 30;
		
		int originx = width / 2;
		int originy = height / 8 * 5;
		
		for (int i = 0; i < line_count; i++) {
			int plotx = (int)(originx + (vertex[lines[i].p1].x * (50 / (50 + vertex[lines[i].p1].z))) * pixelx);
			int ploty = (int)(originy - (vertex[lines[i].p1].y * (50 / (50 + vertex[lines[i].p1].z))) * pixely);
			int plotx2 = (int)(originx + (vertex[lines[i].p2].x * (50 / (50 + vertex[lines[i].p2].z))) * pixelx);
			int ploty2 = (int)(originy - (vertex[lines[i].p2].y * (50 / (50 + vertex[lines[i].p2].z))) * pixely);
			
			pct.drawLine(plotx, ploty, plotx2, ploty2);
		}
		
		joint_x0 = joint[0].x;
		joint_y0 = joint[0].y;
		joint_z0 = joint[0].z;
		joint_x1 = joint[1].x;
		joint_y1 = joint[1].y;
		joint_z1 = joint[1].z;
		joint_x2 = joint[2].x;
		joint_y2 = joint[2].y;
		joint_z2 = joint[2].z;
		joint_x3 = joint[3].x;
		joint_y3 = joint[3].y;
		joint_z3 = joint[3].z;
		
		double clawx = 0.0;
		double clawy = 0.0;
		double clawz = 0.0;
		
		for (int i = 104; i < 116; i++) {
			clawx = clawx + vertex[i].x;
			clawy = clawy + vertex[i].y;
			clawz = clawz + vertex[i].z;
		}
		
		middle_claw_x = clawx / 12;
		middle_claw_y = clawy / 12;
		middle_claw_z = clawz / 12;
		
		//frmRobot.Label1 = Format(middle_claw_x, "##.##");
		//frmRobot.Label2 = Format(middle_claw_y, "##.##");
		//frmRobot.Label3 = Format(middle_claw_z, "##.##");
	}
	
	public void rotateX(Graphics pct, double theta) {
		// y' = y*cos q - z*sin q
		// z' = y*sin q + z*cos q
		// x' = x
		
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y;
			double tempz = vertex[i].z;
			
			vertex[i].y = tempy * Math.cos(radians(theta - rotate_factorX)) - tempz * Math.sin(radians(theta - rotate_factorX));
			vertex[i].z = tempy * Math.sin(radians(theta - rotate_factorX)) + tempz * Math.cos(radians(theta - rotate_factorX));
		}
		
		rotate_factorX = theta;
		
		draw(pct);
	}
	
	public void rotateY(Graphics pct, double theta) {
		// z' = z*cos q - x*sin q
		// x' = z*sin q + x*cos q
		// y' = y
		
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(theta - rotate_factorY)) - tempx * Math.sin(radians(theta - rotate_factorY));
			vertex[i].x = tempz * Math.sin(radians(theta - rotate_factorY)) + tempx * Math.cos(radians(theta - rotate_factorY));
		}
		
		rotate_factorY = theta;
		
		draw(pct);
	}
	
	public void rotateZ(Graphics pct, double theta) {
		// x' = x*cos q - y*sin q
		// y' = x*sin q + y*cos q
		// z' = z
		
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempy = vertex[i].y;
			
			vertex[i].x = tempx * Math.cos(radians(theta - rotate_factorZ)) - tempy * Math.sin(radians(theta - rotate_factorZ));
			vertex[i].y = tempx * Math.sin(radians(theta - rotate_factorZ)) + tempy * Math.cos(radians(theta - rotate_factorZ));
		}
		
		rotate_factorZ = theta;
		
		draw(pct);
	}
	
	public double degrees(double theta) {
		return (theta * (180.0 / 3.1415926538979));
	}
	
	public double radians(double theta) {
		return (theta * (3.1415926538979 / 180.0));
	}
	
	public double ArcSin(double x) {
		return Math.atan(x / Math.sqrt(Math.abs(-x * x + 1)));
	}
	
	public double ArcCos(double x) {
		return Math.atan(-x / Math.sqrt(Math.abs(-x * x + 1))) + 2 * Math.atan(1);
	}
}
