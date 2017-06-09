/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.model;

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
        productData.add(product);
    }
    
    public void addPart(Part part){
        allPartData.add(part);
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

    //loops through arraylist and sets the part with matching id.
    public void updatePart(int selectedId, Part part){
       int i = 0;
       for(Part p: allPartData){
           if(p.getId() == selectedId){
           allPartData.set(i, part);
           }
           i++;
       }
    }

    public void deleteProduct(Product product) {
        getProductData().remove(product);
    }
    
    public void deletePart(Part part) {
        getAllPartData().remove(part);
    }
    
    //this logic is done in the AddProductController
    public void lookupProduct() {}
    
    //this logic is done in the AddPartController
    public void lookupPart() {}
   
    
 }
