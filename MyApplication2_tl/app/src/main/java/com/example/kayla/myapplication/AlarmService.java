package com.example.kayla.myapplication;

import android.app.Dialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.ArriveDownloadHelper;
import helpers.BusManager;
import helpers.Downloader;
import helpers.DownloaderHelper;
import models.Alarm;
import models.Arrival;
import models.User;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlarmService extends IntentService implements View.OnClickListener {

    private User user = BusManager.getUser();
    BusManager busManager = BusManager.getBusManager();



    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.kayla.myapplication.action.FOO";
    private static final String ACTION_BAZ = "com.example.kayla.myapplication.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.kayla.myapplication.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.kayla.myapplication.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public AlarmService() {
        super("AlarmService");
    }


    static int i = 0;

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }

        while(true) {


            //daily check status of alarm, change status to ACTIVE for every day clock,  at 00:01
            Date cDate = new Date();
            if (cDate.getHours() == 0 && cDate.getMinutes() == 1)
            {
                changeState();
            }
            else
            {}

            //get first valid alarm
            Alarm al = getAlarm();
            if (al != null) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                //button in notification, hav not implemented
                PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
                PendingIntent laterIntent = PendingIntent.getActivity(this, 1, intent, 0);

                Arrival arrival = (Arrival) al.getArr().get(0);

                //get arrival time, extract hour and minute
                String whole = arrival.getArrival_at();
                String time = whole.substring(11, 19);
                int hh = Integer.parseInt(time.substring(0, 2));
                int mm = Integer.parseInt(time.substring(3,5));

                //displayed string in notification
                String first = "No." + busManager.getRouteByID(al.getRouteId()).getLongName() +
                        " will arrive at " + hh + ":" + mm;

                String s2 = "";
                if (al.getArr().size() > 1)   //
                {

                    Arrival arrival2 = (Arrival) al.getArr().get(1);
                    String whole2 = arrival2.getArrival_at();
                    String time2 = whole2.substring(11, 19);
                    int hh2 = Integer.parseInt(time2.substring(0, 2));
                    int mm2 = Integer.parseInt(time2.substring(3,5));

                    s2 = "Next one will arrive at " + hh2 + ":" + mm2;
                }

                //String first = "dumy";
                Notification n = new Notification.Builder(this)
                        .setContentTitle(first)
                        .setContentText(s2)
                                //.setSmallIcon(R.drawable.icon)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.icon_notify)
                        .addAction(R.drawable.icon, "I See", pIntent)
                        .addAction(R.drawable.icon, "Notify Me Later", laterIntent).build();

                n.defaults |= Notification.DEFAULT_SOUND;
                n.defaults |= Notification.DEFAULT_VIBRATE;


                mNotificationManager.notify(1, n);

                //String c = (Context)intent.getStringExtra("context");
                SystemClock.sleep(1000*10); // 10 sec
            }
        }
        //String resultTxt = msg + " "
        //        + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Alarm getAlarm()
    {
        Alarm re = null;


        for (Alarm al : user.getAlarms()) {
            boolean isalarm  = false;

            if (isalarm)
                break;

            String routeId = al.getRouteId();
            String stopId = al.getStartStopId();

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


            if (networkInfo != null && networkInfo.isConnected()) {

                MainActivity.downloadsOnTheWire += 5;

                String ARRIVAL_URL = DownloaderHelper.TRANSLOC_URL + "/arrival-estimates.json?" + "agencies=116&routes=" + routeId + "&stops=" + stopId;
                new Downloader(new ArriveDownloadHelper(), getApplicationContext()).execute(ARRIVAL_URL);
            } else {
            }

            //user.downloadArr(getApplicationContext(), (ConnectivityManager) (getSystemService(Context.CONNECTIVITY_SERVICE)));

            SystemClock.sleep(1000*60);  //wati for donwload arrival info


            for (Arrival arr : busManager.getArrivals())
            {
                String  whole = arr.getArrival_at();
                String time = whole.substring(11, 19);


                int hh = Integer.parseInt(time.substring(0, 2));
                int mm = Integer.parseInt(time.substring(3, 5));

                Date current = new Date();
                Date aDate_Arr  = new Date(current.getYear(),current.getMonth(),current.getDate(), hh, mm, 0);

                //java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date cDate = new Date();

                Date eDate = al.getEstimate_time();

                if (eDate.getTime() < aDate_Arr.getTime()  && 0 < (eDate.getTime() - cDate.getTime()) &&
                        (eDate.getTime() - cDate.getTime())/1000/60 < al.getPriorio_min() && al.getStatus() == Alarm.ACTIVE)
                {
                    re = al;

                    if (al.getFrequency() == Alarm.ONCE) {
                        busManager.getUser().getAlarms().remove(al);
                    }
                    else
                    {
                        al.setStatus(Alarm.NOTIFIED);
                    }

                    re.getArr().add(arr);

                    if (busManager.getArrivals().indexOf(arr) < busManager.getArrivals().size() - 1)
                    {
                        re.getArr().add(busManager.getArrivals().get(busManager.getArrivals().indexOf(arr) + 1));
                    }
                    isalarm = true;

                    //break;
                    return re;
                }

                else
                {}
            }
        }

        return re;
    }

    public void changeState()
    {
        for (Alarm al : busManager.getUser().getAlarms())
        {
            if (al.getFrequency() == Alarm.WHOLEWEEK)
            {
                al.setStatus(Alarm.ACTIVE);
            }
            else
            {}
        }
    }


    @Override
    public void onClick(View v) {

    }
}
