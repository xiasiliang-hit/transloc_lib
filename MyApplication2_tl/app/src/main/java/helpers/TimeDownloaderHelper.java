package helpers;

import android.util.Log;

import com.example.kayla.myapplication.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import models.Arrival;
import models.Stop;

public class TimeDownloaderHelper implements DownloaderHelper {


    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {
        if (jsonObject != null && jsonObject.toString().length() > 0) {
            BusManager.parseTime(jsonObject);

            if (MainActivity.LOCAL_LOGV) {
                Log.v(MainActivity.LOG_TAG, "Creating time cache file: " + jsonObject.getString("stop_id"));
                Log.v(MainActivity.LOG_TAG, "*   result: " + jsonObject.toString());
            }

            Downloader.cache(jsonObject.getString("stop_id"), jsonObject);
        }
        else {
            throw new JSONException(jsonObject == null
                    ? "TimeDownloaderHelper#parse given null jsonObject"
                    : "TimeDownloaderHelper#parse given empty jsonObject");
        }
    }



    /*
    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {

        JSONObject arriveJSON = jsonObject;
        
        if (arriveJSON == null) return;      // Couldn't get the JSON. So, give up.
        final JSONObject routes = arriveJSON.getJSONObject(BusManager.TAG_DATA);

        String stopId = arriveJSON.getString("stop_id");
        JSONObject jsArr = arriveJSON.getJSONObject("arrivals");

        Arrival a = new Arrival();
        for (int i = 0; i < jsArr.length(); i++)
        {

        }
*/


        /*


            final String stopID = timesJson.getString("stop_id");
            final Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Stop s = sharedBusManager.getStopByID(stopID);
                    if (s != null) {
                        for (int i = 0; i < s.getRoutes().size(); i++) {
                            if (routes.has(s.getRoutes().get(i).getID())) {
                                try {
                                    JSONObject routeTimes = routes.getJSONObject(s.getRoutes().get(i).getID());
                                    getAllTimes(s, s.getRoutes().get(i), routeTimes);
                                    t.cancel();
                                } catch (JSONException e) {
                                /*
                                if (MainActivity.LOCAL_LOGV)
                                    Log.e("Greenwich", "Error parsing JSON...");

                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }, 0L, 250L);

*/

        /*
        if (jsonObject != null && jsonObject.toString().length() > 0) {
            BusManager.parseTime(jsonObject);

            Downloader.cache(jsonObject.getString("stop_id"), jsonObject);
        }
        else {
            throw new JSONException(jsonObject == null
                    ? "TimeDownloaderHelper#parse given null jsonObject"
                    : "TimeDownloaderHelper#parse given empty jsonObject");
        }
        */
    //}


}