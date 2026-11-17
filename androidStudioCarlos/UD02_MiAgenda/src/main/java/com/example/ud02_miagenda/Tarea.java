package com.example.ud02_miagenda;

import java.io.Serializable;

public class Tarea implements Serializable {
    private String nombre;
    private String categoria;
    private String fecha;
    private String hora;

    public Tarea(String nombre, String categoria, String fecha, String hora) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return nombre + " - " + categoria + " (" + fecha + " " + hora + ")";
    }
}
