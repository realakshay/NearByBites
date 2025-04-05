package com.foodapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String cuisine;
    private String rating;
    private String deliveryTime;
    private String deliveryFee;
    private String distance = "2.5"; // Default distance in km
    private String category;
    private int imageResourceId;
    private String imageUrl;
    private List<MenuItem> menu;
    private List<PromoItem> promoItems;
    private boolean isFavorite;

    // Constructor with resource ID
    public Restaurant(int id, String name, String cuisine, String rating, String deliveryTime, String deliveryFee, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.imageResourceId = imageResourceId;
        this.menu = new ArrayList<>();
        this.promoItems = new ArrayList<>();
    }

    // Constructor with image URL
    public Restaurant(int id, String name, String cuisine, String rating, String deliveryTime, String deliveryFee, String imageUrl) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.imageUrl = imageUrl;
        this.menu = new ArrayList<>();
        this.promoItems = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public void addMenuItem(MenuItem menuItem) {
        if (this.menu == null) {
            this.menu = new ArrayList<>();
        }
        this.menu.add(menuItem);
    }

    public List<PromoItem> getPromoItems() {
        return promoItems;
    }

    public void setPromoItems(List<PromoItem> promoItems) {
        this.promoItems = promoItems;
    }

    public void addPromoItem(PromoItem promoItem) {
        if (this.promoItems == null) {
            this.promoItems = new ArrayList<>();
        }
        this.promoItems.add(promoItem);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
