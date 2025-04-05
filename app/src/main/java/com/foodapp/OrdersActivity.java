package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapters.OrderAdapter;
import com.foodapp.models.Order;
import com.foodapp.utils.OrderManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrderAdapter.OrderClickListener {

    private TabLayout tabLayout;
    private RecyclerView rvOrders;
    private TextView tvNoOrders;
    private BottomNavigationView bottomNavigation;
    
    private OrderManager orderManager;
    private List<Order> orders;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        
        // Initialize OrderManager
        orderManager = OrderManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Set up bottom navigation
        setupBottomNavigation();
        
        // Set up tabs
        setupTabs();
        
        // Load active orders by default
        loadOrders("active");
    }
    
    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        rvOrders = findViewById(R.id.rvOrders);
        tvNoOrders = findViewById(R.id.tvNoOrders);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        
        // Set up RecyclerView
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupBottomNavigation() {
        // Set selected item
        bottomNavigation.setSelectedItemId(R.id.nav_orders);
        
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                // Navigate to HomeActivity
                Intent intent = new Intent(OrdersActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_favorites) {
                // Navigate to FavoritesActivity
                Intent intent = new Intent(OrdersActivity.this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_orders) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Navigate to ProfileActivity
                Intent intent = new Intent(OrdersActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            
            return false;
        });
    }
    
    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVE"));
        tabLayout.addTab(tabLayout.newTab().setText("COMPLETED"));
        tabLayout.addTab(tabLayout.newTab().setText("CANCELLED"));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadOrders("active");
                        break;
                    case 1:
                        loadOrders("completed");
                        break;
                    case 2:
                        loadOrders("cancelled");
                        break;
                }
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Not needed
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Not needed
            }
        });
    }
    
    private void loadOrders(String status) {
        // Get orders by status
        if (status.equals("active")) {
            orders = orderManager.getActiveOrders();
        } else if (status.equals("completed")) {
            orders = orderManager.getCompletedOrders();
        } else if (status.equals("cancelled")) {
            orders = orderManager.getCancelledOrders();
        }
        
        if (orders.isEmpty()) {
            // Show empty state
            tvNoOrders.setVisibility(View.VISIBLE);
            rvOrders.setVisibility(View.GONE);
            
            // Add some demo orders if no orders exist
            if (status.equals("active") && orderManager.getAllOrders().isEmpty()) {
                orderManager.addDemoOrders();
                // Reload orders
                orders = orderManager.getActiveOrders();
                
                if (!orders.isEmpty()) {
                    tvNoOrders.setVisibility(View.GONE);
                    rvOrders.setVisibility(View.VISIBLE);
                }
            }
        } else {
            // Show orders
            tvNoOrders.setVisibility(View.GONE);
            rvOrders.setVisibility(View.VISIBLE);
        }
        
        // Set up adapter
        adapter = new OrderAdapter(this, orders, this);
        rvOrders.setAdapter(adapter);
    }
    
    @Override
    public void onOrderClicked(Order order) {
        // Navigate to track order screen
        Intent intent = new Intent(this, TrackOrderActivity.class);
        intent.putExtra("order_id", order.getId());
        startActivity(intent);
    }
}
