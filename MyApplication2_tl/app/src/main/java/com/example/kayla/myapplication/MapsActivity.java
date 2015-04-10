package com.example.kayla.myapplication;

//tl
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
//tl

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import java.util.ArrayDeque;
import java.util.HashMap;

import helpers.BusManager;
import models.Bus;
import models.Route;
import models.Stop;
import java.util.ArrayList;
import android.graphics.Color;
public class MapsActivity extends ActionBarActivity  {


    private HashMap<String, String> routeIdName = null;

    /*
    private Stop s = null;
    private Route r = null;
    */

    private HashMap<String,String> stopNameId = new HashMap<String, String>();
    BusManager sharedManager = null;
    public MarkerOptions[] marker1 ;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    ArrayList<String> list = new ArrayList<String>();


    ArrayList<String> routeIdList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        setTitle("Choose your start station");

        Intent i = getIntent();

        double lat = i.getDoubleExtra("desLat", 0.0f);
        double lng = i.getDoubleExtra("desLng", 0.0f);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //    mMap.addMarker(new MarkerOptions().position(new LatLng(29, -82)).title("Marker1"));
        //  marker1 = new MarkerOptions().position(new LatLng(29, -82)).title("Marker1");

        //marker1[0]=mMap.addMarker(new MarkerOptions().position(new LatLng(29, -82)).title("Marker1"));
        // marker1.setTitle("Marker1");
        // mMap.addMarker(marker1[0]);

        sharedManager = BusManager.getBusManager();

        for (Stop s : sharedManager.getStops()) {
            LatLng latLng = s.getLocation();
            String id = s.getID();
            String name = s.getName();

            String routeStr = "Routes: ";


            for (Route r : s.getRoutes()) {
                routeStr += r.getLongName() + ",";
              //  list.add(r.getLongName());
            }

            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet(routeStr)
                    );

            this.stopNameId.put(name, id);

        }



/*
         Marker marker1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29,-82))
                .title("Melbourne")
                .snippet("Route Number: 4,137,400"));
                */
      /*  mMap.setOnMarkerClickListener(new OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
               // if(arg0.getTitle().equals("Melbourne")) // if marker source is clicked
                   // Toast.makeText(MainActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
               startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                return true;
            }

        });*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
              //  startActivity(new Intent(getApplicationContext(),MainActivity4.class));

              //  Intent i = new Intent(getApplicationContext(),MainActivity4.class);
                String stopId = stopNameId.get(marker.getTitle());
                Stop s = sharedManager.getStopByID(stopId);
                for (Route r : s.getRoutes()) {
                  //  routeStr += r.getLongName() + ",";
                    list.add(r.getLongName());
                    routeIdList.add(r.getID());
                }
                Log.v("stop:id:", stopId);

                for (Route r : s.getRoutes())
                {
                    Log.v("route:", r.getLongName());
                }



                Intent i = new Intent(getApplicationContext(),MainActivity4.class);
                i.putExtra("getPosition",marker.getPosition());
                i.putExtra("getStation", marker.getTitle());
                // i.putExtra("getRoute",route);
                i.putStringArrayListExtra("getRoute", list);


                i.putExtra("getStationId", s.getID());
                i.putStringArrayListExtra("getRouteIdList", routeIdList);





                startActivity (i);

            }
        });
    }
}
