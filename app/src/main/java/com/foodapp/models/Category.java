package com.foodapp.models;

import android.graphics.drawable.Drawable;

public class Category {
    private int id;
    private String name;
    private Drawable icon;

    public Category(int id, String name, Drawable icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
