package com.example.textview.Actividad_06;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textview.R;

public class A06Ejercicio1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a06_ejercicio1);

        ListView listado = findViewById(R.id.listado);
        TextView tvSeleccion = findViewById(R.id.tvSeleccion);

        String[] paises = {
                "Espa\u00f1a", "Francia", "Italia", "Alemania", "Portugal",
                "M\u00e9xico", "Argentina", "Colombia", "Jap\u00f3n", "Brasil"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, paises);
        listado.setAdapter(adapter);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) parent.getItemAtPosition(position);
                tvSeleccion.setText(elemento);
            }
        });
    }
}