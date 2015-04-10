package com.falcotech.srm;

import android.app.ProgressDialog;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.falcotech.srm.helper.RestPostHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TiempoActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String API_URL = "http://colibricomunicaciones.com/SRMCentral/Estaciones?tipoServicio=";
    private final static String TAG = TiempoActivity.class.getName();
    private Button btnCalcular;
    private EditText txtResultadoTiempo;
    private Spinner spServiceTypeTiempo,spPuntoInicio,spPuntoDestino;

    private LinkedHashMap<String, String> listaPuntos;
    private int pi,pd,difere;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);
        //getActionBar().hide();

        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(this);
        spServiceTypeTiempo = (Spinner) findViewById(R.id.spServiceTypeTiempo);
        spServiceTypeTiempo.setOnItemSelectedListener(this);
        spPuntoInicio = (Spinner) findViewById(R.id.spPuntoInicio);
        spPuntoDestino = (Spinner) findViewById(R.id.spPuntoDestino);
        txtResultadoTiempo = (EditText) findViewById(R.id.txtResultadoTiempo);
    }

    private void cargarSpinners(String respuesta) {
        txtResultadoTiempo.setText("");
        Log.i(TAG, "respuesta:"+respuesta);
        try {
            JSONArray jsonArray = RestPostHelper.getJsonArray(respuesta);
            JSONObject jsonObject ;
            String[] arrayp = new String[jsonArray.length()],arrayd = new String[jsonArray.length()];
            for (int index = 0; index < jsonArray.length(); index++) {
                jsonObject = jsonArray.getJSONObject(index);
                arrayp[index] = jsonObject.getString("nombre");
                listaPuntos.put(arrayp[index],jsonObject.toString());
            }

            Log.i(TAG, "arrayp:"+arrayp);

            arrayd = arrayp.clone();

            ArrayAdapter<CharSequence> adaptador = new ArrayAdapter<CharSequence>(
                    this, android.R.layout.simple_spinner_item, arrayp);
            spPuntoInicio.setAdapter(adaptador);

            adaptador = new ArrayAdapter<CharSequence>(
                    this, android.R.layout.simple_spinner_item, arrayd);
            spPuntoDestino.setAdapter(adaptador);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtResultadoTiempo.setText("");

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView itemSelected = (TextView) view;
        String query = null;
        try {
            if (itemSelected!=null) {
                if (!itemSelected.getText().toString().equals("Seleccione")) {
                    query = URLEncoder.encode(itemSelected.getText().toString(), "UTF-8");
                    Log.i(TAG, query);
                    listaPuntos = new LinkedHashMap<String, String>();
                    String consulta = API_URL + query;

                    Log.i(TAG, "consulta:" + consulta);
                    ConsultaTiempos consultaTiempos = new ConsultaTiempos(this, consulta);
                    consultaTiempos.execute(new String[]{});
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tiempo, menu);
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
    public void onClick(View v) {
        if(v!=null){
            if(v.getId() == R.id.btnCalcular){
                /*spServiceTypeTiempo.setOnItemSelectedListener(this);
                spPuntoInicio = (Spinner) findViewById(R.id.spPuntoInicio);
                spPuntoDestino = (Spinner) findViewById(R.id.spPuntoDestino);
                */
                TextView itemSelected = (TextView) spServiceTypeTiempo.getSelectedView();
                System.out.println(itemSelected);
                if(itemSelected!=null){
                    String pServicio = itemSelected.getText().toString();
                    itemSelected = (TextView) spPuntoInicio.getSelectedView();
                    pi = spPuntoInicio.getSelectedItemPosition();
                    String pInicio = itemSelected.getText().toString();
                    itemSelected = (TextView) spPuntoDestino.getSelectedView();
                    pd = spPuntoDestino.getSelectedItemPosition();
                    String pDestino = itemSelected.getText().toString();
                    if( !pServicio.trim().equals("Seleccione") && !pInicio.trim().equals("")&&!pDestino.trim().equals("")){
                        int temp = 0;
                        if(pi<pd){
                            difere = pd-pi;
                        }else{
                            temp = pi;
                            pi = pd;
                            pd = temp;
                            difere = pd-pi;
                        }
                        calcularTiempoDeDestino();
                    }
                }
            }
        }
    }


    private void calcularTiempoDeDestino() {
        int count = 0;
        boolean conitnua = false;
        int acumulador = 0;
        String obj = "";
        JSONObject jsonObj,jsonItem;
        JSONArray jsonEstadisticas;
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        Iterator itr2 = listaPuntos.keySet().iterator();
        try {
            String msg = "";
            if( hora>7 && hora<23 ) {
                while (itr2.hasNext()) {
                    String key = (String) itr2.next();
                    if (pi == count) {
                        conitnua = true;
                    }

                    if (pd == count) {
                        conitnua = false;
                    }

                    if (conitnua) {
                        obj = listaPuntos.get(key);
                        jsonObj = new JSONObject(obj);
                        jsonEstadisticas = jsonObj.getJSONArray("estadisticas");
                        for (int index = 0; index < jsonEstadisticas.length(); index++) {
                            jsonItem = jsonEstadisticas.getJSONObject(index);
                            if (jsonItem.get("horaInicio").equals(String.valueOf(hora) + ":00")) {
                                acumulador += Integer.valueOf(jsonItem.getInt("cantidad"));
                            }
                        }
                    }
                    count++;
                }
                System.out.println(difere);
                System.out.println(acumulador);
                msg = "Desde que ingresa a estaci\00F3n el tiempo de llegada a su destino sera entre " + ((acumulador-difere)/17) + " y " + (acumulador/15) + " min.";

            }else{
                msg = "Esta fuera del servicio, el servicio se restablece dentro de las 7:00 horas hasta las 22:00 horas";
            }
            txtResultadoTiempo.setText(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ConsultaTiempos extends AsyncTask<String, Void, String> {

        private String consulta;
        private TiempoActivity tiempoActivity;

        public ConsultaTiempos(TiempoActivity tiempoActivity,String consulta){
            super();
            this.consulta = consulta;
            this.tiempoActivity = tiempoActivity;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(tiempoActivity, tiempoActivity.getResources().getString(R.string.prompt_locations_update),
                    tiempoActivity.getResources().getString(R.string.title_locations_update), true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String response = RestPostHelper.runGet(consulta);
                return response;

            } catch (RestPostHelper.ApiException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            cargarSpinners(result);
            progressDialog.dismiss();
        }

    }
}
