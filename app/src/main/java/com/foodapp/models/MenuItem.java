package com.foodapp.models;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int imageResourceId;

    public MenuItem(int id, String name, String description, double price, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
        this.imageResourceId = imageResourceId;
    }
    
    // Constructor with quantity
    public MenuItem(int id, String name, String description, double price, int quantity, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
