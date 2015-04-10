package helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//import com.nyubustracker.R;
import com.example.kayla.myapplication.MainActivity;

import org.json.JSONObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Downloader extends AsyncTask<String, Void, JSONObject> {
    final DownloaderHelper helper;
    public static final String CREATED_FILES_DIR = "TransCachedFiles";
    static Context context;

    public static Context getContext() {
        return context;
    }

    public Downloader(DownloaderHelper helper, Context mContext) {
        this.helper = helper;
        context = mContext;
    }

    @Override
    public JSONObject doInBackground(String... urls) {
        try {
            //if (MainActivity.LOCAL_LOGV) Log.v(MainActivity.REFACTOR_LOG_TAG, "First url: " + urls[0]);
            return new JSONObject(downloadUrl(urls[0]));
        } catch (IOException e) {
            //Log.e("JSON", "DownloadURL IO error.");
            e.printStackTrace();
        } catch (JSONException e) {
            //Log.e("JSON", "DownloadURL JSON error.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            helper.parse(result);
            if (MainActivity.LOCAL_LOGV) Log.v(MainActivity.REFACTOR_LOG_TAG, "helper class: " + helper.getClass() + " (" + MainActivity.downloadsOnTheWire + ")");
            if (!helper.getClass().toString().contains("BusDownloaderHelper")) MainActivity.pieceDownloadsTogether(context);
        } catch (JSONException e) {
            Log.d(MainActivity.REFACTOR_LOG_TAG, "JSON Exception while parsing in onPostExecute.");
            e.printStackTrace();
        } catch (Exception e) {

            e.getMessage();
            Log.d(MainActivity.REFACTOR_LOG_TAG, "IO Exception while parsing in onPostExecute.");
        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    public String downloadUrl(String myUrl) throws IOException {
        Log.v("downloadurl:", "in");


     //myUrl = "http://maps.google.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=14&size=512x512&maptype=roadmap";


        InputStream is = null;
        String str = "";


        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            if (myUrl.contains("googleapis")) {

                conn.setRequestProperty("Authorization", "key=AIzaSyAphkafhOLJaAYWTp6dGsMoO8G3YdhDxAc");

                conn.connect();

                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));

                java.lang.StringBuffer sb = new java.lang.StringBuffer();
                str = br.readLine();
                while (str != null) {
                    sb.append(str);
                    str = br.readLine();
                }
                br.close();


            } else {
                conn.setRequestProperty("X-Mashape-Authorization", BusManager.mashkey);
                conn.connect();
                //int response = conn.getResponseCode();
                //Log.d("JSON", "The response is: " + response);
                is = conn.getInputStream();
                str = readIt(is);

            }
            // Starts the QUERY


            // Convert the InputStream into a string

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        finally {
            if (is != null) {
                is.close();
            }
        }



        return str;
    }

    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"), 1280);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void cache(String fileName, JSONObject jsonObject) throws IOException {
        if (jsonObject != null && !jsonObject.toString().isEmpty()) {
            File path = new File(context.getFilesDir(), CREATED_FILES_DIR);
            path.mkdir();
            File file = new File(path, fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.close();
        }
    }

    public static String makeQuery(String param, String value, String charset) {
        try {
            return String.format(param + "=" + URLEncoder.encode(value, charset)) + "&geo_area=29.6465%2C-82.3234%7C2000";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
