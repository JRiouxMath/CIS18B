/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trysimpleschool;

import java.net.URL;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 *
 * @author paulconrad
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private TableView tableView;
    @FXML private Button addButton;
    @FXML private TextField stu_id, stu_fname, stu_lname, stu_email, stu_phone;
    
    ObservableList items;
    private int row_id;
    
    @FXML
    private void handleAddButton(ActionEvent e)
    {        
        new School_StudentTable().addStudent(Integer.parseInt(stu_id.getText()), stu_lname.getText(),
                                        stu_fname.getText(), stu_email.getText(), stu_phone.getText());
        fillTable();
        stu_id.clear();
        stu_lname.clear();
        stu_fname.clear();
        stu_email.clear();
        stu_phone.clear();
        tableView.requestFocus();
    }
    
    @FXML
    private void handleUpdateButton(ActionEvent e)
    {        
        new School_StudentTable().updateStudent(row_id,Integer.parseInt(stu_id.getText()), stu_lname.getText(),
                                        stu_fname.getText(), stu_email.getText(), stu_phone.getText());
        fillTable();
        stu_id.clear();
        stu_lname.clear();
        stu_fname.clear();
        stu_email.clear();
        stu_phone.clear();
        tableView.requestFocus();
    }
    
    @FXML
    private void handleDeleteButton(ActionEvent e)
    {        
        new School_StudentTable().deleteStudent(row_id);
        fillTable();
        stu_id.clear();
        stu_lname.clear();
        stu_fname.clear();
        stu_email.clear();
        stu_phone.clear();
        tableView.requestFocus();
    }
    private void fillTable()
    {
        tableView.setItems(null);
        tableView.getColumns().clear();
        try 
        {
            ResultSet rs = new School_StudentTable().getResults();
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
                
                tableView.getColumns().addAll(tableCol);
            }
            
            items = FXCollections.observableArrayList();
            while( rs.next() )
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                       row.add(rs.getString(i+1));
                items.add(row);                                
            }   
            tableView.setItems(items);
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
           System.exit(1);
        }
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
                
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(tableView.getSelectionModel().getSelectedItem() != null) 
                {    
                    TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);                    
                    int val = tablePosition.getRow();
                    Object row = items.get(val);
                    String[] a = row.toString().split("[,]|[\\]]");
                    row_id = Integer.parseInt( a[0].substring(1) );
                    stu_id.setText(a[1].trim());
                    stu_lname.setText(a[2].trim());
                    stu_fname.setText(a[3].trim());
                    stu_email.setText(a[4].trim());
                    stu_phone.setText(a[5].trim());
                }
         }
     });
    }
}
