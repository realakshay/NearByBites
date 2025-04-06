package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.foodapp.models.Restaurant;

public class RestaurantDetailActivity extends AppCompatActivity {

    private ImageView ivRestaurantImage;
    private TextView tvRestaurantName;
    private TextView tvCuisine;
    private TextView tvRating;
    private TextView tvDeliveryTime;
    private TextView tvAddress;
    private Button btnViewMenu;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Initialize views
        ivRestaurantImage = findViewById(R.id.ivRestaurantImage);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvCuisine = findViewById(R.id.tvCuisine);
        tvRating = findViewById(R.id.tvRating);
        tvDeliveryTime = findViewById(R.id.tvDeliveryTime);
        tvAddress = findViewById(R.id.tvAddress);
        btnViewMenu = findViewById(R.id.btnViewMenu);

        // Get restaurant data from intent
        if (getIntent().hasExtra("restaurant")) {
            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
            displayRestaurantDetails();
        } else {
            Toast.makeText(this, "Error: Restaurant details not available", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set up button click listener
        btnViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailActivity.this, MenuActivity.class);
                intent.putExtra("restaurant_id", restaurant.getId());
                intent.putExtra("restaurant_name", restaurant.getName());
                startActivity(intent);
            }
        });
    }

    private void displayRestaurantDetails() {
        tvRestaurantName.setText(restaurant.getName());
        tvCuisine.setText(restaurant.getCuisine());
        tvRating.setText(String.format("%.1f★", restaurant.getRating()));
        tvDeliveryTime.setText(restaurant.getDeliveryTimeMinutes());
        tvAddress.setText(restaurant.getAddress());

        // Load image using Glide
        // Since we're not having real image URLs, we'll use a placeholder
        Glide.with(this)
                .load("https://via.placeholder.com/300x200?text=" + restaurant.getName())
                .placeholder(R.drawable.placeholder_restaurant)
                .error(R.drawable.placeholder_restaurant)
                .into(ivRestaurantImage);
    }
}
