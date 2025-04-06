package com.foodapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foodapp.R;

public class TourGuideFragment extends Fragment {
    
    private int pagePosition;
    
    // Tour guide content arrays
    private final int[] IMAGE_RESOURCES = {
            R.drawable.tour_delivery,
            R.drawable.tour_order,
            R.drawable.tour_tracking
    };
    
    private final String[] TITLES = {
            "Find your favorite food",
            "Fast delivery to your door",
            "Track your order in real-time"
    };
    
    private final String[] DESCRIPTIONS = {
            "Discover restaurants that deliver to your location with diverse cuisines and options.",
            "Our delivery partners ensure your food arrives hot and fresh in the shortest time possible.",
            "Follow your order from the restaurant to your doorstep with live GPS tracking."
    };
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get page position from arguments
        if (getArguments() != null) {
            pagePosition = getArguments().getInt("page_position", 0);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_guide, container, false);
        
        // Initialize views
        ImageView ivTourImage = view.findViewById(R.id.ivTourImage);
        TextView tvTourTitle = view.findViewById(R.id.tvTourTitle);
        TextView tvTourDescription = view.findViewById(R.id.tvTourDescription);
        
        // Set content based on page position
        if (pagePosition < IMAGE_RESOURCES.length) {
            ivTourImage.setImageResource(IMAGE_RESOURCES[pagePosition]);
            tvTourTitle.setText(TITLES[pagePosition]);
            tvTourDescription.setText(DESCRIPTIONS[pagePosition]);
        }
        
        return view;
    }
}
