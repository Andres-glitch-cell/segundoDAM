
package org.example;

// Clase que representa un videojuego
public class Videojuego {

    private int id;
    private String titulo;
    private String plataforma;
    private String genero;
    private int anyo;
    private double precio;
    private double valoracion;

    // Constructor para crear un videojuego con todos sus datos
    public Videojuego(int id, String titulo, String plataforma, String genero,
            int anyo, double precio, double valoracion) {
        this.id = id;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.genero = genero;
        this.anyo = anyo;
        this.precio = precio;
        this.valoracion = valoracion;
    }

    // Getters para obtener los datos del videojuego
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnyo() {
        return anyo;
    }

    public double getPrecio() {
        return precio;
    }

    public double getValoracion() {
        return valoracion;
    }
}
