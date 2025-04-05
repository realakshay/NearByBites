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
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_restaurant, parent, false);
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

    public void updateRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = newRestaurants;
        notifyDataSetChanged();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurantImage;
        TextView tvRestaurantName;
        TextView tvCuisine;
        TextView tvDeliveryTime;
        TextView tvRating;
        TextView tvDistance;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvCuisine = itemView.findViewById(R.id.tvCuisine);
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDistance = itemView.findViewById(R.id.tvDistance);

            // Item click listener
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRestaurantClicked(restaurants.get(position));
                }
            });
        }

        void bind(Restaurant restaurant) {
            // Set restaurant data
            ivRestaurantImage.setImageResource(restaurant.getImageResourceId());
            tvRestaurantName.setText(restaurant.getName());
            tvCuisine.setText(restaurant.getCuisine());
            tvDeliveryTime.setText(restaurant.getDeliveryTime() + "mins");
            tvRating.setText(String.valueOf(restaurant.getRating()));
            tvDistance.setText(restaurant.getDistance() + "km");
        }
    }
}
