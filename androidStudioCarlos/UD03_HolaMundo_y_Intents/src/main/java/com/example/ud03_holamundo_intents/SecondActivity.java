package com.example.ud03_holamundo_intents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "EJEMPLO_SEGUNDA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i(TAG, "SecondActivity - onCreate");

        Button btnAbrirWeb = findViewById(R.id.btn_abrir_web);
        btnAbrirWeb.setOnClickListener(v -> abrirNavegadorWeb());
    }

    private void abrirNavegadorWeb() {
        // Ejercicio 3 de UD03_A01 / Ejercicio 6 de UD03_P01: Intent Implícito para abrir navegador web
        Intent intentWeb = new Intent(Intent.ACTION_VIEW);
        intentWeb.setData(Uri.parse("https://www.google.es"));
        startActivity(intentWeb);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "SecondActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "SecondActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "SecondActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "SecondActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "SecondActivity - onDestroy");
    }
}
