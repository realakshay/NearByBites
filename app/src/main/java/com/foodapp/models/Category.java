package com.foodapp.models;

/**
 * Model class to represent a food category
 */
public class Category {
    private int id;
    private String name;
    private int imageResourceId;
    private String filterKey;
    
    public Category(int id, String name, int imageResourceId, String filterKey) {
        this.id = id;
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.filterKey = filterKey;
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
    
    public int getImageResourceId() {
        return imageResourceId;
    }
    
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    
    public String getFilterKey() {
        return filterKey;
    }
    
    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }
}
