package org.example;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Centro {

    private String id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String codigoPostal;
    private ArrayList<Profesor> profesores;

    // Constructor vacío necesario para JAXB
    public Centro() {
    }

    // Indica que el id se obtiene de un atributo del XML
    // Por ejemplo: <centro id="1">
    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    // Guarda el id del centro
    public void setId(String id) {
        this.id = id;
    }

    // Relaciona la variable nombre con la etiqueta <nombre>
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    // Guarda el nombre del centro
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Relaciona la variable ciudad con la etiqueta <ciudad>
    @XmlElement(name = "ciudad")
    public String getCiudad() {
        return ciudad;
    }

    // Guarda la ciudad
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // Relaciona la variable direccion con la etiqueta <direccion>
    @XmlElement(name = "direccion")
    public String getDireccion() {
        return direccion;
    }

    // Guarda la dirección
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Relaciona la variable codigoPostal con la etiqueta <codigoPostal>
    @XmlElement(name = "codigoPostal")
    public String getCodigoPostal() {
        return codigoPostal;
    }

    // Guarda el código postal
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    // Indica que hay una etiqueta contenedora llamada <profesores>
    @XmlElementWrapper(name = "profesores")

    // Indica que cada profesor está dentro de una etiqueta <profesor>
    @XmlElement(name = "profesor")
    public ArrayList<Profesor> getProfesores() {
        return profesores;
    }

    // Guarda la lista de profesores del centro
    public void setProfesores(ArrayList<Profesor> profesores) {
        this.profesores = profesores;
    }
}