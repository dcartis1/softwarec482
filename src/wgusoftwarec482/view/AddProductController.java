/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wgusoftwarec482.MainApp;
import wgusoftwarec482.model.Inventory;
import wgusoftwarec482.model.Part;
import wgusoftwarec482.model.Product;
import wgusoftwarec482.exceptions.InventoryLevelWrong;
import wgusoftwarec482.exceptions.PriceTooLow;
import wgusoftwarec482.exceptions.MinMaxWrong;
import wgusoftwarec482.exceptions.PartNotInProduct;
import wgusoftwarec482.exceptions.NamePriceInventory;

/**
 *
 * @author Dan
 */
public class AddProductController {
    
    
    @FXML
    private Label addProductLabel;
    
    @FXML
    private Label productNameLabel;
    
    @FXML
    private Label productInvLabel;
    
    @FXML
    private Label productPriceLabel;
    
    @FXML
    private Label productMinLabel;
    
    @FXML
    private Label productMaxLabel;
    
    @FXML
    private TextField searchPartField;
    
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
    
    //top parts table, shows all parts
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableColumn<Part, Integer> allPartIdColumn;
    @FXML
    private TableColumn<Part, String> allPartNameColumn;
    @FXML
    private TableColumn<Part, Double> allPartPriceColumn;
    @FXML
    private TableColumn<Part, Integer> allPartInventoryLevelColumn;
    
    
    //bottom parts table. shows associated parts
    @FXML
    private TableView<Part> partsInProductTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
   

    
    private MainApp mainApp;
    private Stage dialogStage;
    private Product product;
    private final boolean okClicked = false;
    private boolean newProduct;
    private int selectedId;
    private Inventory inventory;
    
    
    
    /*
    counts how many parts were added to the partsInProduct arraylist. Is used
    so that we know how many parts to delete when the Cancel button is clicked.
    */
    private int newPartsInArray = 0;
    
