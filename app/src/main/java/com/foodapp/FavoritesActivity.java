package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.FavoriteRestaurantAdapter;
import com.foodapp.models.Restaurant;
import com.foodapp.utils.FavoriteManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoriteRestaurantAdapter.FavoriteRestaurantListener {

    private RecyclerView rvFavorites;
    private TextView tvNoFavorites;
    private BottomNavigationView bottomNavigation;
    
    private FavoriteManager favoriteManager;
    private List<Restaurant> favorites;
    private FavoriteRestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        
        // Initialize FavoriteManager
        favoriteManager = FavoriteManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Set up bottom navigation
        setupBottomNavigation();
        
        // Load favorites
        loadFavorites();
    }
    
    private void initViews() {
        rvFavorites = findViewById(R.id.rvFavorites);
        tvNoFavorites = findViewById(R.id.tvNoFavorites);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        
        // Set up RecyclerView
        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupBottomNavigation() {
        // Set selected item
        bottomNavigation.setSelectedItemId(R.id.nav_favorites);
        
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                // Navigate to HomeActivity
                Intent intent = new Intent(FavoritesActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_favorites) {
                return true;
            } else if (itemId == R.id.nav_orders) {
                // Navigate to OrdersActivity
                Intent intent = new Intent(FavoritesActivity.this, OrdersActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Navigate to ProfileActivity
                Intent intent = new Intent(FavoritesActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            
            return false;
        });
    }
    
    private void loadFavorites() {
        // Get favorite restaurants
        favorites = favoriteManager.getFavoriteRestaurants();
        
        if (favorites.isEmpty()) {
            // Show empty state
            tvNoFavorites.setVisibility(View.VISIBLE);
            rvFavorites.setVisibility(View.GONE);
            
            // Add some demo favorites if no favorites exist
            favoriteManager.addFavoriteRestaurant(new Restaurant(1, "McDonald's", "123 Main St", "Fast Food, American Food, Pasta", 4.2f, 20, 30, R.drawable.restaurant_1));
            favoriteManager.addFavoriteRestaurant(new Restaurant(3, "Jollibee", "789 Main St", "Fast Food, Filipino Food, Pasta", 4.7f, 25, 35, R.drawable.restaurant_3));
            
            // Reload favorites
            favorites = favoriteManager.getFavoriteRestaurants();
            
            if (!favorites.isEmpty()) {
                tvNoFavorites.setVisibility(View.GONE);
                rvFavorites.setVisibility(View.VISIBLE);
            }
        }
        
        // Set up adapter
        adapter = new FavoriteRestaurantAdapter(this, favorites, this);
        rvFavorites.setAdapter(adapter);
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
        
        // Show toast for debugging
        Toast.makeText(this, "Navigating to " + restaurant.getName() + " details", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFavoriteToggled(Restaurant restaurant, boolean isFavorite) {
        if (isFavorite) {
            favoriteManager.addFavoriteRestaurant(restaurant);
            Toast.makeText(this, restaurant.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            favoriteManager.removeFavoriteRestaurant(restaurant.getId());
            // Refresh the list
            favorites.remove(restaurant);
            adapter.notifyDataSetChanged();
            
            Toast.makeText(this, restaurant.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            
            // Show empty state if no favorites
            if (favorites.isEmpty()) {
                tvNoFavorites.setVisibility(View.VISIBLE);
                rvFavorites.setVisibility(View.GONE);
            }
        }
    }
}
