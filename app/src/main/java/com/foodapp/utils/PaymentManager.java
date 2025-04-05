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
    private static final String KEY_SELECTED_PAYMENT_METHOD_ID = "selected_payment_method_id";
    
    private final SharedPreferences paymentPref;
    private final SharedPreferences.Editor paymentEditor;
    private final Gson gson;
    
    private List<PaymentMethod> paymentMethods;
    private int selectedPaymentMethodId = -1;
    
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
        
        // Load selected payment method
        selectedPaymentMethodId = paymentPref.getInt(KEY_SELECTED_PAYMENT_METHOD_ID, -1);
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
            if (paymentMethod.getCardNumber() != null && paymentMethod.getCardNumber().equals(cardNumber)) {
                return paymentMethod;
            }
        }
        return null;
    }
    
    public PaymentMethod getPaymentMethodById(int id) {
        for (PaymentMethod paymentMethod : paymentMethods) {
            if (paymentMethod.getId() == id) {
                return paymentMethod;
            }
        }
        return null;
    }
    
    public void setSelectedPaymentMethodId(int id) {
        selectedPaymentMethodId = id;
        paymentEditor.putInt(KEY_SELECTED_PAYMENT_METHOD_ID, id);
        paymentEditor.apply();
    }
    
    public int getSelectedPaymentMethodId() {
        return selectedPaymentMethodId;
    }
    
    public PaymentMethod getSelectedPaymentMethod() {
        if (selectedPaymentMethodId != -1) {
            return getPaymentMethodById(selectedPaymentMethodId);
        } else if (!paymentMethods.isEmpty()) {
            // Return the first one if none is selected
            return paymentMethods.get(0);
        }
        return null;
    }
}
