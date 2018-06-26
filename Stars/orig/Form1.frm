VERSION 5.00
Begin VB.Form Form1 
   BackColor       =   &H00000000&
   Caption         =   "Form1"
   ClientHeight    =   6990
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   7380
   LinkTopic       =   "Form1"
   ScaleHeight     =   6990
   ScaleWidth      =   7380
   StartUpPosition =   2  'CenterScreen
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Form_Activate()
    
    Dim quad1 As Boolean
    Dim quad2 As Boolean
    Dim quad3 As Boolean
    Dim quad4 As Boolean
    Dim clearing As Integer
    Dim xpos, ypos As Integer
    Dim red, green, blue As Integer
    
    Dim i As Single
    
    pixelx = Form1.ScaleWidth / 40
    pixely = Form1.ScaleHeight / 40
    
    originx = Form1.ScaleWidth / 2
    originy = Form1.ScaleHeight / 2
    
    quad1 = True
    quad2 = False
    quad3 = False
    quad4 = False
    
    clearing = -1
    
    xpos = 21
    ypos = 0
    i = 0
    
    Do
        DoEvents
        If clearing = -1 Then red = red + 255 / 80: green = green + 255 / 80: blue = blue + 255 / 80
        'If clearing = -1 Then red = Int(Rnd * 255): green = Int(Rnd * 255): blue = Int(Rnd * 255):
        If clearing = 1 Then red = 0: blue = 0: green = 0
        Line (originx + (xpos * pixelx), originy)-(originx, originy - (ypos * pixely)), RGB(red, green, blue)
        Do
            'DoEvents
            i = i + 1
        Loop Until i = 250000
        i = 0
        If quad1 = True Then
            xpos = xpos - 1
            ypos = Abs(xpos - 21)
            If xpos = -1 Then
                xpos = 0
                quad1 = False
                quad2 = True
            End If
        End If
        If quad2 = True Then
            xpos = xpos - 1
            ypos = Abs(xpos + 21)
            If xpos = -22 Then
                quad2 = False
                quad3 = True
            End If
        End If
        If quad3 = True Then
            xpos = xpos + 1
            ypos = -Abs(xpos + 21)
            If xpos = 1 Then
                xpos = 0
                quad3 = False
                quad4 = True
            End If
        End If
        If quad4 = True Then
            xpos = xpos + 1
            ypos = -Abs(xpos - 21)
            If xpos = 21 Then
                ypos = 0
                quad4 = False
                quad1 = True
                clearing = -clearing
            End If
        End If
    Loop

End Sub
