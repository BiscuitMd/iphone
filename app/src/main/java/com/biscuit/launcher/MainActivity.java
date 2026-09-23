package com.biscuit.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private TextView clock;
    private TextView date;
    private TextView battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        clock = findViewById(R.id.clock);
        date = findViewById(R.id.date);
        battery = findViewById(R.id.battery);

        updateClock();
        updateBattery();

        findViewById(R.id.appDrawerButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =
                                new Intent(MainActivity.this,
                                        AppDrawerActivity.class);

                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.settingsButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(
                                new Intent(MainActivity.this,
                                        SettingsActivity.class)
                        );
                    }
                }
        );
    }

    private void updateClock() {
        SimpleDateFormat timeFormat =
                new SimpleDateFormat("HH:mm", Locale.getDefault());

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("EEEE, d MMMM",
                        Locale.getDefault());

        clock.setText(timeFormat.format(new Date()));
        date.setText(dateFormat.format(new Date()));

        clock.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateClock();
            }
        }, 1000);
    }

    private void updateBattery() {
        BatteryManager manager =
                (BatteryManager) getSystemService(BATTERY_SERVICE);

        if (manager != null) {
            int level = manager.getIntProperty(
                    BatteryManager.BATTERY_PROPERTY_CAPACITY
            );

            if (level >= 0) {
                battery.setText(level + "%");
            }
        }
    }
}
