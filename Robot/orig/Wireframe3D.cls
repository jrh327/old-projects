VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "Wireframe3D"
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

Private Type point_p
    r As Single
    theta As Single
End Type

Private Type line
    p1 As Integer
    p2 As Integer
    
    r As Integer
    g As Integer
    b As Integer
End Type

Private vertex(2000) As point_c
Private vertex_p(2000) As point_p
Private lines(6000) As line
Private joint(5) As point_c

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

Public middle_claw_x As Single
Public middle_claw_y As Single
Public middle_claw_z As Single

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

Public Function initialize()
    
    vertex_count = 0
    line_count = 0
    
    rotate_factorX = 0
    rotate_factorY = 0
    rotate_factorZ = 0
    rotate_factorX2 = 0
    rotate_factorY2 = 0
    rotate_factorZ2 = 0
    rotate_factorX3 = 0
    rotate_factorY3 = 0
    rotate_factorZ3 = 0
    camera_x = 0
    camera_y = 0
    
End Function
Public Function base()
    
    '1-16
    For i = 1 To 16
        vertex(vertex_count + i).x = 4 * Cos(radians(45 * i))
        vertex(vertex_count + i).z = 4 * Sin(radians(45 * i))
        If i < 9 Then vertex(vertex_count + i).y = 0 Else vertex(vertex_count + i).y = -1.4
    Next i
    
    For i = 1 To 16
        lines(line_count + i).p1 = vertex_count + i: lines(line_count + i).p2 = vertex_count + i + 1
        If i = 8 Then lines(line_count + i).p1 = vertex_count + i: lines(line_count + i).p2 = vertex_count + 1
        If i = 16 Then lines(line_count + i).p1 = vertex_count + i: lines(line_count + i).p2 = vertex_count + 9
    Next i
    
    For i = 17 To 24
        lines(line_count + i).p1 = vertex_count - 16 + i: lines(line_count + i).p2 = vertex_count - 16 + i + 8
    Next i
    

    vertex_count = vertex_count + 16
    line_count = line_count + 24
    
    '16 vertices, 24 lines

End Function
Public Function arm()

    '17-24
    vertex(vertex_count + 1).x = -0.4: vertex(vertex_count + 1).z = -0.4: vertex(vertex_count + 1).y = 0
    vertex(vertex_count + 2).x = 0.4: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = 0
    vertex(vertex_count + 3).x = 0.4: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = 0
    vertex(vertex_count + 4).x = -0.4: vertex(vertex_count + 4).z = 0.4: vertex(vertex_count + 4).y = 0
    vertex(vertex_count + 5).x = -0.4: vertex(vertex_count + 5).z = -0.4: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = 0.4: vertex(vertex_count + 6).z = -0.4: vertex(vertex_count + 6).y = 5
    vertex(vertex_count + 7).x = 0.4: vertex(vertex_count + 7).z = 0.4: vertex(vertex_count + 7).y = 5
    vertex(vertex_count + 8).x = -0.4: vertex(vertex_count + 8).z = 0.4: vertex(vertex_count + 8).y = 5
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 4: lines(line_count + 4).p2 = vertex_count + 1
    lines(line_count + 5).p1 = vertex_count + 5: lines(line_count + 5).p2 = vertex_count + 6
    lines(line_count + 6).p1 = vertex_count + 6: lines(line_count + 6).p2 = vertex_count + 7
    lines(line_count + 7).p1 = vertex_count + 7: lines(line_count + 7).p2 = vertex_count + 8
    lines(line_count + 8).p1 = vertex_count + 8: lines(line_count + 8).p2 = vertex_count + 5
    lines(line_count + 9).p1 = vertex_count + 1: lines(line_count + 9).p2 = vertex_count + 5
    lines(line_count + 10).p1 = vertex_count + 2: lines(line_count + 10).p2 = vertex_count + 6
    lines(line_count + 11).p1 = vertex_count + 3: lines(line_count + 11).p2 = vertex_count + 7
    lines(line_count + 12).p1 = vertex_count + 4: lines(line_count + 12).p2 = vertex_count + 8
    
    vertex_count = vertex_count + 8
    line_count = line_count + 12
    
    '25-32
    vertex(vertex_count + 1).x = -0.4: vertex(vertex_count + 1).z = -0.4: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = -0.2: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = 5.6
    vertex(vertex_count + 3).x = 0.2: vertex(vertex_count + 3).z = -0.4: vertex(vertex_count + 3).y = 5.6
    vertex(vertex_count + 4).x = 0.4: vertex(vertex_count + 4).z = -0.4: vertex(vertex_count + 4).y = 5
    vertex(vertex_count + 5).x = -0.4: vertex(vertex_count + 5).z = -0.15: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = -0.2: vertex(vertex_count + 6).z = -0.15: vertex(vertex_count + 6).y = 5.6
    vertex(vertex_count + 7).x = 0.2: vertex(vertex_count + 7).z = -0.15: vertex(vertex_count + 7).y = 5.6
    vertex(vertex_count + 8).x = 0.4: vertex(vertex_count + 8).z = -0.15: vertex(vertex_count + 8).y = 5
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '33-40
    vertex(vertex_count + 1).x = -0.4: vertex(vertex_count + 1).z = 0.4: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = -0.2: vertex(vertex_count + 2).z = 0.4: vertex(vertex_count + 2).y = 5.6
    vertex(vertex_count + 3).x = 0.2: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = 5.6
    vertex(vertex_count + 4).x = 0.4: vertex(vertex_count + 4).z = 0.4: vertex(vertex_count + 4).y = 5
    vertex(vertex_count + 5).x = -0.4: vertex(vertex_count + 5).z = 0.15: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = -0.2: vertex(vertex_count + 6).z = 0.15: vertex(vertex_count + 6).y = 5.6
    vertex(vertex_count + 7).x = 0.2: vertex(vertex_count + 7).z = 0.15: vertex(vertex_count + 7).y = 5.6
    vertex(vertex_count + 8).x = 0.4: vertex(vertex_count + 8).z = 0.15: vertex(vertex_count + 8).y = 5
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '24 vertices, 30 lines
    
