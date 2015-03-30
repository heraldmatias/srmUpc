package com.falcotech.srm.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.falcotech.srm.MapActivity;
import com.falcotech.srm.R;
import com.falcotech.srm.helper.RestPostHelper;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by holivares on 30/03/2015.
 */
public class TransportStationLocationTask extends AsyncTask<String, Integer, String> {

    public static final String TAG = "StationLocationTask";
    public static final String API_URL = "http://colibricomunicaciones.com:8080/SRMCentral/Estaciones/?tipoServicio=";

    private ProgressDialog progressDialog;

    public TransportStationLocationTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressDialog = ProgressDialog.show(MapActivity.self, MapActivity.staticThis.getResources().getString(R.string.prompt_locations_update),
                MapActivity.staticThis.getResources().getString(R.string.title_locations_update), true);
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        try {
            response = RestPostHelper.run(API_URL + params[0], null);
        } catch (RestPostHelper.ApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "Rest call success");
        if (result == null) {
            Toast.makeText(MapActivity.staticThis, MapActivity.staticThis.getResources().getString(R.string.data_locations_update), Toast.LENGTH_LONG).show();
        } else {
            try {
                JSONArray jsonArray = RestPostHelper.getJsonArray(result);
                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    MapActivity.addMarkMap(new LatLng(Double.parseDouble(jsonObject.getString("d")), Double.parseDouble(jsonObject.getString("dd"))), R.drawable.marker_rounded_red);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        progressDialog.dismiss();
    }
}
