
package rioux_jeanine.inventory.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDatabase {
    
    static final String DB_URL = "jdbc:derby://localhost:1527/InventoryDatabase";
    static final String USERNAME = "Inventory";
    static final String PASSWORD = "demo";
    
    static final String SELECT_QUERY = "SELECT ID, ItemName, Description, Count, Sku, Cost FROM Inventory";
    static final String DELETE_QUERY = "DELETE FROM Inventory WHERE ID=?";
    static final String UPDATE_QUERY = "UPDATE Inventory SET ITEMNAME=?, DESCRIPTION=?, COUNT=?, SKU=?, COST=?, WHERE ID=?";
    
    ResultSet resSet = null;
    
    InventoryDatabase()
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement selectAllItems = connection.prepareStatement(SELECT_QUERY);
            resSet = selectAllItems.executeQuery();
        }//end try
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
           System.exit(1);
        }// end catch
        
    }// end constructor
    
    public void addItem(int id,  String itemname, String desc, int count, String Sku, double cost )
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement insertItem = connection.prepareStatement("INSERT INTO Inventory (ID, ITEMNAME, DESCRIPTION, COUNT, SKU, COST) VALUES (?,?,?,?,?,?)");
            insertItem.setInt(1, id);
            insertItem.setString(2,itemname);
            insertItem.setString(3,desc);
            insertItem.setInt(4,count);
            insertItem.setString(5,Sku);
            insertItem.setDouble(6, cost);
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
            deleteItem.setInt(1, id);
            deleteItem.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(4);
        }
    } // end method deleteItem
   
   public void updateItem(int P_id, String Name, String Description, int Count, String SKU, double Cost)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement updateItem = connection.prepareStatement(UPDATE_QUERY);
            updateItem.setInt(1, P_id);
            updateItem.setString(2,Name);
            updateItem.setString(3,Description);
            updateItem.setInt(4,Count);
            updateItem.setString(5,SKU);
            updateItem.setDouble(6, Cost);
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
}
