VERSION 5.00
Begin VB.Form frmFractalTree 
   AutoRedraw      =   -1  'True
   BorderStyle     =   1  'Fixed Single
   Caption         =   "frmFractalTree"
   ClientHeight    =   12930
   ClientLeft      =   150
   ClientTop       =   435
   ClientWidth     =   15465
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   862
   ScaleMode       =   3  'Pixel
   ScaleWidth      =   1031
   StartUpPosition =   2  'CenterScreen
   Begin VB.Frame Frame1 
      Height          =   4815
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Width           =   1575
      Begin VB.CommandButton Command3 
         Caption         =   "Koch"
         Height          =   375
         Left            =   360
         TabIndex        =   13
         Top             =   4320
         Width           =   855
      End
      Begin VB.CommandButton Command2 
         Caption         =   "Sierpinski"
         Height          =   375
         Left            =   360
         TabIndex        =   12
         Top             =   3840
         Width           =   855
      End
      Begin VB.CommandButton Command1 
         Caption         =   "Draw"
         Height          =   375
         Left            =   360
         TabIndex        =   11
         Top             =   3360
         Width           =   855
      End
      Begin VB.TextBox txtPercentage 
         Alignment       =   1  'Right Justify
         Height          =   285
         Left            =   360
         TabIndex        =   6
         Text            =   "75"
         Top             =   2880
         Width           =   855
      End
      Begin VB.TextBox txtAngle 
         Alignment       =   1  'Right Justify
         Height          =   285
         Left            =   360
         TabIndex        =   5
         Text            =   "30"
         Top             =   1680
         Width           =   855
      End
      Begin VB.TextBox txtIterations 
         Alignment       =   1  'Right Justify
         Height          =   285
         Left            =   360
         TabIndex        =   4
         Text            =   "15"
         Top             =   2280
         Width           =   855
      End
      Begin VB.TextBox txtLength 
         Alignment       =   1  'Right Justify
         Height          =   285
         Left            =   360
         TabIndex        =   3
         Text            =   "150"
         Top             =   1080
         Width           =   855
      End
      Begin VB.CheckBox Check2 
         Caption         =   "Randomness"
         Height          =   375
         Left            =   120
         TabIndex        =   2
         Top             =   360
         Width           =   1335
      End
      Begin VB.CheckBox Check1 
         Caption         =   "Colors :D"
         Height          =   375
         Left            =   120
         TabIndex        =   1
         Top             =   120
         Width           =   1095
      End
      Begin VB.Label Label4 
         Alignment       =   2  'Center
         Caption         =   "Percentage"
         Height          =   255
         Left            =   360
         TabIndex        =   10
         Top             =   2640
         Width           =   855
      End
      Begin VB.Label Label3 
         Alignment       =   2  'Center
         Caption         =   "Iterations"
         Height          =   255
         Left            =   360
         TabIndex        =   9
         Top             =   2040
         Width           =   855
      End
      Begin VB.Label Label2 
         Alignment       =   2  'Center
         Caption         =   "Angle"
         Height          =   255
         Left            =   360
         TabIndex        =   8
         Top             =   1440
         Width           =   855
      End
      Begin VB.Label Label1 
         Alignment       =   2  'Center
         Caption         =   "Length"
         Height          =   255
         Left            =   360
         TabIndex        =   7
         Top             =   840
         Width           =   855
      End
   End
End
Attribute VB_Name = "frmFractalTree"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Function tree(x1 As Single, y1 As Single, angle As Single, length As Single, i As Integer)

    X2 = x1 + (length * Cos((angle) * (3.14159265358979 / 180)))
    Y2 = y1 - (length * Sin((angle) * (3.14159265358979 / 180)))
    
    If Check1.Value = 1 Then
        Randomize
        r = Int(Rnd * 255)
        g = Int(Rnd * 255)
        b = Int(Rnd * 255)
    Else
        'r = 0
        'g = 0
        'b = 0
    End If
    If Check2.Value = 1 Then
        If i <= Val(txtIterations) Then r = 128: g = 64: b = 50
        If i > 0 Then frmFractalTree.DrawWidth = (Val(i) / 2) + 1
        If i < Val(txtIterations) / 4 Then r = 0: g = 120: b = 0
        If txtIterations >= 12 Then
            If i = 0 Then
                f = Int(Rnd * 5)
                If f = 1 Then r = 175: g = 0: b = 255
            End If
        End If
    End If
    Line (x1, y1)-(X2, Y2), RGB(r, g, b)
    
    If Check2.Value = 1 Then
        Randomize
        a = Int(Rnd * 30)
        If i > 0 Then tree Val(X2), Val(Y2), Val(angle + a), Val(length * Val(txtPercentage / 100)), Val(i - 1)
        If i > 0 Then tree Val(X2), Val(Y2), Val(angle - a), Val(length * Val(txtPercentage / 100)), Val(i - 1)
    Else
        If i > 0 Then tree Val(X2), Val(Y2), Val(angle + Val(txtAngle)), Val(length * Val(txtPercentage / 100)), Val(i - 1)
        If i > 0 Then tree Val(X2), Val(Y2), Val(angle - Val(txtAngle)), Val(length * Val(txtPercentage / 100)), Val(i - 1)
    End If
    
End Function

Private Sub Command1_Click()

    frmFractalTree.Cls
    
    tree 600, 600, 90, Val(txtLength), Val(txtIterations)

End Sub

Private Sub Command2_Click()
    
    frmSierpinski.Visible = True
    frmFractalTree.Visible = False

End Sub
Private Sub Command3_click()
    
    frmKochSnowflake.Visible = True
    frmFractalTree.Visible = False

End Sub
Private Sub Form_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)

    If Button = 1 Then
        frmFractalTree.Cls
        
        tree X, Y, 90, Val(txtLength), Val(txtIterations)
    End If
    If Button = 2 Then
        End
    End If

End Sub
