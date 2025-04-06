package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.CartItem;
import com.foodapp.models.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the shopping cart
 */
public class CartManager {
    
    private static final String PREF_NAME = "cart_prefs";
    private static final String KEY_CART_ITEMS = "cart_items";
    
    private static CartManager instance;
    private SharedPreferences preferences;
    private Map<Integer, MenuItem> cartItems;
    
    private CartManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadCartItems();
    }
    
    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }
    
    private void loadCartItems() {
        String cartJson = preferences.getString(KEY_CART_ITEMS, null);
        if (cartJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<Integer, MenuItem>>() {}.getType();
            cartItems = gson.fromJson(cartJson, type);
        }
        
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
    }
    
    private void saveCartItems() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String cartJson = gson.toJson(cartItems);
        editor.putString(KEY_CART_ITEMS, cartJson);
        editor.apply();
    }
    
    public void addItem(MenuItem menuItem) {
        int itemId = menuItem.getId();
        if (cartItems.containsKey(itemId)) {
            MenuItem existingItem = cartItems.get(itemId);
            existingItem.setQuantity(menuItem.getQuantity());
        } else {
            cartItems.put(itemId, menuItem);
        }
        saveCartItems();
    }
    
    // For compatibility with DataGenerator
    public void addItemToCart(MenuItem menuItem, int quantity) {
        menuItem.setQuantity(quantity);
        addItem(menuItem);
    }
    
    public void removeItem(int itemId) {
        cartItems.remove(itemId);
        saveCartItems();
    }
    
    public void updateItemQuantity(int itemId, int quantity) {
        if (cartItems.containsKey(itemId)) {
            MenuItem item = cartItems.get(itemId);
            item.setQuantity(quantity);
            if (quantity <= 0) {
                cartItems.remove(itemId);
            }
            saveCartItems();
        }
    }
    
    public int getItemQuantity(int itemId) {
        if (cartItems.containsKey(itemId)) {
            return cartItems.get(itemId).getQuantity();
        }
        return 0;
    }
    
    public List<MenuItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }
    
    public int getTotalItems() {
        int total = 0;
        for (MenuItem item : cartItems.values()) {
            total += item.getQuantity();
        }
        return total;
    }
    
    public double getSubtotal() {
        double subtotal = 0;
        for (MenuItem item : cartItems.values()) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }
    
    public double getDeliveryFee() {
        // Simple calculation: $2 base fee + 5% of subtotal
        return 2.0 + (getSubtotal() * 0.05);
    }
    
    public double getTax() {
        // Simple tax calculation: 8% of subtotal
        return getSubtotal() * 0.08;
    }
    
    public double getTotal() {
        return getSubtotal() + getDeliveryFee() + getTax();
    }
    
    // Method to match what is used in OrderSuccessActivity
    public double getCartTotal() {
        return getSubtotal();
    }
    
    public void clearCart() {
        cartItems.clear();
        saveCartItems();
    }
    
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
    
    // For compatibility with OrderSuccessActivity which expects CartItem objects
    public List<CartItem> getCartItemsAsCartItems() {
        List<CartItem> cartItemList = new ArrayList<>();
        for (MenuItem item : getCartItems()) {
            cartItemList.add(new CartItem(item));
        }
        return cartItemList;
    }
    
    // Aliased method name for HomeActivity
    public int getItemCount() {
        return getTotalItems();
    }

    public void removeItemFromCart(int itemId){
        // Remove item from cart
        if (cartItems.containsKey(itemId)) {
            cartItems.remove(itemId);
            saveCartItems();
        }
    }
}
