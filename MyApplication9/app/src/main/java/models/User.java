/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.BusManager;
import java.util.ArrayList;

/**
 *
 * @author danny
 */
public class User {
    
    String name = "";
    Stop startStop = null;
    Stop endStop = null;
    
    static ArrayList<Route> mRoutes = null;

    
    public User(String name, Stop startStop, Stop endStop )
    {
    }
    
    public void addAlarm(Alarm a)
    {
        BusManager b = BusManager.getBusManager();
        
    }
    
    //
    public Alarm checkAlarms()
    {   
        BusManager b = BusManager.getBusManager();
        
        for (Alarm a : b.getAlarms())
        {
            
        }
        
        
        return null;
    }
    
    
    
}
