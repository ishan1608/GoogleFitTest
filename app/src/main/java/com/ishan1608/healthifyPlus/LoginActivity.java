package com.ishan1608.healthifyPlus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends Activity {
    private static final int REQUEST_OAUTH = 1;

    /**
     * Track whether an authorization activity is stacking over the current activity, i.e. when
     * a known auth error is being resolved, such as showing the account chooser or presenting a
     * consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";
    private static final String TAG = "LoginActivity";
    private boolean authInProgress = false;

    private GoogleApiClient newSignInClient = null;

    // Initialize Button
    private ImageButton initializeButton;
    private TextView statusTextView;

    // already signed in case
    private static final String SIGNINTAG = "SINGED-IN-TEST";
    public GoogleApiClient alreadySignedInClient = null;

    // WelcomePager
    ViewPager welcomePager = null;
    TextView errorTextView = null;

    // Elements for internet related work
    public EditText userNameEditText;
    public EditText userEmailEditText;
    public RadioGroup userGenderGroup;
    public TextView registrationErrorTextView;
    public String userName;
    public String userPassword;
    public EditText userPasswordEditText;
    public EditText userConfirmationPasswordEditText;
    public boolean nameError;
    public boolean passwordError;
    private Intent waterReminderIntent;
    private EditText userAgeEditText;
    private boolean ageError;
    private String userAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Put Application specific code here
        // Removing title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        Log.d(TAG, "LoginActivity onCreate");

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        // Testing if the user is already signed in and if that's true directly moving to next activity
        buildAlreadySignedInFitnessClient();
        alreadySignedInClient.connect();

        // Initialize button for the case when user is not already signed in
        initializeButton = (ImageButton) findViewById(R.id.initialize_button);
        initializeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hiding as soon as the login is clicked so that the user can't click it again
                initializeButton.setEnabled(false);
                initializeButton.setVisibility(View.GONE);
                buildNewSignInFitnessClient();
                newSignInClient.connect();
            }
        });

        // Welcome pager showing some welcome Pager
        welcomePager = (ViewPager) findViewById(R.id.welcome_pager);
        welcomePager.setAdapter(new welcomeScreenAdapter(getFragmentManager()));
        // Automatically switching welcome Pager
        Timer welcomePagerTimer = new Timer();

        welcomePagerTimer.scheduleAtFixedRate(new TimerTask() {
            int pageCount = 0;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        welcomePager.setCurrentItem(pageCount, true);
                    }
                });
                pageCount = pageCount + 1;
                pageCount = pageCount % 3;
            }
        }, 0, 2500);

        errorTextView = (TextView) findViewById(R.id.error_message);

//        // Starting water reminder service
//        // It shouldn't be required, but kept as a backup solution
//        Log.d(TAG, "Starting water reminder service from login activity");
//        waterReminderIntent = new Intent(getApplicationContext(), WaterReminderService.class);
//        waterReminderIntent.setAction(WaterReminderService.WATER_REMINDER_TASK);
//        getApplicationContext().startService(waterReminderIntent);
//        Log.d(TAG, "water reminder service called from login activity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (newSignInClient != null) {
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
        if (alreadySignedInClient != null && alreadySignedInClient.isConnected()) {
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
     * Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
     * (see documentation for details). Authentication will occasionally fail intentionally,
     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     * can address. Examples of this include the user never having signed in before, or having
     * multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildAlreadySignedInFitnessClient() {
        // Create the Google API Client
        alreadySignedInClient = new GoogleApiClient.Builder(this)
                // Adding Fitness Sensor API
                .addApi(Fitness.SENSORS_API)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                        // Adding Plus API
                .addApi(Plus.API)
                        // Adding Fitness Scopes
//                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_BODY_READ))
                .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
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

                                // Storing registered user email locally
                                // Preference Manager for storing user email locally
                                SharedPreferences userInformationPreferences = getApplicationContext().getSharedPreferences(
                                        LoginActivity.class.getSimpleName(),
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor userInformationPreferencesEditor = userInformationPreferences.edit();

//                            userInformationPreferencesEditor.putString("userEmail", registerAppUserJSONObject.getString("user"));
                                userInformationPreferencesEditor.putString("userEmail", Plus.AccountApi.getAccountName(alreadySignedInClient));
                                userInformationPreferencesEditor.commit();

                                // Going to next Activity
                                goToMainAppActivity(Plus.AccountApi.getAccountName(alreadySignedInClient));
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
                                initializeButton.setVisibility(View.VISIBLE);
                            }
                        }
                )
                .build();
    }


    /**
     * Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
     * (see documentation for details). Authentication will occasionally fail intentionally,
     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     * can address. Examples of this include the user never having signed in before, or having
     * multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildNewSignInFitnessClient() {
        Log.d(TAG, "buildNewSignInFitnessClient called.");
        logStatus("buildNewSignInFitnessClient called.");

        // Create the Google API Client
        newSignInClient = new GoogleApiClient.Builder(this)
                // Adding Fitness Sensor API
                .addApi(Fitness.SENSORS_API)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                        // Adding Plus API
                .addApi(Plus.API)
                        // Adding Fitness Scopes
//                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_BODY_READ))
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
                                            LoginActivity.this, 0).show();
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
                                        result.startResolutionForResult(LoginActivity.this,
                                                REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.e(TAG, "Exception while starting resolution activity", e);
                                        logStatus("Exception while starting resolution activity");
                                    }
                                }
                            }
                        }
                )
                .build();
    }

    private void registerAndNext() {
        Log.d(TAG, "Called registerAndNext");
        logStatus("Called registerAndNext");
        // Getting user information
        final String currentAccountName = Plus.AccountApi.getAccountName(newSignInClient);
        Log.d(TAG, currentAccountName);
        logStatus(currentAccountName);

        // Check the server for the availability of user and if not available then register
        Log.d(TAG, "Checking and registering user.");
        logStatus("Checking and registering user.");

        // Check user existence
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            logStatus("Internet connected");

            Thread checkUserThread = new Thread(new Runnable() {

                @Override
                public void run() {
//                    logStatus("Internet thread started.");
                    Log.d(TAG, "Internet thread started.");
                    // Check if the user is already registered
                    try {
                        // This is sending GET request for checking existance of user
                        Uri checkUserUri = Uri.parse("https://health-server.herokuapp.com/checkUser").buildUpon()
                                .appendQueryParameter("email", currentAccountName)
                                .build();
                        URL checkUserURL = new URL(checkUserUri.toString());

                        // Create the request to health-server, and open the connection
                        HttpURLConnection checkUserURLConnection = (HttpURLConnection) checkUserURL.openConnection();
                        checkUserURLConnection.setRequestMethod("GET");
                        checkUserURLConnection.connect();

                        // Read the input stream into a String
                        InputStream checkUserInputStream = checkUserURLConnection.getInputStream();
                        StringBuffer checkuserStringBuffer = new StringBuffer();
                        if (checkUserInputStream == null) {
                            // Nothing to do.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // display error
                                    logStatus("Not Connected to server");
//                                    // Hiding Welcome pager
//                                    welcomePager.setVisibility(View.GONE);
                                    // Displaying error
                                    errorTextView.setText("Cannot connect to our server.\nPlease check your internet first.");
                                    RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
                                    errorScreen.setVisibility(View.VISIBLE);
                                }
                            });
                            return;
                        }
                        BufferedReader checkUserBufferedReader = new BufferedReader(new InputStreamReader(checkUserInputStream));

                        String line;
                        while ((line = checkUserBufferedReader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // checkuserStringBuffer for debugging.
                            checkuserStringBuffer.append(line + "\n");
                        }

                        if (checkuserStringBuffer.length() == 0) {
                            // Stream was empty.  No point in parsing.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // display error
                                    logStatus("Not Connected to server");
//                                    // Hiding Welcome pager
//                                    welcomePager.setVisibility(View.GONE);
                                    // Displaying error
                                    errorTextView.setText("Cannot connect to our server.\nPlease check your internet first.");
                                    RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
                                    errorScreen.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            String checkUserJsonString = checkuserStringBuffer.toString();

                            // Have to extract JSON from string
                            final JSONObject checkUserJSONObject = new JSONObject(checkUserJsonString);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if(checkUserJSONObject.getBoolean("error")) {
                                            // User not registered
                                            // Display the registration form and continue
                                            // Hiding Welcome pager
                                            welcomePager.setVisibility(View.GONE);
                                            // Hiding Login button
                                            initializeButton.setEnabled(false);
                                            initializeButton.setVisibility(View.GONE);

                                            // Displaying registration form
                                            final View registrationForm = getLayoutInflater().inflate(R.layout.registration_form, null);
                                            LinearLayout registrationScreen = (LinearLayout) findViewById(R.id.registration_screen);
                                            registrationScreen.addView(registrationForm);
                                            registrationScreen.setVisibility(View.VISIBLE);

                                            // Setting the values grabbed from Google+ profile
                                            Person currentPerson = Plus.PeopleApi.getCurrentPerson(newSignInClient);
                                            if (currentPerson != null) {
                                                String currentPersonName = currentPerson.getDisplayName();
                                                Log.d(TAG, "Current Person Name : " + currentPersonName);
                                                logStatus("Current Person Name : " + currentPersonName);
                                                // Setting name
                                                userNameEditText = (EditText)registrationForm.findViewById(R.id.name_edit_text);
                                                userNameEditText.setText(currentPersonName);
                                                userNameEditText.setEnabled(false);

                                                // TODO: Set age
                                                userAgeEditText = (EditText) registrationForm.findViewById(R.id.age_edit_text);
                                                // Setting Email
                                                userEmailEditText = (EditText) registrationForm.findViewById(R.id.email_edit_text);
                                                userEmailEditText.setText(currentAccountName);
                                                userEmailEditText.setEnabled(false);

                                                // Setting Gender
                                                if(currentPerson.hasGender()) {
                                                    userGenderGroup = (RadioGroup) registrationForm.findViewById(R.id.gender_radio_group);
                                                    int currentPersonGender = currentPerson.getGender();
                                                    switch (currentPersonGender) {
                                                        case Person.Gender.FEMALE:
                                                            ((RadioButton)userGenderGroup.getChildAt(0)).setChecked(true);
                                                            break;
                                                        case Person.Gender.MALE:
                                                            ((RadioButton)userGenderGroup.getChildAt(1)).setChecked(true);
                                                            break;
                                                        case Person.Gender.OTHER:
                                                            ((RadioButton)userGenderGroup.getChildAt(2)).setChecked(true);
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                    userGenderGroup.setEnabled(false);
                                                }
                                            }

                                            // Setting on register button
                                            final ImageButton registrationButton = (ImageButton) registrationForm.findViewById(R.id.registration_button);
                                            registrationButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    registrationErrorTextView = (TextView) registrationForm.findViewById(R.id.error_text_view);
                                                    // Checking Name
                                                    if(userNameEditText == null || userNameEditText.getText().toString().trim().equalsIgnoreCase("")) {
                                                        registrationErrorTextView.setText("Name not valid");
                                                        nameError = true;
                                                        registrationErrorTextView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        userName = userNameEditText.getText().toString().trim();
                                                        nameError = false;
                                                    }
                                                    // Checking Age
                                                    if(userAgeEditText == null || userAgeEditText.getText().toString().trim().equalsIgnoreCase("")) {
                                                        registrationErrorTextView.setText("Age not valid");
                                                        ageError = true;
                                                        registrationErrorTextView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        userAge = userAgeEditText.getText().toString().trim();
                                                        ageError = false;
                                                    }

                                                    // Checking password
                                                    userPasswordEditText = (EditText) registrationForm.findViewById(R.id.password_edit_text);
                                                    userConfirmationPasswordEditText = (EditText) registrationForm.findViewById(R.id.confirm_password_edit_text);
                                                    if(userPasswordEditText == null || userConfirmationPasswordEditText == null || ! userPasswordEditText.getText().toString().equals(userConfirmationPasswordEditText.getText().toString())) {
                                                        registrationErrorTextView.setText("Passwords do not match.");
                                                        passwordError = true;
                                                        registrationErrorTextView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        userPassword = userPasswordEditText.getText().toString();
                                                        passwordError = false;
                                                    }

                                                    // If no error send the data to server and continue to main app activity
                                                    if(!nameError && !passwordError &&!ageError) {
                                                        // Reading the gender
                                                        int userGenderID = userGenderGroup.getCheckedRadioButtonId();
                                                        String userGender = null;
                                                        switch (userGenderID) {
                                                            case R.id.gender_female_radio_button:
                                                                userGender = "Female";
                                                                break;
                                                            case R.id.gender_male_radio_button:
                                                                userGender = "Male";
                                                                break;
                                                            case R.id.gender_other_radio_button:
                                                                userGender = "Other";
                                                                break;
                                                        }

                                                        // Sending the data to server for registration
                                                        registerUserOnServer(currentAccountName, userName, userGender, userPassword, userAge);
                                                    }
                                                }
                                            });

                                        } else {
                                            // User already registered
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // Storing registered user email locally
                                                    // Preference Manager for storing user email locally
                                                    SharedPreferences userInformationPreferences = getApplicationContext().getSharedPreferences(
                                                            LoginActivity.class.getSimpleName(),
                                                            Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor userInformationPreferencesEditor = userInformationPreferences.edit();

//                            userInformationPreferencesEditor.putString("userEmail", registerAppUserJSONObject.getString("user"));
                                                    userInformationPreferencesEditor.putString("userEmail", currentAccountName);
                                                    userInformationPreferencesEditor.commit();
                                                    goToMainAppActivity(currentAccountName);
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            checkUserThread.start();
        } else {
            // display error
            logStatus("Not Connected to internet");
//            // Hiding Welcome pager
//            welcomePager.setVisibility(View.GONE);
            // Displaying error
            errorTextView.setText("Internet not Connected.\nPlease connect to internet first before continuing.");
            RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
            errorScreen.setVisibility(View.VISIBLE);
        }
    }

    private void registerUserOnServer(final String userEmail, final String userName, final String userGender, final String userPassword, final String userAge) {
        Log.d(TAG, "registerUserOnServer called");
        Thread userRegistrationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // This is sending GET request for checking existance of user
                    Uri registerAppUserUri = Uri.parse("https://health-server.herokuapp.com/registerAppUser").buildUpon()
                            .appendQueryParameter("email", userEmail)
                            .appendQueryParameter("name", userName)
                            .appendQueryParameter("gender", userGender)
                            .appendQueryParameter("password", userPassword)
                            .appendQueryParameter("age", userAge)
                            .build();
                    URL registerAppUserURL = null;
                    registerAppUserURL = new URL(registerAppUserUri.toString());
                    // Create the request to health-server, and open the connection
                    HttpURLConnection registerAppUserURLConnection = (HttpURLConnection) registerAppUserURL.openConnection();
                    registerAppUserURLConnection.setRequestMethod("GET");
                    registerAppUserURLConnection.connect();

                    // Read the input stream into a String
                    InputStream registerAppUserInputStream = registerAppUserURLConnection.getInputStream();
                    StringBuffer registerAppuserStringBuffer = new StringBuffer();
                    if (registerAppUserInputStream == null) {
                        // Nothing to do.
                        return;
                    }
                    BufferedReader registerAppUserBufferedReader = new BufferedReader(new InputStreamReader(registerAppUserInputStream));

                    String line;
                    while ((line = registerAppUserBufferedReader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // registerAppuserStringBuffer for debugging.
                        registerAppuserStringBuffer.append(line + "\n");
                    }

                    if (registerAppuserStringBuffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // display error
                                logStatus("Not Connected to server");
//                                // Hiding Welcome pager
//                                welcomePager.setVisibility(View.GONE);
                                // Displaying error
                                errorTextView.setText("Cannot connect to our server.\nPlease check your internet first.");
                                RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
                                errorScreen.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        String registerAppUserJsonString = registerAppuserStringBuffer.toString();
                        // Have to extract JSON from string
                        final JSONObject registerAppUserJSONObject = new JSONObject(registerAppUserJsonString);
                        if(registerAppUserJSONObject.getBoolean("error")) {
                           // Some error occurred while registration
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // display error
                                    logStatus("Could not register on server.");
//                                    // Hiding Welcome pager
//                                    welcomePager.setVisibility(View.GONE);
                                    // Displaying error
                                    errorTextView.setText("Could not register on server.\nPlease contact us to resolve this, or use a different account.");
                                    RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
                                    errorScreen.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            // User registered successfully
                            // continue to main app activity
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Log.d(TAG, "After registration on server email is " + registerAppUserJSONObject.getString("user"));
                                    // Storing registered user email locally
                                    // Preference Manager for storing user email locally
                                    SharedPreferences userInformationPreferences = getApplicationContext().getSharedPreferences(
                                            LoginActivity.class.getSimpleName(),
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor userInformationPreferencesEditor = userInformationPreferences.edit();

//                            userInformationPreferencesEditor.putString("userEmail", registerAppUserJSONObject.getString("user"));
                                    userInformationPreferencesEditor.putString("userEmail", userEmail);
                                    userInformationPreferencesEditor.commit();
                                    goToMainAppActivity(userEmail);
                                }
                            });
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        userRegistrationThread.start();
    }

    private void goToMainAppActivity(final String userEmail) {
        Log.d(TAG, "goToMainAppActivity called");

        // Disabling the initialize button
//        Log.d(TAG, "Disabling login button.");
//        logStatus("Disabling login button.");
        initializeButton.setEnabled(false);
        initializeButton.setVisibility(View.GONE);

        logStatus("I can move now to next Activity.\nHave to make a new GoogleAPIClient there.");
        SharedPreferences userInformationPreferences = getApplicationContext().getSharedPreferences(
                LoginActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);


        Log.d(TAG, userInformationPreferences.getString("userEmail", "user@example.com"));

        // Just being sure by checking one more time about the user being registered already
        if (!userInformationPreferences.getString("userEmail", "user@example.com").equalsIgnoreCase("user@example.com")) {
            // User already logged in and confirmed from local storage.
            // Continue to main activity
            moveToMainActivity();
        } else {
            Thread userVerificationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // This is sending GET request for checking existance of user
                        Uri checkUserUri = Uri.parse("https://health-server.herokuapp.com/checkUser").buildUpon()
                                .appendQueryParameter("email", userEmail)
                                .build();
                        URL checkUserURL = null;
                        checkUserURL = new URL(checkUserUri.toString());
                        // Create the request to health-server, and open the connection
                        HttpURLConnection checkUserURLConnection = (HttpURLConnection) checkUserURL.openConnection();
                        checkUserURLConnection.setRequestMethod("GET");
                        checkUserURLConnection.connect();

                        // Read the input stream into a String
                        InputStream checkUserInputStream = checkUserURLConnection.getInputStream();
                        StringBuffer checkuserStringBuffer = new StringBuffer();

                        if (checkUserInputStream == null) {
                            // Nothing to do.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // display error
                                    logStatus("Not Connected to server");
//                                    // Hiding Welcome pager
//                                    welcomePager.setVisibility(View.GONE);
                                    // Displaying error
                                    errorTextView.setText("Cannot connect to our server.\nPlease check your internet first.");
                                    RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
                                    errorScreen.setVisibility(View.VISIBLE);
                                }
                            });
                            return;
                        }
                        BufferedReader checkUserBufferedReader = new BufferedReader(new InputStreamReader(checkUserInputStream));
                        String line;
                        while ((line = checkUserBufferedReader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // checkuserStringBuffer for debugging.
                            checkuserStringBuffer.append(line + "\n");
                        }

                        if (checkuserStringBuffer.length() == 0) {
                            // Stream was empty.  No point in parsing.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // display error
                                    logStatus("Not Connected to server");
//                                    // Hiding Welcome pager
//                                    welcomePager.setVisibility(View.GONE);
                                    // Displaying error
                                    errorTextView.setText("Cannot connect to our server.\nPlease check your internet first.");
                                    RelativeLayout errorScreen = (RelativeLayout) findViewById(R.id.error_screen);
                                    errorScreen.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            String checkUserJsonString = checkuserStringBuffer.toString();

                            // Have to extract JSON from string
                            final JSONObject checkUserJSONObject = new JSONObject(checkUserJsonString);

                            if(checkUserJSONObject.getBoolean("error")) {
                                // User not registered
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Prompting to register by creating newSignInClient
                                        buildNewSignInFitnessClient();
                                        newSignInClient.connect();
                                    }
                                });
                            } else {
                                // Can skip now to next activity
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                    Log.d(TAG, "Can skip now to next activity, because user logged in");
//                                    logStatus("Can skip now to next activity because user logged in");
//
//                                    // Going to main activity
//                                    Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
////                                    // Adding user info to bundle
////                                    Bundle userInfoBundle = new Bundle();
////                                    // Adding Email
////                                    userInfoBundle.putString("email", userEmail);
////                                    mainActivityIntent.putExtra("user-info", userInfoBundle);
//                                    startActivity(mainActivityIntent);
//                                    finish();
                                        // Storing registered user email locally
                                        // Preference Manager for storing user email locally
                                        SharedPreferences userInformationPreferences = getApplicationContext().getSharedPreferences(
                                                LoginActivity.class.getSimpleName(),
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor userInformationPreferencesEditor = userInformationPreferences.edit();

//                            userInformationPreferencesEditor.putString("userEmail", registerAppUserJSONObject.getString("user"));
                                        userInformationPreferencesEditor.putString("userEmail", userEmail);
                                        userInformationPreferencesEditor.commit();

                                        // Going to main activity
                                        moveToMainActivity();
                                    }
                                });
                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            userVerificationThread.start();
        }
    }

    private void moveToMainActivity() {
        Log.d(TAG, "Can skip now to next activity, because user logged in");
        logStatus("Can skip now to next activity because user logged in");

        // Going to main activity
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }

    private void logStatus(String status) {
        statusTextView = (TextView) findViewById(R.id.status_text_view);
        statusTextView.append(status + "\n");
    }

    private class welcomeScreenAdapter extends FragmentStatePagerAdapter {

        public welcomeScreenAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment() {
                        @Nullable
                        @Override
                        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                            ImageView localImageView = new ImageView(getApplicationContext());
                            localImageView.setImageResource(R.drawable.welcome_screen1);
                            return localImageView;
                        }
                    };
                case 1:
                    return new Fragment() {
                        @Nullable
                        @Override
                        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                            ImageView localImageView = new ImageView(getApplicationContext());
                            localImageView.setImageResource(R.drawable.welcome_screen2);
                            return localImageView;
                        }
                    };
                case 2:
                    return new Fragment() {
                        @Nullable
                        @Override
                        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                            ImageView localImageView = new ImageView(getApplicationContext());
                            localImageView.setImageResource(R.drawable.welcome_screen3);
                            return localImageView;
                        }
                    };
                default:
                    return null;
            }
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return 3;
        }
    }
}