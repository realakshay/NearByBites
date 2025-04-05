package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.CartItem;
import com.foodapp.models.Order;
import com.foodapp.models.PaymentMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static final String PREF_NAME = "order_preferences";
    private static final String ORDERS_KEY = "orders";
    
    private static OrderManager instance;
    private SharedPreferences preferences;
    private List<Order> orders;
    
    private OrderManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadOrders();
    }
    
    public static synchronized OrderManager getInstance(Context context) {
        if (instance == null) {
            instance = new OrderManager(context.getApplicationContext());
        }
        return instance;
    }
    
    private void loadOrders() {
        Gson gson = new Gson();
        String json = preferences.getString(ORDERS_KEY, null);
        Type type = new TypeToken<ArrayList<Order>>() {}.getType();
        
        if (json != null) {
            orders = gson.fromJson(json, type);
        } else {
            orders = new ArrayList<>();
        }
    }
    
    private void saveOrders() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        editor.putString(ORDERS_KEY, json);
        editor.apply();
    }
    
    public List<Order> getOrders() {
        return orders;
    }
    
    public Order getOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    
    public void createOrder(String orderId, List<CartItem> items, PaymentMethod paymentMethod, 
                            double subtotal, double discount, double deliveryFee, double total,
                            String promoCode, int discountPercentage) {
        Order order = new Order(orderId, items, paymentMethod, subtotal, discount, deliveryFee, 
                total, promoCode, discountPercentage, System.currentTimeMillis());
        orders.add(order);
        saveOrders();
    }
    
    public void createSampleOrder(String status, String restaurantName, String orderId, 
                                String deliveryAddress, long timestamp) {
        Order order = new Order(orderId);
        order.setStatus(status);
        order.setRestaurantName(restaurantName);
        order.setDeliveryAddress(deliveryAddress);
        order.setTimestamp(timestamp);
        
        orders.add(order);
        saveOrders();
    }
    
    public void updateOrderStatus(String orderId, String status) {
        Order order = getOrder(orderId);
        if (order != null) {
            order.setStatus(status);
            saveOrders();
        }
    }
}
