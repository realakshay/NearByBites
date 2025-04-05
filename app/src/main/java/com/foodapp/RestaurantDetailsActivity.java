package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodapp.adapter.MenuItemAdapter;
import com.foodapp.adapter.PromoItemAdapter;
import com.foodapp.models.MenuItem;
import com.foodapp.models.PromoItem;
import com.foodapp.models.Restaurant;
import com.foodapp.utils.CartManager;
import com.foodapp.utils.FavoriteManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemListener {

    private ImageView ivBack;
    private ImageView ivFavorite;
    private ImageView ivRestaurantImage;
    private TextView tvRestaurantName;
    private TextView tvRestaurantCuisine;
    private TextView tvRestaurantRating;
    private TextView tvDeliveryTime;
    private TextView tvDeliveryFee;
    private RecyclerView rvPromoItems;
    private RecyclerView rvMenuItems;
    private ProgressBar progressBar;
    private Button btnViewCart;
    
    private CartManager cartManager;
    private FavoriteManager favoriteManager;
    
    private int restaurantId;
    private Restaurant restaurant;
    private List<PromoItem> promoItems;
    private List<MenuItem> menuItems;
    
    // Bottom sheet for adding items to cart
    private BottomSheetDialog bottomSheetDialog;
    private MenuItem selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        
        // Initialize CartManager
        cartManager = CartManager.getInstance(this);
        
        // Initialize FavoriteManager
        favoriteManager = FavoriteManager.getInstance(this);
        
        // Get restaurant data from intent
        getIntentData();
        
        // Initialize views
        initViews();
        
        // Set up back button
        ivBack.setOnClickListener(v -> finish());
        
        // Set up favorite button
        setupFavoriteButton();
        
        // Show loading
        showLoading(true);
        
        // Load data (simulate network delay)
        loadData();
        
        // Set up view cart button
        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantDetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });
        
        // Update cart button
        updateCartButton();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Update cart button when returning to this screen
        updateCartButton();
    }
    
    private void getIntentData() {
        if (getIntent().getExtras() != null) {
            restaurantId = getIntent().getIntExtra("restaurant_id", 0);
            String restaurantName = getIntent().getStringExtra("restaurant_name");
            int restaurantImage = getIntent().getIntExtra("restaurant_image", R.drawable.restaurant_1);
            float rating = getIntent().getFloatExtra("restaurant_rating", 0);
            String cuisine = getIntent().getStringExtra("restaurant_cuisine");
            int deliveryTime = getIntent().getIntExtra("restaurant_delivery_time", 30);
            float distance = getIntent().getFloatExtra("restaurant_distance", 0);
            
            // Create restaurant object
            restaurant = new Restaurant(restaurantId, restaurantName, "", cuisine, rating, deliveryTime, distance, restaurantImage);
            
            // Debug message
            Toast.makeText(this, "Loading " + restaurantName, Toast.LENGTH_SHORT).show();
        } else {
            // Mock data for testing
            restaurant = new Restaurant(1, "Restaurant", "", "Cuisine", 4.5f, 30, 1.5f, R.drawable.restaurant_1);
        }
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        ivFavorite = findViewById(R.id.ivFavorite);
        ivRestaurantImage = findViewById(R.id.ivRestaurantImage);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvRestaurantCuisine = findViewById(R.id.tvRestaurantCuisine);
        tvRestaurantRating = findViewById(R.id.tvRestaurantRating);
        tvDeliveryTime = findViewById(R.id.tvDeliveryTime);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        rvPromoItems = findViewById(R.id.rvPromoItems);
        rvMenuItems = findViewById(R.id.rvMenuItems);
        progressBar = findViewById(R.id.progressBar);
        btnViewCart = findViewById(R.id.btnViewCart);
        
        // Set up RecyclerViews
        rvPromoItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupFavoriteButton() {
        // Check if restaurant is favorite
        boolean isFavorite = favoriteManager.isFavorite(restaurant.getId());
        restaurant.setFavorite(isFavorite);
        
        // Update UI
        updateFavoriteIcon();
        
        // Set click listener
        ivFavorite.setOnClickListener(v -> {
            // Toggle favorite status
            boolean newStatus = !restaurant.isFavorite();
            restaurant.setFavorite(newStatus);
            
            // Update in manager
            if (newStatus) {
                favoriteManager.addFavorite(restaurant.getId());
            } else {
                favoriteManager.removeFavorite(restaurant.getId());
            }
            
            // Update UI
            updateFavoriteIcon();
        });
    }
    
    private void updateFavoriteIcon() {
        if (restaurant.isFavorite()) {
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }
    
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            rvPromoItems.setVisibility(View.GONE);
            rvMenuItems.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rvPromoItems.setVisibility(View.VISIBLE);
            rvMenuItems.setVisibility(View.VISIBLE);
        }
    }
    
    private void loadData() {
        // Initialize lists
        promoItems = new ArrayList<>();
        menuItems = new ArrayList<>();
        
        // Populate restaurant data
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantCuisine.setText(restaurant.getCuisine());
        tvRestaurantRating.setText(String.valueOf(restaurant.getRating()));
        tvDeliveryTime.setText(restaurant.getDeliveryTime() + " min");
        tvDeliveryFee.setText("$" + String.format("%.2f", restaurant.getDistance() * 1.5));
        
        // Load restaurant image
        Glide.with(this)
                .load(restaurant.getImageResourceId())
                .placeholder(R.drawable.restaurant_1)
                .into(ivRestaurantImage);
        
        // Add promo items (mock data)
        promoItems.add(new PromoItem(1, "Special Offer", "Get 20% off on your first order", R.drawable.promo_1));
        promoItems.add(new PromoItem(2, "Free Delivery", "On orders above $20", R.drawable.promo_2));
        promoItems.add(new PromoItem(3, "Loyalty Rewards", "Earn points with every order", R.drawable.promo_3));
        
        // Set up promo adapter
        PromoItemAdapter promoItemAdapter = new PromoItemAdapter(promoItems);
        rvPromoItems.setAdapter(promoItemAdapter);
        
        // Add menu items (mock data)
        menuItems.add(new MenuItem(1, "Burger", "Classic beef burger with cheese", 8.99, R.drawable.menu_burger));
        menuItems.add(new MenuItem(2, "Pizza", "Pepperoni pizza with extra cheese", 12.99, R.drawable.menu_pizza));
        menuItems.add(new MenuItem(3, "Pasta", "Spaghetti with meatballs", 10.99, R.drawable.menu_pasta));
        menuItems.add(new MenuItem(4, "Salad", "Fresh garden salad", 6.99, R.drawable.menu_salad));
        menuItems.add(new MenuItem(5, "Drink", "Soft drink of your choice", 2.99, R.drawable.menu_drink));
        
        // Set up menu adapter
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(menuItems, this);
        rvMenuItems.setAdapter(menuItemAdapter);
        
        // Update quantities from cart
        updateMenuItemQuantities();
        
        // Hide loading after a delay (simulate network request)
        rvMenuItems.postDelayed(() -> {
            showLoading(false);
        }, 1000);
    }
    
    private void updateMenuItemQuantities() {
        // Update quantities from cart
        for (MenuItem menuItem : menuItems) {
            int quantity = cartManager.getItemQuantity(menuItem.getId());
            menuItem.setQuantity(quantity);
        }
    }
    
    private void updateCartButton() {
        int totalItems = cartManager.getTotalItems();
        if (totalItems > 0) {
            btnViewCart.setVisibility(View.VISIBLE);
            btnViewCart.setText("View Cart (" + totalItems + ")");
        } else {
            btnViewCart.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onAddToCartClicked(MenuItem menuItem) {
        // Add to cart
        cartManager.addItem(menuItem);
        
        // Update UI
        updateCartButton();
        
        // Show message
        Toast.makeText(this, menuItem.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
