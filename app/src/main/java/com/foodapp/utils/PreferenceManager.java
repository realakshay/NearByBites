package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.Address;
import com.foodapp.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to manage shared preferences data
 */
public class PreferenceManager {
    
    // SharedPreferences file names
    private static final String PREF_APP = "grub_app_preferences";
    private static final String PREF_USER = "grub_user_preferences";
    
    // Keys for app preferences
    private static final String KEY_FIRST_TIME_LAUNCH = "first_time_launch";
    private static final String KEY_CURRENT_USER = "current_user";
    private static final String KEY_SELECTED_ADDRESS = "selected_address";
    
    private final SharedPreferences appPref;
    private final SharedPreferences userPref;
    private final SharedPreferences.Editor appEditor;
    private final SharedPreferences.Editor userEditor;
    private final Gson gson;
    
    // In-memory map of users for demo purposes
    private static final Map<String, User> registeredUsers = new HashMap<>();
    
    public PreferenceManager(Context context) {
        appPref = context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE);
        userPref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        appEditor = appPref.edit();
        userEditor = userPref.edit();
        gson = new Gson();
    }
    
    /**
     * Set first time launch flag
     */
    public void setFirstTimeLaunch(boolean isFirstTime) {
        appEditor.putBoolean(KEY_FIRST_TIME_LAUNCH, isFirstTime);
        appEditor.apply();
    }
    
    /**
     * Check if it's first time launch
     */
    public boolean isFirstTimeLaunch() {
        return appPref.getBoolean(KEY_FIRST_TIME_LAUNCH, true);
    }
    
    /**
     * Register a new user
     */
    public boolean registerUser(User user) {
        // Check if email is already registered
        if (registeredUsers.containsKey(user.getEmail())) {
            return false;
        }
        
        // Store user in memory map
        registeredUsers.put(user.getEmail(), user);
        
        // Save list of registered emails
        saveRegisteredUsers();
        
        return true;
    }
    
    /**
     * Attempt to login a user
     */
    public User loginUser(String email, String password) {
        // Get user from memory map
        User user = registeredUsers.get(email);
        
        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        
        return null;
    }
    
    /**
     * Retrieve list of registered users
     */
    private List<String> getRegisteredUserEmails() {
        String json = userPref.getString("registered_users", null);
        
        if (json != null) {
            try {
                String[] emails = gson.fromJson(json, String[].class);
                List<String> emailList = new ArrayList<>();
                
                for (String email : emails) {
                    emailList.add(email);
                }
                
                return emailList;
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
        
        return new ArrayList<>();
    }
    
    /**
     * Save the list of registered user emails
     */
    private void saveRegisteredUsers() {
        String[] emails = registeredUsers.keySet().toArray(new String[0]);
        String json = gson.toJson(emails);
        userEditor.putString("registered_users", json);
        userEditor.apply();
    }
    
    /**
     * Set current user
     */
    public void setCurrentUser(User user) {
        String json = gson.toJson(user);
        appEditor.putString(KEY_CURRENT_USER, json);
        appEditor.apply();
    }
    
    /**
     * Get current user
     */
    public User getCurrentUser() {
        String json = appPref.getString(KEY_CURRENT_USER, null);
        
        if (json != null) {
            try {
                return gson.fromJson(json, User.class);
            } catch (Exception e) {
                return null;
            }
        }
        
        return null;
    }
    
    /**
     * Logout current user
     */
    public void logoutCurrentUser() {
        appEditor.remove(KEY_CURRENT_USER);
        appEditor.apply();
    }
    
    /**
     * Save selected address
     */
    public void saveSelectedAddress(Address address) {
        String json = gson.toJson(address);
        appEditor.putString(KEY_SELECTED_ADDRESS, json);
        appEditor.apply();
    }
    
    /**
     * Get selected address
     */
    public Address getSelectedAddress() {
        String json = appPref.getString(KEY_SELECTED_ADDRESS, null);
        
        if (json != null) {
            try {
                return gson.fromJson(json, Address.class);
            } catch (Exception e) {
                return null;
            }
        }
        
        return null;
    }
}
