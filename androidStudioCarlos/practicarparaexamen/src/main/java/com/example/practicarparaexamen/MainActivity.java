package com.example.practicarparaexamen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * 1. Enlazamos los elementos/etiquetas de XML con variables creadas en Java
         */
        EditText etNombre = findViewById(R.id.etNombre);
        Button btnSaludar = findViewById(R.id.btnSaludar);
        TextView tvMensaje = findViewById(R.id.tvMensaje);

        /**
         * 2. Programamos que ocurre cuando pulsamos el botón
         */

        btnSaludar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString();
                tvMensaje.setText("Hola " + nombre);
                Toast.makeText(MainActivity.this, "Saludo enviado", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnBorrar = findViewById(R.id.btnBorrar);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Seguro que quieres borrar el nombre?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        etNombre.setText("Texto de prueba");
                        tvMensaje.setText("Aquí saldra el saludo");
                        Toast.makeText(MainActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }
        });

        Button btnFecha = findViewById(R.id.btnFecha);
        TextView tvFecha = findViewById(R.id.tvFecha);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();
                int añoActual = calendario.get(Calendar.YEAR);
                int mesActual = calendario.get(Calendar.MONTH);
                int diaActual = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int mesReal = month + 1;
                        tvFecha.setText("Fecha selecionada" + dayOfMonth + "/" + mesReal + "/" + year);
                    }
                }, añoActual, mesActual, diaActual);
                datePicker.show();
            }
        });

        Button btnHora = findViewById(R.id.btnHora);
        TextView tvHora = findViewById(R.id.tvHora);

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();
                int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
                int minutoActual = calendario.get(Calendar.MINUTE);
                int segundoActual = calendario.get(Calendar.SECOND);

                TimePickerDialog timerPicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvHora.setText("Hora seleccionada: " + hourOfDay + ":" + minute);
                    }
                },
                        horaActual, minutoActual, true
                );
                timerPicker.show();
            }
        });
    }
}