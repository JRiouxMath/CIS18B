package rioux_jeanine.inventory.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jrioux
 */
public class LoginSceneController implements Initializable {
    
    @FXML
    private TextField Messages, UsernameField, PasswordField;
    @FXML
    private Button LoginButn;

   public void loginButnHandler (ActionEvent event) throws IOException{
       //System.out.println("Username field has: " + UsernameField.getText());
       //System.out.println("Username is: "+ InventoryDatabase.getUsername());
       if(UsernameField.getText().equals(InventoryDatabase.getUsername()) && PasswordField.getText().equals(InventoryDatabase.getPassword()))
       {
       Parent toGet_DB_Gui = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
       Scene DB_GUI = new Scene(toGet_DB_Gui);
       
       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(DB_GUI);
       window.show();
       }
       else
        Messages.setText("Please enter Inventory and demo");
   }// end Login button handler
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
