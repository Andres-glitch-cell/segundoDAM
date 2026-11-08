
package org.example;

public class Coche {

    private final int id;
    private final String marca;
    private final String modelo;
    private final int año;
    private final double precio;
    private final int kilometros;

    public Coche(int id, String marca, String modelo, int año, double precio, int kilometros) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
        this.kilometros = kilometros;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAño() {
        return año;
    }

    public double getPrecio() {
        return precio;
    }

    public int getKilometros() {
        return kilometros;
    }
}
