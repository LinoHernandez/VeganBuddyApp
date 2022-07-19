package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private View resLayout;

    EditText postalCode;
    Button searchPc;
    String postalString;

    private FirebaseAuth mAuth;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    public LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        resLayout = findViewById(R.id.resLayout);


        postalCode = findViewById(R.id.postalCode);
        searchPc = findViewById(R.id.searchPc);
        navigationView = findViewById(R.id.navView);
        searchPc.setVisibility(View.INVISIBLE);
        searchPc.setClickable(false);
        navigationView = findViewById(R.id.navView);
        //OnKeyListener on PostalCode TextView for enter key
        postalCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                postalString = String.valueOf(postalCode.getText());
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    validatePostalCode(postalString);
                    return true;
                }
                return false;
            }
        });

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.resLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_account:
                        Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginPage.class));
                        return true;
                    case R.id.nav_payment:
                        Intent payment = new Intent(MainActivity.this,PaymentPage.class);
                        startActivity(payment);
                        return true;
                    default:
                        return true;
                }
            }
        });

        searchPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRestaurants();
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void startRestaurants() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            Intent intent = new Intent(this, RestaurantsPage.class);
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location == null){
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Snackbar.make(resLayout,"Unable to use GPS",
                        Snackbar.LENGTH_SHORT).show();
                String longit = "43.78956";
                String lat = "-79.58964";
                intent.putExtra("long", longit);
                intent.putExtra("lat", lat);
                intent.putExtra("postalString", postalString);
                startActivity(intent);
            }
            else
            {
                Double longitude = location.getLongitude();
                Double latitude = location.getLatitude();
                String longit = Double.toString(longitude);
                String lat = Double.toString(latitude);
                intent.putExtra("long", longit);
                intent.putExtra("lat", lat);
                intent.putExtra("postalString", postalString);
                startActivity(intent);
            }
        }
    }

    public void showRestaurants(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
            //Permission is already available. Show restaurants
            Snackbar.make(resLayout,"Location permission available. Show restaurants.",
                    Snackbar.LENGTH_SHORT).show();
            startRestaurants();
        }else {
            //Permission is missing and must be requested.
            requestLocationPermission();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            //Request for location permission.
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                //Permission has been granted. Start preview Activity.
                Snackbar.make(resLayout, "Location permission granted. Showing restaurants.",
                        Snackbar.LENGTH_SHORT).show();
                startRestaurants();
            } else {
                //Permission request was denied.
                Snackbar.make(resLayout, "Location permission request was denied.",
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void requestLocationPermission(){
        //Permission has not been granted and must be requested
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Provide an additional rationale to the user if the permission was not granted
            //and the user would benefit from additional context for the use of the permission.
            //Display a SnackBar with a button to request the missing permission.
            Snackbar.make(resLayout,"Location access is required to display restaurants near you.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_LOCATION);
                }
            }).show();
        } else {
            Snackbar.make(resLayout, "Permission is not available. Requesting location permission.",
                    Snackbar.LENGTH_SHORT).show();
            //Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_LOCATION);
        }
    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, RegisterUser.class));
        }
        //updateUI(currentUser);
    }

    //Function to Validate the Postal code
    public void validatePostalCode(String postalString) {

        //Regression for Canadian Postal Code
        String regex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(postalString);
        if (matcher.matches()) {
            searchPc.setVisibility(View.VISIBLE);
            searchPc.setClickable(true);
        } else {
            searchPc.setVisibility(View.INVISIBLE);
            searchPc.setClickable(false);
        }

    }



    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
            }

        return super.onOptionsItemSelected(item);

    }


}