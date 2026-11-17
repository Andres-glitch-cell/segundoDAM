package com.example.ud02_miagenda;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Tarea> listaTareas;
    private ArrayAdapter<Tarea> adapter;
    private TextView tvContadorTareas, tvAgendaVacia;
    private ListView listViewTareas;

    private ActivityResultLauncher<Intent> addTaskLauncher;

    private static final String CHANNEL_ID = "canal_agenda_notificaciones";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        crearCanalNotificacion();
        pedirPermisoNotificaciones();

        listaTareas = new ArrayList<>();
        // Tareas de ejemplo iniciales
        listaTareas.add(new Tarea("Estudiar Examen", "Estudios", "15/11/2026", "10:00"));
        listaTareas.add(new Tarea("Reunión de Proyecto", "Trabajo", "16/11/2026", "12:30"));

        tvContadorTareas = findViewById(R.id.tvContadorTareas);
        tvAgendaVacia = findViewById(R.id.tvAgendaVacia);
        listViewTareas = findViewById(R.id.listViewTareas);
        Button btnNuevaTarea = findViewById(R.id.btnNuevaTarea);

        // Adaptador personalizado para ListView utilizando item_tarea.xml (Tarjetas con diseño moderno)
        adapter = new ArrayAdapter<Tarea>(this, R.layout.item_tarea, R.id.tvItemNombre, listaTareas) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Tarea tarea = getItem(position);
                if (tarea != null) {
                    TextView tvNombre = view.findViewById(R.id.tvItemNombre);
                    TextView tvCategoria = view.findViewById(R.id.tvItemCategoria);
                    TextView tvFechaHora = view.findViewById(R.id.tvItemFechaHora);

                    tvNombre.setText(tarea.getNombre());
                    tvCategoria.setText(tarea.getCategoria());
                    tvFechaHora.setText(tarea.getFecha() + " - " + tarea.getHora());
                }
                return view;
            }
        };

        listViewTareas.setAdapter(adapter);

        actualizarEstadoAgenda();

        // Configurar launcher para AddTaskActivity
        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Tarea nuevaTarea = result.getData().getSerializableExtra("NUEVA_TAREA", Tarea.class);
                        if (nuevaTarea != null) {
                            listaTareas.add(nuevaTarea);
                            actualizarEstadoAgenda();
                            Toast.makeText(this, "Tarea añadida correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnNuevaTarea.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(intent);
        });

        // Click en elemento del ListView -> Consultar detalles (AlertDialog)
        listViewTareas.setOnItemClickListener((parent, view, position, id) -> {
            Tarea tareaSeleccionada = listaTareas.get(position);
            mostrarDialogoConsulta(tareaSeleccionada, position);
        });
    }

    private void actualizarEstadoAgenda() {
        adapter.notifyDataSetChanged();
        int total = listaTareas.size();
        tvContadorTareas.setText("Tareas registradas: " + total);

        if (total == 0) {
            tvAgendaVacia.setVisibility(View.VISIBLE);
            listViewTareas.setVisibility(View.GONE);
        } else {
            tvAgendaVacia.setVisibility(View.GONE);
            listViewTareas.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarDialogoConsulta(Tarea tarea, int position) {
        String mensaje = "Nombre: " + tarea.getNombre() + "\n" +
                "Categoría: " + tarea.getCategoria() + "\n" +
                "Fecha: " + tarea.getFecha() + "\n" +
                "Hora: " + tarea.getHora();

        new AlertDialog.Builder(this)
                .setTitle("Detalles de la Tarea")
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", null)
                .setNeutralButton("Recordatorio", (dialog, which) -> enviarNotificacion(tarea))
                .setNegativeButton("Eliminar", (dialog, which) -> confirmarEliminacion(position))
                .show();
    }

    private void confirmarEliminacion(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    listaTareas.remove(position);
                    actualizarEstadoAgenda();
                    Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void enviarNotificacion(Tarea tarea) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_agenda)
                .setContentTitle("Recordatorio: " + tarea.getNombre())
                .setContentText("Fecha: " + tarea.getFecha() + " - Hora: " + tarea.getHora())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso de notificaciones no concedido", Toast.LENGTH_SHORT).show();
            return;
        }
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        Toast.makeText(this, "Notificación generada", Toast.LENGTH_SHORT).show();
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal Agenda";
            String description = "Notificaciones de tareas de Mi Agenda";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_cat_estudios) {
            contarTareasPorCategoria("Estudios");
            return true;
        } else if (id == R.id.menu_cat_trabajo) {
            contarTareasPorCategoria("Trabajo");
            return true;
        } else if (id == R.id.menu_cat_personal) {
            contarTareasPorContainer("Personal");
            return true;
        } else if (id == R.id.menu_cat_otros) {
            contarTareasPorCategoria("Otros");
            return true;
        } else if (id == R.id.menu_vaciar) {
            confirmarVaciarAgenda();
            return true;
        } else if (id == R.id.menu_acerca_de) {
            mostrarAcercaDe();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void contarTareasPorContainer(String categoria) {
        contarTareasPorCategoria(categoria);
    }

    private void contarTareasPorCategoria(String categoria) {
        int count = 0;
        for (Tarea t : listaTareas) {
            if (t.getCategoria().equalsIgnoreCase(categoria)) {
                count++;
            }
        }
        Toast.makeText(this, "Tareas de " + categoria + ": " + count, Toast.LENGTH_LONG).show();
    }

    private void confirmarVaciarAgenda() {
        new AlertDialog.Builder(this)
                .setTitle("Vaciar agenda")
                .setMessage("¿Estás seguro de que deseas eliminar todas las tareas registradas?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    listaTareas.clear();
                    actualizarEstadoAgenda();
                    Toast.makeText(this, "Agenda vaciada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void mostrarAcercaDe() {
        new AlertDialog.Builder(this)
                .setTitle("Acerca de Mi Agenda")
                .setMessage("Aplicación: Mi Agenda\nVersión: 1.0\nDesarrollador: 2º DAM (IES Joan Coromines)")
                .setPositiveButton("Aceptar", null)
                .show();
    }
}
