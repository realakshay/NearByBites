package com.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodapp.R;
import com.foodapp.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants;
    private RestaurantClickListener listener;

    public interface RestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantAdapter(List<Restaurant> restaurants, RestaurantClickListener listener) {
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
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivRestaurantImage;
        private TextView tvRestaurantName;
        private TextView tvCuisine;
        private TextView tvRating;
        private TextView tvDeliveryTime;
        private TextView tvStatus;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvCuisine = itemView.findViewById(R.id.tvCuisine);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onRestaurantClick(restaurants.get(position));
                    }
                }
            });
        }

        public void bind(Restaurant restaurant) {
            tvRestaurantName.setText(restaurant.getName());
            tvCuisine.setText(restaurant.getCuisine());
            tvRating.setText(String.format("%.1f★", restaurant.getRating()));
            tvDeliveryTime.setText(String.format("%d-%d min", restaurant.getMinDeliveryTime(), restaurant.getMaxDeliveryTime()));
            
            if (restaurant.isOpen()) {
                tvStatus.setText("Open");
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvStatus.setText("Closed");
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
            }

            // Load image using Glide
            // Since we don't have real image URLs, we'll use a placeholder with restaurant name
            Glide.with(itemView.getContext())
                    .load("https://via.placeholder.com/200x150?text=" + restaurant.getName().replace(" ", "+"))
                    .placeholder(R.drawable.placeholder_restaurant)
                    .error(R.drawable.placeholder_restaurant)
                    .into(ivRestaurantImage);
        }
    }
}
