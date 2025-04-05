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
    private TextView tvChangeAddress;
    
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
        
        // Setup change address option
        if (tvChangeAddress != null) {
            tvChangeAddress.setOnClickListener(v -> {
                // Navigate to AddressSelectionActivity
                Intent intent = new Intent(CheckoutActivity.this, AddressSelectionActivity.class);
                startActivity(intent);
            });
        }
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
        
        // Reload address in case it was changed
        loadDeliveryAddress();
        
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
        
        try {
            tvChangeAddress = findViewById(R.id.tvChangeAddress);
        } catch (Exception e) {
            // View might not exist in current layout
        }
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
            
            tvAddressType.setText(selectedAddress.getLabel().toUpperCase() + " ADDRESS");
            tvAddressDetail.setText(selectedAddress.getFormattedAddress());
            
            // Add to container
            llAddressContainer.addView(addressView);
        } else {
            // If no address is available, show a message and option to add address
            View addressView = getLayoutInflater().inflate(R.layout.item_no_address, llAddressContainer, false);
            Button btnAddAddress = addressView.findViewById(R.id.btnAddAddress);
            
            btnAddAddress.setOnClickListener(v -> {
                // Navigate to AddressSelectionActivity
                Intent intent = new Intent(CheckoutActivity.this, AddressSelectionActivity.class);
                startActivity(intent);
            });
            
            // Add to container
            llAddressContainer.addView(addressView);
            
            // Disable payment button if no address
            btnProceedToPayment.setEnabled(false);
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
        } else {
            // Hide no payment methods message
            tvNoPaymentMethods.setVisibility(View.GONE);
            llPaymentMethodsContainer.setVisibility(View.VISIBLE);
            
            // Set up payment methods
            for (PaymentMethod paymentMethod : paymentMethods) {
                // Inflate payment method view
                View paymentMethodView = getLayoutInflater().inflate(R.layout.item_payment_method, llPaymentMethodsContainer, false);
                
                // Set payment method data
                TextView tvCardNumber = paymentMethodView.findViewById(R.id.tvCardNumber);
                TextView tvCardName = paymentMethodView.findViewById(R.id.tvCardName);
                ImageView ivCardType = paymentMethodView.findViewById(R.id.ivCardType);
                
                // Format card number to show only last 4 digits
                String maskedCardNumber = "•••• •••• •••• " + paymentMethod.getCardNumber().substring(paymentMethod.getCardNumber().length() - 4);
                tvCardNumber.setText(maskedCardNumber);
                tvCardName.setText(paymentMethod.getCardholderName());
                
                // Set card type icon based on card type
                if (paymentMethod.getCardType().equalsIgnoreCase("visa")) {
                    ivCardType.setImageResource(R.drawable.ic_visa);
                } else if (paymentMethod.getCardType().equalsIgnoreCase("mastercard")) {
                    ivCardType.setImageResource(R.drawable.ic_mastercard);
                } else if (paymentMethod.getCardType().equalsIgnoreCase("amex")) {
                    ivCardType.setImageResource(R.drawable.ic_amex);
                } else {
                    ivCardType.setImageResource(R.drawable.ic_generic_card);
                }
                
                // Set selected state
                View selectedIndicator = paymentMethodView.findViewById(R.id.viewSelectedIndicator);
                if (selectedPaymentMethod != null && selectedPaymentMethod.equals(paymentMethod)) {
                    selectedIndicator.setVisibility(View.VISIBLE);
                } else {
                    selectedIndicator.setVisibility(View.INVISIBLE);
                }
                
                // Set click listener
                paymentMethodView.setOnClickListener(v -> {
                    // Set as selected payment method
                    selectedPaymentMethod = paymentMethod;
                    
                    // Refresh payment methods to update selected state
                    loadPaymentMethods();
                    
                    // Update proceed to payment button state
                    updateProceedToPaymentButtonState();
                });
                
                // Add to container
                llPaymentMethodsContainer.addView(paymentMethodView);
            }
        }
    }
    
    private boolean validateCheckout() {
        if (selectedAddress == null) {
            Toast.makeText(this, "Please select a delivery address", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (selectedPaymentMethod == null) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private void updateProceedToPaymentButtonState() {
        // Enable/disable button based on selection state
        btnProceedToPayment.setEnabled(selectedAddress != null && selectedPaymentMethod != null);
    }
    
    private void proceedToPayment() {
        // In a real app, this would process the payment
        // For demo, we'll navigate to the order success screen
        
        // Show processing message
        Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show();
        
        // Navigate to OrderSuccessActivity after a short delay
        btnProceedToPayment.postDelayed(() -> {
            Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
            intent.putExtra("order_id", generateOrderId());
            intent.putExtra("total", total);
            intent.putExtra("delivery_time", "30-45 minutes");
            startActivity(intent);
            finish();
        }, 1500);
    }
    
    private String generateOrderId() {
        // Generate a random order ID for demo purposes
        return "ORD-" + (int)(Math.random() * 10000);
    }
}
