package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodapp.adapter.MenuItemAdapter;
import com.foodapp.models.MenuItem;
import com.foodapp.models.PromoItem;
import com.foodapp.models.Restaurant;
import com.foodapp.sharing.SocialSharingActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity implements MenuItemAdapter.MenuItemListener {

    private ImageView ivRestaurantImage;
    private TextView tvRestaurantName;
    private TextView tvRestaurantCuisine;
    private TextView tvRestaurantRating;
    private TextView tvRestaurantDelivery;
    private ImageView ivFavorite;
    private RecyclerView rvMenuItems;
    private Button btnViewCart;
    private Button btnShareRestaurant;
    
    private Restaurant restaurant;
    private boolean isFavorite = false;
    private List<MenuItem> cartItems = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        
        // Initialize views
        initViews();
        
        // Create demo restaurant with menu items
        createDemoRestaurant();
        
        // Set up the restaurant details
        setupRestaurantDetails();
        
        // Set up the menu items recycler view
        setupMenuItemsRecyclerView();
        
        // Set up click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        ivRestaurantImage = findViewById(R.id.ivRestaurantImage);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvRestaurantCuisine = findViewById(R.id.tvRestaurantCuisine);
        tvRestaurantRating = findViewById(R.id.tvRestaurantRating);
        tvRestaurantDelivery = findViewById(R.id.tvRestaurantDelivery);
        ivFavorite = findViewById(R.id.ivFavorite);
        rvMenuItems = findViewById(R.id.rvMenuItems);
        btnViewCart = findViewById(R.id.btnViewCart);
        btnShareRestaurant = findViewById(R.id.btnShareRestaurant);
    }
    
    private void createDemoRestaurant() {
        // Create a demo restaurant with menu items for testing
        restaurant = new Restaurant(
                1,
                "Burger Palace",
                "123 Main Street, New York",
                "American, Fast Food",
                4.5f,
                30,
                1.5f,
                R.drawable.restaurant_1
        );
        
        // Add menu items
        restaurant.addMenuItem(new MenuItem(1, "Classic Burger", "Juicy beef patty with lettuce, tomato, and special sauce", 9.99, R.drawable.menu_burger));
        restaurant.addMenuItem(new MenuItem(2, "Cheeseburger", "Classic burger with melted cheddar cheese", 11.99, R.drawable.menu_burger));
        restaurant.addMenuItem(new MenuItem(3, "Deluxe Burger", "Premium beef with bacon, cheese, and avocado", 13.99, R.drawable.menu_burger));
        restaurant.addMenuItem(new MenuItem(4, "Chicken Sandwich", "Grilled chicken with lettuce and mayo", 10.99, R.drawable.menu_burger));
        restaurant.addMenuItem(new MenuItem(5, "Veggie Burger", "Plant-based patty with fresh vegetables", 12.99, R.drawable.menu_burger));
        
        // Add promo items
        restaurant.addPromoItem(new PromoItem(1, "30% OFF", "First order discount", 0.3, "WELCOME30", R.drawable.placeholder_food));
        restaurant.addPromoItem(new PromoItem(2, "Free Delivery", "Orders over $25", 0, "FREEDEL", R.drawable.placeholder_food));
    }
    
    private void setupRestaurantDetails() {
        // Load restaurant image
        Glide.with(this)
                .load(restaurant.getImageResourceId())
                .placeholder(R.drawable.placeholder_food)
                .into(ivRestaurantImage);
        
        // Set text views
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantCuisine.setText(restaurant.getCuisineType());
        tvRestaurantRating.setText(String.format("%.1f", restaurant.getRating()));
        tvRestaurantDelivery.setText(String.format("%d min • %.1f mi", 
                restaurant.getDeliveryTimeMinutes(), restaurant.getDeliveryDistance()));
        
        // Set favorite icon
        updateFavoriteIcon();
    }
    
    private void setupMenuItemsRecyclerView() {
        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
        MenuItemAdapter adapter = new MenuItemAdapter(restaurant.getMenuItems(), this);
        rvMenuItems.setAdapter(adapter);
    }
    
    private void setupClickListeners() {
        // Favorite button click
        ivFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            restaurant.setFavorite(isFavorite);
            updateFavoriteIcon();
            
            String message = isFavorite ? 
                    "Added to favorites" : "Removed from favorites";
            Toast.makeText(RestaurantDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        });
        
        // View cart button click
        btnViewCart.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(RestaurantDetailsActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                // In a real app, this would navigate to the cart activity
                Toast.makeText(RestaurantDetailsActivity.this, 
                        "You have " + cartItems.size() + " items in cart", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Share restaurant button click
        btnShareRestaurant.setOnClickListener(v -> {
            Intent shareIntent = new Intent(this, SocialSharingActivity.class);
            shareIntent.putExtra("restaurant_id", restaurant.getId());
            startActivity(shareIntent);
        });
    }
    
    private void updateFavoriteIcon() {
        ivFavorite.setImageResource(isFavorite ? 
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }
    
    @Override
    public void onAddToCartClicked(MenuItem menuItem) {
        // Check if the item is already in the cart
        boolean itemExists = false;
        for (MenuItem item : cartItems) {
            if (item.getId() == menuItem.getId()) {
                itemExists = true;
                item.setQuantity(menuItem.getQuantity());
                break;
            }
        }
        
        if (!itemExists && menuItem.getQuantity() > 0) {
            cartItems.add(menuItem);
        }
        
        // Update the view cart button visibility
        updateCartButton();
        
        Toast.makeText(this, menuItem.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
    
    private void updateCartButton() {
        if (cartItems.isEmpty()) {
            btnViewCart.setVisibility(View.GONE);
        } else {
            btnViewCart.setVisibility(View.VISIBLE);
            
            // Calculate total price
            double totalPrice = 0;
            for (MenuItem item : cartItems) {
                totalPrice += item.getTotalPrice();
            }
            
            btnViewCart.setText(String.format("View Cart ($%.2f)", totalPrice));
        }
    }
}
