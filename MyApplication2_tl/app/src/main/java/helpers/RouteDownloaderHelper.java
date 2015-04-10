package helpers;

import models.Route;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RouteDownloaderHelper implements DownloaderHelper {
    public static final String ROUTE_JSON_FILE = "routes.json"; //"routeJson";

    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {
        Route.parseJSON(jsonObject);
        Downloader.cache(ROUTE_JSON_FILE, jsonObject);
    }
}