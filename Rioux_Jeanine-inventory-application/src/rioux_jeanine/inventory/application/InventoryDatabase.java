
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
    
    static final String SELECT_QUERY = "SELECT * FROM Inventory";
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
    
    public void addItem( String itemname, String description, int count, String Sku, double cost )
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement insertItem = connection.prepareStatement("INSERT INTO Inventory (ITEMNAME, DESCRIPTION, COUNT, SKU, COST) VALUES (?,?,?,?,?)");
           // insertItem.setInt(1, id);
            insertItem.setString(1,itemname);
            insertItem.setString(2,description);
            insertItem.setInt(3,count);
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
        System.out.println ("deleteItem entered with paramter: " + id);
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement deleteItem = connection.prepareStatement(DELETE_QUERY);
            int toPass = resSet.getInt("ID");
            deleteItem.setInt(1,toPass);
            deleteItem.executeUpdate();   
            
            System.out.println("leaving deleteItem");
                    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(4);
        }
    } // end method deleteItem
   
   public void updateItem(int ProdID, String Name, String Description, int Count, String SKU, double Cost)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement updateItem = connection.prepareStatement(UPDATE_QUERY);
            //updateItem.setInt(1, P_id);
            updateItem.setString(1,Name);
            updateItem.setString(2,Description);
            updateItem.setInt(3,Count);
            updateItem.setString(4,SKU);
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
    
   
}
