package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.CartItem;
import com.foodapp.models.PaymentMethod;
import com.foodapp.utils.CartManager;
import com.foodapp.utils.OrderManager;
import com.foodapp.utils.PaymentManager;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class OrderSuccessActivity extends AppCompatActivity {

    private TextView tvOrderId;
    private LinearLayout llOrderItems;
    private TextView tvPaymentType;
    private TextView tvPaymentDetail;
    private TextView tvDeliveryFee;
    private TextView tvItemTotal;
    private TextView tvDiscount;
    private TextView tvTotal;
    private TextView tvPromoCode;
    private Button btnTrackOrder;
    private TextView tvBackToHome;
    
    private CartManager cartManager;
    private PaymentManager paymentManager;
    private OrderManager orderManager;
    
    private String orderId;
    private List<CartItem> orderItems;
    private PaymentMethod paymentMethod;
    private double subtotal;
    private double discountAmount;
    private String promoCode;
    private int discountPercentage;
    private double deliveryFee;
    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        
        // Initialize managers
        cartManager = CartManager.getInstance(this);
        paymentManager = PaymentManager.getInstance(this);
        orderManager = OrderManager.getInstance(this);
        
        // Get data from intent
        getIntentData();
        
        // Initialize views
        initViews();
        
        // Generate order ID
        generateOrderId();
        
        // Populate order summary
        populateOrderSummary();
        
        // Set up click listeners
        btnTrackOrder.setOnClickListener(v -> navigateToTrackOrder());
        tvBackToHome.setOnClickListener(v -> navigateToHome());
    }
    
    private void getIntentData() {
        // Retrieve cart items before they are cleared
        orderItems = cartManager.getCartItemsAsCartItems();
        
        // Get payment method
        paymentMethod = paymentManager.getSelectedPaymentMethod();
        
        // Get order details from intent (passed from CheckoutActivity)
        if (getIntent().getExtras() != null) {
            subtotal = getIntent().getDoubleExtra("subtotal", 0);
            discountAmount = getIntent().getDoubleExtra("discount", 0);
            promoCode = getIntent().getStringExtra("promo_code");
            discountPercentage = getIntent().getIntExtra("discount_percentage", 0);
            deliveryFee = getIntent().getDoubleExtra("delivery_fee", 0);
            total = getIntent().getDoubleExtra("total", 0);
        } else {
            // If no details are passed, calculate them from cart
            subtotal = cartManager.getCartTotal();
            discountAmount = 0;
            promoCode = "";
            discountPercentage = 0;
            deliveryFee = 3.50; // Default delivery fee
            total = subtotal + deliveryFee;
        }
    }
    
    private void initViews() {
        tvOrderId = findViewById(R.id.tvOrderId);
        llOrderItems = findViewById(R.id.llOrderItems);
        tvPaymentType = findViewById(R.id.tvPaymentType);
        tvPaymentDetail = findViewById(R.id.tvPaymentDetail);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvItemTotal = findViewById(R.id.tvItemTotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvPromoCode = findViewById(R.id.tvPromoCode);
        tvTotal = findViewById(R.id.tvTotal);
        btnTrackOrder = findViewById(R.id.btnTrackOrder);
        tvBackToHome = findViewById(R.id.tvBackToHome);
    }
    
    private void generateOrderId() {
        // Generate a unique order ID
        orderId = UUID.randomUUID().toString();
        tvOrderId.setText(String.format("Order #%s", orderId.substring(0, 8)));
        
        // Save the order in OrderManager
        orderManager.createOrder(orderId, orderItems, paymentMethod, subtotal, discountAmount, 
                deliveryFee, total, promoCode, discountPercentage);
    }
    
    private void populateOrderSummary() {
        // Add order items to the container
        DecimalFormat df = new DecimalFormat("0.##");
        
        for (CartItem item : orderItems) {
            View itemView = getLayoutInflater().inflate(R.layout.item_order_summary, llOrderItems, false);
            
            TextView tvItemName = itemView.findViewById(R.id.tvItemName);
            TextView tvItemDetail = itemView.findViewById(R.id.tvItemDetail);
            TextView tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            
            double itemTotalPrice = item.getQuantity() * item.getMenuItem().getPrice();
            
            tvItemName.setText(item.getMenuItem().getName());
            tvItemDetail.setText(String.format(Locale.getDefault(), "%dx - %s", 
                    item.getQuantity(), item.getMenuItem().getDescription()));
            tvItemPrice.setText(String.format("$%s", df.format(itemTotalPrice)));
            
            llOrderItems.addView(itemView);
        }
        
        // Set payment method details
        if (paymentMethod != null) {
            String paymentType = paymentMethod.getType() != null ? paymentMethod.getType() : "Credit Card";
            String paymentDetail = paymentMethod.getDetail() != null ? paymentMethod.getDetail() : 
                                 (paymentMethod.getCardNumber() != null ? 
                                  "xxxx xxxx xxxx " + paymentMethod.getCardNumber().substring(paymentMethod.getCardNumber().length() - 4) : 
                                  "Unknown");
            
            tvPaymentType.setText(paymentType);
            tvPaymentDetail.setText(paymentDetail);
        } else {
            tvPaymentType.setText("Cash on Delivery");
            tvPaymentDetail.setText("Pay when you receive");
        }
        
        // Set price details
        tvDeliveryFee.setText(String.format("$%s", df.format(deliveryFee)));
        tvItemTotal.setText(String.format("$%s", df.format(subtotal)));
        
        // Show discount if applicable
        if (discountAmount > 0 && promoCode != null && !promoCode.isEmpty()) {
            tvDiscount.setVisibility(View.VISIBLE);
            tvPromoCode.setVisibility(View.VISIBLE);
            
            tvPromoCode.setText(String.format("Discount (Promo Code: %s)", promoCode));
            tvDiscount.setText(String.format("-$%s", df.format(discountAmount)));
        } else {
            tvDiscount.setVisibility(View.GONE);
            tvPromoCode.setVisibility(View.GONE);
        }
        
        // Set total
        tvTotal.setText(String.format("$%s", df.format(total)));
    }
    
    private void navigateToTrackOrder() {
        // In a real app, this would navigate to an order tracking screen
        // For this demo, we'll just navigate to a placeholder activity
        Intent intent = new Intent(this, TrackOrderActivity.class);
        intent.putExtra("order_id", orderId);
        startActivity(intent);
    }
    
    private void navigateToHome() {
        // Navigate back to the home screen
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onBackPressed() {
        // Override back button to go to home screen instead of previous screen
        navigateToHome();
    }
}
