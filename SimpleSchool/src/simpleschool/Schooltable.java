package SimpleSchool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author paulconrad
 */
public class Schooltable {

    static final String DB_URL = "jdbc:derby://localhost:1527/School";
    static final String USERNAME = "school";
    static final String PASSWORD = "demo";
    static final String deleteQuery = "DELETE FROM Student WHERE STUDENTID=?";
    static final String updateQuery =  "UPDATE Student SET STUDENTID=?, LASTNAME=?, FIRSTNAME=?, EMAIL=?, PHONENUMBER=? WHERE ID=?";
    private static ResultSet _rs=null;

    Schooltable()
    {
        try 
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement selectAllStudents = connection.prepareStatement("SELECT * FROM Student");
            _rs = selectAllStudents.executeQuery();
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
           System.exit(1);
        }
    }
    
    public void addStudent(int st_id, String st_lname, String st_fname, String st_email, String st_phone )
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement insertStudent = connection.prepareStatement("INSERT INTO Student (STUDENTID, LASTNAME, FIRSTNAME, EMAIL, PHONENUMBER) VALUES (?,?,?,?,?)");
            insertStudent.setInt(1, st_id);
            insertStudent.setString(2,st_lname);
            insertStudent.setString(3,st_fname);
            insertStudent.setString(4,st_email);
            insertStudent.setString(5,st_phone);
            insertStudent.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(2);
        }
    }
    
    public void updateStudent(int st_id, String st_lname, String st_fname, String st_email, String st_phone, int id)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement updateStudent = connection.prepareStatement(updateQuery);
            updateStudent.setInt(1, st_id);
            updateStudent.setString(2,st_lname);
            updateStudent.setString(3,st_fname);
            updateStudent.setString(4,st_email);
            updateStudent.setString(5,st_phone);
            updateStudent.setInt(6,id);
            updateStudent.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(3);
        }
    }
    
    public void deleteStudent(int id)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement deleteStudent = connection.prepareStatement(deleteQuery);
            deleteStudent.setInt(1, id);
            deleteStudent.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(4);
        }
    }
    
    public ResultSet getResults()
    {
        if (_rs != null) return _rs;
        return new Schooltable()._rs;
    }
}
