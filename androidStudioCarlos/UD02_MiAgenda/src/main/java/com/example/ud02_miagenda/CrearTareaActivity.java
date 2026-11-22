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

/**
 * ====================================================================
 * ACTIVIDAD SECUNDARIA: CREAR TAREA ACTIVITY
 * ====================================================================
 * Formulario para introducir los datos de una nueva tarea:
 * - Nombre de la tarea (EditText con validación de no estar vacío)
 * - Categoría (Selección mediante AlertDialog de opciones)
 * - Fecha (Selección mediante DatePickerDialog)
 * - Hora (Selección mediante TimePickerDialog)
 */
public class CrearTareaActivity extends AppCompatActivity {

    // --- 1. DECLARACIÓN DE COMPONENTES DE VISTA Y VARIABLES DESCRIPTIVAS ---
    private EditText editTextNombreTarea;
    private Button botonElegirCategoria;
    private Button botonElegirFecha;
    private Button botonElegirHora;
    private Button botonGuardarTarea;
    private Button botonCancelarFormulario;

    private String textoCategoriaElegida = "";
    private String textoFechaElegida = "";
    private String textoHoraElegida = "";

    private final String[] listaOpcionesCategorias = {"Estudios", "Trabajo", "Personal", "Otros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_tarea);

        // Ajuste de insets para diseño Edge-to-Edge combinando con padding lateral y vertical
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int sidePadding = (int) (24 * getResources().getDisplayMetrics().density);
            int topBottomPadding = (int) (24 * getResources().getDisplayMetrics().density);
            v.setPadding(sidePadding + systemBars.left, systemBars.top + topBottomPadding, sidePadding + systemBars.right, systemBars.bottom + topBottomPadding);
            return insets;
        });

        inicializarVistasFormulario();
        configurarEscuchadoresDeEventos();
    }

    // --- 2. INICIALIZACIÓN DE VISTAS ---
    private void inicializarVistasFormulario() {
        editTextNombreTarea = findViewById(R.id.etNombre);
        botonElegirCategoria = findViewById(R.id.btnSeleccionarCategoria);
        botonElegirFecha = findViewById(R.id.btnSeleccionarFecha);
        botonElegirHora = findViewById(R.id.btnSeleccionarHora);
        botonGuardarTarea = findViewById(R.id.btnGuardar);
        botonCancelarFormulario = findViewById(R.id.btnCancelar);
    }

    // --- 3. CONFIGURACIÓN DE ESCUCHADORES DE EVENTOS Y DIÁLOGOS ---
    private void configurarEscuchadoresDeEventos() {

        // A. Diálogo para seleccionar la categoría (AlertDialog con lista de opciones)
        botonElegirCategoria.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Selecciona una categoría")
                    .setItems(listaOpcionesCategorias, (dialog, indiceSeleccionado) -> {
                        textoCategoriaElegida = listaOpcionesCategorias[indiceSeleccionado];
                        botonElegirCategoria.setText(textoCategoriaElegida);
                    })
                    .show();
        });

        // B. Diálogo para seleccionar la fecha (DatePickerDialog nativo)
        botonElegirFecha.setOnClickListener(v -> {
            Calendar calendarioActual = Calendar.getInstance();
            int anioActual = calendarioActual.get(Calendar.YEAR);
            int mesActual = calendarioActual.get(Calendar.MONTH);
            int diaActual = calendarioActual.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog selectorFecha = new DatePickerDialog(this, (view, anio, mes, diaDelMes) -> {
                // Formatear la fecha elegida como DD/MM/AAAA
                textoFechaElegida = String.format("%02d/%02d/%d", diaDelMes, (mes + 1), anio);
                botonElegirFecha.setText(textoFechaElegida);
            }, anioActual, mesActual, diaActual);

            selectorFecha.show();
        });

        // C. Diálogo para seleccionar la hora (TimePickerDialog nativo)
        botonElegirHora.setOnClickListener(v -> {
            Calendar calendarioActual = Calendar.getInstance();
            int horaActual = calendarioActual.get(Calendar.HOUR_OF_DAY);
            int minutoActual = calendarioActual.get(Calendar.MINUTE);

            TimePickerDialog selectorHora = new TimePickerDialog(this, (view, horaElegida, minutoElegido) -> {
                // Formatear la hora elegida como HH:MM en formato 24 horas
                textoHoraElegida = String.format("%02d:%02d", horaElegida, minutoElegido);
                botonElegirHora.setText(textoHoraElegida);
            }, horaActual, minutoActual, true);

            selectorHora.show();
        });

        // D. Botón Guardar: Validación estricta y retorno de resultados a MainActivity
        botonGuardarTarea.setOnClickListener(v -> {
            String nombreIngresado = editTextNombreTarea.getText().toString().trim();

            // Validación 1: El nombre no puede estar vacío
            if (nombreIngresado.isEmpty()) {
                Toast.makeText(this, "El nombre de la tarea no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validación 2: Todos los campos obligatorios deben estar seleccionados
            if (textoCategoriaElegida.isEmpty() || textoFechaElegida.isEmpty() || textoHoraElegida.isEmpty()) {
                Toast.makeText(this, "Debe completar todos los campos obligatorios (*)", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear el objeto Tarea y devolverlo mediante un Intent de resultado
            Tarea nuevaTareaCreada = new Tarea(nombreIngresado, textoCategoriaElegida, textoFechaElegida, textoHoraElegida);
            Intent intentResultado = new Intent();
            intentResultado.putExtra("NUEVA_TAREA", nuevaTareaCreada);
            setResult(RESULT_OK, intentResultado);
            finish();
        });

        // E. Botón Cancelar: Regresar a la pantalla principal sin guardar
        botonCancelarFormulario.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
