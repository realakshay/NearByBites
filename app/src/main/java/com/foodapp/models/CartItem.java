package com.foodapp.models;

public class CartItem {
    private MenuItem menuItem;
    private int quantity;
    private String remarks;
    
    public CartItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.remarks = "";
    }
    
    public CartItem(MenuItem menuItem, int quantity, String remarks) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.remarks = remarks;
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
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }
}
