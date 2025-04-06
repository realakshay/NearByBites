package com.foodapp.models;

import java.io.Serializable;

public class PromoItem implements Serializable {
    private int id;
    private String title;
    private String description;
    private String code;
    private double discount;
    private int imageResourceId;
    private String imageUrl;
    private String expiryDate;

    // Constructor with resource ID
    public PromoItem(int id, String title, String description, String code, double discount, int imageResourceId, String expiryDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.code = code;
        this.discount = discount;
        this.imageResourceId = imageResourceId;
        this.expiryDate = expiryDate;
    }

    // Constructor with image URL
    public PromoItem(int id, String title, String description, String code, double discount, String imageUrl, String expiryDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.code = code;
        this.discount = discount;
        this.imageUrl = imageUrl;
        this.expiryDate = expiryDate;
    }

    public PromoItem(int id, String title, String description, double discount, String code,  int imageResourceId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.code = code;
        this.discount = discount;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
