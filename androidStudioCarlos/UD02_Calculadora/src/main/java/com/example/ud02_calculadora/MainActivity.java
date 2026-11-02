package com.example.ud02_calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtOperacion, txtResultado;
    String numero = "0";
    String operador = "";
    double primero = 0;
    boolean nuevo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOperacion = findViewById(R.id.txtOperacion);
        txtResultado = findViewById(R.id.txtResultado);
    }

    public void onButtonClick(View v) {
        if (!(v instanceof Button)) return;
        String tecla = ((Button) v).getText().toString();

        switch (tecla) {
            case "C":
                numero = "0";
                operador = "";
                primero = 0;
                nuevo = true;
                txtOperacion.setText("");
                txtResultado.setText("");
                break;

            case "⌫":
                if (numero.length() > 1) {
                    numero = numero.substring(0, numero.length() - 1);
                } else {
                    numero = "0";
                }
                mostrar();
                break;

            case ".":
                if (nuevo) {
                    numero = "0.";
                    nuevo = false;
                } else if (!numero.contains(".")) {
                    numero += ".";
                }
                mostrar();
                break;

            case "+":
            case "-":
            case "×":
            case "/":
                try {
                    primero = Double.parseDouble(numero);
                } catch (NumberFormatException e) {
                    primero = 0;
                }
                operador = tecla;
                txtOperacion.setText(formato(primero) + " " + operador);
                txtResultado.setText("");
                nuevo = true;
                break;

            case "=":
                if (!operador.isEmpty() && !nuevo) {
                    double segundo;
                    try {
                        segundo = Double.parseDouble(numero);
                    } catch (NumberFormatException e) {
                        return;
                    }
                    double total = 0;
                    boolean error = false;

                    switch (operador) {
                        case "+":
                            total = primero + segundo;
                            break;
                        case "-":
                            total = primero - segundo;
                            break;
                        case "×":
                            total = primero * segundo;
                            break;
                        case "/":
                            if (segundo == 0) {
                                txtResultado.setText("Error");
                                error = true;
                            } else {
                                total = primero / segundo;
                            }
                            break;
                    }

                    if (!error) {
                        txtOperacion.setText(formato(primero) + " " + operador + " " + formato(segundo) + " =");
                        numero = formato(total);
                        txtResultado.setText(numero);
                    }

                    operador = "";
                    nuevo = true;
                }
                break;

            default: // dígitos 0-9
                if (nuevo) {
                    numero = tecla;
                    nuevo = false;
                } else {
                    if (numero.equals("0")) {
                        numero = tecla;
                    } else {
                        numero += tecla;
                    }
                }
                mostrar();
                break;
        }
    }

    private void mostrar() {
        if (operador.isEmpty()) {
            txtOperacion.setText(numero);
        } else {
            txtOperacion.setText(formato(primero) + " " + operador + " " + numero);
        }
    }

    private String formato(double n) {
        if (n == (long) n) {
            return String.valueOf((long) n);
        }
        return String.valueOf(n);
    }
}
