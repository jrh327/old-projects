package net.jonhopkins.robot;

import java.awt.Graphics;
import java.util.Random;

public class Box {
	private class point_c {
		double x;
		double y;
		double z;
	}
	
	private class line {
		int p1;
		int p2;
	}
	
	private point_c[] vertex = new point_c[8];
	private point_c[] joint = new point_c[4];
	private line[] lines = new line[12];
	
	private int vertex_count;
	private int line_count;
	
	public double rotate_factorY;
	private double rotate_factorZ;
	private double rotate_factorZ2;
	private double rotate_factorY3;
	
	private double camera_x;
	private double camera_y;
	
	public double middle_box_x;
	public double middle_box_y;
	public double middle_box_z;
	
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
	
	public void initialize() {
		for (int i = 0; i < vertex.length; i++) {
			vertex[i] = new point_c();
		}
		
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new line();
		}
		
		vertex_count = 0;
		line_count = 0;
		
		rotate_factorY = 0;
		rotate_factorZ = 0;
		camera_x = 0;
		camera_y = 0;
		
		Random rnd = new Random();
		double bx = ((int)((rnd.nextDouble() * 30) * 10) / 10) - 15;
		double bz = ((int)((rnd.nextDouble() * 30) * 10) / 10) - 15;
		
		vertex[0].x = bx; vertex[0].y = -1.4; vertex[0].z = bz;
		vertex[1].x = bx - 1; vertex[1].y = -1.4; vertex[1].z = bz;
		vertex[2].x = bx - 1; vertex[2].y = -1.4; vertex[2].z = bz - 1;
		vertex[3].x = bx; vertex[3].y = -1.4; vertex[3].z = bz - 1;
		
		vertex[4].x = bx; vertex[4].y = -0.4; vertex[4].z = bz;
		vertex[5].x = bx - 1; vertex[5].y = -0.4; vertex[5].z = bz;
		vertex[6].x = bx - 1; vertex[6].y = -0.4; vertex[6].z = bz - 1;
		vertex[7].x = bx; vertex[7].y = -0.4; vertex[7].z = bz - 1;
		
		lines[0].p1 = 0; lines[0].p2 = 1;
		lines[1].p1 = 1; lines[1].p2 = 2;
		lines[2].p1 = 2; lines[2].p2 = 3;
		lines[3].p1 = 3; lines[3].p2 = 0;
		
		lines[4].p1 = 4; lines[4].p2 = 5;
		lines[5].p1 = 5; lines[5].p2 = 6;
		lines[6].p1 = 6; lines[6].p2 = 7;
		lines[7].p1 = 7; lines[7].p2 = 4;
		
		lines[8].p1 = 0; lines[8].p2 = 4;
		lines[9].p1 = 1; lines[9].p2 = 5;
		lines[10].p1 = 2; lines[10].p2 = 6;
		lines[11].p1 = 3; lines[11].p2 = 7;
		
		vertex_count = 8;
		line_count = 12;
		
