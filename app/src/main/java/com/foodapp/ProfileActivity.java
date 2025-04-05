package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.foodapp.models.User;
import com.foodapp.utils.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private Button btnLogout;
    private Button btnEditProfile;
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);
        
        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Profile");
        }
        
        // Set up bottom navigation
        bottomNavigation.setSelectedItemId(R.id.nav_profile);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                finish();
                return true;
            } else if (itemId == R.id.nav_favorites) {
                finish();
                startActivity(getIntent().setClass(this, FavoritesActivity.class));
                return true;
            } else if (itemId == R.id.nav_orders) {
                finish();
                startActivity(getIntent().setClass(this, OrdersActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                return true;
            }
            
            return false;
        });
        
        // Set up user information
        setupUserInfo();
        
        // Set up button click listeners
        btnLogout.setOnClickListener(v -> {
            preferenceManager.logoutCurrentUser();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            
            // Navigate to login
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Edit profile functionality will be implemented later", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void setupUserInfo() {
        User currentUser = preferenceManager.getCurrentUser();
        
        if (currentUser != null) {
            tvProfileName.setText(currentUser.getFullName());
            tvProfileEmail.setText(currentUser.getEmail());
        }
    }
}
