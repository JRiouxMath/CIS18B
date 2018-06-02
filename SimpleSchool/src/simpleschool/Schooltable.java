/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trysimpleschool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author paulconrad
 */
public class School_StudentTable {

    static final String DB_URL = "jdbc:derby://localhost:1527/SimpleSchool";
    static final String USERNAME = "School";
    static final String PASSWORD = "demo";
    
    static final String selectQuery = "SELECT * FROM Student";
    static final String updateQuery = "UPDATE Student SET STUDENTID=?, LASTNAME=?, FIRSTNAME=?, EMAIL=?, PHONENUMBER=? WHERE ID=?";
    static final String deleteQuery = "DELETE FROM Student WHERE ID=?";
    
    private static ResultSet _rs=null;

    private int max_st_char, max_lname_char, max_fname_char;
    private int max_email_char, max_phone_char;
    
    School_StudentTable()
    {
        try 
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement selectAllStudents = connection.prepareStatement(selectQuery);
            _rs = selectAllStudents.executeQuery();
            
            max_st_char = _rs.getMetaData().getPrecision(2);
            max_fname_char = _rs.getMetaData().getPrecision(3);
            max_lname_char = _rs.getMetaData().getPrecision(4);
            max_email_char = _rs.getMetaData().getPrecision(5);
            max_phone_char = _rs.getMetaData().getPrecision(6);
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
    
    public void updateStudent(int id, int st_id, String st_lname, String st_fname, String st_email, String st_phone)
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
        return new School_StudentTable()._rs;
    }
}
