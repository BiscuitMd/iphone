package com.biscuit.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BatteryReceiver extends BroadcastReceiver {

    public interface Listener {
        void onBatteryChanged(int level, boolean charging);
    }

    private Listener listener;

    public BatteryReceiver(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null) {
            return;
        }

        String action = intent.getAction();

        if (!Intent.ACTION_BATTERY_CHANGED.equals(action)
                && !Intent.ACTION_BATTERY_LOW.equals(action)
                && !Intent.ACTION_BATTERY_OKAY.equals(action)) {
            return;
        }

        int level =
                intent.getIntExtra(
                        "level",
                        -1
                );

        int scale =
                intent.getIntExtra(
                        "scale",
                        100
                );

        int status =
                intent.getIntExtra(
                        "status",
                        -1
                );

        if (level < 0) {
            return;
        }

        int percentage;

        if (scale > 0) {
            percentage =
                    Math.round(
                            (level * 100f) / scale
                    );
        } else {
            percentage = level;
        }

        boolean charging =
                status == 2
                        || status == 5;

        if (listener != null) {
            listener.onBatteryChanged(
                    percentage,
                    charging
            );
        }
    }

    public IntentFilter getIntentFilter() {

        IntentFilter filter =
                new IntentFilter();

        filter.addAction(
                Intent.ACTION_BATTERY_CHANGED
        );

        filter.addAction(
                Intent.ACTION_BATTERY_LOW
        );

        filter.addAction(
                Intent.ACTION_BATTERY_OKAY
        );

        return filter;
    }
}
