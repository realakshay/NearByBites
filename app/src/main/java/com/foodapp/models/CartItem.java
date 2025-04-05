package com.foodapp.models;

import java.io.Serializable;

// Compatibility class for OrderSuccessActivity that expects CartItem
public class CartItem implements Serializable {
    
    private MenuItem menuItem;
    
    public CartItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
    
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public int getQuantity() {
        return menuItem.getQuantity();
    }
    
    public void setQuantity(int quantity) {
        menuItem.setQuantity(quantity);
    }
}