		rotate_factorY = 0;
		rotate_factorZ = 0;
		rotate_factorZ2 = -90;
		rotate_factorY3 = 0;
		camera_x = 0;
		camera_y = 0;
	}
	
	public void initialize_joints() {
		for (int i = 0; i < joint.length; i++) {
			joint[i] = new point_c();
		}
		
		joint[0].x = 0; joint[0].y = 0; joint[0].z = 0;
		joint[1].x = 0; joint[1].y = 5.4; joint[1].z = 0;
		joint[2].x = 7.8; joint[2].y = 5.4; joint[2].z = 0;
		joint[3].x = 7.8; joint[3].y = -0.3; joint[3].z = 0;
	}
	
	public void givejoints() {
		joint[0].x = joint_x0; joint[0].y = joint_y0; joint[0].z = joint_z0;
		joint[1].x = joint_x1; joint[1].y = joint_y1; joint[1].z = joint_z1;
		joint[2].x = joint_x2; joint[2].y = joint_y2; joint[2].z = joint_z2;
		joint[3].x = joint_x3; joint[3].y = joint_y3; joint[3].z = joint_z3;
		//joint[4].x = joint_x4; joint[4].y = joint_y4; joint[4].z = joint_z4;
	}
	
	public void draw(Graphics pct) {
		int pixelx = width / 30;
		int pixely = height / 30;
		
		int originx = width / 2;
		int originy = height / 8 * 5;
		
		for (int i = 0; i < line_count; i++) {
			int plotx = (int)(originx + (vertex[lines[i].p1].x * (50 / (50 + vertex[lines[i].p1].z))) * pixelx); // * 0.8
			int ploty = (int)(originy - (vertex[lines[i].p1].y * (50 / (50 + vertex[lines[i].p1].z))) * pixely); // * 0.8
			int plotx2 = (int)(originx + (vertex[lines[i].p2].x * (50 / (50 + vertex[lines[i].p2].z))) * pixelx); // * 0.8
			int ploty2 = (int)(originy - (vertex[lines[i].p2].y * (50 / (50 + vertex[lines[i].p2].z))) * pixely); // * 0.8
			
			pct.drawLine(plotx, ploty, plotx2, ploty2); //, RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255)) ', RGB(lines[i].r, lines[i].g, lines[i].b)
		}
		
		middle_box_x = (vertex[0].x + vertex[1].x + vertex[2].x + vertex[3].x + vertex[4].x + vertex[5].x + vertex[6].x + vertex[7].x) / 8;
		middle_box_y = (vertex[0].y + vertex[1].y + vertex[2].y + vertex[3].y + vertex[4].y + vertex[5].y + vertex[6].y + vertex[7].y) / 8;
		middle_box_z = (vertex[0].z + vertex[1].z + vertex[2].z + vertex[3].z + vertex[4].z + vertex[5].z + vertex[6].z + vertex[7].z) / 8;
		
		//frmRobot.Label16 = Format(middle_box_x, "##.##")
		//frmRobot.Label17 = Format(middle_box_y, "##.##")
		//frmRobot.Label18 = Format(middle_box_z, "##.##")
	}
	
	public void rotate_with_arm1(Graphics pct, double theta) {
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x;
			double tempz = vertex[i].z;
			
			vertex[i].z = tempz * Math.cos(radians(theta)) - tempx * Math.sin(radians(theta));
			vertex[i].x = tempz * Math.sin(radians(theta)) + tempx * Math.cos(radians(theta));
		}
		
		for (int i = 0; i < joint.length; i++) {
			double tempx = joint[i].x;
			double tempz = joint[i].z;
			
			joint[i].z = tempz * Math.cos(radians(theta)) - tempx * Math.sin(radians(theta));
			joint[i].x = tempz * Math.sin(radians(theta)) + tempx * Math.cos(radians(theta));
		}
	}
	
	public void rotate_with_arm2(Graphics pct, double theta) {
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x - joint[0].x;
			double tempy = vertex[i].y - joint[0].y;
			
			vertex[i].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[0].x;
			vertex[i].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[0].y;
		}
		
		for (int i = 1; i < joint.length; i++) {
			double tempx = joint[i].x - joint[0].x;
			double tempy = joint[i].y - joint[0].y;
			
			joint[i].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[0].x;
			joint[i].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[0].y;
		}
		
		rotate_factorZ = theta;
	}
	
	public void rotate_with_arm3(Graphics pct, double theta) {
		for (int i = 0; i < vertex_count; i++) {
			double tempx = vertex[i].x - joint[1].x;
			double tempy = vertex[i].y - joint[1].y;
			
			vertex[i].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[1].x;
			vertex[i].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[1].y;
		}
		
		double tempx = joint[2].x - joint[1].x;
		double tempy = joint[2].y - joint[1].y;
		
		joint[2].x = tempx * Math.cos(radians(theta)) - tempy * Math.sin(radians(theta)) + joint[1].x;
		joint[2].y = tempx * Math.sin(radians(theta)) + tempy * Math.cos(radians(theta)) + joint[1].y;
		
		rotate_factorZ2 = theta;
	}
	
	public void rotate_with_claw(Graphics pct, double theta) {
		for (int i = 0; i < vertex_count; i++) {
			double tempy = vertex[i].y - joint[2].y;
			double tempz = vertex[i].z - joint[2].z;
			
			vertex[i].y = tempy * Math.cos(radians(theta)) - tempz * Math.sin(radians(theta)) + joint[2].y;
			vertex[i].z = tempy * Math.sin(radians(theta)) + tempz * Math.cos(radians(theta)) + joint[2].z;
		}
		
		rotate_factorY3 = theta;
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
	
	public void drop() {
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
		
		// if the box is above the ground, move its location to the ground
		if (vertex[1].y > -1.4) vertex[1].y = -1.4;
		
		double  bx = vertex[1].x;
		double bz = vertex[1].z;
		
		vertex[0].x = bx; vertex[0].y = -1.4; vertex[0].z = bz;
		vertex[1].x = bx - 1; vertex[1].y = -1.4; vertex[1].z = bz;
		vertex[2].x = bx - 1; vertex[2].y = -1.4; vertex[2].z = bz - 1;
		vertex[3].x = bx; vertex[3].y = -1.4; vertex[3].z = bz - 1;
		
		vertex[4].x = bx; vertex[4].y = -0.4; vertex[4].z = bz;
		vertex[5].x = bx - 1; vertex[5].y = -0.4; vertex[5].z = bz;
		vertex[6].x = bx - 1; vertex[6].y = -0.4; vertex[6].z = bz - 1;
		vertex[7].x = bx; vertex[7].y = -0.4; vertex[7].z = bz - 1;
		
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
	
	public void super_rotate(Graphics pct, int arm_num, double ry, double rz, double rz2, double ry3, double openclaw) {
		if (arm_num == 1) rotate_factorY = rotate_factorY + ry;
		if (arm_num == 2) rotate_factorZ = rotate_factorZ + rz;
		if (arm_num == 3) rotate_factorZ2 = rotate_factorZ2 + rz2;
		if (arm_num == 4) rotate_factorY3 = rotate_factorY3 + ry3;
		
		rotate_with_claw(pct, rotate_factorY3);
		rotate_with_arm3(pct, rotate_factorZ2);
		rotate_with_arm2(pct, rotate_factorZ);
		rotate_with_arm1(pct, rotate_factorY);
		
		// redo translation
		for (int i = 0; i < vertex_count; i++) {
			vertex[i].x = vertex[i].x + joint[0].x;
			vertex[i].z = vertex[i].z + joint[0].z;
			if (i < joint.length) {
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
