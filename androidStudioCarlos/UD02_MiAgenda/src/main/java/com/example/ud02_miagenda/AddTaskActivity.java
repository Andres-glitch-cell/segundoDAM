package com.example.ud02_miagenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnSeleccionarCategoria, btnSeleccionarFecha, btnSeleccionarHora;

    private String categoriaSeleccionada = "";
    private String fechaSeleccionada = "";
    private String horaSeleccionada = "";

    private final String[] categorias = {"Estudios", "Trabajo", "Personal", "Otros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombre = findViewById(R.id.etNombre);
        btnSeleccionarCategoria = findViewById(R.id.btnSeleccionarCategoria);
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha);
        btnSeleccionarHora = findViewById(R.id.btnSeleccionarHora);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        // Selección de categoría mediante AlertDialog
        btnSeleccionarCategoria.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Selecciona una categoría")
                    .setItems(categorias, (dialog, which) -> {
                        categoriaSeleccionada = categorias[which];
                        btnSeleccionarCategoria.setText(categoriaSeleccionada);
                    })
                    .show();
        });

        // Selección de fecha mediante DatePickerDialog
        btnSeleccionarFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, (month1 + 1), year1);
                btnSeleccionarFecha.setText(fechaSeleccionada);
            }, year, month, day);
            datePickerDialog.show();
        });

        // Selección de hora mediante TimePickerDialog
        btnSeleccionarHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute1);
                btnSeleccionarHora.setText(horaSeleccionada);
            }, hour, minute, true);
            timePickerDialog.show();
        });

        // Botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre de la tarea no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }
            if (categoriaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Debe seleccionar una categoría", Toast.LENGTH_SHORT).show();
                return;
            }
            if (fechaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show();
                return;
            }
            if (horaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Debe seleccionar una hora", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto Tarea y devolverlo a MainActivity
            Tarea nuevaTarea = new Tarea(nombre, categoriaSeleccionada, fechaSeleccionada, horaSeleccionada);
            Intent intentResult = new Intent();
            intentResult.putExtra("NUEVA_TAREA", nuevaTarea);
            setResult(RESULT_OK, intentResult);
            Toast.makeText(this, "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Botón Cancelar
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
