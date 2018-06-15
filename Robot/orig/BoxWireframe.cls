VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "BoxWireframe"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False
'Jon Hopkins

Private Type point_c
    x As Single
    y As Single
    z As Single
End Type

Private Type line
    p1 As Integer
    p2 As Integer
    
    r As Integer
    g As Integer
    b As Integer
End Type

Private vertex(2000) As point_c
Private joint(5) As point_c
Private lines(6000) As line

Private vertex_count As Integer
Private line_count As Integer

Private rotate_factorX As Single
Public rotate_factorY As Single
Private rotate_factorZ As Single
Private rotate_factorX2 As Single
Private rotate_factorY2 As Single
Private rotate_factorZ2 As Single
Private rotate_factorX3 As Single
Private rotate_factorY3 As Single
Private rotate_factorZ3 As Single

Private camera_x As Single
Private camera_y As Single

Public middle_box_x As Single
Public middle_box_y As Single
Public middle_box_z As Single

Public joint_x0 As Single
Public joint_y0 As Single
Public joint_z0 As Single
Public joint_x1 As Single
Public joint_y1 As Single
Public joint_z1 As Single
Public joint_x2 As Single
Public joint_y2 As Single
Public joint_z2 As Single
Public joint_x3 As Single
Public joint_y3 As Single
Public joint_z3 As Single

Public Sub initialize()

    Dim bx, bz As Single

    vertex_count = 0
    line_count = 0
    
    rotate_factorX = 0
    rotate_factorY = 0
    rotate_factorZ = 0
    camera_x = 0
    camera_y = 0
    
    bx = (Int((Rnd * 30) * 10) / 10) - 15
    bz = (Int((Rnd * 30) * 10) / 10) - 15

    vertex(1).x = bx: vertex(1).y = -1.4: vertex(1).z = bz
    vertex(2).x = bx - 1: vertex(2).y = -1.4: vertex(2).z = bz
    vertex(3).x = bx - 1: vertex(3).y = -1.4: vertex(3).z = bz - 1
    vertex(4).x = bx: vertex(4).y = -1.4: vertex(4).z = bz - 1

    vertex(5).x = bx: vertex(5).y = -0.4: vertex(5).z = bz
    vertex(6).x = bx - 1: vertex(6).y = -0.4: vertex(6).z = bz
    vertex(7).x = bx - 1: vertex(7).y = -0.4: vertex(7).z = bz - 1
    vertex(8).x = bx: vertex(8).y = -0.4: vertex(8).z = bz - 1
    
    lines(1).p1 = 1: lines(1).p2 = 2
    lines(2).p1 = 2: lines(2).p2 = 3
    lines(3).p1 = 3: lines(3).p2 = 4
    lines(4).p1 = 4: lines(4).p2 = 1
    
    lines(5).p1 = 5: lines(5).p2 = 6
    lines(6).p1 = 6: lines(6).p2 = 7
    lines(7).p1 = 7: lines(7).p2 = 8
    lines(8).p1 = 8: lines(8).p2 = 5
    
    lines(9).p1 = 1: lines(9).p2 = 5
    lines(10).p1 = 2: lines(10).p2 = 6
    lines(11).p1 = 3: lines(11).p2 = 7
    lines(12).p1 = 4: lines(12).p2 = 8
    
    vertex_count = 8
    line_count = 12
    
    rotate_factorX = 0
    rotate_factorY = 0
    rotate_factorZ = 0
    rotate_factorX2 = 0
    rotate_factorY2 = 0
    rotate_factorZ2 = -90
    rotate_factorX3 = 0
    rotate_factorY3 = 0
    rotate_factorZ3 = 0
    camera_x = 0
    camera_y = 0

End Sub
Public Function initialize_joints()
    
    joint(0).x = 0: joint(0).y = 0: joint(0).z = 0
    joint(1).x = 0: joint(1).y = 5.4: joint(1).z = 0
    joint(2).x = 7.8: joint(2).y = 5.4: joint(2).z = 0
    joint(3).x = 7.8: joint(3).y = -0.3: joint(3).z = 0
    
End Function
Public Function givejoints()

    joint(0).x = joint_x0: joint(0).y = joint_y0: joint(0).z = joint_z0
    joint(1).x = joint_x1: joint(1).y = joint_y1: joint(1).z = joint_z1
    joint(2).x = joint_x2: joint(2).y = joint_y2: joint(2).z = joint_z2
    joint(3).x = joint_x3: joint(3).y = joint_y3: joint(3).z = joint_z3
    joint(4).x = joint_x4: joint(4).y = joint_y4: joint(4).z = joint_z4

