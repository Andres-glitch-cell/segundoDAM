package org.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Pelicula {

    // Datos de la película
    private String titulo;
    private String director;
    private String anio;
    private String genero;
    private String duracion;
    private int clasificacion;

    // Constructor vacío necesario para JAXB
    public Pelicula() {
    }

    // Constructor con todos los datos
    public Pelicula(String titulo, String director, String anio,
            String genero, String duracion, int clasificacion) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
    }

    // Devuelve y asigna el título
    @XmlElement(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Devuelve y asigna el director
    @XmlElement(name = "director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    // Devuelve y asigna el año
    @XmlElement(name = "anio")
    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    // Devuelve y asigna el género
    @XmlElement(name = "genero")
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Devuelve y asigna la duración en minutos
    @XmlElement(name = "duracion")
    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    // Lee el atributo clasificacion del XML
    @XmlAttribute(name = "clasificacion")
    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }
}
