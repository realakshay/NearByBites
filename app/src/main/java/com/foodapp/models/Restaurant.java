package com.foodapp.models;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String category;
    private float rating;
    private String distance;
    private String time;
    private String address;
    private String image;
    private String priceRange;
    private List<MenuItem> promoItems;
    private List<MenuItem> menuItems;

    public Restaurant() {
        // Empty constructor required for Serializable
    }

    public Restaurant(String id, String name, String category, float rating, String distance, 
                     String time, String address, String image, String priceRange) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.distance = distance;
        this.time = time;
        this.address = address;
        this.image = image;
        this.priceRange = priceRange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public List<MenuItem> getPromoItems() {
        return promoItems;
    }

    public void setPromoItems(List<MenuItem> promoItems) {
        this.promoItems = promoItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
