package com.example.textview.Actividad_03;

import com.example.textview.R;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Actividad03Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad03);

        findViewById(R.id.btnA03Ej1).setOnClickListener(v ->
                startActivity(new Intent(this, A03Ejercicio1Activity.class)));
        findViewById(R.id.btnA03Ej2).setOnClickListener(v ->
                startActivity(new Intent(this, A03Ejercicio2Activity.class)));
    }
}