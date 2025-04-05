package com.foodapp.sharing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.core.content.ContextCompat;

/**
 * Custom view for interactive sticker editing (positioning, scaling, rotating)
 */
public class StickerEditorView extends View {
    
    private Bitmap backgroundBitmap;
    private Bitmap stickerBitmap;
    private Matrix stickerMatrix = new Matrix();
    private Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
    
    private PointF lastTouch = new PointF();
    private float scaleFactor = 1.0f;
    private float rotation = 0f;
    private PointF stickerPosition = new PointF(0.5f, 0.5f); // Center initially
    
    private boolean isMovingSticker = false;
    private ScaleGestureDetector scaleGestureDetector;
    
    public StickerEditorView(Context context) {
        super(context);
        init();
    }
    
    public StickerEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public StickerEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }
    
    /**
     * Set the background image to edit
     */
    public void setBackgroundImage(Bitmap bitmap) {
        backgroundBitmap = bitmap;
        invalidate();
    }
    
    /**
     * Set the sticker by resource ID
     */
    public void setSticker(int resourceId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);
        if (drawable != null) {
            stickerBitmap = drawableToBitmap(drawable);
            resetStickerTransformation();
            invalidate();
        }
    }
    
    /**
     * Reset sticker to default position and scale
     */
    public void resetStickerTransformation() {
        stickerPosition.set(0.5f, 0.5f);
        scaleFactor = 0.5f;
        rotation = 0f;
        invalidate();
    }
    
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
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background image if available
        if (backgroundBitmap != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        }
        
        // Draw sticker if available
        if (stickerBitmap != null) {
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            
            // Reset matrix
            stickerMatrix.reset();
            
            // Scale sticker
            float baseScale = Math.min(viewWidth, viewHeight) / (float) Math.max(stickerBitmap.getWidth(), stickerBitmap.getHeight());
            stickerMatrix.postScale(baseScale * scaleFactor, baseScale * scaleFactor);
            
            // Rotate sticker
            float pivotX = stickerBitmap.getWidth() / 2f;
            float pivotY = stickerBitmap.getHeight() / 2f;
            stickerMatrix.postRotate(rotation, pivotX, pivotY);
            
            // Position sticker
            float targetX = stickerPosition.x * viewWidth;
            float targetY = stickerPosition.y * viewHeight;
            float scaledWidth = stickerBitmap.getWidth() * baseScale * scaleFactor;
            float scaledHeight = stickerBitmap.getHeight() * baseScale * scaleFactor;
            
            stickerMatrix.postTranslate(
                targetX - (scaledWidth / 2f),
                targetY - (scaledHeight / 2f)
            );
            
            // Draw sticker with transformations
            canvas.drawBitmap(stickerBitmap, stickerMatrix, paint);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouch.set(x, y);
                isMovingSticker = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isMovingSticker && !scaleGestureDetector.isInProgress()) {
                    // Calculate the movement as a percentage of view dimensions
                    float deltaX = (x - lastTouch.x) / getWidth();
                    float deltaY = (y - lastTouch.y) / getHeight();
                    
                    // Update sticker position
                    stickerPosition.x += deltaX;
                    stickerPosition.y += deltaY;
                    
                    // Keep sticker within bounds
                    stickerPosition.x = Math.max(0.1f, Math.min(0.9f, stickerPosition.x));
                    stickerPosition.y = Math.max(0.1f, Math.min(0.9f, stickerPosition.y));
                    
                    lastTouch.set(x, y);
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
                isMovingSticker = false;
                return true;
        }
        
        return super.onTouchEvent(event);
    }
    
    /**
     * Gesture listener for scaling the sticker
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            
            // Limit scale
            scaleFactor = Math.max(0.1f, Math.min(2.0f, scaleFactor));
            
            invalidate();
            return true;
        }
    }
    
    /**
     * Rotate the sticker by the specified angle in degrees
     */
    public void rotateSticker(float degrees) {
        rotation += degrees;
        invalidate();
    }
    
    /**
     * Get properties needed for sharing
     */
    public float getStickerX() {
        return stickerPosition.x;
    }
    
    public float getStickerY() {
        return stickerPosition.y;
    }
    
    public float getStickerScale() {
        return scaleFactor;
    }
    
    public float getStickerRotation() {
        return rotation;
    }
    
    /**
     * Create a single bitmap with everything rendered on it
     */
    public Bitmap createFinalBitmap() {
        Bitmap result = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        draw(canvas);
        return result;
    }
}
