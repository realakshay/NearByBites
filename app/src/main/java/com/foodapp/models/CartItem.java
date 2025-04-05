package com.foodapp.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String id;
    private String restaurantId;
    private String itemId;
    private String name;
    private double price;
    private int quantity;
    private String image;

    public CartItem() {
        // Empty constructor required for Serializable
    }

    public CartItem(String id, String restaurantId, String itemId, String name, double price, int quantity, String image) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
