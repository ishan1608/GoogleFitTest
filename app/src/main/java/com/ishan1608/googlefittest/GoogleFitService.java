package com.ishan1608.googlefittest;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Initialize a GoogleFitnessAPI client here
 * TODO: Initialize the actions required for STEP_COUNT_NOW STEP_COUNT_TODAY
 */
public class GoogleFitService extends IntentService {

    private static final String TAG = "GOOGLE-FIT-SERVICE";
    public static final String STEP_COUNT_NOW = "com.ishan1608.googlefittest.action.STEP_COUNT_NOW";
    public static final String STEP_COUNT_TODAY = "com.ishan1608.googlefittest.action.STEP_COUNT_TODAY";
    public static final String STEP_COUNT_TODAY_RESULT = "com.ishan1608.googlefittest.action.STEP_COUNT_TODAY_RESULT";
    public static final String STEP_COUNT_NOW_RESULT = "com.ishan1608.googlefittest.action.STEP_COUNT_NOW_RESULT";

    // Will use these as bundle parameters if requires and will rename them
//    public static final String EXTRA_PARAM1 = "com.ishan1608.googlefittest.extra.PARAM1";
//    public static final String EXTRA_PARAM2 = "com.ishan1608.googlefittest.extra.PARAM2";

    public GoogleFitService() {
        super("GoogleFitService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "GoogleFitService called");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent called");
        if (intent != null) {
            final String action = intent.getAction();
            if (STEP_COUNT_NOW.equals(action)) {
                handleActionStepCountNow();
            } else if (STEP_COUNT_TODAY.equals(action)) {
                handleActionStepCountToday();
            }
        }
    }

    /**
     * Handle action STEP_COUNT_NOW in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStepCountNow() {
        // TODO: Handle action STEP_COUNT_NOW
        Log.d(TAG, "Counting steps as of now.");
        /*
        * Creates a new Intent containing a Uri object
        * BROADCAST_ACTION is a custom Intent action
        */
        Intent stepCountNowResultIntent =
                new Intent(GoogleFitService.STEP_COUNT_NOW)
                        // Puts the status into the Intent
                        .putExtra(GoogleFitService.STEP_COUNT_NOW_RESULT, "10");
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(stepCountNowResultIntent);
    }

    /**
     * Handle action STEP_COUNT_TODAY in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStepCountToday() {
        // TODO: Handle action STEP_COUNT_TODAY
        Log.d(TAG, "Counting steps for today.");
        /*
        * Creates a new Intent containing a Uri object
        * BROADCAST_ACTION is a custom Intent action
        */
        Intent stepCountTodayResultIntent =
                new Intent(GoogleFitService.STEP_COUNT_TODAY)
                        // Puts the status into the Intent
                        .putExtra(GoogleFitService.STEP_COUNT_TODAY_RESULT, "100");
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(stepCountTodayResultIntent);
    }
}
