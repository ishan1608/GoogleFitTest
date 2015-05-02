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
    private Intent activityReminderIntent;
    private Intent waterReminderCheckerIntent;
    private Intent googleFitCheckerIntent;

    public GoogleFitAutoStart() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Starting Google Fit Service
        Intent googleFitIntent = new Intent(context, GoogleFitService.class);
        context.startService(googleFitIntent);
        Log.i(TAG, "started on boot");
        Toast.makeText(context, "GoogleFitAutoStart" + " started on boot", Toast.LENGTH_LONG).show();

        // Starting movement reminder for physical activity
        Log.d(TAG, "calling activity reminder service from google fit auto start");
        activityReminderIntent = new Intent(context, GoogleFitService.class);
        activityReminderIntent.setAction(GoogleFitService.ACTIVITY_REMINDER);
        context.startService(activityReminderIntent);
        Log.d(TAG, "activity reminder service called from google fit auto start");

        // Starting reminder for water intake
        // Starting water reminder service
        Log.d(TAG, "calling water reminder service from google fit auto start");
        waterReminderIntent = new Intent(context, WaterReminderService.class);
        waterReminderIntent.setAction(WaterReminderService.WATER_REMINDER_TASK);
        context.startService(waterReminderIntent);
        Log.d(TAG, "water reminder service called from google fit auto start");

        // Starting service checker for water reminder using google fit service
        Log.d(TAG, "Starting service checker for water using google fit");
        waterReminderCheckerIntent = new Intent(context, GoogleFitService.class);
        waterReminderCheckerIntent.setAction(GoogleFitService.WATER_REMINDER_SERVICE_CHECKER);
        context.startService(waterReminderCheckerIntent);

        // Starting service checker for google fit using water reminder service
        Log.d(TAG, "Starting service checker for google fit using water");
        googleFitCheckerIntent = new Intent(context, WaterReminderService.class);
        googleFitCheckerIntent.setAction(WaterReminderService.GOOGLE_FIT_SERVICE_CHECKER);
        context.startService(googleFitCheckerIntent);
    }
}
