package com.example.layouts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity simplificada.
 * Al estar todo el diseño y formato configurado puramente en XML (activity_main.xml),
 * esta clase Java solo se encarga de inflar la vista correspondiente sin código innecesario.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Carga el layout definido exclusivamente en XML
        setContentView(R.layout.activity_main);
    }
}
