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
    private TextField ItemName, Count, ProdID, SKU, Cost, NameSearch;

    @FXML
    private Button Btn_Export, Btn_Add, Btn_Search, Btn_Update, Btn_Del;

    @FXML
    private Label label;

    @FXML
    private TextArea Description;
    
    @FXML
    private TableView InventTblView;
    
    ObservableList items;
    int prodNum;
    private static final NumberFormat currency = 
      NumberFormat.getCurrencyInstance();
    
    @FXML
    private void AddButtonHandler(ActionEvent e)
    {       
        System.out.println("Add handler entered");
        new InventoryDatabase().addItem(ItemName.getText(),
                                        Description.getText(), Integer.parseInt(Count.getText()),
                                        SKU.getText(), Double.parseDouble(Cost.getText()));
        System.out.println("Line 62 reached");
        fillTable();
        //ProdID.clear();
        ItemName.clear();
        Description.clear();
        Count.clear();
        SKU.clear();
        Cost.clear();
        InventTblView.requestFocus();
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
        
        
        new InventoryDatabase().deleteItem(prodNum);
        fillTable();
        //ProdID.clear();
        ItemName.clear();
        Description.clear();
        Count.clear();
        SKU.clear();
        Cost.clear();
        InventTblView.requestFocus();
    } // end method DeleteButtonHandler
    
    @FXML
    void SearchButtonHandler(ActionEvent event) { //this is all wrong.  copied from regex, still trying to adapt
        
/**        AltText.setText(FindItem(NameSearch.getText(), ItemName.getText(), ReplText.getText()));
    }// end method SearchButtonHandler.  calls private method Replace String.
    
     private String FindItem (String SName, String IName)//This isn't searching all rows
    {
        Pattern regExPatt = Pattern.compile(SName);       
        Matcher regExMatch = regExPatt.matcher(IName);
        
        if (regExMatch.find())
        {
            //Here is where it will populate like UPdateItem
        }//end if find
        
        return origText + "\nNo replacement made";// probably delete and have void return?
    }// end Method ReplaceString  only used by SearchButtonHandler
    * 
    **88888888888888 --------------Two methods commented out here, watch for extra close }
    **/}
    
    
    @FXML
    private void UpdateButtonHandler(ActionEvent e)// I haven't even started on this one
    {        
        //This method call goes to class InventoryDatabase.  I know the signature needs to match
        //This is my biggest problem on all these methods.  There is an issue with the Product Id
        //Since I don't want the user to access it, I have to handle it differently.  I removed the field and 
        //that ultimately led me to getting Add to work.  But for update and delete, I need to access
        // the data in that cell. Herein lies my main issue. 
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
        
        //InventTblView.requestFocus();
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
                   // ProdID.setText(a[0].trim());
                    ItemName.setText(a[1].trim());
                    Description.setText(a[2].trim());
                    Count.setText(a[3].trim());
                    Cost.setText(a[4].trim());
                    SKU.setText(a[5].trim());
                }
                

        /**  These were put in automatically, it doesn't look like I need them
                assert ItemName != null : "fx:id=\"ItemName\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Count != null : "fx:id=\"Count\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Btn_Export != null : "fx:id=\"Btn_Export\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Btn_Add != null : "fx:id=\"Btn_Add\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Btn_Search != null : "fx:id=\"Btn_Search\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Btn_Update != null : "fx:id=\"Btn_Update\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert ProdID != null : "fx:id=\"ProdID\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert SKU != null : "fx:id=\"SKU\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Cost != null : "fx:id=\"Cost\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                assert Btn_Del != null : "fx:id=\"Btn_Del\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
            
            * 
            **/
            
            }//end method changed
            });//end listener fillTable
    }// end method initialize

}// end class Controller
