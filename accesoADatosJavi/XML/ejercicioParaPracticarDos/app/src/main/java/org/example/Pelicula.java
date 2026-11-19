package org.example;

import javax.xml.bind.annotation.XmlElement;

public class Pelicula {
    private String nombre;
    private String fechaEstreno;
    private int identificador;

    public Pelicula() {
    }

    public Pelicula(String nombre, String fechaEstreno, int identificador) {
        this.nombre = nombre;
        this.fechaEstreno = fechaEstreno;
        this.identificador = identificador;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "fechaEstreno")
    public String getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(String fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    @XmlElement(name = "identificador")
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }
}
