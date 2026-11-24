package org.example;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "nombre", "numPueblos", "ingresos" })
public class Provincia {

    private String nombre;
    private int numPueblos;
    private double ingresos;

    public Provincia() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumPueblos() {
        return numPueblos;
    }

    public void setNumPueblos(int numPueblos) {
        this.numPueblos = numPueblos;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }
}
