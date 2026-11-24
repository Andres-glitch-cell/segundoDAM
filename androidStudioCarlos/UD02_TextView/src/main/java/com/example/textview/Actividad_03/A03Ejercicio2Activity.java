package com.example.textview.Actividad_03;

import com.example.textview.R;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class A03Ejercicio2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a03_ejercicio2);

        TextView tvEstado = findViewById(R.id.tvEstado);

        findViewById(R.id.btnBoton1).setOnClickListener(v -> {
            tvEstado.setText(R.string.boton1_pulsado);
            tvEstado.setTextColor(Color.RED);
        });

        findViewById(R.id.btnBoton2).setOnClickListener(v -> {
            tvEstado.setText(R.string.boton2_pulsado);
            tvEstado.setTextColor(Color.GREEN);
        });
    }
}