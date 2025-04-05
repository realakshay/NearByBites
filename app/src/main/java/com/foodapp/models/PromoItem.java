package com.foodapp.models;

import java.io.Serializable;

public class PromoItem implements Serializable {
    private int id;
    private String title;
    private String description;
    private double discount;
    private String code;
    private int imageResourceId;

    public PromoItem(int id, String title, String description, double discount, String code, int imageResourceId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.discount = discount;
        this.code = code;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscount() {
        return discount;
    }

    public String getCode() {
        return code;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
