package com.foodapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LocationSelectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private BottomSheetDialog bottomSheetDialog;
    private LatLng selectedLocation;

    // UI elements
    private Button btnHome, btnOffice, btnFriends, btnSaveAddress;
    private EditText etFullName, etStreetAddress, etCityAddress, etLandmark;
    private ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Create bottom sheet dialog
        createBottomSheetDialog();
    }

    /**
     * Creates and configures the bottom sheet dialog for address details
     */
    private void createBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_address_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Initialize UI elements
        btnHome = bottomSheetView.findViewById(R.id.btnHome);
        btnOffice = bottomSheetView.findViewById(R.id.btnOffice);
        btnFriends = bottomSheetView.findViewById(R.id.btnFriends);
        btnSaveAddress = bottomSheetView.findViewById(R.id.btnSaveAddress);
        btnClose = bottomSheetView.findViewById(R.id.btnClose);
        etFullName = bottomSheetView.findViewById(R.id.etFullName);
        etStreetAddress = bottomSheetView.findViewById(R.id.etStreetAddress);
        etCityAddress = bottomSheetView.findViewById(R.id.etCityAddress);
        etLandmark = bottomSheetView.findViewById(R.id.etLandmark);

        // Set up button click listeners
        btnHome.setOnClickListener(v -> selectAddressType("Home"));
        btnOffice.setOnClickListener(v -> selectAddressType("Office"));
        btnFriends.setOnClickListener(v -> selectAddressType("Friends"));
        btnClose.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnSaveAddress.setOnClickListener(v -> {
            if (validateAddressForm()) {
                saveAddress();
                bottomSheetDialog.dismiss();
                
                // Navigate to Restaurant List screen
                Intent intent = new Intent(LocationSelectionActivity.this, RestaurantListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Updates UI to reflect the selected address type button
     */
    private void selectAddressType(String type) {
        // Reset all buttons
        btnHome.setBackgroundResource(R.drawable.rounded_button_outline);
        btnOffice.setBackgroundResource(R.drawable.rounded_button_outline);
        btnFriends.setBackgroundResource(R.drawable.rounded_button_outline);
        btnHome.setTextColor(getResources().getColor(android.R.color.black));
        btnOffice.setTextColor(getResources().getColor(android.R.color.black));
        btnFriends.setTextColor(getResources().getColor(android.R.color.black));

        // Set the selected button
        switch (type) {
            case "Home":
                btnHome.setBackgroundResource(R.drawable.rounded_button_orange);
                btnHome.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Office":
                btnOffice.setBackgroundResource(R.drawable.rounded_button_orange);
                btnOffice.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Friends":
                btnFriends.setBackgroundResource(R.drawable.rounded_button_orange);
                btnFriends.setTextColor(getResources().getColor(android.R.color.white));
                break;
        }
    }

    /**
     * Validates the form fields
     */
    private boolean validateAddressForm() {
        if (etFullName.getText().toString().trim().isEmpty()) {
            etFullName.setError("Name is required");
            return false;
        }
        
        if (etStreetAddress.getText().toString().trim().isEmpty()) {
            etStreetAddress.setError("Street address is required");
            return false;
        }
        
        return true;
    }

    /**
     * Saves the address information
     */
    private void saveAddress() {
        // Here you would save the address to your database or shared preferences
        // For now, we'll just show a toast
        String fullName = etFullName.getText().toString();
        Toast.makeText(this, "Address saved for " + fullName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check and request location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            enableMyLocation();
        }

        // Set up the map click listener
        mMap.setOnMapClickListener(latLng -> {
            selectedLocation = latLng;
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            
            // Show bottom sheet dialog
            bottomSheetDialog.show();
        });
    }

    /**
     * Enables location tracking on the map and centers on the user's location
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            
            // Get the last known location
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Location permission is required to select your location",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}