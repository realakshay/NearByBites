package com.foodapp.sharing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.foodapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager class for social sharing functionality with stickers
 */
public class SocialSharingManager {
    
    private static final String TAG = "SocialSharingManager";
    private static final String FILE_PROVIDER_AUTHORITY = "com.foodapp.fileprovider";
    
    private Context context;
    private List<Integer> stickerResources;
    
    public SocialSharingManager(Context context) {
        this.context = context;
        initStickers();
    }
    
    private void initStickers() {
        stickerResources = new ArrayList<>();
        stickerResources.add(R.drawable.sticker_burger);
        stickerResources.add(R.drawable.sticker_pizza);
        stickerResources.add(R.drawable.sticker_cake);
        stickerResources.add(R.drawable.sticker_icecream);
        stickerResources.add(R.drawable.sticker_coffee);
    }
    
    /**
     * Share food image with applied sticker
     * @param originalBitmap The original food image bitmap
     * @param stickerPosition The position (index) of the sticker to apply
     * @param x The x position of the sticker (0-1 percentage of image width)
     * @param y The y position of the sticker (0-1 percentage of image height)
     * @param scale The scale factor for the sticker (1.0 = original size)
     * @param rotation The rotation angle for the sticker in degrees
     * @param shareText The text to include in the share intent
     */
    public void shareImageWithSticker(Bitmap originalBitmap, int stickerPosition, 
                                      float x, float y, float scale, float rotation, 
                                      String shareText) {
        if (stickerPosition < 0 || stickerPosition >= stickerResources.size()) {
            stickerPosition = 0; // Default to first sticker if invalid position
        }
        
        int stickerId = stickerResources.get(stickerPosition);
        Drawable stickerDrawable = ContextCompat.getDrawable(context, stickerId);
        
        if (stickerDrawable == null) {
            Toast.makeText(context, "Error loading sticker", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Create a new bitmap with the same dimensions as the original
        Bitmap resultBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), 
                                                 originalBitmap.getHeight(), 
                                                 Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        
        // Draw the original image
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        
        // Convert the drawable to bitmap
        Bitmap stickerBitmap = drawableToBitmap(stickerDrawable);
        
        // Calculate the position and size
        int stickerWidth = (int)(stickerBitmap.getWidth() * scale);
        int stickerHeight = (int)(stickerBitmap.getHeight() * scale);
        
        // Calculate absolute positions
        int posX = (int)(x * originalBitmap.getWidth()) - (stickerWidth / 2);
        int posY = (int)(y * originalBitmap.getHeight()) - (stickerHeight / 2);
        
        // Create a matrix for the sticker transformations
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postRotate(rotation);
        matrix.postTranslate(posX, posY);
        
        // Draw the sticker onto the canvas with the transformation matrix
        canvas.drawBitmap(stickerBitmap, matrix, new Paint(Paint.FILTER_BITMAP_FLAG));
        
        // Save the combined bitmap to a file
        Uri imageUri = saveImageToCache(resultBitmap);
        if (imageUri != null) {
            // Share the image
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            if (shareText != null && !shareText.isEmpty()) {
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            }
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else {
            Toast.makeText(context, "Error creating share image", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Capture a view as a bitmap, add sticker and share
     * @param view The view to capture
     * @param stickerPosition Sticker index to apply
     * @param x X position (0-1)
     * @param y Y position (0-1)
     * @param scale Scale factor
     * @param rotation Rotation in degrees
     * @param shareText Text to include in share
     */
    public void captureViewAndShare(View view, int stickerPosition, 
                                   float x, float y, float scale, float rotation, 
                                   String shareText) {
        // Create a bitmap of the view
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        
        // Share the captured bitmap with a sticker
        shareImageWithSticker(bitmap, stickerPosition, x, y, scale, rotation, shareText);
    }
    
    /**
     * Get list of available sticker resource IDs
     */
    public List<Integer> getAvailableStickers() {
        return new ArrayList<>(stickerResources);
    }
    
    /**
     * Convert drawable to bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        
        return bitmap;
    }
    
    /**
     * Save image to cache directory for sharing
     */
    private Uri saveImageToCache(Bitmap bitmap) {
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");
            
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            
            uri = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, file);
            
        } catch (IOException e) {
            Log.e(TAG, "Error saving image for sharing: " + e.getMessage());
        }
        
        return uri;
    }
}
