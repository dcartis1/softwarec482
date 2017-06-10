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
//custom checked exception for product Name, Price, Inventory values
public class NamePriceInventory extends Exception{
    public NamePriceInventory() {}
    public NamePriceInventory(String message)
    {
       super(message);
    }
}
