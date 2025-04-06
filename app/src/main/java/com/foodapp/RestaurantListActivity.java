package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.models.Address;
import com.foodapp.models.User;
import com.foodapp.utils.PreferenceManager;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private RecyclerView rvRestaurants;
    private TextView tvAddressType;
    private TextView tvUserName;
    private Toolbar toolbar;
    
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        initViews();
        
        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.restaurant_list);
        }
        
        // Set up layout manager
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this));

        // Get selected address type from intent
        if (getIntent() != null && getIntent().hasExtra("selected_address_type")) {
            String addressType = getIntent().getStringExtra("selected_address_type");
            tvAddressType.setText("Delivering to " + addressType);
        }
        
        // Load user information
        loadUserInfo();
        
        // Load saved addresses
        loadSavedAddresses();
    }

    private void initViews() {
        rvRestaurants = findViewById(R.id.rvRestaurants);
        tvAddressType = findViewById(R.id.tvAddressType);
        tvUserName = findViewById(R.id.tvUserName);
        toolbar = findViewById(R.id.toolbar);
    }
    
    private void loadUserInfo() {
        User currentUser = preferenceManager.getCurrentUser();
        if (currentUser != null) {
            tvUserName.setText("Hello, " + currentUser.getFirstName());
        }
    }
    
    private void loadSavedAddresses() {
        List<Address> addresses = preferenceManager.getAddresses();
        
        // In a real app, we would display these addresses in a dropdown or list
        // For now, we'll show a toast with the number of saved addresses
        if (!addresses.isEmpty()) {
            Toast.makeText(this, "You have " + addresses.size() + " saved addresses", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_logout) {
            logoutUser();
            return true;
        } else if (id == R.id.action_add_address) {
            navigateToLocationSelection();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private void logoutUser() {
        preferenceManager.logoutCurrentUser();
        
        // Navigate back to login screen
        Intent intent = new Intent(RestaurantListActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    
    private void navigateToLocationSelection() {
        Intent intent = new Intent(RestaurantListActivity.this, LocationSelectionActivity.class);
        startActivity(intent);
    }
}
