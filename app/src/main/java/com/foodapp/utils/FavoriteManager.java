package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the user's favorite restaurants
 */
public class FavoriteManager {
    
    private static final String PREF_NAME = "favorite_prefs";
    private static final String KEY_FAVORITE_IDS = "favorite_ids";
    
    private static FavoriteManager instance;
    private SharedPreferences preferences;
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
        String favoritesJson = preferences.getString(KEY_FAVORITE_IDS, null);
        if (favoritesJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<Set<Integer>>() {}.getType();
            favoriteIds = gson.fromJson(favoritesJson, type);
        }
        
        if (favoriteIds == null) {
            favoriteIds = new HashSet<>();
        }
    }
    
    private void saveFavorites() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String favoritesJson = gson.toJson(favoriteIds);
        editor.putString(KEY_FAVORITE_IDS, favoritesJson);
        editor.apply();
    }
    
    public boolean isFavorite(int restaurantId) {
        return favoriteIds.contains(restaurantId);
    }
    
    public void addFavorite(int restaurantId) {
        favoriteIds.add(restaurantId);
        saveFavorites();
    }
    
    public void removeFavorite(int restaurantId) {
        favoriteIds.remove(restaurantId);
        saveFavorites();
    }
    
    public void toggleFavorite(int restaurantId) {
        if (isFavorite(restaurantId)) {
            removeFavorite(restaurantId);
        } else {
            addFavorite(restaurantId);
        }
    }
    
    public Set<Integer> getFavoriteIds() {
        return new HashSet<>(favoriteIds);
    }
    
    public void clearFavorites() {
        favoriteIds.clear();
        saveFavorites();
    }
}
