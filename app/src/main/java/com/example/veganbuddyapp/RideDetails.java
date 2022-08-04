package com.example.veganbuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RideDetails extends AppCompatActivity {


    private static final String API_KEY = "AIzaSyAvhvD5YBxPNO2L4bsN745AF8Bi8fpze7w";

    private static final String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";



    private static  String ORIGIN = "";
    private static  String DESTINATION = "";
    private static final String TYPE_SEARCH = "/textsearch";
    private static final String OUT_JSON = "/json?";
    private static final String LOG_TAG = "ListRest";



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
        System.out.println(startlat+" "+startLng);
        System.out.println(endlatitude+" "+endlongitude);
        String postalString = intent.getStringExtra("postalString");
        ORIGIN = startlat+","+startLng;
        DESTINATION = endlatitude+","+endlongitude;

        StringBuilder sb = new StringBuilder(DIRECTIONS_API_BASE);
        sb.append(OUT_JSON);
        sb.append("o");
        sb.append(DESTINATION);

        System.out.println(sb.toString());

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
            Location.distanceBetween(startlat+0,startLng+0,endlatitude+0,endlongitude+0,result);
            distance1 = result[0]/1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Distance", String.valueOf(distance1));
        System.out.println(result[0]);
        distance.setText(String.valueOf(Math.round(distance1))+"KM");
        pCode.setText(postalString);
        charges.setText(String.valueOf(Math.round(distance1))+"CAD");

    }
}
