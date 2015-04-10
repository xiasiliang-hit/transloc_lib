package com.example.kayla.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import helpers.BusManager;
import models.Alarm;
import models.User;

public class notification2 extends ActionBarActivity {
Spinner spinner4;
    Spinner spinner5;
    TimePicker tp;
    String stationId;
    String time;
    String routeId;
    Date date;
 //   ArrayList<String> routeIds = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        Intent i = getIntent();
       routeId = i.getStringExtra("getRouteId");
        time = i.getStringExtra("gettime");
        stationId = i.getStringExtra("getStationId");
        String strArray[] = time.split(" ");
       // time = i.getStringExtra("getTime");
    //    routeIds = i.getStringArrayListExtra("getRouteIdSelect");
                     //Date timee = (Date)time;
       // DateFormat format = new SimpleDateFormat("HH:MM:SS SSS yyyy", Locale.ENGLISH);
      //  Date date;

String hour[] = strArray[3].split(":");

        spinner4= (Spinner) findViewById(R.id.spinner4);
        List list = new ArrayList();
        list.add("5 minutes in advance");
        list.add("10 minutes in advance");
        list.add("15 minutes in advance");
        list.add("20 minutes in advance");
        list.add("25 minutes in advance");
        list.add("30 minutes in advance");
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter);


        spinner5= (Spinner) findViewById(R.id.spinner5);
        List list3 = new ArrayList();

        list3.add(Alarm.ONCE);
        list3.add(Alarm.WEEKDAY);
        list3.add(Alarm.WHOLEWEEK);


        ArrayAdapter dataAdapter3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(dataAdapter3);

        tp= (TimePicker) findViewById(R.id.timePicker3);
       tp.setCurrentHour(Integer.valueOf(hour[0]));
        tp.setCurrentMinute(Integer.valueOf(hour[1]));
       // tp.setCurrentHour();
    }

    public void buttonOnClick10(View v) {

        User user = BusManager.getUser();

      //  Intent i = getIntent();

       // String stopName = (i.getStringExtra("getStation"));
        //route = (ArrayList<String>) getIntent().getStringArrayListExtra("getRouteSelect");

        ///$$
        //Stop stop = busManager.getStopByName(stopName);
        String stopId = stationId;


        String fre = spinner5.getSelectedItem().toString();


        int es_hour = (tp.getCurrentHour());
        int es_min = (tp.getCurrentMinute());

        Date d = new Date();
        Date es_time = new Date(d.getYear(), d.getMonth(), d.getDate(), es_hour, es_min, 0);

        //Log.v("set alarm:", ((spinner.getSelectedItemPosition() + 1)));

        int min_earlier = ((spinner4.getSelectedItemPosition() + 1) * 5);

      //  for (String rid : routeIds) {
            Alarm al = new Alarm(fre, es_time, min_earlier, stopId, routeId);
            user.addAlarm(al);
      //  }


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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification2, menu);
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
}
