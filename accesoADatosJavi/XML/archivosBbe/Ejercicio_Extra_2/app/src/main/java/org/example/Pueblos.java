package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que la etiqueta principal del XML es <pueblos>
@XmlRootElement(name = "pueblos")
public class Pueblos {

    private ArrayList<Pueblo> pueblos;

    // Constructor vacío necesario para JAXB
    public Pueblos() {
    }

    // Indica que cada elemento de la lista se llama <pueblo>
    @XmlElement(name = "pueblo")
    public ArrayList<Pueblo> getPueblos() {
        return pueblos;
    }

    // Guarda la lista de pueblos
    public void setPueblos(ArrayList<Pueblo> pueblos) {
        this.pueblos = pueblos;
    }
}