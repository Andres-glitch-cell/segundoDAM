package com.example.ud03_holamundo_intents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EJEMPLO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Estoy en onCreate");

        Button btnAbrirSegunda = findViewById(R.id.btn_abrir_segunda);
        btnAbrirSegunda.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Estoy en onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Estoy en onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Estoy en onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Estoy en onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Estoy en onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Estoy en onDestroy");
    }
}
