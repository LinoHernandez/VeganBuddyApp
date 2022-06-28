package com.example.veganbuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class RestaurantsPage extends AppCompatActivity {

    private GridView resList;
    private static final String API_KEY = "AIzaSyAvhvD5YBxPNO2L4bsN745AF8Bi8fpze7w";

    private static final String PLACES_API_BASE =
            "https://maps.googleapis.com/maps/api/place";

    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/nearbysearch";
    private static final String OUT_JSON = "/json?";
    private static final String LOG_TAG = "ListRest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_page);

//        gridView = findViewById(R.id.gridView);
//
//        CustomAdapter customAdapter = new CustomAdapter(names, images, this);
//
//        gridView.setAdapter(customAdapter);
    }

//    public class CustomAdapter extends BaseAdapter {
//        private String[] restaurantsNames;
//        private int[] restaurantPhotos;
//        private Context context;
//        private LayoutInflater layoutInflater;
//
//        public CustomAdapter(String[] restaurantsNames, int[] restaurantPhotos, Context context) {
//            this.restaurantsNames = restaurantsNames;
//            this.restaurantPhotos = restaurantPhotos;
//            this.context = context;
//            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getCount() {
//            return restaurantPhotos.length;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//
//
//            if (view == null) {
////                view = LayoutInflater.inflate(R.layout.restaurants_list, viewGroup, false);
//                view = layoutInflater.inflate(R.layout.restaurants_list, viewGroup, false);
//
//            }
//
//            TextView restaurant_name = view.findViewById(R.id.restaurant_name);
//            ImageView imageView = view.findViewById(R.id.restaurant_image);
//
//            restaurant_name.setText(restaurantsNames[i]);
//            imageView.setImageResource(restaurantPhotos[i]);
//
//
//            return view;
//            StrictMode.ThreadPolicy policy = new
//                    StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            //Double lng = Double.parseDouble(longitude);
//            // Double lat = Double.parseDouble(latitude);
//            int radius = 1000;
//
//            //ArrayList<Place> list = search(lat,lng,radius);
//
//            //if (list != null)
//            {
//                resList = (GridView) findViewById(R.id.resListView);
//                //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
//                //resList.setAdapter(adapter);
//            }
//        }
//    }
}