End Function
Public Function arm2()

    '41-48
    vertex(vertex_count + 1).x = 0.4: vertex(vertex_count + 1).z = -0.4: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = 0.4: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = 5.8
    vertex(vertex_count + 3).x = 0.4: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = 5.8
    vertex(vertex_count + 4).x = 0.4: vertex(vertex_count + 4).z = 0.4: vertex(vertex_count + 4).y = 5
    vertex(vertex_count + 5).x = 7.4: vertex(vertex_count + 5).z = -0.4: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = 7.4: vertex(vertex_count + 6).z = -0.4: vertex(vertex_count + 6).y = 5.8
    vertex(vertex_count + 7).x = 7.4: vertex(vertex_count + 7).z = 0.4: vertex(vertex_count + 7).y = 5.8
    vertex(vertex_count + 8).x = 7.4: vertex(vertex_count + 8).z = 0.4: vertex(vertex_count + 8).y = 5
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 4: lines(line_count + 4).p2 = vertex_count + 1
    lines(line_count + 5).p1 = vertex_count + 5: lines(line_count + 5).p2 = vertex_count + 6
    lines(line_count + 6).p1 = vertex_count + 6: lines(line_count + 6).p2 = vertex_count + 7
    lines(line_count + 7).p1 = vertex_count + 7: lines(line_count + 7).p2 = vertex_count + 8
    lines(line_count + 8).p1 = vertex_count + 8: lines(line_count + 8).p2 = vertex_count + 5
    lines(line_count + 9).p1 = vertex_count + 1: lines(line_count + 9).p2 = vertex_count + 5
    lines(line_count + 10).p1 = vertex_count + 2: lines(line_count + 10).p2 = vertex_count + 6
    lines(line_count + 11).p1 = vertex_count + 3: lines(line_count + 11).p2 = vertex_count + 7
    lines(line_count + 12).p1 = vertex_count + 4: lines(line_count + 12).p2 = vertex_count + 8
    
    vertex_count = vertex_count + 8
    line_count = line_count + 12
    
    '49-56
    vertex(vertex_count + 1).x = 0.4: vertex(vertex_count + 1).z = -0.15: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = -0.2: vertex(vertex_count + 2).z = -0.15: vertex(vertex_count + 2).y = 5.2
    vertex(vertex_count + 3).x = -0.2: vertex(vertex_count + 3).z = -0.15: vertex(vertex_count + 3).y = 5.6
    vertex(vertex_count + 4).x = 0.4: vertex(vertex_count + 4).z = -0.15: vertex(vertex_count + 4).y = 5.8
    vertex(vertex_count + 5).x = 0.4: vertex(vertex_count + 5).z = 0.15: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = -0.2: vertex(vertex_count + 6).z = 0.15: vertex(vertex_count + 6).y = 5.2
    vertex(vertex_count + 7).x = -0.2: vertex(vertex_count + 7).z = 0.15: vertex(vertex_count + 7).y = 5.6
    vertex(vertex_count + 8).x = 0.4: vertex(vertex_count + 8).z = 0.15: vertex(vertex_count + 8).y = 5.8
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '57-64
    vertex(vertex_count + 1).x = 7.4: vertex(vertex_count + 1).z = -0.4: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = 8: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = 5.2
    vertex(vertex_count + 3).x = 8: vertex(vertex_count + 3).z = -0.4: vertex(vertex_count + 3).y = 5.6
    vertex(vertex_count + 4).x = 7.4: vertex(vertex_count + 4).z = -0.4: vertex(vertex_count + 4).y = 5.8
    vertex(vertex_count + 5).x = 7.4: vertex(vertex_count + 5).z = -0.15: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = 8: vertex(vertex_count + 6).z = -0.15: vertex(vertex_count + 6).y = 5.2
    vertex(vertex_count + 7).x = 8: vertex(vertex_count + 7).z = -0.15: vertex(vertex_count + 7).y = 5.6
    vertex(vertex_count + 8).x = 7.4: vertex(vertex_count + 8).z = -0.15: vertex(vertex_count + 8).y = 5.8
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '65-72
    vertex(vertex_count + 1).x = 7.4: vertex(vertex_count + 1).z = 0.4: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = 8: vertex(vertex_count + 2).z = 0.4: vertex(vertex_count + 2).y = 5.2
    vertex(vertex_count + 3).x = 8: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = 5.6
    vertex(vertex_count + 4).x = 7.4: vertex(vertex_count + 4).z = 0.4: vertex(vertex_count + 4).y = 5.8
    vertex(vertex_count + 5).x = 7.4: vertex(vertex_count + 5).z = 0.15: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = 8: vertex(vertex_count + 6).z = 0.15: vertex(vertex_count + 6).y = 5.2
    vertex(vertex_count + 7).x = 8: vertex(vertex_count + 7).z = 0.15: vertex(vertex_count + 7).y = 5.6
    vertex(vertex_count + 8).x = 7.4: vertex(vertex_count + 8).z = 0.15: vertex(vertex_count + 8).y = 5.8
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '32 vertices, 39 lines
    
