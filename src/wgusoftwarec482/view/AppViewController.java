/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import wgusoftwarec482.MainApp;
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
    private TextField searchTextField;
    
    private ObservableList<Product> searchData = FXCollections.observableArrayList();

    // Reference to the main application.
    private MainApp mainApp;

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
    
    //calls the overloaded showAddProduct method in mainapp for modifying a product
    @FXML
    private void handleModifyProduct() {
    
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        
        if (selectedProduct != null) {
            boolean okClicked = mainApp.showAddProductView(selectedProduct, selectedIndex);

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

//        productMinColumn.setCellValueFactory(cellData -> cellData.getValue().minProperty().asObject());
//        productMaxColumn.setCellValueFactory(cellData -> cellData.getValue().maxProperty().asObject());
        

        //listens for when search field is cleared, then reloads all products into table and clears search data for new searches
        searchTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            String searchText = newValue;
            if (newValue == null || newValue.isEmpty()) {
                productTable.setItems(mainApp.getProductData());
                searchData.clear();
            }
        });
    }

    @FXML
    void SearchTable(ActionEvent event) {

    String searchItem=searchTextField.getText();
    boolean found=false;
    try{
        int itemNumber = Integer.parseInt(searchItem);
        for(Product p: mainApp.getProductData()){
            if(p.getId()==itemNumber){
                found=true;
                searchData.add(p);            
                productTable.setItems(searchData);         
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
        for(Product p: mainApp.getProductData()){
            if(p.getName().equals(searchItem)){
                found=true;
                searchData.add(p);
                productTable.setItems(searchData);
            }        
        }
            if (found==false){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Product not found");

            alert.showAndWait();
        }
    }}

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        partTable.setItems(mainApp.getPartData());
        productTable.setItems(mainApp.getProductData());
    }
    
}