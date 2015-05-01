package com.ishan1608.healthifyPlus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.plus.Plus;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


public class MainActivity extends Activity {

    private static final String TAG = "USER_INFO_CLIENT";
    private ImageView userImageHero;
    private Bitmap defaultUserImage;
    private Bitmap defaultUserImageRounded;

    private GoogleApiClient userInfoFitnessClient;

    private static final int REQUEST_OAUTH = 1;
    /**
     * Track whether an authorization activity is stacking over the current activity, i.e. when
     * a known auth error is being resolved, such as showing the account chooser or presenting a
     * consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private TextView userEmailHero;
    private TextView userNameHero;
    private ImageView userCoverHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Removing title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // Logging SHA1 fingerprint
        Log.d(TAG, "SHA1 fingerprint is " + getCertificateSHA1Fingerprint());

        // Downloading Library settings
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);

        // Getting handle for user image hero
        userImageHero = (ImageView) findViewById(R.id.user_image_hero);
        userCoverHero = (ImageView) findViewById(R.id.user_cover_hero);

        Log.d(TAG, "calling builduserInfoFitnessClient");
        // Getting the GoogleAPI Client
        builduserInfoFitnessClient();
        userInfoFitnessClient.connect();

        // Filling list of features on the navigation drawer
        ListView featuresList = (ListView) findViewById(R.id.features_list);
        featuresList.setAdapter(new FeaturesListAdapter(this));

        // Default going to home fragment
        Fragment homeFragment = new HomeFragment();
        FragmentTransaction featureTransaction = getFragmentManager().beginTransaction();

        featureTransaction.replace(R.id.main_container, homeFragment);
        featureTransaction.commit();
    }

    private String getCertificateSHA1Fingerprint() {
        PackageManager pm = getApplicationContext().getPackageManager();
        String packageName = getApplicationContext().getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!userInfoFitnessClient.isConnecting() && !userInfoFitnessClient.isConnected()) {
                    userInfoFitnessClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        Log.d("GetRoundedShape", "getRoundedShapeCalled");
        int targetWidth = 48;
        int targetHeight = 48;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), new Paint(Paint.FILTER_BITMAP_FLAG));
        return targetBitmap;
    }

    /**
     * Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
     * (see documentation for details). Authentication will occasionally fail intentionally,
     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     * can address. Examples of this include the user never having signed in before, or having
     * multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void builduserInfoFitnessClient() {
        Log.d(TAG, "builduserInfoFitnessClient called.");

        // Create the Google API Client
        userInfoFitnessClient = new GoogleApiClient.Builder(this)
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
                                Log.i(TAG, "builduserInfoFitnessClient Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.
                                // Just displaying successful connection message

                                // Setting user name and email
                                userEmailHero = (TextView) findViewById(R.id.user_email_hero);
                                String currentUserEmail = Plus.AccountApi.getAccountName(userInfoFitnessClient);
                                Log.d(TAG, "currentUserEmail " + currentUserEmail);
                                userEmailHero.setText(currentUserEmail);

                                Person currentPerson = Plus.PeopleApi.getCurrentPerson(userInfoFitnessClient);
//                                Log.d(TAG, "currentPerson " + currentPerson.toString());
                                if (currentPerson != null) {
                                    userNameHero = (TextView) findViewById(R.id.user_name_hero);
                                    String currentPersonName = currentPerson.getDisplayName();
                                    Log.d(TAG, "currentPersonName " + currentPersonName);
                                    userNameHero.setText(currentPersonName);
                                }

                                if (currentPerson != null) {
                                    String userImageURL = currentPerson.getImage().getUrl();
                                    // Have to display this image
                                    ImageLoader.getInstance().loadImage(userImageURL, new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap userImage) {
                                            super.onLoadingComplete(imageUri, view, userImage);
                                            userImageHero.setImageBitmap(userImage);

                                            // Making the image circular
                                            defaultUserImage = ((BitmapDrawable)userImageHero.getDrawable()).getBitmap();
                                            defaultUserImageRounded = getRoundedShape(defaultUserImage);
                                            userImageHero.setImageBitmap(defaultUserImageRounded);
                                        }
                                    });
                                }

                                if (currentPerson != null) {
                                    // TODO: check for the cover photo before applying it
//                                    Person.Cover.CoverPhoto userCoverPhoto = currentPerson.getCover().getCoverPhoto();
//                                    String userCoverURL = userCoverPhoto.getUrl();
//                                    Log.d(TAG, userCoverURL);
//                                    ImageLoader.getInstance().displayImage(userCoverURL, userCoverHero);
                                }


                                // TODO: Further proceed to get google fit data (Comes a bit later)
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
                                            MainActivity.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Log.i(TAG, "Attempting to resolve failed connection");
                                        authInProgress = true;
                                        result.startResolutionForResult(MainActivity.this,
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
}
