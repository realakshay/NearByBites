package com.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.models.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    
    private final Context context;
    private final List<Restaurant> restaurants;
    private final RestaurantClickListener listener;
    
    public RestaurantAdapter(Context context, List<Restaurant> restaurants, RestaurantClickListener listener) {
        this.context = context;
        this.restaurants = restaurants;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        
        holder.tvRestaurantName.setText(restaurant.getName());
        holder.tvCuisine.setText(restaurant.getCuisine());
        holder.tvRating.setText(String.valueOf(restaurant.getRating()));
        holder.tvDeliveryTime.setText(String.valueOf(restaurant.getDeliveryTime()) + " min");
        holder.tvDistance.setText(String.valueOf(restaurant.getDistance()) + " km");
        holder.ivRestaurantImage.setImageResource(restaurant.getImageResourceId());
        
        // Set favorite icon
        if (restaurant.isFavorite()) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
        
        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClicked(restaurant);
            }
        });
        
        holder.ivFavorite.setOnClickListener(v -> {
            // Toggle favorite status
            restaurant.setFavorite(!restaurant.isFavorite());
            
            // Update favorite icon
            if (restaurant.isFavorite()) {
                holder.ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
            } else {
                holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return restaurants.size();
    }
    
    public void updateRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants.clear();
        this.restaurants.addAll(newRestaurants);
        notifyDataSetChanged();
    }
    
    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurantImage;
        TextView tvRestaurantName;
        TextView tvCuisine;
        TextView tvRating;
        TextView tvDeliveryTime;
        TextView tvDistance;
        ImageView ivFavorite;
        
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvCuisine = itemView.findViewById(R.id.tvCuisine);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
    
    public interface RestaurantClickListener {
        void onRestaurantClicked(Restaurant restaurant);
    }
}
