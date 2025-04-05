package com.foodapp.models;

public class Address {
    private int id;
    private String type;
    private String addressLine;
    
    public Address(int id, String type, String addressLine) {
        this.id = id;
        this.type = type;
        this.addressLine = addressLine;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getAddressLine() {
        return addressLine;
    }
    
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
}
