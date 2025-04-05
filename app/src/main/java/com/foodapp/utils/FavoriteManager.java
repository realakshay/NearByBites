package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteManager {
    private static final String PREF_NAME = "favorite_preferences";
    private static final String FAVORITE_RESTAURANTS_KEY = "favorite_restaurants";
    private static final String FAVORITE_IDS_KEY = "favorite_ids";
    
    private static FavoriteManager instance;
    private SharedPreferences preferences;
    private List<Restaurant> favoriteRestaurants;
    private Set<Integer> favoriteIds;
    
    private FavoriteManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadFavorites();
    }
    
    public static synchronized FavoriteManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteManager(context.getApplicationContext());
        }
        return instance;
    }
    
    private void loadFavorites() {
        // Load favorite restaurant IDs
        Set<String> favoriteIdStrings = preferences.getStringSet(FAVORITE_IDS_KEY, new HashSet<>());
        favoriteIds = new HashSet<>();
        
        for (String idString : favoriteIdStrings) {
            try {
                favoriteIds.add(Integer.parseInt(idString));
            } catch (NumberFormatException e) {
                // Skip invalid IDs
            }
        }
        
        // Load favorite restaurants
        Gson gson = new Gson();
        String json = preferences.getString(FAVORITE_RESTAURANTS_KEY, null);
        Type type = new TypeToken<ArrayList<Restaurant>>() {}.getType();
        
        if (json != null) {
            favoriteRestaurants = gson.fromJson(json, type);
        } else {
            favoriteRestaurants = new ArrayList<>();
        }
    }
    
    private void saveFavorites() {
        SharedPreferences.Editor editor = preferences.edit();
        
        // Save favorite restaurant IDs
        Set<String> favoriteIdStrings = new HashSet<>();
        for (Integer id : favoriteIds) {
            favoriteIdStrings.add(id.toString());
        }
        editor.putStringSet(FAVORITE_IDS_KEY, favoriteIdStrings);
        
        // Save favorite restaurants
        Gson gson = new Gson();
        String json = gson.toJson(favoriteRestaurants);
        editor.putString(FAVORITE_RESTAURANTS_KEY, json);
        
        editor.apply();
    }
    
    public List<Restaurant> getFavoriteRestaurants() {
        return favoriteRestaurants;
    }
    
    public boolean isRestaurantFavorite(int restaurantId) {
        return favoriteIds.contains(restaurantId);
    }
    
    public void addFavoriteRestaurant(Restaurant restaurant) {
        // Check if already in favorites
        if (isRestaurantFavorite(restaurant.getId())) {
            return;
        }
        
        // Add to favorites
        favoriteIds.add(restaurant.getId());
        favoriteRestaurants.add(restaurant);
        saveFavorites();
    }
    
    public void removeFavoriteRestaurant(int restaurantId) {
        // Remove from favorite IDs
        favoriteIds.remove(restaurantId);
        
        // Remove from favorite restaurants
        for (int i = 0; i < favoriteRestaurants.size(); i++) {
            if (favoriteRestaurants.get(i).getId() == restaurantId) {
                favoriteRestaurants.remove(i);
                break;
            }
        }
        
        saveFavorites();
    }
    
    public void toggleFavorite(Restaurant restaurant) {
        if (isRestaurantFavorite(restaurant.getId())) {
            removeFavoriteRestaurant(restaurant.getId());
        } else {
            addFavoriteRestaurant(restaurant);
        }
    }
}
