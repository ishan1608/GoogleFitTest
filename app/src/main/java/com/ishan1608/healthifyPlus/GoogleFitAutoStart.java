package com.ishan1608.healthifyPlus;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class GoogleFitAutoStart extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private Intent waterReminderIntent;
    static final String TAG = "GoogleFitAutoStart";
    private Intent activityReminderIntent;
    private Intent waterReminderCheckerIntent;
    private Intent googleFitCheckerIntent;

    private AlarmManager waterReminderAlarmManager;

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
        // Starting water reminder alarm
        Log.d(TAG, "Starting water reminder alarm");
        waterReminderAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent waterReminderAlarmIntent = new Intent(context, WaterReminderAlarmReceiver.class);
        PendingIntent waterAlarmPendingIntent = PendingIntent.getBroadcast(context, 0, waterReminderAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        waterReminderAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 15 * 1000, waterAlarmPendingIntent);

        // // Starting water reminder service
        // Log.d(TAG, "calling water reminder service from google fit auto start");
        // waterReminderIntent = new Intent(context, WaterReminderService.class);
        // waterReminderIntent.setAction(WaterReminderService.WATER_REMINDER_TASK);
        // context.startService(waterReminderIntent);
        // Log.d(TAG, "water reminder service called from google fit auto start");

        // // Starting service checker for water reminder using google fit service
        // Log.d(TAG, "Starting service checker for water using google fit");
        // waterReminderCheckerIntent = new Intent(context, GoogleFitService.class);
        // waterReminderCheckerIntent.setAction(GoogleFitService.WATER_REMINDER_SERVICE_CHECKER);
        // context.startService(waterReminderCheckerIntent);

        // // Starting service checker for google fit using water reminder service
        // Log.d(TAG, "Starting service checker for google fit using water");
        // googleFitCheckerIntent = new Intent(context, WaterReminderService.class);
        // googleFitCheckerIntent.setAction(WaterReminderService.GOOGLE_FIT_SERVICE_CHECKER);
        // context.startService(googleFitCheckerIntent);
    }
}