package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Singleton class to manage filters throughout the app
 */
public class FilterManager {
    
    private static FilterManager instance;
    
    private static final String PREF_FILTER = "grub_filter_preferences";
    private static final String KEY_FILTER_ACTIVE = "filter_active";
    private static final String KEY_SELECTED_CATEGORIES = "selected_categories";
    private static final String KEY_SELECTED_SORT = "selected_sort";
    private static final String KEY_SELECTED_PRICE_RANGE = "selected_price_range";
    
    private final SharedPreferences filterPref;
    private final SharedPreferences.Editor filterEditor;
    private final Gson gson;
    
    private boolean isFilterActive = false;
    private Set<String> selectedCategories = new HashSet<>();
    private String selectedSort = "Default";
    private String selectedPriceRange = "All";
    
    public static synchronized FilterManager getInstance(Context context) {
        if (instance == null) {
            instance = new FilterManager(context);
        }
        return instance;
    }
    
    private FilterManager(Context context) {
        filterPref = context.getSharedPreferences(PREF_FILTER, Context.MODE_PRIVATE);
        filterEditor = filterPref.edit();
        gson = new Gson();
        
        // Load saved filter settings
        loadFilterSettings();
    }
    
    private void loadFilterSettings() {
        isFilterActive = filterPref.getBoolean(KEY_FILTER_ACTIVE, false);
        
        String categoriesJson = filterPref.getString(KEY_SELECTED_CATEGORIES, null);
        if (categoriesJson != null) {
            Type type = new TypeToken<Set<String>>(){}.getType();
            selectedCategories = gson.fromJson(categoriesJson, type);
        }
        
        selectedSort = filterPref.getString(KEY_SELECTED_SORT, "Default");
        selectedPriceRange = filterPref.getString(KEY_SELECTED_PRICE_RANGE, "All");
    }
    
    public void saveFilterSettings() {
        filterEditor.putBoolean(KEY_FILTER_ACTIVE, isFilterActive);
        
        String categoriesJson = gson.toJson(selectedCategories);
        filterEditor.putString(KEY_SELECTED_CATEGORIES, categoriesJson);
        
        filterEditor.putString(KEY_SELECTED_SORT, selectedSort);
        filterEditor.putString(KEY_SELECTED_PRICE_RANGE, selectedPriceRange);
        
        filterEditor.apply();
    }
    
    public void resetFilters() {
        isFilterActive = false;
        selectedCategories.clear();
        selectedSort = "Default";
        selectedPriceRange = "All";
    }
    
    // Getters and Setters
    public boolean isFilterActive() {
        return isFilterActive;
    }
    
    public void setFilterActive(boolean filterActive) {
        isFilterActive = filterActive;
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
    
    public String getSelectedPriceRange() {
        return selectedPriceRange;
    }
    
    public void setSelectedPriceRange(String selectedPriceRange) {
        this.selectedPriceRange = selectedPriceRange;
    }
}
