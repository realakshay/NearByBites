package com.foodapp.models;

/**
 * Model class to represent a menu item
 */
public class MenuItem {
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
    
    public double getTotalPrice() {
        return this.price * this.quantity;
    }
}
