package org.example;

import javax.xml.bind.annotation.XmlElement;

public class Libro {

    // Atributos que almacenan los datos de cada manga
    private String titulo;
    private String autor;
    private String anio;
    private String precio;

    // Constructor vacío necesario para JAXB
    public Libro() {
    }

    // Constructor que permite crear un libro con todos sus datos
    public Libro(String titulo, String autor, String anio,
            String precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.precio = precio;
    }

    // Devuelve el título del libro
    @XmlElement(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    // Asigna el título del libro
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Devuelve el autor del libro
    @XmlElement(name = "autor")
    public String getAutor() {
        return autor;
    }

    // Asigna el autor del libro
    public void setAutor(String autor) {
        this.autor = autor;
    }

    // Devuelve el año de publicación
    @XmlElement(name = "anio")
    public String getAnio() {
        return anio;
    }

    // Asigna el año de publicación
    public void setAnio(String anio) {
        this.anio = anio;
    }

    // Devuelve el precio del libro
    @XmlElement(name = "precio")
    public String getPrecio() {
        return precio;
    }

    // Asigna el precio del libro
    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
