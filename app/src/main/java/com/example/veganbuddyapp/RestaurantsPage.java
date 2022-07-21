package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.protobuf.StringValue;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
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
    public double longitude;
    public double latitude;
    public String postalString;
    public GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LatLng sydney;
    Location location;
    Task<Location> gLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_page);
        //To make google map ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ///-------------------------------------
        Intent intent = getIntent();
//        longitude = intent.getStringExtra("long");
//        latitude = intent.getStringExtra("lat");
        postalString = intent.getStringExtra("postalString");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        double lng = Double.parseDouble(longitude);
//        double lat = Double.parseDouble(latitude);
        double lng = -79.347015;
        double lat = 43.651070;
        int radius = 10000;

        ArrayList<Place> list = search(lat, lng, radius);

        if (list != null) {
            resList = findViewById(R.id.resListView);
            resList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
            RestaurantAdapter adapter1 = new RestaurantAdapter(this, list);
            resList.setAdapter(adapter1);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }


    public static ArrayList<Place> search(double lat, double lng, int radius) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_SEARCH);
            sb.append(OUT_JSON);
            sb.append("location=" + lat + "," + lng);
            sb.append("&radius=" + radius);
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
//            Log.d(String.valueOf(predsJsonArray), "search: ");
            // Extract the descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                place.name = predsJsonArray.getJSONObject(i).getString("name");
                place.address = predsJsonArray.getJSONObject(i).getString("formatted_address");
                place.latitude1 = predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                place.longitude1 = predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                System.out.println(predsJsonArray.getJSONObject(i));

                resultList.add(place);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }
        return resultList;
    }


    //When Google Map is Ready
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

//        sydney = new LatLng(getIntent().getDoubleExtra("latitude",0) ,  getIntent().getDoubleExtra("longitude",0));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Log.d("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "onMapReady: ");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            gLastLocation = fusedLocationProviderClient.getLastLocation();
            gLastLocation.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if(task.isSuccessful()){
                        location = task.getResult();
                        if(location != null){

                            sydney = new LatLng(location.getLatitude(),location.getLongitude());
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in  MyLocation"));
                            gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                        else{
                            sydney = new LatLng(43.651070,-79.347015);
                            gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in  Toronto"));
                            gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                    }
                }
            });
            return;
        }

        gMap.setMyLocationEnabled(true);
//        gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in  Sydney"));
//        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    //Value Object for the ArrayList
        public static class Place {
            String reference;
            String name;
            String address;
            String latitude1;
            String longitude1;

            public Place(){
            super();
            }
            @Override
            public String toString(){
                return this.name; //This is what returns the name of each restaurant for array list
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
                holder.res_name.setText(restautrantList.get(position).name);
                holder.res_address.setText(restautrantList.get(position).address);
                holder.res_distance.setText(restautrantList.get(position).latitude1);
                holder.res_review.setText("fsddgd");
                holder.res_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RideDetails.class);
                        Log.d(String.valueOf(longitude), "onClick: ");
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
