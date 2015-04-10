package helpers;

import models.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class StopDownloaderHelper implements DownloaderHelper {
    public static final String STOP_JSON_FILE = "stops.json"; //"stopJson";


    //public static String Q2  = Downloader.makeQuery("geo_area", "29.6465%2C-82.3234%7C2000", "UTF-8");

    //public static String STOPS_URL = TRANSLOC_URL + "/stops.json?" + QUERY ;

    /*
       public StopDownloaderHelper()
       {
           Q2 = Downloader.makeQuery("geo_area", "29.6465%2C-82.3234%7C2000", "UTF-8");
           STOPS_URL = TRANSLOC_URL + "/stops.json?" + QUERY ;

       }
*/

    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {
        Stop.parseJSON(jsonObject);
        Downloader.cache(STOP_JSON_FILE, jsonObject);
    }
}
