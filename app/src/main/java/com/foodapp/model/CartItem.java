package com.foodapp.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private MenuItem menuItem;
    private int quantity;
    private int restaurantId;
    private String restaurantName;

    public CartItem() {
        this.quantity = 1;
    }

    public CartItem(MenuItem menuItem, int restaurantId, String restaurantName) {
        this.menuItem = menuItem;
        this.quantity = 1;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CartItem cartItem = (CartItem) obj;
        return menuItem.getId() == cartItem.menuItem.getId();
    }
}
