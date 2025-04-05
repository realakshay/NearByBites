package com.foodapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class to represent a delivery address
 */
public class Address implements Parcelable {
    private String label;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String latitude;
    private String longitude;
    
    public Address(String label, String addressLine1, String addressLine2, 
                  String city, String state, String zipCode, 
                  String latitude, String longitude) {
        this.label = label;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    protected Address(Parcel in) {
        label = in.readString();
        addressLine1 = in.readString();
        addressLine2 = in.readString();
        city = in.readString();
        state = in.readString();
        zipCode = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(addressLine1);
        dest.writeString(addressLine2);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zipCode);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }
        
        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    
    // Getters and Setters
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getAddressLine1() {
        return addressLine1;
    }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    
    public String getAddressLine2() {
        return addressLine2;
    }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    /**
     * Get the full formatted address string
     */
    public String getFormattedAddress() {
        StringBuilder sb = new StringBuilder();
        
        if (addressLine1 != null && !addressLine1.isEmpty()) {
            sb.append(addressLine1);
        }
        
        if (addressLine2 != null && !addressLine2.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(addressLine2);
        }
        
        if (city != null && !city.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(city);
        }
        
        if (state != null && !state.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(state);
        }
        
        if (zipCode != null && !zipCode.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(zipCode);
        }
        
        return sb.toString();
    }
}