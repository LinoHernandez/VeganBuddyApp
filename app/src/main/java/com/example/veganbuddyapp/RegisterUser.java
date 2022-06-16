package com.example.veganbuddyapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RegisterUser extends AppCompatActivity {
    EditText registerName, registerPassword, registerPhone, registerEmail;
    Button login,registerButton;
    ImageView setPicture;
    Uri imagepath;
    String ImageUriAcessToken;
    private static int PICK_IMAGE=123;
    private FirebaseAuth mAuth;
    StorageReference storageReference;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerEmail = findViewById(R.id.registerEmail);
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerPhone = findViewById(R.id.registerPhone);
        registerButton = findViewById(R.id.registerButton);
        login = findViewById(R.id.login);
        setPicture = findViewById(R.id.setPicture);
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

        //Uploading Picture

        ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(new
                        ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getResultCode() == PICK_IMAGE) {
                        Intent data = result.getData();
                        imagepath = data.getData();
                        setPicture.setImageURI(imagepath);
                    }
                });


        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });



        //Registering User
        registerButton.setOnClickListener(view ->{
            createUser();
        });

        login.setOnClickListener(view ->{
            startActivity(new Intent(RegisterUser.this, LoginPage.class));
        });


    }
    //Function to create User
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
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
                        User myuserProfile=new User(mAuth.getUid(),String.valueOf(registerName.getText()),String.valueOf(registerEmail.getText()),String.valueOf(registerPhone.getText()));
                        databaseReference.setValue(myuserProfile);
                        Toast.makeText(RegisterUser.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                        uploadImage();
                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterUser.this,"Not able to register user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void uploadImage(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(mAuth.getUid()).child("ProfilePic");


        //Image compresesion

        Bitmap bitmap=null;
        try {
            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,25,byteArrayOutputStream);
        byte[] data=byteArrayOutputStream.toByteArray();

        ///putting image to storage

        UploadTask uploadTask=storageReference.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageUriAcessToken=uri.toString();
                        Toast.makeText(getApplicationContext(),"URI get success",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"URI get Failed",Toast.LENGTH_SHORT).show();
                    }


                });
                Toast.makeText(getApplicationContext(),"Image is uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Image Not UPdloaded",Toast.LENGTH_SHORT).show();
            }
        });
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
