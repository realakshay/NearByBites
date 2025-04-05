package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.CategoryAdapter;
import com.foodapp.adapters.RestaurantAdapter;
import com.foodapp.models.Category;
import com.foodapp.models.Restaurant;
import com.foodapp.models.User;
import com.foodapp.utils.CartManager;
import com.foodapp.utils.DataGenerator;
import com.foodapp.utils.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements 
        CategoryAdapter.OnCategoryClickListener, 
        RestaurantAdapter.OnRestaurantClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private PreferenceManager preferenceManager;
    private CartManager cartManager;
    private RecyclerView rvCategories;
    private RecyclerView rvRestaurants;
    private TextView tvGreeting;
    private TextView tvUsername;
    private TextView tvAddressType;
    private TextView tvAddressDetail;
    private BottomNavigationView bottomNavigation;
    private Button btnViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize PreferenceManager and CartManager
        preferenceManager = new PreferenceManager(this);
        cartManager = CartManager.getInstance(this);

        // Initialize views
        rvCategories = findViewById(R.id.rvCategories);
        rvRestaurants = findViewById(R.id.rvRestaurants);
        tvGreeting = findViewById(R.id.tvGreeting);
        tvUsername = findViewById(R.id.tvUsername);
        tvAddressType = findViewById(R.id.tvAddressType);
        tvAddressDetail = findViewById(R.id.tvAddressDetail);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        btnViewCart = findViewById(R.id.btnViewCart);

        // Set up bottom navigation
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.menu_home);

        // Set up greeting
        setupGreeting();

        // Set up address
        setupAddress();

        // Set up categories
        setupCategories();

        // Set up restaurants
        setupRestaurants();
        
        // Set up cart button
        setupCartButton();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Update cart button whenever returning to this screen
        updateCartButton();
    }

    private void setupGreeting() {
        // Get time of day for appropriate greeting
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hourOfDay < 12) {
            greeting = "Good Morning";
        } else if (hourOfDay < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        tvGreeting.setText(greeting);

        // Get user's name
        User user = preferenceManager.getUser();
        if (user != null && user.getName() != null && !user.getName().isEmpty()) {
            tvUsername.setText(user.getName());
        } else {
            tvUsername.setText("Codename One!");
        }
    }

    private void setupAddress() {
        // Get selected address from preferences
        com.foodapp.models.Address selectedAddress = preferenceManager.getSelectedAddress();
        if (selectedAddress != null) {
            tvAddressType.setText(selectedAddress.getType());
            tvAddressDetail.setText(selectedAddress.getAddressLine());
        } else {
            tvAddressType.setText("Home");
            tvAddressDetail.setText("Please add an address");
        }
    }

    private void setupCategories() {
        // Create a sample list of categories
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Rice", getDrawable(R.drawable.ic_rice)));
        categoryList.add(new Category(2, "Pizza", getDrawable(R.drawable.ic_pizza)));
        categoryList.add(new Category(3, "Donut", getDrawable(R.drawable.ic_donut)));
        categoryList.add(new Category(4, "Chicken", getDrawable(R.drawable.ic_chicken)));
        categoryList.add(new Category(5, "Steak", getDrawable(R.drawable.ic_steak)));
        categoryList.add(new Category(6, "Meal", getDrawable(R.drawable.ic_meal)));
        categoryList.add(new Category(7, "Kebab", getDrawable(R.drawable.ic_kebab)));
        categoryList.add(new Category(8, "All", getDrawable(R.drawable.ic_all)));

        // Set up the adapter
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rvCategories.setLayoutManager(gridLayoutManager);
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupRestaurants() {
        // Get restaurants from data generator
        List<Restaurant> restaurantList = DataGenerator.getRestaurants();

        // Set up the adapter
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRestaurants.setLayoutManager(linearLayoutManager);
        rvRestaurants.setAdapter(restaurantAdapter);
    }
    
    private void setupCartButton() {
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // In a real app, we would navigate to a CartActivity here
                Toast.makeText(HomeActivity.this, "View Cart functionality coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Update the cart button state
        updateCartButton();
    }
    
    private void updateCartButton() {
        int itemCount = cartManager.getCartItemCount();
        double totalPrice = cartManager.getCartTotal();
        
        // Always show the cart button, regardless of whether there are items in the cart
        if (itemCount > 0) {
            btnViewCart.setText("View Cart (" + itemCount + ") • $" + String.format("%.2f", totalPrice));
        } else {
            btnViewCart.setText("View Cart (0)");
        }
        
        // Always make the button visible
        btnViewCart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCategoryClick(Category category, int position) {
        Toast.makeText(this, "Selected category: " + category.getName(), Toast.LENGTH_SHORT).show();
        // In a real app, we would filter restaurants by category
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant, int position) {
        Toast.makeText(this, "Selected restaurant: " + restaurant.getName(), Toast.LENGTH_SHORT).show();
        // In a real app, we would navigate to the restaurant details screen
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.menu_home) {
            // Already on home screen
            return true;
        } else if (id == R.id.menu_favorites) {
            Toast.makeText(this, "Favorites feature coming soon!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_orders) {
            Toast.makeText(this, "Orders feature coming soon!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_profile) {
            Toast.makeText(this, "Profile feature coming soon!", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return false;
    }
}
