package org.stardewvalley.modelos;

import javax.xml.bind.annotation.XmlElement;

public class Habitante {

    private String nombre;
    private String profesion;
    private int edad;
    private String ubicacion;
    private String regalo;

    public Habitante() {

    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "profeesion")
    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @XmlElement(name = "edad")
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @XmlElement(name = "ubicacion")
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @XmlElement(name = "regalo")
    public String getRegalo() {
        return regalo;
    }

    public void setRegalo(String regalo) {
        this.regalo = regalo;
    }
}