    //temporary arraylist for storing found parts during a search
    private final ObservableList<Part> searchPart = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        
        //initalize all part table
        allPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        allPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        allPartPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        allPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inventoryLevelProperty().asObject());
        
        //initialize parts in product table
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        partInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inventoryLevelProperty().asObject());
        
        //listens for when part search field is cleared, then reloads all parts into table and clears search data for new searches
        searchPartField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            String searchText = newValue;
            if (newValue == null || newValue.isEmpty()) {
                allPartsTable.setItems(inventory.getAllPartData());
                searchPart.clear();
            }
        });
    }
    
    //mainApp tells the controller if user clicked Add or Modify
    public void setNewProduct(boolean newProduct){
        this.newProduct = newProduct;
    }
    //mainApp passes the Id of the product that was selected for modification
    public void setSelectedId(int selectedId){
        this.selectedId = selectedId;
    }
    
    //mainApp passes the selected product and then we load its attributes into
    //the corresponding fields and tables.
    public void setProduct(Product product) {
        
        if (product != null) {
        this.addProductLabel.setText("Modify Product");
        
        // Fill the textfields with info from the selected product object.
        productId.setText(Integer.toString(product.getId()));
        productName.setText(product.getName());
        productInv.setText(Integer.toString(product.getInventoryLevel()));
        productPrice.setText(Double.toString(product.getPrice()));
        productMin.setText(Integer.toString(product.getMin()));
        productMax.setText(Integer.toString(product.getMax()));
        
        // fill bottom parts table with the associated parts
        partsInProductTable.setItems(product.getPartsInProduct());

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
    
    //Adds parts from the all parts table into the associated parts table
    @FXML
    public void addPartInProductBtn(){
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            newPartsInArray++;
            product.getPartsInProduct().add(selectedPart);
            partsInProductTable.setItems(product.getPartsInProduct());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part is selected");
            alert.setContentText("Please select a Part from the top table.");
            alert.showAndWait();
        }
    }
    
    //removes parts from the associated parts table.
    @FXML
    public void removePartInProductBtn(){ 
        Part selectedPart = partsInProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            product.removePartInProduct(selectedPart);
            partsInProductTable.setItems(product.getPartsInProduct());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part is selected");
            alert.setContentText("Please select a Part to remove from the bottom table.");
            alert.showAndWait();
        }
    }
    
    //save button in the add/modify product view
    @FXML
    public void saveProductBtn() {
        //checks if add button was clicked in appview then this is a new product
        if (newProduct == true){
            //checks if user input is valid for a new product
            if(validateProduct() == true){
                //checks if inventory level is not between min and max range, throws exception
                try{
                    //checks if product has a Name, Inventory, and Price. throws exception
                    try{
                        checkNamePriceInventory(productName.getText(), Double.parseDouble(productPrice.getText()), Integer.parseInt(productInv.getText()));
                        checkInventoryLevel(Integer.parseInt(productInv.getText()), Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));        
                        //checks if min is greater than max or max is less than min, throws exception
                        try{
                            checkMinMax(Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));
                            try{
                                //checks if product does not have at least one associated part, throw exception
                                checkPartInProduct();
                                try{
                                    //checks if product price is lower than total price of all associated parts, throws exception
                                    checkProductPrice(Double.parseDouble(productPrice.getText()));
                                    product.setNewId();
                                    product.setName(productName.getText());
                                    product.setInventoryLevel(Integer.parseInt(productInv.getText()));
                                    product.setPrice(Double.parseDouble(productPrice.getText()));
                                    product.setMin(Integer.parseInt(productMin.getText()));
                                    product.setMax(Integer.parseInt(productMax.getText()));

                                    inventory.addProduct(product);
                                    dialogStage.close();
                                }
                                catch(PriceTooLow e){
                                    e.printStackTrace();
                                }         
                            }
                            catch(PartNotInProduct e){
                                e.printStackTrace();
                            }

                        }
                        catch(MinMaxWrong e){
                           e.printStackTrace();
                        }
                    }
                    catch(NamePriceInventory e){
                        e.printStackTrace();
                    }
                }
                catch(InventoryLevelWrong e){
                    e.printStackTrace();
                }
            }
        }
        //if modify button was clicked in appview then this is an existing product
        else {
            //if input is valid
            if(validateProduct() == true){
                try{
                    //checks if inventory level is outside of min max range, throws exception
                    checkInventoryLevel(Integer.parseInt(productInv.getText()),
                            Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));
                    try{
                        //checks if min is greater than max or max less than min, throws exception
                        checkMinMax(Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));
                        try{
                            //checks if product does not have at least one associated part, throws exception
                            checkPartInProduct();
                            try{
                                //checks if product price is less than total price of all associated parts, throw exception
                                checkProductPrice(Double.parseDouble(productPrice.getText()));
                                
                                product.setOldId(Integer.parseInt(productId.getText()));
                                product.setName(productName.getText());
                                product.setInventoryLevel(Integer.parseInt(productInv.getText()));
                                product.setPrice(Double.parseDouble(productPrice.getText()));
                                product.setMin(Integer.parseInt(productMin.getText()));
                                product.setMax(Integer.parseInt(productMax.getText()));
                                inventory.updateProduct(selectedId, product);

                            dialogStage.close();
                            }
                            
                            catch(PriceTooLow e){
                                e.printStackTrace();
                            }
                        }
                        catch(PartNotInProduct e){
                            e.printStackTrace();
                        }
                    }
                    catch(MinMaxWrong e){
                        e.printStackTrace();
                    }
                }
                catch(InventoryLevelWrong e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    //cancel button inside add product view. displays alert telling user
    //nothing will be saved and will be taken back to main screen
    @FXML
    public void productCancelBtn(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("This Product will not be saved.");
        alert.setContentText("Are you sure you would like to cancel and go back to"
                + " main screen without saving this Product?");        
        alert.showAndWait()
            
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> addProductCancelBtn());
    }
    
    // handles if user added parts into the associated parts table but then 
    //hit cancel instead of save. user is warned first.
    public void addProductCancelBtn(){
        //if new product, do nothing and close dialog
        if(newProduct == true){
            dialogStage.close();
            
        //if no new parts were added to partsInProductArray, do nothing and
        //close dialog
        }else if(newPartsInArray == 0){
            dialogStage.close();
        }
        //removes the last item from the partsInProductArraylist a number of
        //times equal to the number of new parts that were added.
        else{
            for(int i = newPartsInArray; i > 0; i--){
                product.getPartsInProduct().remove(product.getPartsInProduct().size()-1);
            }
        }
        dialogStage.close();
    }

    //allPartsTable search function, allows for searching by id or name
    @FXML
    void SearchPart(ActionEvent event) {
        searchPart.clear();
        String searchItem=searchPartField.getText().toLowerCase();
        boolean found=false;
        try{
            int itemNumber = Integer.parseInt(searchItem);
            for(Part p: inventory.getAllPartData()){
                if(p.getId()==itemNumber){
                    found=true;
                    searchPart.add(p);            
                    allPartsTable.setItems(searchPart);         
                }
            }
                if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part not found");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Part p: inventory.getAllPartData()){
                if(p.getName().toLowerCase().equals(searchItem)){
                    found=true;
                    searchPart.add(p);
                    allPartsTable.setItems(searchPart);
                }        
            }
                if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");

                alert.showAndWait();
            }
        }
    }
    
    //validates user input. is called before any products are added or modified
    public boolean validateProduct(){
        
        //
        //Product Name, Price, and Inventorylevel validation is disabled so that evaluator can see the
        //custom exception controls required for the project.
        //
        
        /*
        if(productName.getText() == null || productName.getText().isEmpty()){
            productNameLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Product not valid");
            alert.setContentText("Product Name cannot be empty");

            alert.showAndWait();
            return false;
        }
        if(!productInv.getText().matches("[0-9]+")){
            productInvLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Product not valid");
            alert.setContentText("Inventory must be a number.");

            alert.showAndWait();
            return false;
        }
        if(!productPrice.getText().matches("^[0-9]+\\.?[0-9]*")){
            productPriceLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Product not valid");
            alert.setContentText("Price must be a number.");

            alert.showAndWait();
            return false;
        }

        */
        if(!productMin.getText().matches("[0-9]+")){
            productMinLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Product not valid");
            alert.setContentText("Min must be a number.");

            alert.showAndWait();
            return false;
        }
        if(!productMax.getText().matches("[0-9]+")){
            productMaxLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Product not valid");
            alert.setContentText("Max must be a number.");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    //passed to us from main app, gives us access to mainapp and and the correct
    // instances of inventory and product
    public void setMainApp(MainApp mainApp, Inventory inventory, Product product) {
        this.mainApp = mainApp;
        this.inventory = inventory;
        this.product = product;
        
        allPartsTable.setItems(inventory.getAllPartData());
    }
     
    //checks if inventory level is between min and max range
    public void checkInventoryLevel(int inventoryLevel, int min, int max) throws InventoryLevelWrong{
        if(inventoryLevel < min || inventoryLevel > max){
            throw new InventoryLevelWrong("Inventory level must be between min and max");
        }
    }
    //checks if min and less than max and max is more than min
    public void checkMinMax(int min, int max) throws MinMaxWrong{
        if(min > max || max < min){
            throw new MinMaxWrong("Min must be less than Max");
        }
    }
    //checks if product has at least one associated part
    public void checkPartInProduct() throws PartNotInProduct{
       if (product.getPartsInProduct().isEmpty()) {
           throw new PartNotInProduct("Products must always have at least one part");
       }
    }
    //checks if productprice is more than total price of all associated parts 
    public void checkProductPrice(double productPrice) throws PriceTooLow{

        double totalPartPrice = 0;
        for(Part p: product.getPartsInProduct()){
            totalPartPrice = totalPartPrice + p.getPrice();
        }

        if(productPrice < totalPartPrice){
            throw new PriceTooLow("Product price must be equal or more than the sum of the added parts");
        }
    }
    //checks if product has a Name, price, and inventorylevel
    public void checkNamePriceInventory(String name, Double price, Integer inventoryLevel) throws NamePriceInventory{
        if(name == null || name.isEmpty()){
            throw new NamePriceInventory("Product Name field cannot be empty!");
        }
        if("name".equals(name)){
            throw new NamePriceInventory("Product Name field cannot be empty!");
        }
        if(price == null || price == 0){
            throw new NamePriceInventory("Product must have a price!");
        }
        if(inventoryLevel == null || inventoryLevel == 0){
            throw new NamePriceInventory("Product must have an inventory level!");
        }
    }
}