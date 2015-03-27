package com.falcotech.srm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
        switch (v.getId()) {
            case R.id.btnMapa:
                startActivity(new Intent(this, MapActivity.class));
        }
    }
}
