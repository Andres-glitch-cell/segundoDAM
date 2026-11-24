package org.example;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que la etiqueta raíz del XML es <peliculas>
@XmlRootElement(name = "peliculas")
public class Peliculas {

    // Lista que contiene todas las películas
    private ArrayList<Pelicula> peliculas;

    // Constructor vacío necesario para JAXB
    public Peliculas() {
    }

    // Cada película de la lista utiliza la etiqueta <pelicula>
    @XmlElement(name = "pelicula")
    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    // Asigna la lista de películas
    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
