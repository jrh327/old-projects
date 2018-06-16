package net.jonhopkins.robot;

public class Form {
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
	    
	    If chkMoves.Value = 1 Then                                                          // if chkMoves is true after it is clicked
	        txtArm1.Enabled = False: txtArm2.Enabled = False: txtArm3.Enabled = False:      // disable the use of txtArm1, txtArm2, and txtArm3
	        txtClawRotate.Enabled = False: txtOpenClaw.Enabled = False                      // disable the use of txtClawRotate and txtOpenClaw
	        txtXTranslate.Enabled = False: txtZTranslate.Enabled = False                    // disable the use of txtXTranslate and txtZTranslate
	                                                                                        //  all of these are disabled because moving the robot
	                                                                                        //  through the textboxes can affect the way a route is
	                                                                                        //  replayed, so it is bad if they are used while teaching
	        If lblRouteName = "" Then                                                       // if no route is currently selected
	            msg = MsgBox("Would you like to create a new route?", vbYesNo)              // ask if user wants to create a new route
	            If msg = 6 Then                                                             // if the user selects "Yes"
	                cmdNewRoute_Click                                                       // go to the function to create a new route
	            Else                                                                        // if the user selects "No"
	                msg = MsgBox("Would you like to open an existing route?", vbYesNo)      // ask if user wants to load an existing route
	                If msg = 6 Then                                                         // if the user selects "Yes"
	                    cmdOpenRoute_Click                                                  // go to the function to create a new route
	                Else                                                                    // if the user selects "No"
	                End If                                                                  // do nothing, end of If msg = 6
	            End If                                                                      // end of If msg = 6
	        End If                                                                          // end of If lblRouteName = "
	        cmdDrawRobot_Click                                                              // draw the robot even if it is already drawn
	    End If                                                                              // end of If chkMoves.Value = 1
	    
