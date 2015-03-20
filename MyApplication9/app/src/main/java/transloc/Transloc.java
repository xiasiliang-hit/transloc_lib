/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transloc;

//import com.google.api.client.http.HttpResponse;
//import com.google.api.client.json.JsonParser;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import java.io.File;
import java.util.ArrayList;
import java.lang.Object.*;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import helpers.BusManager;
import java.util.Map;

//import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import helpers.*;
import models.Bus;
import models.Route;
import models.Stop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.apache.http.HttpResponse;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONValue;
//import org.json.JSONArray;
//import org.json.

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

//import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;




import models.*;

/**
 *
 * @author danny
 */
public class Transloc {

    public static final boolean LOCAL_LOGV = true;
    private static final String RUN_ONCE_PREF = "runOnce";
    private static final String STOP_PREF = "stops";
    private static final String START_STOP_PREF = "startStop";
    private static final String END_STOP_PREF = "endStop";
    private static final String FIRST_TIME = "firstTime";
    public static final String REFACTOR_LOG_TAG = "refactor";
    public static final String LOG_TAG = "nyu_log_tag";

    private boolean offline = true;


    String prefix = "https://transloc-api-1-2.p.mashape.com";

    public static String leftTop_lat = "29.6465";
    public static    String leftTop_long = "-82.3234";

    public static    String ufl = "116";

    public static    String blank = "%2C";
    public static    String split = "%7C";

    public static    String paraSplit = "&";
        String agency = "agencies=116&";
        String callback = "callback=call&";

        Context context = null;

    public Transloc(Context c)
    {
        this.context = c;

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Time t1 = new Time(8, 15);
        //Time t2 = new Time(9, 15);
        
        /*
        System.out.println(t1.getHour());
        System.out.println(t1.getMinute());
        System.out.println(t1.getRoute());
        System.out.println(t1.getTimeAsTimeUntil(t2));
        System.out.println(t1.getTimeOfWeek());
        System.out.println(t1.getTimeOfWeekAsString());
        */
        
        //Transloc t = new Transloc();
        //t.initStops();
        //t.initRoutes();

        /*
        try {
            //getAllRoutes();
        } catch (Exception e) {

        }*/
    }

    public void test()
    {
        initStops();
    }


/*
    private void deleteEverythingInMemory() {
        if (LOCAL_LOGV) Log.v(REFACTOR_LOG_TAG, "Trying to delete all files.");
        File directory = new File(getFilesDir(), Downloader.CREATED_FILES_DIR);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.delete()) {
                    if (LOCAL_LOGV) Log.v(REFACTOR_LOG_TAG, "Deleted " + f.toString());
                }
                else if (LOCAL_LOGV) Log.v(REFACTOR_LOG_TAG, "Could not delete " + f.toString());
            }
        }
    }
*/
/*
    private void downloadEverything(boolean block) {
        deleteEverythingInMemory();
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); //$
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            offline = false;
            // Download and parse everything, put it all in persistent memory, continue.
            if (block) progressDialog = ProgressDialog.show(this, getString(R.string.downloading), getString(R.string.wait), true, false);
            else progressDialog = null;
            Context context = getApplicationContext();
            downloadsOnTheWire += 4;
            new Downloader(new StopDownloaderHelper(), context).execute(DownloaderHelper.STOPS_URL);
            new Downloader(new RouteDownloaderHelper(), context).execute(DownloaderHelper.ROUTES_URL);
            new Downloader(new SegmentDownloaderHelper(), context).execute(DownloaderHelper.SEGMENTS_URL);
            new Downloader(new VersionDownloaderHelper(), context).execute(DownloaderHelper.VERSION_URL);
        }
        else if (!offline) {    // Only show the offline dialog once.
            offline = true;
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.unable_to_connect);
            int duration = Toast.LENGTH_SHORT;

            if (context != null) {
                Toast.makeText(context, text, duration).show();
            }
        }
    }
*/


    void initStops()
    {
        String radius = "5000";
        String geo_area = "geo_area=" + leftTop_lat + blank + leftTop_long + split + radius + "&";
        String url = prefix + "/stops.json?" + agency + callback + geo_area;
        HttpResponse<JsonNode> response = null;
        
        try {

            //new Downloader(new StopDownloaderHelper(), context).execute(DownloaderHelper.STOPS_URL);

            response = Unirest.get(url)
            .header("X-Mashape-Key", "L3gIwvsTlHmshD9RFzWiUrvYtRUDp1b4dXijsnp1ItvyVyVBMF")
            .header("Accept", "application/json")
             .asJson();
        
            Stop.parseJSON( response.getBody().getObject());

        }
        catch (Exception e) {
            Log.d("app", e.getMessage());

            //e.printStackTrace();
        }        
    }
    
    
    ArrayList<String> initRoutes() {
        
        
        String radius = "2000";
        String geo_area = "geo_area=" + leftTop_lat + blank + leftTop_long + split + radius + "&";

        String url = prefix + "/routes.json?" + agency + callback + geo_area;
        HttpResponse<JsonNode> response = null;

        try {
            response = Unirest.get(url)
            .header("X-Mashape-Key", "L3gIwvsTlHmshD9RFzWiUrvYtRUDp1b4dXijsnp1ItvyVyVBMF")
            .header("Accept", "application/json")
            .asJson();
        }
        catch (Exception e) {
            System.out.println("ex:getAllRoutes()");
            e.printStackTrace();
        }
        //System.out.println(response.getRawBody().toString());
        //Object obj = JSONValue.parse(response.getBody().getObject());
        //JSONArray finalResult = (JSONArray) obj;
        JSONObject obj = response.getBody().getObject();
        
        
        try 
        {
            Route.parseJSON(obj);
            
            /*
            int len =  obj.getJSONObject("data").getJSONArray(ufl).length();
            for (int i = 0; i<len ; i++)
            {
                Route.parseJSON(obj.getJSONObject("data").getJSONArray(ufl).getJSONObject(i));
            }
            */
            BusManager b =  BusManager.getBusManager();
            
            System.out.println(
            b.getRouteByID("4001310").getID()
            ) ;
            
        }
        catch (Exception e)
        {
            System.out.println("ex:getAllRoutes()");
            e.printStackTrace();
        }
        
        /*
        for (Object o : finalResult) {
            System.out.println(o.toString());
        }
        */
        return null;
    }

    
    
    ArrayList<Stop> getStopsByRoute(String str)
    {
        ArrayList<Stop> ary = new ArrayList<Stop>();
                
        return null;
    }

    
    
    
    
    
      
}
