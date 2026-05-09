package com.example.ffantiban;

import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnToggle;
    private TextView tvStatus;
    private boolean isEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToggle = findViewById(R.id.btnToggleAim);
        tvStatus = findViewById(R.id.tvStatus);

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAccessibilityEnabled()) {
                    Toast.makeText(MainActivity.this,
                        "Pehle Accessibility ON karo!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                } else {
                    Toast.makeText(MainActivity.this,
                        "Headshot Aim Active! Free Fire kholo.", Toast.LENGTH_SHORT).show();
                    tvStatus.setText("Status: ACTIVE ✅");
                }
            }
        });
    }

    private boolean isAccessibilityEnabled() {
        try {
            int enabled = Settings.Secure.getInt(
                getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED);
            return enabled == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
