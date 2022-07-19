package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RestaurantsPage extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerView resList;
    private static final String API_KEY = "AIzaSyAvhvD5YBxPNO2L4bsN745AF8Bi8fpze7w";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/textsearch";
    private static final String OUT_JSON = "/json?";
    private static final String LOG_TAG = "ListRest";
    public String longitude ;
    public String latitude ;
    public String postalString;
    public GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_page);
        //To make google map ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ///-------------------------------------
        Intent intent = getIntent();
        longitude = intent.getStringExtra("long");
        latitude = intent.getStringExtra("lat");
        postalString = "L6V2B5";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Double lng = Double.parseDouble(longitude);
        Double lat = Double.parseDouble(latitude);
        int radius = 10000;

        ArrayList<Place> list = search(lat, lng, radius);

        if (list != null)
        {
            resList = findViewById(R.id.resListView);
            resList.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
            RestaurantAdapter adapter1 = new RestaurantAdapter(this,list);
            resList.setAdapter(adapter1);
        }
    }

    public static ArrayList<Place> search(double lat, double lng, int radius) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_SEARCH);
            sb.append(OUT_JSON);
            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&radius=" + String.valueOf(radius));
            sb.append("&type=restaurant");
            sb.append("&keyword=veganbase");
            sb.append("&key=" + API_KEY);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to PLaces API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            // Extract the descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                //place.name = predsJsonArray.getJSONObject(i).getString("name");
                place.adr_address = predsJsonArray.getJSONObject(i).getString("adr_address");
                resultList.add(place);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }
        return resultList;
    }
    //When Googlee Map is Ready
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng sydney = new LatLng(-34,151);
        gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in  Sydney"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //Value Object for the ArrayList
    public static class Place {
        String reference;
        String name;
        String adr_address;

        public Place(){
            super();
        }
        
        @Override
        public String toString(){
            return this.adr_address; //This is what returns the name of each restaurant for array list
        }
    }


    class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
        Activity activity;
        ArrayList<Place> restautrantList;

        public RestaurantAdapter(Activity activity, ArrayList<Place> restautrantList) {
            this.activity = activity;
            this.restautrantList = restautrantList;
        }

        @NonNull
        @Override
        public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
            return new RestaurantAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.res_name.setText("fgggg");
            holder.res_address.setText(restautrantList.get(position).adr_address);
            holder.res_distance.setText("fgggg");
            holder.res_review.setText("fsddgd");
            holder.res_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RideDetails.class);
                    intent.putExtra("long", longitude);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("postalString", postalString);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return restautrantList.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {

            //                TextView categoryNameTV;
            TextView res_name, res_address, res_distance, res_review;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

//                    categoryNameTV = itemView.findViewById(R.id.categoryNameTV);
                res_name = itemView.findViewById(R.id.res_name);
                res_address = itemView.findViewById(R.id.res_address);
                res_distance = itemView.findViewById(R.id.res_distance);
                res_review = itemView.findViewById(R.id.res_review);
            }
        }
    }
}
