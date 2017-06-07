/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dan
 */
 public class Inventory {
    private final ObservableList<Product> productData;
    private final ObservableList<Part> allPartData;

    public Inventory() {
        this.allPartData = FXCollections.observableArrayList();
        this.productData = FXCollections.observableArrayList();
        
        allPartData.add(new Outsourced(0, "Wood", 10.25, 25, 1, 100, "Lumber Yard"));
        allPartData.add(new Inhouse(0, "Glass", 5.50, 5, 1, 10, 125));
        allPartData.add(new Inhouse(0, "Screws", 2.00, 100, 1, 200, 34));
        productData.add(new Product(0, "Table", 3.33, 10, 1, 10));
        productData.add(new Product(0, "Door", 3.33, 10, 1, 10));
        productData.add(new Product(0, "Window", 5, 10, 1, 5));
    }

    public Inventory(ObservableList<Product> productData, ObservableList<Part> allPartData) {
        this.productData = productData;
        this.allPartData = allPartData;
    }

    public ObservableList<Product> getProductData() {
        return productData;
    }

    public ObservableList<Part> getAllPartData() {
        return allPartData;
    }
    
    public void addProduct(Product product){
        this.productData.add(product);
    }
    
    //loops through arraylist and sets the product with matching id.
    public void updateProduct(int selectedId, Product product){
        int i = 0;
        for(Product p: productData){
            if(p.getId() == selectedId){
            productData.set(i, product);
            }
            i++;
        }
    }
    
    public void addPart(Part inHouse){
        this.allPartData.add(inHouse);
    }
    
    public void updatePart(int selectedId, Part part){
       int i = 0;
       for(Part p: allPartData){
           if(p.getId() == selectedId){
           allPartData.set(i, part);
           }
           i++;
       }
    }
    
    //this logic is done in the AddProductController
    public void lookupProduct(){}
    //this logic is done in the AddPartController
    public void lookupPart(){}
    
 }
