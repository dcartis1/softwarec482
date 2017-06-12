/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author Dan
 */
//custom checked expression for ensuring product price is more than total price of all associated parts
public class PriceTooLow extends Exception {
    public PriceTooLow() {}
    public PriceTooLow(String message)  
    {
        super(message);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exception Error");
        alert.setHeaderText("Product not valid");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
