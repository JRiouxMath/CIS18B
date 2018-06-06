/*
 * Strongly based on sample given by Prof. Conrad
 * "SimpleSchoolDatabase"
 */
package rioux_jeanine.inventory.application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.NumberFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.util.Callback;



public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField ItemName, Count, ProdID, SKU, Cost, Messages;

    @FXML
    private Button Btn_Export, Btn_Add, Btn_Update, Btn_Del;

    @FXML
    private Label label;

    @FXML
    private TextArea Description;
    
    @FXML
    private TableView InventTblView;
    
    ObservableList items;
    int prodNum;
    ResultSet rs = null;
    
    @FXML
    private void AddButtonHandler(ActionEvent e)
    {  
        
        try //Ensuring entry in count can be parsed as an Int
        {
            Integer.parseInt(Count.getText());
            try
            {
                Double.parseDouble(Cost.getText());
            
  
                new InventoryDatabase().addItem(ItemName.getText(),
                                                Description.getText(), Integer.parseInt(Count.getText()),
                                                SKU.getText(), Double.parseDouble(Cost.getText()));
                fillTable();

                ItemName.clear();
                Description.clear();
                Count.clear();
                SKU.clear();
                Cost.clear();
                InventTblView.requestFocus();
                Messages.setText("Item added to database");
            }// close try parseDouble
            catch (NumberFormatException exc)
            {
               Messages.setText("Cost must be a floating point number");
            }//close catch parseDouble
        }//close try parseInt
        catch(NumberFormatException ex)
        {
              Messages.setText("Count must be an Integer");
        }//close catch parseInt
    }// end Add BTN Handler
    
     private void fillTable()
    {
        InventTblView.setItems(null);
        InventTblView.getColumns().clear();
        try 
        {
            ResultSet rs = new InventoryDatabase().getResults();
            ////////////////////////////////////////////////////////////////////////////
            for(int i=0;i<rs.getMetaData().getColumnCount();i++)
            {
                TableColumn tableCol = new TableColumn(rs.getMetaData().getColumnName(i+1));        
                final int j=i;
                tableCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
                
                InventTblView.getColumns().addAll(tableCol);
            }
            
            items = FXCollections.observableArrayList();
            while( rs.next() )
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                       row.add(rs.getString(i+1));
                items.add(row);                                
            }   
            InventTblView.setItems(items);
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
           System.exit(1);
        }// end catch block
    }// end private method filltable 
    
    @FXML
    private void DeleteButtonHandler(ActionEvent e) throws SQLException
    {  
        //int curRow = rs.getRow();
        new InventoryDatabase().deleteItem(prodNum);
        fillTable();

        ItemName.clear();
        Description.clear();
        Count.clear();
        SKU.clear();
        Cost.clear();
        InventTblView.requestFocus();
        Messages.setText("Item deleted from database.");
    } // end method DeleteButtonHandler
    
    @FXML
    private void ExportButtonHandler(ActionEvent e)
    {
            Messages.setText("This button had just one job.  But I'm too tired to work.");
    }//end Export handler
    
    
    @FXML
    private void UpdateButtonHandler(ActionEvent e)// I haven't even started on this one
    {        
        new InventoryDatabase().updateItem(prodNum, ItemName.getText(), Description.getText(), 
                                        Integer.parseInt(Count.getText()),
                                        SKU.getText(), Double.parseDouble(Cost.getText()));
        
        //This calls the above method to repopulate the TableView 
        fillTable();

        //These just clear out the entry fields
        ItemName.clear();
        Description.clear();
        Count.clear();
        SKU.clear();
        Cost.clear();
        InventTblView.requestFocus();
        Messages.setText("Item updated.");
    }
    
   
    
   @Override  
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
        
         InventTblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(InventTblView.getSelectionModel().getSelectedItem() != null) 
                {    
                    TableViewSelectionModel selectionModel = InventTblView.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);                    
                    int val = tablePosition.getRow();
                    Object row = items.get(val);
                    String[] a = row.toString().split("[,]|[\\]]");
                    
                    //ProdID = Integer.parseInt(a[0].substring(1));
                    //ProdID.setText(a[0].trim());
                    prodNum = Integer.parseInt(a[0].substring(1).trim());
                    ItemName.setText(a[1].trim());
                    Description.setText(a[2].trim());
                    Count.setText(a[3].trim());
                    Cost.setText(a[4].trim());
                    SKU.setText(a[5].trim());
                }
          
            }//end method changed
        });//end listener fillTable
    }// end method initialize

}// end class Controller
