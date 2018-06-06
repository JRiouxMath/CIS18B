
package rioux_jeanine.inventory.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.*;
import java.util.regex.Pattern;

public class InventoryDatabase {
    
    static final String DB_URL = "jdbc:derby://localhost:1527/InventoryDatabase";
    static final String USERNAME = "Inventory";
    static final String PASSWORD = "demo";
    
    static final String SELECT_QUERY = "SELECT * FROM Inventory";
    static final String DELETE_QUERY = "DELETE FROM Inventory WHERE ID=?";
    static final String UPDATE_QUERY = "UPDATE Inventory SET ITEMNAME=?, DESCRIPTION=?, COUNT=?, SKU=?, COST=? WHERE ID=?";
    
    ResultSet resSet = null;
    
    private int max_char_IName, max_char_Desc, max_char_Count, max_char_sku, max_char_cost;
    
    InventoryDatabase()
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement selectAllItems = connection.prepareStatement(SELECT_QUERY);
            resSet = selectAllItems.executeQuery();
            
            max_char_IName = resSet.getMetaData().getColumnDisplaySize(2)-1;
            max_char_Desc = resSet.getMetaData().getColumnDisplaySize(3);
           // max_char_Count = resSet.getMetaData().getPrecision(5);
            max_char_sku = resSet.getMetaData().getColumnDisplaySize(5);
            //max_char_cost  = resSet.getMetaData().getPrecision(6);
        }//end try
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
           System.exit(1);
        }// end catch
        
    }// end constructor
    
    public void addItem(String itemname, String description, int count, String Sku, double cost )
    {
        try
        {

            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement insertItem = connection.prepareStatement("INSERT INTO Inventory (ITEMNAME, DESCRIPTION, COUNT, SKU, COST) VALUES (?,?,?,?,?)");
            if( itemname.length() > max_char_IName) itemname = itemname.substring(0, max_char_IName);
            insertItem.setString(1,itemname);
            if( description.length() > max_char_Desc) description = description.substring(0, max_char_Desc);
            insertItem.setString(2,description);
                insertItem.setInt(3,count);
            if( Sku.length() > max_char_sku) Sku = Sku.substring(0, max_char_sku);
            insertItem.setString(4,Sku);
            insertItem.setDouble(5, cost);
            insertItem.executeUpdate();  
        
        }// end try block
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(2);
        }// end catch block
    }// end method addItem
    
    
   public void deleteItem(int id)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement deleteItem = connection.prepareStatement(DELETE_QUERY);
            
            deleteItem.setInt(1,id);
            deleteItem.executeUpdate();           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(4);
        }
    } // end method deleteItem
   
   public void exportTable() throws SQLException
   {
       System.out.println("It's a sad day when I give myself permission to stop working.");
   }// end method to export table
   
   public void updateItem(int ProdID, String itemname, String description, int Count, String Sku, double Cost)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement updateItem = connection.prepareStatement(UPDATE_QUERY);
             if( itemname.length() > max_char_IName) itemname = itemname.substring(0, max_char_IName);
            updateItem.setString(1,itemname);
             if( description.length() > max_char_Desc) description = description.substring(0, max_char_Desc);
            updateItem.setString(2,description);
            updateItem.setInt(3,Count);
             if( Sku.length() > max_char_sku) Sku = Sku.substring(0, max_char_sku);
            updateItem.setString(4,Sku);
            updateItem.setDouble(5, Cost);
            updateItem.setInt(6, ProdID);
            updateItem.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(3);
        }
    }// end method updateItem
    
    public ResultSet getResults()
    {
        if (resSet != null) return resSet;
        return new InventoryDatabase().resSet;
    }// end method getResults
    
    public static String getUsername()
    {
        return USERNAME;
    }// end username getter
    
    public static String getPassword()
    {
        return PASSWORD;
    }// end password getter
    
   
}
