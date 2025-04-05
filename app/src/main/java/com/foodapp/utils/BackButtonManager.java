package com.foodapp.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Utility class to manage the back button behavior
 * Implements the "Press back again to exit" pattern
 */
public class BackButtonManager {
    
    private boolean doubleBackToExitPressedOnce = false;
    private final Activity activity;
    private final String exitMessage;
    
    public BackButtonManager(Activity activity) {
        this(activity, "Press back again to exit");
    }
    
    public BackButtonManager(Activity activity, String exitMessage) {
        this.activity = activity;
        this.exitMessage = exitMessage;
    }
    
    /**
     * Call this method from your activity's onBackPressed() method
     * @return true if back press was handled, false otherwise
     */
    public boolean handleBackPress() {
        if (doubleBackToExitPressedOnce) {
            // User pressed back twice, exit the app
            activity.finish();
            return true;
        }
        
        // First back press, show toast and set flag
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(activity, exitMessage, Toast.LENGTH_SHORT).show();
        
        // Reset flag after 2 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> 
                doubleBackToExitPressedOnce = false, 2000);
        
        return true;
    }
}
