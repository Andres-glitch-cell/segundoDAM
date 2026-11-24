package com.example.textview.Actividad_02;

import com.example.textview.R;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ejercicio3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        TextView tvTexto = findViewById(R.id.tvTexto);

        SpannableString extra = new SpannableString("\nTexto a\u00f1adido con Append desde Java");
        extra.setSpan(new ForegroundColorSpan(Color.BLUE), 0, extra.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        extra.setSpan(new StyleSpan(Typeface.ITALIC), 0, extra.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvTexto.append(extra);
    }
}