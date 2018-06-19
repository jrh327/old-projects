package net.jonhopkins.robot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Route {
	public class route {
		public int[] move = new int[1000];
	}
	
	route[] routes = new route[10];
	public void Save_Moves(int route_number, int move_number, int move_type) {
		String filename = getPath("RobotRoutes.txt");
		int[] moves = routes[route_number].move;
		moves[move_number] = move_type;
		
		StringBuilder route = new StringBuilder();
		int i = 0;
		while (moves[i] != 0) {
			route.append(moves[i]);
		}
		
		try {
			FileWriter writer = new FileWriter(getPath("RouteList.txt"));
			writer.append(route.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int Get_Moves(int route_number, int move_number) {
		String filename = getPath("RobotRoutes.txt");
		
		int move = 0;
		try {
			byte[] route = Files.readAllBytes(new File(filename).toPath());
			for (int i = 0; i < route.length; i++) {
				routes[route_number].move[i] = route[i];
			}
			move = routes[route_number].move[move_number];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return move;
	}
	
	private String getPath(String filename) {
		return System.getProperty("user.dir") + "/" + filename;
	}
}