End Function
Public Function arm3()

    '73-80
    vertex(vertex_count + 1).x = 7.4: vertex(vertex_count + 1).z = -0.4: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = 7.4: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = 0.2
    vertex(vertex_count + 3).x = 7.4: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = 0.2
    vertex(vertex_count + 4).x = 7.4: vertex(vertex_count + 4).z = 0.4: vertex(vertex_count + 4).y = 5
    vertex(vertex_count + 5).x = 8.2: vertex(vertex_count + 5).z = -0.4: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = 8.2: vertex(vertex_count + 6).z = -0.4: vertex(vertex_count + 6).y = 0.2
    vertex(vertex_count + 7).x = 8.2: vertex(vertex_count + 7).z = 0.4: vertex(vertex_count + 7).y = 0.2
    vertex(vertex_count + 8).x = 8.2: vertex(vertex_count + 8).z = 0.4: vertex(vertex_count + 8).y = 5
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 4: lines(line_count + 4).p2 = vertex_count + 1
    lines(line_count + 5).p1 = vertex_count + 5: lines(line_count + 5).p2 = vertex_count + 6
    lines(line_count + 6).p1 = vertex_count + 6: lines(line_count + 6).p2 = vertex_count + 7
    lines(line_count + 7).p1 = vertex_count + 7: lines(line_count + 7).p2 = vertex_count + 8
    lines(line_count + 8).p1 = vertex_count + 8: lines(line_count + 8).p2 = vertex_count + 5
    lines(line_count + 9).p1 = vertex_count + 1: lines(line_count + 9).p2 = vertex_count + 5
    lines(line_count + 10).p1 = vertex_count + 2: lines(line_count + 10).p2 = vertex_count + 6
    lines(line_count + 11).p1 = vertex_count + 3: lines(line_count + 11).p2 = vertex_count + 7
    lines(line_count + 12).p1 = vertex_count + 4: lines(line_count + 12).p2 = vertex_count + 8
    
    vertex_count = vertex_count + 8
    line_count = line_count + 12
    
    '81-88
    vertex(vertex_count + 1).x = 7.4: vertex(vertex_count + 1).z = -0.15: vertex(vertex_count + 1).y = 5
    vertex(vertex_count + 2).x = 7.6: vertex(vertex_count + 2).z = -0.15: vertex(vertex_count + 2).y = 5.6
    vertex(vertex_count + 3).x = 8: vertex(vertex_count + 3).z = -0.15: vertex(vertex_count + 3).y = 5.6
    vertex(vertex_count + 4).x = 8.2: vertex(vertex_count + 4).z = -0.15: vertex(vertex_count + 4).y = 5
    vertex(vertex_count + 5).x = 7.4: vertex(vertex_count + 5).z = 0.15: vertex(vertex_count + 5).y = 5
    vertex(vertex_count + 6).x = 7.6: vertex(vertex_count + 6).z = 0.15: vertex(vertex_count + 6).y = 5.6
    vertex(vertex_count + 7).x = 8: vertex(vertex_count + 7).z = 0.15: vertex(vertex_count + 7).y = 5.6
    vertex(vertex_count + 8).x = 8.2: vertex(vertex_count + 8).z = 0.15: vertex(vertex_count + 8).y = 5
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '89-96
    vertex(vertex_count + 1).x = 7.4: vertex(vertex_count + 1).z = -0.15: vertex(vertex_count + 1).y = 0.2
    vertex(vertex_count + 2).x = 7.6: vertex(vertex_count + 2).z = -0.15: vertex(vertex_count + 2).y = -0.1
    vertex(vertex_count + 3).x = 8: vertex(vertex_count + 3).z = -0.15: vertex(vertex_count + 3).y = -0.1
    vertex(vertex_count + 4).x = 8.2: vertex(vertex_count + 4).z = -0.15: vertex(vertex_count + 4).y = 0.2
    vertex(vertex_count + 5).x = 7.4: vertex(vertex_count + 5).z = 0.15: vertex(vertex_count + 5).y = 0.2
    vertex(vertex_count + 6).x = 7.6: vertex(vertex_count + 6).z = 0.15: vertex(vertex_count + 6).y = -0.1
    vertex(vertex_count + 7).x = 8: vertex(vertex_count + 7).z = 0.15: vertex(vertex_count + 7).y = -0.1
    vertex(vertex_count + 8).x = 8.2: vertex(vertex_count + 8).z = 0.15: vertex(vertex_count + 8).y = 0.2
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 5: lines(line_count + 4).p2 = vertex_count + 6
    lines(line_count + 5).p1 = vertex_count + 6: lines(line_count + 5).p2 = vertex_count + 7
    lines(line_count + 6).p1 = vertex_count + 7: lines(line_count + 6).p2 = vertex_count + 8
    lines(line_count + 7).p1 = vertex_count + 8: lines(line_count + 7).p2 = vertex_count + 5
    lines(line_count + 8).p1 = vertex_count + 2: lines(line_count + 8).p2 = vertex_count + 6
    lines(line_count + 9).p1 = vertex_count + 3: lines(line_count + 9).p2 = vertex_count + 7
    
    vertex_count = vertex_count + 8
    line_count = line_count + 9
    
    '24 vertices, 30 lines
    
