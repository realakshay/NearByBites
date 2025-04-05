package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.Address;
import com.foodapp.models.PaymentMethod;
import com.foodapp.utils.CartManager;
import com.foodapp.utils.PaymentManager;
import com.foodapp.utils.PreferenceManager;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private ImageView ivBack;
    private LinearLayout llAddressContainer;
    private LinearLayout llPaymentMethodsContainer;
    private TextView tvNoPaymentMethods;
    private Button btnAddPaymentMethod;
    private Button btnProceedToPayment;
    
    private CartManager cartManager;
    private PreferenceManager preferenceManager;
    private PaymentManager paymentManager;
    
    private Address selectedAddress;
    private PaymentMethod selectedPaymentMethod;

    // Constants for fees
    private static final double DELIVERY_FEE = 3.50;
    private double subtotal;
    private double discountAmount = 0.0;
    private String promoCode = "";
    private int discountPercentage = 0;
    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        
        // Initialize managers
        cartManager = CartManager.getInstance(this);
        preferenceManager = new PreferenceManager(this);
        paymentManager = PaymentManager.getInstance(this);
        
        // Calculate order totals
        calculateOrderTotals();
        
        // Initialize views
        initViews();
        
        // Set up back button
        ivBack.setOnClickListener(v -> finish());
        
        // Load delivery address
        loadDeliveryAddress();
        
        // Set up add payment method button
        btnAddPaymentMethod.setOnClickListener(v -> {
            // Navigate to AddCreditCardActivity
            Intent intent = new Intent(CheckoutActivity.this, AddCreditCardActivity.class);
            startActivity(intent);
        });
        
        // Set up proceed to payment button
        btnProceedToPayment.setOnClickListener(v -> {
            if (validateCheckout()) {
                proceedToPayment();
            }
        });
    }
    
    private void calculateOrderTotals() {
        // Get order details from intent (passed from CartActivity)
        if (getIntent().getExtras() != null) {
            subtotal = getIntent().getDoubleExtra("subtotal", 0);
            discountAmount = getIntent().getDoubleExtra("discount", 0);
            promoCode = getIntent().getStringExtra("promo_code");
            discountPercentage = getIntent().getIntExtra("discount_percentage", 0);
        } else {
            // If no details are passed, calculate them from cart
            subtotal = cartManager.getCartTotal();
            discountAmount = 0;
            promoCode = "";
            discountPercentage = 0;
        }
        
        // Calculate total
        total = subtotal - discountAmount + DELIVERY_FEE;
        if (total < DELIVERY_FEE) total = DELIVERY_FEE; // Ensure total is at least delivery fee
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Load payment methods when returning from AddCreditCardActivity
        loadPaymentMethods();
        
        // Update proceed to payment button state
        updateProceedToPaymentButtonState();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        llAddressContainer = findViewById(R.id.llAddressContainer);
        llPaymentMethodsContainer = findViewById(R.id.llPaymentMethodsContainer);
        tvNoPaymentMethods = findViewById(R.id.tvNoPaymentMethods);
        btnAddPaymentMethod = findViewById(R.id.btnAddPaymentMethod);
        btnProceedToPayment = findViewById(R.id.btnProceedToPayment);
    }
    
    private void loadDeliveryAddress() {
        // Clear previous address views
        llAddressContainer.removeAllViews();
        
        // Get selected address from preferences
        selectedAddress = preferenceManager.getSelectedAddress();
        
        if (selectedAddress != null) {
            // Inflate address view
            View addressView = getLayoutInflater().inflate(R.layout.item_address_checkout, llAddressContainer, false);
            
            // Set address data
            TextView tvAddressType = addressView.findViewById(R.id.tvAddressType);
            TextView tvAddressDetail = addressView.findViewById(R.id.tvAddressDetail);
            
            tvAddressType.setText(selectedAddress.getType().toUpperCase() + " ADDRESS");
            tvAddressDetail.setText(selectedAddress.getAddressLine());
            
            // Add to container
            llAddressContainer.addView(addressView);
        } else {
            // If no address is available, create a default home address
            selectedAddress = new Address(1, "Home", "123 Main St, New York, NY 10001");
            preferenceManager.saveSelectedAddress(selectedAddress);
            
            // Inflate address view
            View addressView = getLayoutInflater().inflate(R.layout.item_address_checkout, llAddressContainer, false);
            
            // Set address data
            TextView tvAddressType = addressView.findViewById(R.id.tvAddressType);
            TextView tvAddressDetail = addressView.findViewById(R.id.tvAddressDetail);
            
            tvAddressType.setText("HOME ADDRESS");
            tvAddressDetail.setText(selectedAddress.getAddressLine());
            
            // Add to container
            llAddressContainer.addView(addressView);
        }
    }
    
    private void loadPaymentMethods() {
        // Clear container
        llPaymentMethodsContainer.removeAllViews();
        
        // Get payment methods
        List<PaymentMethod> paymentMethods = paymentManager.getPaymentMethods();
        
        if (paymentMethods.isEmpty()) {
            // Show no payment methods message
            tvNoPaymentMethods.setVisibility(View.VISIBLE);
            llPaymentMethodsContainer.setVisibility(View.GONE);
            selectedPaymentMethod = null;
        } else {
            // Hide no payment methods message
            tvNoPaymentMethods.setVisibility(View.GONE);
            llPaymentMethodsContainer.setVisibility(View.VISIBLE);
            
            // Check if there's a saved selected payment method
            int savedPaymentMethodId = paymentManager.getSelectedPaymentMethodId();
            
            // If no saved payment method but we have payment methods, select the first one
            if (savedPaymentMethodId == -1 && !paymentMethods.isEmpty()) {
                savedPaymentMethodId = paymentMethods.get(0).getId();
                paymentManager.setSelectedPaymentMethodId(savedPaymentMethodId);
            }
            
            // Add payment methods to container
            for (PaymentMethod paymentMethod : paymentMethods) {
                addPaymentMethodView(paymentMethod, paymentMethod.getId() == savedPaymentMethodId);
                
                // Set the selected payment method
                if (paymentMethod.getId() == savedPaymentMethodId) {
                    selectedPaymentMethod = paymentMethod;
                }
            }
        }
        
        // Update proceed to payment button state
        updateProceedToPaymentButtonState();
    }
    
    private void addPaymentMethodView(PaymentMethod paymentMethod, boolean isSelected) {
        // Inflate payment method view
        View paymentMethodView = getLayoutInflater().inflate(R.layout.item_payment_method, llPaymentMethodsContainer, false);
        paymentMethodView.setTag(paymentMethod.getId());
        
        // Set payment method data
        ImageView ivPaymentIcon = paymentMethodView.findViewById(R.id.ivPaymentIcon);
        TextView tvPaymentType = paymentMethodView.findViewById(R.id.tvPaymentType);
        TextView tvPaymentDetail = paymentMethodView.findViewById(R.id.tvPaymentDetail);
        ImageView ivSelected = paymentMethodView.findViewById(R.id.ivSelected);
        
        // Set icon based on payment type
        if (paymentMethod.getType().equalsIgnoreCase("Credit Card")) {
            ivPaymentIcon.setImageResource(R.drawable.ic_credit_card);
        } else if (paymentMethod.getType().equalsIgnoreCase("PayPal")) {
            ivPaymentIcon.setImageResource(R.drawable.ic_paypal);
        }
        
        tvPaymentType.setText(paymentMethod.getType());
        tvPaymentDetail.setText(paymentMethod.getDetail());
        
        // Set selected state
        paymentMethodView.setSelected(isSelected);
        ivSelected.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        
        // Set click listener
        paymentMethodView.setOnClickListener(v -> {
            // Deselect all payment methods
            for (int i = 0; i < llPaymentMethodsContainer.getChildCount(); i++) {
                View child = llPaymentMethodsContainer.getChildAt(i);
                child.setSelected(false);
                child.findViewById(R.id.ivSelected).setVisibility(View.GONE);
            }
            
            // Select clicked payment method
            v.setSelected(true);
            ivSelected.setVisibility(View.VISIBLE);
            selectedPaymentMethod = paymentMethod;
            
            // Save selected payment method
            paymentManager.setSelectedPaymentMethodId(paymentMethod.getId());
            
            // Update proceed to payment button state
            updateProceedToPaymentButtonState();
        });
        
        // Add to container
        llPaymentMethodsContainer.addView(paymentMethodView);
    }
    
    private void updateProceedToPaymentButtonState() {
        if (selectedPaymentMethod != null) {
            btnProceedToPayment.setEnabled(true);
            btnProceedToPayment.setAlpha(1.0f);
        } else {
            btnProceedToPayment.setEnabled(false);
            btnProceedToPayment.setAlpha(0.5f);
        }
    }
    
    private boolean validateCheckout() {
        if (selectedAddress == null) {
            Toast.makeText(this, "Please select a delivery address", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (selectedPaymentMethod == null) {
            Toast.makeText(this, "Please add a payment method", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private void proceedToPayment() {
        // Navigate to OrderSuccessActivity
        Intent intent = new Intent(this, OrderSuccessActivity.class);
        
        // Pass order details
        intent.putExtra("subtotal", subtotal);
        intent.putExtra("discount", discountAmount);
        intent.putExtra("promo_code", promoCode);
        intent.putExtra("discount_percentage", discountPercentage);
        intent.putExtra("delivery_fee", DELIVERY_FEE);
        intent.putExtra("total", total);
        
        startActivity(intent);
        
        // Note: Cart will be cleared in OrderSuccessActivity after order is created
    }
}
