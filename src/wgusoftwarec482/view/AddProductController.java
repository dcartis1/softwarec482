/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgusoftwarec482.MainApp;
import wgusoftwarec482.model.Inventory;
import wgusoftwarec482.model.Part;
import wgusoftwarec482.model.Product;

/**
 *
 * @author Dan
 */
public class AddProductController {
    
    
    @FXML
    private Label addProductLabel;
    
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
    
    private int newPartsInArray = 0;
    private int currentPartsInArraySize;
    
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
    
    public void setNewProduct(boolean newProduct){
        this.newProduct = newProduct;
    }
    public void setSelectedId(int selectedId){
        this.selectedId = selectedId;
    }
    
    
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
    
    @FXML
    public void addPartInProductBtn(){
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            newPartsInArray++;
            product.getPartsInProduct().add(selectedPart);
            partsInProductTable.setItems(product.getPartsInProduct());
        }
        else {//part not selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part is selected");
            alert.setContentText("Please select a Part from the top table.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void removePartInProductBtn(){
       
        Part selectedPart = partsInProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            
            product.removePartInProduct(selectedPart);
            partsInProductTable.setItems(product.getPartsInProduct());
        }
        else {//part not selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part is selected");
            alert.setContentText("Please select a Part to remove from the bottom table.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void saveProductBtn() {
         //main app sets this to true if add product button is clicked.
        if (newProduct == true){
        product.setNewId();
        product.setName(productName.getText());
        product.setInventoryLevel(Integer.parseInt(productInv.getText()));
        product.setPrice(Double.parseDouble(productPrice.getText()));
        product.setMin(Integer.parseInt(productMin.getText()));
        product.setMax(Integer.parseInt(productMax.getText()));
        
        inventory.addProduct(product);
        dialogStage.close();
        }
        else {
            
            product.setOldId(Integer.parseInt(productId.getText()));
            product.setName(productName.getText());
            product.setInventoryLevel(Integer.parseInt(productInv.getText()));
            product.setPrice(Double.parseDouble(productPrice.getText()));
            product.setMin(Integer.parseInt(productMin.getText()));
            product.setMax(Integer.parseInt(productMax.getText()));
            inventory.updateProduct(selectedId, product);
            
            dialogStage.close();
        }

    }
    
    
    // cancel button inside add/modify product view. handles if user added
    //parts into the partsInProductArray but then hit cancel instead of save.
    @FXML
    public void addProductCancelBtn(){
        //if new product, do nothing and close dialog
        if(newProduct == true){
            dialogStage.close();
            
        //if no new parts were added to partsInProductArray, do nothing and
        //close dialog
        }else if(newPartsInArray == 0){
            dialogStage.close();
        }
        //removes the last item from the partsInProductArraylist equal to the
        //number of parts that were added.
        else{
            for(int i = newPartsInArray; i > 0; i--){
                product.getPartsInProduct().remove(product.getPartsInProduct().size()-1);
            }
        }
        dialogStage.close();

    }
    
    
    //allPartsTable search function
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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

     public void setMainApp(MainApp mainApp, Inventory inventory, Product product) {
        this.mainApp = mainApp;
        this.inventory = inventory;
        this.product = product;
        
        allPartsTable.setItems(inventory.getAllPartData());
    }
    
}
