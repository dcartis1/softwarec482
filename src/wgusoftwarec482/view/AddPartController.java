/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wgusoftwarec482.MainApp;
import wgusoftwarec482.model.Inhouse;
import wgusoftwarec482.model.Inventory;
import wgusoftwarec482.model.Outsourced;
import wgusoftwarec482.model.Part;


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
    public boolean newPart;
    private int selectedId;
    private Inventory inventory;
    private boolean inOrOut;
    private Inhouse inhouse;
    private Outsourced outsourced;
    private Part inhousePart;
    private Part outsourcedPart;
    
    
    public void setSelectedId(int selectedId){
        this.selectedId = selectedId;
    }
    
    public void setParts(Part inhousePart, Part outsourcedPart){
        this.inhousePart = inhousePart;
        this.outsourcedPart = outsourcedPart;
    }
    
    public void setPart(Inhouse part) {
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
    
    public void setPart(Outsourced part){
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
        //if add button was clicked add new, else modify existing part
        if (newPart == true){
            //if inhouse or outsourced part
            if (inOrOut == true){
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
            else{
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
        }
        else {
            if(inOrOut == true){
                
                inhouse = new Inhouse();
                inhouse.setNewId();
                inhouse.setName(partName.getText());
                inhouse.setInventoryLevel(Integer.parseInt(partInv.getText()));
                inhouse.setPrice(Double.parseDouble(partPrice.getText()));
                inhouse.setMin(Integer.parseInt(partMin.getText()));
                inhouse.setMax(Integer.parseInt(partMax.getText()));
                inhouse.setMachineId(Integer.parseInt(machineIdField.getText()));

                inventory.updatePart(selectedId, inhouse);
                dialogStage.close();
                }
            else{
                outsourcedBtn();
                outsourced = new Outsourced();
                outsourced.setNewId();
                outsourced.setName(partName.getText());
                outsourced.setInventoryLevel(Integer.parseInt(partInv.getText()));
                outsourced.setPrice(Double.parseDouble(partPrice.getText()));
                outsourced.setMin(Integer.parseInt(partMin.getText()));
                outsourced.setMax(Integer.parseInt(partMax.getText()));
                outsourced.setCompanyName(companyNameField.getText());

                inventory.updatePart(selectedId, outsourced);
                dialogStage.close();
            }
        }
    }
    
    public void inhouseBtn(){
        companyNameLabel.setVisible(false);
        companyNameField.setVisible(false);
        
        machineIdLabel.setVisible(true);
        machineIdField.setVisible(true);
        
        this.inOrOut = true;
        
    }
    
    public void outsourcedBtn(){             
        machineIdLabel.setVisible(false);
        machineIdField.setVisible(false);
       // machineIdField.setDisable(true);
        
        companyNameLabel.setVisible(true);
        companyNameField.setVisible(true);
       // companyNameField.setDisable(false);
        
        this.inOrOut = false;

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
    
}
