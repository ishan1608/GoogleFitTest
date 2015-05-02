package com.ishan1608.healthifyPlus;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.awt.font.TextAttribute;

public class GoogleFitAutoStart extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private Intent waterReminderIntent;
    static final String TAG = "GoogleFitAutoStart";

    public GoogleFitAutoStart() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Starting Google Fit Service
        Intent googleFitIntent = new Intent(context, GoogleFitService.class);
        context.startService(googleFitIntent);
        Log.i(TAG, "started on boot");
        Toast.makeText(context, "GoogleFitAutoStart" + " started on boot", Toast.LENGTH_LONG).show();

        // TODO: Starting Google Fit movement reminder.

        // Starting reminder for water intake
        // Starting water reminder service
        Log.d(TAG, "calling water reminder service from google fit auto start");
        waterReminderIntent = new Intent(context, WaterReminderService.class);
        waterReminderIntent.setAction(WaterReminderService.WATER_REMINDER_TASK);
        context.startService(waterReminderIntent);
        Log.d(TAG, "water reminder service called from google fit auto start");
    }
}
