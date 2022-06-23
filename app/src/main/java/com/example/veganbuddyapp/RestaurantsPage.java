package com.example.veganbuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantsPage extends AppCompatActivity {

    GridView gridView;

    String[] names = {"restaurants name"};
    int[] images = {R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_page);

        gridView = findViewById(R.id.restaurants_name_grid_view);

        CustomAdapter customAdapter = new CustomAdapter(names,images, this);

        gridView.setAdapter(customAdapter);
    }

    public class CustomAdapter extends BaseAdapter{
        private String[] restaurantsNames;
        private int[] restaurantPhotos;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] restaurantsNames, int[] restaurantPhotos, Context context) {
            this.restaurantsNames = restaurantsNames;
            this.restaurantPhotos = restaurantPhotos;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return restaurantPhotos.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


            if(view == null){
//                view = LayoutInflater.inflate(R.layout.restaurants_list, viewGroup, false);
                view = layoutInflater.inflate(R.layout.restaurants_list, viewGroup, false);

            }

            TextView restaurant_name = view.findViewById(R.id.restaurant_name);
            ImageView imageView =view.findViewById(R.id.restaurant_image);

            restaurant_name.setText(restaurantsNames[i]);
            imageView.setImageResource(restaurantPhotos[i]);


            return view;
        }
    }
}