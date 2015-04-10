package helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import models.Route;
import models.Stop;

/**
 * Created by danny on 3/28/15.
 */
public class GMapDownloadHelper implements DownloaderHelper{



    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {

        BusManager busManager = BusManager.getBusManager();


        try {
            JSONArray routesArray = jsonObject.getJSONArray("routes");
            JSONObject route = routesArray.getJSONObject(0);
            JSONArray legs = route.getJSONArray("legs");
            JSONObject duration = legs.getJSONObject(0);

            JSONObject text1 = duration.getJSONObject("duration");
            String value = text1.getString("text");
            String vv = text1.getString("value");


            JSONArray steps = jsonObject.getJSONArray("steps");
            JSONObject start = (JSONObject) (steps.get(1));

            String routeName = start.getJSONObject("transit_details").getJSONObject("line").getString("short_name");
            String startStop = start.getJSONObject("transit_details").getJSONObject("departure_stop").getString("name");


            String routeId = "";
            String stopId = "";

            for (Route r : busManager.getRoutes()) {
                if (r.getLongName().equals(routeName)) {
                    routeId = r.getID();
                    break;
                } else {
                }
            }



            for (Stop s : busManager.getStops()) {
                if (s.getName().equals(startStop)) {
                    stopId = s.getID();
                    break;
                } else {
                }
            }



            busManager.getgMapRouteInfo().put("routeId", routeId);
            busManager.getgMapRouteInfo().put("stopId", stopId);

            busManager.getgMapRouteInfo().put("startStopName", startStop);
            busManager.getgMapRouteInfo().put("routeName", routeName);
            busManager.getgMapRouteInfo().put("duration", value);
        }
        catch(Exception e)
        {
            e.getMessage();
        }


        }
}
