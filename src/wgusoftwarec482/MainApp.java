/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482;

import java.io.IOException;

import javafx.application.Application;
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
import wgusoftwarec482.model.Inhouse;
import wgusoftwarec482.model.Inventory;
import wgusoftwarec482.model.Outsourced;
import wgusoftwarec482.view.AddPartController;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    //instantiate inventory here and pass it through the application as needed.
    //ensures we will always be working with the same instance of inventory.
    private final Inventory inventory = new Inventory();
    
    //sample data for testing purposes
    public MainApp() {
        //sample parts
        inventory.addPart(new Inhouse(0, "Wood", 25.00, 25, 1, 100, 125));
        inventory.addPart(new Outsourced(0, "Glass", 10.00, 5, 1, 10, "Glass Store"));
        inventory.addPart(new Inhouse(0, "Screws", 2.25, 100, 1, 200, 34));
        
        //sample product "table" has "wood" and "screws" parts associated with it
        inventory.addProduct(new Product(0, "Table", 60, 5, 1, 10));
        inventory.getProductData().get(0).addPartInProduct(inventory.getAllPartData().get(0));
        inventory.getProductData().get(0).addPartInProduct(inventory.getAllPartData().get(2));
        
        //sample product "door" has "wood" part associated with it
        inventory.addProduct(new Product(0, "Door", 26, 4, 1, 10));
        inventory.getProductData().get(1).addPartInProduct(inventory.getAllPartData().get(0));
        
        //sample product "window" has "wood" and "glass" parts associated with it
        inventory.addProduct(new Product(0, "Window", 45, 25, 1, 50));
        inventory.getProductData().get(2).addPartInProduct(inventory.getAllPartData().get(0));
        inventory.getProductData().get(2).addPartInProduct(inventory.getAllPartData().get(1));
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WGUSoftwareC482 - Daniel Cartisano");

        initRootLayout();

        showInventoryOverview();
    }

    //initalize root layout
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //show application overview in root layout
    public void showInventoryOverview() {
        try {
            // Load AppView
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AppView.fxml"));
            AnchorPane inventoryOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(inventoryOverview);   
            //give controller access to mainApp
            //also passes our instance of Inventory
            AppViewController controller = loader.getController();
            controller.setMainApp(this, inventory);
        
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
        
        //instantiate product and pass it to the controller
        //for adding a new product. also pass our instance of inventory
        Product product = new Product();
        AddProductController controller = loader.getController();
        controller.setMainApp(this, inventory, product);

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Product");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //set dialog stage into the controller
        controller.setDialogStage(dialogStage);
        //tells controller that user is adding a new product
        controller.setNewProduct(true);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
    //overloaded show addproducteview method is called when product modify button clicked.
    //takes the selected product and its productId as parameters and passes them to the
    //addproduct controller for modification.
    public boolean showAddProductView(Product product, int selectedId) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AddProduct.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modify Product");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        // Give the controller access to the main app and, pass our instance
        //of inventory, and pass the selected product instance
        AddProductController controller = loader.getController();
        controller.setMainApp(this, inventory, product);

        //set dialog stage into controller
        controller.setDialogStage(dialogStage);
        
        //set selected product and its product Id into controller. used for 
        //modifying the selected product in the productData arraylist
        controller.setProduct(product);
        controller.setSelectedId(selectedId);
        
        //tells addproduct controller that user is modifying existing product
        controller.setNewProduct(false);
        
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    //called when add new part button is clicked
    public boolean showAddPartView() {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AddPart.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        // Give the controller access to the main app and pass our instance
        //of inventory
        AddPartController controller = loader.getController();
        controller.setMainApp(this, inventory);
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Part");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //set dialog stage into the controller
        controller.setDialogStage(dialogStage);
        
        //tell controller that user is adding a new part
        controller.setNewPart(true);


        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    /*overloaded method is called when modify part button is clicked. it takes
    the selected part and its partId as parameters and passes them to the controller
    for modification
    */
    public boolean showAddPartView(Part part, int selectedId) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AddPart.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modify Part");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        // Give the controller access to the main app, pass our instance of 
        //inventory
        AddPartController controller = loader.getController();
        controller.setMainApp(this, inventory);

        //set dialog stage into controller
        controller.setDialogStage(dialogStage);

        /*
        determines the subclass of the Part instance that was selected for
        modification(Inhouse or Outsourced). It is then explicitly cast back
        to its specific subclass in order to get its corresponding getMachineId()
        OR getCompanyName() from the allPartData arraylist.
        */
        if (part instanceof Inhouse){
            Inhouse polymorphPart = (Inhouse)part;
            controller.setPart(polymorphPart);
            controller.setSelectedId(selectedId);
        }
        else{
            Outsourced polymorphPart = (Outsourced)part;
            controller.setPart(polymorphPart);
            controller.setSelectedId(selectedId);
        } 
        //tells addpart controller that user is modifying existing part
        controller.setNewPart(false);
        
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
