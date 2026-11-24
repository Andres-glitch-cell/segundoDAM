package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que el elemento principal del XML se llama "biblioteca"
@XmlRootElement(name = "biblioteca")
public class Biblioteca {

    // Lista que almacena todos los libros de la biblioteca
    private ArrayList<Libro> libros;

    // Constructor vacío necesario para JAXB
    public Biblioteca() {
    }

    // Indica que cada libro de la lista se representa con la etiqueta <libro>
    @XmlElement(name = "libro")
    public ArrayList<Libro> getLibros() {
        return libros;
    }

    // Asigna la lista de libros a la biblioteca
    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }
}
