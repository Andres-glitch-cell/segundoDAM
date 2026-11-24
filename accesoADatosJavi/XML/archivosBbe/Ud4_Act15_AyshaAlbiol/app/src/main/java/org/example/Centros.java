package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que la etiqueta principal del XML se llama <centros>
@XmlRootElement(name = "centros")
public class Centros {

    private ArrayList<Centro> centros;

    // Constructor vacío necesario para JAXB
    public Centros() {
    }

    // Indica que cada elemento de la lista se llama <centro>
    @XmlElement(name = "centro")
    public ArrayList<Centro> getCentros() {
        return centros;
    }

    // Guarda la lista de centros recibida
    public void setCentros(ArrayList<Centro> centros) {
        this.centros = centros;
    }
}