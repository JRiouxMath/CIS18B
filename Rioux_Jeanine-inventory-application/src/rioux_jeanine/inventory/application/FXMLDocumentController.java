/*
 * Strongly based on sample given by Prof. Conrad
 * "SiimpleSchoolDatabase"
 */
package rioux_jeanine.inventory.application;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.sql.ResultSet;
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
    private TextField ItemName, Count, ProdID, SKU, Cost;

    @FXML
    private Button Btn_Export, Btn_Add, Btn_Search, Btn_Update, Btn_Del;

    @FXML
    private Label label;

    @FXML
    private TextArea Description;
    
    @FXML
    private TableView InventTblView;
    
    ObservableList items;
    
    @FXML
    private void AddButtonHandler(ActionEvent e)
    {        
        new InventoryDatabase().addItem(Integer.parseInt(ProdID.getText()), ItemName.getText(),
                                        Description.getText(), Integer.parseInt(Count.getText()),
                                        SKU.getText(), Double.parseDouble(Cost.getText()));
        fillTable();
        ProdID.clear();
        ItemName.clear();
        Description.clear();
        Count.clear();
        SKU.clear();
        Cost.clear();
        InventTblView.requestFocus();
    }// end Add BTN Handlers
    
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
                    ProdID.setText(a[0].trim());
                    ItemName.setText(a[1].trim());
                    Description.setText(a[2].trim());
                    Count.setText(a[3].trim());
                    Cost.setText(a[4].trim());
                    SKU.setText(a[5].trim());
                }
                

        
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
            }//end method changed
            });//end listener fillTable
    }// end method initialize

}// end class Controller
