/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Telephony;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.kayla.myapplication.AlarmNotification;
import com.example.kayla.myapplication.MainActivity;
import com.example.kayla.myapplication.R;

import helpers.ArriveDownloadHelper;
import helpers.BusManager;
import helpers.Downloader;
import helpers.DownloaderHelper;
import helpers.SegmentDownloaderHelper;
import helpers.TimeDownloaderHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author danny
 */
public class User {


    private static final User singleUser = null;


    private static ArrayList<Alarm> alarms = null;



    public ArrayList<Alarm> getAlarms()
    {
        return alarms;
    }

    public static  AtomicReference<String> result = new AtomicReference<String>();
    public final static CountDownLatch latch = new CountDownLatch(1);

    private static int i = 1;

    String name = "";
    Stop startStop = null;
    Stop endStop = null;
    
    static ArrayList<Route> mRoutes = null;

    
    private User()
    {
        alarms = new ArrayList<Alarm>();
    }
    
    public void addAlarm(Alarm a)
    {
        alarms.add(a);
    }

    //
    public Alarm checkAlarms(Context context, ConnectivityManager connMgr )
    {
        //Context context = getApplicationContext();
        BusManager busManager = BusManager.getBusManager();
        Alarm re = null;

        for (Alarm al : alarms)
        {
            String routeId = al.getRouteId();
            String stopId = al.getStartStopId();

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                MainActivity.downloadsOnTheWire += 5;

                String ARRIVAL_URL = DownloaderHelper.TRANSLOC_URL + "/arrival-estimates.json?" + "agencies=116&routes=" + routeId + "&stops=" + stopId;
                new Downloader(new ArriveDownloadHelper(), context).execute(ARRIVAL_URL);
            }
            else
            {}
            //sleep(10000);


            for (Arrival arr : busManager.getArrivals())
            {
                String  time = arr.arrival_at;
                Date aDate_Arr  = new Date(time);

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date cDate = new Date();

                Date eDate = al.getEstimate_time();

                if (eDate.getTime() < aDate_Arr.getTime()  && 0 < (eDate.getTime() - cDate.getTime()) &&
                        (eDate.getTime() - cDate.getTime())*1000*60 < al.getPriorio_min() )
                {
                    re = al;
                    re.getArr().add(arr);

                    if (busManager.getArrivals().indexOf(arr) < busManager.getArrivals().size() - 1)
                    {
                        re.getArr().add(busManager.getArrivals().get(busManager.getArrivals().indexOf(arr) + 1));
                    }
                    break;
                }
            }
        }

        return re;
    }

    public void downloadArr(Context context, ConnectivityManager connMgr)
    {
        BusManager busManager = BusManager.getBusManager();
        Alarm re = null;

        for (Alarm al : alarms) {
            String routeId = al.getRouteId();
            String stopId = al.getStartStopId();

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                MainActivity.downloadsOnTheWire += 5;

                String ARRIVAL_URL = DownloaderHelper.TRANSLOC_URL + "/arrival-estimates.json?" + "agencies=116&routes=" + routeId + "&stops=" + stopId;
                new Downloader(new ArriveDownloadHelper(), context).execute(ARRIVAL_URL);
            } else {
            }

        }
    }



    public static synchronized User getUser()
    {
        if (singleUser == null)
        {
            return new User();
        }
        else
        {
            return singleUser;
        }
    }


    public void delete(Alarm a)
    {
        alarms.remove(a);
    }

}
