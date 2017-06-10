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
//custom checked exception for min and max values
public class MinMaxWrong extends Exception {
    public MinMaxWrong() {}
    public MinMaxWrong(String message)
    {
       super(message);
    }
}