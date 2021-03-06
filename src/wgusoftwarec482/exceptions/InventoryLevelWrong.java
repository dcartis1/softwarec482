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
//custom checked exception for inventorylevel value
public class InventoryLevelWrong extends Exception {
    public InventoryLevelWrong() {}
    public InventoryLevelWrong(String message)
    {
        super(message);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exception Error");
        alert.setHeaderText("Product/Part not valid");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
