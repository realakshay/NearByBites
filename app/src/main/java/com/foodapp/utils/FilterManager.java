package com.foodapp.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages filter preferences for restaurant searches
 */
public class FilterManager {
    private static FilterManager instance;
    
    private Set<String> selectedCategories;
    private Set<String> selectedCuisines;
    private Set<String> selectedPrices;
    private float maxDistance;
    private int maxDeliveryTime;
    private float minRating;
    private boolean filterApplied;
    
    private FilterManager() {
        selectedCategories = new HashSet<>();
        selectedCuisines = new HashSet<>();
        selectedPrices = new HashSet<>();
        maxDistance = 5.0f; // Default 5 miles/km
        maxDeliveryTime = 60; // Default 60 minutes
        minRating = 0.0f; // Default any rating
        filterApplied = false;
    }
    
    public static synchronized FilterManager getInstance() {
        if (instance == null) {
            instance = new FilterManager();
        }
        return instance;
    }
    
    public void toggleCategory(String category) {
        if (selectedCategories.contains(category)) {
            selectedCategories.remove(category);
        } else {
            selectedCategories.add(category);
        }
        filterApplied = true;
    }
    
    public void toggleCuisine(String cuisine) {
        if (selectedCuisines.contains(cuisine)) {
            selectedCuisines.remove(cuisine);
        } else {
            selectedCuisines.add(cuisine);
        }
        filterApplied = true;
    }
    
    public void togglePrice(String price) {
        if (selectedPrices.contains(price)) {
            selectedPrices.remove(price);
        } else {
            selectedPrices.add(price);
        }
        filterApplied = true;
    }
    
    public void setMaxDistance(float distance) {
        this.maxDistance = distance;
        filterApplied = true;
    }
    
    public void setMaxDeliveryTime(int minutes) {
        this.maxDeliveryTime = minutes;
        filterApplied = true;
    }
    
    public void setMinRating(float rating) {
        this.minRating = rating;
        filterApplied = true;
    }
    
    public void clearFilters() {
        selectedCategories.clear();
        selectedCuisines.clear();
        selectedPrices.clear();
        maxDistance = 5.0f;
        maxDeliveryTime = 60;
        minRating = 0.0f;
        filterApplied = false;
    }
    
    public Set<String> getSelectedCategories() {
        return selectedCategories;
    }
    
    public Set<String> getSelectedCuisines() {
        return selectedCuisines;
    }
    
    public Set<String> getSelectedPrices() {
        return selectedPrices;
    }
    
    public float getMaxDistance() {
        return maxDistance;
    }
    
    public int getMaxDeliveryTime() {
        return maxDeliveryTime;
    }
    
    public float getMinRating() {
        return minRating;
    }
    
    public boolean isFilterApplied() {
        return filterApplied;
    }
}
