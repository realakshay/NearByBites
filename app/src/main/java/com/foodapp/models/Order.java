package com.foodapp.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private List<CartItem> items;
    private PaymentMethod paymentMethod;
    private double subtotal;
    private double discount;
    private double deliveryFee;
    private double total;
    private String promoCode;
    private int discountPercentage;
    private long timestamp;
    private String status;
    private String restaurantName;
    private String deliveryAddress;
    
    public Order(String orderId, List<CartItem> items, PaymentMethod paymentMethod, 
                double subtotal, double discount, double deliveryFee, double total,
                String promoCode, int discountPercentage, long timestamp) {
        this.orderId = orderId;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.subtotal = subtotal;
        this.discount = discount;
        this.deliveryFee = deliveryFee;
        this.total = total;
        this.promoCode = promoCode;
        this.discountPercentage = discountPercentage;
        this.timestamp = timestamp;
        this.status = "Processing"; // Initial status
        
        // Determine restaurant name from first item
        if (items != null && !items.isEmpty()) {
            // In a real app, each item would have a restaurant name/ID
            // For this demo, we'll use a default
            this.restaurantName = "McDonald's";
        } else {
            this.restaurantName = "Restaurant";
        }
        
        // Set default delivery address
        this.deliveryAddress = "Home";
    }
    
    // Constructor for sample orders
    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.paymentMethod = null;
        this.subtotal = 0;
        this.discount = 0;
        this.deliveryFee = 0;
        this.total = 0;
        this.promoCode = "";
        this.discountPercentage = 0;
        this.timestamp = System.currentTimeMillis();
        this.status = "Processing";
        this.restaurantName = "Restaurant";
        this.deliveryAddress = "Home";
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public double getDeliveryFee() {
        return deliveryFee;
    }
    
    public double getTotal() {
        return total;
    }
    
    public String getPromoCode() {
        return promoCode;
    }
    
    public int getDiscountPercentage() {
        return discountPercentage;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
