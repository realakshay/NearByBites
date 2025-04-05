package com.foodapp.models;

/**
 * Model class to represent a promotional item in a restaurant
 */
public class PromoItem {
    private int id;
    private String title;
    private String description;
    private int imageResourceId;
    
    public PromoItem(int id, String title, String description, int imageResourceId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getImageResourceId() {
        return imageResourceId;
    }
    
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
