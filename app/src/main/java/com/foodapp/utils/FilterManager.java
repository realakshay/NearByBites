package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterManager {
    private static final String PREF_NAME = "filter_preferences";
    private static final String KEY_SELECTED_CATEGORIES = "selected_categories";
    private static final String KEY_SELECTED_SORT = "selected_sort";
    private static final String KEY_SELECTED_PRICE = "selected_price";
    private static final String KEY_FILTER_ACTIVE = "filter_active";
    
    private static FilterManager instance;
    private SharedPreferences preferences;
    private Gson gson;
    
    // Filter states
    private Set<String> selectedCategories;
    private String selectedSort;
    private Set<String> selectedPrices;
    private boolean isFilterActive;
    
    private FilterManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadFilterSettings();
    }
    
    public static synchronized FilterManager getInstance(Context context) {
        if (instance == null) {
            instance = new FilterManager(context.getApplicationContext());
        }
        return instance;
    }
    
    private void loadFilterSettings() {
        // Load selected categories
        String categoriesJson = preferences.getString(KEY_SELECTED_CATEGORIES, null);
        if (categoriesJson != null) {
            Type type = new TypeToken<Set<String>>() {}.getType();
            selectedCategories = gson.fromJson(categoriesJson, type);
        } else {
            selectedCategories = new HashSet<>();
            selectedCategories.add("All"); // Default is "All"
        }
        
        // Load selected sort option
        selectedSort = preferences.getString(KEY_SELECTED_SORT, "");
        
        // Load selected price ranges
        String pricesJson = preferences.getString(KEY_SELECTED_PRICE, null);
        if (pricesJson != null) {
            Type type = new TypeToken<Set<String>>() {}.getType();
            selectedPrices = gson.fromJson(pricesJson, type);
        } else {
            selectedPrices = new HashSet<>();
        }
        
        // Load filter active state
        isFilterActive = preferences.getBoolean(KEY_FILTER_ACTIVE, false);
    }
    
    public void saveFilterSettings() {
        SharedPreferences.Editor editor = preferences.edit();
        
        // Save selected categories
        String categoriesJson = gson.toJson(selectedCategories);
        editor.putString(KEY_SELECTED_CATEGORIES, categoriesJson);
        
        // Save selected sort option
        editor.putString(KEY_SELECTED_SORT, selectedSort);
        
        // Save selected price ranges
        String pricesJson = gson.toJson(selectedPrices);
        editor.putString(KEY_SELECTED_PRICE, pricesJson);
        
        // Save filter active state
        editor.putBoolean(KEY_FILTER_ACTIVE, isFilterActive);
        
        editor.apply();
    }
    
    public void resetFilters() {
        selectedCategories = new HashSet<>();
        selectedCategories.add("All");
        selectedSort = "";
        selectedPrices = new HashSet<>();
        isFilterActive = false;
        saveFilterSettings();
    }
    
    public Set<String> getSelectedCategories() {
        return selectedCategories;
    }
    
    public void setSelectedCategories(Set<String> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }
    
    public String getSelectedSort() {
        return selectedSort;
    }
    
    public void setSelectedSort(String selectedSort) {
        this.selectedSort = selectedSort;
    }
    
    public Set<String> getSelectedPrices() {
        return selectedPrices;
    }
    
    public void setSelectedPrices(Set<String> selectedPrices) {
        this.selectedPrices = selectedPrices;
    }
    
    public boolean isFilterActive() {
        return isFilterActive;
    }
    
    public void setFilterActive(boolean filterActive) {
        isFilterActive = filterActive;
    }
    
    // Helper method to check if a category is selected
    public boolean isCategorySelected(String category) {
        return selectedCategories.contains(category) || selectedCategories.contains("All");
    }
    
    // Helper method to check if a sort option is selected
    public boolean isSortSelected(String sortOption) {
        return selectedSort.equals(sortOption);
    }
    
    // Helper method to check if a price range is selected
    public boolean isPriceSelected(String price) {
        return selectedPrices.contains(price);
    }
    
    // Helper method to add a category
    public void addCategory(String category) {
        // If adding a specific category, remove "All"
        if (!category.equals("All")) {
            selectedCategories.remove("All");
        } else {
            // If adding "All", remove all other categories
            selectedCategories.clear();
        }
        selectedCategories.add(category);
    }
    
    // Helper method to remove a category
    public void removeCategory(String category) {
        selectedCategories.remove(category);
        // If no categories selected, default to "All"
        if (selectedCategories.isEmpty()) {
            selectedCategories.add("All");
        }
    }
    
    // Helper method to toggle a category
    public void toggleCategory(String category) {
        if (isCategorySelected(category)) {
            removeCategory(category);
        } else {
            addCategory(category);
        }
    }
    
    // Helper method to toggle a price range
    public void togglePrice(String price) {
        if (selectedPrices.contains(price)) {
            selectedPrices.remove(price);
        } else {
            selectedPrices.add(price);
        }
    }
}
