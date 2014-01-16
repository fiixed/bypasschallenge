package com.fiixed.bypasschallenge;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abell on 12/9/13.
 */
public class JSONFetcher {
    public static final String TAG = "JSONFetcher";

    private static final String ENDPOINT = "http://integration.bypasslane.com/api/anywhere/concessions/1/categories/283";

    private String jsonString;
    private JSONObject burger;


    /*
    fetches raw data from a URL and returns it as an array of bytes
     */
    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //creates a connection object pointed at the URL

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();  //connects to the endpoint

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0) {  //calls read until the connection runs out of data, InputStream yeilds bytes as they are available
                out.write(buffer, 0, bytesRead);
            }
            out.close();  //once data is finished we close the connection
            return out.toByteArray();  //and return the ByteArrayOutputStream array
        } finally {
            connection.disconnect();
        }
    }

    /*
    converts the result of getURLBytes into a string
     */
    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public ArrayList<BurgersDogs> fetchItems() {
        ArrayList<BurgersDogs> items = new ArrayList<BurgersDogs>();
        try {

            Log.i(TAG, ENDPOINT);
            jsonString = getUrl(ENDPOINT);
            Log.i(TAG, "Received json: " + jsonString);
            parseItems(items);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return items;
    }
    /*
    json parsing and adding BurgersDogs objects to ArrayList
     */
    void parseItems(ArrayList<BurgersDogs> bypass) {
        try {
            JSONObject rootJSON = new JSONObject(jsonString);
            JSONArray items = rootJSON.getJSONArray("items");

            for(int i = 0; i < items.length(); i++) {
                burger = items.getJSONObject(i);
                String title = burger.getString("name");
                String price = burger.getString("price");

                BurgersDogs item = new BurgersDogs();
                item.setTitle(title);
                item.setPrice(Double.parseDouble(price));
                bypass.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

