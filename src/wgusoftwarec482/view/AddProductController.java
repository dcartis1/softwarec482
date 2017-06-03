/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgusoftwarec482.MainApp;
import wgusoftwarec482.model.Product;

/**
 *
 * @author Dan
 */
public class AddProductController {
    
    
    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField productInv;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMin;
    @FXML
    private TextField productMax;

    
    private MainApp mainApp;
     
    private Stage dialogStage;
    private Product product;
    private final boolean okClicked = false;
    public boolean newProduct;
    private int selectedIndex;

    @FXML
    private void initialize() {
    }
    
    public void setIndex(int selectedIndex){
        this.selectedIndex = selectedIndex;
    }
    
    public void setProduct(Product product) {
    if (product != null) {
        
        // Fill the textfields with info from the selected product object.
        productId.setText(Integer.toString(product.getId()));
        productName.setText(product.getName());
        productInv.setText(Integer.toString(product.getInventoryLevel()));
        productPrice.setText(Double.toString(product.getPrice()));
        productMin.setText(Integer.toString(product.getMin()));
        productMax.setText(Integer.toString(product.getMax()));   

    } else {
        // Product is null, remove all the text.
        productId.setText("");
        productName.setText("");
        productInv.setText("");
        productPrice.setText("");
        productMin.setText("");
        productMax.setText("");
    }
}
    
    @FXML
    public void addProduct() {
        //main app sets this to true if add product button is clicked.
        if (newProduct == true){
        
        product = new Product();
        product.setNewId();
        product.setName(productName.getText());
        product.setInventoryLevel(Integer.parseInt(productInv.getText()));
        product.setPrice(Double.parseDouble(productPrice.getText()));
        product.setMin(Integer.parseInt(productMin.getText()));
        product.setMax(Integer.parseInt(productMax.getText()));
        
        mainApp.getProductData().add(product);
        dialogStage.close();
        }
        else {
            
            product = new Product();
            product.setOldId(Integer.parseInt(productId.getText()));
            product.setName(productName.getText());
            product.setInventoryLevel(Integer.parseInt(productInv.getText()));
            product.setPrice(Double.parseDouble(productPrice.getText()));
            product.setMin(Integer.parseInt(productMin.getText()));
            product.setMax(Integer.parseInt(productMax.getText()));
            
            mainApp.getProductData().set(selectedIndex, product);
            dialogStage.close();         
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    

    @FXML
    private void addProductCancelBtn() {
        dialogStage.close();
    }


    
     public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

}
    
}
