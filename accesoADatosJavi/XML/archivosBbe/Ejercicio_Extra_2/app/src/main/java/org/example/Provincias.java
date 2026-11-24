package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que la etiqueta principal del XML es <provincias>
@XmlRootElement(name = "provincias")
public class Provincias {

    private ArrayList<Provincia> provincias;

    // Constructor vacío necesario para JAXB
    public Provincias() {
    }

    // Indica que cada elemento de la lista se llama <provincia>
    @XmlElement(name = "provincia")
    public ArrayList<Provincia> getProvincias() {
        return provincias;
    }

    // Guarda la lista de provincias
    public void setProvincias(ArrayList<Provincia> provincias) {
        this.provincias = provincias;
    }
}