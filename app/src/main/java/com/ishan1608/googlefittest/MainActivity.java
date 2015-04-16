package com.ishan1608.googlefittest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.IntentSender;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


public class MainActivity extends Activity {
    private static final int REQUEST_OAUTH = 1;

    /**
     *  Track whether an authorization activity is stacking over the current activity, i.e. when
     *  a known auth error is being resolved, such as showing the account chooser or presenting a
     *  consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";
    private static final String TAG = "FIT-TEST";
    private boolean authInProgress = false;

    private GoogleApiClient newSignInClient = null;

    // Initialize Button
    private Button initializeButton;
    private TextView statusTextView;

    // already signed in case
    private  static final String SIGNINTAG = "SINGED-IN-TEST";
    private GoogleApiClient alreadySignedInClient = null;

    // Client to work with
//    private GoogleApiClient workingClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Put Application specific code here
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        // Testing if the user is already signed in and if that's true directly moving to next activity
        buildAlreadySignedInFitnessClient();
        alreadySignedInClient.connect();

        // Initialize button for the case when user is not already signed in
        initializeButton = (Button) findViewById(R.id.initialize_button);
        initializeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildNewSignInFitnessClient();
                newSignInClient.connect();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(newSignInClient != null) {
            // Connect to the Fitness API
            Log.i(TAG, "Connecting...");
            logStatus("Connecting...");
            newSignInClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (newSignInClient != null && newSignInClient.isConnected()) {
            newSignInClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alreadySignedInClient != null && alreadySignedInClient.isConnected()) {
            alreadySignedInClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!newSignInClient.isConnecting() && !newSignInClient.isConnected()) {
                    newSignInClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    /**
     *  Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     *  to connect to Fitness APIs. The scopes included should match the scopes your app needs
     *  (see documentation for details). Authentication will occasionally fail intentionally,
     *  and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     *  can address. Examples of this include the user never having signed in before, or having
     *  multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildAlreadySignedInFitnessClient() {
        // Create the Google API Client
        alreadySignedInClient = new GoogleApiClient.Builder(this)
                // Adding Fitness Sensor API
                .addApi(Fitness.SENSORS_API)
                        // Adding Plus API
                .addApi(Plus.API)
                        // Adding Fitness Scopes
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                        // Adding Plus Scopes
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(SIGNINTAG, "alreadySignedInClient Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.
                                // Just displaying successful connection message
//                                Toast.makeText(getApplicationContext(), "Connected successfully", Toast.LENGTH_LONG).show();
                                logStatus("alreadySignedInClient Connected successfully");

                                // Going to next Activity
                                goToNextActivity();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(SIGNINTAG, "Connection lost.  Cause: Network Lost.");
                                    logStatus("Connection lost.  Cause: Network Lost.");
                                } else if (i == ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(SIGNINTAG, "Connection lost.  Reason: Service Disconnected");
                                    logStatus("Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.

                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                Log.i(SIGNINTAG, "Connection failed. Cause: " + result.toString() + "\nEnabling login button.");
                                logStatus("Connection failed. Cause: " + result.toString() + "\nEnabling login button.");
                                // Enabling the login button
                                initializeButton.setEnabled(true);
                            }
                        }
                )
                .build();
    }


    /**
     *  Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     *  to connect to Fitness APIs. The scopes included should match the scopes your app needs
     *  (see documentation for details). Authentication will occasionally fail intentionally,
     *  and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     *  can address. Examples of this include the user never having signed in before, or having
     *  multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildNewSignInFitnessClient() {
        Log.d(TAG, "buildNewSignInFitnessClient called.");
        logStatus("buildNewSignInFitnessClient called.");

        // Create the Google API Client
        newSignInClient = new GoogleApiClient.Builder(this)
                // Adding Fitness Sensor API
                .addApi(Fitness.SENSORS_API)
                // Adding Plus API
                .addApi(Plus.API)
                // Adding Fitness Scopes
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
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
                                logStatus("Connected successfully");

                                // Registering and going to next Activity
                                registerAndNext();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                    logStatus("Connection lost.  Cause: Network Lost.");
                                } else if (i == ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                    logStatus("Connection lost.  Reason: Service Disconnected");
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
                                logStatus("Connection failed. Cause: " + result.toString());
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                            MainActivity.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Log.i(TAG, "Attempting to resolve failed connection");
                                        logStatus("Attempting to resolve failed connection");
                                        authInProgress = true;
                                        result.startResolutionForResult(MainActivity.this,
                                                REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.e(TAG,"Exception while starting resolution activity", e);
                                        logStatus("Exception while starting resolution activity");
                                    }
                                }
                            }
                        }
                )
                .build();
    }

    private void registerAndNext(){
        Log.d(TAG, "Called registerAndNext");
        logStatus("Called registerAndNext");
        // Getting user information
        String currentAccountName = Plus.AccountApi.getAccountName(newSignInClient);
        Log.d(TAG, currentAccountName);
        logStatus(currentAccountName);

        // Getting the name of the user as given on Google Plus profile
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(newSignInClient);
        if(currentPerson != null) {
            String currentPersonName = currentPerson.getDisplayName();
            Log.d(TAG, currentPersonName);
            logStatus(currentPersonName);
        }

        // Check the server for the availability of user and if not available then register
        Log.d(TAG, "Checking and registering user.");
        logStatus("Checking and registering user.");

        // Can skip now to next activity
        Log.d(TAG, "User registered move to next activity.");
        logStatus("User registered move to next activity.");

    }

    private void goToNextActivity() {
        // Disabling the initialize button
        Log.d(TAG, "Disabling login button.");
        logStatus("Disabling login button.");
        initializeButton.setEnabled(false);

        logStatus("I can move now to next Activity.\nHave to make a new GoogleAPIClient there.");

        // Can skip now to next activity
        Log.d(TAG, "Can skip now to next activity, because user logged in");
        logStatus("Can skip now to next activity because user logged in");
    }

    private void logStatus(String status) {
        statusTextView = (TextView) findViewById(R.id.status_text_view);
        statusTextView.append(status + "\n");
    }
}
