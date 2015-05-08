package com.ishan1608.healthifyPlus;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class WaterReminderAlarmReceiver extends BroadcastReceiver {
	
	private static final int WATER_REMINDER_TASK_NOTIFICATION_ID = 0;
    private String TAG = WaterReminderAlarmReceiver.class.getSimpleName();

	@Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "AlarmReceiver -> onReceive called");
        
        Calendar nowCalendar = Calendar.getInstance();
        int hour = nowCalendar.get(Calendar.HOUR_OF_DAY);
        Log.d(TAG, "Current Hour : " + hour);
        if (hour > 8 && hour < 22) {
            sendNotification(context, "Stay Hydrated", "Drinking water is good for health.\nDrink some.");
        }
    }
    // Put the message into a notification and post it.
    private void sendNotification(Context context, String title, String msg) {
        Log.d(TAG, "sendNotification called with msg " + msg);
        NotificationManager waterReminderNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Intent goingIntent = new Intent(context, MainActivity.class);
        goingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, goingIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder waterNotificationBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        waterNotificationBuilder.setContentIntent(contentIntent);
        waterReminderNotificationManager.notify(WATER_REMINDER_TASK_NOTIFICATION_ID, waterNotificationBuilder.build());
    }
}