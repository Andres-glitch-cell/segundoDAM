package com.example.ud02_notificaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "canal_ies_joan_coromines";
    private static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Crear el canal de notificación (necesario para Android 8.0 o superior)
        crearCanalNotificacion();

        // 2. Configurar el botón para lanzar la notificación
        Button btnLanzar = findViewById(R.id.btnLanzarNotificacion);
        btnLanzar.setOnClickListener(v -> lanzarNotificacion());
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificaciones IES";
            String description = "Canal para notificaciones de la web del IES Joan Coromines";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void lanzarNotificacion() {
        // 3. Crear el Intent implícito para abrir la página web del IES Joan Coromines
        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iesjoancoromines.org"));

        // 4. Envolver el Intent en un PendingIntent para que se ejecute al pulsar la notificación
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intentWeb,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // 5. Construir la notificación con NotificationCompat
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle("IES Joan Coromines")
                .setContentText("Pulsa aquí para abrir la web oficial del centro")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Se borra automáticamente al pulsarla

        // 6. Lanzar la notificación usando NotificationManagerCompat
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        try {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            Toast.makeText(this, "¡Notificación enviada correctamente!", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "Error: Se requieren permisos de notificación", Toast.LENGTH_SHORT).show();
        }
    }
}
