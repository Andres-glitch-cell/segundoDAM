package com.example.textview.Actividad_03;

import com.example.textview.R;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class A03Ejercicio1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a03_ejercicio1);

        TextView tvEstado = findViewById(R.id.tvEstado);

        findViewById(R.id.btnBoton).setOnClickListener(v -> {
            tvEstado.setText(R.string.boton_pulsado);
            tvEstado.setTextColor(Color.RED);
        });
    }
}