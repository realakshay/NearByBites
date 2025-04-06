package com.foodapp.models;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int id;    
    private int restaurentId;
    private String name;
    private String description;
    private double price;
    private int imageResourceId;
    private String imageUrl;
    private int quantity;
    private String category;
    private boolean isVeg;
    private boolean isSpicy;

    // Constructor with resource ID
    public MenuItem(int id, int restaurentId, String name, String description, double price) {
        this.id = id;
        this.id = restaurentId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
    }

    // Constructor with resto ID
    public MenuItem(int id, int restaurentId, String name, String description, double price, String imageUrl, String category, boolean isVeg, boolean isSpicy) {
        this.id = id;
        this.id = restaurentId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.isVeg = isVeg;
        this.isSpicy = isSpicy;
    }

    // Constructor with resource ID
    public MenuItem(int id, String name, String description, double price, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.quantity = 0;
    }

    // Constructor with image URL
    public MenuItem(int id, String name, String description, double price, String imageUrl, boolean isVeg, boolean isSpicy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = 0;
        this.isVeg = isVeg;
        this.isSpicy = isSpicy;
    }

    // Constructor with category
    public MenuItem(int id, String name, String description, double price, int imageResourceId, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.category = category;
        this.quantity = 0;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
