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

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    Button login,register;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navView);

        login.setOnClickListener(view -> {
            loginUser();
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
                        Intent intent = new Intent(LoginPage.this,ProfilePage.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        Intent register = new Intent(LoginPage.this, LoginPage.class);
                        startActivity(register);
                        return true;
                    case R.id.nav_payment:
                        Intent payment = new Intent(LoginPage.this,PaymentPage.class);
                        startActivity(payment);
                        return true;
                    case R.id.nav_menu:
                        Intent menu = new Intent(LoginPage.this,MainActivity.class);
                        startActivity(menu);
                        return true;
                    default:
                        return true;
                }
            }
        });


        register.setOnClickListener(view ->{
            startActivity(new Intent(LoginPage.this, RegisterUser.class));
        });

        
    }

    private void loginUser(){
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(TextUtils.isEmpty(Email)){
            email.setError("Please add an email address");
            email.requestFocus();
        }else if (TextUtils.isEmpty(Password)){
            password.setError("A password is required");
            password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginPage.this,"User logged successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPage.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginPage.this,"Log in Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

//   @Override
//    public void onStart() {
//        super.onStart();
//         Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user == null){
//            startActivity(new Intent(MainActivity.this, RegisterPage.class));
//        }
//        updateUI(currentUser);
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
