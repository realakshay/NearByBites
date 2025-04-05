package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.foodapp.adapters.TourGuideAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TourGuideActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button btnNext;
    private TextView tvSkip;
    private TabLayout tabLayout;
    private final int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);

        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        tvSkip = findViewById(R.id.tvSkip);
        tabLayout = findViewById(R.id.tabLayout);

        // Set up the adapter
        TourGuideAdapter adapter = new TourGuideAdapter(this, NUM_PAGES);
        viewPager.setAdapter(adapter);

        // Connect the tab layout with the view pager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // No title for tabs
        }).attach();

        // Set up the next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < NUM_PAGES - 1) {
                    // Go to the next page
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    // On the last page, proceed to the main activity
                    finishTourGuide();
                }
            }
        });

        // Skip button to bypass tour guide
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTourGuide();
            }
        });

        // Page change listener to update button text
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Change text if last page
                if (position == NUM_PAGES - 1) {
                    btnNext.setText(R.string.get_started);
                } else {
                    btnNext.setText(R.string.next);
                }
            }
        });
    }

    private void finishTourGuide() {
        // Navigate to the restaurant list screen
        Intent intent = new Intent(TourGuideActivity.this, RestaurantListActivity.class);
        startActivity(intent);
        finish();
    }
}