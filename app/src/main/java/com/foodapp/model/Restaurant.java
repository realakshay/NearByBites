package com.foodapp.model;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String cuisine;
    private String address;
    private float rating;
    private int minDeliveryTime;
    private int maxDeliveryTime;
    private boolean isOpen;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String cuisine, String address, float rating, 
                    int minDeliveryTime, int maxDeliveryTime, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.address = address;
        this.rating = rating;
        this.minDeliveryTime = minDeliveryTime;
        this.maxDeliveryTime = maxDeliveryTime;
        this.isOpen = isOpen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getMinDeliveryTime() {
        return minDeliveryTime;
    }

    public void setMinDeliveryTime(int minDeliveryTime) {
        this.minDeliveryTime = minDeliveryTime;
    }

    public int getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public void setMaxDeliveryTime(int maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
