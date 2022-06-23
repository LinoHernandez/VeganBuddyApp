package com.example.veganbuddyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import com.example.veganbuddyapp.models.DataModal;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<DataModal> {
    // constructor for our list view adapter.
    public Adapter(@NonNull Context context, ArrayList<DataModal> dataModalArrayList) {
    super(context,0, (List<DataModal>) dataModalArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){


        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_menu_grid, parent, false);
        }
        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        DataModal dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView itemIV = listitemView.findViewById(R.id.idIVimage);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        nameTV.setText(dataModal.getName());

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.get().load(dataModal.getImgUrl()).into(itemIV);

        return listitemView;
    }


}
