package com.example.ud02_dialogs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjercicioUnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);

        Button btnDialogoBotones = findViewById(R.id.btnDialogoBotones);
        Button btnDialogoLista = findViewById(R.id.btnDialogoLista);

        btnDialogoBotones.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EjercicioUnoActivity.this);
            builder.setTitle("Diálogo con Botones");
            builder.setMessage("¿Desea confirmar la acción?");

            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                Toast.makeText(EjercicioUnoActivity.this, "Has pulsado: Aceptar", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> {
                Toast.makeText(EjercicioUnoActivity.this, "Has pulsado: Cancelar", Toast.LENGTH_SHORT).show();
            });

            builder.setNeutralButton("Neutral", (dialog, which) -> {
                Toast.makeText(EjercicioUnoActivity.this, "Has pulsado: Neutral", Toast.LENGTH_SHORT).show();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btnDialogoLista.setOnClickListener(v -> {
            String[] diasSemana = {
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(EjercicioUnoActivity.this);
            builder.setTitle("Seleccione un día de la semana");
            builder.setItems(diasSemana, (dialog, which) -> {
                String diaSeleccionado = diasSemana[which];
                Toast.makeText(EjercicioUnoActivity.this, "Has seleccionado: " + diaSeleccionado, Toast.LENGTH_SHORT).show();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}
