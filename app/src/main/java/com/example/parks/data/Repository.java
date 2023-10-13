package com.example.parks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.parks.model.Park;
import com.example.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();

    public static void getParks(final AsyncResponse callback){
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, Util.PARKS_URL,null, response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i =0; i<jsonArray.length();i++){
                            Park park = new Park();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            park.setId(jsonObject.getString("id"));
                            park.setFullName(jsonObject.getString("fullName"));
                            park.setLatitude(jsonObject.getString("latitude"));
                            park.setLongitude(jsonObject.getString("longitude"));
                            parkList.add(park);
                        }
                        if(null != callback) {
                            callback.processPark(parkList);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }, error -> {
                    error.printStackTrace();
                });
    }
}
