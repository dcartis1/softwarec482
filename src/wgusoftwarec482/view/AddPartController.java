/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wgusoftwarec482.MainApp;
import wgusoftwarec482.model.Inhouse;
import wgusoftwarec482.model.Inventory;
import wgusoftwarec482.model.Outsourced;


/**
 *
 * @author Dan
 */
public class AddPartController {
    @FXML
    private TextField partId;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInv;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMin;
    @FXML
    private TextField partMax;
    @FXML
    private RadioButton inhouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private ToggleGroup partRadioGroup;
    
    @FXML
    private Label addPartLabel;
    
    @FXML
    private Label partNameLabel;
    @FXML
    private Label partInvLabel;
    @FXML
    private Label partPriceLabel;
    @FXML
    private Label partMinLabel;
    @FXML
    private Label partMaxLabel;
    
    
    @FXML
    Label companyNameLabel;
    
    @FXML
    TextField companyNameField;
    
    @FXML
    Label machineIdLabel;
    
    @FXML
    TextField machineIdField;
    
    
    private MainApp mainApp;
    private Stage dialogStage;
    private final boolean okClicked = false;
    private boolean newPart;
    private int selectedId;
    private Inventory inventory;
    private boolean inOrOut;
    private Inhouse inhouse;
    private Outsourced outsourced;
    
    //mainapp passes the partId of the selected part for modification
    public void setSelectedId(int selectedId){
        this.selectedId = selectedId;
    }
    //main app tells us if user clicked add or modify part button
    public void setNewPart(boolean newPart){
        this.newPart = newPart;
    }
    //mainApp passes the selected part and then we load its attributes into
    //the corresponding fields and tables.
    public void setPart(Inhouse part) {
        this.addPartLabel.setText("Modify Part");
        inhouseRadio.setSelected(true);
        inhouseBtn();
        partId.setText(Integer.toString(part.getId()));
        partName.setText(part.getName());
        partInv.setText(Integer.toString(part.getInventoryLevel()));
        partPrice.setText(Double.toString(part.getPrice()));
        partMin.setText(Integer.toString(part.getMin()));
        partMax.setText(Integer.toString(part.getMax()));
        machineIdField.setText(Integer.toString(part.getMachineId()));
    }
    //mainApp passes the selected part and then we load its attributes into
    //the corresponding fields and tables.
    public void setPart(Outsourced part){
        this.addPartLabel.setText("Modify Part");
        outsourcedRadio.setSelected(true);
        outsourcedBtn();
        partId.setText(Integer.toString(part.getId()));
        partName.setText(part.getName());
        partInv.setText(Integer.toString(part.getInventoryLevel()));
        partPrice.setText(Double.toString(part.getPrice()));
        partMin.setText(Integer.toString(part.getMin()));
        partMax.setText(Integer.toString(part.getMax()));
        companyNameField.setText(part.getCompanyName());
    }
    
