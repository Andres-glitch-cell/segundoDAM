package com.example.textview.Actividad_06;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textview.R;

public class Actividad06Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad06);

        findViewById(R.id.btnA06Ej1).setOnClickListener(v ->
                startActivity(new Intent(this, A06Ejercicio1Activity.class)));
        findViewById(R.id.btnA06Ej2).setOnClickListener(v ->
                startActivity(new Intent(this, A06Ejercicio2Activity.class)));
        findViewById(R.id.btnA06Ej3).setOnClickListener(v ->
                startActivity(new Intent(this, A06Ejercicio3Activity.class)));
    }
}