package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.FilteredRestaurantAdapter;
import com.foodapp.models.Restaurant;
import com.foodapp.utils.FilterManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RestaurantsListActivity extends AppCompatActivity implements FilteredRestaurantAdapter.RestaurantClickListener {

    private ImageView ivBack;
    private ImageView ivFilter;
    private TextView tvTitle;
    private EditText etSearch;
    private RecyclerView rvRestaurants;
    private TextView tvNoRestaurants;
    
    private FilterManager filterManager;
    private List<Restaurant> allRestaurants;
    private List<Restaurant> filteredRestaurants;
    private FilteredRestaurantAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
        
        // Initialize FilterManager
        filterManager = FilterManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Update title if coming from category
        updateTitle();
        
        // Set up click listeners
        setupClickListeners();
        
        // Set up search functionality
        setupSearch();
        
        // Load restaurant data
        loadRestaurants();
        
        // Apply filters and display restaurants
        applyFiltersAndUpdateUI();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh filters when returning to this screen
        applyFiltersAndUpdateUI();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        ivFilter = findViewById(R.id.ivFilter);
        tvTitle = findViewById(R.id.tvTitle);
        etSearch = findViewById(R.id.etSearch);
        rvRestaurants = findViewById(R.id.rvRestaurants);
        tvNoRestaurants = findViewById(R.id.tvNoRestaurants);
        
        // Set up RecyclerView
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void updateTitle() {
        // Check if we have a category name in the intent
        String categoryName = getIntent().getStringExtra("category_name");
        if (categoryName != null && !categoryName.isEmpty()) {
            tvTitle.setText(categoryName.toUpperCase());
        }
    }
    
    private void setupClickListeners() {
        // Back button
        ivBack.setOnClickListener(v -> finish());
        
        // Filter button
        ivFilter.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantsListActivity.this, SetFilterActivity.class);
            startActivity(intent);
        });
    }
    
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter restaurants based on search query
                filterRestaurants(s.toString());
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }
    
    private void loadRestaurants() {
        // In a real app, this would come from an API or database
        allRestaurants = new ArrayList<>();
        
        // Add some restaurants with different categories
        allRestaurants.add(new Restaurant(1, "McDonald's", "123 Main St", "Fast Food, Burger, Chicken", 4.2f, 20, 1.2f, R.drawable.restaurant_1, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(2, "Pizza Hut", "456 Main St", "Pizza, Italian", 4.5f, 30, 2.5f, R.drawable.restaurant_2, "Pizza", "$$"));
        allRestaurants.add(new Restaurant(3, "Papa John's", "789 Elm St", "Pizza, Italian", 4.6f, 25, 1.5f, R.drawable.restaurant_3, "Pizza", "$$"));
        allRestaurants.add(new Restaurant(4, "KFC", "101 Oak St", "Fast Food, Chicken", 4.0f, 15, 0.8f, R.drawable.restaurant_4, "Chicken", "$"));
        allRestaurants.add(new Restaurant(5, "Subway", "202 Pine St", "Sandwiches, Healthy", 4.3f, 10, 1.7f, R.drawable.restaurant_1, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(6, "Taco Bell", "303 Maple St", "Mexican, Fast Food", 3.9f, 25, 3.2f, R.drawable.restaurant_2, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(7, "Outback Steakhouse", "404 Birch St", "Steak, American", 4.7f, 40, 4.5f, R.drawable.restaurant_3, "Steak", "$$$"));
        allRestaurants.add(new Restaurant(8, "Jollibee", "505 Cedar St", "Filipino, Chicken, Fast Food", 4.6f, 20, 2.1f, R.drawable.restaurant_4, "Chicken", "$"));
        allRestaurants.add(new Restaurant(9, "Dunkin' Donuts", "606 Walnut St", "Donuts, Coffee, Breakfast", 4.4f, 10, 1.3f, R.drawable.restaurant_1, "Donut", "$"));
        allRestaurants.add(new Restaurant(10, "Starbucks", "707 Maple St", "Coffee, Breakfast", 4.5f, 5, 0.5f, R.drawable.restaurant_2, "Coffee", "$$"));
        allRestaurants.add(new Restaurant(11, "Nando's", "808 Oak St", "Chicken, Portuguese", 4.8f, 30, 3.7f, R.drawable.restaurant_3, "Chicken", "$$"));
        allRestaurants.add(new Restaurant(12, "Chipotle", "909 Pine St", "Mexican, Healthy", 4.5f, 15, 1.9f, R.drawable.restaurant_4, "Mexican", "$$"));
        allRestaurants.add(new Restaurant(13, "Olive Garden", "111 Main St", "Italian, Pasta", 4.3f, 35, 4.2f, R.drawable.restaurant_1, "Italian", "$$$"));
        allRestaurants.add(new Restaurant(14, "Burger King", "222 Elm St", "Fast Food, Burger", 3.8f, 15, 1.4f, R.drawable.restaurant_2, "Fast Food", "$"));
        allRestaurants.add(new Restaurant(15, "Red Lobster", "333 Oak St", "Seafood, American", 4.6f, 45, 5.3f, R.drawable.restaurant_3, "Seafood", "$$$$"));
        allRestaurants.add(new Restaurant(16, "Asian Rice Bowl", "444 Pine St", "Asian, Rice", 4.5f, 20, 2.0f, R.drawable.restaurant_4, "Rice", "$$"));
        allRestaurants.add(new Restaurant(17, "Rice Palace", "555 Oak St", "Asian, Rice, Thai", 4.7f, 25, 2.3f, R.drawable.restaurant_1, "Rice", "$$"));
        allRestaurants.add(new Restaurant(18, "Kebab King", "666 Elm St", "Middle Eastern, Kebab", 4.3f, 15, 1.8f, R.drawable.restaurant_2, "Kebab", "$"));
        
        // Create filtered list initially with all restaurants
        filteredRestaurants = new ArrayList<>(allRestaurants);
        
        // Set up adapter
        adapter = new FilteredRestaurantAdapter(this, filteredRestaurants, this);
        rvRestaurants.setAdapter(adapter);
    }
    
    private void applyFiltersAndUpdateUI() {
        // Apply filters if they are active
        if (filterManager.isFilterActive()) {
            applyFilters();
        } else {
            // If filters are not active, show all restaurants
            filteredRestaurants = new ArrayList<>(allRestaurants);
            adapter.updateRestaurants(filteredRestaurants);
        }
        
        // Apply any current search query
        if (etSearch.getText().length() > 0) {
            filterRestaurants(etSearch.getText().toString());
        }
        
        // Show empty state if no restaurants
        updateEmptyState();
    }
    
    private void applyFilters() {
        filteredRestaurants = new ArrayList<>();
        
        // Get filter settings
        Set<String> categories = filterManager.getSelectedCategories();
        String sortOption = filterManager.getSelectedSort();
        Set<String> prices = filterManager.getSelectedPrices();
        
        // Apply category filter
        for (Restaurant restaurant : allRestaurants) {
            boolean categoryMatch = categories.contains("All") || 
                    categories.contains(restaurant.getCategory());
            
            boolean priceMatch = prices.isEmpty() || 
                    prices.contains(restaurant.getPriceRange());
            
            if (categoryMatch && priceMatch) {
                filteredRestaurants.add(restaurant);
            }
        }
        
        // Apply sort
        if (!sortOption.isEmpty()) {
            sortRestaurants(sortOption);
        }
        
        // Update adapter
        adapter.updateRestaurants(filteredRestaurants);
    }
    
    private void sortRestaurants(String sortOption) {
        switch (sortOption) {
            case "Fast Delivery":
                filteredRestaurants.sort((r1, r2) -> 
                        Integer.compare(r1.getDeliveryTime(), r2.getDeliveryTime()));
                break;
            case "Near You":
                filteredRestaurants.sort((r1, r2) -> 
                        Float.compare(r1.getDistance(), r2.getDistance()));
                break;
            case "Popular":
                filteredRestaurants.sort((r1, r2) -> 
                        Float.compare(r2.getRating(), r1.getRating()));
                break;
            case "Trending":
                // For demo, just do the same as popular
                filteredRestaurants.sort((r1, r2) -> 
                        Float.compare(r2.getRating(), r1.getRating()));
                break;
        }
    }
    
    private void filterRestaurants(String query) {
        if (query.isEmpty()) {
            // If search query is empty, apply only category/price filters
            applyFiltersAndUpdateUI();
            return;
        }
        
        // Start with the currently filtered list (which may already have category/price filters applied)
        List<Restaurant> searchResults = new ArrayList<>();
        
        // Apply search filter
        String lowerQuery = query.toLowerCase(Locale.getDefault());
        for (Restaurant restaurant : filteredRestaurants) {
            if (restaurant.getName().toLowerCase(Locale.getDefault()).contains(lowerQuery) ||
                    restaurant.getCuisine().toLowerCase(Locale.getDefault()).contains(lowerQuery)) {
                searchResults.add(restaurant);
            }
        }
        
        // Update adapter with search results
        adapter.updateRestaurants(searchResults);
        
        // Show empty state if no results
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (adapter.getItemCount() == 0) {
            tvNoRestaurants.setVisibility(View.VISIBLE);
            rvRestaurants.setVisibility(View.GONE);
        } else {
            tvNoRestaurants.setVisibility(View.GONE);
            rvRestaurants.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void onRestaurantClicked(Restaurant restaurant) {
        // Navigate to restaurant details
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
