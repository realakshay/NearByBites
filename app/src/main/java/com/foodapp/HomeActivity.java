package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.CategoryAdapter;
import com.foodapp.adapters.RestaurantAdapter;
import com.foodapp.models.Category;
import com.foodapp.models.Restaurant;
import com.foodapp.utils.BackButtonManager;
import com.foodapp.utils.FilterManager;
import com.foodapp.utils.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements RestaurantAdapter.RestaurantClickListener, CategoryAdapter.CategoryClickListener {

    private ImageView ivFilter;
    private EditText etSearchHome;
    private RecyclerView rvCategories;
    private RecyclerView rvRestaurants;
    private BottomNavigationView bottomNavigation;
    private TextView tvGreeting;
    private TextView tvUserLocation;
    private View locationContainer;
    
    private List<Category> categories;
    private List<Restaurant> restaurants;
    private FilterManager filterManager;
    private PreferenceManager preferenceManager;
    private BackButtonManager backButtonManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        // Initialize managers
        filterManager = FilterManager.getInstance(this);
        preferenceManager = new PreferenceManager(this);
        backButtonManager = new BackButtonManager(this);
        
        // Initialize views
        initViews();
        
        // Set up user information
        setupUserInformation();
        
        // Set up bottom navigation
        setupBottomNavigation();
        
        // Set up search bar
        setupSearchBar();
        
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
        etSearchHome = findViewById(R.id.etSearchHome);
        rvCategories = findViewById(R.id.rvCategories);
        rvRestaurants = findViewById(R.id.rvRestaurants);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        tvGreeting = findViewById(R.id.tvGreeting);
        tvUserLocation = findViewById(R.id.tvUserLocation);
        locationContainer = findViewById(R.id.locationContainer);
    }
    
    private void setupUserInformation() {
        // Set greeting with user name if available
        if (preferenceManager.getCurrentUser() != null) {
            String firstName = preferenceManager.getCurrentUser().getFirstName();
            if (firstName != null && !firstName.isEmpty()) {
                tvGreeting.setText("Hello, " + firstName + "! What would you like to eat?");
            }
        }
        
        // Set user location if available
        if (preferenceManager.getSelectedAddress() != null) {
            String address = preferenceManager.getSelectedAddress().getAddressLine1();
            if (address != null && !address.isEmpty()) {
                tvUserLocation.setText(address);
            }
        }
        
        // Set click listener for address selection
        locationContainer.setOnClickListener(v -> {
            // Navigate to AddressSelectionActivity
            Intent intent = new Intent(HomeActivity.this, AddressSelectionActivity.class);
            startActivity(intent);
        });
    }
    
    private void setupSearchBar() {
        // Set up enter key listener for search
        etSearchHome.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || 
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                
                // Get search query
                String searchQuery = etSearchHome.getText().toString().trim();
                
                // Validate search query
                if (searchQuery.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter search term", Toast.LENGTH_SHORT).show();
                    return true;
                }
                
                // Navigate to search results
                Intent intent = new Intent(HomeActivity.this, SearchResultActivity.class);
                intent.putExtra("search_query", searchQuery);
                startActivity(intent);
                
                // Clear search field
                etSearchHome.setText("");
                
                return true;
            }
            return false;
        });
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
    
    @Override
    protected void onResume() {
        super.onResume();
        
        // Update address display
        if (preferenceManager.getSelectedAddress() != null) {
            String address = preferenceManager.getSelectedAddress().getAddressLine1();
            if (address != null && !address.isEmpty()) {
                tvUserLocation.setText(address);
            } else {
                tvUserLocation.setText(preferenceManager.getSelectedAddress().getFormattedAddress());
            }
        }
    }
    
    @Override
    public void onBackPressed() {
        // Use the back button manager to handle back press with double-tap to exit
        backButtonManager.handleBackPress();
    }
}
