package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Profesor {

    private String nombre;
    private String departamento;
    private ArrayList<String> asignaturas;

    // Constructor vacío necesario para que JAXB pueda crear objetos
    public Profesor() {
    }

    // Indica que este atributo se corresponde con la etiqueta <nombre> del XML
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    // Guarda el nombre recibido en la variable nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Indica que este atributo se corresponde con la etiqueta <departamento>
    @XmlElement(name = "departamento")
    public String getDepartamento() {
        return departamento;
    }

    // Guarda el departamento recibido
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    // Indica que existe una etiqueta contenedora llamada <asignaturas>
    @XmlElementWrapper(name = "asignaturas")

    // Indica que cada elemento de la lista se llama <asignatura>
    @XmlElement(name = "asignatura")
    public ArrayList<String> getAsignaturas() {
        return asignaturas;
    }

    // Guarda la lista de asignaturas recibida
    public void setAsignaturas(ArrayList<String> asignaturas) {
        this.asignaturas = asignaturas;
    }
}