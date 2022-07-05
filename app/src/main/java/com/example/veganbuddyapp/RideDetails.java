//package com.example.veganbuddyapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class RideDetails extends AppCompatActivity {
//
//    private GridView resList;
//    private static final String API_KEY = "AIzaSyAvhvD5YBxPNO2L4bsN745AF8Bi8fpze7w";
//
//    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
//
//    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
//    private static final String TYPE_DETAILS = "/details";
//    private static final String TYPE_SEARCH = "/textsearch";
//    private static final String OUT_JSON = "/json?";
//    private static final String LOG_TAG = "ListRest";
//    float[] result = new float[1];
//    public TextView distance,charges, pCode;
//    float distance1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ride_details);
//
//        distance = findViewById(R.id.rideDistance);
//        charges = findViewById(R.id.rideCharges);
//        pCode = findViewById(R.id.rideAddress);
//        Intent intent = getIntent();
//        String longitude = intent.getStringExtra("long");
//        String latitude = intent.getStringExtra("lat");
//        String postalString = intent.getStringExtra("postalString");
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        Double lng = Double.parseDouble(longitude);
//        Double lat = Double.parseDouble(latitude);
//
////        String locationName = postalString + ", " + "CANADA";
////        Geocoder geoCoder = new Geocoder(this,Locale.getDefault());
////
////        try {
////            List<Address> address = geoCoder.getFromLocationName(locationName, 1);
////            double endlatitude = address.get(0).getLatitude();
////            Log.d("Latitude", latitude);
////            double endlongitude = address.get(0).getLongitude();
////            Log.d("Longitude", longitude);
////            Location.distanceBetween(lng+0,lat+0,endlongitude+0,endlatitude+0,result);
////            distance1 = result[0];
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        Log.d("Distance", String.valueOf(distance1));
////        distance.setText(String.valueOf(distance1));
////        pCode.setText(postalString);
////        charges.setText(String.valueOf(distance1));
//
//        int radius = 10000;
//
//        ArrayList<RestaurantsPage.Place> list = search(lat, lng, radius);
//
//        if (list != null)
//        {
//            resList = (GridView) findViewById(R.id.resListView);
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
//            resList.setAdapter(adapter);
//        }
//    }
//
//    public static ArrayList<RestaurantsPage.Place> search(double lat, double lng, int radius) {
//        ArrayList<RestaurantsPage.Place> resultList = null;
//
//        HttpURLConnection conn = null;
//        StringBuilder jsonResults = new StringBuilder();
//        try {
//            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
//            sb.append(TYPE_SEARCH);
//            sb.append(OUT_JSON);
//            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
//            sb.append("&radius=" + String.valueOf(radius));
//            sb.append("&type=restaurant");
//            sb.append("&keyword=veganbase");
//            sb.append("&key=" + API_KEY);
//
//            URL url = new URL(sb.toString());
//            conn = (HttpURLConnection) url.openConnection();
//            InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//            int read;
//            char[] buff = new char[1024];
//            while ((read = in.read(buff)) != -1) {
//                jsonResults.append(buff, 0, read);
//            }
//        } catch (MalformedURLException e) {
//            Log.e(LOG_TAG, "Error processing Places API URL", e);
//            return resultList;
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error connecting to PLaces API", e);
//            return resultList;
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//
//        try {
//            // Create a JSON object hierarchy from the results
//            JSONObject jsonObj = new JSONObject(jsonResults.toString());
//            JSONArray predsJsonArray = jsonObj.getJSONArray("results");
//
//            // Extract the descriptions from the results
//            resultList = new ArrayList<RestaurantsPage.Place>(predsJsonArray.length());
//            for (int i = 0; i < predsJsonArray.length(); i++) {
//                RestaurantsPage.Place place = new RestaurantsPage.Place();
//                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
//                place.name = predsJsonArray.getJSONObject(i).getString("name");
//                resultList.add(place);
//            }
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, "Error processing JSON results", e);
//        }
//        return resultList;
//    }
//
//    //Value Object for the ArrayList
//    public static class Place {
//        public String reference;
//        public String name;
//
//        public Place(){
//            super();
//        }
//        @Override
//        public String toString(){
//            return this.name; //This is what returns the name of each restaurant for array list
//        }
//    }
//}
