package com.foodapp.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model class representing a delivery address
 */
public class Address {
    private String id;
    private String fullName;
    private String streetAddress;
    private String cityAddress;
    private String landmark;
    private String addressType; // "Home", "Office", or "Friends"
    private LatLng location;

    // Constructor
    public Address(String id, String fullName, String streetAddress, String cityAddress, 
                  String landmark, String addressType, LatLng location) {
        this.id = id;
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.landmark = landmark;
        this.addressType = addressType;
        this.location = location;
    }

    // Empty constructor
    public Address() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return streetAddress + ", " + cityAddress + 
               (landmark != null && !landmark.isEmpty() ? ", Near " + landmark : "");
    }

    /**
     * Returns a formatted full address
     */
    public String getFormattedAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(streetAddress);
        
        if (cityAddress != null && !cityAddress.isEmpty()) {
            sb.append(", ").append(cityAddress);
        }
        
        if (landmark != null && !landmark.isEmpty()) {
            sb.append(", Near ").append(landmark);
        }
        
        return sb.toString();
    }
}