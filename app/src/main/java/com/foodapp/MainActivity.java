package com.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set up the "Start Ordering" button
        Button startOrderingButton = findViewById(R.id.btnStartOrdering);
        startOrderingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For now, we'll just show a toast message
                Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
                
                // For demonstration purposes, we would navigate to a login activity
                // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // startActivity(intent);
            }
        });
    }
}
