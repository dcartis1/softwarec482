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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import wgusoftwarec482.MainApp;
import wgusoftwarec482.model.Inventory;
import wgusoftwarec482.model.Part;
import wgusoftwarec482.model.Product;

public class AppViewController {
    
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Integer> partMinColumn;
    @FXML
    private TableColumn<Part, Integer> partMaxColumn;
    
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Integer> productMinColumn;
    @FXML
    private TableColumn<Product, Integer> productMaxColumn;
    
    @FXML
    private TextField searchProductField;
    @FXML
    private TextField searchPartField;
    
    private ObservableList<Product> searchProduct = FXCollections.observableArrayList();
    private ObservableList<Part> searchPart = FXCollections.observableArrayList();

    // Reference to the main application.
    private MainApp mainApp;
    private Inventory inventory;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
  
    public AppViewController() {

    }
    
    @FXML
    private void handleNewProduct() {
    
        boolean okClicked = mainApp.showAddProductView();
        
    }
    
    @FXML 
    private void handleNewPart(){
    
        boolean okClicked = mainApp.showAddPartView();
    }
    
    //calls the overloaded showAddProduct method in mainapp.
    //passes the selected object to mainApp so it can then be passed to the AddProductController.
    //the Id of the object is passed along as well for updating purposes in the allPartData arraylist.
    @FXML
    private void handleModifyProduct() {
    
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        
        if (selectedProduct != null) {
            
            int selectedId = selectedProduct.idProperty().getValue();
            boolean okClicked = mainApp.showAddProductView(selectedProduct, selectedId);
        }
        else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product in the table.");

            alert.showAndWait();
        }
    }
    
    //calls the overloaded ShowAddPart method in Main app
    //passes the selected object to mainApp so it can then be passed to the AddPartController.
    //the Id of the object is passed along as well for updating purposes in the allPartData arraylist
    @FXML
    private void handleModifyPart() {
    
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            
            int selectedId = selectedPart.idProperty().getValue();
            boolean okClicked = mainApp.showAddPartView(selectedPart, selectedId);
        }
        else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product in the table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void exitApplication(ActionEvent event) {
        System.exit(0);
    }
   
       
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
       
        // Initialize the part table
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        partInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inventoryLevelProperty().asObject());
        
        
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        productInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inventoryLevelProperty().asObject());
        
     
        //listens for when product search field is cleared, then reloads all products into table and clears search data for new searches
        searchProductField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            String searchText = newValue;
            if (newValue == null || newValue.isEmpty()) {
                productTable.setItems(inventory.getProductData());
                searchProduct.clear();
            }
        });
        //listens for when part search field is cleared, then reloads all parts into table and clears search data for new searches
        searchPartField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            String searchText = newValue;
            if (newValue == null || newValue.isEmpty()) {
                partTable.setItems(inventory.getAllPartData());
                searchPart.clear();
            }
        });
    }

    @FXML
    void SearchProduct(ActionEvent event) {
        searchProduct.clear();
        String searchItem=searchProductField.getText().toLowerCase();
        boolean found=false;
        try{
            int itemNumber = Integer.parseInt(searchItem);
            for(Product p: inventory.getProductData()){
                if(p.getId()==itemNumber){
                    found=true;
                    searchProduct.add(p);
                    productTable.setItems(searchProduct);
                }
            }
                if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Product not found");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Product p: inventory.getProductData()){
                if(p.getName().toLowerCase().equals(searchItem)){
                    found=true;
                    searchProduct.add(p);
                    productTable.setItems(searchProduct);
                }        
            }
                if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Product not found");

                alert.showAndWait();
            }
        }
    }
    
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
                    partTable.setItems(searchPart);         
                }
            }
                if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Product not found");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Part p: inventory.getAllPartData()){
                if(p.getName().toLowerCase().equals(searchItem)){
                    found=true;
                    searchPart.add(p);
                    partTable.setItems(searchPart);
                }        
            }
                if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Product not found");

                alert.showAndWait();
            }
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     * @param inventory
     */
    public void setMainApp(MainApp mainApp, Inventory inventory) {
        this.mainApp = mainApp;
        this.inventory = inventory;
        
        partTable.setItems(inventory.getAllPartData());
        productTable.setItems(inventory.getProductData());
   
    }
    
}