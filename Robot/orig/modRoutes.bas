Attribute VB_Name = "modRoutes"
'Jon Hopkins

Public Type route
    move(1000) As Integer
End Type

Dim routes(10) As route
Public Sub Save_Moves(ByRef route_number As Integer, ByRef move_number As Integer, ByRef move_type As Integer)

    Dim filename As String
    
    filename = App.Path + "\RobotRoutes.txt"
    
    Open filename For Random As #1 Len = Len(routes(route_number)) 'len(<name of Private Type being saved>) is the number of bytes
        
        routes(route_number).move(move_number) = move_type
        
        Put #1, 1, routes(route_number) '#1 is file number, 1 is record number, s is record variable to be saved to H:Students.DBD
    Close #1
    
End Sub
Public Sub Get_Moves(ByRef route_number As Integer, ByRef move_number As Integer)

    Dim filename As String
    
    filename = App.Path + "\RobotRoutes.txt"

    Open filename For Random As #1 Len = Len(routes(route_number)) 'len(s) is the number of bytes
        
    Get #1, 1, routes(route_number)   '#1 is file number, 1 is record number, s is record variable to be saved to H:Students.DBD
        
        Get_Moves = routes(route_number).move(move_number)
        
    Close #1
    
End Sub
