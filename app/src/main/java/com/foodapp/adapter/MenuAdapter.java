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

import java.text.DecimalFormat;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder> {

    private List<MenuItem> menuItems;
    private MenuItemClickListener listener;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public interface MenuItemClickListener {
        void onAddToCart(MenuItem menuItem);
    }

    public MenuAdapter(List<MenuItem> menuItems, MenuItemClickListener listener) {
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

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFoodImage;
        private TextView tvItemName;
        private TextView tvItemDescription;
        private TextView tvItemPrice;
        private Button btnAddToCart;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }

        public void bind(final MenuItem menuItem) {
            tvItemName.setText(menuItem.getName());
            tvItemDescription.setText(menuItem.getDescription());
            tvItemPrice.setText("$" + decimalFormat.format(menuItem.getPrice()));

            // Load image using Glide
            // Since we don't have real image URLs, we'll use a placeholder with item name
            Glide.with(itemView.getContext())
                    .load("https://via.placeholder.com/150x150?text=" + menuItem.getName().replace(" ", "+"))
                    .placeholder(R.drawable.placeholder_food)
                    .error(R.drawable.placeholder_food)
                    .into(ivFoodImage);

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAddToCart(menuItem);
                    }
                }
            });
        }
    }
}
