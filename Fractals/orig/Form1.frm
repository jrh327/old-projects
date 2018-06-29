VERSION 5.00
Begin VB.Form frmSierpinski 
   AutoRedraw      =   -1  'True
   Caption         =   "Form1"
   ClientHeight    =   11115
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   14355
   DrawWidth       =   2
   LinkTopic       =   "Form1"
   ScaleHeight     =   741
   ScaleMode       =   3  'Pixel
   ScaleWidth      =   957
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton Command1 
      Caption         =   "Fractals"
      Height          =   375
      Left            =   8400
      TabIndex        =   5
      Top             =   0
      Width           =   975
   End
   Begin VB.CommandButton cmdDrawRandom 
      Caption         =   "Random"
      Height          =   255
      Left            =   4800
      TabIndex        =   4
      Top             =   0
      Width           =   1095
   End
   Begin VB.TextBox txtIterations 
      Alignment       =   1  'Right Justify
      Height          =   285
      Left            =   3360
      TabIndex        =   3
      Text            =   "5"
      Top             =   0
      Width           =   975
   End
   Begin VB.CommandButton cmdDraw 
      Caption         =   "Draw"
      Height          =   255
      Left            =   2160
      TabIndex        =   2
      Top             =   0
      Width           =   975
   End
   Begin VB.Label lblY 
      Alignment       =   1  'Right Justify
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Left            =   1080
      TabIndex        =   1
      Top             =   0
      Width           =   855
   End
   Begin VB.Label lblX 
      Alignment       =   1  'Right Justify
      BorderStyle     =   1  'Fixed Single
      Height          =   255
      Left            =   120
      TabIndex        =   0
      Top             =   0
      Width           =   855
   End
End
Attribute VB_Name = "frmSierpinski"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
'Jon Hopkins
'Fractals Project
'11/19/09
'form for making Sierpinski Triangles

Private Type vertex

    X As Integer
    Y As Integer

End Type

Dim v(3) As vertex

Private Function midpoint(p1 As vertex, p2 As vertex) As vertex

    midpoint.X = (p1.X + p2.X) / 2
    midpoint.Y = (p1.Y + p2.Y) / 2
    

End Function
Private Function draw_line(p1 As vertex, p2 As vertex)

    frmSierpinski.Line (p1.X, p1.Y)-(p2.X, p2.Y)

End Function
Private Function draw_vertices(v1 As vertex, v2 As vertex, v3 As vertex)

    draw_line v1, v2
    draw_line v2, v3
    draw_line v3, v1
    
End Function
Private Function draw_triangle(v1 As vertex, v2 As vertex, v3 As vertex, i As Integer)
    
    draw_line v1, v2
    draw_line v2, v3
    draw_line v3, v1
    
    If i > 0 Then
        draw_triangle v1, midpoint(v1, v2), midpoint(v1, v3), i - 1
        draw_triangle v2, midpoint(v1, v2), midpoint(v2, v3), i - 1
        draw_triangle v3, midpoint(v1, v3), midpoint(v2, v3), i - 1
    End If

End Function
Private Function draw_triangle_random()
    
    Dim i As Single
    
    Dim vert(2) As vertex
    Dim rndvert As Integer
    
    For i = 0 To 100000
    Randomize
    
    m = (v(1).Y - v(2).Y) / (v(1).X - v(2).X)
    
    
    vert(1).Y = Int(Rnd * (v(2).Y - v(1).Y)) + v(1).Y '(Int(Rnd * 610) + 50)
    
    vert(1).X = Int(Rnd * (vert(1).Y - (v(1).Y) / m))
    
    'vert(1).X = Int(Rnd * (v(3).X - v(2).X)) + v(2).X '(Int(Rnd * 800) + 25)
    
    rndvert = Int(Rnd * 3 + 1)
    
    vert(2) = midpoint(vert(1), v(rndvert))
    
    PSet (vert(1).X, vert(1).Y) ', RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255))
    PSet (vert(2).X, vert(2).Y) ', RGB(Int(Rnd * 255), Int(Rnd * 255), Int(Rnd * 255))
    
    Next i
    

End Function
Private Sub cmdDraw_Click()
    
    frmSierpinski.Cls
    
    v(1).X = 425: v(1).Y = 50
    v(2).X = 25: v(2).Y = 660
    v(3).X = 825: v(3).Y = 660
    
    draw_triangle v(1), v(2), v(3), Val(txtIterations)
    
End Sub

Private Sub cmdDrawRandom_Click()

    frmSierpinski.Cls
    
    v(1).X = 425: v(1).Y = 50
    v(2).X = 25: v(2).Y = 660
    v(3).X = 825: v(3).Y = 660
    
    draw_triangle_random
    
End Sub

Private Sub Command1_Click()

    frmFractalTree.Visible = True
    frmSierpinski.Visible = False
    
End Sub

Private Sub Form_MouseMove(Button As Integer, Shift As Integer, X As Single, Y As Single)

    If Button = 1 Then
        lblX = X
        lblY = Y
    End If

End Sub
