package com.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView rvFavorites;
    private TextView tvNoFavorites;
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        
        // Initialize views
        rvFavorites = findViewById(R.id.rvFavorites);
        tvNoFavorites = findViewById(R.id.tvNoFavorites);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);
        
        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Favorites");
        }
        
        // Set up bottom navigation
        bottomNavigation.setSelectedItemId(R.id.nav_favorites);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                finish();
                return true;
            } else if (itemId == R.id.nav_favorites) {
                return true;
            } else if (itemId == R.id.nav_orders) {
                finish();
                startActivity(getIntent().setClass(this, OrdersActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                finish();
                startActivity(getIntent().setClass(this, ProfileActivity.class));
                return true;
            }
            
            return false;
        });
        
        // Set up RecyclerView
        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        
        // For now, show the "no favorites" message
        rvFavorites.setVisibility(View.GONE);
        tvNoFavorites.setVisibility(View.VISIBLE);
    }
}
