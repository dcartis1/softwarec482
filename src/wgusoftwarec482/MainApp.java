/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wgusoftwarec482.view.AppViewController;
import wgusoftwarec482.view.AddProductController;
import wgusoftwarec482.model.Part;
import wgusoftwarec482.model.Product;
import wgusoftwarec482.model.Inventory;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Part> partData = FXCollections.observableArrayList();
    private ObservableList<Product> productData = FXCollections.observableArrayList();

    public MainApp() {
       // partData.add(new Part(2,"part 1", 10.50, 8, 1, 10));
        productData.add(new Product(0, "poop", 5.55, 15, 1, 10));
        productData.add(new Product(0, "fart", 8.88, 8, 1, 8));
        productData.add(new Product(0, "poop", 10.20, 5, 1, 10));
        productData.add(new Product(0, "product 3", 8.17, 1, 1, 10));
    }

    public ObservableList<Part> getPartData() {
        return partData;
    }
    
    public ObservableList<Product> getProductData() {
        return productData;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WGUSoftwareC482 - Daniel Cartisano");

        initRootLayout();

        showInventoryOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the inventory overview inside the root layout.
     */
    public void showInventoryOverview() {
        try {
            // Load Inventory overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AppView.fxml"));
            AnchorPane inventoryOverview = (AnchorPane) loader.load();

            // Set app overview into the center of root layout.
            rootLayout.setCenter(inventoryOverview);
            
            // Give the controller access to the main app.
        AppViewController controller = loader.getController();
        controller.setMainApp(this);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //called when product add button clicked
    public boolean showAddProductView() {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AddProduct.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        
        // Give the controller access to the main app.
        AddProductController controller = loader.getController();
        controller.setMainApp(this);
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Product");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //set dialog stage into the controller
        controller.setDialogStage(dialogStage);
        controller.newProduct = true;


        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
    //overloaded method called when product modify button clicked
    public boolean showAddProductView(Product product, int selectedIndex) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AddProduct.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Product");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        // Give the controller access to the main app.
        AddProductController controller = loader.getController();
        controller.setMainApp(this);

        //set dialog stage into controller
        controller.setDialogStage(dialogStage);
        
        //set selected product and its arraylist index into controller
        controller.setProduct(product);
        controller.setIndex(selectedIndex);
        
        //tells addproduct controller that user is modifying existing product
        controller.newProduct = false;
        
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
