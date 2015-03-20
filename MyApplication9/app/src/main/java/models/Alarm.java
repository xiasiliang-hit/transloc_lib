/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author danny
 */
public class Alarm {
    Stop startStop = null;
    Stop endStop = null;
    
    Route r = null;        
    Time t = null;
    
    Bus bNextOne = null;
    Bus bNextTwo = null;
    
    public static final String WEEKDAY = "WEEKDAY";
    public static final String WHOLEWEEK = "WHOLEWEEK";
    public static final String ONCE = "ONCE";
    
    public static final String ACTIVE = "ACTIVE";
    public static final String DISABLED = "DISABLED";
   
    String status = "";
    String frequency = ""; 
    
    
    public Alarm ()
    {
        
    }
    
}
