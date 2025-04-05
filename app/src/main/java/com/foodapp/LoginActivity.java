package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.foodapp.models.User;
import com.foodapp.utils.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    private ConstraintLayout containerSignIn;
    private ConstraintLayout containerSignUp;
    private Button btnSignIn;
    private Button btnSignUp;
    private TextView tvSwitchToSignUp;
    private TextView tvSwitchToSignIn;
    private TextView tvForgotPassword;
    private ImageView ivFacebook;
    private ImageView ivGoogle;
    private ImageView ivApple;
    
    // Sign In inputs
    private EditText etEmailSignIn;
    private EditText etPasswordSignIn;
    
    // Sign Up inputs
    private EditText etNameSignUp;
    private EditText etEmailSignUp;
    private EditText etPhoneSignUp;
    private EditText etPasswordSignUp;
    
    private boolean isSignUpMode = false;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Check if user is already logged in
        if (preferenceManager.getCurrentUser() != null) {
            navigateToTourGuide();
            return;
        }

        // Get mode from intent
        if (getIntent() != null && getIntent().hasExtra("is_signup_mode")) {
            isSignUpMode = getIntent().getBooleanExtra("is_signup_mode", false);
        }

        // Initialize views
        initViews();
        
        // Set initial mode (sign in or sign up)
        updateViewVisibility();
        
        // Set click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        // Containers
        containerSignIn = findViewById(R.id.containerSignIn);
        containerSignUp = findViewById(R.id.containerSignUp);
        
        // Buttons
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        
        // Text views for switching modes
        tvSwitchToSignUp = findViewById(R.id.tvSwitchToSignUp);
        tvSwitchToSignIn = findViewById(R.id.tvSwitchToSignIn);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        
        // Social login icons
        ivFacebook = findViewById(R.id.ivFacebook);
        ivGoogle = findViewById(R.id.ivGoogle);
        ivApple = findViewById(R.id.ivApple);
        
        // Sign In inputs
        etEmailSignIn = findViewById(R.id.etEmailSignIn);
        etPasswordSignIn = findViewById(R.id.etPasswordSignIn);
        
        // Sign Up inputs
        etNameSignUp = findViewById(R.id.etNameSignUp);
        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPhoneSignUp = findViewById(R.id.etPhoneSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
    }
    
    private void updateViewVisibility() {
        if (isSignUpMode) {
            containerSignIn.setVisibility(View.GONE);
            containerSignUp.setVisibility(View.VISIBLE);
        } else {
            containerSignIn.setVisibility(View.VISIBLE);
            containerSignUp.setVisibility(View.GONE);
        }
    }
    
    private void setupClickListeners() {
        // Switch mode listeners
        tvSwitchToSignUp.setOnClickListener(v -> {
            isSignUpMode = true;
            updateViewVisibility();
        });
        
        tvSwitchToSignIn.setOnClickListener(v -> {
            isSignUpMode = false;
            updateViewVisibility();
        });
        
        // Sign In button clicked
        btnSignIn.setOnClickListener(v -> {
            if (validateSignInInputs()) {
                String email = etEmailSignIn.getText().toString().trim();
                String password = etPasswordSignIn.getText().toString().trim();
                
                // Authenticate user
                User user = preferenceManager.loginUser(email, password);
                
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Welcome back, " + user.getName(), Toast.LENGTH_SHORT).show();
                    navigateToTourGuide();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        // Sign Up button clicked
        btnSignUp.setOnClickListener(v -> {
            if (validateSignUpInputs()) {
                String name = etNameSignUp.getText().toString().trim();
                String email = etEmailSignUp.getText().toString().trim();
                String phone = etPhoneSignUp.getText().toString().trim();
                String password = etPasswordSignUp.getText().toString().trim();
                
                // Create new user
                User newUser = new User(name, email, phone, password);
                
                // Register user
                if (preferenceManager.registerUser(newUser)) {
                    // Set as current user
                    preferenceManager.setCurrentUser(newUser);
                    
                    Toast.makeText(LoginActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    navigateToTourGuide();
                } else {
                    Toast.makeText(LoginActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                }
            }
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
    
    private boolean validateSignInInputs() {
        String email = etEmailSignIn.getText().toString().trim();
        String password = etPasswordSignIn.getText().toString().trim();
        
        if (email.isEmpty()) {
            etEmailSignIn.setError("Email is required");
            return false;
        }
        
        if (password.isEmpty()) {
            etPasswordSignIn.setError("Password is required");
            return false;
        }
        
        return true;
    }
    
    private boolean validateSignUpInputs() {
        String name = etNameSignUp.getText().toString().trim();
        String email = etEmailSignUp.getText().toString().trim();
        String phone = etPhoneSignUp.getText().toString().trim();
        String password = etPasswordSignUp.getText().toString().trim();
        
        if (name.isEmpty()) {
            etNameSignUp.setError("Name is required");
            return false;
        }
        
        if (email.isEmpty()) {
            etEmailSignUp.setError("Email is required");
            return false;
        }
        
        if (phone.isEmpty()) {
            etPhoneSignUp.setError("Phone is required");
            return false;
        }
        
        if (password.isEmpty()) {
            etPasswordSignUp.setError("Password is required");
            return false;
        }
        
        return true;
    }
    
    private void performSocialLogin(String provider) {
        // Keep the social login buttons but just show a message
        Toast.makeText(this, provider + " login will be implemented in the future", Toast.LENGTH_SHORT).show();
    }
    
    private void navigateToTourGuide() {
        Intent intent = new Intent(LoginActivity.this, TourGuideActivity.class);
        startActivity(intent);
        finish();
    }
}
