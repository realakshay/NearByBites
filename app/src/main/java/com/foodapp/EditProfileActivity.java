package com.foodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.User;
import com.foodapp.utils.UserManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView ivBack;
    private CircleImageView ivProfileImage;
    private View btnUploadPhoto;
    private View btnRemovePhoto;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPhoneNumber;
    private Button btnSave;
    private View btnManageAddresses;
    
    private UserManager userManager;
    private Uri selectedImageUri;
    
    // Activity result launcher for picking images
    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivProfileImage.setImageURI(uri);
                }
            }
    );
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        
        // Initialize UserManager
        userManager = UserManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Load user data
        loadUserData();
        
        // Set up button click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        btnRemovePhoto = findViewById(R.id.btnRemovePhoto);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);
        btnManageAddresses = findViewById(R.id.btnManageAddresses);
    }
    
    private void loadUserData() {
        User user = userManager.getCurrentUser();
        
        if (user != null) {
            // Set form data
            etFirstName.setText(user.getFirstName());
            etLastName.setText(user.getLastName());
            etEmail.setText(user.getEmail());
            etPhoneNumber.setText(user.getPhoneNumber());
            
            // Set profile image if available
            if (user.getProfileImageUri() != null && !user.getProfileImageUri().isEmpty()) {
                try {
                    Uri imageUri = Uri.parse(user.getProfileImageUri());
                    selectedImageUri = imageUri;
                    ivProfileImage.setImageURI(imageUri);
                } catch (Exception e) {
                    // If there's an error loading the image, use the default
                    ivProfileImage.setImageResource(R.drawable.profile_placeholder);
                }
            }
        }
    }
    
    private void setupClickListeners() {
        // Back button
        ivBack.setOnClickListener(v -> finish());
        
        // Upload photo button
        btnUploadPhoto.setOnClickListener(v -> {
            // Open image picker
            pickImage.launch("image/*");
        });
        
        // Remove photo button
        btnRemovePhoto.setOnClickListener(v -> {
            // Reset profile image
            ivProfileImage.setImageResource(R.drawable.profile_placeholder);
            selectedImageUri = null;
        });
        
        // Save button
        btnSave.setOnClickListener(v -> {
            saveUserData();
        });
        
        // Manage addresses button
        btnManageAddresses.setOnClickListener(v -> {
            // For demo, just show a toast
            Toast.makeText(this, "Manage Addresses clicked", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void saveUserData() {
        // Get data from form
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        
        // Validate data
        if (firstName.isEmpty()) {
            etFirstName.setError("First name is required");
            return;
        }
        
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return;
        }
        
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("Phone number is required");
            return;
        }
        
        // Update user data
        userManager.updateUserProfile(firstName, lastName, email, phoneNumber);
        
        // Update profile image if changed
        if (selectedImageUri != null) {
            userManager.setProfileImageUri(selectedImageUri.toString());
        } else if (selectedImageUri == null && userManager.getCurrentUser().getProfileImageUri() != null) {
            // If image was removed, clear the URI
            userManager.setProfileImageUri(null);
        }
        
        // Show success message
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        
        // Go back to profile screen
        finish();
    }
}
