package com.foodapp.sharing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.foodapp.R;
import com.foodapp.models.MenuItem;
import com.foodapp.models.Restaurant;

import java.util.List;

public class SocialSharingActivity extends AppCompatActivity {
    
    private StickerEditorView stickerEditorView;
    private SocialSharingManager sharingManager;
    private EditText etShareCaption;
    private LinearLayout layoutStickers;
    private Button btnShare;
    private Button btnRotateLeft;
    private Button btnRotateRight;
    
    private int selectedStickerPosition = 0;
    private MenuItem foodItem;
    private Restaurant restaurant;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_sharing);
        
        // Initialize the sharing manager
        sharingManager = new SocialSharingManager(this);
        
        // Initialize views
        stickerEditorView = findViewById(R.id.stickerEditorView);
        etShareCaption = findViewById(R.id.etShareCaption);
        layoutStickers = findViewById(R.id.layoutStickers);
        btnShare = findViewById(R.id.btnShare);
        btnRotateLeft = findViewById(R.id.btnRotateLeft);
        btnRotateRight = findViewById(R.id.btnRotateRight);
        
        // Get data from intent
        getIntentData();
        
        // Set up the background image
        setupFoodImage();
        
        // Load stickers
        loadStickerOptions();
        
        // Setup default sticker
        if (sharingManager.getAvailableStickers().size() > 0) {
            stickerEditorView.setSticker(sharingManager.getAvailableStickers().get(0));
        }
        
        // Set up button actions
        setupButtonActions();
        
        // Set default share caption text
        setupDefaultCaption();
    }
    
    private void getIntentData() {
        if (getIntent().hasExtra("menu_item_id")) {
            int menuItemId = getIntent().getIntExtra("menu_item_id", 0);
            // In a real app, you would load the actual MenuItem from your database
            // For now, we'll use a placeholder
            foodItem = new MenuItem(menuItemId, "Delicious Food", "Food Description", 12.99, R.drawable.menu_burger);
        }
        
        if (getIntent().hasExtra("restaurant_id")) {
            int restaurantId = getIntent().getIntExtra("restaurant_id", 0);
            // In a real app, you would load the actual Restaurant from your database
            restaurant = new Restaurant(restaurantId, "Restaurant Name", "123 Main St", "Cuisine", 4.5f, 30, 1.5f, R.drawable.restaurant_1);
        }
    }
    
    private void setupFoodImage() {
        // Load the food image - in a real app this would come from the network or resources
        Bitmap foodBitmap = null;
        
        if (foodItem != null && foodItem.getImageResourceId() > 0) {
            // Use the food item's image if available
            foodBitmap = BitmapFactory.decodeResource(getResources(), foodItem.getImageResourceId());
        } else if (restaurant != null && restaurant.getImageResourceId() > 0) {
            // Use the restaurant's image if available
            foodBitmap = BitmapFactory.decodeResource(getResources(), restaurant.getImageResourceId());
        } else {
            // Use a default food image
            foodBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.menu_burger);
        }
        
        stickerEditorView.setBackgroundImage(foodBitmap);
    }
    
    private void loadStickerOptions() {
        List<Integer> stickers = sharingManager.getAvailableStickers();
        
        layoutStickers.removeAllViews();
        
        // Add stickers to the horizontal scrollview
        for (int i = 0; i < stickers.size(); i++) {
            final int stickerPosition = i;
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(stickers.get(i));
            
            // Set the layout parameters
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(70),
                    dpToPx(70)
            );
            params.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            imageView.setLayoutParams(params);
            
            // Make the sticker selectable
            imageView.setOnClickListener(v -> {
                selectedStickerPosition = stickerPosition;
                stickerEditorView.setSticker(stickers.get(stickerPosition));
                
                // Highlight the selected sticker
                for (int j = 0; j < layoutStickers.getChildCount(); j++) {
                    View child = layoutStickers.getChildAt(j);
                    child.setBackground(j == stickerPosition ? 
                            ContextCompat.getDrawable(this, R.drawable.sticker_selected_border) : null);
                }
            });
            
            // Highlight the first sticker by default
            if (i == 0) {
                imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.sticker_selected_border));
            }
            
            layoutStickers.addView(imageView);
        }
    }
    
    private void setupButtonActions() {
        // Share button
        btnShare.setOnClickListener(v -> {
            String caption = etShareCaption.getText().toString().trim();
            
            // Create a final bitmap with the sticker applied
            Bitmap finalBitmap = stickerEditorView.createFinalBitmap();
            
            // Share using the SocialSharingManager
            sharingManager.shareImageWithSticker(
                    finalBitmap,
                    selectedStickerPosition,
                    stickerEditorView.getStickerX(),
                    stickerEditorView.getStickerY(),
                    stickerEditorView.getStickerScale(),
                    stickerEditorView.getStickerRotation(),
                    caption
            );
        });
        
        // Rotation buttons
        btnRotateLeft.setOnClickListener(v -> {
            stickerEditorView.rotateSticker(-15); // Rotate 15 degrees counterclockwise
        });
        
        btnRotateRight.setOnClickListener(v -> {
            stickerEditorView.rotateSticker(15); // Rotate 15 degrees clockwise
        });
    }
    
    private void setupDefaultCaption() {
        StringBuilder captionBuilder = new StringBuilder("Check out this amazing food");
        
        if (foodItem != null) {
            captionBuilder = new StringBuilder("Check out this delicious ")
                .append(foodItem.getName());
        }
        
        if (restaurant != null) {
            captionBuilder.append(" from ")
                .append(restaurant.getName());
        }
        
        captionBuilder.append(" on #GRUB! 😋🍔");
        
        etShareCaption.setText(captionBuilder.toString());
    }
    
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