End Function
Public Function thingy_with_the_grabby()
    
    '97-104
    vertex(vertex_count + 1).x = 6.9: vertex(vertex_count + 1).z = -0.4: vertex(vertex_count + 1).y = -0.1
    vertex(vertex_count + 2).x = 6.9: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = -0.3
    vertex(vertex_count + 3).x = 6.9: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = -0.3
    vertex(vertex_count + 4).x = 6.9: vertex(vertex_count + 4).z = 0.4: vertex(vertex_count + 4).y = -0.1
    vertex(vertex_count + 5).x = 8.7: vertex(vertex_count + 5).z = -0.4: vertex(vertex_count + 5).y = -0.1
    vertex(vertex_count + 6).x = 8.7: vertex(vertex_count + 6).z = -0.4: vertex(vertex_count + 6).y = -0.3
    vertex(vertex_count + 7).x = 8.7: vertex(vertex_count + 7).z = 0.4: vertex(vertex_count + 7).y = -0.3
    vertex(vertex_count + 8).x = 8.7: vertex(vertex_count + 8).z = 0.4: vertex(vertex_count + 8).y = -0.1
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 3
    lines(line_count + 3).p1 = vertex_count + 3: lines(line_count + 3).p2 = vertex_count + 4
    lines(line_count + 4).p1 = vertex_count + 4: lines(line_count + 4).p2 = vertex_count + 1
    lines(line_count + 5).p1 = vertex_count + 5: lines(line_count + 5).p2 = vertex_count + 6
    lines(line_count + 6).p1 = vertex_count + 6: lines(line_count + 6).p2 = vertex_count + 7
    lines(line_count + 7).p1 = vertex_count + 7: lines(line_count + 7).p2 = vertex_count + 8
    lines(line_count + 8).p1 = vertex_count + 8: lines(line_count + 8).p2 = vertex_count + 5
    lines(line_count + 9).p1 = vertex_count + 1: lines(line_count + 9).p2 = vertex_count + 5
    lines(line_count + 10).p1 = vertex_count + 2: lines(line_count + 10).p2 = vertex_count + 6
    lines(line_count + 11).p1 = vertex_count + 3: lines(line_count + 11).p2 = vertex_count + 7
    lines(line_count + 12).p1 = vertex_count + 4: lines(line_count + 12).p2 = vertex_count + 8
    
    vertex_count = vertex_count + 8
    line_count = line_count + 12
    
    '105-110
    vertex(vertex_count + 1).x = 7.4: vertex(vertex_count + 1).z = 0.4: vertex(vertex_count + 1).y = -0.3
    vertex(vertex_count + 2).x = 7.4: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = -0.3
    vertex(vertex_count + 3).x = 7.8: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = -0.3
    vertex(vertex_count + 4).x = 7.8: vertex(vertex_count + 4).z = -0.4: vertex(vertex_count + 4).y = -0.3
    vertex(vertex_count + 5).x = 7.8: vertex(vertex_count + 5).z = 0.4: vertex(vertex_count + 5).y = -1.4
    vertex(vertex_count + 6).x = 7.8: vertex(vertex_count + 6).z = -0.4: vertex(vertex_count + 6).y = -1.4
    
    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 4
    lines(line_count + 3).p1 = vertex_count + 4: lines(line_count + 3).p2 = vertex_count + 3
    lines(line_count + 4).p1 = vertex_count + 3: lines(line_count + 4).p2 = vertex_count + 1
    lines(line_count + 5).p1 = vertex_count + 5: lines(line_count + 5).p2 = vertex_count + 1
    lines(line_count + 6).p1 = vertex_count + 5: lines(line_count + 6).p2 = vertex_count + 3
    lines(line_count + 7).p1 = vertex_count + 6: lines(line_count + 7).p2 = vertex_count + 4
    lines(line_count + 8).p1 = vertex_count + 6: lines(line_count + 8).p2 = vertex_count + 2
    lines(line_count + 9).p1 = vertex_count + 6: lines(line_count + 9).p2 = vertex_count + 5
    
    vertex_count = vertex_count + 6
    line_count = line_count + 9
    
    '111-116
    vertex(vertex_count + 1).x = 8.2: vertex(vertex_count + 1).z = 0.4: vertex(vertex_count + 1).y = -0.3
    vertex(vertex_count + 2).x = 8.2: vertex(vertex_count + 2).z = -0.4: vertex(vertex_count + 2).y = -0.3
    vertex(vertex_count + 3).x = 7.8: vertex(vertex_count + 3).z = 0.4: vertex(vertex_count + 3).y = -0.3
    vertex(vertex_count + 4).x = 7.8: vertex(vertex_count + 4).z = -0.4: vertex(vertex_count + 4).y = -0.3
    vertex(vertex_count + 5).x = 7.8: vertex(vertex_count + 5).z = 0.4: vertex(vertex_count + 5).y = -1.4
    vertex(vertex_count + 6).x = 7.8: vertex(vertex_count + 6).z = -0.4: vertex(vertex_count + 6).y = -1.4

    lines(line_count + 1).p1 = vertex_count + 1: lines(line_count + 1).p2 = vertex_count + 2
    lines(line_count + 2).p1 = vertex_count + 2: lines(line_count + 2).p2 = vertex_count + 4
    lines(line_count + 3).p1 = vertex_count + 4: lines(line_count + 3).p2 = vertex_count + 3
    lines(line_count + 4).p1 = vertex_count + 3: lines(line_count + 4).p2 = vertex_count + 1
    lines(line_count + 5).p1 = vertex_count + 5: lines(line_count + 5).p2 = vertex_count + 1
    lines(line_count + 6).p1 = vertex_count + 5: lines(line_count + 6).p2 = vertex_count + 3
    lines(line_count + 7).p1 = vertex_count + 6: lines(line_count + 7).p2 = vertex_count + 4
    lines(line_count + 8).p1 = vertex_count + 6: lines(line_count + 8).p2 = vertex_count + 2
    lines(line_count + 9).p1 = vertex_count + 6: lines(line_count + 9).p2 = vertex_count + 5
    
    vertex_count = vertex_count + 6
    line_count = line_count + 9
    
    '20 vertices, 30 lines
    
