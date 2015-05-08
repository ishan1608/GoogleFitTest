package com.ishan1608.healthifyPlus;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class GoogleFitService extends IntentService {

    private static final String TAG = "GOOGLE-FIT-SERVICE";
    public static final String STEP_COUNT_TODAY = "com.ishan1608.healthifyPlus.action.STEP_COUNT_TODAY";
    public static final String STEP_COUNT_TODAY_RESULT = "com.ishan1608.healthifyPlus.action.STEP_COUNT_TODAY_RESULT";
    public static final String STEPS_PER_SECOND_COUNT = "com.ishan1608.healthifyPlus.action.STEPS_PER_SECOND_COUNT";
    public static final String STEPS_PER_SECOND_COUNT_RESULT = "com.ishan1608.healthifyPlus.action.STEPS_PER_SECOND_COUNT_RESULT";
    public static final String MILES_COUNT_TODAY = "com.ishan1608.healthifyPlus.action.MILES_COUNT_TODAY";
    public static final String MILES_TODAY_COUNT_RESULT = "com.ishan1608.healthifyPlus.action.MILES_TODAY_COUNT_RESULT";
    public static final String CALORIES_EXPENDED_TODAY = "com.ishan1608.healthifyPlus.action.CALORIES_EXPENDED_TODAY";
    public static final String CALORIES_EXPENDED_TODAY_RESULT = "com.ishan1608.healthifyPlus.action.CALORIES_EXPENDED_TODAY_RESULT";
//    public static final String ACTIVITY_REMINDER = "com.ishan1608.healthifyPlus.action.ACTIVITY_REMINDER";

//    static final String WATER_REMINDER_SERVICE_CHECKER = "com.ishan1608.healthifyPlus.action.WATER_REMINDER_SERVICE_CHECKER";
//    private static final int NOTIFICATION_ID = 2;

    private GoogleApiClient physicalFitnessClient;
    private OnDataPointListener mListener;
//    private TimerTask activityReminderTask;
//    private Timer activityReminderTimer;
//    private NotificationManager mNotificationManager;
//    private Intent googleFitReminderServiceCheckerIntent;

    // Will use these as bundle parameters if requires and will rename them
//    public static final String EXTRA_PARAM1 = "com.ishan1608.healthifyPlus.extra.PARAM1";
//    public static final String EXTRA_PARAM2 = "com.ishan1608.healthifyPlus.extra.PARAM2";

    public GoogleFitService() {
        super("GoogleFitService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "GoogleFitService called");

        // Initializing physical fitness client for all kind of fit data
        buildPhysicalFitnessClient();
        // Connecting the physical fitness client
        physicalFitnessClient.connect();

//        // Counting and broadcasting step count now every second
//        handleActionStepsPerSecond();
//        // Counting and broadcasting step count for today
//        handleActionStepCountToday();
//        // Counting and broadcasting miles count for today
//        handleActionMilesCountToday();
//        // Counting and broadcasting calories expended for today
//        handleActionCaloriesExpendedToday();
    }

    /**
     * Build a {@link com.google.android.gms.common.api.GoogleApiClient} that will authenticate the user and allow the application
     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
     * (see documentation for details). Authentication will occasionally fail intentionally,
     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     * can address. Examples of this include the user never having signed in before, or having
     * multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildPhysicalFitnessClient() {
        Log.d(TAG, "buildPhysicalFitnessClient called.");

        // Create the Google API Client
        physicalFitnessClient = new GoogleApiClient.Builder(this)
                // Adding Fitness Sensor API
                .addApi(Fitness.SENSORS_API)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                // Adding Plus API
                // Not required for google fit data
//                .addApi(Plus.API)
                // Adding Fitness Scopes
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
                // Adding Plus Scopes
                // Not required for google fit data
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.
                                // Just displaying successful connection message
//                                Toast.makeText(getApplicationContext(), "Connected successfully", Toast.LENGTH_LONG).show();

                                // TODO: Notify UI that connection has been made successfully
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.

                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                Log.i(TAG, "Connection failed. Cause: " + result.toString());
                                // TODO: Notify UI or set a flag stating that connection could not be made.
                            }
                        }
                )
                .build();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent called");
        if (intent != null) {
            final String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case STEPS_PER_SECOND_COUNT:
                        handleActionStepsPerSecond();
                        break;
                    case STEP_COUNT_TODAY:
                        handleActionStepCountToday();

//                    // Broadcasting this information every 2 seconds
//                    TimerTask stepCountTodayBroadcastTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                            handleActionStepCountToday();
//                        }
//                    };
//                    Timer stepCountTodayTimer = new Timer("stepCountTodayTimer");
//                    stepCountTodayTimer.scheduleAtFixedRate(stepCountTodayBroadcastTask, 0, 2000);
                        break;
                    case MILES_COUNT_TODAY:
                        handleActionMilesCountToday();
//                    // Broadcasting this information every 2 seconds
//                    TimerTask milesCountTodayBroadcastTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                            handleActionMilesCountToday();
//                        }
//                    };
//                    Timer milesCountTodayTimer = new Timer("milesCountTodayTimer");
//                    milesCountTodayTimer.scheduleAtFixedRate(milesCountTodayBroadcastTask, 0, 2000);
                        break;
                    case CALORIES_EXPENDED_TODAY:
                        handleActionCaloriesExpendedToday();
//                    // Broadcasting this information every 2 seconds
//                    TimerTask caloriesExpendedTodayBroadcastTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                            handleActionCaloriesExpendedToday();
//                        }
//                    };
//                    Timer caloriesExpendedTodayTimer = new Timer("caloriesExpendedTodayTimer");
//                    caloriesExpendedTodayTimer.scheduleAtFixedRate(caloriesExpendedTodayBroadcastTask, 0, 2000);
                        break;
//                    case ACTIVITY_REMINDER:
//                        Log.d(TAG, "ACTIVITY_REMINDER case");
//                        handleActivityReminder();
//                        break;

//                    case WATER_REMINDER_SERVICE_CHECKER:
//                        // TODO: Check if Google fit service is running every 15 minutes
//                        TimerTask waterReminderRunningCheckTask = new TimerTask() {
//
//                            @Override
//                            public void run() {
//                                if(isMyServiceRunning(WaterReminderService.class)) {
//                                    // Google Fit service already running.
//                                    Log.d(TAG, "Water Reminder service already running");
//                                } else {
//                                    Log.d(TAG, "Starting Water reminder service for google fit checker");
//                                    googleFitReminderServiceCheckerIntent = new Intent(getApplicationContext(), WaterReminderService.class);
//                                    googleFitReminderServiceCheckerIntent.setAction(WaterReminderService.GOOGLE_FIT_SERVICE_CHECKER);
//                                    getApplicationContext().startService(googleFitReminderServiceCheckerIntent);
//                                    Log.d(TAG, "Water reminder service for google fit checker started");
//                                }
//                            }
//                        };
//                        Timer waterReminderRunningCheckTimer = new Timer("waterReminderRunningCheckTimer");
//                        waterReminderRunningCheckTimer.scheduleAtFixedRate(waterReminderRunningCheckTask, 0, 15 * 60 * 1000);
//                        break;
                }
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

//    private void handleActivityReminder() {
//        Log.d(TAG, "handleActivityReminder called");
//        // Making water reminder task
//        activityReminderTask = new TimerTask() {
//            @Override
//            public void run() {
//                Log.d(TAG, "activityReminderTask running");
//                // If current time is between 8AM to 10PM send notification
//                int hour = new Time(System.currentTimeMillis()).getHours();
//                if((hour > 6 && hour < 8) || (hour > 17 && hour < 20)) {
//                    sendNotification("It's time to have some physical activity.");
//                }
//                // Without checking display notification
//                // Only for testing, not to be used
//                // sendNotification("Water or juice, whichever you prefer");
//            }
//        };
//        activityReminderTimer = new Timer("activityReminderTimer");
//        Log.d(TAG, "starting activityReminderTimer");
//        activityReminderTimer.scheduleAtFixedRate(activityReminderTask, 0, 2700000);
//    }

//    private void sendNotification(String msg) {
//        Log.d(TAG, "sendNotification called with msg " + msg);
//        mNotificationManager = (NotificationManager) this
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        Intent goingIntent = new Intent(this, LoginActivity.class);
//        goingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        // PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//        // goingIntent, 0);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                goingIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                this).setSmallIcon(R.drawable.app_icon)
//                .setContentTitle("Movement is good.")
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
//                .setContentText(msg);
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//    }

    /**
     * Handle action STEPS_PER_SECOND_COUNT in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStepsPerSecond() {
        Log.d(TAG, "Counting steps as of now.");

        // [START find_data_sources]
        Fitness.SensorsApi.findDataSources(physicalFitnessClient, new DataSourcesRequest.Builder()
        // At least one datatype must be specified.
//        .setDataTypes(DataType.TYPE_LOCATION_SAMPLE)
        .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
//        Can specify whether data type is raw or derived.
//        .setDataSourceTypes(DataSource.TYPE_RAW)
        .build())
        .setResultCallback(new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
                for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                    Log.i(TAG, "Data source found: " + dataSource.toString());
                    Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                    //Let's register a listener to receive Activity data!
//                            if (dataSource.getDataType().equals(DataType.TYPE_LOCATION_SAMPLE)
//                                    && mListener == null) {
//                                Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
//                                logStatus("Data source for LOCATION_SAMPLE found!  Registering.");
//                                registerFitnessDataListener(dataSource,
//                                        DataType.TYPE_LOCATION_SAMPLE);
//                            }
                    // Checking step count delta
                    if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA) && mListener == null) {
                        Log.i(TAG, "Data source for STEP_COUNT_DELTA found!  Registering.");
                        DataType dataType = DataType.TYPE_STEP_COUNT_DELTA;


                        // [START register_data_listener]
                        // Creating a listener for step count delta
                        mListener = new OnDataPointListener() {
                            @Override
                            public void onDataPoint(DataPoint dataPoint) {
                                for (Field field : dataPoint.getDataType().getFields()) {
                                    Value val = dataPoint.getValue(field);
                                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
                                    Log.i(TAG, "Detected DataPoint value: " + val);

                                    Log.d(TAG, "Broadcasting total steps for now.");
                                    // Broadcasting step count now
                                    Intent stepCountNowResultIntent =
                                            new Intent(GoogleFitService.STEPS_PER_SECOND_COUNT)
                                                    // Puts the status into the Intent
                                                    // Put the information received instead of a hardcoded 10
                                                    .putExtra(GoogleFitService.STEPS_PER_SECOND_COUNT_RESULT, val.asInt());
                                    // Broadcasts the Intent to receivers in this app.
                                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(stepCountNowResultIntent);
                                }
                            }
                        };

                        Fitness.SensorsApi.add(
                                physicalFitnessClient,
                                new SensorRequest.Builder()
                                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
                                        .setDataType(dataType) // Can't be omitted.
                                        .setSamplingRate(1, TimeUnit.SECONDS)
                                        .build(),
                                mListener)
                                .setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        if (status.isSuccess()) {
                                            Log.i(TAG, "Listener registered!");
                                        } else {
                                            Log.i(TAG, "Listener not registered.");
                                        }
                                    }
                                });
                        // [END register_data_listener]
                    }
                }
            }
        });
        // [END find_data_sources]
    }

    /**
     * Handle action STEP_COUNT_TODAY in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStepCountToday() {
        Log.d(TAG, "Counting steps for today.");
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long startTime = cal.getTimeInMillis();

        final DataReadRequest stepCountTodayReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_STEP_COUNT_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.HistoryApi.readData(physicalFitnessClient, stepCountTodayReadRequest).setResultCallback(new ResultCallback<DataReadResult>() {
            @Override
            public void onResult(DataReadResult dataReadResult) {
                // Getting step data for today
                Log.d(TAG, "Getting step data for today");
                DataSet stepData = dataReadResult.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);

                int totalSteps = 0;

                for (DataPoint dp : stepData.getDataPoints()) {
                    for(Field field : dp.getDataType().getFields()) {
                        int steps = dp.getValue(field).asInt();

                        totalSteps += steps;

                    }
                }

                // Broadcasting total steps for today
                Log.d(TAG, "Broadcasting total steps for today.");
                Intent stepCountTodayResultIntent =
                        new Intent(GoogleFitService.STEP_COUNT_TODAY)
                                // Puts the status into the Intent
                                .putExtra(GoogleFitService.STEP_COUNT_TODAY_RESULT, totalSteps);
                Log.d(TAG, "Step count today result " + totalSteps);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(stepCountTodayResultIntent);
            }
        });
    }

    private void handleActionMilesCountToday() {
        Log.d(TAG, "Counting miles for today.");
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long startTime = cal.getTimeInMillis();

        final DataReadRequest stepCountTodayReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_DISTANCE_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.HistoryApi.readData(physicalFitnessClient, stepCountTodayReadRequest).setResultCallback(new ResultCallback<DataReadResult>() {
            @Override
            public void onResult(DataReadResult dataReadResult) {
                // Getting step data for today
                Log.d(TAG, "Getting miles covered data for today");
                DataSet milesData = dataReadResult.getDataSet(DataType.TYPE_DISTANCE_DELTA);

                float totalMiles = 0;

                for (DataPoint dp : milesData.getDataPoints()) {
                    for(Field field : dp.getDataType().getFields()) {
                        float miles = dp.getValue(field).asFloat();
                        totalMiles += miles;
                    }
                }

                totalMiles = totalMiles / 1000;
                totalMiles = (float) (Math.round(totalMiles * 100.0) / 100.0);

                // Broadcasting total miles for today
                Log.d(TAG, "Broadcasting total miles for today.");
                Intent milesCountTodayResultIntent =
                        new Intent(GoogleFitService.MILES_COUNT_TODAY)
                                // Puts the status into the Intent
                                .putExtra(GoogleFitService.MILES_TODAY_COUNT_RESULT, totalMiles);
                Log.d(TAG, "Miles count today result " + totalMiles);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(milesCountTodayResultIntent);
            }
        });
    }

    private void handleActionCaloriesExpendedToday() {
        Log.d(TAG, "Counting calories expended for today.");
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long startTime = cal.getTimeInMillis();

        final DataReadRequest caloriesExpendedTodayReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_CALORIES_EXPENDED)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.HistoryApi.readData(physicalFitnessClient, caloriesExpendedTodayReadRequest).setResultCallback(new ResultCallback<DataReadResult>() {
            @Override
            public void onResult(DataReadResult dataReadResult) {
                // Getting calories expended for today
                Log.d(TAG, "Getting calories expended data for today");
                DataSet caloriesData = dataReadResult.getDataSet(DataType.TYPE_CALORIES_EXPENDED);

                float totalCalories = 0;

                for (DataPoint dp : caloriesData.getDataPoints()) {
                    for(Field field : dp.getDataType().getFields()) {
                        float calories = dp.getValue(field).asFloat();
                        totalCalories += calories;
                    }
                }

                totalCalories = totalCalories / 1000;
                totalCalories = (float) (Math.round(totalCalories * 100.0) / 100.0);

                // Broadcasting total miles for today
                Log.d(TAG, "Broadcasting total calories for today.");
                Intent caloriesExpendedTodayResultIntent =
                        new Intent(GoogleFitService.CALORIES_EXPENDED_TODAY)
                                // Puts the status into the Intent
                                .putExtra(GoogleFitService.CALORIES_EXPENDED_TODAY_RESULT, totalCalories);
                Log.d(TAG, "Miles count today result " + totalCalories);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(caloriesExpendedTodayResultIntent);
            }
        });
    }
}
