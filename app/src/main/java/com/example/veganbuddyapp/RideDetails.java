package com.example.veganbuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RideDetails extends AppCompatActivity {


    private static final String API_KEY = "AIzaSyAvhvD5YBxPNO2L4bsN745AF8Bi8fpze7w";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    float[] result = new float[1];
    public TextView distance, charges, pCode;
    float distance1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        distance = findViewById(R.id.rideDistance);
        charges = findViewById(R.id.rideCharges);
        pCode = findViewById(R.id.rideAddress);
        Intent intent = getIntent();
        double longitude = intent.getDoubleExtra("long",0);
        double latitude = intent.getDoubleExtra("lat",0);
        String postalString = intent.getStringExtra("postalString");

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        Double lng = longitude;
        Double lat = latitude;

        String locationName = postalString + ", " + "CANADA";
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> address = geoCoder.getFromLocationName(locationName, 1);
            double endlatitude = address.get(0).getLatitude();
            Log.d("Latitude", String.valueOf(latitude));
            double endlongitude = address.get(0).getLongitude();
            Log.d("Longitude", String.valueOf(longitude));
            Location.distanceBetween(lng+0,lat+0,endlongitude+0,endlatitude+0,result);
            distance1 = result[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Distance", String.valueOf(distance1));
        distance.setText(String.valueOf(distance1));
        pCode.setText(postalString);
        charges.setText(String.valueOf(distance1));

    }
}
