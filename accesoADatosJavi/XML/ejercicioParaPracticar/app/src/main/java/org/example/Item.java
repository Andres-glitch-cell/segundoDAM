package org.example;

import javax.xml.bind.annotation.XmlElement;

public class Item {

    private String nombre;
    private double precio;

    public Item() {
    }

    public Item(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "precio")
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + " " + precio;
    }
}
