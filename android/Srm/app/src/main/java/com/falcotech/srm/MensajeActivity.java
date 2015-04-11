package com.falcotech.srm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MensajeActivity extends ActionBarActivity implements View.OnClickListener {

    Button btnConfirmPayment;
    private TextView msgResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        btnConfirmPayment = (Button) findViewById(R.id.btnConfirmPayment);
        btnConfirmPayment.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        String text = extras.getString("mensajeResultado");
        msgResultado = (TextView) findViewById(R.id.msgResultado);
        msgResultado.setText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mensaje, menu);
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
       // startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

   /* @Override
    public void onBackPressed()
    {

        finish();

    }*/
}