End Function
Public Function draw(ByRef pct As PictureBox)
    
    Dim plotx, ploty As Single
    Dim plotx2, ploty2 As Single
    
    pixelx = pct.ScaleWidth / 30
    pixely = pct.ScaleHeight / 30
    
    originx = pct.ScaleWidth / 2
    originy = pct.ScaleHeight / 8 * 5
    
    For i = 1 To line_count
        
        plotx = originx + (vertex(lines(i).p1).x * (50 / (50 + vertex(lines(i).p1).z))) * pixelx ' * 0.8
        ploty = originy - (vertex(lines(i).p1).y * (50 / (50 + vertex(lines(i).p1).z))) * pixely ' * 0.8
        plotx2 = originx + (vertex(lines(i).p2).x * (50 / (50 + vertex(lines(i).p2).z))) * pixelx ' * 0.8
        ploty2 = originy - (vertex(lines(i).p2).y * (50 / (50 + vertex(lines(i).p2).z))) * pixely ' * 0.8
        
        pct.Line (plotx, ploty)-(plotx2, ploty2) ', RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255)) ', RGB(lines(i).r, lines(i).g, lines(i).b)
    
    Next i
    
    middle_box_x = (vertex(1).x + vertex(2).x + vertex(3).x + vertex(4).x + vertex(5).x + vertex(6).x + vertex(7).x + vertex(8).x) / 8
    middle_box_y = (vertex(1).y + vertex(2).y + vertex(3).y + vertex(4).y + vertex(5).y + vertex(6).y + vertex(7).y + vertex(8).y) / 8
    middle_box_z = (vertex(1).z + vertex(2).z + vertex(3).z + vertex(4).z + vertex(5).z + vertex(6).z + vertex(7).z + vertex(8).z) / 8
    
    frmRobot.Label16 = Format(middle_box_x, "##.##")
    frmRobot.Label17 = Format(middle_box_y, "##.##")
    frmRobot.Label18 = Format(middle_box_z, "##.##")

End Function
Public Function rotate_with_arm1(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 1 To vertex_count
    
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(theta)) - tempx * Sin(radians(theta))
        vertex(i).x = tempz * Sin(radians(theta)) + tempx * Cos(radians(theta))
        
    Next i
    For i = 1 To 3
        tempx = joint(i).x: tempz = joint(i).z
        
        joint(i).z = tempz * Cos(radians(theta)) - tempx * Sin(radians(theta))
        joint(i).x = tempz * Sin(radians(theta)) + tempx * Cos(radians(theta))
    Next i
    
    rotate_factorY = theta
        
End Function
Public Function rotate_with_arm2(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single

    For i = 1 To vertex_count
        tempx = vertex(i).x - joint(1).x: tempy = vertex(i).y - joint(1).y
        
        vertex(i).x = tempx * Cos(radians(theta)) - tempy * Sin(radians(theta)) + joint(1).x
        vertex(i).y = tempx * Sin(radians(theta)) + tempy * Cos(radians(theta)) + joint(1).y
    
    Next i
    
    For i = 2 To 3
        tempx = joint(i).x - joint(1).x: tempy = joint(i).y - joint(1).y
   
        joint(i).x = tempx * Cos(radians(theta)) - tempy * Sin(radians(theta)) + joint(1).x
        joint(i).y = tempx * Sin(radians(theta)) + tempy * Cos(radians(theta)) + joint(1).y

    Next i
    
    rotate_factorZ = theta
    
End Function
Public Function rotate_with_arm3(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 1 To vertex_count
        tempx = vertex(i).x - joint(2).x: tempy = vertex(i).y - joint(2).y
        
        vertex(i).x = tempx * Cos(radians(theta)) - tempy * Sin(radians(theta)) + joint(2).x
        vertex(i).y = tempx * Sin(radians(theta)) + tempy * Cos(radians(theta)) + joint(2).y
    
    Next i
    
        tempx = joint(3).x - joint(2).x: tempy = joint(3).y - joint(2).y
   
        joint(3).x = tempx * Cos(radians(theta)) - tempy * Sin(radians(theta)) + joint(2).x
        joint(3).y = tempx * Sin(radians(theta)) + tempy * Cos(radians(theta)) + joint(2).y
    
    rotate_factorZ2 = theta

End Function
Public Function rotate_with_claw(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 1 To vertex_count
    
        tempy = vertex(i).y - joint(3).y: tempz = vertex(i).z - joint(3).z
        
        vertex(i).y = tempy * Cos(radians(theta)) - tempz * Sin(radians(theta)) + joint(3).y
        vertex(i).z = tempy * Sin(radians(theta)) + tempz * Cos(radians(theta)) + joint(3).z
    Next i

    rotate_factorY3 = theta

End Function
Public Function translate_x(ByRef pct As PictureBox, x_change As Integer)
    
    'undo camera rotation
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(-camera_x)) - tempz * Sin(radians(-camera_x))
        vertex(i).z = tempy * Sin(radians(-camera_x)) + tempz * Cos(radians(-camera_x))
    Next i
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(-camera_y)) - tempx * Sin(radians(-camera_y))
        vertex(i).x = tempz * Sin(radians(-camera_y)) + tempx * Cos(radians(-camera_y))
    Next i
    
    'translate
    For i = 0 To vertex_count
        vertex(i).x = vertex(i).x + x_change
        If i < 4 Then joint(i).x = joint(i).x + x_change
    Next i
    
    'redo camera rotation
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(camera_y)) - tempx * Sin(radians(camera_y))
        vertex(i).x = tempz * Sin(radians(camera_y)) + tempx * Cos(radians(camera_y))
    Next i
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(camera_x)) - tempz * Sin(radians(camera_x))
        vertex(i).z = tempy * Sin(radians(camera_x)) + tempz * Cos(radians(camera_x))
    Next i
    
    draw pct
    
