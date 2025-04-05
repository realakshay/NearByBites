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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pagePosition = getArguments().getInt("page_position", 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour_guide, container, false);
        
        // Initialize views
        ImageView ivTourIcon = view.findViewById(R.id.ivTourIcon);
        TextView tvTourTitle = view.findViewById(R.id.tvTourTitle);
        TextView tvTourDescription = view.findViewById(R.id.tvTourDescription);
        
        // Set content based on page position
        switch (pagePosition) {
            case 0:
                // First page - Order your favorite
                ivTourIcon.setImageResource(R.drawable.tour_order);
                tvTourTitle.setText(R.string.tour_order_title);
                tvTourDescription.setText(R.string.tour_order_desc);
                break;
            case 1:
                // Second page - Fast Delivery
                ivTourIcon.setImageResource(R.drawable.tour_delivery);
                tvTourTitle.setText(R.string.tour_delivery_title);
                tvTourDescription.setText(R.string.tour_delivery_desc);
                break;
            case 2:
                // Third page - Live Tracking
                ivTourIcon.setImageResource(R.drawable.tour_tracking);
                tvTourTitle.setText(R.string.tour_tracking_title);
                tvTourDescription.setText(R.string.tour_tracking_desc);
                break;
        }
        
        return view;
    }
}