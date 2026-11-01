package com.example.ud02_edittext;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private Button btnSubmit;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvOutput = findViewById(R.id.tvOutput);

        btnSubmit.setOnClickListener(v -> {
            String texto = etInput.getText().toString().trim();
            if (texto.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, escribe algo", Toast.LENGTH_SHORT).show();
            } else {
                tvOutput.setText("Texto ingresado: " + texto);
            }
        });
    }
}
