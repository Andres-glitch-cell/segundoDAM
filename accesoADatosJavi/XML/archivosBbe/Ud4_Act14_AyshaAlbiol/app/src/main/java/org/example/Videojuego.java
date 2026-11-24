
package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Videojuego {

    // Atributos que almacenan los datos del videojuego
    private String titulo;
    private String anio;
    private String desarrollador;
    private String genero;
    private ArrayList<String> plataformas;

    // Constructor vacío necesario para JAXB
    public Videojuego() {
    }

    // Constructor que permite crear un videojuego con todos sus datos
    public Videojuego(String titulo, String anio, String desarrollador,
            String genero, ArrayList<String> plataformas) {
        this.titulo = titulo;
        this.anio = anio;
        this.desarrollador = desarrollador;
        this.genero = genero;
        this.plataformas = plataformas;
    }

    // Devuelve el título del videojuego
    @XmlElement(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    // Asigna el título del videojuego
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Devuelve el año de lanzamiento
    @XmlElement(name = "anio")
    public String getAnio() {
        return anio;
    }

    // Asigna el año de lanzamiento
    public void setAnio(String anio) {
        this.anio = anio;
    }

    // Devuelve el nombre del desarrollador
    @XmlElement(name = "desarrollador")
    public String getDesarrollador() {
        return desarrollador;
    }

    // Asigna el desarrollador
    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    // Devuelve el género del videojuego
    @XmlElement(name = "genero")
    public String getGenero() {
        return genero;
    }

    // Asigna el género del videojuego
    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Indica que las plataformas están dentro de <plataformas>
    // Cada plataforma utiliza la etiqueta <plataforma>
    @XmlElementWrapper(name = "plataformas")
    @XmlElement(name = "plataforma")
    public ArrayList<String> getPlataformas() {
        return plataformas;
    }

    // Asigna la lista de plataformas del videojuego
    public void setPlataformas(ArrayList<String> plataformas) {
        this.plataformas = plataformas;
    }
}
