package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.User;
import com.foodapp.utils.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvSignUp;
    private ImageView ivFacebook;
    private ImageView ivGoogle;
    private ImageView ivApple;
    
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Check if user is already logged in
        if (preferenceManager.getCurrentUser() != null) {
            navigateToMain();
            return;
        }

        // Initialize views
        initViews();
        
        // Set click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        ivFacebook = findViewById(R.id.ivFacebook);
        ivGoogle = findViewById(R.id.ivGoogle);
        ivApple = findViewById(R.id.ivApple);
    }
    
    private void setupClickListeners() {
        // Login button
        btnLogin.setOnClickListener(v -> {
            if (validateInputs()) {
                attemptLogin();
            }
        });
        
        // Sign up link
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
        
        // Social login buttons
        ivFacebook.setOnClickListener(v -> performSocialLogin("Facebook"));
        ivGoogle.setOnClickListener(v -> performSocialLogin("Google"));
        ivApple.setOnClickListener(v -> performSocialLogin("Apple"));
        
        // Forgot password
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Forgot password functionality will be implemented later", Toast.LENGTH_SHORT).show();
        });
    }
    
    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        boolean isValid = true;
        
        // Clear previous errors
        etEmail.setError(null);
        etPassword.setError(null);
        
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            isValid = false;
        }
        
        return isValid;
    }
    
    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        // Show loading state
        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");
        
        // Attempt login after a short delay to show the loading state
        btnLogin.postDelayed(() -> {
            // Attempt login
            User user = preferenceManager.loginUser(email, password);
            
            if (user != null) {
                // Set as current user
                preferenceManager.setCurrentUser(user);
                
                Toast.makeText(LoginActivity.this, "Welcome back, " + user.getFullName(), Toast.LENGTH_SHORT).show();
                navigateToMain();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
                btnLogin.setText("Login");
            }
        }, 800); // Short delay for UX purposes
    }
    
    private void performSocialLogin(String provider) {
        // Keep the social login buttons but just show a message
        Toast.makeText(this, provider + " login will be implemented in the future", Toast.LENGTH_SHORT).show();
        
        // For demo purposes, create a dummy user with better data
        User demoUser = new User(provider + " User", "", provider.toLowerCase() + "@example.com", "1234567890");
        demoUser.setPassword("password");
        preferenceManager.setCurrentUser(demoUser);
        
        navigateToTourGuide();
    }
    
    private void navigateToTourGuide() {
        // Navigate to tour guide (first time users)
        Intent intent = new Intent(LoginActivity.this, TourGuideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    
    private void navigateToMain() {
        // Navigate to home screen (returning users) or tour guide (first timers)
        if (preferenceManager.isFirstTimeLaunch()) {
            navigateToTourGuide();
        } else {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
    
    @Override
    public void onBackPressed() {
        // Navigate back to SplashActivity if possible, or just exit
        // This method is called when user presses the back button
        if (getIntent().hasExtra("from_splash")) {
            super.onBackPressed();
        } else {
            // Just finish and let the system decide
            finish();
        }
    }
}
