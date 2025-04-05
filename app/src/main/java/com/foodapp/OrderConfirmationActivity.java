package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapter.CartAdapter;
import com.foodapp.model.Order;

import java.text.DecimalFormat;
import java.util.Random;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView tvOrderNumber;
    private TextView tvRestaurantName;
    private TextView tvEstimatedDelivery;
    private TextView tvSubtotal;
    private TextView tvDeliveryFee;
    private TextView tvTotal;
    private RecyclerView recyclerViewOrderItems;
    private CartAdapter cartAdapter;
    private Button btnReturnHome;
    private Order order;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Initialize views
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvEstimatedDelivery = findViewById(R.id.tvEstimatedDelivery);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvTotal = findViewById(R.id.tvTotal);
        recyclerViewOrderItems = findViewById(R.id.recyclerViewOrderItems);
        btnReturnHome = findViewById(R.id.btnReturnHome);

        // Get order from intent
        if (getIntent().hasExtra("order")) {
            order = (Order) getIntent().getSerializableExtra("order");
            displayOrderDetails();
        } else {
            // If no order is passed, return to main activity
            finish();
            return;
        }

        // Setup RecyclerView for order items
        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(order.getItems(), null);
        cartAdapter.setReadOnly(true);
        recyclerViewOrderItems.setAdapter(cartAdapter);

        // Setup button to return to main screen
        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayOrderDetails() {
        // Generate a random order number
        Random random = new Random();
        String orderNumber = "ORD-" + (10000 + random.nextInt(90000));
        tvOrderNumber.setText("Order #: " + orderNumber);

        // Display restaurant name
        tvRestaurantName.setText(order.getRestaurantName());

        // Calculate estimated delivery time (30-45 minutes from now)
        int minDeliveryTime = 30;
        int maxDeliveryTime = 45;
        tvEstimatedDelivery.setText("Estimated delivery: " + minDeliveryTime + "-" + maxDeliveryTime + " minutes");

        // Display price details
        tvSubtotal.setText("$" + decimalFormat.format(order.getSubtotal()));
        tvDeliveryFee.setText("$" + decimalFormat.format(order.getDeliveryFee()));
        tvTotal.setText("$" + decimalFormat.format(order.getTotal()));
    }
}
