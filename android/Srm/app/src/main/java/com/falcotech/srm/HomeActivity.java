package com.falcotech.srm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Session;
import com.facebook.SessionState;
import com.falcotech.srm.util.GCMConstants;
import com.falcotech.srm.util.ServerUtilities;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.maps.MapFragment;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class HomeActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "Home";
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private ConnectionResult mConnectionResult;
    private GoogleApiClient googleApiClient;
    private ProgressDialog mConnectionProgressDialog;

    private GoogleCloudMessaging gcm;
    private AtomicInteger msgId = new AtomicInteger();
    private Context context;

    String regid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       /* getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        System.out.println(getActionBar());
        if(getActionBar()!=null){
            getActionBar().hide();
        }
        System.out.println(getSupportActionBar());
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }*/
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        //googleApiClient.connect();
        findViewById(R.id.plusButton).setOnClickListener(this);
        findViewById(R.id.fbButton).setOnClickListener(this);
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Procesando...");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // Respond to session state changes, ex: updating the view
            if (state.isOpened()) {
                Log.i(TAG, "Logged in...");
            } else if (state.isClosed()) {
                Log.i(TAG, "Logged out...");
            }
        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isOpened() && !session.isClosed()) {
                session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("public_profile")).setCallback(statusCallback));
            } else {
                Session.openActiveSession(this, true, statusCallback);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        mConnectionProgressDialog.dismiss();
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, ConnectionResult.SIGN_IN_REQUIRED);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            googleApiClient.connect();
        }
        mConnectionResult = connectionResult;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        boolean authorizeUser = false;
        if (resultCode == RESULT_OK) {
            if(requestCode == 64206) {
                authorizeUser = fbAuth();
            } else if(requestCode == 4) {
                authorizeUser = plusAuth();
            }
        }

        if(authorizeUser) {

            context = getApplicationContext();

            // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
            if (checkPlayServices()) {
                gcm = GoogleCloudMessaging.getInstance(this);
                regid = getRegistrationId(context);

                if (regid.isEmpty()) {
                    registerInBackground();
                }
            } else {
                Log.i(TAG, "No valid Google Play Services APK found.");
            }

            startActivity(new Intent(this, MenuActivity.class));
        } else {
            Toast.makeText(this, getString(R.string.authorize_error), Toast.LENGTH_LONG).show();
        }
    }

    private boolean plusAuth() {
        return true;
    }

    private boolean fbAuth() {
        return true;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(GCMConstants.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

                Log.i(TAG,msg + "\n");
            }
        }.execute(null, null, null);
    }

    private void sendRegistrationIdToBackend() {
        Log.i(TAG,"Registrar en el servidor :"+regid);

        ServerUtilities.register(null, regid);


    }

    @Override
    public void onClick(View view) {
        if(isNetworkAvailable()) {
            if (view.getId() == R.id.plusButton && !googleApiClient.isConnected()) {
                if (mConnectionResult == null) {
                    mConnectionProgressDialog.show();
                    googleApiClient.connect();
                } else {
                    try {
                        mConnectionResult.startResolutionForResult(this, CommonStatusCodes.SIGN_IN_REQUIRED);
                    } catch (IntentSender.SendIntentException e) {
                        // Intenta la conexión de nuevo.
                        mConnectionResult = null;
                        googleApiClient.connect();
                    }
                }
            } else if (view.getId() == R.id.fbButton) {
                onClickLogin();
            }
        } else {
            Toast.makeText(this, getString(R.string.internet_disconnected_error), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        GCMConstants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCMConstants.PROPERTY_REG_ID, regId);
        editor.putInt(GCMConstants.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(GCMConstants.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(GCMConstants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(HomeActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}
