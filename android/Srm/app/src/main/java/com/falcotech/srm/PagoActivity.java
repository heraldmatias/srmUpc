package com.falcotech.srm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.falcotech.srm.helper.RestPostHelper;
import com.falcotech.srm.util.CommonUtilities;
import com.falcotech.srm.util.GCMConstants;
import com.falcotech.srm.util.TimePickerFragment;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class PagoActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "PagoActivity";

    Spinner spServicio;
    Button btnRecarga, btnCancelar;
    EditText txtMonto;
    EditText txtNumeroTarjeTransporte;

    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AXv6izNPjU3jidb9XHZcnRrqixkm8agQQ7RGnNwabH2mvw7C5aVivfAcAos8LY3jeY9WXiW0oVEscpcZ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        // getActionBar().hide();
        spServicio = (Spinner) findViewById(R.id.spServicio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.servicioTransporte, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spServicio.setAdapter(adapter);
        spServicio.setOnItemSelectedListener(this);

        txtMonto = (EditText) findViewById(R.id.txtMonto);
        txtNumeroTarjeTransporte = (EditText) findViewById(R.id.txtNumeroTarjeTransporte);

        btnRecarga = (Button) findViewById(R.id.btnRecarga);
        btnRecarga.setOnClickListener(this);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        initPayment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pago, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.btnRecarga:
                    doPayment();
                    break;
                case R.id.btnCancelar:
                    startActivity(new Intent(this, MenuActivity.class));
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "requestCode: " + requestCode);
        Log.i(TAG, "resultCode: " + resultCode);
        if (resultCode == RESULT_OK) {
            Context app = getApplicationContext();
            Intent intent =new Intent(this, MensajeActivity.class);
            intent.putExtra("mensajeResultado", app.getResources().getString(R.string.message_payment_ok));

            SharedPreferences prefs = getSharedPreferences(HomeActivity.class.getSimpleName(), Context.MODE_PRIVATE);
            final String registrationId = prefs.getString(GCMConstants.PROPERTY_REG_ID, "");

            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    try {
                        String consulta = CommonUtilities.SERVER_URL+"pago";
                        HashMap<String,Object> paramPost = new HashMap<String, Object>();
                        paramPost.put("regId",registrationId);
                        String response = RestPostHelper.run(consulta, paramPost);
                        Log.i(TAG, "response el pago en central:" + response);
                    } catch (Exception e) {
                        Log.e(TAG,"Pago",e);
                    }
                    return msg;
                }

                @Override
                protected void onPostExecute(String msg) {

                    Log.i(TAG,msg + "\n");
                }
            }.execute(null, null, null);

            startActivity(intent);
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    private void initPayment() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void doPayment() {
        if (validPayment()) {
            String price = txtMonto.getText().toString();
            String serviceType = spServicio.getSelectedItem().toString();
            PayPalPayment payment = new PayPalPayment(new BigDecimal(price), "USD", "Recarga de " + serviceType,
                    PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, 0);
        }
    }

    private Boolean validPayment() {
        Boolean result = Boolean.FALSE;
        if (spServicio.getSelectedItem() == null) {
            Toast.makeText(this, "Debe seleccionar el tipo de servicio", Toast.LENGTH_SHORT).show();
        } else if (txtMonto.getText().toString().equals("")) {
            Toast.makeText(this, "Debe seleccionar ingresar el monto de recarga", Toast.LENGTH_SHORT).show();
        } else if (txtNumeroTarjeTransporte.getText().toString().equals("")) {
            Toast.makeText(this, "Debe ingresar el número de tarjeta de servicio", Toast.LENGTH_SHORT).show();
        } else {
            result = Boolean.TRUE;
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
