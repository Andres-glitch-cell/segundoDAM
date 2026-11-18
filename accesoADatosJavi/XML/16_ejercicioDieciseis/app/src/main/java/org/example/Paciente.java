package org.example;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Paciente {

    private String dni;
    private String nombre;
    private String fechaNacimiento;
    private String ciudad;
    private int doctor; // número de doctor en medicos.xml

    public Paciente() { }

    public Paciente(String dni, String nombre, String fechaNacimiento, String ciudad, int doctor) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.doctor = doctor;
    }

    @XmlAttribute(name = "dni")
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    @XmlElement(name = "nombre")
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement(name = "fechaNacimiento")
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @XmlElement(name = "ciudad")
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    @XmlElement(name = "doctor")
    public int getDoctor() { return doctor; }
    public void setDoctor(int doctor) { this.doctor = doctor; }
}
