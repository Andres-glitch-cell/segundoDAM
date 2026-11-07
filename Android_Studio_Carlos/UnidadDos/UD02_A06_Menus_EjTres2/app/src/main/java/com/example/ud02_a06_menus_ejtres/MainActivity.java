package com.example.ud02_a06_menus_ejtres;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textoSeleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoSeleccion = findViewById(R.id.textoSeleccion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Los items que abren un submenú no deben cambiar el texto
        if (id == R.id.menu_dias || id == R.id.menu_meses) {
            return super.onOptionsItemSelected(item);
        }

        textoSeleccion.setText(item.getTitle());
        return true;
    }
}