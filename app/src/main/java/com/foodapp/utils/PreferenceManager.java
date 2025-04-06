package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.Address;
import com.foodapp.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing shared preferences.
 */
public class PreferenceManager {
    private static final String PREF_NAME = "com.foodapp.prefs";
    private static final String KEY_USER = "key_user";
    private static final String KEY_ADDRESS = "key_address";
    private static final String KEY_IS_LOGGED_IN = "key_is_logged_in";
    private static final String KEY_IS_FIRST_TIME = "key_is_first_time";
    private static final String KEY_ADDRESS_LIST = "key_address_list";

    private SharedPreferences preferences;
    private Gson gson;

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Save user data to shared preferences.
     *
     * @param user User object to save
     */
    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        preferences.edit().putString(KEY_USER, userJson).apply();
    }

    /**
     * Get current user from shared preferences.
     *
     * @return User object if exists, null otherwise
     */
    public User getCurrentUser() {
        String userJson = preferences.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }
        return null;
    }

    public User loginUser(String email, String password) {
        User savedUser = getCurrentUser();

        if (savedUser != null &&
            savedUser.getEmail().equalsIgnoreCase(email) &&
            savedUser.getPassword().equals(password)) {
            setLoggedIn(true);
            return savedUser;
        }

        return null;
    }

    /**
     * Save user selected address to shared preferences.
     *
     * @param address Address object to save
     */
    public void saveSelectedAddress(Address address) {
        String addressJson = gson.toJson(address);
        preferences.edit().putString(KEY_ADDRESS, addressJson).apply();
    }

    /**
     * Get selected address from shared preferences.
     *
     * @return Address object if exists, null otherwise
     */
    public Address getSelectedAddress() {
        String addressJson = preferences.getString(KEY_ADDRESS, null);
        if (addressJson != null) {
            return gson.fromJson(addressJson, Address.class);
        }
        return null;
    }
    
    /**
     * Save list of addresses to SharedPreferences.
     */
    public void saveAddressList(List<Address> addressList) {
        String json = gson.toJson(addressList);
        preferences.edit().putString(KEY_ADDRESS_LIST, json).apply();
    }

    /**
     * Get the list of saved addresses from SharedPreferences.
     */
    public List<Address> getAddresses() {
        // String json = preferences.getString(KEY_ADDRESS_LIST, null);
        // if (json != null) {
        //     Type type = new TypeToken<List<Address>>() {}.getType();
        //     return gson.fromJson(json, type);
        // }
        return new ArrayList<>();
    }
    

    /**
     * Set login status.
     *
     * @param isLoggedIn true if user is logged in, false otherwise
     */
    public void setLoggedIn(boolean isLoggedIn) {
        preferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    /**
     * Check if user is logged in.
     *
     * @return true if user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Set first time launch status.
     *
     * @param isFirstTime true if it's first time, false otherwise
     */
    public void setFirstTimeLaunch(boolean isFirstTime) {
        preferences.edit().putBoolean(KEY_IS_FIRST_TIME, isFirstTime).apply();
    }

    /**
     * Check if it's first time launch.
     *
     * @return true if it's first time, false otherwise
     */
    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(KEY_IS_FIRST_TIME, true);
    }

    /**
     * Clear all preferences.
     */
    public void clearPreferences() {
        preferences.edit().clear().apply();
    }

    public void logoutCurrentUser() {
        clearPreferences();
    }

    /**
     * Sets the current user and marks them as logged in.
     *
     * @param user The user to set as current
     */
    public void setCurrentUser(User user) {
        saveUser(user);
        setLoggedIn(true);
    }

    public boolean registerUser(User user){
        saveUser(user);
        return true;
    }
}
