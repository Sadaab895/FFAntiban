package com.example.ffantiban;

import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnToggleAim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToggleAim = findViewById(R.id.btnToggleAim);

        btnToggleAim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAccessibilityEnabled()) {
                    Toast.makeText(MainActivity.this, "Aimbot Toggled", Toast.LENGTH_SHORT).show();
                } else {
                    enableAccessibility();
                }
            }
        });

        checkPermission();
    }

    private void checkPermission() {
        if (!isAccessibilityEnabled()) {
            Toast.makeText(this, "Enable Accessibility in Settings", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isAccessibilityEnabled() {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessibilityEnabled == 1;
    }

    private void enableAccessibility() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }
}
