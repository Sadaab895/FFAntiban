package com.example.ffantiban;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.Random;

public class FFAccessibilityService extends AccessibilityService {

    private static final String TAG = "FFAntiban";
    private boolean isAimActive = false;
    private Random random = new Random();
    private Thread aimThread;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Service Interrupted");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "Accessibility Service Connected");
    }

    public void startAim() {
        if (isAimActive) return;
        isAimActive = true;

        aimThread = new Thread(() -> {
            while (isAimActive) {
                try {
                    int delay = random.nextInt(200) + 150;
                    Thread.sleep(delay);
                    simulateHumanTap();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        aimThread.start();
    }

    public void stopAim() {
        isAimActive = false;
        if (aimThread != null) {
            try {
                aimThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateHumanTap() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;

        int x = 540;
        int y = 960;

        GestureDescription.Builder builder = new GestureDescription.Builder();

        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, y + 1);

        builder.addStroke(new GestureDescription.StrokeDescription(path, 10, 60));
        dispatchGesture(builder.build(), null, null);
    }
}
