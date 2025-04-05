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
        
        // Make API call to get restaurants
        Call<List<Restaurant>> call = foodService.getRestaurants();
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    restaurantList.clear();
                    restaurantList.addAll(response.body());
                    filteredList.clear();
                    filteredList.addAll(restaurantList);
                    restaurantAdapter.notifyDataSetChanged();
                    
                    if (restaurantList.isEmpty()) {
                        showError("No restaurants found");
                    }
                } else {
                    showError("Failed to load restaurants");
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                hideLoading();
                showError("Network error: " + t.getMessage());
                
                // For demo purposes, add mock data when API fails
                addMockRestaurants();
            }
        });
    }

    private void addMockRestaurants() {
        // This is only used when the API fails and is for demonstration purposes
        // In a real app, you would handle the error more gracefully
        Restaurant r1 = new Restaurant(1, "Italian Delight", "Italian", "123 Main St", 4.5f, 15, 25, true);
        Restaurant r2 = new Restaurant(2, "Spice Garden", "Indian", "456 Oak Ave", 4.2f, 20, 35, true);
        Restaurant r3 = new Restaurant(3, "Sushi World", "Japanese", "789 Pine Blvd", 4.7f, 25, 40, false);
        Restaurant r4 = new Restaurant(4, "Taco Heaven", "Mexican", "101 Elm St", 4.0f, 10, 20, true);
        Restaurant r5 = new Restaurant(5, "Burger Joint", "American", "202 Maple Dr", 4.3f, 12, 25, true);
        
        restaurantList.add(r1);
        restaurantList.add(r2);
        restaurantList.add(r3);
        restaurantList.add(r4);
        restaurantList.add(r5);
        
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
