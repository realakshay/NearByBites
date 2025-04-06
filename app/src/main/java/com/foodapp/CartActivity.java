package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.CartAdapter;
import com.foodapp.models.CartItem;
import com.foodapp.utils.CartManager;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartItemListener {

    private RecyclerView rvCartItems;
    private TextView tvSubtotal;
    private TextView tvDiscount;
    private LinearLayout llDiscount;
    private TextView tvDiscountPercentage;
    private TextView tvDeliveryFee;
    private TextView tvTotal;
    private Button btnCheckout;
    private LinearLayout llEmptyCart;
    private Button btnContinueShopping;
    private ImageView ivBack;
    private EditText etPromoCode;
    private Button btnApplyPromo;
    private TextView tvPromoApplied;
    
    private CartManager cartManager;
    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;
    
    // Constants for fees
    private static final double DELIVERY_FEE = 3.50;
    private double discountAmount = 0.0;
    private int discountPercentage = 0;
    private String appliedPromoCode = "";
    private boolean promoApplied = false;
    
    // Define some valid promo codes (in a real app, these would come from a database or API)
    private Map<String, Integer> validPromoCodes = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        // Initialize valid promo codes
        validPromoCodes.put("SAVE10", 10);
        validPromoCodes.put("SAVE20", 20);
        validPromoCodes.put("WELCOME", 15);
        validPromoCodes.put("NEWUSER", 25);
        
        // Initialize CartManager
        cartManager = CartManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Set up back button
        ivBack.setOnClickListener(v -> finish());
        
        // Get cart items
        cartItems = cartManager.getCartItemsAsCartItems();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        // Check if cart is empty
        updateCartVisibility();
        
        // Set up promo code button
        btnApplyPromo.setOnClickListener(v -> applyPromoCode());
        
        // Set up continue shopping button
        btnContinueShopping.setOnClickListener(v -> finish());
        
        // Set up checkout button
        btnCheckout.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                // Navigate to CheckoutActivity with order details
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("subtotal", cartManager.getCartTotal());
                intent.putExtra("discount", discountAmount);
                intent.putExtra("promo_code", appliedPromoCode);
                intent.putExtra("discount_percentage", discountPercentage);
                startActivity(intent);
            }
        });
    }
    
    private void initViews() {
        rvCartItems = findViewById(R.id.rvCartItems);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        llDiscount = findViewById(R.id.llDiscount);
        tvDiscountPercentage = findViewById(R.id.tvDiscountPercentage);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        llEmptyCart = findViewById(R.id.llEmptyCart);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);
        ivBack = findViewById(R.id.ivBack);
        etPromoCode = findViewById(R.id.etPromoCode);
        btnApplyPromo = findViewById(R.id.btnApplyPromo);
        tvPromoApplied = findViewById(R.id.tvPromoApplied);
        
        // Set delivery fee text
        tvDeliveryFee.setText("$" + String.format("%.2f", DELIVERY_FEE));
        
        // Initially hide discount section and promo applied text
        llDiscount.setVisibility(View.GONE);
        tvPromoApplied.setVisibility(View.GONE);
    }
    
    private void setupRecyclerView() {
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, this);
        rvCartItems.setAdapter(cartAdapter);
    }
    
    private void updateCartVisibility() {
        if (cartItems.isEmpty()) {
            // Show empty cart message
            llEmptyCart.setVisibility(View.VISIBLE);
            rvCartItems.setVisibility(View.GONE);
            btnCheckout.setEnabled(false);
            btnCheckout.setAlpha(0.5f);
            
            // Hide promo code section and discount info
            findViewById(R.id.promoCodeSection).setVisibility(View.GONE);
            llDiscount.setVisibility(View.GONE);
            tvPromoApplied.setVisibility(View.GONE);
        } else {
            // Show cart items
            llEmptyCart.setVisibility(View.GONE);
            rvCartItems.setVisibility(View.VISIBLE);
            findViewById(R.id.promoCodeSection).setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(true);
            btnCheckout.setAlpha(1.0f);
            
            // Calculate and update totals
            calculateOrderTotals();
        }
    }
    
    private void calculateOrderTotals() {
        // Calculate subtotal
        double subtotal = cartManager.getCartTotal();
        tvSubtotal.setText("$" + String.format("%.2f", subtotal));
        
        // Apply discount if promo code is valid
        if (promoApplied) {
            discountAmount = subtotal * (discountPercentage / 100.0);
            llDiscount.setVisibility(View.VISIBLE);
            tvDiscountPercentage.setText("Discount (" + discountPercentage + "%)");
            tvDiscount.setText("-$" + String.format("%.2f", discountAmount));
            tvDiscount.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            discountAmount = 0;
            llDiscount.setVisibility(View.GONE);
        }
        
        double discountedSubtotal = subtotal - discountAmount;
        if (discountedSubtotal < 0) discountedSubtotal = 0;
        
        // Calculate total (subtotal - discount + delivery fee)
        double total = discountedSubtotal + DELIVERY_FEE;
        tvTotal.setText("$" + String.format("%.2f", total));
        
        // Update checkout button text
        btnCheckout.setText("Proceed to Checkout");
    }
    
    private void applyPromoCode() {
        String promoCode = etPromoCode.getText().toString().trim().toUpperCase();
        
        if (promoCode.isEmpty()) {
            Toast.makeText(this, "Please enter a promo code", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (promoApplied) {
            Toast.makeText(this, "Promo code already applied", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Check if promo code is valid
        if (validPromoCodes.containsKey(promoCode)) {
            discountPercentage = validPromoCodes.get(promoCode);
            appliedPromoCode = promoCode;
            promoApplied = true;
            
            // Show success message
            tvPromoApplied.setText("Promo code applied! " + discountPercentage + "% discount");
            tvPromoApplied.setVisibility(View.VISIBLE);
            tvPromoApplied.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            
            // Update totals
            calculateOrderTotals();
            
            // Disable the promo code field and button
            etPromoCode.setEnabled(false);
            btnApplyPromo.setEnabled(false);
            btnApplyPromo.setAlpha(0.5f);
            
            Toast.makeText(this, "Promo code applied successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid promo code", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onQuantityChanged() {
        // Recalculate totals when quantity changes
        calculateOrderTotals();
    }
    
    @Override
    public void onItemRemoved(CartItem cartItem) {
        // Update cart visibility and totals when an item is removed
        updateCartVisibility();
        
        // Show toast
        Toast.makeText(this, cartItem.getMenuItem().getName() + " removed from cart", 
                Toast.LENGTH_SHORT).show();
    }
}
