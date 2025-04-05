package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Button btnSignUp;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize buttons
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        // Set click listeners
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login activity in registration mode
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.putExtra("MODE", "REGISTER");
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login activity in login mode
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.putExtra("MODE", "LOGIN");
                startActivity(intent);
            }
        });
    }
}
