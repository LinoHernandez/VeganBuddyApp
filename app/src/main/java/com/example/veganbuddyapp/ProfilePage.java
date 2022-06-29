package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.Objects;

public class ProfilePage extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    private StorageReference storageReference;

    //profile page objects
    EditText nameProfile, emailProfile, phoneProfile;
    Button editProfile;
    ImageView setPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        mAuth = FirebaseAuth.getInstance();

        nameProfile = findViewById(R.id.nameProfile);
        emailProfile = findViewById(R.id.emailProfile);
        phoneProfile = findViewById(R.id.phoneProfile);
        editProfile = findViewById(R.id.editProfile);

        navigationView = findViewById(R.id.navView);
        setPicture = findViewById(R.id.setPicture);

        storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(mAuth.getUid()).child("ProfilePic");
//        storageReference.getDownloadUrl().addOnSuccessListener(uri -> setPicture.setImageURI(uri));

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                    setPicture.setImageBitmap(bitmap);
//                    imageuriaccesstoken =uri.toString();
                    Picasso.get().load(uri).into(setPicture);
                    Toast.makeText(getApplicationContext(),"Fetched Image"+e+"",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Unable to Fetch Image"+e+"",Toast.LENGTH_SHORT).show();
            }
        });


        //Showing data in profile page
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                nameProfile.setText(userprofile.getName());
                emailProfile.setText(userprofile.getEmail());
                phoneProfile.setText(userprofile.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Updating User profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
                User user = new User(mAuth.getUid(), String.valueOf(nameProfile.getText()),String.valueOf(emailProfile.getText()),String.valueOf(phoneProfile.getText()));
                databaseReference.setValue(user);
                Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(ProfilePage.this,ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        Intent register = new Intent(ProfilePage.this, LoginPage.class);
                        startActivity(register);
                        return true;
                    case R.id.nav_payment:
                        Intent payment = new Intent(ProfilePage.this,PaymentPage.class);
                        startActivity(payment);
                        return true;
                    case R.id.nav_menu:
                        Intent menu = new Intent(ProfilePage.this,MainActivity.class);
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
