package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que la etiqueta principal del XML es <medicos>
@XmlRootElement(name = "medicos")
public class Medicos {

    private ArrayList<Medico> medicos;

    // Constructor vacío necesario para JAXB
    public Medicos() {
    }

    // Indica que cada elemento de la lista se llama <medico>
    @XmlElement(name = "medico")
    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    // Guarda la lista de médicos
    public void setMedicos(ArrayList<Medico> medicos) {
        this.medicos = medicos;
    }
}