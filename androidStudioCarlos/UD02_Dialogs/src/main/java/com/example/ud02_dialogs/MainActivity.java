package com.example.ud02_dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIrEjercicio1 = findViewById(R.id.btnIrEjercicio1);
        Button btnIrEjercicio2 = findViewById(R.id.btnIrEjercicio2);

        btnIrEjercicio1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EjercicioUnoActivity.class);
            startActivity(intent);
        });

        btnIrEjercicio2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EjercicioDosActivitiy.class);
            startActivity(intent);
        });
    }
}
