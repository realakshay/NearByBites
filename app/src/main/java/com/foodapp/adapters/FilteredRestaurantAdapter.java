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

public class FilteredRestaurantAdapter extends RecyclerView.Adapter<FilteredRestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> restaurants;
    private RestaurantClickListener listener;
    
    public interface RestaurantClickListener {
        void onRestaurantClicked(Restaurant restaurant);
    }
    
    public FilteredRestaurantAdapter(Context context, List<Restaurant> restaurants, RestaurantClickListener listener) {
        this.context = context;
        this.restaurants = restaurants;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_list, parent, false);
        return new RestaurantViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        
        // Set restaurant data
        holder.tvRestaurantName.setText(restaurant.getName());
        holder.tvRestaurantCuisine.setText(restaurant.getCuisine());
        holder.tvDeliveryTime.setText(restaurant.getDeliveryTime() + " mins");
        holder.tvRating.setText(String.format("%.1f/5", restaurant.getRating()));
        holder.tvDistance.setText(String.format("%.1fkm", restaurant.getDistance()));
        
        // Set restaurant image
        holder.ivRestaurantImage.setImageResource(restaurant.getImageResourceId());
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClicked(restaurant);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return restaurants.size();
    }
    
    public void updateRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = newRestaurants;
        notifyDataSetChanged();
    }
    
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurantImage;
        TextView tvRestaurantName;
        TextView tvRestaurantCuisine;
        TextView tvDeliveryTime;
        TextView tvRating;
        TextView tvDistance;
        
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvRestaurantCuisine = itemView.findViewById(R.id.tvRestaurantCuisine);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDistance = itemView.findViewById(R.id.tvDistance);
        }
    }
}
