
package org.example;

// Clase que representa un coche
public class Coche {

    private int id;
    private String marca;
    private String modelo;
    private int anyo;
    private double precio;
    private int kilometros;

    // Constructor para crear un coche con todos sus datos
    public Coche(int id, String marca, String modelo, int anyo,
            double precio, int kilometros) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anyo = anyo;
        this.precio = precio;
        this.kilometros = kilometros;
    }

    // Getters para obtener los datos del coche
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnyo() {
        return anyo;
    }

    public double getPrecio() {
        return precio;
    }

    public int getKilometros() {
        return kilometros;
    }
}
