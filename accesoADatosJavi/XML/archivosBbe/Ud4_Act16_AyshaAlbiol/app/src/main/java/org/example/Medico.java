package org.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Medico {

    private String numeroDoctor;
    private String nombre;
    private String especialidad;
    private int aniosExperiencia;

    // Constructor vacío necesario para JAXB
    public Medico() {
    }

    // Indica que numeroDoctor es un atributo del XML
    @XmlAttribute(name = "numeroDoctor")
    public String getNumeroDoctor() {
        return numeroDoctor;
    }

    public void setNumeroDoctor(String numeroDoctor) {
        this.numeroDoctor = numeroDoctor;
    }

    // Relaciona la variable nombre con la etiqueta <nombre>
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Relaciona la variable especialidad con la etiqueta <especialidad>
    @XmlElement(name = "especialidad")
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Relaciona los años de experiencia con su etiqueta XML
    @XmlElement(name = "aniosExperiencia")
    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }
}
