package models;

import java.util.ArrayList;

/**
 * Created by danny on 3/24/15.
 */
public class Arrival {

    public String getStop_id() {
        return stop_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public String getArrival_at() {
        return arrival_at;
    }

    public String getType() {
        return type;
    }

    String stop_id = "";

    String route_id  = "";

    String bus_id =  "";
    String arrival_at = "";
    String type = "";


    public Arrival(String sid, String rid, String bid, String arr, String typeP)
    {
        stop_id = sid;
        route_id = rid;
        bus_id = bid;
        arrival_at = arr;
        type = typeP;
    }


}
