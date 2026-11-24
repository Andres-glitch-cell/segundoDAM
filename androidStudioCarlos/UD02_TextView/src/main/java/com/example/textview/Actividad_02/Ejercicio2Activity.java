package com.example.textview.Actividad_02;

import com.example.textview.R;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ejercicio2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        TextView tvTexto = findViewById(R.id.tvTexto);
        tvTexto.setText("Texto construido desde XML.\nTama\u00f1o 20dp, Italic y color Azul");
        tvTexto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tvTexto.setTypeface(null, Typeface.ITALIC);
        tvTexto.setTextColor(Color.BLUE);
        tvTexto.setGravity(Gravity.CENTER);
    }
}