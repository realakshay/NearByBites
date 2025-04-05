package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.foodapp.adapters.TourGuideAdapter;
import com.foodapp.utils.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TourGuideActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button btnNext;
    private TextView tvSkip;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private final int NUM_PAGES = 3;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);

        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        tvSkip = findViewById(R.id.tvSkip);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbar);
        
        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tour Guide");
        }

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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tour, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_logout) {
            logoutUser();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private void logoutUser() {
        preferenceManager.logoutUser();
        
        // Navigate back to login screen
        Intent intent = new Intent(TourGuideActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void finishTourGuide() {
        // Navigate to the location selection screen
        Intent intent = new Intent(TourGuideActivity.this, LocationSelectionActivity.class);
        startActivity(intent);
        finish();
    }
}
