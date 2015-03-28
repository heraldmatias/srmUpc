package com.falcotech.srm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.falcotech.srm.util.TimePickerFragment;


public class PagoActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spServicio,spBanco,dateMes,dateAnno ;
    private EditText txtNumeroCuenta,txtMonto, txtCodVer, txtNumeroTarjeTransporte;
    private Button btnRecarga,btnCancelar;

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

        spBanco = (Spinner) findViewById(R.id.spBanco);
        adapter = ArrayAdapter.createFromResource(this, R.array.bancos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBanco.setAdapter(adapter);
        spBanco.setOnItemSelectedListener(this);

        dateMes = (Spinner) findViewById(R.id.dateMes);
        adapter = ArrayAdapter.createFromResource(this, R.array.meses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateMes.setAdapter(adapter);
        dateMes.setOnItemSelectedListener(this);

        dateAnno = (Spinner) findViewById(R.id.dateAnno);
        adapter = ArrayAdapter.createFromResource(this, R.array.annos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateAnno.setAdapter(adapter);
        dateAnno.setOnItemSelectedListener(this);



        txtNumeroCuenta = (EditText)findViewById(R.id.TxtNumeroCuenta);
        txtMonto = (EditText)findViewById(R.id.txtMonto);
        txtCodVer = (EditText)findViewById(R.id.txtCodVer);
        txtNumeroTarjeTransporte = (EditText)findViewById(R.id.txtNumeroTarjeTransporte);

        btnRecarga = (Button)findViewById(R.id.btnRecarga);
        btnRecarga.setOnClickListener(this);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

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

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Fecha de Vencimiento");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v!=null){
            switch (v.getId()){
                case R.id.btnRecarga:
                    startActivity(new Intent(this, Mensaje.class));
                    break;
                case R.id.btnCancelar:
                    startActivity(new Intent(this, MenuActivity.class));
                    break;
            }
        }
    }
}
