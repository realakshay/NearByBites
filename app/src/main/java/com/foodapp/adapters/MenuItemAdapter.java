package com.foodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.models.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {
    
    private List<MenuItem> menuItems;
    private OnMenuItemClickListener listener;
    
    public interface OnMenuItemClickListener {
        void onMenuItemClick(MenuItem menuItem);
    }
    
    public MenuItemAdapter(List<MenuItem> menuItems, OnMenuItemClickListener listener) {
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
        ImageView ivItemImage;
        TextView tvItemName;
        TextView tvItemPrice;
        
        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
        }
        
        public void bind(final MenuItem menuItem) {
            tvItemName.setText(menuItem.getName());
            tvItemPrice.setText("$" + String.format("%.0f", menuItem.getPrice()));
            
            // Load image using Picasso
            if (menuItem.getImageUrl() != null && !menuItem.getImageUrl().isEmpty()) {
                Picasso.get().load(menuItem.getImageUrl()).into(ivItemImage);
            }
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onMenuItemClick(menuItem);
                    }
                }
            });
        }
    }
}
