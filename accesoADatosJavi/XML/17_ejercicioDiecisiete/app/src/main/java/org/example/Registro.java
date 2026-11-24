package org.example;

import javax.xml.bind.annotation.XmlAttribute;

public class Registro {

    private int id;
    private String usuario;
    private String accion;
    private String fecha;

    public Registro() {
    }

    public Registro(int id, String usuario, String accion, String fecha) {
        this.id = id;
        this.usuario = usuario;
        this.accion = accion;
        this.fecha = fecha;
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
