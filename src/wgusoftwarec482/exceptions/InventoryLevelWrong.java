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
//custom checked exception for inventorylevel value
public class InventoryLevelWrong extends Exception {
    public InventoryLevelWrong() {}
    public InventoryLevelWrong(String message)
    {
        super(message);
    }
}
