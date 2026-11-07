package com.example.ud02_a06_menus_ejdos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridView cuadricula;
    private TextView textoSeleccion;

    private final String[] paises = {
            "España", "Francia", "Italia", "Alemania", "Portugal",
            "Reino Unido", "Japón", "Brasil", "Argentina", "México"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cuadricula = findViewById(R.id.cuadricula);
        textoSeleccion = findViewById(R.id.textoSeleccion);

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, paises);
        cuadricula.setAdapter(adaptador);

        cuadricula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) parent.getAdapter().getItem(position);
                textoSeleccion.setText(elemento);
            }
        });
    }
}