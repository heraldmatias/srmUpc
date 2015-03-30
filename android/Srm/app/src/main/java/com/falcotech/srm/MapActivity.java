package com.falcotech.srm;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.falcotech.srm.task.TransportStationLocationTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActionBarActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, AdapterView.OnItemSelectedListener {

    public static final String TAG = "MapActivity";

    LocationRequest mLocationRequest;
    boolean mUpdatesRequested;
    private GoogleApiClient mGoogleApiClient;
    private static GoogleMap map;
    private Location mLastLocation;
    private LatLng latLngOrigin;
    private LatLng latLngCurrent;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    Spinner spServiceType;
    public static Context staticThis;
    public static MapActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        buildGoogleApiClient();
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        spServiceType = (Spinner) findViewById(R.id.spServiceType);
        spServiceType.setOnItemSelectedListener(this);
        staticThis = this.getApplicationContext();
        self = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        latLngCurrent = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        addMarkMap(latLngCurrent, R.drawable.marker_rounded_light_blue, Boolean.TRUE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        CameraUpdate update = null;
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.i(TAG, "Connection successfully");
        if (mLastLocation != null) {
            latLngCurrent = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addMarkMap(latLngCurrent, R.drawable.marker_rounded_light_blue, Boolean.TRUE);
        } else {
            Log.d(TAG, "Error capturing current location");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, String.format("Item data: %s Item position: %s", view.toString(), position));
        if (position > 0) {
            TextView itemSelected = (TextView) view;
            TransportStationLocationTask transportStationLocationTask = new TransportStationLocationTask();
            try {
                transportStationLocationTask.execute(itemSelected.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void addMarkMap(LatLng latLng, Integer markIcon, Boolean animated) {
        CameraUpdate update = null;

        if (latLng != null) {
            Log.d(TAG, "Mark on: " + latLng.toString());
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(markIcon);
            if (animated) {
                update = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                map.animateCamera(update);
            }
            map.addMarker(new MarkerOptions().position(latLng).icon(icon));
        } else {
            Log.d(TAG, "Error capturing current location");
        }
    }

    public static void addMarkMap(LatLng latLng, Integer markIcon) {
        addMarkMap(latLng, markIcon, Boolean.FALSE);
    }
}
