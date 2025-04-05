package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.CategoryAdapter;
import com.foodapp.adapters.RestaurantAdapter;
import com.foodapp.models.Category;
import com.foodapp.models.Restaurant;
import com.foodapp.utils.CartManager;
import com.foodapp.utils.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private RecyclerView rvCategories, rvRestaurants;
    private CategoryAdapter categoryAdapter;
    private RestaurantAdapter restaurantAdapter;
    private List<Category> categories = new ArrayList<>();
    private List<Restaurant> restaurants = new ArrayList<>();
    private BottomNavigationView bottomNavigation;
    private FloatingActionButton fabCart;
    private LinearLayout locationContainer;
    private TextView tvUserLocation, tvGreeting, tvSearch;
    private ImageView ivFilter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
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
        rvCategories = findViewById(R.id.rvCategories);
        rvRestaurants = findViewById(R.id.rvRestaurants);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        fabCart = findViewById(R.id.fabCart);
        locationContainer = findViewById(R.id.locationContainer);
        tvUserLocation = findViewById(R.id.tvUserLocation);
        tvGreeting = findViewById(R.id.tvGreeting);
        tvSearch = findViewById(R.id.tvSearch);
        ivFilter = findViewById(R.id.ivFilter);
        
        // Setup RecyclerViews
        setupRecyclerViews();
        
        // Setup cart button
        setupCartButton();
    }
    
    private void setupUserInformation() {
        // Set greeting with user name if available
        if (preferenceManager.getCurrentUser() != null) {
            String firstName = preferenceManager.getCurrentUser().getFirstName();
            if (firstName != null && !firstName.isEmpty()) {
                // Create a stylized greeting with a line break
                String greeting = "Hello " + firstName + ",\nWhat would you like to eat?";
                tvGreeting.setText(greeting);
                tvGreeting.setTextColor(getResources().getColor(R.color.colorGreen));
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
    
    private void setupBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                
                if (itemId == R.id.action_home) {
                    // Already on HomeActivity
                    return true;
                } else if (itemId == R.id.action_orders) {
                    // Navigate to OrdersActivity
                    Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.action_favorites) {
                    // Navigate to FavoritesActivity
                    Intent intent = new Intent(HomeActivity.this, FavoritesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.action_profile) {
                    // Navigate to ProfileActivity
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                
                return false;
            }
        });
    }
    
    private void setupSearchBar() {
        // Set up search bar click listener
        LinearLayout searchBar = findViewById(R.id.searchBar);
        searchBar.setOnClickListener(v -> {
            // Navigate to SearchResultActivity
            Intent intent = new Intent(HomeActivity.this, SearchResultActivity.class);
            startActivity(intent);
        });
    }
    
    private void setupRecyclerViews() {
        // Setup categories RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rvCategories.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(this, categories, category -> {
            // Navigate to RestaurantsListActivity with selected category
            Intent intent = new Intent(HomeActivity.this, RestaurantsListActivity.class);
            intent.putExtra("CATEGORY_ID", category.getId());
            intent.putExtra("CATEGORY_NAME", category.getName());
            startActivity(intent);
        });
        rvCategories.setAdapter(categoryAdapter);
        
        // Setup restaurants RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRestaurants.setLayoutManager(linearLayoutManager);
        restaurantAdapter = new RestaurantAdapter(restaurants, restaurant -> {
            // Navigate to RestaurantDetailsActivity with selected restaurant
            Intent intent = new Intent(HomeActivity.this, RestaurantDetailsActivity.class);
            intent.putExtra("RESTAURANT_ID", restaurant.getId());
            startActivity(intent);
        });
        rvRestaurants.setAdapter(restaurantAdapter);
    }
    
    private void setupCartButton() {
        // Show cart button only if there are items in cart
        CartManager cartManager = CartManager.getInstance(this);
        fabCart.setVisibility(cartManager.getItemCount() > 0 ? android.view.View.VISIBLE : android.view.View.GONE);
        
        // Set click listener for cart button
        fabCart.setOnClickListener(v -> {
            // Navigate to CartActivity
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
    
    private void loadCategories() {
        // Sample categories data - In a real app, this would come from an API
        categories.clear();
        categories.add(new Category(1, "Burgers", R.drawable.ic_category_burger));
        categories.add(new Category(2, "Pizza", R.drawable.ic_category_pizza));
        categories.add(new Category(3, "Sushi", R.drawable.ic_category_sushi));
        categories.add(new Category(4, "Salads", R.drawable.ic_category_salad));
        categories.add(new Category(5, "Desserts", R.drawable.ic_category_dessert));
        categories.add(new Category(6, "Drinks", R.drawable.ic_category_drink));
        categories.add(new Category(7, "Italian", R.drawable.ic_category_italian));
        categories.add(new Category(8, "Asian", R.drawable.ic_category_asian));
        
        categoryAdapter.notifyDataSetChanged();
    }
    
    private void loadRestaurants() {
        // Sample restaurants data - In a real app, this would come from an API
        restaurants.clear();
        restaurants.add(new Restaurant(1, "Burger King", "Fast Food, Burgers", "4.5", "15-20 min", "$5.99", R.drawable.restaurant_burger));
        restaurants.add(new Restaurant(2, "Pizza Hut", "Italian, Pizza", "4.2", "25-30 min", "$6.99", R.drawable.restaurant_pizza));
        restaurants.add(new Restaurant(3, "Sushi House", "Japanese, Sushi", "4.8", "30-35 min", "$12.99", R.drawable.restaurant_sushi));
        restaurants.add(new Restaurant(4, "Green Garden", "Healthy, Salads", "4.1", "20-25 min", "$8.99", R.drawable.restaurant_salad));
        restaurants.add(new Restaurant(5, "Sweet Treats", "Desserts, Bakery", "4.6", "15-20 min", "$4.99", R.drawable.restaurant_dessert));
        
        restaurantAdapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        // Update cart button visibility
        CartManager cartManager = CartManager.getInstance(this);
        fabCart.setVisibility(cartManager.getItemCount() > 0 ? android.view.View.VISIBLE : android.view.View.GONE);
        
        // Update user location if changed
        if (preferenceManager.getSelectedAddress() != null) {
            String address = preferenceManager.getSelectedAddress().getAddressLine1();
            if (address != null && !address.isEmpty()) {
                tvUserLocation.setText(address);
            }
        }
    }
    
    private long backPressedTime;
    
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            // Finish all activities
            finishAffinity();
        } else {
            // Show toast message
            android.widget.Toast.makeText(this, "Press back again to exit", android.widget.Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
