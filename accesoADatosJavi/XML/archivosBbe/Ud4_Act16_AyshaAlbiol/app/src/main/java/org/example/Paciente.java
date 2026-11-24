package org.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Paciente {

    private String dni;
    private String nombre;
    private String fechaNacimiento;
    private String ciudad;
    private String doctor;

    // Constructor vacío necesario para JAXB
    public Paciente() {
    }

    // Indica que el DNI es un atributo del XML
    // Ejemplo: <paciente DNI="11111111A">
    @XmlAttribute(name = "DNI")
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    // Relaciona la variable nombre con la etiqueta <nombre>
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Relaciona la fecha de nacimiento con su etiqueta XML
    @XmlElement(name = "fechaNacimiento")
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Relaciona la ciudad con la etiqueta <ciudad>
    @XmlElement(name = "ciudad")
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // Guarda el número del médico relacionado con el paciente
    @XmlElement(name = "doctor")
    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}