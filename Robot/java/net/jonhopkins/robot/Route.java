package net.jonhopkins.robot;

public class Route {
	public class route {
		public int[] move = new int[1000];
	}
	
	route[] routes = new route[10];
	public void Save_Moves(int route_number, int move_number, int move_type) {
		String filename;
		filename = System.getProperty("user.dir") + "/RobotRoutes.txt";
		
		//Open filename For Random As #1 Len = Len(routes[route_number]) //len(<name of Private Type being saved>) is the number of bytes
		//	routes[route_number].move[move_number] = move_type
		//	Put #1, 1, routes(route_number) //#1 is file number, 1 is record number, s is record variable to be saved to H:Students.DBD
		//Close #1
	}
	public int Get_Moves(int route_number, int move_number) {
		String filename;
		filename = System.getProperty("user.dir") + "/RobotRoutes.txt";
		
		int move = 0;
		//Open filename For Random As #1 Len = Len(routes[route_number]) //len(s) is the number of bytes
		//	Get #1, 1, routes[route_number]   //#1 is file number, 1 is record number, s is record variable to be saved to H:Students.DBD
			move = routes[route_number].move[move_number];
		//Close #1
		return move;
	}
}
