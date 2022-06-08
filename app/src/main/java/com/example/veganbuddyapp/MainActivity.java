package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText postalCode;
    Button searchPc;
    String postalString;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------------------
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
        drawerLayout = findViewById(R.id.my_drawer_layout);
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
                        Intent intent = new Intent(MainActivity.this,ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:

                    case R.id.nav_payment:

                    default:
                        return true;
                }
            }
        });


        //Navigation method
        switchToSecondActivity = findViewById(R.id.nav_account);



    }

    //Function to Validate the code
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