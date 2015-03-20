package com.falcotech.srm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.util.Arrays;
import java.util.Set;


public class HomeActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "Home";
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private ConnectionResult mConnectionResult;
    private GoogleApiClient googleApiClient;
    private ProgressDialog mConnectionProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

//        if (resultCode == RESULT_OK) {
//            mConnectionResult = null;
//            googleApiClient.connect();
//        }


//        Set<String> keySet = data.getExtras().keySet();
//        for (String key : keySet) {
//            Log.d(TAG, String.format("%s = %s", key, data.getExtras().get(key)));
//            Class aclass = data.getExtras().get(key).getClass();
//
//        }
//        Log.d(TAG, data.toString());

    }

    @Override
    public void onClick(View view) {
        if(isNetworkAvailable()) {
            if (view.getId() == R.id.plusButton && !googleApiClient.isConnected()) {
                if (mConnectionResult == null) {
                    mConnectionProgressDialog.show();
                } else {
                    try {
                        mConnectionResult.startResolutionForResult(this, CommonStatusCodes.SIGN_IN_REQUIRED);
                    } catch (IntentSender.SendIntentException e) {
                        // Intenta la conexión de nuevo.
                        mConnectionResult = null;
                        googleApiClient.connect();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Debe estar conectado a internet", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
    }

}
