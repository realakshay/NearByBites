package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartManager {
    private static final String PREF_NAME = "CartPreferences";
    private static final String KEY_CART_ITEMS = "cartItems";
    
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;
    
    private static CartManager instance;
    
    private CartManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
    }
    
    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }
    
    public void addToCart(CartItem item) {
        List<CartItem> cartItems = getCartItems();
        
        // Check if item already exists in cart
        boolean itemExists = false;
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            if (cartItem.getItemId().equals(item.getItemId()) && 
                cartItem.getRestaurantId().equals(item.getRestaurantId())) {
                // Update quantity
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                itemExists = true;
                break;
            }
        }
        
        // If item doesn't exist, add it
        if (!itemExists) {
            // Generate a unique ID if not provided
            if (item.getId() == null || item.getId().isEmpty()) {
                item.setId(UUID.randomUUID().toString());
            }
            cartItems.add(item);
        }
        
        // Save updated cart
        saveCartItems(cartItems);
    }
    
    public void removeFromCart(String itemId) {
        List<CartItem> cartItems = getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getId().equals(itemId)) {
                cartItems.remove(i);
                break;
            }
        }
        saveCartItems(cartItems);
    }
    
    public void updateItemQuantity(String itemId, int quantity) {
        List<CartItem> cartItems = getCartItems();
        for (CartItem item : cartItems) {
            if (item.getId().equals(itemId)) {
                item.setQuantity(quantity);
                if (quantity <= 0) {
                    cartItems.remove(item);
                }
                break;
            }
        }
        saveCartItems(cartItems);
    }
    
    public void clearCart() {
        editor.remove(KEY_CART_ITEMS);
        editor.apply();
    }
    
    public List<CartItem> getCartItems() {
        String cartItemsJson = preferences.getString(KEY_CART_ITEMS, null);
        if (cartItemsJson != null) {
            Type type = new TypeToken<ArrayList<CartItem>>(){}.getType();
            return gson.fromJson(cartItemsJson, type);
        }
        return new ArrayList<>();
    }
    
    public int getCartItemCount() {
        List<CartItem> cartItems = getCartItems();
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }
    
    public double getCartTotal() {
        List<CartItem> cartItems = getCartItems();
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    
    private void saveCartItems(List<CartItem> cartItems) {
        String cartItemsJson = gson.toJson(cartItems);
        editor.putString(KEY_CART_ITEMS, cartItemsJson);
        editor.apply();
    }
}
