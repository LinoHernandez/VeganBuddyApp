package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class RegisterPage extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    int counter = 5;
    Button login,register;
    EditText username,password;
    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logregister_screen);

        navigationView = findViewById(R.id.navView);

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
                        Intent intent = new Intent(RegisterPage.this,ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        Intent register = new Intent(RegisterPage.this,RegisterPage.class);
                        startActivity(register);
                        return true;
                    case R.id.nav_payment:
                        Intent payment = new Intent(RegisterPage.this,PaymentPage.class);
                        startActivity(payment);
                        return true;
                    case R.id.nav_menu:
                        Intent menu = new Intent(RegisterPage.this,MainActivity.class);
                        startActivity(menu);
                        return true;
                    default:
                        return true;
                }
            }
        });

        login = (Button)findViewById(R.id.loginbutton);
        register = (Button)findViewById(R.id.registerbutton);

        username = (EditText)findViewById(R.id.editTextTextEmailAddress);
        password = (EditText)findViewById(R.id.editTextTextPassword);

        tx1 = (TextView)findViewById(R.id.textView5);
        tx1.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){

                    //Correct password function
                    Toast.makeText(getApplicationContext(),
                            "Loading", Toast.LENGTH_SHORT).show();
                }else{
                    //Wrong password function
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setTextColor(Color.RED);
                    counter--;
                    tx1.setText(getString(R.string.attempts)+Integer.toString(counter));
                    if(counter==0){
                        login.setEnabled(false);
                        //disable button function or warning
                    }
                }
            }
        });
        
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
