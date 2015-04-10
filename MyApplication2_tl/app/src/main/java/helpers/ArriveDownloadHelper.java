package helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import models.Arrival;
import models.User;

/**
 * Created by danny on 3/18/15.
 */
public class ArriveDownloadHelper implements DownloaderHelper{

    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {

        Log.v("parse:", "in arriveDownloadHelper");

        BusManager busManager = BusManager.getBusManager();
        //JSONObject arriveJSON = jsonObject;

        if (jsonObject == null) return;      // Couldn't get the JSON. So, give up.

        if (jsonObject.getJSONArray(BusManager.TAG_DATA).length() == 0)
        {
            return;
        }


        final JSONObject arrivals = (JSONObject)(jsonObject.getJSONArray(BusManager.TAG_DATA).get(0));

        String stopId = arrivals.getString("stop_id");
        JSONArray jsArr = arrivals.getJSONArray("arrivals");

        String route_id = "";
        String vehicle_id = "";
        String arrival_at = "";
        String type = "";

        busManager.getArrivals().clear();
        for (int i = 0; i< jsArr.length(); i++)
        {
            JSONObject s = (JSONObject)jsArr.get(i);

            route_id = s.getString("route_id");
            vehicle_id = s.getString("vehicle_id");
            arrival_at = s.getString("arrival_at");
            type = s.getString("type");

            busManager.getArrivals().add( new Arrival(new String(stopId), route_id, vehicle_id, arrival_at, type));
        }


    }
}
