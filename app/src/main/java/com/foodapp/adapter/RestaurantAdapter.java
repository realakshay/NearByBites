package com.foodapp.adapter;

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
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> restaurants;
    private RestaurantClickListener listener;

    public interface RestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

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
        
        holder.tvName.setText(restaurant.getName());
        holder.tvCuisine.setText(restaurant.getCuisine());
        holder.tvDeliveryTime.setText(restaurant.getDeliveryTime() + " min");
        holder.tvDistance.setText(restaurant.getDistance() + " km");
        
        if (restaurant.getImageResourceId() != 0) {
            holder.imageView.setImageResource(restaurant.getImageResourceId());
        } else if (restaurant.getImageUrl() != null && !restaurant.getImageUrl().isEmpty()) {
            Picasso.get().load(restaurant.getImageUrl()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants != null ? restaurants.size() : 0;
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvCuisine;
        TextView tvDeliveryTime;
        TextView tvDistance;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivRestaurant);
            tvName = itemView.findViewById(R.id.tvRestaurantName);
            tvCuisine = itemView.findViewById(R.id.tvCuisine);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvDistance = itemView.findViewById(R.id.tvDistance);
        }
    }
}