    @FXML
    public void addpartBtn() {
        //if add button was clicked, add new part, else modify existing part
        if (newPart == true){
            //if inhouse or outsourced instance of part
            if (inOrOut == true){
                //if new inhouse part input was valid
                if(validatePart() == true){
                    //make sure inventory is between min and max, otherwise throw Inventory Wrong exception
                    try{
                        checkInventoryLevel(Integer.parseInt(partInv.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                        //make sure min is less than max, otherwise throw MinMaxWrong exception
                        try{
                            checkMinMax(Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                            //new inhouse part input was valid and no exceptions were thrown, so
                            //add new inhouse part to inventory.allPartData arraylist
                            inhouse = new Inhouse();
                            inhouse.setNewId();
                            inhouse.setName(partName.getText());
                            inhouse.setInventoryLevel(Integer.parseInt(partInv.getText()));
                            inhouse.setPrice(Double.parseDouble(partPrice.getText()));
                            inhouse.setMin(Integer.parseInt(partMin.getText()));
                            inhouse.setMax(Integer.parseInt(partMax.getText()));
                            inhouse.setMachineId(Integer.parseInt(machineIdField.getText()));

                            inventory.addPart(inhouse);
                            dialogStage.close(); 
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
            else{
                //if new outsourced part input is valid 
                if(validatePart() == true){
                    //make sure inventory is between min and max, otherwise throw Inventory Wrong exception
                     try{
                        checkInventoryLevel(Integer.parseInt(partInv.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                        //make sure min is less than max, otherwise throw MinMaxWrong exception
                        try{
                            checkMinMax(Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                            //new outsourced part input was valid and no exceptions were thrown, so
                            //add new outsourced part to inventory.allPartData arraylist
                            outsourced = new Outsourced();
                            outsourced.setNewId();
                            outsourced.setName(partName.getText());
                            outsourced.setInventoryLevel(Integer.parseInt(partInv.getText()));
                            outsourced.setPrice(Double.parseDouble(partPrice.getText()));
                            outsourced.setMin(Integer.parseInt(partMin.getText()));
                            outsourced.setMax(Integer.parseInt(partMax.getText()));
                            outsourced.setCompanyName(companyNameField.getText());

                            inventory.addPart(outsourced);
                            dialogStage.close();
                            
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
        else {
            //if part chosen to modify is inhouse or outsourced instance of part
            if(inOrOut == true){
                //if existing inhouse part input is valid 
                if(validatePart() == true){
                    //make sure inventory is between min and max, otherwise throw Inventory Wrong exception
                     try{
                        checkInventoryLevel(Integer.parseInt(partInv.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                        //make sure min is less than max, otherwise throw MinMaxWrong exception
                        try{
                            checkMinMax(Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                            //update inhouse part input was valid and no exceptions were thrown, so
                            //update inhouse inside inventory.allPartData arraylist
                            inhouse = new Inhouse();
                            inhouse.setOldId(Integer.parseInt(partId.getText()));
                            inhouse.setName(partName.getText());
                            inhouse.setInventoryLevel(Integer.parseInt(partInv.getText()));
                            inhouse.setPrice(Double.parseDouble(partPrice.getText()));
                            inhouse.setMin(Integer.parseInt(partMin.getText()));
                            inhouse.setMax(Integer.parseInt(partMax.getText()));
                            inhouse.setMachineId(Integer.parseInt(machineIdField.getText()));

                            inventory.updatePart(selectedId, inhouse);
                            dialogStage.close();
                            
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
            else{
                //if existing outsourced part input is valid
                if(validatePart() == true){
                    //make sure inventory is between min and max, otherwise throw Inventory Wrong exception
                    try{
                        checkInventoryLevel(Integer.parseInt(partInv.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                        //make sure min is less than max, otherwise throw MinMaxWrong exception
                        try{
                            checkMinMax(Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()));
                            //existing outsourced part input was valid and no exceptions were thrown, so
                            //update outsourced part inside inventory.allPartData arraylist
                            outsourced = new Outsourced();
                            outsourced.setOldId(Integer.parseInt(partId.getText()));
                            outsourced.setName(partName.getText());
                            outsourced.setInventoryLevel(Integer.parseInt(partInv.getText()));
                            outsourced.setPrice(Double.parseDouble(partPrice.getText()));
                            outsourced.setMin(Integer.parseInt(partMin.getText()));
                            outsourced.setMax(Integer.parseInt(partMax.getText()));
                            outsourced.setCompanyName(companyNameField.getText());

                            inventory.updatePart(selectedId, outsourced);
                            dialogStage.close();
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
    }
    
    //hides or displays necessary textfields and labels depending if user clicked
    //inhouse or outsourced radio button.
    public void inhouseBtn(){
        companyNameLabel.setVisible(false);
        companyNameField.setVisible(false);
        
        machineIdLabel.setVisible(true);
        machineIdField.setVisible(true);
        
        this.inOrOut = true;
        
    }
    //hides or displays necessary textfields and labels depending if user clicked
    //inhouse or outsourced radio button.
    public void outsourcedBtn(){             
        machineIdLabel.setVisible(false);
        machineIdField.setVisible(false);
        
        companyNameLabel.setVisible(true);
        companyNameField.setVisible(true);
        
        this.inOrOut = false;

    }
    
    //cancel button in add part view. displays alert telling user that nothing
    //will be saved and will return to main screen
    @FXML
    public void addPartCancelBtn(ActionEvent event){
        if(newPart == true){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("This Part will not be saved.");
        alert.setContentText("Are you sure you would like to cancel and go back to"
                + " main screen without saving this Part?");        
        alert.showAndWait()
            
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> dialogStage.close());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Cancel");
            alert.setHeaderText("These changes will not be saved.");
            alert.setContentText("Are you sure you would like to cancel and go back to"
                    + " main screen without modifying this Part?");        
            alert.showAndWait()

                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> dialogStage.close());
        }
    }
    
    //validates user input for adding or modifying parts
    public boolean validatePart(){ 
        if(partRadioGroup.getSelectedToggle() == null){
            inhouseRadio.setTextFill(Color.web("red"));
            outsourcedRadio.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Part not valid");
            alert.setContentText("Must select either Inhouse or Outsourced");

            alert.showAndWait();
            return false;  
        }
        if(partRadioGroup.getSelectedToggle() == inhouseRadio){
            if(!machineIdField.getText().matches("[0-9]+")){
                machineIdLabel.setTextFill(Color.web("red"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input Validation");
                alert.setHeaderText("Part not valid");
                alert.setContentText("MachineId must be a number");

                alert.showAndWait();
                return false;  
            }
        }
         if(partRadioGroup.getSelectedToggle() == outsourcedRadio){
            if(companyNameField.getText() == null|| companyNameField.getText().isEmpty()){
                companyNameLabel.setTextFill(Color.web("red"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input Validation");
                alert.setHeaderText("Part not valid");
                alert.setContentText("Company Name must not be empty");

                alert.showAndWait();
                return false;  
            }
         }
        if(partName.getText() == null || partName.getText().isEmpty()){
            partNameLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Part not valid");
            alert.setContentText("Part Name cannot be empty");

            alert.showAndWait();
            return false;
        }
        if(!partInv.getText().matches("[0-9]+")){
            partInvLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Part not valid");
            alert.setContentText("Inventory must be a number.");

            alert.showAndWait();
            return false;
        }
        if(!partPrice.getText().matches("^[0-9]+\\.?[0-9]*")){
            partPriceLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Part not valid");
            alert.setContentText("Price must be a number.");

            alert.showAndWait();
            return false;
        }
        if(!partMin.getText().matches("[0-9]+")){
            partMinLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Part not valid");
            alert.setContentText("Min must be a number.");

            alert.showAndWait();
            return false;
        }
        if(!partMax.getText().matches("[0-9]+")){
            partMaxLabel.setTextFill(Color.web("red"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Part not valid");
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
    
    public void setMainApp(MainApp mainApp, Inventory inventory) {
        this.mainApp = mainApp;
        this.inventory = inventory;
    }
    
    //checks if inventory level is between min and max range
    public void checkInventoryLevel(int inventoryLevel, int min, int max) throws InventoryLevelWrong{
        if(inventoryLevel < min || inventoryLevel > max){
            throw new InventoryLevelWrong("Inventory level must be between min and max");
        }
    }
    //checks if min is less than max and max is more than min
    public void checkMinMax(int min, int max) throws MinMaxWrong{
        if(min > max || max < min){
            throw new MinMaxWrong("Min must be less than Max");
        }
    }
    //custom checked exception for ensuring inventorylevel is correct
    class InventoryLevelWrong extends Exception {
        public InventoryLevelWrong() {}
        public InventoryLevelWrong(String message)
        {
            super(message);
        }
    }
    //custom checked exception for ensuring min max values are correct
    class MinMaxWrong extends Exception {
        public MinMaxWrong() {}
        public MinMaxWrong(String message)
        {
           super(message);
        }
    }
}
