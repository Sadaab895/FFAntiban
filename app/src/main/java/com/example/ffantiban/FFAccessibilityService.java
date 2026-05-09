package com.example.ffantiban;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.Random;

public class FFAccessibilityService extends AccessibilityService {

    private static final String TAG = "FFAntiban";
    private boolean isRunning = false;
    private Random random = new Random();
    private Handler handler = new Handler(Looper.getMainLooper());

    // Free Fire landscape screen
    // Headshot zone — enemy head area (adjust for your phone)
    private static final int[] HEADSHOT_X = {540, 560, 520, 550, 530};
    private static final int[] HEADSHOT_Y = {400, 390, 410, 395, 405};

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {
        isRunning = false;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "Service Connected - Starting Headshot Aim");
        isRunning = true;
        startHeadshotLoop();
    }

    private void startHeadshotLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    performHeadshot();
                    // Random delay 80ms-180ms for antiban
                    int delay = random.nextInt(100) + 80;
                    handler.postDelayed(this, delay);
                }
            }
        }, 100);
    }

    private void performHeadshot() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;

        // Random headshot point se pick karo
        int index = random.nextInt(HEADSHOT_X.length);
        int x = HEADSHOT_X[index];
        int y = HEADSHOT_Y[index];

        // Micro jitter add karo — antiban ke liye
        x += random.nextInt(10) - 5;
        y += random.nextInt(10) - 5;

        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(x, y);

        // Downward drag simulate karo — recoil control
        path.lineTo(x + random.nextInt(6) - 3, y + random.nextInt(8) + 2);

        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 50));
        dispatchGesture(builder.build(), null, null);
    }
}
