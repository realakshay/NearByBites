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
import com.foodapp.models.CartItem;
import com.foodapp.models.MenuItem;
import com.foodapp.utils.CartManager;

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
                if (CartManager.getInstance(MenuActivity.this).getCartItems().size() > 0) {
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
        
        // Use dummy data instead of making API calls
        // In a real app, this would be replaced with actual API calls
        addMockMenuItems();
        
        // Simulate network delay for better UX testing
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
            }
        }, 1000); // 1 second delay
    }

    private void addMockMenuItems() {
        // Add appropriate menu items based on restaurant ID
        switch (restaurantId) {
            case 1: // Italian restaurant
                menuItems.add(new MenuItem(1, restaurantId, "Margherita Pizza", "Classic pizza with tomato sauce and mozzarella", 12.99));
                menuItems.add(new MenuItem(2, restaurantId, "Pepperoni Pizza", "Pizza topped with pepperoni slices", 14.99));
                menuItems.add(new MenuItem(3, restaurantId, "Caesar Salad", "Fresh romaine lettuce with caesar dressing", 8.99));
                menuItems.add(new MenuItem(4, restaurantId, "Garlic Bread", "Toasted bread with garlic butter", 5.99));
                menuItems.add(new MenuItem(5, restaurantId, "Spaghetti Bolognese", "Spaghetti with meat sauce", 13.99));
                menuItems.add(new MenuItem(6, restaurantId, "Tiramisu", "Classic Italian dessert", 7.99));
                break;
                
            case 2: // Indian restaurant
                menuItems.add(new MenuItem(7, restaurantId, "Butter Chicken", "Chicken in creamy tomato sauce", 15.99));
                menuItems.add(new MenuItem(8, restaurantId, "Vegetable Samosas", "Crispy pastry filled with spiced vegetables", 6.99));
                menuItems.add(new MenuItem(9, restaurantId, "Chicken Biryani", "Fragrant rice dish with chicken and spices", 14.99));
                menuItems.add(new MenuItem(10, restaurantId, "Naan Bread", "Traditional flatbread", 3.99));
                menuItems.add(new MenuItem(11, restaurantId, "Paneer Tikka Masala", "Cheese cubes in spiced sauce", 13.99));
                menuItems.add(new MenuItem(12, restaurantId, "Gulab Jamun", "Sweet milk dumplings in syrup", 5.99));
                break;
                
            case 3: // Japanese restaurant
                menuItems.add(new MenuItem(13, restaurantId, "Salmon Sushi (2 pcs)", "Fresh salmon on rice", 6.99));
                menuItems.add(new MenuItem(14, restaurantId, "California Roll", "Crab, avocado and cucumber roll", 8.99));
                menuItems.add(new MenuItem(15, restaurantId, "Miso Soup", "Traditional Japanese soup", 3.99));
                menuItems.add(new MenuItem(16, restaurantId, "Chicken Teriyaki", "Grilled chicken with teriyaki sauce", 14.99));
                menuItems.add(new MenuItem(17, restaurantId, "Tempura Udon", "Noodle soup with tempura", 12.99));
                menuItems.add(new MenuItem(18, restaurantId, "Green Tea Ice Cream", "Traditional Japanese ice cream", 4.99));
                break;
                
            case 4: // Mexican restaurant
                menuItems.add(new MenuItem(19, restaurantId, "Beef Tacos (3)", "Corn tortillas with beef filling", 9.99));
                menuItems.add(new MenuItem(20, restaurantId, "Chicken Quesadilla", "Flour tortilla with chicken and cheese", 10.99));
                menuItems.add(new MenuItem(21, restaurantId, "Guacamole & Chips", "Fresh avocado dip with tortilla chips", 7.99));
                menuItems.add(new MenuItem(22, restaurantId, "Beef Burrito", "Large flour tortilla filled with beef and beans", 12.99));
                menuItems.add(new MenuItem(23, restaurantId, "Nachos Supreme", "Tortilla chips topped with cheese, beans, and meat", 11.99));
                menuItems.add(new MenuItem(24, restaurantId, "Churros", "Fried dough pastry with cinnamon sugar", 5.99));
                break;
                
            case 5: // American restaurant
                menuItems.add(new MenuItem(25, restaurantId, "Classic Cheeseburger", "Beef patty with cheese on a bun", 10.99));
                menuItems.add(new MenuItem(26, restaurantId, "Chicken Sandwich", "Grilled chicken with lettuce and mayo", 9.99));
                menuItems.add(new MenuItem(27, restaurantId, "French Fries", "Crispy potato fries", 4.99));
                menuItems.add(new MenuItem(28, restaurantId, "Buffalo Wings (8 pcs)", "Spicy chicken wings", 12.99));
                menuItems.add(new MenuItem(29, restaurantId, "Caesar Wrap", "Grilled chicken with caesar salad in a wrap", 8.99));
                menuItems.add(new MenuItem(30, restaurantId, "Apple Pie", "Classic American dessert", 6.99));
                break;
                
            case 6: // Chinese restaurant
                menuItems.add(new MenuItem(31, restaurantId, "Kung Pao Chicken", "Spicy stir-fried chicken with peanuts", 13.99));
                menuItems.add(new MenuItem(32, restaurantId, "Beef Chow Mein", "Stir-fried noodles with beef", 12.99));
                menuItems.add(new MenuItem(33, restaurantId, "Vegetable Spring Rolls (4)", "Crispy rolls filled with vegetables", 6.99));
                menuItems.add(new MenuItem(34, restaurantId, "Sweet and Sour Pork", "Battered pork in sweet and sour sauce", 14.99));
                menuItems.add(new MenuItem(35, restaurantId, "Wonton Soup", "Clear soup with pork dumplings", 7.99));
                break;
                
            case 7: // Mediterranean restaurant
                menuItems.add(new MenuItem(36, restaurantId, "Greek Salad", "Fresh vegetables with feta cheese", 9.99));
                menuItems.add(new MenuItem(37, restaurantId, "Lamb Gyro", "Pita bread with grilled lamb and tzatziki", 12.99));
                menuItems.add(new MenuItem(38, restaurantId, "Hummus with Pita", "Chickpea dip with pita bread", 7.99));
                menuItems.add(new MenuItem(39, restaurantId, "Chicken Souvlaki", "Grilled chicken skewers", 14.99));
                menuItems.add(new MenuItem(40, restaurantId, "Baklava", "Sweet pastry with nuts and honey", 5.99));
                break;
                
            case 8: // Korean restaurant
                menuItems.add(new MenuItem(41, restaurantId, "Beef Bulgogi", "Marinated grilled beef", 15.99));
                menuItems.add(new MenuItem(42, restaurantId, "Kimchi Fried Rice", "Fried rice with spicy fermented cabbage", 12.99));
                menuItems.add(new MenuItem(43, restaurantId, "Bibimbap", "Rice bowl with vegetables, beef and egg", 14.99));
                menuItems.add(new MenuItem(44, restaurantId, "Kimchi Pancake", "Savory pancake with kimchi", 9.99));
                menuItems.add(new MenuItem(45, restaurantId, "Japchae", "Sweet potato noodles with vegetables", 13.99));
                break;
                
            case 9: // Thai restaurant
                menuItems.add(new MenuItem(46, restaurantId, "Pad Thai", "Stir-fried rice noodles with shrimp", 13.99));
                menuItems.add(new MenuItem(47, restaurantId, "Green Curry with Chicken", "Spicy coconut curry", 14.99));
                menuItems.add(new MenuItem(48, restaurantId, "Tom Yum Soup", "Hot and sour soup with shrimp", 8.99));
                menuItems.add(new MenuItem(49, restaurantId, "Papaya Salad", "Shredded green papaya salad", 9.99));
                menuItems.add(new MenuItem(50, restaurantId, "Mango Sticky Rice", "Sweet dessert with coconut milk", 6.99));
                break;
                
            case 10: // Middle Eastern restaurant
                menuItems.add(new MenuItem(51, restaurantId, "Falafel Plate", "Chickpea fritters with rice and salad", 11.99));
                menuItems.add(new MenuItem(52, restaurantId, "Chicken Shawarma Wrap", "Marinated chicken in pita", 10.99));
                menuItems.add(new MenuItem(53, restaurantId, "Tabbouleh Salad", "Parsley and bulgur wheat salad", 8.99));
                menuItems.add(new MenuItem(54, restaurantId, "Baba Ganoush", "Eggplant dip with pita", 7.99));
                menuItems.add(new MenuItem(55, restaurantId, "Baklava", "Sweet pastry with nuts", 5.99));
                break;
                
            default:
                // Default menu items for any other restaurant
                menuItems.add(new MenuItem(1, restaurantId, "House Special Appetizer", "Chef's selection of appetizers", 9.99));
                menuItems.add(new MenuItem(2, restaurantId, "Main Course 1", "Popular main dish", 14.99));
                menuItems.add(new MenuItem(3, restaurantId, "Main Course 2", "Another popular dish", 15.99));
                menuItems.add(new MenuItem(4, restaurantId, "Side Dish", "Complements main courses", 5.99));
                menuItems.add(new MenuItem(5, restaurantId, "Dessert Special", "Sweet treat to finish your meal", 6.99));
                break;
        }
        
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
        CartManager.getInstance(MenuActivity.this).addItem(menuItem);
        updateCartButton();
        Toast.makeText(this, menuItem.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }

    private void updateCartButton() {
        int cartSize = CartManager.getInstance(MenuActivity.this).getCartItems().size();
        if (cartSize > 0) {
            btnViewCart.setText("View Cart (" + cartSize + ")");
            btnViewCart.setEnabled(true);
        } else {
            btnViewCart.setText("View Cart");
            btnViewCart.setEnabled(false);
        }
    }
}
