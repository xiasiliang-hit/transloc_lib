package com.example.kayla.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import helpers.BusManager;
import models.Alarm;
import android.widget.Button;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ActivityEdit extends ActionBarActivity {

    BusManager busManager = BusManager.getBusManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_edit);

       // BusManager busManager = BusManager.getBusManager();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup1);

        final RadioButton[] rbutton=new RadioButton[ busManager.getUser().getAlarms().size()];
        int i=0;
        for (Alarm al : busManager.getUser().getAlarms())
        {

            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            rbutton[i] = new RadioButton(getApplicationContext());
            rbutton[i].setText("Route:" + al.getRouteId() + "\nLeaving Time:" + al.getEstimate_time());
            rbutton[i].setTextColor(Color.BLACK);
            ll.addView(rbutton[i]);
            radioGroup.addView(ll);
            i++;
          /*  checkbox[j].setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox[j].isChecked())


                }
            });*/
        }
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.rl1);
        if(busManager.getUser().getAlarms().size()!=0)
        {
            Button bt1 = (Button)findViewById(R.id.button10);

            Button bt2 = (Button)findViewById(R.id.button11);
         bt1.setVisibility(View.VISIBLE);
            bt2.setVisibility(View.VISIBLE);
            bt1.setBackgroundColor(0xff008080);
            bt1.setText("Edit it");
            bt1.setTextColor(Color.WHITE);
            bt1.setTextSize(28);
          //  RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
             //       RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            //params.topMargin =  200;
           // bt1.setLayoutParams(params);
            bt2.setBackgroundColor(0xffff3700);
            bt2.setText("Delete it");
            bt2.setTextColor(Color.WHITE);
            bt2.setTextSize(28);
          //  RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                  //  RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

           // params1.topMargin = 400;
         //   bt2.setLayoutParams(params);
         //   layout.addView(bt1);
          //  layout.addView(bt2);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for(int j=0;j<busManager.getUser().getAlarms().size();j++)
                    {
                       if( rbutton[j].isChecked())
                       {

                        //  busManager.getUser().getAlarms().get(j).getRouteId();
                        //   busManager.getUser().getAlarms().get(j).getEstimate_time();
                         String time = String.valueOf((busManager.getUser().getAlarms().get(j).getEstimate_time()));
                           Intent ii = new Intent(getApplicationContext(),notification2.class);
                           ii.putExtra("gettime",  time);
                           ii.putExtra("getRouteId", busManager.getUser().getAlarms().get(j).getRouteId()
                           );

                           //i.putExtra("getRoute",route);
                           ii.putExtra("getStationId", busManager.getUser().getAlarms().get(j).getStartStopId());

                        //   ii.putStringArrayListExtra("getRouteIdSelect", routeIdListSel);



                           startActivity(ii);
                       }

                        // Perform action on click
                    }

                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    for(int j=0;j<busManager.getUser().getAlarms().size();j++)
                    {
                        if( rbutton[j].isChecked())
                        {
                          busManager.getUser().getAlarms().remove(busManager.getUser().getAlarms().get(j));
                        //    busManager.getUser().getAlarms().remove(al)
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Your alarm has deleted", Toast.LENGTH_SHORT).show();
                        }

                        // Perform action on click
                    }
                }
            });
        }
        else
        {
            TextView tx = new TextView(this);
            tx.setText("There is no alarm ");
            tx.setTextColor(Color.BLACK);
            tx.setTextSize(25);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            params2.topMargin =  200;
            tx.setLayoutParams(params2);
            layout.addView(tx);
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
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
