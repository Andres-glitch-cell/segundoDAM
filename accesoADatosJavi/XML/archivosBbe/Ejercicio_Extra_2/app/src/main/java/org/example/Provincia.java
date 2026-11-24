package org.example;

import javax.xml.bind.annotation.XmlElement;

public class Provincia {

    private String nombre;
    private int numPueblos;
    private double ingresos;

    // Constructor vacío necesario para JAXB
    public Provincia() {
    }

    // Relaciona nombre con la etiqueta <nombre>
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Relaciona numPueblos con la etiqueta <numPueblos>
    @XmlElement(name = "numPueblos")
    public int getNumPueblos() {
        return numPueblos;
    }

    public void setNumPueblos(int numPueblos) {
        this.numPueblos = numPueblos;
    }

    // Relaciona ingresos con la etiqueta <ingresos>
    @XmlElement(name = "ingresos")
    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }
}