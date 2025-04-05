package com.foodapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.RestaurantDetailsActivity;
import com.foodapp.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    
    private List<Restaurant> restaurants;
    private OnRestaurantClickListener listener;
    
    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant, int position);
    }
    
    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantClickListener listener) {
        this.restaurants = restaurants;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.bind(restaurant, position);
    }
    
    @Override
    public int getItemCount() {
        return restaurants.size();
    }
    
    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurantImage;
        TextView tvRestaurantName;
        TextView tvCategory;
        TextView tvRating;
        TextView tvDistance;
        TextView tvDeliveryTime;
        TextView tvPriceRange;
        
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvPriceRange = itemView.findViewById(R.id.tvPriceRange);
        }
        
        public void bind(final Restaurant restaurant, final int position) {
            tvRestaurantName.setText(restaurant.getName());
            tvCategory.setText(restaurant.getCategory());
            tvRating.setText(String.valueOf(restaurant.getRating()));
            tvDistance.setText(restaurant.getDistance() + " km");
            tvDeliveryTime.setText(restaurant.getDeliveryTime());
            tvPriceRange.setText(restaurant.getPriceRange());
            
            // Load image using Picasso
            if (restaurant.getImageUrl() != null && !restaurant.getImageUrl().isEmpty()) {
                Picasso.get().load(restaurant.getImageUrl()).into(ivRestaurantImage);
            }
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onRestaurantClick(restaurant, position);
                        
                        // Launch RestaurantDetailsActivity
                        Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
                        intent.putExtra("restaurant_id", restaurant.getId());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
