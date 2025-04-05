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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PromoItemAdapter extends RecyclerView.Adapter<PromoItemAdapter.PromoItemViewHolder> {
    
    private List<String> promos;
    private List<MenuItem> menuItems; // To get images and prices for promos
    private Random random;
    
    public PromoItemAdapter(List<String> promos, List<MenuItem> menuItems) {
        this.promos = promos;
        this.menuItems = menuItems;
        this.random = new Random();
    }
    
    @NonNull
    @Override
    public PromoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo, parent, false);
        return new PromoItemViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PromoItemViewHolder holder, int position) {
        String promoText = promos.get(position);
        
        // Get a random menu item to display with the promo
        MenuItem randomMenuItem = null;
        if (menuItems != null && !menuItems.isEmpty()) {
            randomMenuItem = menuItems.get(random.nextInt(menuItems.size()));
        }
        
        holder.bind(promoText, randomMenuItem, position);
    }
    
    @Override
    public int getItemCount() {
        return promos.size();
    }
    
    class PromoItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPromoImage;
        TextView tvPromoDescription;
        TextView tvPromoDiscount;
        TextView tvPromoPrice;
        
        public PromoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPromoImage = itemView.findViewById(R.id.ivPromoImage);
            tvPromoDescription = itemView.findViewById(R.id.tvPromoDescription);
            tvPromoDiscount = itemView.findViewById(R.id.tvPromoDiscount);
            tvPromoPrice = itemView.findViewById(R.id.tvPromoPrice);
        }
        
        public void bind(String promoText, MenuItem menuItem, int position) {
            // Set promo description
            tvPromoDescription.setText(promoText);
            
            // Generate random discount between 10% and 30%
            int discount = 10 + random.nextInt(21); // 10 to 30
            tvPromoDiscount.setText("-" + discount + "%");
            
            // Set price based on menu item (if available)
            if (menuItem != null) {
                double originalPrice = menuItem.getPrice();
                double discountedPrice = originalPrice * (1 - discount / 100.0);
                tvPromoPrice.setText("$" + String.format("%.0f", discountedPrice));
                
                // Load image using Picasso
                if (menuItem.getImageUrl() != null && !menuItem.getImageUrl().isEmpty()) {
                    Picasso.get().load(menuItem.getImageUrl()).into(ivPromoImage);
                }
            } else {
                tvPromoPrice.setText("$" + (5 + random.nextInt(16))); // Random price between $5 and $20
            }
            
            // Set background color based on position
            int colorRes;
            if (position % 3 == 0) {
                colorRes = R.color.colorPromoRed;
            } else if (position % 3 == 1) {
                colorRes = R.color.colorPromoBlue;
            } else {
                colorRes = R.color.colorPromoGreen;
            }
            tvPromoDiscount.setBackgroundResource(colorRes);
        }
    }
}
