package com.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.models.CartItem;
import com.foodapp.utils.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private CartManager cartManager;
    private CartItemListener cartItemListener;

    public interface CartItemListener {
        void onQuantityChanged();
        void onItemRemoved(CartItem cartItem);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, CartItemListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartManager = CartManager.getInstance(context);
        this.cartItemListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        
        // Set item name and price
        holder.tvItemName.setText(cartItem.getMenuItem().getName());
        holder.tvItemPrice.setText("$" + String.format("%.2f", cartItem.getMenuItem().getPrice()));
        
        // Set quantity
        holder.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
        
        // Set decrease button click listener
        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity > 1) {
                cartItem.setQuantity(currentQuantity - 1);
                cartManager.updateItemQuantity(cartItem.getMenuItem().getId(), currentQuantity - 1);
                holder.tvQuantity.setText(String.valueOf(currentQuantity - 1));
                notifyItemChanged(position);
                cartItemListener.onQuantityChanged();
            }
        });
        
        // Set increase button click listener
        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = cartItem.getQuantity();
            cartItem.setQuantity(currentQuantity + 1);
            cartManager.updateItemQuantity(cartItem.getMenuItem().getId(), currentQuantity + 1);
            holder.tvQuantity.setText(String.valueOf(currentQuantity + 1));
            notifyItemChanged(position);
            cartItemListener.onQuantityChanged();
        });
        
        // Set delete button click listener
        holder.ivDelete.setOnClickListener(v -> {
            cartManager.removeItemFromCart(cartItem.getMenuItem().getId());
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            cartItemListener.onItemRemoved(cartItem);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemPrice;
        TextView tvQuantity;
        Button btnDecrease;
        Button btnIncrease;
        ImageView ivDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
