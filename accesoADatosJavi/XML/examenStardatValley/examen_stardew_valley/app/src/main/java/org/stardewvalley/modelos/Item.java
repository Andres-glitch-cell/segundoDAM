package org.stardewvalley.modelos;

import javax.xml.bind.annotation.XmlElement;


public class Item {

    private String nombre;
    private int cantidad;

    public Item() {
    }

    public Item(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "cantidad")
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
