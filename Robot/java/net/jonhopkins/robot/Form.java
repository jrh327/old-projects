package net.jonhopkins.robot;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Form {
	JTextField txtNumberBoxes; // Right Justify, Text = "5"
	JScrollBar hscrRotateX; // Max = 75, SmallChange = 5, LargeChange = 20
	JTextField txtRotateX; // Right Justify, Text = "0"
	JScrollBar hscrRotateY; // Max = 360, SmallChange = 5, LargeChange = 20
	JTextField txtRotateY; // Right Justify, Text = "0"
	JScrollBar hscrXTranslate; // Min = -15, Max = 15
	JScrollBar hscrZTranslate; // Min = -20, Max = 20
	JTextField txtXTranslate; // Right Justify, Text = "0"
	JTextField txtZTranslate; // Right Justify, Text = "0"
	JButton cmdOpenRoute; // Caption = "Open"
	JButton cmdSaveRoute; // Caption = "Save", Enabled = False
	JButton cmdAutoRobot; // Caption = "AutoGo"
	JButton cmdClearCurrentRoute; // Caption = "Clear Route"
	JButton cmdNewRoute; // Caption = "New Route"
	JCheckBox chkMoves; // Caption = "Teach Route"
	JButton cmdResetRobot; // Caption = "Reset"
	Timer tmrResetRobot; // Enabled = False, Interval = 50
	Timer tmrAutoRobot; // Enabled = False, Interval = 100
	JTextField txtOpenClaw; // Right Justify, Text = "0"
	JTextField txtClawRotate; // Right Justify, Text = "90"
	JTextField txtArm3; // Right Justify, Text = "0"
	JTextField txtArm2; // Right Justify, Text = "0"
	JScrollBar hscrClawOpen; // Max = 6
	JScrollBar hscrClawRotate; // Max = 180, SmallChange = 5, LargeChange = 5
	JScrollBar hscrArm3; // Min = -20, Max = 200, SmallChange = 5, LargeChange = 5
	JScrollBar hscrArm2; // Max = 180, SmallChange = 5, LargeChange = 5
	JScrollBar hscrArm1; // Min = -360, Max = 360, SmallChange = 5, LargeChange = 5
	JTextField txtArm1; // Right Justify, Text = "0"
	JButton cmdDrawRobot; // Caption = "Drawz :D"
// Begin VB.PictureBox pctRobot 
//    Height          =   9000
//    Left            =   0
//    ScaleHeight     =   596
//    ScaleMode       =   3  'Pixel
//    ScaleWidth      =   596
//    TabIndex        =   0
//    Top             =   0
//    Width           =   9000
// End
	Graphics pctRobot;
	JLabel Label1; // Right Justify, BorderStyle = Fixed Single
	JLabel Label24; // Right Justify, Caption = "box middle"
	JLabel Label23; // Caption = "claw middle"
	JLabel Label19; // Center, Caption = "Number of boxes"
	JLabel Label22; // Center, Caption = "Camera Rotation"
	JLabel Label21; // Right Justify, Caption = "X"
	JLabel Label20; // Right Justify, Caption = "Y"
	JLabel Label18; // Right Justify, BorderStyle = Fixed Single
	JLabel Label17; // Right Justify, BorderStyle = Fixed Single
	JLabel Label15; // Center, Caption = "X-Translate"
	JLabel Label14; // Center, Caption = "Z-Translate"
	JLabel lblRouteName; // Center, BorderStyle = Fixed Single
	JLabel Label13; // Center, Caption = "Route Name:"
	JLabel Label12; // Center, Caption = "Open/Close"
	JLabel Label11; // Center, Caption = "Rotate"
	JLabel Label10; // Center, Caption = "Claw"
	JLabel Label9; // Center, Caption = "Arm3"
	JLabel Label8; // Center, Caption = "Arm2"
	JLabel Label7; // Center, Caption = "Arm1"
	JLabel Label6; // Caption = "z"
	JLabel Label5; // Caption = "y"
	JLabel Label4; // Caption = "x"
	JLabel Label3; // Right Justify, BorderStyle = Fixed Single
	JLabel Label2; // Right Justify, BorderStyle = Fixed Single
	JLabel Label16; // Right Justify, BorderStyle = Fixed Single
	
	Robot r;
	Box[] b = new Box[10];
	
	/**
	 * Array of strings for each of the 1000 allowed moves in a route
	 */
	String[] moves = new String[1000];
	
//	    1 - Arm1 right
//	    2 - Arm1 left
//	    3 - Arm2 right
//	    4 - Arm2 left
//	    5 - Arm3 right
//	    6 - Arm3 left
//	    7 - Clawrotate right
//	    8 - Clawrotate left
//	    9 - Clawopen right
//	    a - Clawopen left
//	    b - Xtrans right
//	    c - Xtrans left
//	    d - Ztrans right
//	    e - Ztrans left
	
	/**
	 * number of moves of the current route of the robot
	 */
	int number_moves;
	
	/**
	 * number of boxes on the screen
	 */
	int number_boxes;
	
	/**
	 * starting variable for the timer that moves the robot through a route
	 */
	int s;
	
	/**
	 * ending variables for the timer that moves the robot through a route
	 */
	int e;
	
	/**
	 * x-coordinate of the mouse on the screen
	 */
	int mousex;
	
	/**
	 * y-coordinate of the mouse on the screen
	 */
	int mousey;
	
	/**
	 * x-coordinate of the middle of the robot's claw
	 */
	double middle_claw_x;
	
	/**
	 * y-coordinate of the middle of the robot's claw
	 */
	double middle_claw_y;
	
	/**
	 * z-coordinate of the middle of the robot's claw
	 */
	double middle_claw_z;
	
	/**
	 * x-coordinate of the middle of a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	double[] middle_box_x = new double[10];
	
	/**
	 * y-coordinate of the middle of a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	double[] middle_box_y = new double[10];
	
	/**
	 * z-coordinate of the middle of a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	double[] middle_box_z = new double[10];
	
	/**
	 * boolean to tell the robot if it is holding a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	boolean[] holding = new boolean[10];
	
	private void chkMoves_Click() {
		if (chkMoves.isSelected()) { // if chkMoves is true after it is clicked
			// disable the use of txtArm1, txtArm2, and txtArm3
			txtArm1.setEnabled(false);
			txtArm2.setEnabled(false);
			txtArm3.setEnabled(false);
			
			// disable the use of txtClawRotate and txtOpenClaw
			txtClawRotate.setEnabled(false);
			txtOpenClaw.setEnabled(false);
			
			// disable the use of txtXTranslate and txtZTranslate
			txtXTranslate.setEnabled(false);
			txtZTranslate.setEnabled(false);
			
			// all of these are disabled because moving the robot
			// through the textboxes can affect the way a route is
			// replayed, so it is bad if they are used while teaching
			
			if (lblRouteName.getText().isEmpty()) { // if no route is currently selected
				// ask if user wants to create a new route
				msg = MsgBox("Would you like to create a new route?", vbYesNo);
				if (msg == 6) { // if the user selects "Yes"
					cmdNewRoute_Click(); // go to the function to create a new route
				} else { // if the user selects "No"
					// ask if user wants to load an existing route
					msg = MsgBox("Would you like to open an existing route?", vbYesNo);
					if (msg == 6) { // if the user selects "Yes"
						cmdOpenRoute_Click(); // go to the function to create a new route
					} else { // if the user selects "No"
						// do nothing
					}
				}
			}
			cmdDrawRobot_Click(); // draw the robot even if it is already drawn
		}
		
		if (!chkMoves.isSelected()) { // if chkMoves is false after it is clicked
			// allow the use of txtArm1, txtArm2, and txtArm3
			txtArm1.setEnabled(true);
			txtArm2.setEnabled(true);
			txtArm3.setEnabled(true);
			
			// allow the use of txtClawRotate and txtOpenClaw
			txtClawRotate.setEnabled(true);
			txtOpenClaw.setEnabled(true);
			
			// allow the use of txtXTranslate and txtZTranslate
			txtXTranslate.setEnabled(true);
			txtZTranslate.setEnabled(true);
		}
	}
	
	private void cmdClearCurrentRoute_Click() {
		for (int i = 0; i < number_moves; i++) {
			moves[i] = ""; // clears the current move
		}
		
		number_moves = 0; // there are no moves in the route (new route)
		
		if (!lblRouteName.getText().isEmpty()) { // if there is a route selected
			//Open App.Path + "\" & lblRouteName & ".txt" For Output As #1            // open the file for that route
			//Close #1                                                                // close the file without adding anything, which clears the file
		}
	}
	
	private void cmdDrawRobot_Click() {
		hscrArm1.setValue(0);
		txtArm1.setText("0");
		hscrArm2.setValue(0);
		txtArm2.setText("0");
		hscrArm3.setValue(0);
		txtArm3.setText("0");
		hscrClawOpen.setValue(0);
		txtOpenClaw.setText("0");
		hscrClawRotate.setValue(90);
		txtClawRotate.setText("90");
		hscrXTranslate.setValue(0);
		txtXTranslate.setText("0");
		hscrZTranslate.setValue(0);
		txtZTranslate.setText("0");
		hscrRotateX.setValue(0);
		txtRotateX.setText("0");
		hscrRotateY.setValue(0);
		txtRotateY.setText("0");
		
		number_moves = 0; // new route, so set number_moves to 0
		number_boxes = Integer.parseInt(txtNumberBoxes.getText());
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.initialize();
		r.base();
		r.arm();
		r.arm2();
		r.arm3();
		r.thingy_with_the_grabby();
		r.initialize_joints();
		r.draw(pctRobot);
		
		for (int i = 0; i < number_boxes; i++) {
			b[i].initialize();
			b[i].initialize_joints();
		}
		
		getjoints();
		
		get_middle_box();
		get_middle_claw();
	}
	
	private void cmdAutoRobot_Click() {
		tmrAutoRobot.stop();
		tmrResetRobot.stop();
		
		chkMoves.setSelected(false);
		
		tmrResetRobot.restart();
		
		s = 1;
		e = number_moves;
		
		while (tmrResetRobot.isRunning()) {
			try {
				Thread.sleep(tmrResetRobot.getDelay());
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		tmrAutoRobot.restart();
	}
	
	private void cmdNewRoute_Click() {
		String route_name;
		String route;
		int msg;
		
		if (!lblRouteName.getText().isEmpty()) {
			msg = MsgBox("Would you like to save your current route?", vbYesNo);
			if (msg == 6) {
				for (int i = 0; i < number_moves; i++) {
					route = route + moves[i];
				}
				
				Save_route(route);
			}
		}
		
		route_name = InputBox("What would you like the new route to be named?");
		
		do {
			if (!Dir(App.Path + "/" + route_name + ".txt").isEmpty()) {
				msg = MsgBox("This route already exists. Overwrite?", vbYesNo);
				if (msg == 6) {
					Open App.Path + "/" + route_name + ".txt" For Output As #1
					Close #1
					GoTo EndLoop
				}
				if (msg == 7) {
					msg = MsgBox("Create a different route?", vbYesNo);
					if (msg == 6) {
						route_name = InputBox("Route name:");
					}
				}
			}
		} while (!Dir(App.Path + "/" + route_name + ".txt").isEmpty());
		
		for (int i = 0; i < number_moves; i++) {
			moves[i] = "";
		}
		
		number_moves = 0;
		
		lblRouteName.setText(route_name);
		cmdSaveRoute.setEnabled(true);
	}
	
	private void cmdOpenRoute_Click() {
		String route_name;
		String listRoutes;
		
		if (Dir(App.Path + "/RouteList.txt").isEmpty()) {
			Open App.Path + "/RouteList.txt" For Output As #1
			Close #1
		}
		
		Open App.Path + "\RouteList.txt" For Input As #1
		
		while (Not EOF(1)) {
			Line Input #1, sNextLine
			listRoutes = listRoutes + sNextLine + "\n";
		}
		
		Close #1
		
		route_name = InputBox("Which route would you like to open?" & vbCrLf & "Routes: " & vbCrLf & listRoutes)
		
		if (route_name.isEmpty()) {
			MsgBox "No route selected.";
			return;
		}
		
		for (int i = 0; i < number_moves; i++) {
			moves[i] = "";
		}
		
		number_moves = 0;
		
		Open_route(route_name);
		cmdSaveRoute.setEnabled(true);
		
		cmdAutoRobot.SetFocus();
	}
	
	private void Save_route(String route) {
		if (Dir(App.Path + "/" + lblRouteName.getText() + ".txt").isEmpty()) {
			Open App.Path + "/RouteList.txt" For Append As #1
				Print #1, lblRouteName
			Close #1
		}
		
		Open App.Path + "/" + lblRouteName.getText() + ".txt" For Output As #1
			Print #1, route
		Close #1
	}
	
	public void Open_route(String route_name) {
		String move_type;
		String filename;
		String routelist;
		
		filename = App.Path + "/" + route_name + ".txt";
		if (Dir(filename).isEmpty()) {
			GoTo CreateRoute;
		}
		
		Open filename For Input As #1
		Input #1, routelist
			if (routelist.length() == 0) {
				GoTo skipOpen;
			}
			
			number_moves = routelist.length();
			for (int i = 0; i < number_moves; i++) {
				move_type = routelist.substring(i, 1);
				moves[i] = move_type;
			}
			
	skipOpen:
		
		Close #1
		
		lblRouteName.setText(route_name);
		
		return;
		
	CreateRoute:
		msg = MsgBox("File does not exist! Would you like to create the route?", vbYesNo);
		if (msg == 6) {
			lblRouteName.setText(route_name);
			cmdSaveRoute_Click();
		}
	}
	
	private void cmdResetRobot_Click() {
		int msg = MsgBox("Save route?", vbYesNo);
		if (msg == 6) {
			cmdSaveRoute_Click();
		}
		
		chkMoves.setSelected(false);
		tmrResetRobot.restart();
	}
	
	private void cmdSaveRoute_Click() {
		String route = "";
		for (int i = 0; i < number_moves; i++) {
			route = route + moves[i];
		}
		
		// MsgBox route
		
		Save_route(route);
	}
	
	private void Form_Unload(int Cancel) {
		int msg;
		if (!lblRouteName.getText().isEmpty()) {
			msg = MsgBox("Save current route?", vbYesNo);
		}
		if (msg == 6) {
			cmdSaveRoute_Click();
		}
		
		Close();
	}
	
	private void hscrArm1_Change() {
		if (chkMoves.isSelected()) {
			if (hscrArm1.getValue() - Integer.parseInt(txtArm1.getText()) > 0) {
				moves[number_moves + 1] = "1";
			}
			if (hscrArm1.getValue() - Integer.parseInt(txtArm1.getText()) < 0) {
				moves[number_moves + 1] = "2";
			}
			number_moves = number_moves + 1;
		}
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.super_rotate(pctRobot, 1, hscrArm1.getValue(), 0, 0, 0, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 1, hscrArm1.getValue() - Integer.parseInt(txtArm1.getText()), 0, 0, 0, 0);
			}
		}
		
		txtArm1.setText(String.valueOf(hscrArm1.getValue()));
	}
	
	private void hscrArm2_Change() {
		if (chkMoves.isSelected()) {
			if (hscrArm2.getValue() - Integer.parseInt(txtArm2.getText()) > 0) {
				moves[number_moves + 1] = "3";
			}
			if (hscrArm2.getValue() - Integer.parseInt(txtArm2.getText()) < 0) {
				moves[number_moves + 1] = "4";
			}
			number_moves = number_moves + 1;
		}
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.super_rotate(pctRobot, 2, 0, hscrArm2.getValue(), 0, 0, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 2, 0, hscrArm2.getValue() - Integer.parseInt(txtArm2.getText()), 0, 0, 0);
			}
		}
		
		txtArm2.setText(String.valueOf(hscrArm2.getValue()));
	}
	
	private void hscrArm3_Change() {
		if (chkMoves.isSelected()) {
			if (hscrArm3.getValue() - Integer.parseInt(txtArm3.getText()) > 0) {
				moves[number_moves + 1] = "5";
			}
			if (hscrArm3.getValue() - Integer.parseInt(txtArm3.getText()) < 0) {
				moves[number_moves + 1] = "6";
			}
			number_moves = number_moves + 1;
		}
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.super_rotate(pctRobot, 3, 0, 0, hscrArm3.getValue(), 0, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 3, 0, 0, hscrArm3.getValue() - Integer.parseInt(txtArm3.getText()), 0, 0);
			}
		}
		
		txtArm3.setText(String.valueOf(hscrArm3.getValue()));
	}
	
	private void hscrClawRotate_Change() {
		if (chkMoves.isSelected()) {
			if (hscrClawRotate.getValue() - Integer.parseInt(txtClawRotate.getText()) > 0) {
				moves[number_moves + 1] = "7";
			}
			if (hscrClawRotate.getValue() - Integer.parseInt(txtClawRotate.getText()) < 0) {
				moves[number_moves + 1] = "8";
			}
			number_moves = number_moves + 1;
		}
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.super_rotate(pctRobot, 4, 0, 0, 0,  hscrClawRotate.getValue() - 90, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 4, 0, 0, 0, hscrClawRotate.getValue() - Integer.parseInt(txtClawRotate.getText()), 0);
			}
		}
		
		txtClawRotate.setText(String.valueOf(hscrClawRotate.getValue()));
	}
	
	private void hscrClawOpen_Change() {
		if (hscrClawOpen.getValue() < (Integer.parseInt(txtOpenClaw.getText()) * 10)) {
			for (int i = 0; i < number_boxes; i++) {
				if (holding[i]) {
					hscrClawOpen.setValue(hscrClawOpen.getValue() + hscrClawOpen.SmallChange);
					return;
				}
			}
		}
		
		if (chkMoves.isSelected()) {
			if (hscrClawOpen.getValue() - Integer.parseInt(txtOpenClaw.getText()) * 10 > 0) {
				moves[number_moves + 1] = "9";
			}
			if (hscrClawOpen.getValue() - Integer.parseInt(txtOpenClaw.getText()) * 10 < 0) {
				moves[number_moves + 1] = "a";
			}
			number_moves = number_moves + 1;
		}
		
		get_middle_box();
		get_middle_claw();
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.super_rotate(pctRobot, 5, 0, 0, 0, 0, (hscrClawOpen.getValue() / 10) - Integer.parseInt(txtOpenClaw.getText()));
		for (int i = 0; i < number_boxes; i++) {
			b[i].draw(pctRobot);
		}
		
		// if closing the claw, check if it will grab a box
		if (hscrClawOpen.getValue() < Integer.parseInt(txtOpenClaw.getText()) * 10) {
			for (int i = 0; i < number_boxes; i++) {
				if (middle_claw_x < middle_box_x[i] + 0.5
						&& middle_claw_x > middle_box_x[i] - 0.5
						&& middle_claw_y < middle_box_y[i] + 0.5
						&& middle_claw_y > middle_box_y[i] - 0.5
						&& middle_claw_z < middle_box_z[i] + 0.5
						&& middle_claw_z > middle_box_z[i] - 0.5) {
					holding[i] = true;
					MsgBox("Holding a box");
					b[i].rotate_factorY = r.rotate_factorY;
				}
			}
		}
		
		// if opening the claw, release anything it's holding
		if (hscrClawOpen.getValue() > Integer.parseInt(txtOpenClaw.getText()) * 10) {
			for (int i = 0; i < number_boxes; i++) {
				holding[i] = false;
				b[i].drop();
			}
		}
		
		txtOpenClaw.setText(String.valueOf(hscrClawOpen.getValue() / 10));
	}
	
	private void hscrXTranslate_Change() {
		if (chkMoves.isSelected()) {
			if (hscrXTranslate.getValue() - Integer.parseInt(txtXTranslate.getText()) > 0) {
				moves[number_moves + 1] = "b";
			}
			if (hscrXTranslate.getValue() - Integer.parseInt(txtXTranslate.getText()) < 0) {
				moves[number_moves + 1] = "c";
			}
			number_moves = number_moves + 1;
		}
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.translate_x(pctRobot, hscrXTranslate.getValue() - Integer.parseInt(txtXTranslate.getText()));
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].translate_x(pctRobot, hscrXTranslate.getValue() - Integer.parseInt(txtXTranslate.getText()));
			}
		}
		
		getjoints();
		
		txtXTranslate.setText(String.valueOf(hscrXTranslate.getValue()));
	}
	
	private void hscrZTranslate_Change() {
		if (chkMoves.isSelected()) {
			if (hscrZTranslate.getValue() - Integer.parseInt(txtZTranslate.getText()) > 0) {
				moves[number_moves + 1] = "d";
			}
			if (hscrZTranslate.getValue() - Integer.parseInt(txtZTranslate.getText()) < 0) {
				moves[number_moves + 1] = "e";
			}
			number_moves = number_moves + 1;
		}
		
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.translate_z(pctRobot, hscrZTranslate.getValue() - Integer.parseInt(txtZTranslate.getText()));
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].translate_z(pctRobot, hscrZTranslate.getValue() - Integer.parseInt(txtZTranslate.getText()));
			}
		}
		
		getjoints();
		
		txtZTranslate.setText(String.valueOf(hscrZTranslate.getValue()));
	}
	
	private void lblRouteName_Change() {
		if (!lblRouteName.getText().isEmpty()) {
			cmdSaveRoute.setEnabled(true);
		} else {
			cmdSaveRoute.setEnabled(false);
		}
	}
	
	private void pctRobot_MouseDown(int Button, int Shift, int x, int y) {
		mousex = x; mousey = y;
	}
	
	private void pctRobot_MouseMove(int Button, int Shift, int x, int y) {
		if (Button == 1) {
			if (hscrRotateX.getValue() - (mousey - y) < 76 && hscrRotateX.getValue() - (mousey - y) > -1) {
				hscrRotateX.setValue(hscrRotateX.getValue() - (mousey - y));
			}
			if (hscrRotateY.getValue() + (mousex - x) < 361 && hscrRotateY.getValue() + (mousex - x) > -361) {
				hscrRotateY.setValue(hscrRotateY.getValue() + (mousex - x));
			}
			mousex = x;
			mousey = y;
		}
	}
	
	private void hscrRotateX_Change() {
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.camera_rotate(pctRobot, -hscrRotateX.getValue(), 0);
		
		for (int i = 0; i < number_boxes; i++) {
			b[i].camera_rotate(pctRobot, -hscrRotateX.getValue(), 0);
		}
		
		txtRotateX.setText(String.valueOf(hscrRotateX.getValue()));
	}
	
	private void hscrRotateY_Change() {
		pctRobot.clearRect(0, 0, 9000, 9000);
		
		r.camera_rotate(pctRobot, 0, hscrRotateY.getValue());
		
		for (int i = 0; i < number_boxes; i++) {
			b[i].camera_rotate(pctRobot, 0, hscrRotateY.getValue());
		}
		
		txtRotateY.setText(String.valueOf(hscrRotateY.getValue()));
	}
	
	private void txtNumberBoxes_Change() {
		if (Integer.parseInt(txtNumberBoxes.getText()) < 1
				|| Integer.parseInt(txtNumberBoxes.getText()) > 10) {
			txtNumberBoxes.setText(String.valueOf(number_boxes));
		}
	}
	
	private void txtRotateY_Change() {
		if (Integer.parseInt(txtRotateY.getText()) > 360 || Integer.parseInt(txtRotateY.getText()) < -360) {
			txtRotateY.setText(String.valueOf(Integer.parseInt(txtRotateY.getText()) % 360));
		}
		hscrRotateY.setValue(Integer.parseInt(txtRotateY.getText()));
		if (Integer.parseInt(txtRotateY.getText()) == 360 || Integer.parseInt(txtRotateY.getText()) == -360) {
			txtRotateY.setText("0");
			hscrRotateY.setValue(0);
		}
	}
	
	private void tmrAutoRobot_Timer() {
		if (moves[s] == "1") {
			hscrArm1.setValue(hscrArm1.getValue() + 5);
		}
		if (moves[s] == "2") {
			hscrArm1.setValue(hscrArm1.getValue() - 5);
		}
		if (moves[s] == "3") {
			hscrArm2.setValue(hscrArm2.getValue() + 5);
		}
		if (moves[s] == "4") {
			hscrArm2.setValue(hscrArm2.getValue() - 5);
		}
		if (moves[s] == "5") {
			hscrArm3.setValue(hscrArm3.getValue() + 5);
		}
		if (moves[s] == "6") {
			hscrArm3.setValue(hscrArm3.getValue() - 5);
		}
		if (moves[s] == "7") {
			hscrClawRotate.setValue(hscrClawRotate.getValue() + 5);
		}
		if (moves[s] == "8") {
			hscrClawRotate.setValue(hscrClawRotate.getValue() - 5);
		}
		if (moves[s] == "9") {
			hscrClawOpen.setValue(hscrClawOpen.getValue() + 1);
		}
		if (moves[s] == "a") {
			hscrClawOpen.setValue(hscrClawOpen.getValue() - 1);
		}
		if (moves[s] == "b") {
			hscrXTranslate.setValue(hscrXTranslate.getValue() + 1);
		}
		if (moves[s] == "c") {
			hscrXTranslate.setValue(hscrXTranslate.getValue() - 1);
		}
		if (moves[s] == "d") {
			hscrZTranslate.setValue(hscrZTranslate.getValue() + 1);
		}
		if (moves[s] == "e") {
			hscrZTranslate.setValue(hscrZTranslate.getValue() - 1);
		}
		
		s = s + 1;
		
		if (s > e) {
			tmrAutoRobot.stop();
		}
	}
	
	private void tmrResetRobot_Timer() {
		if (hscrArm1.getValue() > 0 && hscrArm1.getValue() <= 180) {
			hscrArm1.setValue(hscrArm1.getValue() - 5);
		}
		if (hscrArm1.getValue() > 0 && hscrArm1.getValue() > 180) {
			hscrArm1.setValue(hscrArm1.getValue() + 5);
		}
		if (hscrArm1.getValue() < 0 && hscrArm1.getValue() >= -180) {
			hscrArm1.setValue(hscrArm1.getValue() + 5);
		}
		if (hscrArm1.getValue() < 0 && hscrArm1.getValue() < -180) {
			hscrArm1.setValue(hscrArm1.getValue() - 5);
		}
		if (hscrArm2.getValue() > 0) {
			hscrArm2.setValue(hscrArm2.getValue() - 5);
		}
		if (hscrArm2.getValue() < 0) {
			hscrArm2.setValue(hscrArm2.getValue() + 5);
		}
		if (hscrArm3.getValue() > 0) {
			hscrArm3.setValue(hscrArm3.getValue() - 5);
		}
		if (hscrArm3.getValue() < 0) {
			hscrArm3.setValue(hscrArm3.getValue() + 5);
		}
		if (hscrClawOpen.getValue() > 0) {
			hscrClawOpen.setValue(hscrClawOpen.getValue() - 1);
		}
		if (hscrClawOpen.getValue() < 0) {
			hscrClawOpen.setValue(hscrClawOpen.getValue() + 1);
		}
		if (hscrClawRotate.getValue() > 90) {
			hscrClawRotate.setValue(hscrClawRotate.getValue() - 5);
		}
		if (hscrClawRotate.getValue() < 90) {
			hscrClawRotate.setValue(hscrClawRotate.getValue() + 5);
		}
		if (hscrXTranslate.getValue() > 0) {
			hscrXTranslate.setValue(hscrXTranslate.getValue() - 1);
		}
		if (hscrXTranslate.getValue() < 0) {
			hscrXTranslate.setValue(hscrXTranslate.getValue() + 1);
		}
		if (hscrZTranslate.getValue() > 0) {
			hscrZTranslate.setValue(hscrZTranslate.getValue() - 1);
		}
		if (hscrZTranslate.getValue() < 0) {
			hscrZTranslate.setValue(hscrZTranslate.getValue() + 1);
		}
		
		if (hscrArm1.getValue() == 0
				&& hscrArm2.getValue() == 0
				&& hscrArm3.getValue() == 0
				&& hscrClawOpen.getValue() == 0
				&& hscrClawRotate.getValue() == 90
				&& hscrXTranslate.getValue() == 0
				&& hscrZTranslate.getValue() == 0) {
			tmrResetRobot.stop();
		}
	}
	
	private void txtArm1_Change() {
		if (Integer.parseInt(txtArm1.getText()) > hscrArm1.getMaximum()) {
			txtArm1.setText(String.valueOf(Integer.parseInt(txtArm1.getText()) % hscrArm1.getMaximum()));
		}
		if (Integer.parseInt(txtArm1.getText()) < hscrArm1.getMinimum()) {
			txtArm1.setText(String.valueOf(Integer.parseInt(txtArm1.getText()) % hscrArm1.getMinimum()));
		}
		
		if (Integer.parseInt(txtArm1.getText()) > 360 || Integer.parseInt(txtArm1.getText()) < -360) {
			txtArm1.setText(String.valueOf(Integer.parseInt(txtArm1.getText()) % 360));
			hscrArm1.setValue(Integer.parseInt(txtArm1.getText()));
		}
		
		if (Integer.parseInt(txtArm1.getText()) == 360 || Integer.parseInt(txtArm1.getText()) == -360) {
			txtArm1.setText("0");
			hscrArm1.setValue(0);
		}
		
		hscrArm1_Change();
	}
	
	private void txtArm2_Change() {
		if (Integer.parseInt(txtArm2.getText()) > hscrArm2.getMaximum()) {
			txtArm2.setText(String.valueOf(Integer.parseInt(txtArm2.getText()) % hscrArm2.getMaximum()));
		}
		if (Integer.parseInt(txtArm2.getText()) < hscrArm2.getMinimum()) {
			txtArm2.setText(String.valueOf(Integer.parseInt(txtArm2.getText()) % hscrArm2.getMinimum()));
		}
		
		hscrArm2.setValue(Integer.parseInt(txtArm2.getText()));
		
		hscrArm2_Change();
	}
	
	private void txtArm3_Change() {
		if (Integer.parseInt(txtArm3.getText()) > hscrArm3.getMaximum()) {
			txtArm3.setText(String.valueOf(Integer.parseInt(txtArm3.getText()) % hscrArm3.getMaximum()));
		}
		if (Integer.parseInt(txtArm3.getText()) < hscrArm3.getMinimum()) {
			txtArm3.setText(String.valueOf(Integer.parseInt(txtArm3.getText()) % hscrArm3.getMinimum()));
		}
		
		hscrArm3.setValue(Integer.parseInt(txtArm3.getText()));
		
		hscrArm3_Change();
	}
	
	private void txtClawRotate_Change() {
		if (Integer.parseInt(txtClawRotate.getText()) > hscrClawRotate.getMaximum()) {
			txtClawRotate.setText(String.valueOf(Integer.parseInt(txtClawRotate.getText()) % hscrClawRotate.getMaximum()));
		}
		if (Integer.parseInt(txtClawRotate.getText()) < hscrClawRotate.getMinimum()) {
			txtClawRotate.setText(String.valueOf(Integer.parseInt(txtClawRotate.getText()) % hscrClawRotate.getMinimum()));
		}
		
		hscrClawRotate.setValue(Integer.parseInt(txtClawRotate.getText()));
		
		hscrClawRotate_Change();
	}
	
	private void txtOpenClaw_Change() {
		if (Integer.parseInt(txtOpenClaw.getText()) * 10 > hscrClawOpen.getMaximum()) {
			txtOpenClaw.setText(String.valueOf((Integer.parseInt(txtOpenClaw.getText()) % hscrClawOpen.getMaximum()) / 10));
		}
		if (Integer.parseInt(txtOpenClaw.getText()) * 10 < hscrClawOpen.getMinimum()) {
			txtOpenClaw.setText(String.valueOf(Math.abs(Integer.parseInt(txtOpenClaw.getText()) % hscrClawOpen.getMinimum()) / 10));
		}
		
		hscrClawOpen.setValue(Integer.parseInt(txtOpenClaw.getText()) * 10);
		
		hscrClawOpen_Change();
	}
	
	private void txtXTranslate_Change() {
		if (Integer.parseInt(txtXTranslate.getText()) > hscrXTranslate.getMaximum()) {
			txtXTranslate.setText(String.valueOf(Integer.parseInt(txtXTranslate.getText()) % hscrXTranslate.getMaximum()));
		}
		if (Integer.parseInt(txtXTranslate.getText()) < hscrXTranslate.getMinimum()) {
			txtXTranslate.setText(String.valueOf(Integer.parseInt(txtXTranslate.getText()) % hscrXTranslate.getMinimum()));
		}
		
		hscrXTranslate.setValue(Integer.parseInt(txtXTranslate.getText()));
		
		hscrXTranslate_Change();
	}
	
	private void txtZTranslate_Change() {
		if (Integer.parseInt(txtZTranslate.getText()) > hscrZTranslate.getMaximum()) {
			txtZTranslate.setText(String.valueOf(Integer.parseInt(txtZTranslate.getText()) % hscrZTranslate.getMaximum()));
		}
		if (Integer.parseInt(txtZTranslate.getText()) < hscrZTranslate.getMinimum()) {
			txtZTranslate.setText(String.valueOf(Integer.parseInt(txtZTranslate.getText()) % hscrZTranslate.getMinimum()));
		}
		
		hscrZTranslate.setValue(Integer.parseInt(txtZTranslate.getText()));
		
		hscrZTranslate_Change();
	}
	
	public void get_middle_claw() {
		middle_claw_x = r.middle_claw_x;
		middle_claw_y = r.middle_claw_y;
		middle_claw_z = r.middle_claw_z;
		
		// MsgBox middle_claw_z
	}
	
	public void get_middle_box() {
		for (int i = 0; i < number_boxes; i++) {
			middle_box_x[i] = b[i].middle_box_x;
			middle_box_y[i] = b[i].middle_box_y;
			middle_box_z[i] = b[i].middle_box_z;
		}
	}
	
	public void getjoints() {
		for (int i = 0; i < number_boxes; i++) {
			b[i].joint_x0 = r.joint_x0;
			b[i].joint_y0 = r.joint_y0;
			b[i].joint_z0 = r.joint_z0;
			
			b[i].joint_x1 = r.joint_x1;
			b[i].joint_y1 = r.joint_y1;
			b[i].joint_z1 = r.joint_z1;
			
			b[i].joint_x2 = r.joint_x2;
			b[i].joint_y2 = r.joint_y2;
			b[i].joint_z2 = r.joint_z2;
			
			b[i].joint_x3 = r.joint_x3;
			b[i].joint_y3 = r.joint_y3;
			b[i].joint_z3 = r.joint_z3;
			
			b[i].draw(pctRobot);
			b[i].givejoints();
		}
	}
	
	public double degrees(double theta) {
		return (theta * (180.0 / 3.1415926538979));
	}
	
	public double radians(double theta) {
		return (theta * (3.1415926538979 / 180.0));
	}
}
