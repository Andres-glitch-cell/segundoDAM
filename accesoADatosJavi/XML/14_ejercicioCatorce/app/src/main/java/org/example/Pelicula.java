package org.example;

import javax.xml.bind.annotation.XmlAttribute;

public class Pelicula {
    private int clasificacion;
    private String titulo;
    private String director;
    private int anio;
    private String genero;
    private int duracion;

    // Constructor vacío obligatorio para JAXB
    public Pelicula() {
    }

    public Pelicula(int clasificacion, String titulo, String director, int anio, String genero, int duracion) {
        this.clasificacion = clasificacion;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.genero = genero;
        this.duracion = duracion;
    }

    @XmlAttribute(name = "clasificacion")
    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
