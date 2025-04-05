package com.foodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.User;
import com.foodapp.utils.UserManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView ivProfileImage;
    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private Button btnEditProfile;
    private Button btnLogout;
    private View btnMyOrders;
    private View btnManageAddresses;
    private View btnFavorites;
    private BottomNavigationView bottomNavigation;
    
    private UserManager userManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        // Initialize UserManager
        userManager = UserManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Set up bottom navigation
        setupBottomNavigation();
        
        // Load user data
        loadUserData();
        
        // Set up button click listeners
        setupClickListeners();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Reload user data when returning to this screen
        loadUserData();
    }
    
    private void initViews() {
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnMyOrders = findViewById(R.id.btnMyOrders);
        btnManageAddresses = findViewById(R.id.btnManageAddresses);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnLogout = findViewById(R.id.btnLogout);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
    
    private void setupBottomNavigation() {
        // Set selected item
        bottomNavigation.setSelectedItemId(R.id.nav_profile);
        
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                // Navigate to HomeActivity
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_favorites) {
                // Navigate to FavoritesActivity
                Intent intent = new Intent(ProfileActivity.this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_orders) {
                // Navigate to OrdersActivity
                Intent intent = new Intent(ProfileActivity.this, OrdersActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                return true;
            }
            
            return false;
        });
    }
    
    private void loadUserData() {
        User user = userManager.getCurrentUser();
        
        if (user != null) {
            // Set user data
            tvProfileName.setText(user.getFullName());
            tvProfileEmail.setText(user.getEmail());
            
            // Set profile image if available
            if (user.getProfileImageUri() != null && !user.getProfileImageUri().isEmpty()) {
                try {
                    Uri imageUri = Uri.parse(user.getProfileImageUri());
                    ivProfileImage.setImageURI(imageUri);
                } catch (Exception e) {
                    // If there's an error loading the image, use the default
                    ivProfileImage.setImageResource(R.drawable.profile_placeholder);
                }
            } else {
                ivProfileImage.setImageResource(R.drawable.profile_placeholder);
            }
        }
    }
    
    private void setupClickListeners() {
        // Edit Profile button
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
        
        // My Orders button
        btnMyOrders.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, OrdersActivity.class);
            startActivity(intent);
        });
        
        // Manage Addresses button
        btnManageAddresses.setOnClickListener(v -> {
            // For demo, just show a toast
            Toast.makeText(this, "Manage Addresses clicked", Toast.LENGTH_SHORT).show();
        });
        
        // Favorites button
        btnFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
        
        // Logout button
        btnLogout.setOnClickListener(v -> {
            // Perform logout
            userManager.logout();
            
            // Navigate to login screen
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        });
    }
}
