package com.foodapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to represent a restaurant
 */
public class Restaurant {
    private int id;
    private String name;
    private String address;
    private String cuisine;
    private float rating;
    private int deliveryTime; // in minutes
    private float distance; // in kilometers or miles
    private int imageResourceId;
    private String category;
    private String priceRange;
    private boolean isFavorite;
    private List<MenuItem> menuItems;
    
    public Restaurant(int id, String name, String address, String cuisine, 
                     float rating, int deliveryTime, float distance, 
                     int imageResourceId, String category, String priceRange) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.distance = distance;
        this.imageResourceId = imageResourceId;
        this.category = category;
        this.priceRange = priceRange;
        this.isFavorite = false;
        this.menuItems = new ArrayList<>();
    }
    
    // Getters and Setters
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCuisine() {
        return cuisine;
    }
    
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    
    public float getRating() {
        return rating;
    }
    
    public void setRating(float rating) {
        this.rating = rating;
    }
    
    public int getDeliveryTime() {
        return deliveryTime;
    }
    
    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    public float getDistance() {
        return distance;
    }
    
    public void setDistance(float distance) {
        this.distance = distance;
    }
    
    public int getImageResourceId() {
        return imageResourceId;
    }
    
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getPriceRange() {
        return priceRange;
    }
    
    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
    
    public void addMenuItem(MenuItem menuItem) {
        if (this.menuItems == null) {
            this.menuItems = new ArrayList<>();
        }
        this.menuItems.add(menuItem);
    }
    
    /**
     * Inner class for menu items
     */
    public static class MenuItem {
        private int id;
        private String name;
        private String description;
        private double price;
        private int imageResourceId;
        private int quantity;
        
        public MenuItem(int id, String name, String description, double price, int imageResourceId) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageResourceId = imageResourceId;
            this.quantity = 0;
        }
        
        // Getters and Setters
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
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public double getPrice() {
            return price;
        }
        
        public void setPrice(double price) {
            this.price = price;
        }
        
        public int getImageResourceId() {
            return imageResourceId;
        }
        
        public void setImageResourceId(int imageResourceId) {
            this.imageResourceId = imageResourceId;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public void incrementQuantity() {
            this.quantity++;
        }
        
        public void decrementQuantity() {
            if (this.quantity > 0) {
                this.quantity--;
            }
        }
    }
}
