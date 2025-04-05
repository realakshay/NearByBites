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
        allRestaurants.add(new Restaurant(1, "McDonald's", "123 Main St", "Fast Food, American Food, Pasta", 4.2f, 20, 1.2f, R.drawable.restaurant_1, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(2, "Pizza Hut", "456 Main St", "Pizza, Italian Food, Pasta", 4.5f, 30, 2.5f, R.drawable.restaurant_2, "Pizza", "$$"));
        allRestaurants.add(new Restaurant(3, "Jollibee", "789 Main St", "Fast Food, Filipino Food, Pasta", 4.7f, 25, 1.5f, R.drawable.restaurant_3, "Chicken", "$"));
        allRestaurants.add(new Restaurant(4, "KFC", "101 Main St", "Fast Food, American Food, Chicken", 4.0f, 15, 0.8f, R.drawable.restaurant_4, "Chicken", "$"));
        allRestaurants.add(new Restaurant(5, "Burger King", "202 Main St", "Fast Food, Burgers", 3.8f, 25, 1.0f, R.drawable.restaurant_1, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(6, "Domino's Pizza", "303 Main St", "Pizza, Italian Food", 4.3f, 35, 3.0f, R.drawable.restaurant_2, "Pizza", "$$"));
        allRestaurants.add(new Restaurant(7, "Taco Bell", "404 Main St", "Fast Food, Mexican Food", 3.9f, 20, 1.8f, R.drawable.restaurant_3, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(8, "Subway", "505 Main St", "Sandwiches, Healthy Food", 4.1f, 15, 0.5f, R.drawable.restaurant_4, "Fast Food", "$"));
    }
    
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
    public void onRestaurantClicked(Restaurant restaurant) {
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
