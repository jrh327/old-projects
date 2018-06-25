package net.jonhopkins.robot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

public class Form extends JFrame {
	private static final long serialVersionUID = -6031794944802349379L;
	private JTextField txtNumberBoxes;
	private JScrollBar hscrRotateX;
	private JTextField txtRotateX;
	private JScrollBar hscrRotateY;
	private JTextField txtRotateY;
	private JScrollBar hscrXTranslate;
	private JScrollBar hscrZTranslate;
	private JTextField txtXTranslate;
	private JTextField txtZTranslate;
	private JButton cmdOpenRoute;
	private JButton cmdSaveRoute;
	private JButton cmdAutoRobot;
	private JButton cmdClearCurrentRoute;
	private JButton cmdNewRoute;
	private JCheckBox chkMoves;
	private JButton cmdResetRobot;
	private Timer tmrResetRobot;
	private Timer tmrAutoRobot;
	private JTextField txtOpenClaw;
	private JTextField txtClawRotate;
	private JTextField txtArm3;
	private JTextField txtArm2;
	private JScrollBar hscrClawOpen;
	private JScrollBar hscrClawRotate;
	private JScrollBar hscrArm3;
	private JScrollBar hscrArm2;
	private JScrollBar hscrArm1;
	private JTextField txtArm1;
	private JButton cmdDrawRobot;
	private JPanel renderArea;
	private Image buffer;
	private Graphics pctRobot;
	private JLabel Label1;
	private JLabel Label24;
	private JLabel Label23;
	private JLabel Label19;
	private JLabel Label22;
	private JLabel Label21;
	private JLabel Label20;
	private JLabel Label18;
	private JLabel Label17;
	private JLabel Label15;
	private JLabel Label14;
	private JLabel lblRouteName;
	private JLabel Label13;
	private JLabel Label12;
	private JLabel Label11;
	private JLabel Label10;
	private JLabel Label9;
	private JLabel Label8;
	private JLabel Label7;
	private JLabel Label6;
	private JLabel Label5;
	private JLabel Label4;
	private JLabel Label3;
	private JLabel Label2;
	private JLabel Label16;
	
	private final int RENDER_WIDTH = 800;
	private final int RENDER_HEIGHT = 800;
	
	private Robot r;
	private Box[] b = new Box[10];
	
	/**
	 * Array of strings for each of the 1000 allowed moves in a route
	 */
	private String[] moves = new String[1000];
	
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
	private int number_moves;
	
	/**
	 * number of boxes on the screen
	 */
	private int number_boxes;
	
	/**
	 * starting variable for the timer that moves the robot through a route
	 */
	private int s;
	
	/**
	 * ending variables for the timer that moves the robot through a route
	 */
	private int e;
	
	/**
	 * x-coordinate of the mouse on the screen
	 */
	private int mousex;
	
	/**
	 * y-coordinate of the mouse on the screen
	 */
	private int mousey;
	
	/**
	 * x-coordinate of the middle of the robot's claw
	 */
	private double middle_claw_x;
	
	/**
	 * y-coordinate of the middle of the robot's claw
	 */
	private double middle_claw_y;
	
	/**
	 * z-coordinate of the middle of the robot's claw
	 */
	private double middle_claw_z;
	
	/**
	 * x-coordinate of the middle of a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	private double[] middle_box_x = new double[10];
	
	/**
	 * y-coordinate of the middle of a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	private double[] middle_box_y = new double[10];
	
	/**
	 * z-coordinate of the middle of a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	private double[] middle_box_z = new double[10];
	
	/**
	 * boolean to tell the robot if it is holding a box, array of 10, one for each of the 10 possible boxes on the screen
	 */
	private boolean[] holding = new boolean[10];
	
	public static void main(String[] args) {
		new Form().init();
	}
	
