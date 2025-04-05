package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapter.MenuAdapter;
import com.foodapp.api.ApiClient;
import com.foodapp.api.FoodService;
import com.foodapp.model.CartItem;
import com.foodapp.model.MenuItem;
import com.foodapp.util.CartManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements MenuAdapter.MenuItemClickListener {

    private RecyclerView recyclerViewMenu;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuItems;
    private ProgressBar progressBar;
    private TextView tvError;
    private TextView tvRestaurantName;
    private Button btnViewCart;
    private int restaurantId;
    private String restaurantName;
    private FoodService foodService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize views
        recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        btnViewCart = findViewById(R.id.btnViewCart);

        // Get restaurant data from intent
        restaurantId = getIntent().getIntExtra("restaurant_id", -1);
        restaurantName = getIntent().getStringExtra("restaurant_name");

        if (restaurantId == -1 || restaurantName == null) {
            Toast.makeText(this, "Error: Restaurant information not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvRestaurantName.setText(restaurantName);

        // Setup RecyclerView
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        menuItems = new ArrayList<>();
        menuAdapter = new MenuAdapter(menuItems, this);
        recyclerViewMenu.setAdapter(menuAdapter);

        // Initialize API client
        foodService = ApiClient.getClient().create(FoodService.class);

        // Setup cart button
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartManager.getInstance().getCartItems().size() > 0) {
                    Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                    intent.putExtra("restaurant_name", restaurantName);
                    startActivity(intent);
                } else {
                    Toast.makeText(MenuActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load menu items
        loadMenuItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartButton();
    }

    private void loadMenuItems() {
        showLoading();
        
        Call<List<MenuItem>> call = foodService.getMenuItems(restaurantId);
        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    menuItems.clear();
                    menuItems.addAll(response.body());
                    menuAdapter.notifyDataSetChanged();
                    
                    if (menuItems.isEmpty()) {
                        showError("No menu items available");
                    }
                } else {
                    showError("Failed to load menu items");
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                hideLoading();
                showError("Network error: " + t.getMessage());
                
                // For demo purposes, add mock data when API fails
                addMockMenuItems();
            }
        });
    }

    private void addMockMenuItems() {
        // This is only used when the API fails and is for demonstration purposes
        // In a real app, you would handle the error more gracefully
        menuItems.add(new MenuItem(1, restaurantId, "Margherita Pizza", "Classic pizza with tomato sauce and mozzarella", 12.99));
        menuItems.add(new MenuItem(2, restaurantId, "Pepperoni Pizza", "Pizza topped with pepperoni slices", 14.99));
        menuItems.add(new MenuItem(3, restaurantId, "Caesar Salad", "Fresh romaine lettuce with caesar dressing", 8.99));
        menuItems.add(new MenuItem(4, restaurantId, "Garlic Bread", "Toasted bread with garlic butter", 5.99));
        menuItems.add(new MenuItem(5, restaurantId, "Spaghetti Bolognese", "Spaghetti with meat sauce", 13.99));
        menuItems.add(new MenuItem(6, restaurantId, "Tiramisu", "Classic Italian dessert", 7.99));
        menuAdapter.notifyDataSetChanged();
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }

    @Override
    public void onAddToCart(MenuItem menuItem) {
        CartManager.getInstance().addToCart(new CartItem(menuItem, restaurantId, restaurantName));
        Toast.makeText(this, menuItem.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        updateCartButton();
    }

    private void updateCartButton() {
        int cartSize = CartManager.getInstance().getCartItems().size();
        if (cartSize > 0) {
            btnViewCart.setText("View Cart (" + cartSize + ")");
            btnViewCart.setEnabled(true);
        } else {
            btnViewCart.setText("View Cart");
            btnViewCart.setEnabled(false);
        }
    }
}
