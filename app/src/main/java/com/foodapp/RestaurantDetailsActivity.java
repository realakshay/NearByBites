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

import com.foodapp.adapters.MenuItemAdapter;
import com.foodapp.adapters.PromoItemAdapter;
import com.foodapp.models.CartItem;
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
            System.out.println("Restaurant details loaded: " + restaurantName + ", ID: " + restaurantId);
        } else {
            // Default restaurant if no data
            restaurant = new Restaurant(1, "Restaurant", "", "Cuisine", 4.5f, 30, 1.5f, R.drawable.restaurant_1);
            System.out.println("No restaurant data found in intent, using default");
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
    }
    
    private void setupFavoriteButton() {
        // Check if restaurant is in favorites
        boolean isFavorite = favoriteManager.isRestaurantFavorite(restaurantId);
        
        // Set initial icon
        updateFavoriteIcon(isFavorite);
        
        // Set click listener
        ivFavorite.setOnClickListener(v -> {
            // Toggle favorite status
            boolean newStatus = !favoriteManager.isRestaurantFavorite(restaurantId);
            
            if (newStatus) {
                favoriteManager.addFavoriteRestaurant(restaurant);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                favoriteManager.removeFavoriteRestaurant(restaurantId);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
            
            // Update icon
            updateFavoriteIcon(newStatus);
        });
    }
    
    private void updateFavoriteIcon(boolean isFavorite) {
        ivFavorite.setImageResource(isFavorite ? 
                R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
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
        // Set restaurant data
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantCuisine.setText(restaurant.getCuisine());
        tvRestaurantRating.setText(String.valueOf(restaurant.getRating()));
        tvDeliveryTime.setText(restaurant.getDeliveryTime() + " min");
        tvDeliveryFee.setText("$" + String.format("%.2f", 2.99));
        
        // Set restaurant image
        ivRestaurantImage.setImageResource(restaurant.getImageResourceId());
        
        // Load promo items
        loadPromoItems();
        
        // Load menu items
        loadMenuItems();
        
        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            showLoading(false);
        }, 1000);
    }
    
    private void loadPromoItems() {
        // In a real app, these would come from an API or database
        promoItems = new ArrayList<>();
        promoItems.add(new PromoItem(1, "Special Offer", "Get 20% off on your first order", R.drawable.promo_1));
        promoItems.add(new PromoItem(2, "Free Delivery", "On orders above $20", R.drawable.promo_2));
        promoItems.add(new PromoItem(3, "Loyalty Rewards", "Earn points with every order", R.drawable.promo_3));
        
        // Set up promo items RecyclerView
        rvPromoItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PromoItemAdapter promoItemAdapter = new PromoItemAdapter(this, promoItems);
        rvPromoItems.setAdapter(promoItemAdapter);
    }
    
    private void loadMenuItems() {
        // In a real app, these would come from an API or database
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "Burger", "Classic beef burger with cheese", 8.99, R.drawable.menu_burger));
        menuItems.add(new MenuItem(2, "Pizza", "Pepperoni pizza with extra cheese", 12.99, R.drawable.menu_pizza));
        menuItems.add(new MenuItem(3, "Pasta", "Spaghetti with meatballs", 10.99, R.drawable.menu_pasta));
        menuItems.add(new MenuItem(4, "Salad", "Fresh garden salad", 6.99, R.drawable.menu_salad));
        menuItems.add(new MenuItem(5, "Drink", "Soft drink of your choice", 2.99, R.drawable.menu_drink));
        
        // Set up menu items RecyclerView
        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems, this);
        rvMenuItems.setAdapter(menuItemAdapter);
    }
    
    private void updateCartButton() {
        if (cartManager.getCartItems().isEmpty()) {
            btnViewCart.setVisibility(View.GONE);
        } else {
            btnViewCart.setVisibility(View.VISIBLE);
            btnViewCart.setText("VIEW CART (" + cartManager.getCartItemCount() + ")");
        }
    }
    
    @Override
    public void onMenuItemClicked(MenuItem menuItem) {
        selectedMenuItem = menuItem;
        showAddToCartBottomSheet(menuItem);
    }
    
    private void showAddToCartBottomSheet(MenuItem menuItem) {
        bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_add_to_cart_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        
        // Initialize bottom sheet views
        ImageView ivItemImage = bottomSheetView.findViewById(R.id.ivItemImage);
        TextView tvItemName = bottomSheetView.findViewById(R.id.tvItemName);
        TextView tvItemDescription = bottomSheetView.findViewById(R.id.tvItemDescription);
        TextView tvItemPrice = bottomSheetView.findViewById(R.id.tvItemPrice);
        TextView tvQuantity = bottomSheetView.findViewById(R.id.tvQuantity);
        ImageView ivMinus = bottomSheetView.findViewById(R.id.ivMinus);
        ImageView ivPlus = bottomSheetView.findViewById(R.id.ivPlus);
        Button btnAddToCart = bottomSheetView.findViewById(R.id.btnAddToCart);
        
        // Set menu item data
        ivItemImage.setImageResource(menuItem.getImageResourceId());
        tvItemName.setText(menuItem.getName());
        tvItemDescription.setText(menuItem.getDescription());
        tvItemPrice.setText("$" + String.format("%.2f", menuItem.getPrice()));
        
        // Get current quantity from cart
        int currentQuantity = cartManager.getItemQuantity(menuItem.getId());
        tvQuantity.setText(String.valueOf(currentQuantity > 0 ? currentQuantity : 1));
        
        // Set quantity controls
        ivMinus.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            if (quantity > 1) {
                tvQuantity.setText(String.valueOf(quantity - 1));
            }
        });
        
        ivPlus.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            tvQuantity.setText(String.valueOf(quantity + 1));
        });
        
        // Set add to cart button
        btnAddToCart.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            cartManager.addToCart(menuItem, quantity);
            bottomSheetDialog.dismiss();
            
            // Update cart button
            updateCartButton();
            
            // Show toast
            Toast.makeText(this, menuItem.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });
        
        bottomSheetDialog.show();
    }
}
