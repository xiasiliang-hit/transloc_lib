package com.example.kayla.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import helpers.BusManager;


public class MainActivity2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BusManager busManager = BusManager.getBusManager();
        busManager.time = "";

    }

    public void buttonOnClick1(View v){
        Button button4 = (Button)v;
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));

        BusManager busManager = BusManager.getBusManager();
        busManager.time = "";
    }
    public void buttonOnClick2(View v){
        //  Button button4 = (Button)v;
        startActivity(new Intent(getApplicationContext(),schedule.class));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