End Function
Public Function translate_z(ByRef pct As PictureBox, z_change As Integer)

    'undo camera rotation
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(-camera_x)) - tempz * Sin(radians(-camera_x))
        vertex(i).z = tempy * Sin(radians(-camera_x)) + tempz * Cos(radians(-camera_x))
    Next i
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(-camera_y)) - tempx * Sin(radians(-camera_y))
        vertex(i).x = tempz * Sin(radians(-camera_y)) + tempx * Cos(radians(-camera_y))
    Next i
    
    'translate
    For i = 0 To vertex_count
        vertex(i).z = vertex(i).z + z_change
        If i < 4 Then joint(i).z = joint(i).z + z_change
    Next
    
    'redo camera rotation
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(camera_y)) - tempx * Sin(radians(camera_y))
        vertex(i).x = tempz * Sin(radians(camera_y)) + tempx * Cos(radians(camera_y))
    Next i
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(camera_x)) - tempz * Sin(radians(camera_x))
        vertex(i).z = tempy * Sin(radians(camera_x)) + tempz * Cos(radians(camera_x))
    Next i
    
    draw pct
    
End Function
Public Function drop()
        
    'undo camera rotation
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(-camera_x)) - tempz * Sin(radians(-camera_x))
        vertex(i).z = tempy * Sin(radians(-camera_x)) + tempz * Cos(radians(-camera_x))
    Next i
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(-camera_y)) - tempx * Sin(radians(-camera_y))
        vertex(i).x = tempz * Sin(radians(-camera_y)) + tempx * Cos(radians(-camera_y))
    Next i
    
    'if the box is above the ground, move its location to the ground
    If vertex(1).y > -1.4 Then vertex(1).y = -1.4
    
    bx = vertex(1).x: bz = vertex(1).z
    
    vertex(1).x = bx: vertex(1).y = -1.4: vertex(1).z = bz
    vertex(2).x = bx - 1: vertex(2).y = -1.4: vertex(2).z = bz
    vertex(3).x = bx - 1: vertex(3).y = -1.4: vertex(3).z = bz - 1
    vertex(4).x = bx: vertex(4).y = -1.4: vertex(4).z = bz - 1

    vertex(5).x = bx: vertex(5).y = -0.4: vertex(5).z = bz
    vertex(6).x = bx - 1: vertex(6).y = -0.4: vertex(6).z = bz
    vertex(7).x = bx - 1: vertex(7).y = -0.4: vertex(7).z = bz - 1
    vertex(8).x = bx: vertex(8).y = -0.4: vertex(8).z = bz - 1
    
    'redo camera rotation
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(camera_y)) - tempx * Sin(radians(camera_y))
        vertex(i).x = tempz * Sin(radians(camera_y)) + tempx * Cos(radians(camera_y))
    Next i
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(camera_x)) - tempz * Sin(radians(camera_x))
        vertex(i).z = tempy * Sin(radians(camera_x)) + tempz * Cos(radians(camera_x))
    Next i
    
