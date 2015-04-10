package com.example.kayla.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import helpers.ArriveDownloadHelper;
import helpers.BusManager;
import helpers.Downloader;
import helpers.DownloaderHelper;
import helpers.GMapDownloadHelper;
import models.Route;
import models.Stop;

public class calendarIndex extends ActionBarActivity {

    BusManager busManager = BusManager.getBusManager();

    Spinner spinner2;
    String alarmTime;
    String name;
    String location;
    String tmin;
    //ArrayList<String> route;
    Intent intent = new Intent();


    String routeName = "";
    String startStop = "";
    String stopId = "";
    String routeId = "";

    int hh = 0;
    int mm = 0;
/*
    ArrayList<String> routeIds = new ArrayList<String>();
    String stationId = "";
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_index);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List list = new ArrayList();
        list.add("5 minutes in advance");
        list.add("10 minutes in advance");
        list.add("15 minutes in advance");
        list.add("20 minutes in advance");
        list.add("25 minutes in advance");
        list.add("30 minutes in advance");
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        Intent i = getIntent();
        //   String message = i.getStringExtra("uName");

        name = i.getStringExtra("getStation");
        location = i.getStringExtra("location");
        String time = i.getStringExtra("time");
        Double desLat = i.getExtras().getDouble("desLat");
        Double desLng = i.getExtras().getDouble("desLng");
        String routeStr = i.getStringExtra("routeStr");
        // route = (ArrayList<String>) getIntent().getStringArrayListExtra("getRoute");

        TextView t1 = (TextView) findViewById(R.id.textView11);
        t1.setText(name);

        TextView t2 = (TextView) findViewById(R.id.textView13);
        t2.setText(location);
        //   String routeSel=" ";
        //    for(int j=0;j<route.size();j++)
        //       routeSel += route.get(j)+", ";
        //  TextView t3 = (TextView)findViewById(R.id.textView14);
        // t3.setText("can take bus  No "+ routeSel);
        //  String time = i.getStringExtra("time");
        //
        getDistance1();


        //getDistance2();


/*
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
*/



    int minAdvance = 0;

        for (String s : tmin.split(" ", 0)) {
            minAdvance = Integer.parseInt(s);
        }

        String hourStr = time.substring(11, 13);
        String minStr = time.substring(14, 16);
        int hour = Integer.parseInt(hourStr);
        int min = Integer.parseInt(minStr);


        ArrayList<String> routeIds = new ArrayList<String>();
        routeIds.add(routeId);

        ArrayList<String> routes = new ArrayList<String>();
        routes.add(routeName);


        intent.putExtra("actID", "schedule");

        intent.putStringArrayListExtra("getRouteIdSelect", routeIds);
        intent.putStringArrayListExtra("getRouteSelect", routes);

        intent.putExtra("getStationId", stopId);
        intent.putExtra("getStation", startStop);


        intent.putExtra("minAdvance", minAdvance);


        min = (min - minAdvance);
        if (min < 0) {
            min += 60;
            hour -= 1;

            if (hour < 0) {
                hour += 24;
            }
        }
        intent.putExtra("hour", hour);
        intent.putExtra("min", min);

        //routeIds = i.getStringArrayListExtra("getRouteIdSelect");
        //stationId = i.getStringExtra("getStationId");

    }

    public void buttonOnClick5(View v) {
        // Button btnOk = (Button) findViewById(R.id.button);
        alarmTime = String.valueOf(spinner2.getSelectedItem());
        // depatureTime = String.valueOf(timePicker.getCurrentHour()+" "+timePicker.getCurrentMinute());
        startActivity(new Intent(getApplicationContext(), done2.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_index, menu);
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

    private synchronized void getDistance2() {
        /*
        name = "university commons Gainesville";
        location = "reitz union Gainesville";
*/

        try {
            MainActivity.downloadsOnTheWire += 5;


            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + name + "&destination=" + location + "&mode=transit";
            url = url.replace(" ", "%20");

            //String myURL = URLEncoder.encode(url, "UTF-8");

            new Downloader(new GMapDownloadHelper(), getApplicationContext()).execute(url);

            wait(1000*2);

            String routeName = busManager.getgMapRouteInfo().get("routeName");
            String startStopName = busManager.getgMapRouteInfo().get("startStopName");
            String duration = busManager.getgMapRouteInfo().get("duration");


            /*
            for (Route r : busManager.getRoutes())
            {
                  if (r.getLongName().equals(routeName))
                  {
                      routeId = r.getID();
                      break;
                  }
                else
                  {}
            }

            for (Stop s : busManager.getStops())
            {
                if (s.getName().equals(startStopName))
                {
                    stopId = s.getID();
                    break;
                }
                else
                {}
            }
            */

        } catch (Exception e) {

            e.getMessage();
        }


    }

private void getDistance1() {
    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //   String paramUsername = params[0];
            //   String paramPassword = params[1];
            //   System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);
            HttpClient httpClient = new DefaultHttpClient();
            // In a POST request, we don't pass the values in the URL.
            //Therefore we use only the web page URL as the parameter of the HttpPost argument
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + name + "&destination=" + location + "&mode=transit";
            // url = new String(url.replace(" ", "%20"));
            try {
                HttpGet httpPost = new HttpGet(url);

                // HttpResponse is an interface just like HttpPost.
                //Therefore we can't initialize them
                HttpResponse httpResponse = httpClient.execute(httpPost);

                // According to the JAVA API, InputStream constructor do nothing.
                //So we can't initialize InputStream although it is not an interface
                InputStream inputStream = httpResponse.getEntity().getContent();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedStrChunk);
                }
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray routesArray = jsonObject.getJSONArray("routes");
                JSONObject route = routesArray.getJSONObject(0);
                JSONArray legs = route.getJSONArray("legs");
                JSONObject duration = legs.getJSONObject(0);

                JSONObject text1 = duration.getJSONObject("duration");
                tmin = text1.getString("text");
                //String vv = text1.getString("value");

                    JSONArray steps = duration.getJSONArray("steps");
                    JSONObject start = (JSONObject) (steps.get(1));  //step [1] is bus  $$

                    routeName = start.getJSONObject("transit_details").getJSONObject("line").getString("short_name");
                    startStop = start.getJSONObject("transit_details").getJSONObject("departure_stop").getString("name");

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
                    //routeIds = i.getStringArrayListExtra("getRouteIdSelect");
                    //stationId = i.getStringExtra("getStationId");
            }

                catch (Exception e) {
                Log.v("calender:", e.getMessage().toString());
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("working")) {
                Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid POST req...", Toast.LENGTH_LONG).show();
            }
        }

    }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
}






    public void setTime1(View view) {
        // Do something in response to button

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
     //   alertDialog.setTitle("You alarm has been created");
        alertDialog.setMessage("You alarm has been created");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions

            }
        });
        alertDialog.setIcon(R.drawable.ic_action_view_as_list);
        alertDialog.show();
    }
}
