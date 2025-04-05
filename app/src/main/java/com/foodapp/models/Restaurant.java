package com.foodapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String address;
    private String cuisineType;
    private float rating;
    private int deliveryTimeMinutes;
    private float deliveryDistance;
    private int imageResourceId;
    private boolean isFavorite;
    private List<MenuItem> menuItems;
    private List<PromoItem> promoItems;

    public Restaurant(int id, String name, String address, String cuisineType, float rating, 
                     int deliveryTimeMinutes, float deliveryDistance, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.deliveryTimeMinutes = deliveryTimeMinutes;
        this.deliveryDistance = deliveryDistance;
        this.imageResourceId = imageResourceId;
        this.isFavorite = false;
        this.menuItems = new ArrayList<>();
        this.promoItems = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public float getRating() {
        return rating;
    }

    public int getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }

    public float getDeliveryDistance() {
        return deliveryDistance;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public List<PromoItem> getPromoItems() {
        return promoItems;
    }

    public void addPromoItem(PromoItem promoItem) {
        promoItems.add(promoItem);
    }
}
