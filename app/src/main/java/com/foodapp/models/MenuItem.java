package com.foodapp.models;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private boolean isRecommended;
    private boolean isPopular;
    
    public MenuItem(int id, String name, String description, double price, String imageUrl, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.isRecommended = false;
        this.isPopular = false;
    }
    
    public MenuItem(int id, String name, String description, double price, String imageUrl, String category, boolean isRecommended, boolean isPopular) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.isRecommended = isRecommended;
        this.isPopular = isPopular;
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
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public boolean isRecommended() {
        return isRecommended;
    }
    
    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }
    
    public boolean isPopular() {
        return isPopular;
    }
    
    public void setPopular(boolean popular) {
        isPopular = popular;
    }
}
