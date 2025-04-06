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

import com.foodapp.adapters.AddressAdapter;
import com.foodapp.models.Address;
import com.foodapp.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class AddressSelectionActivity extends AppCompatActivity implements AddressAdapter.AddressClickListener {

    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvAddresses;
    private Button btnUseCurrentLocation;
    private Button btnAddNewAddress;
    
    private PreferenceManager preferenceManager;
    private List<Address> savedAddresses;
    private AddressAdapter addressAdapter;
    private Address selectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        initViews();
        
        // Load addresses
        loadSavedAddresses();
        
        // Set up RecyclerView
        setupRecyclerView();
        
        // Set up click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        rvAddresses = findViewById(R.id.rvAddresses);
        btnUseCurrentLocation = findViewById(R.id.btnUseCurrentLocation);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        
        // Set title
        tvTitle.setText("Select Delivery Address");
    }
    
    private void loadSavedAddresses() {
        savedAddresses = new ArrayList<>();
        
        // Get previously selected address
        selectedAddress = preferenceManager.getSelectedAddress();
        
        // Add some sample addresses for demo
        savedAddresses.add(new Address("Home", "123 Main Street", "Apt 4B", "New York", "NY", "10001", 40.7128, -74.0060));
        savedAddresses.add(new Address("Work", "456 Park Avenue", "Floor 20", "New York", "NY", "10022", 40.7624, -73.9738));
        savedAddresses.add(new Address("Gym", "789 Fitness Way", "", "New York", "NY", "10013", 40.7254, -74.0051));

        
        // If we have a selected address that's not in the list (like "Current Location"),
        // add it to the list
        if (selectedAddress != null) {
            boolean found = false;
            for (Address address : savedAddresses) {
                if (address.getFormattedAddress().equals(selectedAddress.getFormattedAddress())) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                savedAddresses.add(0, selectedAddress);
            }
        }
    }
    
    private void setupRecyclerView() {
        // Create adapter
        addressAdapter = new AddressAdapter(this, savedAddresses, selectedAddress, this);
        
        // Set up RecyclerView
        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvAddresses.setAdapter(addressAdapter);
    }
    
    private void setupClickListeners() {
        // Back button
        ivBack.setOnClickListener(v -> onBackPressed());
        
        // Use current location button
        btnUseCurrentLocation.setOnClickListener(v -> {
            // In a real app, we would use location services to get the current location
            // For now, we'll create a dummy address
            Address currentLocation = new Address(
                "Current Location",
                "Current Street",
                "",
                "Current City",
                "State",
                "12345",
                0.0,
                0.0
            );

            
            // Save the selected address
            preferenceManager.saveSelectedAddress(currentLocation);
            
            // Return to HomeActivity
            Toast.makeText(this, "Using current location", Toast.LENGTH_SHORT).show();
            finish();
        });
        
        // Add new address button
        btnAddNewAddress.setOnClickListener(v -> {
            // Navigate to AddAddressActivity
            Intent intent = new Intent(AddressSelectionActivity.this, AddAddressActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    public void onAddressSelected(Address address) {
        // Save the selected address
        preferenceManager.saveSelectedAddress(address);
        
        // Update the adapter
        addressAdapter.setSelectedAddress(address);
        
        // Show success message
        Toast.makeText(this, "Delivery address updated", Toast.LENGTH_SHORT).show();
        
        // Return to HomeActivity
        finish();
    }
    
    @Override
    public void onAddressEditClicked(Address address, int position) {
        // In a real app, navigate to edit address screen
        // For now, just show a toast
        Toast.makeText(this, "Edit address functionality will be implemented later", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onAddressDeleteClicked(Address address, int position) {
        // Remove the address from the list
        savedAddresses.remove(position);
        
        // Update the adapter
        addressAdapter.notifyItemRemoved(position);
        
        // If the deleted address was the selected one, select the first address
        if (selectedAddress != null && 
                address.getFormattedAddress().equals(selectedAddress.getFormattedAddress())) {
            if (!savedAddresses.isEmpty()) {
                selectedAddress = savedAddresses.get(0);
                preferenceManager.saveSelectedAddress(selectedAddress);
                addressAdapter.setSelectedAddress(selectedAddress);
            } else {
                selectedAddress = null;
                preferenceManager.saveSelectedAddress(null);
            }
        }
        
        // Show success message
        Toast.makeText(this, "Address removed", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        // Refresh the address list when returning from AddAddressActivity
        loadSavedAddresses();
        
        // Update the adapter
        if (addressAdapter != null) {
            addressAdapter.updateAddresses(savedAddresses, selectedAddress);
        }
    }
}
