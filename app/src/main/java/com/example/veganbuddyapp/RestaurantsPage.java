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

    private GridView resList;
    private static final String API_KEY = "AIzaSyAvhvD5YBxPNO2L4bsN745AF8Bi8fpze7w";

    String[] names = {"restaurants name"};
    int[] images = {R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_page);

        gridView = findViewById(R.id.restaurants_name_grid_view);

        CustomAdapter customAdapter = new CustomAdapter(names,images, context: this);

        ArrayList<Place> list = search(lat, lng, radius);

        if (list != null)
        {
            resList = (GridView) findViewById(R.id.resListView);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
            resList.setAdapter(adapter);
        }
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

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            // Extract the descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                place.name = predsJsonArray.getJSONObject(i).getString("name");
                resultList.add(place);
            }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON results", e);
            }
            return resultList;
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
                view = LayoutInflater.inflate(R.layout.restaurants_list, viewGroup, attachToRoot: false);

            }

            TextView restaurant_name = view.findViewById(R.id.restaurant_name);
            ImageView imageView =view.findViewById(R.id.restaurant_image);

            restaurant_name.setText(restaurantsNames[i]);
            imageView.setImageResource(restaurantPhotos[i]);


            return view;
        }
    }
}