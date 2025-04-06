package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.RestaurantAdapter;
import com.foodapp.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements RestaurantAdapter.RestaurantClickListener {

    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvSearchResults;
    private ProgressBar progressBar;
    private TextView tvNoResults;
    
    private String searchQuery;
    private List<Restaurant> allRestaurants;
    private List<Restaurant> filteredRestaurants;
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        
        // Get search query from intent
        searchQuery = getIntent().getStringExtra("search_query");
        
        // Initialize views
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        rvSearchResults = findViewById(R.id.rvSearchResults);
        progressBar = findViewById(R.id.progressBar);
        tvNoResults = findViewById(R.id.tvNoResults);
        
        // Set title
        tvTitle.setText("Search Results");
        
        // Set back button click listener
        ivBack.setOnClickListener(v -> onBackPressed());
        
        // Set up RecyclerView
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        
        // Show loading state
        rvSearchResults.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        
        // Load restaurants and perform search after a delay (to show loading animation)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Load all restaurants
            loadRestaurants();
            
            // Filter restaurants based on search query
            filterRestaurants();
            
            // Update UI
            progressBar.setVisibility(View.GONE);
            
            if (filteredRestaurants.isEmpty()) {
                tvNoResults.setVisibility(View.VISIBLE);
                rvSearchResults.setVisibility(View.GONE);
            } else {
                tvNoResults.setVisibility(View.GONE);
                rvSearchResults.setVisibility(View.VISIBLE);
                
                // Set up adapter with filtered results
                adapter = new RestaurantAdapter(this, filteredRestaurants, this);
                rvSearchResults.setAdapter(adapter);
            }
        }, 1500);
    }
    
    private void loadRestaurants() {
        // Initialize restaurants (in a real app, this would come from a database or API)
        allRestaurants = new ArrayList<>();
        allRestaurants.add(new Restaurant(1, "McDonald's", "123 Main St", "Fast Food, Burger, Chicken", "4.2", "20", "1.2", R.drawable.restaurant_1, "$"));
        allRestaurants.add(new Restaurant(2, "Pizza Hut", "456 Main St", "Pizza, Italian", "4.5", "30", "2.5", R.drawable.restaurant_2,  "$$"));
        allRestaurants.add(new Restaurant(3, "Papa John's", "789 Elm St", "Pizza, Italian", "4.6", "25", "1.5", R.drawable.restaurant_3,  "$$"));
        allRestaurants.add(new Restaurant(4, "KFC", "101 Oak St", "Fast Food, Chicken", "4.0", "15", "0.8", R.drawable.restaurant_4,  "$"));
        allRestaurants.add(new Restaurant(5, "Subway", "202 Pine St", "Sandwiches, Healthy", "4.0", "15", "0.8", R.drawable.restaurant_1,  "$"));
        allRestaurants.add(new Restaurant(6, "Taco Bell", "303 Maple St", "Mexican, Fast Food", "4.7", "5", "1.8", R.drawable.restaurant_2, "$"));
        allRestaurants.add(new Restaurant(7, "Outback Steakhouse", "404 Birch St", "Steak, American", "4.7", "5", "1.8", R.drawable.restaurant_3, "$$$"));
        allRestaurants.add(new Restaurant(8, "Jollibee", "505 Cedar St", "Filipino, Chicken, Fast Food","4.0", "15", "0.8", R.drawable.restaurant_4,  "$"));
        allRestaurants.add(new Restaurant(9, "Dunkin' Donuts", "606 Walnut St", "Donuts, Coffee, Breakfast", "4.7", "5", "1.8", R.drawable.restaurant_1,  "$"));
        allRestaurants.add(new Restaurant(10, "Starbucks", "707 Maple St", "Coffee, Breakfast", "4.0", "15", "0.8", R.drawable.restaurant_2, "$$"));

    };
    
    private void filterRestaurants() {
        filteredRestaurants = new ArrayList<>();
        
        if (searchQuery == null || searchQuery.isEmpty()) {
            // If no search query, return all restaurants
            filteredRestaurants.addAll(allRestaurants);
            return;
        }
        
        // Convert search query to lowercase for case-insensitive search
        String query = searchQuery.toLowerCase();
        
        // Filter restaurants by name, cuisine, or category
        for (Restaurant restaurant : allRestaurants) {
            if (restaurant.getName().toLowerCase().contains(query) ||
                    restaurant.getCuisine().toLowerCase().contains(query) ||
                    restaurant.getCategory().toLowerCase().contains(query)) {
                filteredRestaurants.add(restaurant);
            }
        }
    }
    
    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        // Navigate to RestaurantDetailsActivity
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra("restaurant_id", restaurant.getId());
        intent.putExtra("restaurant_name", restaurant.getName());
        intent.putExtra("restaurant_image", restaurant.getImageResourceId());
        intent.putExtra("restaurant_rating", restaurant.getRating());
        intent.putExtra("restaurant_cuisine", restaurant.getCuisine());
        intent.putExtra("restaurant_delivery_time", restaurant.getDeliveryTime());
        intent.putExtra("restaurant_distance", restaurant.getDistance());
        startActivity(intent);
    }
}
