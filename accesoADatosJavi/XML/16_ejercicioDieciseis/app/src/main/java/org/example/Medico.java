package org.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Medico {

    private int numero;
    private String nombre;
    private String especialidad;
    private int experiencia;

    public Medico() { }

    public Medico(int numero, String nombre, String especialidad, int experiencia) {
        this.numero = numero;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.experiencia = experiencia;
    }

    @XmlAttribute(name = "numero")
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    @XmlElement(name = "nombre")
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement(name = "especialidad")
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    @XmlElement(name = "experiencia")
    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }
}
