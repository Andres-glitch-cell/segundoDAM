package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Indica que la etiqueta principal del XML es <pacientes>
@XmlRootElement(name = "pacientes")
public class Pacientes {

    private ArrayList<Paciente> pacientes;

    // Constructor vacío necesario para JAXB
    public Pacientes() {
    }

    // Indica que cada elemento de la lista se llama <paciente>
    @XmlElement(name = "paciente")
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    // Guarda la lista de pacientes
    public void setPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
}