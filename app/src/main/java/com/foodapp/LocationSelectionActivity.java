package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.Address;
import com.foodapp.utils.PreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.UUID;

public class LocationSelectionActivity extends AppCompatActivity {

    private MapView mapView;
    private IMapController mapController;
    private Marker locationMarker;
    private Button btnContinue;
    private PreferenceManager preferenceManager;
    private GeoPoint selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize OSMDroid configuration
        Configuration.getInstance().setUserAgentValue(getPackageName());
        
        setContentView(R.layout.activity_location_selection);

        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);

        // Initialize views
        mapView = findViewById(R.id.mapView);
        btnContinue = findViewById(R.id.btnContinue);

        // Set up the map
        setupMap();

        // Set up the button
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLocation != null) {
                    showAddressDetailsBottomSheet();
                } else {
                    Toast.makeText(LocationSelectionActivity.this, "Please select a location on the map", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        
        mapController = mapView.getController();
        mapController.setZoom(14.0);
        
        // Default location (City center or a default location)
        GeoPoint startPoint = new GeoPoint(18.5204, 73.8567); // Pune, India
        mapController.setCenter(startPoint);
        
        // Add a tap listener to the map
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                updateMarker(p);
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mapEventsReceiver);
        mapView.getOverlays().add(0, mapEventsOverlay);
    }

    private void updateMarker(GeoPoint point) {
        selectedLocation = point;
        
        if (locationMarker == null) {
            locationMarker = new Marker(mapView);
            locationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapView.getOverlays().add(locationMarker);
        }
        
        locationMarker.setPosition(point);
        locationMarker.setTitle("Selected Location");
        mapView.invalidate();
    }

    private void showAddressDetailsBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_address_details, null);
        
        // Find views in bottom sheet
        EditText etAddressLine = bottomSheetView.findViewById(R.id.etAddressLine);
        EditText etCity = bottomSheetView.findViewById(R.id.etCity);
        EditText etState = bottomSheetView.findViewById(R.id.etState);
        EditText etZipCode = bottomSheetView.findViewById(R.id.etZipCode);
        RadioGroup rgAddressType = bottomSheetView.findViewById(R.id.rgAddressType);
        Button btnSaveAddress = bottomSheetView.findViewById(R.id.btnSaveAddress);
        
        // Set default values if needed
        etCity.setText("Pune");
        etState.setText("Maharashtra");
        
        // Save button click listener
        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate inputs
                String addressLine = etAddressLine.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String state = etState.getText().toString().trim();
                String zipCode = etZipCode.getText().toString().trim();
                
                if (addressLine.isEmpty()) {
                    etAddressLine.setError("Address is required");
                    return;
                }
                
                if (city.isEmpty()) {
                    etCity.setError("City is required");
                    return;
                }
                
                if (state.isEmpty()) {
                    etState.setError("State is required");
                    return;
                }
                
                // Get selected address type
                int selectedId = rgAddressType.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = bottomSheetView.findViewById(selectedId);
                String addressType = "Home"; // Default
                
                if (selectedRadioButton != null) {
                    addressType = selectedRadioButton.getText().toString();
                }
                
                // Create and save address
                Address address = new Address(
                    UUID.randomUUID().toString(),
                    addressType,
                    addressLine,
                    selectedLocation.getLatitude(),
                    selectedLocation.getLongitude()
                );
                
                address.setCity(city);
                address.setState(state);
                address.setPostalCode(zipCode);
                
                preferenceManager.saveAddress(address);
                
                Toast.makeText(LocationSelectionActivity.this, "Address saved successfully", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
                
                // Navigate to HomeActivity
                navigateToHome();
            }
        });
        
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    
    private void navigateToHome() {
        Intent intent = new Intent(LocationSelectionActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
