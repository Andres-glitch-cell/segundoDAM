package com.example.ud02_calculadora;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private static final String OPERADORES = "+−×÷";

    private TextView tvExpresion;
    private TextView tvResultado;

    private String expresion = "";
    private boolean tieneResultado = false; // true justo después de pulsar "="

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvExpresion = findViewById(R.id.tvExpresion);
        tvResultado = findViewById(R.id.tvResultado);

        asignarListeners((ViewGroup) findViewById(R.id.teclado));
    }

    /** Recorre el teclado y asigna el mismo listener a todos los botones. */
    private void asignarListeners(ViewGroup grupo) {
        for (int i = 0; i < grupo.getChildCount(); i++) {
            View hijo = grupo.getChildAt(i);
            if (hijo instanceof ViewGroup) {
                asignarListeners((ViewGroup) hijo);
            } else if (hijo instanceof Button) {
                final String texto = ((Button) hijo).getText().toString();
                if (texto.equals("÷") || texto.equals("×") || texto.equals("−")
                        || texto.equals("+") || texto.equals("=")) {
                    ((Button) hijo).setTextColor(Color.parseColor("#FF6D00"));
                }
                hijo.setOnClickListener(v -> pulsar(texto));
            }
        }
    }

    private void pulsar(String tecla) {
        if (tecla.equals("C")) {
            expresion = "";
            tieneResultado = false;
        } else if (tecla.equals("⌫")) {
            if (tieneResultado) {
                expresion = "";
                tieneResultado = false;
            } else if (!expresion.isEmpty()) {
                expresion = expresion.substring(0, expresion.length() - 1);
            }
        } else if (tecla.equals("=")) {
            calcularFinal();
            return;
        } else if (tecla.equals(".")) {
            anadirPunto();
        } else if (tecla.equals("%")) {
            anadirPorcentaje();
        } else if (OPERADORES.contains(tecla)) {
            anadirOperador(tecla.charAt(0));
        } else {
            anadirDigito(tecla);
        }
        actualizarPantalla();
    }

    private void anadirDigito(String d) {
        if (tieneResultado) {
            expresion = "";
            tieneResultado = false;
        }
        expresion += d;
    }

    /** Devuelve el número que se está escribiendo (lo que hay tras el último operador). */
    private String numeroActual() {
        int i = expresion.length();
        while (i > 0) {
            char c = expresion.charAt(i - 1);
            if (OPERADORES.indexOf(c) >= 0 || c == '%') break;
            i--;
        }
        return expresion.substring(i);
    }

    private void anadirPunto() {
        if (tieneResultado) {
            expresion = "";
            tieneResultado = false;
        }
        String actual = numeroActual();
        if (actual.contains(".")) return;
        expresion += actual.isEmpty() ? "0." : ".";
    }

    private void anadirPorcentaje() {
        tieneResultado = false;
        if (expresion.isEmpty()) return;
        char ultimo = expresion.charAt(expresion.length() - 1);
        if (Character.isDigit(ultimo) || ultimo == '%') expresion += "%";
    }

    private void anadirOperador(char op) {
        tieneResultado = false;
        if (expresion.isEmpty()) {
            if (op == '−') expresion = "−"; // número negativo
            return;
        }
        if (expresion.equals("−")) return; // solo signo, no hacer nada
        char ultimo = expresion.charAt(expresion.length() - 1);
        if (OPERADORES.indexOf(ultimo) >= 0) {
            expresion = expresion.substring(0, expresion.length() - 1) + op; // reemplaza operador
        } else {
            expresion += op;
        }
    }

    private void calcularFinal() {
        Double valor = evaluar(expresion);
        if (valor == null) {
            if (!expresion.isEmpty()) tvResultado.setText("Error");
            return;
        }
        String texto = formatear(valor);
        tvExpresion.setText(expresion);
        tvResultado.setText(texto);
        expresion = texto;
        tieneResultado = true;
    }

    private void actualizarPantalla() {
        tvExpresion.setText(expresion);
        if (expresion.isEmpty()) {
            tvResultado.setText("0");
            return;
        }
        // Vista previa del resultado mientras se escribe
        Double valor = evaluar(expresion);
        if (valor != null) tvResultado.setText(formatear(valor));
    }

    private String formatear(double v) {
        return new BigDecimal(v)
                .setScale(10, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()
                .replace("-", "−");
    }

    /** Devuelve null si la expresión es inválida o incompleta. */
    private Double evaluar(String s) {
        try {
            double r = new Evaluador(s).evaluar();
            if (Double.isNaN(r) || Double.isInfinite(r)) return null;
            return r;
        } catch (Exception e) {
            return null;
        }
    }

    /** Parser descendente recursivo con precedencia: % > unario − > × ÷ > + − */
    private static class Evaluador {
        private final String s;
        private int pos = 0;

        Evaluador(String s) {
            this.s = s;
        }

        double evaluar() {
            double r = expresion();
            if (pos < s.length()) throw new IllegalArgumentException();
            return r;
        }

        private double expresion() {
            double v = termino();
            while (pos < s.length() && (s.charAt(pos) == '+' || s.charAt(pos) == '−')) {
                char op = s.charAt(pos++);
                double t = termino();
                v = (op == '+') ? v + t : v - t;
            }
            return v;
        }

        private double termino() {
            double v = factor();
            while (pos < s.length() && (s.charAt(pos) == '×' || s.charAt(pos) == '÷')) {
                char op = s.charAt(pos++);
                double f = factor();
                if (op == '÷' && f == 0.0) throw new ArithmeticException();
                v = (op == '×') ? v * f : v / f;
            }
            return v;
        }

        private double factor() {
            if (pos < s.length() && s.charAt(pos) == '−') {
                pos++;
                return -factor();
            }
            double v = numero();
            while (pos < s.length() && s.charAt(pos) == '%') {
                pos++;
                v /= 100.0;
            }
            return v;
        }

        private double numero() {
            int inicio = pos;
            while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) pos++;
            if (inicio == pos) throw new IllegalArgumentException();
            return Double.parseDouble(s.substring(inicio, pos));
        }
    }
}