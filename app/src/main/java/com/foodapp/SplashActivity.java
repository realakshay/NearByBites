package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvAppName;
    private Button btnSignUp;
    private Button btnSignIn;
    private View containerButtons;
    private PreferenceManager preferenceManager;

    private static final int SPLASH_DISPLAY_TIME = 1500; // 1.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // If user is already logged in, skip to main flow
        if (preferenceManager.getCurrentUser() != null) {
            // Short delay for splash screen effect
            new Handler(Looper.getMainLooper()).postDelayed(this::navigateToMainFlow, SPLASH_DISPLAY_TIME);
            return;
        }

        // Initialize views
        ivLogo = findViewById(R.id.ivLogo);
        tvAppName = findViewById(R.id.tvAppName);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        containerButtons = findViewById(R.id.containerButtons);
        
        // Initial state
        containerButtons.setVisibility(View.INVISIBLE);
        
        // Animate the logo and app name
        setupSplashAnimation();

        // Set click listeners
        btnSignUp.setOnClickListener(v -> {
            // Navigate to Signup Activity
            Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
            intent.putExtra("from_splash", true);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(v -> {
            // Navigate to Login Activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.putExtra("from_splash", true);
            startActivity(intent);
        });
    }
    
    private void setupSplashAnimation() {
        // Logo scale and fade animation
        AnimationSet scaleAndFade = new android.view.animation.AnimationSet(true);
        Animation scale = new android.view.animation.ScaleAnimation(
            0.5f, 1.0f, 0.5f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        
        scale.setDuration(1000);
        fadeIn.setDuration(1000);
        
        scaleAndFade.addAnimation(scale);
        scaleAndFade.addAnimation(fadeIn);
        
        scaleAndFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            
            @Override
            public void onAnimationEnd(Animation animation) {
                // Show buttons with bounce effect
                containerButtons.setVisibility(View.VISIBLE);
                Animation buttonAnimation = new android.view.animation.TranslateAnimation(
                    0, 0, 50, 0
                );
                buttonAnimation.setDuration(500);
                buttonAnimation.setInterpolator(new android.view.animation.OvershootInterpolator());
                containerButtons.startAnimation(buttonAnimation);
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        
        ivLogo.startAnimation(scaleAndFade);
        tvAppName.startAnimation(scaleAndFade);
    }
    
    private void navigateToMainFlow() {
        Intent intent;
        
        if (preferenceManager.isFirstTimeLaunch()) {
            // First time user goes to tour guide
            intent = new Intent(SplashActivity.this, TourGuideActivity.class);
        } else {
            // Returning user goes directly to home
            intent = new Intent(SplashActivity.this, HomeActivity.class);
        }
        
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onBackPressed() {
        // Exit the app if back is pressed from the splash screen
        finish();
    }
}
