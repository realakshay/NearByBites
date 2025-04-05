package com.foodapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        
        // Initialize views and load restaurant data
        // This is a placeholder - we'll implement the full restaurant list later
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Nearby Restaurants");
    }
}