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

    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_guide, container, false);
        
        ImageView ivTourImage = view.findViewById(R.id.ivTourImage);
        TextView tvTourTitle = view.findViewById(R.id.tvTourTitle);
        TextView tvTourDescription = view.findViewById(R.id.tvTourDescription);
        
        // Set the content based on position
        switch (position) {
            case 0:
                ivTourImage.setImageResource(R.drawable.ic_location);
                tvTourTitle.setText("Find Restaurants Near You");
                tvTourDescription.setText("Discover the best restaurants, cafes, and bars in your area.");
                break;
            case 1:
                ivTourImage.setImageResource(R.drawable.ic_meal);
                tvTourTitle.setText("Choose Your Favorite Food");
                tvTourDescription.setText("Browse menus and select from a variety of delicious meals.");
                break;
            case 2:
                ivTourImage.setImageResource(R.drawable.star);
                tvTourTitle.setText("Fast Delivery");
                tvTourDescription.setText("Get your food delivered to your doorstep with fast and reliable service.");
                break;
        }
        
        return view;
    }
}
