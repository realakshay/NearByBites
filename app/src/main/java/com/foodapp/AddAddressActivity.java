package com.foodapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.Address;
import com.foodapp.utils.PreferenceManager;

public class AddAddressActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private EditText etAddressLabel;
    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private EditText etCity;
    private EditText etState;
    private EditText etZipCode;
    private Button btnSaveAddress;
    
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        initViews();
        
        // Set up click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        etAddressLabel = findViewById(R.id.etAddressLabel);
        etAddressLine1 = findViewById(R.id.etAddressLine1);
        etAddressLine2 = findViewById(R.id.etAddressLine2);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etZipCode = findViewById(R.id.etZipCode);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);
        
        // Set title
        tvTitle.setText("Add New Address");
    }
    
    private void setupClickListeners() {
        // Back button
        ivBack.setOnClickListener(v -> onBackPressed());
        
        // Save address button
        btnSaveAddress.setOnClickListener(v -> {
            if (validateInputs()) {
                saveAddress();
            }
        });
    }
    
    private boolean validateInputs() {
        String label = etAddressLabel.getText().toString().trim();
        String addressLine1 = etAddressLine1.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String zipCode = etZipCode.getText().toString().trim();
        
        // Validate label
        if (TextUtils.isEmpty(label)) {
            etAddressLabel.setError("Please enter an address label (e.g., Home, Work)");
            return false;
        }
        
        // Validate address line 1
        if (TextUtils.isEmpty(addressLine1)) {
            etAddressLine1.setError("Please enter street address");
            return false;
        }
        
        // Validate city
        if (TextUtils.isEmpty(city)) {
            etCity.setError("Please enter city");
            return false;
        }
        
        // Validate state
        if (TextUtils.isEmpty(state)) {
            etState.setError("Please enter state");
            return false;
        }
        
        // Validate zip code
        if (TextUtils.isEmpty(zipCode)) {
            etZipCode.setError("Please enter zip code");
            return false;
        }
        
        return true;
    }
    
    private void saveAddress() {
        // Get values from form
        String label = etAddressLabel.getText().toString().trim();
        String addressLine1 = etAddressLine1.getText().toString().trim();
        String addressLine2 = etAddressLine2.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String zipCode = etZipCode.getText().toString().trim();
        
        // Create new address
        Address newAddress = new Address(
                label,
                addressLine1,
                addressLine2,
                city,
                state,
                zipCode,
                "0", // latitude (placeholder)
                "0"  // longitude (placeholder)
        );
        
        // Save address and set as selected
        preferenceManager.saveSelectedAddress(newAddress);
        
        // Show success message
        Toast.makeText(this, "Address added successfully", Toast.LENGTH_SHORT).show();
        
        // Return to previous screen
        finish();
    }
}
