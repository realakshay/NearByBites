package com.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignUp, tvSignIn;
    private View layoutLogin, layoutSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
        layoutLogin = findViewById(R.id.layoutLogin);
        layoutSignUp = findViewById(R.id.layoutSignUp);

        // Set click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate login credentials
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // For now, we'll just navigate to the Tour Guide activity
                    loginSuccess();
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show forgot password dialog or navigate to forgot password screen
                Toast.makeText(LoginActivity.this, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show sign up screen
                showSignUpLayout();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show login screen
                showLoginLayout();
            }
        });

        // Social login listeners
        findViewById(R.id.ivGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Google login
                Toast.makeText(LoginActivity.this, "Google login clicked", Toast.LENGTH_SHORT).show();
                loginSuccess();
            }
        });

        findViewById(R.id.ivFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Facebook login
                Toast.makeText(LoginActivity.this, "Facebook login clicked", Toast.LENGTH_SHORT).show();
                loginSuccess();
            }
        });

        findViewById(R.id.ivApple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Apple login
                Toast.makeText(LoginActivity.this, "Apple login clicked", Toast.LENGTH_SHORT).show();
                loginSuccess();
            }
        });
    }

    private void showLoginLayout() {
        layoutLogin.setVisibility(View.VISIBLE);
        layoutSignUp.setVisibility(View.GONE);
        btnLogin.setText("LOGIN");
    }

    private void showSignUpLayout() {
        layoutLogin.setVisibility(View.GONE);
        layoutSignUp.setVisibility(View.VISIBLE);
        btnLogin.setText("SIGN UP");
    }

    /**
     * Called when login is successful
     * Navigates to the Tour Guide screen
     */
    private void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, TourGuideActivity.class);
        startActivity(intent);
        finish();
    }
}