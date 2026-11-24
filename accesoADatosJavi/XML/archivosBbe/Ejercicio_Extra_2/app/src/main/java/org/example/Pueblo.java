package org.example;

import javax.xml.bind.annotation.XmlElement;

public class Pueblo {

    private String nombre;
    private int numHabitantes;
    private String gentilicio;
    private String provincia;

    // Constructor vacío necesario para JAXB
    public Pueblo() {
    }

    // Relaciona nombre con la etiqueta <nombre>
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Relaciona numHabitantes con la etiqueta <numHabitantes>
    @XmlElement(name = "numHabitantes")
    public int getNumHabitantes() {
        return numHabitantes;
    }

    public void setNumHabitantes(int numHabitantes) {
        this.numHabitantes = numHabitantes;
    }

    // Relaciona gentilicio con la etiqueta <gentilicio>
    @XmlElement(name = "gentilicio")
    public String getGentilicio() {
        return gentilicio;
    }

    public void setGentilicio(String gentilicio) {
        this.gentilicio = gentilicio;
    }

    // Relaciona provincia con la etiqueta <provincia>
    @XmlElement(name = "provincia")
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}