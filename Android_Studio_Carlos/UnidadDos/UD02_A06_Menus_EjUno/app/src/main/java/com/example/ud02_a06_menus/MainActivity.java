package com.example.ud02_a06_menus;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ud02_a06_menus.R;

public class MainActivity extends AppCompatActivity {

    private TextView textoSeleccion;

    private final String[] paises = {
            "España", "Francia", "Italia", "Alemania", "Portugal",
            "Reino Unido", "Japón", "Brasil", "Argentina", "México"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listado = findViewById(R.id.listado);
        textoSeleccion = findViewById(R.id.textoSeleccion);

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, paises);
        listado.setAdapter(adaptador);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) parent.getItemAtPosition(position);
                textoSeleccion.setText(elemento);
            }
        });
    }
}