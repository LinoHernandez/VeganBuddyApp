package com.example.veganbuddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterUser extends AppCompatActivity {
    EditText registerName, registerPassword, registerPhone, registerEmail;
    Button registerUser,registerbutton,login;
    private FirebaseAuth mAuth;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerEmail = findViewById(R.id.registerEmail);
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerPhone = findViewById(R.id.registerPhone);
        registerUser = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

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
                        Intent intent = new Intent(RegisterUser.this,ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        Intent register = new Intent(RegisterUser.this, LoginPage.class);
                        startActivity(register);
                        return true;
                    case R.id.nav_payment:
                        Intent payment = new Intent(RegisterUser.this,PaymentPage.class);
                        startActivity(payment);
                        return true;
                    case R.id.nav_menu:
                        Intent menu = new Intent(RegisterUser.this,MainActivity.class);
                        startActivity(menu);
                        return true;
                    default:
                        return true;
                }
            }
        });

        registerUser = (Button)findViewById(R.id.registerbutton);

        registerEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        registerPassword = (EditText)findViewById(R.id.editTextTextPassword);

        registerEmail = findViewById(R.id.editTextTextEmailAddress);


        registerbutton.setOnClickListener(view ->{
            createUser();
        });

        registerbutton.setOnClickListener(view ->{
            startActivity(new Intent(RegisterUser.this, MainActivity.class));
        });


    }

    private void createUser(){
        String Email = registerEmail.getText().toString();
        String Password = registerPassword.getText().toString();
        String Name = registerName.getText().toString();
        String PhoneNumber = registerPhone.getText().toString();

        if(TextUtils.isEmpty(Email)){
            registerEmail.setError("Please add an email address");
            registerEmail.requestFocus();
        }else if (TextUtils.isEmpty(Password)){
            registerPassword.setError("A password is required");
            registerPassword.requestFocus();
        }else if (TextUtils.isEmpty(Name)){
            registerName.setError("Please introduce your name");
            registerName.requestFocus();
        }else if (TextUtils.isEmpty(PhoneNumber)){
            registerPhone.setError("Please add your phone number");
            registerPhone.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterUser.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterUser.this,"Not able to register user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //@Override
    //public void onStart() {
    //super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    //FirebaseUser user = mAuth.getCurrentUser();
    //if (user == null){
    //startActivity(new Intent(MainActivity.this, RegisterPage.class));
    //}
    //updateUI(currentUser);
    //}


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
