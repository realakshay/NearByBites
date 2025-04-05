package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.PaymentMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaymentManager {
    private static final String PREF_NAME = "payment_preferences";
    private static final String PAYMENT_METHODS_KEY = "payment_methods";
    private static final String SELECTED_PAYMENT_METHOD_KEY = "selected_payment_method";
    
    private static PaymentManager instance;
    private SharedPreferences preferences;
    private List<PaymentMethod> paymentMethods;
    private int selectedPaymentMethodId = -1;
    
    private PaymentManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadPaymentMethods();
        selectedPaymentMethodId = preferences.getInt(SELECTED_PAYMENT_METHOD_KEY, -1);
    }
    
    public static synchronized PaymentManager getInstance(Context context) {
        if (instance == null) {
            instance = new PaymentManager(context.getApplicationContext());
        }
        return instance;
    }
    
    private void loadPaymentMethods() {
        Gson gson = new Gson();
        String json = preferences.getString(PAYMENT_METHODS_KEY, null);
        Type type = new TypeToken<ArrayList<PaymentMethod>>() {}.getType();
        
        if (json != null) {
            paymentMethods = gson.fromJson(json, type);
        } else {
            paymentMethods = new ArrayList<>();
        }
    }
    
    private void savePaymentMethods() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(paymentMethods);
        editor.putString(PAYMENT_METHODS_KEY, json);
        editor.apply();
    }
    
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
    
    public void addPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethods.add(paymentMethod);
        savePaymentMethods();
    }
    
    public void removePaymentMethod(int paymentMethodId) {
        for (int i = 0; i < paymentMethods.size(); i++) {
            if (paymentMethods.get(i).getId() == paymentMethodId) {
                paymentMethods.remove(i);
                savePaymentMethods();
                
                // If this was the selected payment method, clear selection
                if (selectedPaymentMethodId == paymentMethodId) {
                    setSelectedPaymentMethodId(-1);
                }
                
                return;
            }
        }
    }
    
    public void clearPaymentMethods() {
        paymentMethods.clear();
        savePaymentMethods();
        setSelectedPaymentMethodId(-1);
    }
    
    public int getSelectedPaymentMethodId() {
        return selectedPaymentMethodId;
    }
    
    public PaymentMethod getSelectedPaymentMethod() {
        if (selectedPaymentMethodId == -1) {
            return null;
        }
        
        for (PaymentMethod paymentMethod : paymentMethods) {
            if (paymentMethod.getId() == selectedPaymentMethodId) {
                return paymentMethod;
            }
        }
        
        return null;
    }
    
    public void setSelectedPaymentMethodId(int paymentMethodId) {
        selectedPaymentMethodId = paymentMethodId;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SELECTED_PAYMENT_METHOD_KEY, paymentMethodId);
        editor.apply();
    }
}
