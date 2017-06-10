/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftwarec482.exceptions;

/**
 *
 * @author Dan
 */
//custom checked exception for ensuring a product always has at least one associated part
public class PartNotInProduct extends Exception {
    public PartNotInProduct() {}
    public PartNotInProduct(String message)
    {
       super(message);
    }
}
