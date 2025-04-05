package com.foodapp.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.foodapp.fragments.TourGuideFragment;

public class TourGuideAdapter extends FragmentStateAdapter {

    private final int numPages;

    public TourGuideAdapter(@NonNull FragmentActivity fragmentActivity, int numPages) {
        super(fragmentActivity);
        this.numPages = numPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create a new fragment for each page
        TourGuideFragment fragment = new TourGuideFragment();
        
        // Pass the page number to the fragment
        Bundle args = new Bundle();
        args.putInt("page_position", position);
        fragment.setArguments(args);
        
        return fragment;
    }

    @Override
    public int getItemCount() {
        return numPages;
    }
}