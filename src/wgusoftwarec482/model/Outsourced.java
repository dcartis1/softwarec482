/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dan
 */
public class Outsourced extends Part{

   private final StringProperty companyName;
    
    public Outsourced(){
        this.companyName = new SimpleStringProperty("");
    }

    public Outsourced(int id, String name, double price, int inventoryLevel, int min, int max, String companyName) {
        super(id, name, price, inventoryLevel, min, max);
        this.companyName = new SimpleStringProperty(companyName);
    }
  
    public String getCompanyName(){
        return companyName.get();
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    public StringProperty companyNameProperty(){
        return companyName;
    }
    
}
