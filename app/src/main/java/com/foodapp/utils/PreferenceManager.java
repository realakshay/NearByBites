package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.Address;
import com.foodapp.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PreferenceManager {
    private static final String PREF_NAME = "FoodAppPreferences";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_ADDRESSES = "addresses";
    private static final String KEY_SELECTED_ADDRESS = "selectedAddress";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;

    public PreferenceManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
    }

    // User related methods
    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public User getUser() {
        String userJson = preferences.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }
        return null;
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        editor.remove(KEY_USER);
        editor.remove(KEY_IS_LOGGED_IN);
        // We're not removing addresses when logging out
        editor.apply();
    }

    // Address related methods
    public void saveAddress(Address address) {
        // If no ID is set, generate one
        if (address.getId() == null || address.getId().isEmpty()) {
            address.setId(UUID.randomUUID().toString());
        }

        List<Address> addresses = getAddresses();
        
        // Check if address already exists (by ID)
        boolean exists = false;
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getId().equals(address.getId())) {
                addresses.set(i, address); // Replace with updated address
                exists = true;
                break;
            }
        }

        // If it doesn't exist, add it
        if (!exists) {
            addresses.add(address);
        }

        // Save updated list
        String addressesJson = gson.toJson(addresses);
        editor.putString(KEY_ADDRESSES, addressesJson);
        editor.apply();
        
        // Also set this as the selected address
        setSelectedAddress(address);
    }

    public List<Address> getAddresses() {
        String addressesJson = preferences.getString(KEY_ADDRESSES, null);
        if (addressesJson != null) {
            Type type = new TypeToken<ArrayList<Address>>(){}.getType();
            return gson.fromJson(addressesJson, type);
        }
        return new ArrayList<>();
    }

    public void deleteAddress(String addressId) {
        List<Address> addresses = getAddresses();
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getId().equals(addressId)) {
                addresses.remove(i);
                break;
            }
        }
        String addressesJson = gson.toJson(addresses);
        editor.putString(KEY_ADDRESSES, addressesJson);
        editor.apply();
        
        // If we deleted the selected address, clear it
        Address selectedAddress = getSelectedAddress();
        if (selectedAddress != null && selectedAddress.getId().equals(addressId)) {
            editor.remove(KEY_SELECTED_ADDRESS);
            editor.apply();
        }
    }

    public void setSelectedAddress(Address address) {
        String addressJson = gson.toJson(address);
        editor.putString(KEY_SELECTED_ADDRESS, addressJson);
        editor.apply();
    }

    public Address getSelectedAddress() {
        String addressJson = preferences.getString(KEY_SELECTED_ADDRESS, null);
        if (addressJson != null) {
            return gson.fromJson(addressJson, Address.class);
        }
        // If no selected address, return the first one if available
        List<Address> addresses = getAddresses();
        if (!addresses.isEmpty()) {
            return addresses.get(0);
        }
        return null;
    }

    public void clearSelectedAddress() {
        editor.remove(KEY_SELECTED_ADDRESS);
        editor.apply();
    }

    // Clear all preferences
    public void clearPreferences() {
        editor.clear();
        editor.apply();
    }
}
