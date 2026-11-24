package org.example;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cine")

public class PeliculaListWrapper {
    private List<Pelicula> peliculas = new ArrayList<>();

    public PeliculaListWrapper() {

    }

    public PeliculaListWrapper(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @XmlElement(name = "pelicula")
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    } 
}
