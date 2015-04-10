/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author danny
 */
public class Alarm {



    ArrayList<Arrival> arr = null;


    //Stop startStop = null;
    //Stop endStop = null;

    String startStopId = "";



    //Route route = null;
    String routeId = "";

    public String getRouteId() {
        return routeId;
    }

    public String getStartStopId() {
        return startStopId;
    }

    public Date getEstimate_time() {
        return estimate_time;
    }

    public int getPriorio_min() {
        return priorio_min;
    }






    Date estimate_time = null;

    int priorio_min = 0;
    
    Bus bNextOne = null;
    Bus bNextTwo = null;
    
    public static final String WEEKDAY = "Working days";
    public static final String WHOLEWEEK = "Every day";
    public static final String ONCE = "Alarm Once";

    public static final String NOTIFIED = "notified";
    public static final String ACTIVE = "active";


    public ArrayList<Arrival> getArr() {
        return arr;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

    String status = ACTIVE;

    public String getFrequency() {
        return frequency;
    }

    String frequency = "";


    public Alarm (String fre, Date ptime, int ppriorio_min, String pstopId, String prouteId)
    {
        frequency = fre;

        estimate_time = ptime;
        priorio_min = ppriorio_min;

        startStopId = pstopId;
        routeId = prouteId;

        arr = new ArrayList<Arrival>();


        status = Alarm.ACTIVE;
    }
    
}
