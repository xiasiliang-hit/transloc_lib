package helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface DownloaderHelper {
    static final String TRANSLOC_URL = "https://transloc-api-1-2.p.mashape.com";
    static final String QUERY = Downloader.makeQuery("agencies", "116", "UTF-8");

    //static final String Q2 = Downloader.makeQuery("geo_area", "29.6465%2C-82.3234%7C2000", "UTF-8");

    static final String STOPS_URL = TRANSLOC_URL + "/stops.json?" + QUERY ;
    static final String ROUTES_URL = TRANSLOC_URL + "/routes.json?" + QUERY;
    static final String SEGMENTS_URL = TRANSLOC_URL + "/segments.json?" + QUERY;


    //static final String VEHICLES_URL = TRANSLOC_URL + "/vehicles.json?" + QUERY;


    //static final String AMAZON_URL = "https://s3.amazonaws.com/nyubustimes/1.0/";
    //static final String VERSION_URL = AMAZON_URL + "version.json";

    public abstract void parse(JSONObject jsonObject) throws JSONException, IOException;
}
