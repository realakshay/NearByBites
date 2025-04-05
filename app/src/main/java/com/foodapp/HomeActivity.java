package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.CategoryAdapter;
import com.foodapp.adapters.RestaurantAdapter;
import com.foodapp.models.Category;
import com.foodapp.models.Restaurant;
import com.foodapp.utils.FilterManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements RestaurantAdapter.RestaurantClickListener, CategoryAdapter.CategoryClickListener {

    private ImageView ivFilter;
    private RecyclerView rvCategories;
    private RecyclerView rvRestaurants;
    private BottomNavigationView bottomNavigation;
    
    private List<Category> categories;
    private List<Restaurant> restaurants;
    private FilterManager filterManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        // Initialize FilterManager
        filterManager = FilterManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Set up bottom navigation
        setupBottomNavigation();
        
        // Load data
        loadCategories();
        loadRestaurants();
        
        // Set up filter icon click
        ivFilter.setOnClickListener(v -> {
            // Navigate to SetFilterActivity
            Intent intent = new Intent(HomeActivity.this, SetFilterActivity.class);
            startActivity(intent);
        });
    }
    
    private void initViews() {
        ivFilter = findViewById(R.id.ivFilter);
        rvCategories = findViewById(R.id.rvCategories);
        rvRestaurants = findViewById(R.id.rvRestaurants);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
    
    private void setupBottomNavigation() {
        // Set selected item
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_favorites) {
                // Navigate to FavoritesActivity
                Intent intent = new Intent(HomeActivity.this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_orders) {
                // Navigate to OrdersActivity
                Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Navigate to ProfileActivity
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            
            return false;
        });
    }
    
    private void loadCategories() {
        // Initialize categories
        categories = new ArrayList<>();
        categories.add(new Category(1, "Fast Food", R.drawable.category_fast_food, "Fast Food"));
        categories.add(new Category(2, "Pizza", R.drawable.ic_pizza, "Pizza"));
        categories.add(new Category(3, "Rice", R.drawable.ic_rice, "Rice"));
        categories.add(new Category(4, "Chicken", R.drawable.ic_chicken, "Chicken"));
        categories.add(new Category(5, "Steak", R.drawable.ic_steak, "Steak"));
        categories.add(new Category(6, "Kebab", R.drawable.ic_kebab, "Kebab"));
        
        // Set up RecyclerView
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories, this);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);
    }
    
    private void loadRestaurants() {
        // Initialize restaurants
        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1, "McDonald's", "123 Main St", "Fast Food, American Food, Pasta", 4.2f, 20, 1.2f, R.drawable.restaurant_1, "Fast Food", "$"));
        restaurants.add(new Restaurant(2, "Pizza Hut", "456 Main St", "Pizza, Italian Food, Pasta", 4.5f, 30, 2.5f, R.drawable.restaurant_2, "Pizza", "$$"));
        restaurants.add(new Restaurant(3, "Jollibee", "789 Main St", "Fast Food, Filipino Food, Pasta", 4.7f, 25, 1.5f, R.drawable.restaurant_3, "Chicken", "$"));
        restaurants.add(new Restaurant(4, "KFC", "101 Main St", "Fast Food, American Food, Chicken", 4.0f, 15, 0.8f, R.drawable.restaurant_4, "Chicken", "$"));
        
        // Set up RecyclerView
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, restaurants, this);
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this));
        rvRestaurants.setAdapter(restaurantAdapter);
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
    
    @Override
    public void onCategoryClicked(Category category) {
        // Reset filter manager
        filterManager.resetFilters();
        
        // Set category filter
        Set<String> selectedCategories = new HashSet<>();
        selectedCategories.add(category.getFilterKey());
        filterManager.setSelectedCategories(selectedCategories);
        filterManager.setFilterActive(true);
        filterManager.saveFilterSettings();
        
        // Navigate to RestaurantsListActivity
        Intent intent = new Intent(this, RestaurantsListActivity.class);
        intent.putExtra("category_name", category.getName());
        startActivity(intent);
        
        // Show toast for feedback
        Toast.makeText(this, "Showing " + category.getName() + " restaurants", Toast.LENGTH_SHORT).show();
    }
}
