package com.foodapp.util;

import com.foodapp.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;
    private int currentRestaurantId = -1;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(CartItem item) {
        // Check if we're ordering from a different restaurant
        if (currentRestaurantId != -1 && currentRestaurantId != item.getRestaurantId()) {
            // Clear cart if ordering from a different restaurant
            cartItems.clear();
            currentRestaurantId = item.getRestaurantId();
        } else if (currentRestaurantId == -1) {
            // First time adding to cart
            currentRestaurantId = item.getRestaurantId();
        }

        // Check if item already exists in cart
        int existingIndex = findCartItemIndex(item);
        if (existingIndex != -1) {
            // Item exists, increment quantity
            CartItem existingItem = cartItems.get(existingIndex);
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            // New item, add to cart
            cartItems.add(item);
        }
    }

    public void updateCartItemQuantity(CartItem item) {
        int index = findCartItemIndex(item);
        if (index != -1) {
            cartItems.get(index).setQuantity(item.getQuantity());
        }
    }

    public void removeFromCart(CartItem item) {
        cartItems.remove(item);
        if (cartItems.isEmpty()) {
            currentRestaurantId = -1;
        }
    }

    public void clearCart() {
        cartItems.clear();
        currentRestaurantId = -1;
    }

    private int findCartItemIndex(CartItem item) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getMenuItem().getId() == item.getMenuItem().getId()) {
                return i;
            }
        }
        return -1;
    }
}
