package com.example.veganbuddyapp.models;

public class DataModal {

    private String name;
        private String price;
        private String imgUrl;

        public DataModal() {
            //empty constructor required for firebase.
        }

        //constructor for our object class.
        public DataModal(String name, String price, String imgUrl) {
            this.name = name;
            this.imgUrl = imgUrl;
            this.price = price;
        }
        //getter and setter methods
        public String getName(){
            return name;

        }
        public void setName(String name){
            this.name = name;
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price){
            this.price = price;
        }
        public String getImgUrl(){
            return imgUrl;
        }
        public void setImgUrl(){
            this.imgUrl = imgUrl;
        }
    }


