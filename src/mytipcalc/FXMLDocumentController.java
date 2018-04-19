/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytipcalc;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;



import javafx.scene.control.Button;
        

public class FXMLDocumentController  {
    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent = NumberFormat.getPercentInstance();
    
    private BigDecimal tipPercentage = new BigDecimal(0.15);
  
    @FXML
    private TextField tipTextField;

    @FXML
    private Label tipPercLabel;

    @FXML
    private TextField amtTextField;

    @FXML
    private TextField totalTextField;
    
    @FXML
    private Slider tipPercSlider;
    
    @FXML
    private Button CalcButn;
    
    @FXML
    public void calculateButtonPressed(ActionEvent event) {
        try
        {
            BigDecimal amount = new BigDecimal(amtTextField.getText());
            BigDecimal tip = amount.multiply(tipPercentage);
            BigDecimal total = amount.add(tip);
            
            tipTextField.setText(currency.format(tip));
            totalTextField.setText(currency.format(total));
        }// end try block
        catch (NumberFormatException ex)
        {
            amtTextField.setText("Enter amount");
            amtTextField.selectAll();
            amtTextField. requestFocus();
        }// end catch block
    }// end calculateButtonPRessed event handler
    
    //@Override
    public void initialize() {
        currency.setRoundingMode (RoundingMode.HALF_UP);
        
        // Listener for changes to tipPercSlider
        
        tipPercSlider.valueProperty().addListener( new ChangeListener<Number>()
        {
            @Override
            public void changed (ObservableValue<? extends Number> ov,
                       Number oldValue, Number newValue)
                       {
                           tipPercentage = 
                                BigDecimal.valueOf(newValue.intValue() / 100.0);
                           tipPercLabel.setText(percent.format(tipPercentage));
                       }//end nested method change ov
        } // end ChangeListener
        );
    } // end initializer   
    
}// end controller class
