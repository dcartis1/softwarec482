/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author Dan
 */
public class Product {
    
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inventoryLevel; 
    private final IntegerProperty min;
    private final IntegerProperty max;
    private static int nextId = 1;
    
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    
    public Product(){
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0.00);
        this.inventoryLevel = new SimpleIntegerProperty(0);
        this.min = new SimpleIntegerProperty(0);
        this.max = new SimpleIntegerProperty(0);
    }
    
    public Product(int id, String name, double price, int inventoryLevel, int min, int max){      
        this.id = new SimpleIntegerProperty(getNextId());
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inventoryLevel = new SimpleIntegerProperty(inventoryLevel);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }

    
    public int getId(){
        return id.get();
    }
    
    public void setOldId(int id) {
        this.id.set(id);
    }

    public void setNewId() {
        this.id.set(getNextId());
    }
    
    public IntegerProperty idProperty(){
        return id;
    }
    
    public String getName(){
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty(){
        return name;
    }
    
    public double getPrice(){
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public DoubleProperty priceProperty(){
        return price;
    }
    public int getInventoryLevel(){
        return inventoryLevel.get();
    }

    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel.set(inventoryLevel);
    }
    
    public IntegerProperty inventoryLevelProperty(){
        return inventoryLevel;
    }
    
    public int getMin(){
        return min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }
    
    public IntegerProperty minProperty(){
        return min;
    }
    
    public int getMax(){
        return max.get();
    }

    public void setMax(int max) {
        this.max.set(max);
    }
    
    public IntegerProperty maxProperty(){
        return max;
    }
    
    public static int getNextId() {
        int i = nextId;
        nextId ++;
        return i;
    }


}
