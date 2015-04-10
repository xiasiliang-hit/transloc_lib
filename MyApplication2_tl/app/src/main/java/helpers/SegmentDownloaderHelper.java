package helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SegmentDownloaderHelper implements DownloaderHelper {
    public static final String SEGMENT_JSON_FILE = "segments.json";// = "segmentJson";
    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {
        BusManager.parseSegments(jsonObject);
        Downloader.cache(SEGMENT_JSON_FILE, jsonObject);
    }
}