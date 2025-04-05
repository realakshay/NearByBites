package com.foodapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Entry point, redirect to splash screen
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
}
