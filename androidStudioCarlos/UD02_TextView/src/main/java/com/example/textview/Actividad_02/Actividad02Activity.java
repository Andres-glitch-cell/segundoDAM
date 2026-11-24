package com.example.textview.Actividad_02;

import com.example.textview.R;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Actividad02Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad02);

        findViewById(R.id.btnA02Ej1).setOnClickListener(v ->
                startActivity(new Intent(this, Ejercicio1Activity.class)));
        findViewById(R.id.btnA02Ej2).setOnClickListener(v ->
                startActivity(new Intent(this, Ejercicio2Activity.class)));
        findViewById(R.id.btnA02Ej3).setOnClickListener(v ->
                startActivity(new Intent(this, Ejercicio3Activity.class)));
    }
}