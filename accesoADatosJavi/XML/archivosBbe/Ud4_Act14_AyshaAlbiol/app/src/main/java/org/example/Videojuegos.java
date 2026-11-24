package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que el elemento raíz del XML se llama <videojuegos>
@XmlRootElement(name = "videojuegos")
public class Videojuegos {

    // Lista que almacena todos los videojuegos
    private ArrayList<Videojuego> videojuegos;

    // Constructor vacío necesario para JAXB
    public Videojuegos() {
    }

    // Indica que cada videojuego se representa con la etiqueta <videojuego>
    @XmlElement(name = "videojuego")
    public ArrayList<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    // Asigna la lista de videojuegos
    public void setVideojuegos(ArrayList<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }
}
