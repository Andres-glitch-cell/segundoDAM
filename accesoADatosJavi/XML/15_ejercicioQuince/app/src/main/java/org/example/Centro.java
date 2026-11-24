package org.example;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

public class Centro {
    private int id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String codigoPostal;
    private List<Profesor> profesores;

    public Centro() {
    }

    public Centro(int id, String nombre, String ciudad, String direccion, String codigoPostal,
            List<Profesor> profesores) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.profesores = profesores;
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @XmlElementWrapper(name = "profesores")
    @XmlElement(name = "profesor")
    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }
}
