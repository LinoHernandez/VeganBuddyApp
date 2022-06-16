package com.example.veganbuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
=======
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
>>>>>>> parent of 1acae23 (database)

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {
    EditText registerName, registerPassword, registerPhone, registerEmail;
    Button registerUser;

    String str_name,str_pass,str_mail,str_phone;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerEmail = findViewById(R.id.registerEmail);
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerPhone = findViewById(R.id.registerPhone);
        registerUser = findViewById(R.id.registerUser);

        // Progress

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }
    private void Validation() {
    str_name = registerName.getText().toString();
    str_mail = registerEmail.getText().toString();
    str_pass = registerPassword.getText().toString();
    str_phone = registerPhone.getText().toString();

<<<<<<< HEAD
    if (str_name.isEmpty()) {
        registerName.setError("Please enter field");
        registerName.requestFocus();
        return;
=======
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
>>>>>>> parent of 1acae23 (database)
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(str_mail).matches()){
       registerEmail.setError("Please enter valid email");
       registerEmail.requestFocus();
       return;
    }
    if (str_pass.isEmpty()) {
        registerPassword.setError("Please enter field");
        registerPassword.requestFocus();
        return;
    }
    else if (!passwordValidation(str_pass)){
        registerPassword.setError("enter maximum 6 digits");
        registerPassword.requestFocus();
}
    if (!numberCheck(str_phone)) {
        registerPhone.setError("Invalid Mobile No.");
        registerPhone.requestFocus();
        return;
    }

    createAccount();
}

    private boolean passwordValidation(String str_pass) {
            Pattern p = Pattern.compile(".{6}");
            Matcher m = p.matcher(str_pass);
            return m.matches();
        }


    private boolean numberCheck(String str_phone) {

        Pattern p = Pattern.compile("[0,9][11]");
        Matcher m = p.matcher(str_phone);
        return m.matches();
    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account ");
        progressDialog.show();

        sendDataToDb();
    }

    private void sendDataToDb() {
        String regtime = "" + System.currentTimeMillis();
        HashMap<String, String> data = new HashMap<>();
        data.put("name", str_name);
        data.put("mail", str_mail);
        data.put("password", str_pass);
        data.put("phone", str_phone);

        //database

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(str_name).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                    }

                });
    }}












