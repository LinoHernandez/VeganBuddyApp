package com.example.veganbuddyapp;

public class User {
    String uID,name,email,phoneNumber;

    public User(String uID, String name, String email, String phoneNumber) {
        this.uID = uID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
