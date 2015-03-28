package com.falcotech.srm.helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by holivares on 27/03/2015.
 */
public class RestPostHelper {

    private static final String TAG = "REST_HELPER";
    private static final int HTTP_STATUS_OK = 200;
    private static final byte[] buff = new byte[1024];

    public static class ApiException extends Exception {
        private static final long serialVersionUID = 1L;

        public ApiException(String msg) {
            super(msg);
        }

        public ApiException(String msg, Throwable thr) {
            super(msg, thr);
        }
    }

    private static synchronized String run(String URL, HashMap<String, Object> parameters) throws ApiException {
        String strResponse = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(URL);
        Log.d(TAG, "va leer url");
        try {
            if(parameters != null) {
                List<NameValuePair> params = new ArrayList<>();
                for (String parameter : parameters.keySet()) {
                    params.add(new BasicNameValuePair(parameter, parameters.get(parameter).toString()));
                }
                request.setEntity(new UrlEncodedFormEntity(params));
            }
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() != HTTP_STATUS_OK) {
                throw new ApiException("HTTP ERROR " + status.getStatusCode());
            }
            Log.d(TAG, "CONNECT TO REMOTE HOST SUCCESSFULLY");
            HttpEntity entity = response.getEntity();
            InputStream ist = entity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            Log.d(TAG, "READ JSON RESPONSE SUCCESSFULLY");
            int readCount = 0;
            while ((readCount = ist.read(buff)) != -1) {
                content.write(buff, 0, readCount);
            }
            strResponse = new String(content.toByteArray());
            Log.d(TAG, "JSON RESPONSE : " + strResponse);
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ERROR CONNECTING TO REMOTE HOST : " + e.getMessage());
            throw new ApiException("Ocurrio un error al conectar al servidor" + e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, "ERROR READING RESPONSE DATA : " + e.getMessage());
            throw new ApiException("Ocurrio un error al conectar al servidor" + e.getMessage(), e);
        }

        return strResponse;
    }

    public static synchronized JSONArray getJsonArray(String URL) throws JSONException, ApiException {
        return getJsonArray(URL, null);
    }

    public static synchronized JSONArray getJsonArray(String URL, HashMap<String, Object> parameters) throws JSONException, ApiException {
        String response = run(URL, parameters);
        JSONArray jsonArray = new JSONArray(response);
        return jsonArray;
    }

    public static synchronized JSONObject getJsonObject(String URL, HashMap<String, Object> parameters) throws JSONException, ApiException {
        String response = run(URL, parameters);
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject;
    }

    public static synchronized JSONObject getJsonObject(String URL) throws JSONException, ApiException {
        return getJsonObject(URL, null);
    }
}