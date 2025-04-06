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
import com.foodapp.utils.FavoriteManager;

import java.util.List;

public class FavoriteRestaurantAdapter extends RecyclerView.Adapter<FavoriteRestaurantAdapter.FavoriteViewHolder> {

    private Context context;
    private List<Restaurant> restaurants;
    private FavoriteRestaurantListener listener;
    private FavoriteManager favoriteManager;
    
    public interface FavoriteRestaurantListener {
        void onRestaurantClicked(Restaurant restaurant);
        void onFavoriteToggled(Restaurant restaurant, boolean isFavorite);
    }
    
    public FavoriteRestaurantAdapter(Context context, List<Restaurant> restaurants, FavoriteRestaurantListener listener) {
        this.context = context;
        this.restaurants = restaurants;
        this.listener = listener;
        this.favoriteManager = FavoriteManager.getInstance(context);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_restaurant, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        
        // Set restaurant data
        holder.tvRestaurantName.setText(restaurant.getName());
        holder.tvRestaurantCuisine.setText(restaurant.getCuisine());
        holder.tvDeliveryTime.setText(restaurant.getDeliveryTime() + " mins");
        holder.tvRating.setText(String.valueOf(restaurant.getRating()));
        holder.tvDistance.setText(restaurant.getDistance() + " km");
        
        // Set restaurant image
        holder.ivRestaurantImage.setImageResource(restaurant.getImageResourceId());
        
        // Set favorite status
        boolean isFavorite = favoriteManager.isFavorite(restaurant.getId());
        holder.ivFavorite.setImageResource(isFavorite ? 
                R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
        
        // Set click listener on the entire card
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClicked(restaurant);
            }
        });
        
        // Set click listener on the favorite icon only
        holder.ivFavorite.setOnClickListener(v -> {
            if (listener != null) {
                boolean newFavoriteStatus = !favoriteManager.isFavorite(restaurant.getId());
                listener.onFavoriteToggled(restaurant, newFavoriteStatus);
                
                // Update icon
                holder.ivFavorite.setImageResource(newFavoriteStatus ? 
                        R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
    
    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurantImage;
        TextView tvRestaurantName;
        TextView tvRestaurantCuisine;
        TextView tvDeliveryTime;
        TextView tvRating;
        TextView tvDistance;
        ImageView ivFavorite;
        
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvRestaurantCuisine = itemView.findViewById(R.id.tvRestaurantCuisine);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}
