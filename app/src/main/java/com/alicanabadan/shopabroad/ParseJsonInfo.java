package com.alicanabadan.shopabroad;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alican on 3/13/2017.
 */

public class ParseJsonInfo {
    private final static String TAG = "CityInfo_HttpURLConn";
    private static final String TAG_RESULTS = "response";
    private static final String TAG_CITY_NAME = "name";
    private static final String TAG_CITY_CODE = "code";
    List<City> cities = new ArrayList<>();

    // constructors
    public ParseJsonInfo() {
    }

    public List<City> decodeMessage(String message) {
        try {
            Log.d(TAG, "Parsing: ");
            JSONObject jObject;
            jObject = new JSONObject(message);
            JSONArray results = jObject.getJSONArray(TAG_RESULTS);
            // loop through the JSONArray and get the items
            for (int i = 0; i < results.length(); i++) {
                String code = results.getJSONObject(i).getString(TAG_CITY_CODE).toString();
                String name = results.getJSONObject(i).getString(TAG_CITY_NAME).toString();
                cities.add(new City(name,code));
            }
        } catch (Exception e) {
            Log.e(TAG, "decodeMessage: exception during parsing");
            e.printStackTrace();
            return null;
        }
        return cities;
    }
}
