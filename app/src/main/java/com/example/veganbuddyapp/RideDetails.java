package com.example.veganbuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        double endlongitude = intent.getDoubleExtra("long",0);
        double endlatitude = intent.getDoubleExtra("lat",0);
        double startLng = intent.getDoubleExtra("mylng",0);
        double startlat = intent.getDoubleExtra("mylat",0);
        String postalString = intent.getStringExtra("postalString");

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

//        Double lng = Double.parseDouble(longitude);
//        Double lat = Double.parseDouble(latitude);

//        String locationName = postalString + ", " + "CANADA";
//        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
//
//        try {
//            List<Address> address = geoCoder.getFromLocationName(locationName, 1);
//            double endlatitude = address.get(0).getLatitude();
//            Log.d("Latitude", latitude);
//            double endlongitude = address.get(0).getLongitude();
//            Log.d("Longitude", longitude);
//            Location.distanceBetween(lng+0,lat+0,endlongitude+0,endlatitude+0,result);
//            distance1 = result[0];
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            Location.distanceBetween(startLng,startlat,endlongitude,endlatitude,result);
            distance1 = result[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Distance", String.valueOf(distance1));
        System.out.println(result);
        distance.setText(String.valueOf(distance1));
        pCode.setText(postalString);
        charges.setText(String.valueOf(distance1));

    }
}
