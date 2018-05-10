/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertcourse;
import java.util.Scanner;
import java.sql.*; // for copied class in try/catch

public class InsertCourse {

   
    public static void main(String[] args) {
        // Prompt user for course name, number, description and units
        final String DATABASE_URL = "jdbc:derby://localhost:1527/sample";
        final String QUERY = "Insert into DEMO18B.course (COURSENUMBER, COURSENAME, COURSEDESC, COURSEUNITS) VALUES (?, ?, ?, ?)";
        
        
        System.out.print("Enter Course Number: ");
        String CrsNum = new Scanner(System.in).nextLine();
        
        System.out.print("Enter Course Name: ");
        String CrsName = new Scanner(System.in).nextLine();
        
        System.out.print("Enter Course Description: ");
        String CrsDesc = new Scanner(System.in).nextLine();
        
        System.out.print("Enter Course Units: ");
        double CrsUnits = new Scanner(System.in).nextDouble();
        
        
         try
        {
            Connection conn = DriverManager.getConnection(
                DATABASE_URL, "Demo18B", "Demo18B");// strings are username and password
            
            PreparedStatement insertNewCourse = conn.prepareStatement(QUERY);
            insertNewCourse.setString(1,CrsNum);
            insertNewCourse.setString(2,CrsName);
            insertNewCourse.setString(3,CrsDesc);
            insertNewCourse.setDouble(4,CrsUnits);
            
            insertNewCourse.executeUpdate();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
}
