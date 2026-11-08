
package org.example;

// Clase que representa un videojuego
public class Videojuego {

    private final int id;
    private final String titulo;
    private final String plataforma;
    private final String genero;
    private final int anyo;
    private final double precio;
    private final double valoracion;

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
