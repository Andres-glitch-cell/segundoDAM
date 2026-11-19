package org.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Pueblo {

    private String provincia;
    private String nombre;
    private int numHabitantes;
    private String gentilicio;
    private String comunidad;

    public Pueblo() {
    }

    @XmlAttribute(name = "provincia")
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "numHabitantes")
    public int getNumHabitantes() {
        return numHabitantes;
    }

    public void setNumHabitantes(int numHabitantes) {
        this.numHabitantes = numHabitantes;
    }

    @XmlElement(name = "gentilicio")
    public String getGentilicio() {
        return gentilicio;
    }

    public void setGentilicio(String gentilicio) {
        this.gentilicio = gentilicio;
    }

    @XmlElement(name = "comunidad")
    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }
}