End Function
Public Function initialize_joints()
    
    joint(0).x = 0: joint(0).y = 0: joint(0).z = 0
    joint(1).x = 0: joint(1).y = 5.4: joint(1).z = 0
    joint(2).x = 7.8: joint(2).y = 5.4: joint(2).z = 0
    joint(3).x = 7.8: joint(3).y = -0.3: joint(3).z = 0
    
End Function
Public Function rotate_arm1(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 17 To vertex_count
    
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
Public Function rotate_arm2(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single

    For i = 41 To vertex_count
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
Public Function rotate_arm3(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 73 To vertex_count
        tempx = vertex(i).x - joint(2).x: tempy = vertex(i).y - joint(2).y
        
        vertex(i).x = tempx * Cos(radians(theta)) - tempy * Sin(radians(theta)) + joint(2).x
        vertex(i).y = tempx * Sin(radians(theta)) + tempy * Cos(radians(theta)) + joint(2).y
    
    Next i
    
        tempx = joint(3).x - joint(2).x: tempy = joint(3).y - joint(2).y
   
        joint(3).x = tempx * Cos(radians(theta)) - tempy * Sin(radians(theta)) + joint(2).x
        joint(3).y = tempx * Sin(radians(theta)) + tempy * Cos(radians(theta)) + joint(2).y
    
    rotate_factorZ2 = theta

End Function
Public Function rotate_claw(ByRef pct As PictureBox, theta As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 97 To vertex_count
    
        tempy = vertex(i).y - joint(3).y: tempz = vertex(i).z - joint(3).z
        
        vertex(i).y = tempy * Cos(radians(theta)) - tempz * Sin(radians(theta)) + joint(3).y
        vertex(i).z = tempy * Sin(radians(theta)) + tempz * Cos(radians(theta)) + joint(3).z
    Next i

    rotate_factorY3 = theta

End Function
Public Function rawr(ByRef pct As PictureBox, openwide As Single)
    
    Dim tempx, tempy, tempz As Single
    
    For i = 105 To 110
        vertex(i).y = vertex(i).y - openwide
    Next i
    
    For i = 111 To 116
        vertex(i).y = vertex(i).y + openwide
    Next i
    
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
    
    If arm_num = 1 Then rotate_factorY = ry
    If arm_num = 2 Then rotate_factorZ = rz
    If arm_num = 3 Then rotate_factorZ2 = rz2
    If arm_num = 4 Then rotate_factorY3 = ry3
    If arm_num = 5 Then rawr pct, openclaw
    
    'initialize
    vertex_count = 0
    line_count = 0
    
    base
    arm
    arm2
    arm3
    thingy_with_the_grabby
    'initialize_joints
    
    rotate_claw pct, rotate_factorY3
    rotate_arm3 pct, rotate_factorZ2
    rotate_arm2 pct, rotate_factorZ
    rotate_arm1 pct, rotate_factorY
    
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
        
        joint_x0 = joint(0).x
        joint_y0 = joint(0).y
        joint_z0 = joint(0).z
        joint_x1 = joint(1).x
        joint_y1 = joint(1).y
        joint_z1 = joint(1).z
        joint_x2 = joint(2).x
        joint_y2 = joint(2).y
        joint_z2 = joint(2).z
        joint_x3 = joint(3).x
        joint_y3 = joint(3).y
        joint_z3 = joint(3).z
    
    Dim clawx, clawy, clawz As Single
    
    For i = 105 To 116
        clawx = clawx + vertex(i).x
        clawy = clawy + vertex(i).y
        clawz = clawz + vertex(i).z
    Next i
    
    middle_claw_x = clawx / 12
    middle_claw_y = clawy / 12
    middle_claw_z = clawz / 12
    
    frmRobot.Label1 = Format(middle_claw_x, "##.##")
    frmRobot.Label2 = Format(middle_claw_y, "##.##")
    frmRobot.Label3 = Format(middle_claw_z, "##.##")
    
End Function
Public Function rotateX(ByRef pct As PictureBox, theta As Single)

    ' y' = y*cos q - z*sin q
    ' z' = y*sin q + z*cos q
    ' x' = x
    
    Dim tempy, tempz As Single
    
    For i = 1 To vertex_count
        tempy = vertex(i).y: tempz = vertex(i).z
        
        vertex(i).y = tempy * Cos(radians(theta - rotate_factorX)) - tempz * Sin(radians(theta - rotate_factorX))
        vertex(i).z = tempy * Sin(radians(theta - rotate_factorX)) + tempz * Cos(radians(theta - rotate_factorX))
    Next i
    
    rotate_factorX = theta
    
    draw pct
    
End Function
Public Function rotateY(ByRef pct As PictureBox, theta As Single)

    ' z' = z*cos q - x*sin q
    ' x' = z*sin q + x*cos q
    ' y' = y
    
    Dim tempx, tempz As Single
    
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempz = vertex(i).z
        
        vertex(i).z = tempz * Cos(radians(theta - rotate_factorY)) - tempx * Sin(radians(theta - rotate_factorY))
        vertex(i).x = tempz * Sin(radians(theta - rotate_factorY)) + tempx * Cos(radians(theta - rotate_factorY))
    Next i

    rotate_factorY = theta
    
    draw pct
    
End Function
Public Function rotateZ(ByRef pct As PictureBox, theta As Single)

    ' x' = x*cos q - y*sin q
    ' y' = x*sin q + y*cos q
    ' z' = z
    
    Dim tempx, tempy As Single
    
    For i = 1 To vertex_count
        tempx = vertex(i).x: tempy = vertex(i).y
        
        vertex(i).x = tempx * Cos(radians(theta - rotate_factorZ)) - tempy * Sin(radians(theta - rotate_factorZ))
        vertex(i).y = tempx * Sin(radians(theta - rotate_factorZ)) + tempy * Cos(radians(theta - rotate_factorZ))
    Next i
    
    rotate_factorZ = theta
    
    draw pct
    
End Function
Public Function degrees(ByRef theta As Single)
    
    degrees = (theta * (180 / 3.1415926538979))
    
End Function
Public Function radians(ByRef theta As Single)
    
    radians = (theta * (3.1415926538979 / 180))
    
End Function
Public Function ArcSin(x As Double) As Double

    ArcSin = Atn(x / Sqr(-x * x + 1))

End Function
Public Function ArcCos(x As Double) As Double
    
    ArcCos = Atn(-x / Sqr(-x * x + 1)) + 2 * Atn(1)

End Function
