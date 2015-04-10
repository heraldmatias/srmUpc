package com.falcotech.srm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MenuActivity extends ActionBarActivity implements View.OnClickListener {

    ImageButton btnPay;
    ImageButton btnTime;
    ImageButton btnMap;
    ImageButton btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnPay = (ImageButton) findViewById(R.id.btnPago);
        btnMap = (ImageButton) findViewById(R.id.btnMapa);
        btnTime = (ImageButton) findViewById(R.id.btnTiempo);
        btnExit = (ImageButton) findViewById(R.id.btnSalir);

        btnExit.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        //getActionBar().hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnPago:
                startActivity(new Intent(this, PagoActivity.class));
                break;
            case R.id.btnMapa:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.btnTiempo:
                startActivity(new Intent(this, TiempoActivity.class));
                break;
            case R.id.btnSalir:
                // i'm lazy, do nothing
                break;
        }
    }


}

