/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Dan
 */
public class Inhouse extends Part{
    
    private final IntegerProperty machineId;
    
    public Inhouse(){
        this.machineId = new SimpleIntegerProperty(0);
    }

    public Inhouse(int id, String name, double price, int inventoryLevel, int min, int max, int machineId) {
        super(id, name, price, inventoryLevel, min, max);
        this.machineId = new SimpleIntegerProperty(machineId);
    }
  
    public int getMachineId(){
        return machineId.get();
    }
    
    public void setMachineId(int machineId) {
        this.machineId.set(machineId);
    }
    
    public IntegerProperty machineIdProperty(){
        return machineId;
    }
}
