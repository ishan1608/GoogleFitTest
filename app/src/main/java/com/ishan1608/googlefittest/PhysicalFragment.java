package com.ishan1608.googlefittest;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.plus.Plus;

import java.util.concurrent.TimeUnit;

public class PhysicalFragment extends Fragment {

    private static final int REQUEST_OAUTH = 1;

    /**
     * Track whether an authorization activity is stacking over the current activity, i.e. when
     * a known auth error is being resolved, such as showing the account chooser or presenting a
     * consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";
    private static final String TAG = "PHYSICAL-FRAGMENT";
    private boolean authInProgress = false;
    private View returnView;
    private GoogleApiClient physicalFitnessClient;

    // [START mListener_variable_reference]
    // Need to hold a reference to this listener, as it's passed into the "unregister"
    // method in order to stop all sensors from sending data to this listener.
    private OnDataPointListener mListener;
    private TextView physicalFragmentStatus;
    private TextView totalStepsTextView;
    private int totalSteps;
    // [END mListener_variable_reference]

    public PhysicalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalSteps = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        returnView = inflater.inflate(R.layout.fragment_physical, container, false);

        physicalFragmentStatus = (TextView) returnView.findViewById(R.id.physical_fragment_status);

        totalStepsTextView = (TextView) returnView.findViewById(R.id.step_count);

        // Making and registering a GoogleFit client to get fitness data
        buildPhysicalFitnessClient();
        physicalFitnessClient.connect();

        // Starting the service on click of button
        Button stepCountNowButton = (Button) returnView.findViewById(R.id.step_count_now_button);
        stepCountNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates a new Intent to start the step count now IntentService.
                Intent stepCountNowIntent;
                stepCountNowIntent = new Intent(getActivity(), GoogleFitService.class);
                stepCountNowIntent.setAction(GoogleFitService.STEP_COUNT_NOW);
                getActivity().startService(stepCountNowIntent);
            }
        });

        Button stepCountTodayButton = (Button) returnView.findViewById(R.id.step_count_today_button);
        stepCountTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates a new Intent to start the step count today IntentService.
                Intent stepCountTodayIntent;
                stepCountTodayIntent = new Intent(getActivity(), GoogleFitService.class);
                stepCountTodayIntent.setAction(GoogleFitService.STEP_COUNT_TODAY);
                getActivity().startService(stepCountTodayIntent);
            }
        });



        return returnView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == Activity.RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!physicalFitnessClient.isConnecting() && !physicalFitnessClient.isConnected()) {
                    physicalFitnessClient.connect();
                }
            }
        }
    }

    void logStatus(String message) {
        physicalFragmentStatus.append("\n" + message);
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
        physicalFitnessClient = new GoogleApiClient.Builder(getActivity())
                // Adding Fitness Sensor API
                .addApi(Fitness.SENSORS_API)
                        // Adding Plus API
                .addApi(Plus.API)
                        // Adding Fitness Scopes
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_BODY_READ))
                .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
                        // Adding Plus Scopes
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.
                                // Just displaying successful connection message
//                                Toast.makeText(getApplicationContext(), "Connected successfully", Toast.LENGTH_LONG).show();

                                // TODO: Invoke fitness APIS and do cool stuff
                                doCoolStuff();
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
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                            getActivity(), 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Log.i(TAG, "Attempting to resolve failed connection");
                                        authInProgress = true;
                                        result.startResolutionForResult(getActivity(),
                                                REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.e(TAG, "Exception while starting resolution activity", e);
                                    }
                                }
                            }
                        }
                )
                .build();
    }

    private void doCoolStuff() {
        logStatus("Reday to do some cool stuff");
        // [START find_data_sources]
        Fitness.SensorsApi.findDataSources(physicalFitnessClient, new DataSourcesRequest.Builder()
                // At least one datatype must be specified.
//                .setDataTypes(DataType.TYPE_LOCATION_SAMPLE)
                        .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                        // Can specify whether data type is raw or derived.
//                .setDataSourceTypes(DataSource.TYPE_RAW)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
                        logStatus("Result: " + dataSourcesResult.getStatus().toString());
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Log.i(TAG, "Data source found: " + dataSource.toString());
                            logStatus("Data source found: " + dataSource.toString());
                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());
                            logStatus("Data Source type: " + dataSource.getDataType().getName());

                            //Let's register a listener to receive Activity data!
//                            if (dataSource.getDataType().equals(DataType.TYPE_LOCATION_SAMPLE)
//                                    && mListener == null) {
//                                Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
//                                logStatus("Data source for LOCATION_SAMPLE found!  Registering.");
//                                registerFitnessDataListener(dataSource,
//                                        DataType.TYPE_LOCATION_SAMPLE);
//                            }
                            if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA) && mListener == null) {
                                Log.i(TAG, "Data source for STEP_COUNT_DELTA found!  Registering.");
                                logStatus("Data source for STEP_COUNT_DELTA found!  Registering.");
                                registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_DELTA);
                            }
                        }
                    }
                });
        // [END find_data_sources]
    }

    /**
     * Register a listener with the Sensors API for the provided {@link DataSource} and
     * {@link DataType} combo.
     */
    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        logStatus("registerFitnessDataListener called");
        // [START register_data_listener]
        mListener = new OnDataPointListener() {
            @Override
            public void onDataPoint(DataPoint dataPoint) {
                for (Field field : dataPoint.getDataType().getFields()) {
                    Value val = dataPoint.getValue(field);
                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
                    logStatus("Detected DataPoint field: " + field.getName());
                    Log.i(TAG, "Detected DataPoint value: " + val);
                    logStatus("Detected DataPoint value: " + val);
                    countSteps(val.asInt());
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
                            logStatus("Listener registered!");
                        } else {
                            Log.i(TAG, "Listener not registered.");
                            logStatus("Listener not registered.");
                        }
                    }
                });
        // [END register_data_listener]
    }

    void countSteps(int newSteps) {
        totalSteps += newSteps;
        totalStepsTextView.setText("Total Steps : " + totalSteps);
    }
}
