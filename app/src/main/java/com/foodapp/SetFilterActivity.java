package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.utils.FilterManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SetFilterActivity extends AppCompatActivity {

    private ImageView ivBack;
    private View categoryRice, categoryPizza, categoryDonut, categoryChicken;
    private View categorySteak, categoryMeal, categoryKebab, categoryAll;
    private View sortFastDelivery, sortNearYou, sortTrending, sortPopular;
    private View priceOne, priceTwo, priceThree, priceFour;
    private Button btnResetFilters, btnApplyFilter;
    
    private FilterManager filterManager;
    
    // Maps to store views for easier management
    private Map<String, View> categoryViews = new HashMap<>();
    private Map<String, View> sortViews = new HashMap<>();
    private Map<String, View> priceViews = new HashMap<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_filter);
        
        // Initialize FilterManager
        filterManager = FilterManager.getInstance();
        
        // Initialize views
        initViews();
        
        // Set up view maps for easier management
        setupViewMaps();
        
        // Set up initial state based on saved filters
        updateUIFromFilterSettings();
        
        // Set up click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        
        // Category views
        categoryRice = findViewById(R.id.categoryRice);
        categoryPizza = findViewById(R.id.categoryPizza);
        categoryDonut = findViewById(R.id.categoryDonut);
        categoryChicken = findViewById(R.id.categoryChicken);
        categorySteak = findViewById(R.id.categorySteak);
        categoryMeal = findViewById(R.id.categoryMeal);
        categoryKebab = findViewById(R.id.categoryKebab);
        categoryAll = findViewById(R.id.categoryAll);
        
        // Sort views
        sortFastDelivery = findViewById(R.id.sortFastDelivery);
        sortNearYou = findViewById(R.id.sortNearYou);
        sortTrending = findViewById(R.id.sortTrending);
        sortPopular = findViewById(R.id.sortPopular);
        
        // Price views
        priceOne = findViewById(R.id.priceOne);
        priceTwo = findViewById(R.id.priceTwo);
        priceThree = findViewById(R.id.priceThree);
        priceFour = findViewById(R.id.priceFour);
        
        // Buttons
        btnResetFilters = findViewById(R.id.btnResetFilters);
        btnApplyFilter = findViewById(R.id.btnApplyFilter);
    }
    
    private void setupViewMaps() {
        // Category views
        categoryViews.put("Rice", categoryRice);
        categoryViews.put("Pizza", categoryPizza);
        categoryViews.put("Donut", categoryDonut);
        categoryViews.put("Chicken", categoryChicken);
        categoryViews.put("Steak", categorySteak);
        categoryViews.put("Meal", categoryMeal);
        categoryViews.put("Kebab", categoryKebab);
        categoryViews.put("All", categoryAll);
        
        // Sort views
        sortViews.put("Fast Delivery", sortFastDelivery);
        sortViews.put("Near You", sortNearYou);
        sortViews.put("Trending", sortTrending);
        sortViews.put("Popular", sortPopular);
        
        // Price views
        priceViews.put("$", priceOne);
        priceViews.put("$$", priceTwo);
        priceViews.put("$$$", priceThree);
        priceViews.put("$$$$", priceFour);
    }
    
    private void updateUIFromFilterSettings() {
        // Update category selection
        for (Map.Entry<String, View> entry : categoryViews.entrySet()) {
            boolean isSelected = filterManager.isCategorySelected(entry.getKey());
            updateViewSelectionState(entry.getValue(), isSelected);
        }
        
        // Update sort selection
        for (Map.Entry<String, View> entry : sortViews.entrySet()) {
            boolean isSelected = filterManager.isSortSelected(entry.getKey());
            updateViewSelectionState(entry.getValue(), isSelected);
        }
        
        // Update price selection
        for (Map.Entry<String, View> entry : priceViews.entrySet()) {
            boolean isSelected = filterManager.isPriceSelected(entry.getKey());
            updateViewSelectionState(entry.getValue(), isSelected);
        }
    }
    
    private void setupClickListeners() {
        // Back button
        ivBack.setOnClickListener(v -> finish());
        
        // Category click listeners
        for (Map.Entry<String, View> entry : categoryViews.entrySet()) {
            final String category = entry.getKey();
            View view = entry.getValue();
            
            view.setOnClickListener(v -> {
                // Special handling for "All" category
                if (category.equals("All")) {
                    // Reset all categories and select only "All"
                    filterManager.setSelectedCategories(new HashSet<String>() {{ add("All"); }});
                } else {
                    filterManager.toggleCategory(category);
                }
                
                // Update UI
                updateUIFromFilterSettings();
            });
        }
        
        // Sort click listeners
        for (Map.Entry<String, View> entry : sortViews.entrySet()) {
            final String sort = entry.getKey();
            View view = entry.getValue();
            
            view.setOnClickListener(v -> {
                // Toggle sort option (only one can be selected at a time)
                if (filterManager.isSortSelected(sort)) {
                    filterManager.setSelectedSort("");
                } else {
                    filterManager.setSelectedSort(sort);
                }
                
                // Update UI
                updateUIFromFilterSettings();
            });
        }
        
        // Price click listeners
        for (Map.Entry<String, View> entry : priceViews.entrySet()) {
            final String price = entry.getKey();
            View view = entry.getValue();
            
            view.setOnClickListener(v -> {
                // Toggle price range (multiple can be selected)
                filterManager.togglePrice(price);
                
                // Update UI
                updateUIFromFilterSettings();
            });
        }
        
        // Reset Filters button
        btnResetFilters.setOnClickListener(v -> {
            // Reset filters
            filterManager.resetFilters();
            
            // Update UI
            updateUIFromFilterSettings();
        });
        
        // Apply Filter button
        btnApplyFilter.setOnClickListener(v -> {
            // Save filter settings
            filterManager.setFilterActive(true);
            filterManager.saveFilterSettings();
            
            // Navigate to RestaurantsListActivity
            Intent intent = new Intent(SetFilterActivity.this, RestaurantsListActivity.class);
            startActivity(intent);
            
            // Finish this activity
            finish();
        });
    }
    
    private void updateViewSelectionState(View view, boolean isSelected) {
        view.setBackgroundResource(isSelected ? 
                R.drawable.filter_item_selected_background : 
                R.drawable.filter_item_background);
    }
}
