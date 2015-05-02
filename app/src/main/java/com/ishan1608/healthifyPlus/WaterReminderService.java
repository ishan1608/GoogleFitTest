package com.ishan1608.healthifyPlus;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class WaterReminderService extends IntentService {
    static final String WATER_REMINDER_TASK = "com.ishan1608.healthifyPlus.action.WATER-REMINDER-TASK";
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "WATER-REMINDER-SERVICE";
    private TimerTask waterReminderTask;
    private Timer waterReminderTimer;
    private NotificationManager mNotificationManager;


    public WaterReminderService() {
        super("WaterReminderService");

        Log.d(TAG, "WaterReminderService constructor called");


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "waterReminderService onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case WATER_REMINDER_TASK:
                        Log.d(TAG, "WATER_REMINDER_TASK case");
                        // Making water reminder task
                        waterReminderTask = new TimerTask() {
                            @Override
                            public void run() {
                                Log.d(TAG, "waterReminderTask running");
                                // If current time is between 8AM to 10PM send notification
                                int hour = new Time(System.currentTimeMillis()).getHours();
                                Log.d(TAG, "current hour : " + hour);
                                if(hour > 8 && hour < 22) {
                                    sendNotification("Water or juice, whichever you prefer");
                                }
                                // Without checking display notification
                                // Only for testing, not to be used
                                // sendNotification("Water or juice, whichever you prefer");
                            }
                        };
                        waterReminderTimer = new Timer("waterReminderTimer");
                        waterReminderTimer.scheduleAtFixedRate(waterReminderTask, 0, 300000);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // Put the message into a notification and post it.
    private void sendNotification(String msg) {
        Log.d(TAG, "sendNotification called with msg " + msg);
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Intent goingIntent = new Intent(this, LoginActivity.class);
        goingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        // goingIntent, 0);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                goingIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Have some water")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
