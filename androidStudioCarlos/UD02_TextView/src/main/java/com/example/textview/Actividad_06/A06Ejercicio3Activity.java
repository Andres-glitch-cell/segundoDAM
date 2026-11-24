package com.example.textview.Actividad_06;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.textview.R;

public class A06Ejercicio3Activity extends AppCompatActivity {

    private TextView tvSeleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a06_ejercicio3);

        tvSeleccion = findViewById(R.id.tvSeleccion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a06_ejercicio3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Los items que abren un submenú no cambian el texto
        if (item.hasSubMenu()) {
            return super.onOptionsItemSelected(item);
        }
        tvSeleccion.setText(item.getTitle());
        return true;
    }
}