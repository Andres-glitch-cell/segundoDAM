package com.example.ud02_dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EjercicioDosActivitiy extends AppCompatActivity {

    private Button btnDialogoFecha;
    private Button btnDialogoHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityejerciciodos);

        btnDialogoFecha = findViewById(R.id.btnDialogoFecha);
        btnDialogoHora = findViewById(R.id.btnDialogoHora);

        btnDialogoFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                EjercicioDosActivitiy.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String fechaStr = String.format("%02d/%02d/%d", selectedDay, (selectedMonth + 1), selectedYear);
                    Toast.makeText(EjercicioDosActivitiy.this, "Fecha seleccionada: " + fechaStr, Toast.LENGTH_SHORT).show();
                },
                year, month, day
            );
            datePickerDialog.show();
        });

        btnDialogoHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                EjercicioDosActivitiy.this,
                (view, selectedHour, selectedMinute) -> {
                    String horaStr = String.format("%02d:%02d", selectedHour, selectedMinute);
                    Toast.makeText(EjercicioDosActivitiy.this, "Hora seleccionada: " + horaStr, Toast.LENGTH_SHORT).show();
                },
                hour, minute, true
            );
            timePickerDialog.show();
        });
    }
}
