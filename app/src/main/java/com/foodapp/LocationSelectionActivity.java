package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.models.Address;
import com.foodapp.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class LocationSelectionActivity extends AppCompatActivity {

    private RecyclerView rvAddresses;
    private Button btnUseCurrentLocation;
    private Button btnAddNewAddress;
    private TextView tvTitle;
    private ImageView ivBack;
    
    private PreferenceManager preferenceManager;
    private List<Address> savedAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        rvAddresses = findViewById(R.id.rvAddresses);
        btnUseCurrentLocation = findViewById(R.id.btnUseCurrentLocation);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        
        // Hide back button as this screen should not go back to tour guide
        ivBack.setVisibility(View.GONE);
        
        // Set title
        tvTitle.setText("Select Delivery Location");
        
        // Load addresses
        loadAddresses();
        
        // Set up RecyclerView
        setupRecyclerView();
        
        // Set up button listeners
        setupButtonListeners();
    }
    
    private void loadAddresses() {
        // In a real app, this would come from storage or user profile
        savedAddresses = new ArrayList<>();
        
        // Add some dummy addresses for demo
        savedAddresses.add(new Address("Home", "123 Main Street", "Apt 4B", "New York", "NY", "10001", "40.7128", "-74.0060"));
        savedAddresses.add(new Address("Work", "456 Park Avenue", "Floor 20", "New York", "NY", "10022", "40.7624", "-73.9738"));
        savedAddresses.add(new Address("Gym", "789 Fitness Way", "", "New York", "NY", "10013", "40.7254", "-74.0051"));
    }
    
    private void setupRecyclerView() {
        // In a real implementation, create an AddressAdapter and set it up here
        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        // rvAddresses.setAdapter(new AddressAdapter(this, savedAddresses, this::selectAddress));
    }
    
    private void setupButtonListeners() {
        btnUseCurrentLocation.setOnClickListener(v -> {
            // For demo purposes, create a current location address
            Address currentLocation = new Address("Current Location", "Current Street", "", "Current City", "State", "12345", "0", "0");
            selectAddress(currentLocation);
        });
        
        btnAddNewAddress.setOnClickListener(v -> {
            // In a real app, show address input form
            Toast.makeText(this, "Add new address functionality will be implemented later", Toast.LENGTH_SHORT).show();
            
            // For demo, just proceed with the first address
            if (!savedAddresses.isEmpty()) {
                selectAddress(savedAddresses.get(0));
            }
        });
    }
    
    private void selectAddress(Address address) {
        // Save selected address
        preferenceManager.saveSelectedAddress(address);
        
        // Navigate to home screen
        Intent intent = new Intent(LocationSelectionActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onBackPressed() {
        // Don't allow going back to TourGuide
        // Instead, offer logout option
        Toast.makeText(this, "Please select a delivery location to continue", Toast.LENGTH_SHORT).show();
    }
}
