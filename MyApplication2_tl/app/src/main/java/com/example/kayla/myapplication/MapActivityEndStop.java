package com.example.kayla.myapplication;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.location.Geocoder;
import android.location.Address;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpers.BusManager;
import models.Route;
import models.Stop;

public class MapActivityEndStop extends ActionBarActivity {
    public Marker marker1 ;
    private HashMap<String,String> stopNameId = new HashMap<String, String>();
    BusManager sharedManager = null;
    private GoogleMap mMap;
    double lat=0;
    double lng=0;
    String location;
    String time;
    String name;
    //  String routeStr;


    ArrayList<String> list = new ArrayList<String>();  //route name
    ArrayList<String> routeIdList = new ArrayList<String>();  //route id



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  setUpMapIfNeeded();

        setTitle("Choose your end station");

        Intent i = getIntent();
        //   String message = i.getStringExtra("uName");
         lng =  i.getDoubleExtra("desLng", 0.0f);
         lat = i.getDoubleExtra("desLat", 0.0f);

         //time = i.getStringExtra("time");

        setUpMap();



/*
        name = i.getStringExtra("name");
        location = i.getStringExtra("location");
        time = i.getStringExtra("time");
*/
        /*
        name = "university commons, gainesville";
        location = "university commons, gainesville";
        time = "2015-03-27 18:11";
*/

        /*
        lat=0;
        lng=0;

        //  Log.v("jjj", uName);
        // String uName1 = "dsd";


        /*
        if(!location.equals("")) {
            // TextView textview = (TextView) findViewById(R.id.textView10);

            //  textview.setTextSize(28);
            Geocoder coder = new Geocoder(this);
            location = location + " Gainesville";
            try {
                List<Address> addressList = coder.getFromLocationName(location, 1);
                if (addressList != null && addressList.size() > 0) {
                    lat = addressList.get(0).getLatitude();
                    lng = addressList.get(0).getLongitude();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // textview.setText(lat+"     "+lng);
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

    private void setUpMap() {
        //    mMap.addMarker(new MarkerOptions().position(new LatLng(29, -82)).title("Marker1"));
        //  marker1 = new MarkerOptions().position(new LatLng(29, -82)).title("Marker1");

        //marker1[0]=mMap.addMarker(new MarkerOptions().position(new LatLng(29, -82)).title("Marker1"));
        // marker1.setTitle("Marker1");
        // mMap.addMarker(marker1[0]);

        LatLng loc = new LatLng(lat, lng);
        CameraPosition currentPlace = new CameraPosition.Builder().target(loc).zoom(17.0f).bearing(112.5f).build();


        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1))
                .getMap();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));


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
        // marker1= mMap.addMarker(new MarkerOptions()
        //  .position(new LatLng(29.64,-82.34))
        //  .title("Station name")
        // .snippet("Route Number: 4,137,400"));
        /* marker1[0][1] = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.23,-82.33))
                .title("TTT")
                .snippet("Route Number: 4,137,400"));*/
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
                String stopId = stopNameId.get(marker.getTitle());
                Stop s = sharedManager.getStopByID(stopId);
                for (Route r : s.getRoutes()) {
                    // routeStr += r.getLongName() + ",";
                    list.add(r.getLongName());
                    routeIdList.add(r.getID());

                }

                //  for (Route r : s.getRoutes())
                //  {
                //     Log.v("route:", r.getLongName());
                // }


                Intent i = new Intent(getApplicationContext(), MainActivity4.class);
                i.putExtra("getPosition",marker.getPosition());
                i.putExtra("getStation", marker.getTitle());
                // i.putExtra("getRoute",route);
                i.putStringArrayListExtra("getRoute", list);


                i.putExtra("getStationId", s.getID());
                i.putStringArrayListExtra("getRouteIdList", routeIdList);


                BusManager busManager = BusManager.getBusManager();
                busManager.setEndStop(new Stop(marker.getTitle(), Double.toString(lat), Double.toString(lng), stopId, routeIdList, list));


                //i.putExtra("time", time);
                i.putExtra("ACTID", "MapActivityEndStop");

/*
                Intent i = new Intent(getApplicationContext(),calendarIndex.class);
                i.putExtra("getPosition",marker.getPosition());
                i.putExtra("getStation", marker.getTitle());
                i.putExtra("desLat",lat);
                i.putExtra("desLng",lng);
                i.putExtra("location",location);
                i.putExtra("time",time);
                */

                //    i.putStringArrayListExtra("getRoute", list);
                //  i.putExtra("routeStr",routeStr);
                startActivity (i);

                // startActivity(new Intent(getApplicationContext(),MainActivity3.class));
            }
        });
    }

}
