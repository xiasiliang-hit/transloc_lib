package com.example.kayla.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Activity;
import android.app.AlarmManager;
import android.widget.Toast;
import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.app.AlertDialog;
import android.content.DialogInterface;

import helpers.BusManager;
import models.Alarm;
import models.Route;
import models.Stop;
import models.User;

public class notification1 extends ActionBarActivity implements View.OnClickListener {
    ArrayList<String> route;
    String station;
    Spinner spinner;
    Spinner spinner3;
    TimePicker timePicker;
    String alarmTime;
    String depatureTime;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private final static int NOTIFICATION_ID = 0x0001;
    private  AlarmManager mService;
    //private AlarmReceiver alarm;

    BusManager busManager = BusManager.getBusManager();


    String stationId = "";
    ArrayList<String> routeIds = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        //   String message = i.getStringExtra("uName");


/*
        if (i.getStringExtra("actID", "schedule"))
        {
            routeIds.add(i.getStringExtra("getRouteIdSelect"));
            stationId = i.getStringExtra("getStationId");

            route.add(busManager.getRouteByID(i.getStringExtra("getRouteIdSelect"));
            station =
        }
*/

        route = (ArrayList<String>) getIntent().getStringArrayListExtra("getRouteSelect");
        station = i.getStringExtra("getStation");

        routeIds = i.getStringArrayListExtra("getRouteIdSelect");
        stationId = i.getStringExtra("getStationId");

        /*
        int hour = i.getIntExtra("hour", 0);
        int min = i.getIntExtra("min", 0);
*/

        String time = busManager.time;
        int hour = 0;
        int min = 0;


        if (time != "") {
            String hourStr = time.substring(11, 13);
            String minStr = time.substring(14, 16);

            hour = Integer.parseInt(hourStr);
             min = Integer.parseInt(minStr);
                min -= 30;
            if (min < 0 )
            {
                min += 60;
                hour -= 1;

                if (hour < 0)
                {
                    hour += 24;
                }
            }

        }



        // log.v("dsd", name);
        //   Log.v("d", route.get(0));
        // TextView text = (TextView) findViewById(R.id.text);
        //  text.setText(route.get(0));
        spinner= (Spinner) findViewById(R.id.spinner);
        List list = new ArrayList();
        list.add("5 minutes in advance");
        list.add("10 minutes in advance");
        list.add("15 minutes in advance");
        list.add("20 minutes in advance");
        list.add("25 minutes in advance");
        list.add("30 minutes in advance");
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner3= (Spinner) findViewById(R.id.spinner3);
        List list3 = new ArrayList();

        list3.add(Alarm.ONCE);
        list3.add(Alarm.WEEKDAY);
        list3.add(Alarm.WHOLEWEEK);


        ArrayAdapter dataAdapter3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);

        timePicker= (TimePicker) findViewById(R.id.timePicker);


        if (hour != 0 || min != 0)
        {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(min);
        }


      //  mNotification = new Notification(R.drawable.ic_action_view_as_list,"This is a notification.",System.currentTimeMillis());
        //将使用默认的声音来提醒用户
      //  mNotification.defaults = Notification.DEFAULT_SOUND;
      //  mNotificationManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);


    }

    public void buttonOnClick4(View v){

        User user = BusManager.getUser();

        Intent i = getIntent();

        String stopName = (i.getStringExtra("getStation"));
        //route = (ArrayList<String>) getIntent().getStringArrayListExtra("getRouteSelect");

        ///$$
        //Stop stop = busManager.getStopByName(stopName);
        String stopId = stationId;


        String fre = spinner3.getSelectedItem().toString();


        int es_hour = (timePicker.getCurrentHour());
        int es_min = (timePicker.getCurrentMinute());

        Date d = new Date();
        Date es_time = new Date(d.getYear(),d.getMonth(),d.getDate(),es_hour, es_min, 0);

        //Log.v("set alarm:", ((spinner.getSelectedItemPosition() + 1)));

        int min_earlier =  ((spinner.getSelectedItemPosition()+1)*5);

        for (String rid : routeIds)
        {
            Alarm al = new Alarm(fre, es_time, min_earlier, stopId, rid);

            if (busManager.time == "") {
                user.addAlarm(al);
            }
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Your alarm has been created");
        alertDialog.setMessage("Your alarm has been created");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions

            }
        });

        alertDialog.setIcon(R.drawable.ic_action_view_as_list);
        alertDialog.show();


        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Your alarm has been created")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
                */


/*
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }
                )*/



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification1, menu);
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


    public void set(int type, long triggerAtMillis, PendingIntent operation) {

        mService.set(type, triggerAtMillis, operation);

    }

   /* public void setTime(View view) {
        // Do something in response to button

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("You have set");
        alertDialog.setMessage("NOTIFICATION");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions

            }
        });
        alertDialog.setIcon(R.drawable.ic_action_view_as_list);
        alertDialog.show();
    }


*/
   public void setTime(View view) {
       // Do something in response to button

       AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       alertDialog.setTitle("You have set");
       alertDialog.setMessage("NOTIFICATION");
       alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
// here you can add functions

           }
       });
       alertDialog.setIcon(R.drawable.ic_action_view_as_list);
       alertDialog.show();
   }

    @Override
    public void onClick(View v) {

    }
}