	    If chkMoves.Value = 0 Then                                                          // if chkMoves is false after it is clicked
	        txtArm1.Enabled = True: txtArm2.Enabled = True: txtArm3.Enabled = True          // allow the use of txtArm1, txtArm2, and txtArm3
	        txtClawRotate.Enabled = True: txtOpenClaw.Enabled = True                        // allow the use of txtClawRotate and txtOpenClaw
	        txtXTranslate.Enabled = True: txtZTranslate.Enabled = True                      // allow the use of txtXTranslate and txtZTranslate
	    End If                                                                              // end of If chkMoves.Value = 0
	        
	    
	}
	
	private void cmdClearCurrentRoute_Click() {

	    For i = 0 To number_moves                                                   // start a for-loop from 0 to the number of moves in the current route
	        moves(i) = ""                                                           // clears the current move
	    Next i                                                                      // advance the for-loop

	    number_moves = 0                                                            // there are no moves in the route (new route)
	    
	    If lblRouteName <> "" Then                                                  // if there is a route selected
	        Open App.Path + "\" & lblRouteName & ".txt" For Output As #1            // open the file for that route
	        Close #1                                                                // close the file without adding anything, which clears the file
	    End If                                                                      // end of If lblRouteName <> ""
	    
	}
	
	private void cmdDrawRobot_Click() {
	        
	    frmRobot.hscrArm1.Value = 0                 // reset hscrArm1
	    frmRobot.txtArm1 = 0                        // set txtArm1 to 0 to reflect the above change
	    frmRobot.hscrArm2.Value = 0                 // reset hscrArm2
	    frmRobot.txtArm2 = 0                        // set txtArm2 to 0 to reflect the above change
	    frmRobot.hscrArm3.Value = 0                 // reset hscrArm3
	    frmRobot.txtArm3 = 0                        // set txtArm3 to 0 to reflect the above change
	    frmRobot.hscrClawOpen = 0                   // reset hscrClawOpen
	    frmRobot.txtOpenClaw = 0                    // set txtOpenClaw to 0 to reflect the above change
	    frmRobot.hscrClawRotate = 90                // reset hscrClawRotate
	    frmRobot.txtClawRotate = 90                 // set txtClawRotate to 90 to reflect the above change
	    frmRobot.hscrXTranslate = 0                 // reset hscrXTranslate
	    frmRobot.txtXTranslate = 0                  // set txtXTranslate to 0 to reflect the above change
	    frmRobot.hscrZTranslate = 0                 // reset hscrZTranslate
	    frmRobot.txtZTranslate = 0                  // set txtRotateX to 0 to reflect the above change
	    frmRobot.hscrRotateX = 0                    // reset hscrZTranslate
	    frmRobot.txtRotateX = 0                     // set txtRotateX to 0 to reflect the above change
	    frmRobot.hscrRotateY = 0                    // reset hscrRotateY
	    frmRobot.txtRotateY = 0                     // set txtRotateY to 0 to reflect the above change
	    
	    number_moves = 0                            // new route, so set number_moves to 0
	    number_boxes = Val(txtNumberBoxes)          // txtNumberBoxes is how many boxes the user wants on the screen, so set number_boxes to its value
	    
	    frmRobot.pctRobot.Cls                       // clear the picture box
	    
	    r.initialize                                // call the initialize function of the robot
	    r.base                                      // call the base function of the robot
	    r.arm                                       // call the arm function of the robot
	    r.arm2                                      // call the arm2 function of the robot
	    r.arm3                                      // call the arm3 function of the robot
	    r.thingy_with_the_grabby                    // call the thingy_with_the_grabby function of the robot
	    r.initialize_joints                         // call the initialize_joints function of the robot
	    r.draw frmRobot.pctRobot                    // call the draw function of the robot, which ByRefs a picturebox, so send frmRobot.pctRobot
	    
	    For i = 1 To number_boxes                   // start a for-loop from 1 to the number of boxes to be on the screen
	        b(i).initialize                         // call the initialize function of the current box
	        b(i).initialize_joints                  // call the initialize_joints function of the current box
	    Next i                                      // advance the loop
	    
	    getjoints                                   // call the function to set the coordinates of the joints of the boxex to that of the robot
	    
	    get_middle_box                              // call the function to find the coordinates of the center of all the boxes
	    get_middle_claw                             // call the function to find the coordinates of the center of the robot// s claw

	}
	
	private void cmdAutoRobot_Click() {
	    
	    tmrAutoRobot.Enabled = False
	    tmrResetRobot.Enabled = False
	    
	    chkMoves.Value = 0
	    
	    tmrResetRobot.Enabled = True
	    
	    s = 1: e = number_moves
	    
	    Do
	    DoEvents
	    Loop Until tmrResetRobot.Enabled = False
	    
	    tmrAutoRobot.Enabled = True
	    
	}
	
	private void cmdNewRoute_Click() {
	        
	    String route_name;
	    String route;
	    int msg;
	    
	    If lblRouteName <> "" Then
	        msg = MsgBox("Would you like to save your current route?", vbYesNo)
	        If msg = 6 Then
	                
	            For i = 1 To number_moves
	                route = route + moves(i)
	            Next i
	            
	            Save_route route
	        
	        End If
	    End If
	    
	    route_name = InputBox("What would you like the new route to be named?")
	    
	    Do
	        If Dir(App.Path + "\" & route_name & ".txt") <> "" Then
	            msg = MsgBox("This route already exists. Overwrite?", vbYesNo)
	            If msg = 6 Then
	                Open App.Path + "\" & route_name & ".txt" For Output As #1
	                Close #1
	                GoTo EndLoop
	            End If
	            If msg = 7 Then
	                msg = MsgBox("Create a different route?", vbYesNo)
	                If msg = 6 Then
	                    route_name = InputBox("Route name:")
	                End If
	            End If
	        End If
	    Loop Until Dir(App.Path + "\" & route_name & ".txt") = ""

	EndLoop:
	    
	    For i = 0 To number_moves
	        moves(i) = ""
	    Next i
	    
	    number_moves = 0
	    
	    lblRouteName = route_name
	    cmdSaveRoute.Enabled = True
	    
	    
	}
	
	private void cmdOpenRoute_Click() {
	    
	    String route_name;
	    String listRoutes;
	    
	    If Dir(App.Path + "\RouteList.txt") = "" Then
	        Open App.Path + "\RouteList.txt" For Output As #1
	        Close #1
	    End If

	    Open App.Path + "\RouteList.txt" For Input As #1
	        
	        Do While Not EOF(1)
	            Line Input #1, sNextLine
	            listRoutes = listRoutes & sNextLine & vbCrLf
	        Loop
	        
	    Close #1
	    
	    route_name = InputBox("Which route would you like to open?" & vbCrLf & "Routes: " & vbCrLf & listRoutes)
	    
	    If route_name = "" Then MsgBox "No route selected.": Exit Sub
	    
	    For i = 1 To number_moves
	        moves(i) = ""
	    Next i
	    
	    number_moves = 0
	    
	    Open_route (route_name)
	    cmdSaveRoute.Enabled = True
	    
	    cmdAutoRobot.SetFocus
	    
	}
	
	private void Save_route(String route) {
	    
	    If Dir(App.Path + "\" & lblRouteName & ".txt") = "" Then
	        Open App.Path + "\RouteList.txt" For Append As #1
	            Print #1, lblRouteName
	        Close #1
	    End If

	    Open App.Path + "\" & lblRouteName & ".txt" For Output As #1
	        Print #1, route
	    Close #1
	    
	}
	
	public void Open_route(String route_name) {
	    
	    String move_type;
	    String filename;
	    String routelist;
	    
	    filename = App.Path + "\" & route_name & ".txt"
	    If Dir(filename) = "" Then GoTo CreateRoute
	    
	    Open filename For Input As #1
	    Input #1, routelist
	        If Len(routelist) = 0 Then GoTo skipOpen
	        
	        number_moves = Len(routelist)
	        For i = 1 To number_moves
	            move_type = Mid(routelist, i, 1)
	            moves(i) = move_type
	        Next i
	        
	skipOpen:
	        
	    Close #1
	    
	    lblRouteName = route_name
	    
	    Exit Sub
	    
	CreateRoute:
	    msg = MsgBox("File does not exist! Would you like to create the route?", vbYesNo)
	    If msg = 6 Then
	        lblRouteName = route_name
	        cmdSaveRoute_Click
	    End If
	    
	}
	
	private void cmdResetRobot_Click() {
	            
	    msg = MsgBox("Save route?", vbYesNo)
	    If msg = 6 Then
	        cmdSaveRoute_Click
	    End If
	    
	    chkMoves.Value = 0

	    tmrResetRobot.Enabled = True
	    
	}
	
	private void cmdSaveRoute_Click() {

	    String route;
	    For i = 1 To number_moves
	        route = route + moves(i)
	    Next i
	    
	    // MsgBox route
	    
	    Save_route route
	    
	}
	
	private void Form_Unload(int Cancel) {
	    
	    If lblRouteName <> "" Then msg = MsgBox("Save current route?", vbYesNo)
	    If msg = 6 Then cmdSaveRoute_Click
	    
	    Close
	    
	}
	
	private void hscrArm1_Change() {

	    If chkMoves.Value = 1 Then
	        If hscrArm1.Value - Val(txtArm1) > 0 Then moves(number_moves + 1) = "1"
	        If hscrArm1.Value - Val(txtArm1) < 0 Then moves(number_moves + 1) = "2"
	        number_moves = number_moves + 1
	    End If
	    
	    frmRobot.pctRobot.Cls
	    
	    r.super_rotate frmRobot.pctRobot, 1, Val(hscrArm1.Value), 0, 0, 0, 0
	    
	    getjoints
	    
	    For i = 1 To number_boxes
	        If holding(i) = True Then b(i).super_rotate frmRobot.pctRobot, 1, Val(hscrArm1.Value) - Val(txtArm1), 0, 0, 0, 0
	    Next i

	    txtArm1 = hscrArm1.Value
	    
	}
	
	private void hscrArm2_Change() {
	    
	    If chkMoves.Value = 1 Then
	        If hscrArm2.Value - Val(txtArm2) > 0 Then moves(number_moves + 1) = "3"
	        If hscrArm2.Value - Val(txtArm2) < 0 Then moves(number_moves + 1) = "4"
	        number_moves = number_moves + 1
	    End If
	    
	    frmRobot.pctRobot.Cls
	    
	    r.super_rotate frmRobot.pctRobot, 2, 0, Val(hscrArm2.Value), 0, 0, 0
	    
	    getjoints
	    
	    For i = 1 To number_boxes
	        If holding(i) = True Then b(i).super_rotate frmRobot.pctRobot, 2, 0, Val(hscrArm2.Value) - Val(txtArm2), 0, 0, 0
	    Next i
	    
	    txtArm2 = hscrArm2.Value

	}
	
	private void hscrArm3_Change() {
	    
	    If chkMoves.Value = 1 Then
	        If hscrArm3.Value - Val(txtArm3) > 0 Then moves(number_moves + 1) = "5"
	        If hscrArm3.Value - Val(txtArm3) < 0 Then moves(number_moves + 1) = "6"
	        number_moves = number_moves + 1
	    End If
	    
	    frmRobot.pctRobot.Cls
	    
	    r.super_rotate frmRobot.pctRobot, 3, 0, 0, Val(hscrArm3.Value), 0, 0
	    
	    getjoints
	    
	    For i = 1 To number_boxes
	        If holding(i) = True Then b(i).super_rotate frmRobot.pctRobot, 3, 0, 0, Val(hscrArm3.Value) - Val(txtArm3), 0, 0
	    Next i
	    
	    txtArm3 = hscrArm3.Value
	    
	}
	
	private void hscrClawRotate_Change() {
	    
	    If chkMoves.Value = 1 Then
	        If hscrClawRotate.Value - Val(txtClawRotate) > 0 Then moves(number_moves + 1) = "7"
	        If hscrClawRotate.Value - Val(txtClawRotate) < 0 Then moves(number_moves + 1) = "8"
	        number_moves = number_moves + 1
	    End If
	    
	    frmRobot.pctRobot.Cls
	    
	    r.super_rotate frmRobot.pctRobot, 4, 0, 0, 0, (Val(hscrClawRotate.Value) - 90), 0
	    
	    getjoints
	    
	    For i = 1 To number_boxes
	        If holding(i) = True Then b(i).super_rotate frmRobot.pctRobot, 4, 0, 0, 0, (Val(hscrClawRotate.Value)) - Val(txtClawRotate), 0
	    Next i
	    
	    txtClawRotate = hscrClawRotate.Value
	    
	}
	
	private void hscrClawOpen_Change() {
	        
	    If hscrClawOpen.Value < (Val(txtOpenClaw) * 10) Then
	        For i = 1 To number_boxes
	            If holding(i) = True Then hscrClawOpen.Value = hscrClawOpen.Value + hscrClawOpen.SmallChange: Exit Sub
	        Next i
	    End If
	        
	    If chkMoves.Value = 1 Then
	        If hscrClawOpen.Value - Val(txtOpenClaw) * 10 > 0 Then moves(number_moves + 1) = "9"
	        If hscrClawOpen.Value - Val(txtOpenClaw) * 10 < 0 Then moves(number_moves + 1) = "a"
	        number_moves = number_moves + 1
	    End If
	    
	    get_middle_box
	    get_middle_claw
	    
	    frmRobot.pctRobot.Cls
	    
	    r.super_rotate frmRobot.pctRobot, 5, 0, 0, 0, 0, ((Val(hscrClawOpen.Value) / 10) - Val(txtOpenClaw))
	    For i = 1 To number_boxes
	        b(i).draw frmRobot.pctRobot
	    Next i
	    
	    If hscrClawOpen.Value < txtOpenClaw * 10 Then // if closing the claw, check if it will grab a box
	        For i = 1 To number_boxes
	            If middle_claw_x < middle_box_x(i) + 0.5 And middle_claw_x > middle_box_x(i) - 0.5 And _
	            middle_claw_y < middle_box_y(i) + 0.5 And middle_claw_y > middle_box_y(i) - 0.5 And _
	            middle_claw_z < middle_box_z(i) + 0.5 And middle_claw_z > middle_box_z(i) - 0.5 Then
	                holding(i) = True
	                MsgBox "Holding a box"
	                b(i).rotate_factorY = r.rotate_factorY
	            End If
	        Next i
	    End If
	    If hscrClawOpen.Value > txtOpenClaw * 10 Then  // if opening the claw, release anything it// s holding
	        For i = 1 To number_boxes
	            holding(i) = False
	            b(i).drop
	        Next i
	    End If
	    
	    txtOpenClaw = Val(hscrClawOpen.Value) / 10
	    
	}
	
	private void hscrXTranslate_Change() {
	        
	    If chkMoves.Value = 1 Then
	        If hscrXTranslate.Value - Val(txtXTranslate) > 0 Then moves(number_moves + 1) = "b"
	        If hscrXTranslate.Value - Val(txtXTranslate) < 0 Then moves(number_moves + 1) = "c"
	        number_moves = number_moves + 1
	    End If
	    
	    frmRobot.pctRobot.Cls
	    
	    r.translate_x frmRobot.pctRobot, hscrXTranslate.Value - Val(txtXTranslate)
	    
	    For i = 1 To number_boxes
	        If holding(i) = True Then b(i).translate_x frmRobot.pctRobot, hscrXTranslate.Value - Val(txtXTranslate)
	    Next i
	    
	    getjoints
	    
	    txtXTranslate = hscrXTranslate.Value
	    
	}
	
	private void hscrZTranslate_Change() {
	    
	    If chkMoves.Value = 1 Then
	        If hscrZTranslate.Value - Val(txtZTranslate) > 0 Then moves(number_moves + 1) = "d"
	        If hscrZTranslate.Value - Val(txtZTranslate) < 0 Then moves(number_moves + 1) = "e"
	        number_moves = number_moves + 1
	    End If
	    
	    frmRobot.pctRobot.Cls
	    
	    r.translate_z frmRobot.pctRobot, hscrZTranslate.Value - Val(txtZTranslate)
	    
	    For i = 1 To number_boxes
	        If holding(i) = True Then b(i).translate_z frmRobot.pctRobot, hscrZTranslate.Value - Val(txtZTranslate)
	    Next i
	    
	    getjoints
	    
	    txtZTranslate = hscrZTranslate.Value
	    
	}
	
	private void lblRouteName_Change() {
	    
	    If lblRouteName <> "" Then cmdSaveRoute.Enabled = True Else cmdSaveRoute.Enabled = False
	    
	}
	
	private void pctRobot_MouseDown(int Button, int Shift, int x, int y) {

	    mousex = x: mousey = y
	    
	}
	
	private void pctRobot_MouseMove(int Button, int Shift, int x, int y) {

	    If Button = 1 Then
	        If hscrRotateX.Value - Val(mousey - y) < 76 And hscrRotateX.Value - Val(mousey - y) > -1 Then hscrRotateX.Value = hscrRotateX.Value - Val(mousey - y)
	        If hscrRotateY.Value + Val(mousex - x) < 361 And hscrRotateY.Value + Val(mousex - x) > -361 Then hscrRotateY.Value = hscrRotateY.Value + Val(mousex - x)
	        mousex = x
	        mousey = y
	    End If

	}
	
	private void hscrRotateX_Change() {
	    
	    frmRobot.pctRobot.Cls
	    
	    r.camera_rotate frmRobot.pctRobot, -Val(hscrRotateX.Value), 0
	    
	    For i = 1 To number_boxes
	        b(i).camera_rotate frmRobot.pctRobot, -Val(hscrRotateX.Value), 0
	    Next i
	    
	    txtRotateX = hscrRotateX.Value
	    
	}
	
	private void hscrRotateY_Change() {
	    
	    frmRobot.pctRobot.Cls
	    
	    r.camera_rotate frmRobot.pctRobot, 0, Val(hscrRotateY.Value)
	    
	    For i = 1 To number_boxes
	        b(i).camera_rotate frmRobot.pctRobot, 0, Val(hscrRotateY.Value)
	    Next i
	    
	    txtRotateY = hscrRotateY.Value
	    
	}
	
	private void txtNumberBoxes_Change() {
	    
	    If Val(txtNumberBoxes) < 1 Or Val(txtNumberBoxes) > 10 Then txtNumberBoxes = number_boxes
	    
	}
	
	private void txtRotateY_Change() {
	    
	    If Val(txtRotateY) > 360 Or Val(txtRotateY) < -360 Then txtRotateY = Val(txtRotateY) Mod 360
	    hscrRotateY.Value = Val(txtRotateY)
	    If Val(txtRotateY) = 360 Or Val(txtRotateY) = -360 Then txtRotateY = 0: hscrRotateY.Value = 0
	    
	}
	
	private void tmrAutoRobot_Timer() {
	    
	    int m;
	    
	    If moves(s) = "1" Then hscrArm1.Value = hscrArm1.Value + 5
	    If moves(s) = "2" Then hscrArm1.Value = hscrArm1.Value - 5
	    If moves(s) = "3" Then hscrArm2.Value = hscrArm2.Value + 5
	    If moves(s) = "4" Then hscrArm2.Value = hscrArm2.Value - 5
	    If moves(s) = "5" Then hscrArm3.Value = hscrArm3.Value + 5
	    If moves(s) = "6" Then hscrArm3.Value = hscrArm3.Value - 5
	    If moves(s) = "7" Then hscrClawRotate.Value = hscrClawRotate.Value + 5
	    If moves(s) = "8" Then hscrClawRotate.Value = hscrClawRotate.Value - 5
	    If moves(s) = "9" Then hscrClawOpen.Value = hscrClawOpen.Value + 1
	    If moves(s) = "a" Then hscrClawOpen.Value = hscrClawOpen.Value - 1
	    If moves(s) = "b" Then hscrXTranslate.Value = hscrXTranslate.Value + 1
	    If moves(s) = "c" Then hscrXTranslate.Value = hscrXTranslate.Value - 1
	    If moves(s) = "d" Then hscrZTranslate.Value = hscrZTranslate.Value + 1
	    If moves(s) = "e" Then hscrZTranslate.Value = hscrZTranslate.Value - 1
	    
	    s = s + 1
	    
	    If s > e Then tmrAutoRobot.Enabled = False
	}
	
	private void tmrResetRobot_Timer() {
	    
	    If hscrArm1.Value > 0 And hscrArm1.Value <= 180 Then hscrArm1.Value = hscrArm1.Value - 5
	    If hscrArm1.Value > 0 And hscrArm1.Value > 180 Then hscrArm1.Value = hscrArm1.Value + 5
	    If hscrArm1.Value < 0 And hscrArm1.Value >= -180 Then hscrArm1.Value = hscrArm1.Value + 5
	    If hscrArm1.Value < 0 And hscrArm1.Value < -180 Then hscrArm1.Value = hscrArm1.Value - 5
	    If hscrArm2.Value > 0 Then hscrArm2.Value = hscrArm2.Value - 5
	    If hscrArm2.Value < 0 Then hscrArm2.Value = hscrArm2.Value + 5
	    If hscrArm3.Value > 0 Then hscrArm3.Value = hscrArm3.Value - 5
	    If hscrArm3.Value < 0 Then hscrArm3.Value = hscrArm3.Value + 5
	    If hscrClawOpen.Value > 0 Then hscrClawOpen.Value = hscrClawOpen.Value - 1
	    If hscrClawOpen.Value < 0 Then hscrClawOpen.Value = hscrClawOpen.Value + 1
	    If hscrClawRotate.Value > 90 Then hscrClawRotate.Value = hscrClawRotate.Value - 5
	    If hscrClawRotate.Value < 90 Then hscrClawRotate.Value = hscrClawRotate.Value + 5
	    If hscrXTranslate.Value > 0 Then hscrXTranslate.Value = hscrXTranslate.Value - 1
	    If hscrXTranslate.Value < 0 Then hscrXTranslate.Value = hscrXTranslate.Value + 1
	    If hscrZTranslate.Value > 0 Then hscrZTranslate.Value = hscrZTranslate.Value - 1
	    If hscrZTranslate.Value < 0 Then hscrZTranslate.Value = hscrZTranslate.Value + 1
	    
	    If frmRobot.hscrArm1.Value = 0 _
	        And frmRobot.hscrArm2.Value = 0 _
	        And frmRobot.hscrArm3.Value = 0 _
	        And frmRobot.hscrClawOpen = 0 _
	        And frmRobot.hscrClawRotate = 90 _
	        And frmRobot.hscrXTranslate = 0 _
	        And frmRobot.hscrZTranslate = 0 Then _
	        tmrResetRobot.Enabled = False
	    
	}
	
	private void txtArm1_Change() {
	    
	    If Val(txtArm1) > hscrArm1.Max Then txtArm1 = Val(txtArm1) Mod hscrArm1.Max
	    If Val(txtArm1) < hscrArm1.Min Then txtArm1 = Val(txtArm1) Mod hscrArm1.Min
	    
	    If Val(txtArm1) > 360 Or Val(txtArm1) < -360 Then txtArm1 = Val(txtArm1) Mod 360
	        hscrArm1.Value = Val(txtArm1)
	    If Val(txtArm1) = 360 Or Val(txtArm1) = -360 Then txtArm1 = 0: hscrArm1.Value = 0
	    
	    hscrArm1_Change
	    
	}
	
	private void txtArm2_Change() {
	    
	    
	    If Val(txtArm2) > hscrArm2.Max Then txtArm2 = Val(txtArm2) Mod hscrArm2.Max
	    If Val(txtArm2) < hscrArm2.Min Then txtArm2 = Val(txtArm2) Mod hscrArm2.Min
	    
	    hscrArm2.Value = Val(txtArm2)
	    
	    hscrArm2_Change
	    
	}
	
	private void txtArm3_Change() {

	    If Val(txtArm3) > hscrArm3.Max Then txtArm3 = Val(txtArm3) Mod hscrArm3.Max
	    If Val(txtArm3) < hscrArm3.Min Then txtArm3 = Val(txtArm3) Mod hscrArm3.Min
	    
	    hscrArm3.Value = Val(txtArm3)
	    
	    hscrArm3_Change
	    
	}
	
	private void txtClawRotate_Change() {

	    If Val(txtClawRotate) > hscrClawRotate.Max Then txtClawRotate = Val(txtClawRotate) Mod hscrClawRotate.Max
	    If Val(txtClawRotate) < hscrClawRotate.Min Then txtClawRotate = Val(txtClawRotate) Mod hscrClawRotate.Min
	    
	    hscrClawRotate.Value = Val(txtClawRotate)
	    
	    hscrClawRotate_Change
	    
	}
	
	private void txtOpenClaw_Change() {
	    
	    If Val(txtOpenClaw) * 10 > hscrClawOpen.Max Then txtOpenClaw = (Val(txtOpenClaw) Mod hscrClawOpen.Max) / 10
	    If Val(txtOpenClaw) * 10 < hscrClawOpen.Min Then txtOpenClaw = Abs(Val(txtOpenClaw) Mod hscrClawOpen.Min) / 10
	    
	    hscrClawOpen.Value = Val(txtOpenClaw) * 10
	    
	    hscrClawOpen_Change
	    
	}
	
	private void txtXTranslate_Change() {
	    
	    If Val(txtXTranslate) > hscrXTranslate.Max Then txtXTranslate = Val(txtXTranslate) Mod hscrXTranslate.Max
	    If Val(txtXTranslate) < hscrXTranslate.Min Then txtXTranslate = Val(txtXTranslate) Mod hscrXTranslate.Min
	    
	    hscrXTranslate.Value = Val(txtXTranslate)
	    
	    hscrXTranslate_Change
	    
	}
	
	private void txtZTranslate_Change() {

	    If Val(txtZTranslate) > hscrZTranslate.Max Then txtZTranslate = Val(txtZTranslate) Mod hscrZTranslate.Max
	    If Val(txtZTranslate) < hscrZTranslate.Min Then txtZTranslate = Val(txtZTranslate) Mod hscrZTranslate.Min
	    
	    hscrZTranslate.Value = Val(txtZTranslate)
	    
	    hscrZTranslate_Change
	    
	}
	
	public void get_middle_claw() {

	    middle_claw_x = r.middle_claw_x
	    middle_claw_y = r.middle_claw_y
	    middle_claw_z = r.middle_claw_z
	    
	    // MsgBox middle_claw_z
	    
	}
	
	public void get_middle_box() {

	    For i = 1 To number_boxes
	        middle_box_x(i) = b(i).middle_box_x
	        middle_box_y(i) = b(i).middle_box_y
	        middle_box_z(i) = b(i).middle_box_z
	    Next i
	    
	}
	
	public void getjoints() {

	    For i = 1 To number_boxes                   // start a for-loop from 1 to the number of boxes to be on the screen
	        b(i).joint_x0 = r.joint_x0              // the x-coordinate of the joint(0) of the current box is equal to that of joint(0) of the robot
	        b(i).joint_y0 = r.joint_y0              // the y-coordinate of the joint(0) of the current box is equal to that of joint(0) of the robot
	        b(i).joint_z0 = r.joint_z0              // the z-coordinate of the joint(0) of the current box is equal to that of joint(0) of the robot

	        b(i).joint_x1 = r.joint_x1              // the x-coordinate of the joint(1) of the current box is equal to that of joint(1) of the robot
	        b(i).joint_y1 = r.joint_y1              // the y-coordinate of the joint(1) of the current box is equal to that of joint(1) of the robot
	        b(i).joint_z1 = r.joint_z1              // the z-coordinate of the joint(1) of the current box is equal to that of joint(1) of the robot

	        b(i).joint_x2 = r.joint_x2              // the x-coordinate of the joint(2) of the current box is equal to that of joint(2) of the robot
	        b(i).joint_y2 = r.joint_y2              // the y-coordinate of the joint(2) of the current box is equal to that of joint(2) of the robot
	        b(i).joint_z2 = r.joint_z2              // the z-coordinate of the joint(2) of the current box is equal to that of joint(2) of the robot

	        b(i).joint_x3 = r.joint_x3              // the x-coordinate of the joint(3) of the current box is equal to that of joint(3) of the robot
	        b(i).joint_y3 = r.joint_y3              // the y-coordinate of the joint(3) of the current box is equal to that of joint(3) of the robot
	        b(i).joint_z3 = r.joint_z3              // the z-coordinate of the joint(3) of the current box is equal to that of joint(3) of the robot
	        
	        b(i).draw frmRobot.pctRobot             // call the draw function of the current box, which ByRefs a picturebox, so send frmRobot.pctRobot
	        
	        b(i).givejoints                         // call the givejoints function of the current box
	    
	    Next i                                      // advance the for-loop

	}
	
	public double degrees(double theta) {
		return (theta * (180.0 / 3.1415926538979));
	}
	
	public double radians(double theta) {
		return (theta * (3.1415926538979 / 180.0));
	}
}