	public Form() {
		r = new Robot();
		for (int i = 0; i < b.length; i++) {
			b[i] = new Box();
		}
	}
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpComponents();
				setUpWindow();
			}
		});
	}
	
	public void setUpWindow() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				Form_Unload();
			}
		});
		
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}
	
	public void setUpComponents() {
		txtNumberBoxes = new JTextField("5");
		txtNumberBoxes.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumberBoxes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtNumberBoxes_Change();
			}
		});
		
		hscrRotateX = new JScrollBar(SwingConstants.HORIZONTAL, 0, 20, 0, 75);
		hscrRotateX.setUnitIncrement(5);
		hscrRotateX.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrRotateX_Change();
			}
		});
		
		txtRotateX = new JTextField("0");
		txtRotateX.setHorizontalAlignment(SwingConstants.RIGHT);
		
		hscrRotateY = new JScrollBar(SwingConstants.HORIZONTAL, 0, 20, 0, 360);
		hscrRotateY.setUnitIncrement(5);
		hscrRotateY.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrRotateY_Change();
			}
		});
		
		txtRotateY = new JTextField("0");
		txtRotateY.setHorizontalAlignment(SwingConstants.RIGHT);
		txtRotateY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtRotateY_Change();
			}
		});
		
		hscrXTranslate = new JScrollBar(SwingConstants.HORIZONTAL, 0, 0, -15, 15);
		hscrXTranslate.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrXTranslate_Change();
			}
		});
		
		hscrZTranslate = new JScrollBar(SwingConstants.HORIZONTAL, 0, 0, -20, 20);
		hscrZTranslate.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrZTranslate_Change();
			}
		});
		
		txtXTranslate = new JTextField("0");
		txtXTranslate.setHorizontalAlignment(SwingConstants.RIGHT);
		txtXTranslate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtXTranslate_Change();
			}
		});
		
		txtZTranslate = new JTextField("0");
		txtZTranslate.setHorizontalAlignment(SwingConstants.RIGHT);
		txtZTranslate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtZTranslate_Change();
			}
		});
		
		cmdOpenRoute = new JButton("Open");
		cmdOpenRoute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdOpenRoute_Click();
			}
		});
		
		cmdSaveRoute = new JButton("Save");
		cmdSaveRoute.setEnabled(false);
		cmdSaveRoute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdSaveRoute_Click();
			}
		});
		
		cmdAutoRobot = new JButton("AutoGo");
		cmdAutoRobot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdAutoRobot_Click();
			}
		});
		
		cmdClearCurrentRoute = new JButton("Clear Route");
		cmdClearCurrentRoute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdClearCurrentRoute_Click();
			}
		});
		
		cmdNewRoute = new JButton("New Route");
		cmdNewRoute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdNewRoute_Click();
			}
		});
		
		chkMoves = new JCheckBox("Teach Route");
		chkMoves.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				chkMoves_Click();
			}
		});
		
		cmdResetRobot = new JButton("Reset");
		cmdResetRobot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdResetRobot_Click();
			}
		});
		
		tmrResetRobot = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmrResetRobot_Timer();
			}
		});
		tmrAutoRobot = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmrAutoRobot_Timer();
			}
		});
		
		txtOpenClaw = new JTextField("0");
		txtOpenClaw.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOpenClaw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtOpenClaw_Change();
			}
		});
		
		txtClawRotate = new JTextField("90");
		txtClawRotate.setHorizontalAlignment(SwingConstants.RIGHT);
		txtClawRotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtClawRotate_Change();
			}
		});
		
		txtArm3 = new JTextField("0");
		txtArm3.setHorizontalAlignment(SwingConstants.RIGHT);
		txtArm3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtArm3_Change();
			}
		});
		
		txtArm2 = new JTextField("0");
		txtArm2.setHorizontalAlignment(SwingConstants.RIGHT);
		txtArm2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtArm2_Change();
			}
		});
		
		hscrClawOpen = new JScrollBar(SwingConstants.HORIZONTAL, 0, 0, 0, 6);
		hscrClawOpen.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrClawOpen_Change();
			}
		});
		
		hscrClawRotate = new JScrollBar(SwingConstants.HORIZONTAL, 0, 5, 0, 180);
		hscrClawRotate.setUnitIncrement(5);
		hscrClawRotate.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrClawRotate_Change();
			}
		});
		
		hscrArm3 = new JScrollBar(SwingConstants.HORIZONTAL, 0, 5, -20, 200);
		hscrArm3.setUnitIncrement(5);
		hscrArm3.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrArm3_Change();
			}
		});
		
		hscrArm2 = new JScrollBar(SwingConstants.HORIZONTAL, 0, 5, 0, 180);
		hscrArm2.setUnitIncrement(5);
		hscrArm2.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrArm2_Change();
			}
		});
		
		hscrArm1 = new JScrollBar(SwingConstants.HORIZONTAL, 0, 5, -360, 360);
		hscrArm1.setUnitIncrement(5);
		hscrArm1.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hscrArm1_Change();
			}
		});
		
		txtArm1 = new JTextField("0");
		txtArm1.setHorizontalAlignment(SwingConstants.RIGHT);
		txtArm1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtArm1_Change();
			}
		});
		
		cmdDrawRobot = new JButton("Drawz :D");
		cmdDrawRobot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdDrawRobot_Click();
			}
		});
		
		renderArea = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				if (buffer == null) {
					buffer = renderArea.createImage(RENDER_WIDTH, RENDER_HEIGHT);
					pctRobot = buffer.getGraphics();
				}
				synchronized (buffer) {
					g.drawImage(buffer, 0, 0, this);
				}
			}
		};
		renderArea.setPreferredSize(new Dimension(RENDER_WIDTH, RENDER_HEIGHT));
		renderArea.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				pctRobot_MouseDown(e.getButton(), e.getModifiers(), e.getX(), e.getY());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		});
		renderArea.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				pctRobot_MouseMove(e.getButton(), e.getModifiers(), e.getX(), e.getY());
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
		});
		
		Label1 = new JLabel();
		Label1.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label24 = new JLabel("box middle");
		Label24.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label23 = new JLabel("claw middle");
		
		Label19 = new JLabel("Number of boxes");
		Label19.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label22 = new JLabel("Camera Rotation");
		Label22.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label21 = new JLabel("X: ");
		Label21.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label20 = new JLabel("Y: ");
		Label20.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label18 = new JLabel();
		Label18.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label17 = new JLabel();
		Label17.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label15 = new JLabel("X-Translate");
		Label15.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label14 = new JLabel("Z-Translate");
		Label14.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblRouteName = new JLabel("");
		lblRouteName.setHorizontalAlignment(SwingConstants.CENTER);
		lblRouteName.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		Label13 = new JLabel("Route Name:");
		Label13.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label12 = new JLabel("Open/Close");
		Label12.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label11 = new JLabel("Rotate");
		Label11.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label10 = new JLabel("Claw");
		Label10.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label9 = new JLabel("Arm3");
		Label9.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label8 = new JLabel("Arm2");
		Label8.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label7 = new JLabel("Arm1");
		Label7.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label6 = new JLabel("z");
		Label6.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label5 = new JLabel("y");
		Label5.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label4 = new JLabel("x");
		Label4.setHorizontalAlignment(SwingConstants.CENTER);
		
		Label3 = new JLabel();
		Label3.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label2 = new JLabel();
		Label2.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Label16 = new JLabel();
		Label16.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel bottomControls = new JPanel(new FlowLayout());
		
		JPanel mids = new JPanel(new GridLayout(3, 4));
		// row 1
		mids.add(new JPanel());
		mids.add(Label4);
		mids.add(Label5);
		mids.add(Label6);
		// row 2
		mids.add(Label23);
		mids.add(Label1);
		mids.add(Label2);
		mids.add(Label3);
		// row 3
		mids.add(Label24);
		mids.add(Label16);
		mids.add(Label17);
		mids.add(Label18);
		bottomControls.add(mids);
		
		JPanel camera = new JPanel(new BorderLayout());
		JPanel cameraControls = new JPanel(new GridLayout(2, 4));
		// row 1
		cameraControls.add(Label21);
		cameraControls.add(hscrRotateX);
		cameraControls.add(Label20);
		cameraControls.add(hscrRotateY);
		// row 2
		cameraControls.add(new JPanel());
		cameraControls.add(txtRotateX);
		cameraControls.add(new JPanel());
		cameraControls.add(txtRotateY);
		
		camera.add(Label22, BorderLayout.NORTH);
		camera.add(cameraControls, BorderLayout.CENTER);
		bottomControls.add(camera);
		
		JPanel boxes = new JPanel(new GridLayout(3, 1));
		boxes.add(Label19);
		boxes.add(txtNumberBoxes);
		boxes.add(new JPanel());
		bottomControls.add(boxes);
		
		JPanel renderPanel = new JPanel(new BorderLayout());
		renderPanel.add(renderArea, BorderLayout.CENTER);
		renderPanel.add(bottomControls, BorderLayout.SOUTH);
		
		JPanel sideControls = new JPanel(new GridLayout(0, 1));
		sideControls.add(cmdDrawRobot);
		sideControls.add(chkMoves);
		sideControls.add(Label13);
		sideControls.add(lblRouteName);
		
		JPanel routes1 = new JPanel(new GridLayout(1, 2));
		routes1.add(cmdNewRoute);
		routes1.add(cmdClearCurrentRoute);
		sideControls.add(routes1);
		
		JPanel routes2 = new JPanel(new GridLayout(1, 2));
		routes2.add(cmdSaveRoute);
		routes2.add(cmdOpenRoute);
		sideControls.add(routes2);
		sideControls.add(cmdResetRobot);
		sideControls.add(cmdAutoRobot);
		
		sideControls.add(Label15);
		sideControls.add(hscrXTranslate);
		sideControls.add(txtXTranslate);
		
		sideControls.add(Label14);
		sideControls.add(hscrZTranslate);
		sideControls.add(txtZTranslate);
		
		sideControls.add(Label7);
		sideControls.add(hscrArm1);
		sideControls.add(txtArm1);
		
		sideControls.add(Label8);
		sideControls.add(hscrArm2);
		sideControls.add(txtArm2);
		
		sideControls.add(Label9);
		sideControls.add(hscrArm3);
		sideControls.add(txtArm3);
		
		sideControls.add(Label10);
		sideControls.add(Label11);
		sideControls.add(hscrClawRotate);
		sideControls.add(txtClawRotate);
		
		sideControls.add(Label12);
		sideControls.add(hscrClawOpen);
		sideControls.add(txtOpenClaw);
		
		setLayout(new FlowLayout());
		add(renderPanel);
		add(sideControls);
	}
	
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
				if (yesNo("Would you like to create a new route?")) { // if the user selects "Yes"
					cmdNewRoute_Click(); // go to the function to create a new route
				} else { // if the user selects "No"
					// ask if user wants to load an existing route
					if (yesNo("Would you like to open an existing route?")) { // if the user selects "Yes"
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
			// open the file for that route, and close the file without adding anything, which clears the file
			try {
				new FileWriter(getPath(lblRouteName.getText() + ".txt")).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
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
		
		repaint();
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
		String route = "";
		
		if (!lblRouteName.getText().isEmpty()) {
			if (yesNo("Would you like to save your current route?")) {
				for (int i = 0; i < number_moves; i++) {
					route = route + moves[i];
				}
				
				Save_route(route);
			}
		}
		
		route_name = input("What would you like the new route to be named?");
		
		do {
			String filename = getPath(route_name + ".txt");
			if (fileExists(filename)) {
				if (yesNo("This route already exists. Overwrite?")) {
					try {
						new FileWriter(filename).close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				} else {
					if (yesNo("Create a different route?")) {
						route_name = input("Route name:");
					}
				}
			}
		} while (fileExists(getPath(route_name + ".txt")));
		
		for (int i = 0; i < number_moves; i++) {
			moves[i] = "";
		}
		
		number_moves = 0;
		
		lblRouteName.setText(route_name);
		lblRouteName_Change();
		cmdSaveRoute.setEnabled(true);
	}
	
	private void cmdOpenRoute_Click() {
		String route_name;
		String listRoutes = "";
		
		String filename = getPath("RouteList.txt");
		if (!fileExists(filename)) {
			try {
				new FileWriter(filename).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			listRoutes = String.join("\n", Files.readAllLines(new File(filename).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		route_name = input("Which route would you like to open?\nRoutes: \n" + listRoutes);
		
		if (route_name.isEmpty()) {
			alert("No route selected.");
			return;
		}
		
		for (int i = 0; i < number_moves; i++) {
			moves[i] = "";
		}
		
		number_moves = 0;
		
		Open_route(route_name);
		cmdSaveRoute.setEnabled(true);
		
		cmdAutoRobot.grabFocus();
	}
	
	private void Save_route(String route) {
		String filename = getPath(lblRouteName.getText() + ".txt");
		if (!fileExists(filename)) {
			try {
				FileWriter writer = new FileWriter(getPath("RouteList.txt"));
				writer.append(lblRouteName.getText());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(route);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Open_route(String route_name) {
		String move_type;
		String filename;
		String routelist = "";
		
		filename = getPath(route_name + ".txt");
		if (!fileExists(filename)) {
			if (yesNo("File does not exist! Would you like to create the route?")) {
				lblRouteName.setText(route_name);
				lblRouteName_Change();
				cmdSaveRoute_Click();
			}
			return;
		}
		
		try {
			routelist = new String(Files.readAllBytes(new File(filename).toPath())).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		number_moves = routelist.length();
		for (int i = 0; i < number_moves; i++) {
			move_type = routelist.substring(i, 1);
			moves[i] = move_type;
		}
		
		lblRouteName.setText(route_name);
		lblRouteName_Change();
	}
	
	private void cmdResetRobot_Click() {
		if (yesNo("Save route?")) {
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
		
		// alert(route);
		
		Save_route(route);
	}
	
	private void Form_Unload() {
		if (!lblRouteName.getText().isEmpty() && yesNo("Save current route?")) {
			cmdSaveRoute_Click();
		}
		
		dispose();
		System.exit(0);
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.super_rotate(pctRobot, 1, hscrArm1.getValue(), 0, 0, 0, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 1, hscrArm1.getValue() - Integer.parseInt(txtArm1.getText()), 0, 0, 0, 0);
			}
		}
		
		txtArm1.setText(String.valueOf(hscrArm1.getValue()));
		
		repaint();
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.super_rotate(pctRobot, 2, 0, hscrArm2.getValue(), 0, 0, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 2, 0, hscrArm2.getValue() - Integer.parseInt(txtArm2.getText()), 0, 0, 0);
			}
		}
		
		txtArm2.setText(String.valueOf(hscrArm2.getValue()));
		
		repaint();
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.super_rotate(pctRobot, 3, 0, 0, hscrArm3.getValue(), 0, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 3, 0, 0, hscrArm3.getValue() - Integer.parseInt(txtArm3.getText()), 0, 0);
			}
		}
		
		txtArm3.setText(String.valueOf(hscrArm3.getValue()));
		
		repaint();
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.super_rotate(pctRobot, 4, 0, 0, 0,  hscrClawRotate.getValue() - 90, 0);
		
		getjoints();
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].super_rotate(pctRobot, 4, 0, 0, 0, hscrClawRotate.getValue() - Integer.parseInt(txtClawRotate.getText()), 0);
			}
		}
		
		txtClawRotate.setText(String.valueOf(hscrClawRotate.getValue()));
		
		repaint();
	}
	
	private void hscrClawOpen_Change() {
		if (hscrClawOpen.getValue() < (Integer.parseInt(txtOpenClaw.getText()) * 10)) {
			for (int i = 0; i < number_boxes; i++) {
				if (holding[i]) {
					hscrClawOpen.setValue(hscrClawOpen.getValue() + hscrClawOpen.getUnitIncrement());
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
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
					alert("Holding a box");
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
		
		repaint();
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.translate_x(pctRobot, hscrXTranslate.getValue() - Integer.parseInt(txtXTranslate.getText()));
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].translate_x(pctRobot, hscrXTranslate.getValue() - Integer.parseInt(txtXTranslate.getText()));
			}
		}
		
		getjoints();
		
		txtXTranslate.setText(String.valueOf(hscrXTranslate.getValue()));
		
		repaint();
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
		
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.translate_z(pctRobot, hscrZTranslate.getValue() - Integer.parseInt(txtZTranslate.getText()));
		
		for (int i = 0; i < number_boxes; i++) {
			if (holding[i]) {
				b[i].translate_z(pctRobot, hscrZTranslate.getValue() - Integer.parseInt(txtZTranslate.getText()));
			}
		}
		
		getjoints();
		
		txtZTranslate.setText(String.valueOf(hscrZTranslate.getValue()));
		
		repaint();
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
		
		repaint();
	}
	
	private void hscrRotateX_Change() {
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.camera_rotate(pctRobot, -hscrRotateX.getValue(), 0);
		
		for (int i = 0; i < number_boxes; i++) {
			b[i].camera_rotate(pctRobot, -hscrRotateX.getValue(), 0);
		}
		
		txtRotateX.setText(String.valueOf(hscrRotateX.getValue()));
		
		repaint();
	}
	
	private void hscrRotateY_Change() {
		pctRobot.clearRect(0, 0, RENDER_WIDTH, RENDER_HEIGHT);
		
		r.camera_rotate(pctRobot, 0, hscrRotateY.getValue());
		
		for (int i = 0; i < number_boxes; i++) {
			b[i].camera_rotate(pctRobot, 0, hscrRotateY.getValue());
		}
		
		txtRotateY.setText(String.valueOf(hscrRotateY.getValue()));
		
		repaint();
	}
	
	private void txtNumberBoxes_Change() {
		if (Integer.parseInt(txtNumberBoxes.getText()) < 1
				|| Integer.parseInt(txtNumberBoxes.getText()) > 10) {
			txtNumberBoxes.setText(String.valueOf(number_boxes));
			
			repaint();
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
		
		repaint();
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
		
		repaint();
	}
	
	private void tmrResetRobot_Timer() {
		if (hscrArm1.getValue() > 0 && hscrArm1.getValue() <= 180) {
			int newVal = hscrArm1.getValue() - hscrArm1.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrArm1.setValue(newVal);
		}
		if (hscrArm1.getValue() > 0 && hscrArm1.getValue() > 180) {
			int newVal = hscrArm1.getValue() + hscrArm1.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrArm1.setValue(newVal);
		}
		if (hscrArm1.getValue() < 0 && hscrArm1.getValue() >= -180) {
			int newVal = hscrArm1.getValue() + hscrArm1.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrArm1.setValue(newVal);
		}
		if (hscrArm1.getValue() < 0 && hscrArm1.getValue() < -180) {
			int newVal = hscrArm1.getValue() - hscrArm1.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrArm1.setValue(newVal);
		}
		if (hscrArm2.getValue() > 0) {
			int newVal = hscrArm2.getValue() - hscrArm2.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrArm2.setValue(newVal);
		}
		if (hscrArm2.getValue() < 0) {
			int newVal = hscrArm2.getValue() + hscrArm2.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrArm2.setValue(newVal);
		}
		if (hscrArm3.getValue() > 0) {
			int newVal = hscrArm3.getValue() - hscrArm3.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrArm3.setValue(newVal);
		}
		if (hscrArm3.getValue() < 0) {
			int newVal = hscrArm3.getValue() + hscrArm3.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrArm3.setValue(newVal);
		}
		if (hscrClawOpen.getValue() > 0) {
			int newVal = hscrClawOpen.getValue() - hscrClawOpen.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrClawOpen.setValue(newVal);
		}
		if (hscrClawOpen.getValue() < 0) {
			int newVal = hscrClawOpen.getValue() + hscrClawOpen.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrClawOpen.setValue(newVal);
		}
		if (hscrClawRotate.getValue() > 90) {
			int newVal = hscrClawRotate.getValue() - hscrClawRotate.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrClawRotate.setValue(newVal);
		}
		if (hscrClawRotate.getValue() < 90) {
			int newVal = hscrClawRotate.getValue() + hscrClawRotate.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrClawRotate.setValue(newVal);
		}
		if (hscrXTranslate.getValue() > 0) {
			int newVal = hscrXTranslate.getValue() - hscrXTranslate.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrXTranslate.setValue(newVal);
		}
		if (hscrXTranslate.getValue() < 0) {
			int newVal = hscrXTranslate.getValue() + hscrXTranslate.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrXTranslate.setValue(newVal);
		}
		if (hscrZTranslate.getValue() > 0) {
			int newVal = hscrZTranslate.getValue() - hscrZTranslate.getBlockIncrement();
			if (newVal < 0) {
				newVal = 0;
			}
			hscrZTranslate.setValue(newVal);
		}
		if (hscrZTranslate.getValue() < 0) {
			int newVal = hscrZTranslate.getValue() + hscrZTranslate.getBlockIncrement();
			if (newVal > 0) {
				newVal = 0;
			}
			hscrZTranslate.setValue(newVal);
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
		
		repaint();
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
	
	private void alert(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	private boolean yesNo(String message) {
		int result = JOptionPane.showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);
		return (result == JOptionPane.YES_OPTION);
	}
	
	private String input(String message) {
		String response = JOptionPane.showInputDialog(message);
		return response == null ? "" : response;
	}
	
	private boolean fileExists(String filepath) {
		return new File(filepath).exists();
	}
	
	private String getPath(String filename) {
		return System.getProperty("user.dir") + "/" + filename;
	}
}
