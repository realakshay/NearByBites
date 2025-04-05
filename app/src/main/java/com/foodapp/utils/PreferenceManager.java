package com.foodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodapp.models.Address;
import com.google.gson.Gson;

public class PreferenceManager {
    private static final String PREF_NAME = "food_app_preferences";
    private static final String IS_FIRST_TIME_LAUNCH = "is_first_time_launch";
    private static final String SELECTED_ADDRESS_KEY = "selected_address";
    
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    
    public PreferenceManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    
    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    
    public void saveSelectedAddress(Address address) {
        Gson gson = new Gson();
        String json = gson.toJson(address);
        editor.putString(SELECTED_ADDRESS_KEY, json);
        editor.apply();
    }
    
    public Address getSelectedAddress() {
        Gson gson = new Gson();
        String json = preferences.getString(SELECTED_ADDRESS_KEY, null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, Address.class);
    }
}
