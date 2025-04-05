package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapter.RestaurantAdapter;
import com.foodapp.api.ApiClient;
import com.foodapp.api.FoodService;
import com.foodapp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListActivity extends AppCompatActivity implements RestaurantAdapter.RestaurantClickListener {

    private RecyclerView recyclerViewRestaurants;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> restaurantList;
    private List<Restaurant> filteredList;
    private ProgressBar progressBar;
    private TextView tvError;
    private EditText etSearch;
    private FoodService foodService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Initialize views
        recyclerViewRestaurants = findViewById(R.id.recyclerViewRestaurants);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        etSearch = findViewById(R.id.etSearch);

        // Setup RecyclerView
        recyclerViewRestaurants.setLayoutManager(new LinearLayoutManager(this));
        restaurantList = new ArrayList<>();
        filteredList = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(filteredList, this);
        recyclerViewRestaurants.setAdapter(restaurantAdapter);

        // Initialize API client
        foodService = ApiClient.getClient().create(FoodService.class);

        // Setup search functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter restaurants based on search query
                filterRestaurants(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        // Load restaurants
        loadRestaurants();
    }

    private void loadRestaurants() {
        showLoading();
        
        // Use dummy data instead of making API calls
        // In a real app, this would be replaced with actual API calls
        addMockRestaurants();
        
        // Simulate network delay for better UX testing
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
            }
        }, 1000); // 1 second delay
    }

    private void addMockRestaurants() {
        // Add dummy restaurants to the list
        Restaurant r1 = new Restaurant(1, "Italian Delight", "Italian", "123 Main St, New York, NY", 4.5f, 15, 25, true);
        Restaurant r2 = new Restaurant(2, "Spice Garden", "Indian", "456 Oak Ave, New York, NY", 4.2f, 20, 35, true);
        Restaurant r3 = new Restaurant(3, "Sushi World", "Japanese", "789 Pine Blvd, New York, NY", 4.7f, 25, 40, false);
        Restaurant r4 = new Restaurant(4, "Taco Heaven", "Mexican", "101 Elm St, New York, NY", 4.0f, 10, 20, true);
        Restaurant r5 = new Restaurant(5, "Burger Joint", "American", "202 Maple Dr, New York, NY", 4.3f, 12, 25, true);
        Restaurant r6 = new Restaurant(6, "Noodle House", "Chinese", "303 Cherry Ln, New York, NY", 4.1f, 15, 30, true);
        Restaurant r7 = new Restaurant(7, "Mediterranean Palace", "Mediterranean", "404 Olive Rd, New York, NY", 4.6f, 20, 30, true);
        Restaurant r8 = new Restaurant(8, "Seoul Kitchen", "Korean", "505 Peach St, New York, NY", 4.4f, 25, 35, true);
        Restaurant r9 = new Restaurant(9, "Thai Spice", "Thai", "606 Walnut Ave, New York, NY", 4.2f, 15, 25, false);
        Restaurant r10 = new Restaurant(10, "Falafel King", "Middle Eastern", "707 Pineapple Blvd, New York, NY", 4.3f, 20, 30, true);
        
        restaurantList.add(r1);
        restaurantList.add(r2);
        restaurantList.add(r3);
        restaurantList.add(r4);
        restaurantList.add(r5);
        restaurantList.add(r6);
        restaurantList.add(r7);
        restaurantList.add(r8);
        restaurantList.add(r9);
        restaurantList.add(r10);
        
        filteredList.clear();
        filteredList.addAll(restaurantList);
        restaurantAdapter.notifyDataSetChanged();
    }

    private void filterRestaurants(String query) {
        filteredList.clear();
        
        if (query.isEmpty()) {
            filteredList.addAll(restaurantList);
        } else {
            query = query.toLowerCase();
            for (Restaurant restaurant : restaurantList) {
                if (restaurant.getName().toLowerCase().contains(query) || 
                    restaurant.getCuisine().toLowerCase().contains(query)) {
                    filteredList.add(restaurant);
                }
            }
        }
        
        restaurantAdapter.notifyDataSetChanged();
        
        if (filteredList.isEmpty()) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("No restaurants match your search");
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }
}
