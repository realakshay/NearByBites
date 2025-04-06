package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.User;
import com.foodapp.utils.PreferenceManager;

public class SignupActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private CheckBox cbTerms;
    private Button btnSignUp;
    private TextView tvLoginLink;
    
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(this);
        
        // Initialize views
        initViews();
        
        // Set up listeners
        setupListeners();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        cbTerms = findViewById(R.id.cbTerms);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        
        // Set title
        tvTitle.setText("CREATE ACCOUNT");
    }
    
    private void setupListeners() {
        // Back button click listener
        ivBack.setOnClickListener(v -> finish());
        
        // Login link click listener
        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        
        // Signup button click listener
        btnSignUp.setOnClickListener(v -> {
            if (validateInputs()) {
                createAccount();
            }
        });
    }
    
    private boolean validateInputs() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        
        boolean isValid = true;
        
        // Clear previous errors
        etFirstName.setError(null);
        etLastName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);
        
        // Validate first name
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("First name is required");
            isValid = false;
        }
        
        // Validate last name
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Last name is required");
            isValid = false;
        }
        
        // Validate email
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            isValid = false;
        }
        
        // Validate phone
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone number is required");
            isValid = false;
        } else if (phone.length() < 10) {
            etPhone.setError("Please enter a valid phone number");
            isValid = false;
        }
        
        // Validate password
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }
        
        // Validate confirm password
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }
        
        // Validate terms and conditions
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "You must agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        
        return isValid;
    }
    
    private void createAccount() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        // Show loading state
        btnSignUp.setEnabled(false);
        btnSignUp.setText("Creating Account...");
        
        // Delay to show loading state
        btnSignUp.postDelayed(() -> {
            // Create user object
            User newUser = new User(firstName, lastName, email, phone);
            newUser.setPassword(password);
            
            // Register user
            if (preferenceManager.registerUser(newUser)) {
                // Set as current user
                preferenceManager.setCurrentUser(newUser);
                
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                
                // Navigate to tour guide for first-time users
                Intent intent = new Intent(SignupActivity.this, TourGuideActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show();
                btnSignUp.setEnabled(true);
                btnSignUp.setText("CREATE ACCOUNT");
                etEmail.requestFocus();
                etEmail.setError("Email already exists");
            }
        }, 800); // Short delay for UX purposes
    }
    
    @Override
    public void onBackPressed() {
        // Simply go back to previous screen, typically LoginActivity
        super.onBackPressed();
    }

    public boolean registerUser(User user) {
        // Dummy logic for now
        return true;
    }
}
