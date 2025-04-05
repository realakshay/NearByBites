package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.adapter.CartAdapter;
import com.foodapp.model.CartItem;
import com.foodapp.model.Order;
import com.foodapp.util.CartManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartItemActionListener {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private TextView tvEmptyCart;
    private TextView tvSubtotal;
    private TextView tvDeliveryFee;
    private TextView tvTotal;
    private TextView tvRestaurantName;
    private Button btnPlaceOrder;
    private List<CartItem> cartItems;
    private String restaurantName;
    private static final double DELIVERY_FEE = 2.99;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvTotal = findViewById(R.id.tvTotal);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Get restaurant name from intent
        restaurantName = getIntent().getStringExtra("restaurant_name");
        if (restaurantName != null) {
            tvRestaurantName.setText("Order from: " + restaurantName);
        }

        // Setup RecyclerView
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartItems = new ArrayList<>(CartManager.getInstance().getCartItems());
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerViewCart.setAdapter(cartAdapter);

        // Setup place order button
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        // Display cart items or empty cart message
        updateCartView();
    }

    private void updateCartView() {
        if (cartItems.isEmpty()) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            recyclerViewCart.setVisibility(View.GONE);
            btnPlaceOrder.setEnabled(false);
        } else {
            tvEmptyCart.setVisibility(View.GONE);
            recyclerViewCart.setVisibility(View.VISIBLE);
            btnPlaceOrder.setEnabled(true);
        }

        updatePrices();
    }

    private void updatePrices() {
        double subtotal = calculateSubtotal();
        double total = subtotal + DELIVERY_FEE;

        tvSubtotal.setText("$" + decimalFormat.format(subtotal));
        tvDeliveryFee.setText("$" + decimalFormat.format(DELIVERY_FEE));
        tvTotal.setText("$" + decimalFormat.format(total));
    }

    private double calculateSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getMenuItem().getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    private void placeOrder() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new order
        Order order = new Order();
        order.setRestaurantId(cartItems.get(0).getRestaurantId());
        order.setRestaurantName(cartItems.get(0).getRestaurantName());
        order.setItems(new ArrayList<>(cartItems));
        order.setSubtotal(calculateSubtotal());
        order.setDeliveryFee(DELIVERY_FEE);
        order.setTotal(order.getSubtotal() + order.getDeliveryFee());

        // Clear the cart
        CartManager.getInstance().clearCart();

        // Navigate to order confirmation
        Intent intent = new Intent(CartActivity.this, OrderConfirmationActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemQuantityChanged(int position, int quantity) {
        CartItem item = cartItems.get(position);
        if (quantity <= 0) {
            cartItems.remove(position);
            CartManager.getInstance().removeFromCart(item);
            cartAdapter.notifyItemRemoved(position);
        } else {
            item.setQuantity(quantity);
            CartManager.getInstance().updateCartItemQuantity(item);
            cartAdapter.notifyItemChanged(position);
        }
        updateCartView();
    }

    @Override
    public void onItemRemoved(int position) {
        CartItem item = cartItems.get(position);
        cartItems.remove(position);
        CartManager.getInstance().removeFromCart(item);
        cartAdapter.notifyItemRemoved(position);
        updateCartView();
    }
}
