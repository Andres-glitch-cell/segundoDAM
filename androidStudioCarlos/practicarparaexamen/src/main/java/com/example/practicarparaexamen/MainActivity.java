package com.example.practicarparaexamen;

// Importaciones Android

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

// Widgets
// AndroidX
// Java

public class MainActivity extends AppCompatActivity {

    private TextView tvResultado;

    // ==========================================
    // 2. MÉTODO ONCREATE
    // ==========================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // ==========================================
        // EJERCICIO 1. SALUDO
        // ==========================================

        EditText etNombre = findViewById(R.id.etNombre);
        Button btnSaludar = findViewById(R.id.btnSaludar);
        TextView tvMensaje = findViewById(R.id.tvMensaje);

        btnSaludar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString();

                if (nombre.isEmpty()) {
                    Toast.makeText(MainActivity.this, "El campo nombre está vacío", Toast.LENGTH_SHORT).show();
                } else {
                    tvMensaje.setText("Hola " + nombre + " !");
                    Toast.makeText(MainActivity.this, "Saludo correcto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ==========================================
        // EJERCICIO 2. BORRAR CON ALERTDIALOG
        // ==========================================

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
                        etNombre.setText("");

                        Toast.makeText(MainActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", null);

                builder.create().show();
            }
        });

        // ==========================================
        // EJERCICIO 3. SELECTOR DE FECHA
        // ==========================================

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

                        tvFecha.setText("Fecha seleccionada: " + dayOfMonth + "/" + mesReal + "/" + year);
                    }
                }, añoActual, mesActual, diaActual);

                datePicker.show();
            }
        });

        // ==========================================
        // EJERCICIO 4. SELECTOR DE HORA
        // ==========================================

        Button btnHora = findViewById(R.id.btnHora);
        TextView tvHora = findViewById(R.id.tvHora);

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();

                int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
                int minutoActual = calendario.get(Calendar.MINUTE);

                TimePickerDialog timerPicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvHora.setText("Hora seleccionada: " + hourOfDay + ":" + String.format("%02d", minute));
                    }
                }, horaActual, minutoActual, true);

                timerPicker.show();
            }
        });

        // ==========================================
        // EJERCICIO 5. APERTURA DE PÁGINA WEB
        // ==========================================

        Button btnNotificacion = findViewById(R.id.btnNotificacion);

        btnNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificator = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                String canalID = "canal_IES";

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel canalDeNotificacion = new NotificationChannel(canalID, "Notificación IES Joan Coromines", NotificationManager.IMPORTANCE_DEFAULT);

                    notificator.createNotificationChannel(canalDeNotificacion);
                }

                Intent abrirPaginaGVA = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://portal.edu.gva.es/iesjoancoromines/es/centro/"));

                startActivity(abrirPaginaGVA);
            }
        });

        // ==========================================
        // RESULTADO DEL MENÚ
        // ==========================================

        tvResultado = findViewById(R.id.tvResultado);
    }
}