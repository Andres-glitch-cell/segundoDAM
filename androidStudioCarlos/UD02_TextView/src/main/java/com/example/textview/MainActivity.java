package com.example.textview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.textview.Actividad_06.Actividad06Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textview.Actividad_02.Actividad02Activity;
import com.example.textview.Actividad_03.Actividad03Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnEjemplos).setOnClickListener(v ->
                Toast.makeText(this, "Sin ejercicios a\u00fan", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnActividad01).setOnClickListener(v ->
                Toast.makeText(this, "Sin ejercicios a\u00fan", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnActividad02).setOnClickListener(v ->
                startActivity(new Intent(this, Actividad02Activity.class)));

        findViewById(R.id.btnActividad03).setOnClickListener(v ->
                startActivity(new Intent(this, Actividad03Activity.class)));

        findViewById(R.id.btnActividad06).setOnClickListener(v ->
                startActivity(new Intent(this, Actividad06Activity.class)));
    }
}