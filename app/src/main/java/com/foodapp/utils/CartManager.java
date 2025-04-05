package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class to manage shopping cart
 */
public class CartManager {
    
    private static CartManager instance;
    
    private static final String PREF_CART = "grub_cart_preferences";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static final String KEY_CART_RESTAURANT = "cart_restaurant";
    
    private final SharedPreferences cartPref;
    private final SharedPreferences.Editor cartEditor;
    private final Gson gson;
    
    private Map<Integer, Restaurant.MenuItem> cartItems;
    private Restaurant cartRestaurant;
    
    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }
    
    private CartManager(Context context) {
        cartPref = context.getSharedPreferences(PREF_CART, Context.MODE_PRIVATE);
        cartEditor = cartPref.edit();
        gson = new Gson();
        
        // Load cart data
        loadCartData();
    }
    
    private void loadCartData() {
        String itemsJson = cartPref.getString(KEY_CART_ITEMS, null);
        String restaurantJson = cartPref.getString(KEY_CART_RESTAURANT, null);
        
        if (itemsJson != null) {
            try {
                Type type = new TypeToken<Map<Integer, Restaurant.MenuItem>>(){}.getType();
                cartItems = gson.fromJson(itemsJson, type);
            } catch (Exception e) {
                cartItems = new HashMap<>();
            }
        } else {
            cartItems = new HashMap<>();
        }
        
        if (restaurantJson != null) {
            try {
                cartRestaurant = gson.fromJson(restaurantJson, Restaurant.class);
            } catch (Exception e) {
                cartRestaurant = null;
            }
        } else {
            cartRestaurant = null;
        }
    }
    
    private void saveCartData() {
        String itemsJson = gson.toJson(cartItems);
        String restaurantJson = gson.toJson(cartRestaurant);
        
        cartEditor.putString(KEY_CART_ITEMS, itemsJson);
        cartEditor.putString(KEY_CART_RESTAURANT, restaurantJson);
        cartEditor.apply();
    }
    
    public void addToCart(Restaurant restaurant, Restaurant.MenuItem menuItem) {
        // If cart is empty, set the restaurant
        if (cartRestaurant == null) {
            cartRestaurant = restaurant;
        }
        
        // Don't allow items from different restaurants
        if (cartRestaurant.getId() != restaurant.getId()) {
            return;
        }
        
        // Add to cart or increment quantity
        if (cartItems.containsKey(menuItem.getId())) {
            Restaurant.MenuItem existingItem = cartItems.get(menuItem.getId());
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            menuItem.setQuantity(1);
            cartItems.put(menuItem.getId(), menuItem);
        }
        
        // Save cart data
        saveCartData();
    }
    
    public void updateCartItemQuantity(int menuItemId, int quantity) {
        if (cartItems.containsKey(menuItemId)) {
            if (quantity <= 0) {
                cartItems.remove(menuItemId);
            } else {
                Restaurant.MenuItem menuItem = cartItems.get(menuItemId);
                menuItem.setQuantity(quantity);
            }
            
            // Check if cart is now empty
            if (cartItems.isEmpty()) {
                cartRestaurant = null;
            }
            
            // Save cart data
            saveCartData();
        }
    }
    
    public void removeFromCart(int menuItemId) {
        cartItems.remove(menuItemId);
        
        // Check if cart is now empty
        if (cartItems.isEmpty()) {
            cartRestaurant = null;
        }
        
        // Save cart data
        saveCartData();
    }
    
    public void clearCart() {
        cartItems.clear();
        cartRestaurant = null;
        
        // Save cart data
        saveCartData();
    }
    
    public Map<Integer, Restaurant.MenuItem> getCartItems() {
        return cartItems;
    }
    
    public List<Restaurant.MenuItem> getCartItemsList() {
        return new ArrayList<>(cartItems.values());
    }
    
    public Restaurant getCartRestaurant() {
        return cartRestaurant;
    }
    
    public int getCartItemCount() {
        int count = 0;
        for (Restaurant.MenuItem menuItem : cartItems.values()) {
            count += menuItem.getQuantity();
        }
        return count;
    }
    
    public double getCartTotal() {
        double total = 0;
        for (Restaurant.MenuItem menuItem : cartItems.values()) {
            total += menuItem.getPrice() * menuItem.getQuantity();
        }
        return total;
    }
    
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}
