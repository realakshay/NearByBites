package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.PaymentMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage payment methods
 */
public class PaymentManager {
    
    private static PaymentManager instance;
    
    private static final String PREF_PAYMENT = "grub_payment_preferences";
    private static final String KEY_PAYMENT_METHODS = "payment_methods";
    
    private final SharedPreferences paymentPref;
    private final SharedPreferences.Editor paymentEditor;
    private final Gson gson;
    
    private List<PaymentMethod> paymentMethods;
    
    public static synchronized PaymentManager getInstance(Context context) {
        if (instance == null) {
            instance = new PaymentManager(context);
        }
        return instance;
    }
    
    private PaymentManager(Context context) {
        paymentPref = context.getSharedPreferences(PREF_PAYMENT, Context.MODE_PRIVATE);
        paymentEditor = paymentPref.edit();
        gson = new Gson();
        
        // Load payment methods
        loadPaymentMethods();
    }
    
    private void loadPaymentMethods() {
        String json = paymentPref.getString(KEY_PAYMENT_METHODS, null);
        
        if (json != null) {
            try {
                Type type = new TypeToken<List<PaymentMethod>>(){}.getType();
                paymentMethods = gson.fromJson(json, type);
            } catch (Exception e) {
                paymentMethods = new ArrayList<>();
            }
        } else {
            paymentMethods = new ArrayList<>();
            
            // Add a demo payment method
            PaymentMethod demoPayment = new PaymentMethod(
                    "4242424242424242",
                    "John Doe",
                    "12",
                    "2028",
                    "123",
                    "visa"
            );
            paymentMethods.add(demoPayment);
            
            // Save to preferences
            savePaymentMethods();
        }
    }
    
    private void savePaymentMethods() {
        String json = gson.toJson(paymentMethods);
        paymentEditor.putString(KEY_PAYMENT_METHODS, json);
        paymentEditor.apply();
    }
    
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
    
    public void addPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethods.add(paymentMethod);
        savePaymentMethods();
    }
    
    public void removePaymentMethod(PaymentMethod paymentMethod) {
        paymentMethods.remove(paymentMethod);
        savePaymentMethods();
    }
    
    public PaymentMethod getPaymentMethodByCardNumber(String cardNumber) {
        for (PaymentMethod paymentMethod : paymentMethods) {
            if (paymentMethod.getCardNumber().equals(cardNumber)) {
                return paymentMethod;
            }
        }
        return null;
    }
}
