package com.example.ud02_calculadora;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView visorOperacion, visorResultado;
    private String entradaActual = "0";
    private String operadorSeleccionado = "";
    private double primerOperando = 0;
    private boolean esNuevoNumero = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visorOperacion = findViewById(R.id.txtOperacion);
        visorResultado = findViewById(R.id.txtResultado);

        // Busca automáticamente todos los botones del XML y los activa
        conectarTodosLosBotones(findViewById(android.R.id.content));
    }

    // Este método recorre tu diseño XML y les asigna la función del clic a los botones
    private void conectarTodosLosBotones(View vista) {
        if (vista instanceof Button) {
            vista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick(v); // Aquí ya se usa explícitamente el método
                }
            });
        } else if (vista instanceof ViewGroup) {
            ViewGroup grupo = (ViewGroup) vista;
            for (int i = 0; i < grupo.getChildCount(); i++) {
                conectarTodosLosBotones(grupo.getChildAt(i));
            }
        }
    }

    public void onButtonClick(View view) {
        if (!(view instanceof Button)) return;
        String botonPresionado = ((Button) view).getText().toString();

        switch (botonPresionado) {
            case "C":
                entradaActual = "0";
                operadorSeleccionado = "";
                primerOperando = 0;
                esNuevoNumero = true;
                visorOperacion.setText("");
                visorResultado.setText("0");
                break;

            case "⌫":
                if (entradaActual.length() > 1) {
                    entradaActual = entradaActual.substring(0, entradaActual.length() - 1);
                } else {
                    entradaActual = "0";
                }
                visorResultado.setText(entradaActual);
                break;

            case ".":
                if (esNuevoNumero) {
                    entradaActual = "0.";
                    esNuevoNumero = false;
                } else if (!entradaActual.contains(".")) {
                    entradaActual += ".";
                }
                visorResultado.setText(entradaActual);
                break;

            case "+":
            case "-":
            case "×":
            case "/":
                primerOperando = Double.parseDouble(entradaActual);
                operadorSeleccionado = botonPresionado;
                esNuevoNumero = true;
                visorOperacion.setText(entradaActual + " " + operadorSeleccionado);
                break;

            case "=":
                if (!operadorSeleccionado.isEmpty()) {
                    double segundoOperando = Double.parseDouble(entradaActual);
                    double resultado = 0;

                    switch (operadorSeleccionado) {
                        case "+": resultado = primerOperando + segundoOperando; break;
                        case "-": resultado = primerOperando - segundoOperando; break;
                        case "×": resultado = primerOperando * segundoOperando; break;
                        case "/":
                            if (segundoOperando == 0) {
                                visorResultado.setText("Error");
                                entradaActual = "0";
                                operadorSeleccionado = "";
                                esNuevoNumero = true;
                                return;
                            }
                            resultado = primerOperando / segundoOperando;
                            break;
                    }

                    visorOperacion.append(" " + entradaActual + " =");

                    if (resultado % 1 == 0) {
                        entradaActual = String.valueOf((int) resultado);
                    } else {
                        entradaActual = String.valueOf(resultado);
                    }

                    visorResultado.setText(entradaActual);
                    operadorSeleccionado = "";
                    esNuevoNumero = true;
                }
                break;

            default:
                if (esNuevoNumero || entradaActual.equals("0")) {
                    entradaActual = botonPresionado;
                    esNuevoNumero = false;
                } else {
                    entradaActual += botonPresionado;
                }
                visorResultado.setText(entradaActual);
                break;
        }
    }
}
