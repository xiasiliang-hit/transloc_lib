package com.example.kayla.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import java.util.Date;
import java.text.*;
import android.view.View;


public class schedule extends ActionBarActivity {
    private static String calanderURL = "";
    private static String calanderEventURL = "";
    private static String calanderRemiderURL = "";
    //为了兼容不同版本的日历,2.2以后url发生改变
    //  Button[] button;

    String calName[] = new String[256];
    String calLocation[] = new String[256];
    long calStart[]= new long[256];
    String dateFormatted[]= new String[256];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* String[] projection = new String[] { "_id", "name" };
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor =
                managedQuery(calendars, projection, "selected=1", null, null);
        if (managedCursor.moveToFirst()) {
            String calName;
            String calId;
            int nameColumn = managedCursor.getColumnIndex("name");
            int idColumn = managedCursor.getColumnIndex("_id");
            do {
                calName = managedCursor.getString(nameColumn);
                calId = managedCursor.getString(idColumn);
            } while (managedCursor.moveToNext());

        }*/
      /*  Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity"));
        startActivity(intent);*/
        calanderURL = "content://com.android.calendar/calendars";
        calanderEventURL = "content://com.android.calendar/events";
        calanderRemiderURL = "content://com.android.calendar/reminders";

        Cursor eventCursor = getContentResolver().query(Uri.parse(calanderEventURL), null,
                null, null, null);
       /* if(eventCursor.getCount() > 0){
            eventCursor.moveToLast();
            String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
            Toast.makeText(schedule.this, eventTitle, Toast.LENGTH_LONG).show();
        }*/
       /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);*/

        if(eventCursor.moveToFirst()) {

            int i = 0;
            int nameColumn = eventCursor.getColumnIndex("title");
            int location = eventCursor.getColumnIndex("eventLocation");
            int start = eventCursor.getColumnIndex("dtstart");
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.RelativeLayout);

            //  int idColumn = eventCursor.getColumnIndex("_id");
            do {
                calName[i] = eventCursor.getString(nameColumn);
                calLocation[i] = eventCursor.getString(location);
                if (calLocation[i] == null || calLocation[i].equals(""))
                    calLocation[i] = "";
                calStart[i] = eventCursor.getLong(start);
                if (!calLocation[i].equals("")) {

                    Date date = new Date(calStart[i]);
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    dateFormatted[i] = formatter.format(date);
                    Date dt1 = null;
                    try {
                        dt1 = formatter.parse(dateFormatted[i]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date dt2 = null;
                    try {
                        dt2 = formatter.parse("2015-03-25 15:21");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (dt1.getTime() > dt2.getTime())
                    {Button button = new Button(this);
                        button.setText(calName[i] + " at: " + calLocation[i] + " " + dateFormatted[i]);
                        button.setBackgroundColor(0xff008080);
                        button.setTextColor(0xffffffff);
                        //   RelativeLayout layout = (RelativeLayout)findViewById(R.id.RelativeLayout);

                        //  layout.addView(button);
                        //  TextView textview = new TextView(this);
                        button.setId(i);
                        //  textview.setText(calName);
                        button.setTextSize(20);
                        //   textview.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        // relativeParams.addRule(RelativeLayout.BELOW, textview.getId());
                        // layout.addView(textview, relativeParams);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                        params.topMargin = (i) * 200;
                        button.setLayoutParams(params);
                        layout.addView(button);
                        i++;
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                // Perform action on click
                                Intent i = new Intent(getApplicationContext(), getLocation.class);
                                int i1 = v.getId();
                                i.putExtra("location", calLocation[i1]);
                                i.putExtra("name", calName[i1]);
                                i.putExtra("time", dateFormatted[i1]);
                                startActivity(i);
                            }
                        });
                    }
                }
            } while (eventCursor.moveToNext());

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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
