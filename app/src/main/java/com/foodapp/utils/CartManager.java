package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.CartItem;
import com.foodapp.models.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "cart_preferences";
    private static final String CART_ITEMS_KEY = "cart_items";
    
    private static CartManager instance;
    private SharedPreferences preferences;
    private List<CartItem> cartItems;
    private List<CartUpdateListener> listeners = new ArrayList<>();
    
    public interface CartUpdateListener {
        void onCartUpdated();
    }
    
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
        Gson gson = new Gson();
        String json = preferences.getString(CART_ITEMS_KEY, null);
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        
        if (json != null) {
            cartItems = gson.fromJson(json, type);
        } else {
            cartItems = new ArrayList<>();
        }
    }
    
    private void saveCartItems() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(CART_ITEMS_KEY, json);
        editor.apply();
        
        // Notify listeners
        notifyCartUpdated();
    }
    
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public void addToCart(MenuItem menuItem, int quantity) {
        // Check if the item is already in the cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getMenuItem().getId() == menuItem.getId()) {
                // Update quantity
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                saveCartItems();
                return;
            }
        }
        
        // Add new item to cart
        CartItem newItem = new CartItem(menuItem, quantity);
        cartItems.add(newItem);
        saveCartItems();
    }
    
    public void updateCartItemQuantity(int menuItemId, int newQuantity) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getMenuItem().getId() == menuItemId) {
                if (newQuantity <= 0) {
                    // Remove item if quantity is 0 or less
                    removeFromCart(menuItemId);
                } else {
                    // Update quantity
                    cartItem.setQuantity(newQuantity);
                    saveCartItems();
                }
                return;
            }
        }
    }
    
    public void removeFromCart(int menuItemId) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getMenuItem().getId() == menuItemId) {
                cartItems.remove(i);
                saveCartItems();
                return;
            }
        }
    }
    
    public void clearCart() {
        cartItems.clear();
        saveCartItems();
    }
    
    public int getCartItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }
    
    public double getCartTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getMenuItem().getPrice() * item.getQuantity();
        }
        return total;
    }
    
    public int getItemQuantity(int menuItemId) {
        for (CartItem item : cartItems) {
            if (item.getMenuItem().getId() == menuItemId) {
                return item.getQuantity();
            }
        }
        return 0;
    }
    
    public void addCartUpdateListener(CartUpdateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void removeCartUpdateListener(CartUpdateListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyCartUpdated() {
        for (CartUpdateListener listener : listeners) {
            listener.onCartUpdated();
        }
    }
}
