package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.foodapp.models.User;
import com.google.gson.Gson;

public class UserManager {
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_USER_DATA = "user_data";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    
    private static UserManager instance;
    private SharedPreferences preferences;
    private Gson gson;
    
    private User currentUser;
    
    private UserManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadUserData();
    }
    
    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }
    
    private void loadUserData() {
        String userJson = preferences.getString(KEY_USER_DATA, null);
        if (userJson != null) {
            currentUser = gson.fromJson(userJson, User.class);
        } else {
            // Create a default user if none exists
            currentUser = new User("John", "Doe", "john.doe@example.com", "1234567890");
            saveUserData();
        }
    }
    
    public void saveUserData() {
        String userJson = gson.toJson(currentUser);
        preferences.edit().putString(KEY_USER_DATA, userJson).apply();
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(User user) {
        currentUser = user;
        saveUserData();
    }
    
    public void updateUserProfile(String firstName, String lastName, String email, String phoneNumber) {
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setPhoneNumber(phoneNumber);
        saveUserData();
    }
    
    public void setProfileImageUri(String imageUri) {
        currentUser.setProfileImageUri(imageUri);
        saveUserData();
    }
    
    public void setLoggedIn(boolean isLoggedIn) {
        preferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }
    
    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    public void logout() {
        setLoggedIn(false);
    }
}
