VERSION 5.00
Begin VB.Form frmKochSnowflake 
   AutoRedraw      =   -1  'True
   Caption         =   "Koch Snowflake"
   ClientHeight    =   10320
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   12780
   LinkTopic       =   "Form1"
   ScaleHeight     =   688
   ScaleMode       =   3  'Pixel
   ScaleWidth      =   852
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton Command1 
      Caption         =   "Fractals"
      Height          =   255
      Left            =   9600
      TabIndex        =   2
      Top             =   120
      Width           =   855
   End
   Begin VB.CommandButton cmdDraw 
      Caption         =   "Draw"
      Height          =   255
      Left            =   240
      TabIndex        =   1
      Top             =   120
      Width           =   855
   End
   Begin VB.TextBox txtIterations 
      Alignment       =   1  'Right Justify
      Height          =   285
      Left            =   1200
      TabIndex        =   0
      Text            =   "2"
      Top             =   120
      Width           =   735
   End
End
Attribute VB_Name = "frmKochSnowflake"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Type vertex

    X As Integer
    Y As Integer

End Type

Dim v(3) As vertex
Private Sub cmdDraw_Click()
    
    frmKochSnowflake.Cls
    
    v(1).X = 150: v(1).Y = 550
    v(2).X = 650: v(2).Y = 550
    v(3).X = 150 + 500 * Cos(3.14159265358979 / 3): v(3).Y = 550 - 500 * Sin(3.14159265358979 / 3)
    
    draw_vertices v(1), v(2), v(3)
    
    draw_snowflake v(1), v(2), v(3), Val(txtIterations)
    
End Sub
Private Function draw_snowflake(v1 As vertex, v2 As vertex, v3 As vertex, i As Integer)
    
    If i > 0 Then
        
        'third of way up the side, third of the way from other side
        'connect at midpoint of side a (length of side) / 3 * 3 ^ .5 away
        
        Line (v(1).X + ((v(3).X - v(1).X) / 3), v(1).Y - ((v(1).Y - v(3).Y) / 3))-(((v(1).X + v(3).X) / 2) + ((250 * (3 ^ 0.5) / 2) * Cos(150 * (3.14159265358979 / 180))), ((v(1).Y + v(3).Y) / 2) - ((250 * (3 ^ 0.5) / 2) * Sin(150 * (3.14159265358979 / 180))))
        Line (v(1).X + ((v(3).X - v(1).X) * (2 / 3)), v(1).Y - ((v(1).Y - v(3).Y) * (2 / 3)))-(((v(1).X + v(3).X) / 2) + ((250 * (3 ^ 0.5) / 2) * Cos(150 * (3.14159265358979 / 180))), ((v(1).Y + v(3).Y) / 2) - ((250 * (3 ^ 0.5) / 2) * Sin(150 * (3.14159265358979 / 180))))
        
        
        
    End If


End Function
Private Function thirdify(p1 As vertex, p2 As vertex) As vertex

    thirdify.X = (p1.X + p2.X) / 3 * 2
    thirdify.Y = (p1.Y + p2.Y) / 3 * 2

End Function
Private Function draw_line(p1 As vertex, p2 As vertex)

    Line (p1.X, p1.Y)-(p2.X, p2.Y)

End Function
Private Function draw_vertices(v1 As vertex, v2 As vertex, v3 As vertex)

    draw_line v1, v2
    draw_line v2, v3
    draw_line v3, v1
    
End Function
Private Sub Command1_Click()
    
    frmKochSnowflake.Visible = False
    frmFractalTree.Visible = True

End Sub
Private Sub Form_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)
    
    If Button = 1 Then
        Cls
        
        v(1).X = X: v(1).Y = Y
        v(2).X = X + 500: v(2).Y = Y
        v(3).X = X + 500 * Cos(3.14159265358979 / 3): v(3).Y = Y - 500 * Sin(3.14159265358979 / 3)
        
        draw_vertices v(1), v(2), v(3)
    End If
    If Button = 2 Then
        End
    End If
    
End Sub
