package com.example.kayla.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


import java.util.ArrayList;
import android.content.Context;

import helpers.BusManager;

public class MainActivity4 extends ActionBarActivity {
    RadioGroup radioGroup;

    ArrayList<String> route = new ArrayList<String>();
    ArrayList<String> routeSel= new ArrayList<String>();
    CheckBox[] checkbox ;
    String station;
    // String getroute=null;

    ArrayList<String> routeIdList = null;
    ArrayList<String> routeIdListSel = new ArrayList<String>();
    String stationId = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        //   String message = i.getStringExtra("uName");


        BusManager busManager = BusManager.getBusManager();

        if (i.getStringExtra("ACTID") == "MapActivityEndStop")
        {
            for (String s : busManager.getStartStop().getRouteIds())
            {
                if (busManager.getEndStop().getRouteIds().contains(s))
                {
                    routeIdList.add(s);
                    int p = busManager.getStartStop().getRouteIds().indexOf(s);
                    route.add(
                    busManager.getStartStop().getRouteNames().get(p));
                }
            }

            station = busManager.getStartStop().getName();
            stationId = busManager.getEndStop().getName();
        }

        else {
            route = (ArrayList<String>) getIntent().getStringArrayListExtra("getRoute");
            routeIdList = (ArrayList<String>) getIntent().getStringArrayListExtra("getRouteIdList");

            station = i.getStringExtra("getStation");
            stationId = i.getStringExtra("getStationId");
        }

        checkbox = new CheckBox[route.size()];

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        for(int j=0;j<route.size();j++)
        {
            checkbox[j] = new CheckBox(this);
            checkbox[j].setText(route.get(j));
            checkbox[j].setId(j + 100);
            // checkbox[j].setBackgroundColor(Color.rgb(0+j*10,0+j*5,100+j*5));
          /*  checkbox[j].setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox[j].isChecked())


                }
            });*/
            radioGroup.addView(checkbox[j]);
        }

        //  Button button = (Button)findViewById(R.id.button6);

        //    button.setTextSize(28);
        //   button.setText(station);

        TextView tx= (TextView)findViewById(R.id.textView18);
        tx.setText(station);
        //  findViewById(R.id.action_list);
    }

    public void buttonOnClick3(View v){
        // Button btnOk = (Button) findViewById(R.id.button);

        for(int i = 0;i<route.size();i++)
        {
            if(checkbox[i].isChecked()) {
                // getroute += route.get(i);
                routeSel.add(route.get(i));
                routeIdListSel.add(routeIdList.get(i));
            }
        }
        if(routeSel.size()!=0)
        {    Intent ii = new Intent(getApplicationContext(),notification1.class);
            //   Bundle routeNum = new Bundle();
            //   routeNum.putSerializable("Route", new int[]{15,2,12,2});
            //  routeNum.putSerializable("ad",new String[]{"5","6"});
            //    i.putExtra("getRoute", routeNum);


            //   ii.putExtra("getRoute",getroute);
            ii.putExtra("getStation", station);
            ii.putExtra("getStationId", stationId);

            //i.putExtra("getRoute",route);
            ii.putStringArrayListExtra("getRouteSelect", routeSel);
            ii.putStringArrayListExtra("getRouteIdSelect", routeIdListSel);



            startActivity(ii);}
        else {
            Context context = this.getApplicationContext();
            Toast.makeText(context, "Please choose at least a route", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity4, menu);
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

        return super.onOptionsItemSelected(item);}


}
