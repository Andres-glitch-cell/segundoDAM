package com.example.ud02_layouts_textview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ejercicio 2: Configurado completamente desde la clase Java
        TextView tvEjercicio2 = findViewById(R.id.tv_ejercicio2);
        tvEjercicio2.setText("Texto 2: Configurado totalmente desde la clase Java del Activity.");
        tvEjercicio2.setTextSize(16);
        tvEjercicio2.setTextColor(Color.parseColor("#2E7D32")); // Verde
        tvEjercicio2.setTypeface(null, Typeface.BOLD);
        tvEjercicio2.setBackgroundColor(Color.parseColor("#E8F5E9"));
        tvEjercicio2.setPadding(32, 32, 32, 32);

        // Ejercicio 3: Primera parte configurada en XML, segunda parte añadida desde Java
        TextView tvEjercicio3 = findViewById(R.id.tv_ejercicio3);
        String textoActual = tvEjercicio3.getText().toString();
        String segundaParteJava = "Y esta segunda parte se añade programáticamente desde la clase Java del Activity.";
        tvEjercicio3.setText(textoActual + segundaParteJava);
        tvEjercicio3.setTypeface(null, Typeface.BOLD_ITALIC);
    }
}