End Function
Public Function camera_rotate(ByRef pct As PictureBox, x_theta, y_theta As Integer)
    
    Dim tempy, tempz As Single
    
    '__________________________________
    '*******Rotate about x-axis********
    '----------------------------------
    
    'x-rotation
    If x_theta <> 0 Then
        For i = 1 To vertex_count
            tempy = vertex(i).y: tempz = vertex(i).z
            
            vertex(i).y = tempy * Cos(radians(x_theta - camera_x)) - tempz * Sin(radians(x_theta - camera_x))
            vertex(i).z = tempy * Sin(radians(x_theta - camera_x)) + tempz * Cos(radians(x_theta - camera_x))
        Next i
        
        '**Note** undoing the y-rotation is unnecessary because the camera rotates up and down relative
        'to the x-axis that goes across the screen, not the one relative to the robot. moving the camera
        'relative to the robot's x-axis causes unwanted rotations that actually make the camera go under
        'the ground, which is both unrealistic and annoying
        
        camera_x = x_theta
    End If
    
    '___________________________________
    '********Rotate about y-axis********
    '-----------------------------------
    
    If y_theta <> 0 Then
        'undo x-rotation
        For i = 1 To vertex_count
            tempy = vertex(i).y: tempz = vertex(i).z
        
            vertex(i).y = tempy * Cos(radians(-camera_x)) - tempz * Sin(radians(-camera_x))
            vertex(i).z = tempy * Sin(radians(-camera_x)) + tempz * Cos(radians(-camera_x))
        Next i
        
        'y-rotation
        For i = 1 To vertex_count
            tempx = vertex(i).x: tempz = vertex(i).z
            
            vertex(i).z = tempz * Cos(radians(y_theta - camera_y)) - tempx * Sin(radians(y_theta - camera_y))
            vertex(i).x = tempz * Sin(radians(y_theta - camera_y)) + tempx * Cos(radians(y_theta - camera_y))
        Next i
        
        'redo x-rotation
        For i = 1 To vertex_count
            tempy = vertex(i).y: tempz = vertex(i).z
        
            vertex(i).y = tempy * Cos(radians(camera_x)) - tempz * Sin(radians(camera_x))
            vertex(i).z = tempy * Sin(radians(camera_x)) + tempz * Cos(radians(camera_x))
        Next i
        
        'undoing the x-rotation here is necessary because this rotates the camera relative to the y-axis
        'that goes up the screen, not the robot's y-axis. without undoing the x-rotation, the whole robot
        'is rotated around an axis that would make the camera go under it and the ground. by undoing the
        'x-rotation so that the robot is standing straight up in its original position and then rotating it
        'around the y-axis, it is possible to create the illusion that the camera itself is moving around it
        
        camera_y = y_theta
    End If

    draw pct
    
End Function
Public Function super_rotate(ByRef pct As PictureBox, arm_num As Integer, ry As Single, rz As Single, rz2 As Single, ry3 As Single, openclaw As Single)
    
    Dim tempx, tempy, tempz As Single
    Dim temprotatex As Single
    Dim temprotatey As Single
    Dim temprotatez As Single
    Dim temprotatex2 As Single
    Dim temprotatey2 As Single
    Dim temprotatez2 As Single
    Dim temprotatex3 As Single
    Dim temprotatey3 As Single
    Dim temprotatez3 As Single
    
    If arm_num = 1 Then rotate_factorY = rotate_factorY + ry
    If arm_num = 2 Then rotate_factorZ = rotate_factorZ + rz
    If arm_num = 3 Then rotate_factorZ2 = rotate_factorZ2 + rz2
    If arm_num = 4 Then rotate_factorY3 = rotate_factorY3 + ry3
    
    rotate_with_claw pct, rotate_factorY3
    rotate_with_arm3 pct, rotate_factorZ2
    rotate_with_arm2 pct, rotate_factorZ
    rotate_with_arm1 pct, rotate_factorY
    
'    'redo translation
'    For i = 1 To vertex_count
'        vertex(i).x = vertex(i).x + joint(0).x
'        vertex(i).z = vertex(i).z + joint(0).z
'        If i < 4 Then joint(i).x = joint(i).x + joint(0).x
'        If i < 4 Then joint(i).z = joint(i).z + joint(0).z
'    Next i
'
'    'redo camera rotation
'    For i = 1 To vertex_count
'        tempx = vertex(i).x: tempz = vertex(i).z
'
'        vertex(i).z = tempz * Cos(radians(camera_y)) - tempx * Sin(radians(camera_y))
'        vertex(i).x = tempz * Sin(radians(camera_y)) + tempx * Cos(radians(camera_y))
'    Next i
'    For i = 1 To vertex_count
'        tempy = vertex(i).y: tempz = vertex(i).z
'
'        vertex(i).y = tempy * Cos(radians(camera_x)) - tempz * Sin(radians(camera_x))
'        vertex(i).z = tempy * Sin(radians(camera_x)) + tempz * Cos(radians(camera_x))
'    Next i
    
    draw pct
    
End Function
Public Function degrees(ByRef theta As Single)
    
    degrees = (theta * (180 / 3.1415926538979))
    
End Function
Public Function radians(ByRef theta As Single)
    
    radians = (theta * (3.1415926538979 / 180))
    
End Function
Function ArcSin(x As Double) As Double

    ArcSin = Atn(x / Sqr(-x * x + 1))

End Function
Function ArcCos(x As Double) As Double
    
    ArcCos = Atn(-x / Sqr(-x * x + 1)) + 2 * Atn(1)

End Function
