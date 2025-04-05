package com.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.Order;
import com.foodapp.utils.OrderManager;

public class TrackOrderActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvOrderId;
    private TextView tvOrderStatus;
    
    private OrderManager orderManager;
    private String orderId;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        
        // Initialize OrderManager
        orderManager = OrderManager.getInstance(this);
        
        // Get order ID from intent
        if (getIntent().getExtras() != null) {
            orderId = getIntent().getStringExtra("order_id");
        }
        
        // Initialize views
        initViews();
        
        // Set up back button
        ivBack.setOnClickListener(v -> finish());
        
        // Load order details
        loadOrderDetails();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
    }
    
    private void loadOrderDetails() {
        if (orderId != null) {
            order = orderManager.getOrder(orderId);
            
            if (order != null) {
                // Set order ID
                tvOrderId.setText("Order #" + orderId.substring(0, 8));
                
                // Set order status
                tvOrderStatus.setText("Status: " + order.getStatus());
                
                // In a real app, this would show a map, live tracking, etc.
            }
        }
    }
}
