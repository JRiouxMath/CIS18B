
package rioux_jeanine.inventory.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDatabase {
    
    static final String DB_URL = "jdbc:derby://localhost:1527/Inventory";
    static final String SELECT_QUERY = "SELECT ID, ItemName, Description, Count, Sku, Cost FROM InventoryDB";
    static final String USERNAME = "Sample";
    static final String PASSWORD = "demo";
    
    static final ResultSet resSet = null;
    
    InventoryDatabase()
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement selectAllItems = connection.prepareStatement(SELECT_QUERY);
        }//end try
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
           System.exit(1);
        }// end catch
        
    }// end constructor
    
    public void addItem(int id, String itemname, String desc, int count, String Sku, double cost )
    {
        try
        {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement insertItem = connection.prepareStatement("INSERT INTO Inventory (ID, ITEMNAME, DESC, COUNT, SKU, COST) VALUES (?,?,?,?,?,?)");
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
    
    
    public ResultSet getResults()
    {
        if (resSet != null) return resSet;
        return new InventoryDatabase().resSet;
    }// end method getResults
}
