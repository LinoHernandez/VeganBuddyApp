package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.veganbuddyapp.models.Card;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class PaymentPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    //-----------
    EditText cardNumber, expiry, cvv;
    Button saveCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        mAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navView);

        cardNumber = findViewById(R.id.cardNumber);
        expiry = findViewById(R.id.expiry);
        cvv =  findViewById(R.id.cvv);
        saveCard = findViewById(R.id.saveCard);

        //Saving Card Details
        saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("Cards");

                Card card = new Card(String.valueOf(cardNumber.getText()),String.valueOf(expiry.getText()),String.valueOf(cvv.getText()));

                databaseReference.setValue(card);
                Toast.makeText(getApplicationContext(),"Card Added Sucessfully",Toast.LENGTH_SHORT).show();
            }
        });

        // drawer layout instance to toggle the menu icon to open        // drawer and back button to close drawer
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
                        Intent intent = new Intent(PaymentPage.this,ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        Intent register = new Intent(PaymentPage.this, LoginPage.class);
                        startActivity(register);
                        return true;
                    case R.id.nav_payment:
                        Intent payment = new Intent(PaymentPage.this,PaymentPage.class);
                        startActivity(payment);
                        return true;
                    case R.id.nav_menu:
                        Intent menu = new Intent(PaymentPage.this,MainActivity.class);
                        startActivity(menu);
                        return true;
                    default:
                        return true;
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
