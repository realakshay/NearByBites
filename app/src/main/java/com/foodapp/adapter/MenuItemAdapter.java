package com.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodapp.R;
import com.foodapp.models.MenuItem;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    // Interface for menu item click listener
    public interface MenuItemListener {
        void onAddToCartClicked(MenuItem menuItem);
    }

    private List<MenuItem> menuItems;
    private MenuItemListener listener;

    public MenuItemAdapter(List<MenuItem> menuItems, MenuItemListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);
        holder.bind(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMenuItem;
        private TextView tvMenuItemName;
        private TextView tvMenuItemDescription;
        private TextView tvMenuItemPrice;
        private Button btnAddToCart;
        private TextView tvQuantity;
        private Button btnDecrement;
        private Button btnIncrement;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenuItem = itemView.findViewById(R.id.ivMenuItem);
            tvMenuItemName = itemView.findViewById(R.id.tvMenuItemName);
            tvMenuItemDescription = itemView.findViewById(R.id.tvMenuItemDescription);
            tvMenuItemPrice = itemView.findViewById(R.id.tvMenuItemPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrement = itemView.findViewById(R.id.btnDecrement);
            btnIncrement = itemView.findViewById(R.id.btnIncrement);
        }

        public void bind(final MenuItem menuItem) {
            tvMenuItemName.setText(menuItem.getName());
            tvMenuItemDescription.setText(menuItem.getDescription());
            tvMenuItemPrice.setText(String.format("$%.2f", menuItem.getPrice()));
            
            // Use Glide to load the image
            Glide.with(itemView.getContext())
                    .load(menuItem.getImageResourceId())
                    .placeholder(R.drawable.placeholder_food)
                    .into(ivMenuItem);
            
            // Update quantity display
            tvQuantity.setText(String.valueOf(menuItem.getQuantity()));
            
            // If quantity is 0, show "Add to Cart" button, otherwise show quantity controls
            if (menuItem.getQuantity() == 0) {
                btnAddToCart.setVisibility(View.VISIBLE);
                tvQuantity.setVisibility(View.GONE);
                btnDecrement.setVisibility(View.GONE);
                btnIncrement.setVisibility(View.GONE);
            } else {
                btnAddToCart.setVisibility(View.GONE);
                tvQuantity.setVisibility(View.VISIBLE);
                btnDecrement.setVisibility(View.VISIBLE);
                btnIncrement.setVisibility(View.VISIBLE);
            }
            
            // Set click listeners
            btnAddToCart.setOnClickListener(v -> {
                menuItem.incrementQuantity();
                notifyItemChanged(getAdapterPosition());
                if (listener != null) {
                    listener.onAddToCartClicked(menuItem);
                }
            });
            
            btnIncrement.setOnClickListener(v -> {
                menuItem.incrementQuantity();
                notifyItemChanged(getAdapterPosition());
                if (listener != null) {
                    listener.onAddToCartClicked(menuItem);
                }
            });
            
            btnDecrement.setOnClickListener(v -> {
                menuItem.decrementQuantity();
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
