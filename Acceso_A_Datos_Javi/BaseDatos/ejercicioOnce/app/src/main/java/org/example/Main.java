package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Datos para conectar con la base de datos
        String url = "jdbc:mysql://51.48.4.228:3306/basededatos_DAM";
        String usuario = "alumno";
        String password = "123456";

        // Conectamos con MySQL
        try (Connection con = DriverManager.getConnection(url, usuario, password)) {

            // Obtenemos todos los videojuegos y los guardamos en una lista
            List<Videojuego> videojuegos = obtenerVideojuegos(con);

            // Comprobamos si hay videojuegos
            if (videojuegos.isEmpty()) {
                System.out.println("No hay videojuegos en la base de datos.");
            } else {

                // 1. Mostrar videojuegos de Xbox con valoración >= 8 y precio < 30
                System.out.println("VIDEOJUEGOS DE XBOX CON VALORACIÓN >= 8 Y PRECIO < 30:");

                for (Videojuego v : videojuegos) {

                    if (v.getPlataforma().equalsIgnoreCase("Xbox")
                            && v.getValoracion() >= 8
                            && v.getPrecio() < 30) {

                        System.out.println("Título: " + v.getTitulo());
                        System.out.println("Plataforma: " + v.getPlataforma());
                        System.out.println("Valoración: " + v.getValoracion());
                        System.out.println();
                    }
                }

                // 2. Contar cuántos videojuegos hay de cada plataforma
                System.out.println("NÚMERO DE VIDEOJUEGOS POR PLATAFORMA:");

                // Lista para guardar las plataformas que ya hemos contado
                List<String> plataformasContadas = new ArrayList<>();

                // Recorremos todos los videojuegos
                for (Videojuego v : videojuegos) {

                    // Solo contamos la plataforma si todavía no la hemos contado
                    if (!plataformasContadas.contains(v.getPlataforma())) {

                        int contador = 0;

                        // Contamos los videojuegos que tienen esa plataforma
                        for (Videojuego otro : videojuegos) {

                            if (otro.getPlataforma().equals(v.getPlataforma())) {
                                contador++;
                            }
                        }

                        // Mostramos el resultado
                        System.out.println(v.getPlataforma() + ": " + contador);

                        // Guardamos la plataforma para no contarla otra vez
                        plataformasContadas.add(v.getPlataforma());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar o consultar la base de datos.");
            e.printStackTrace();
        }
    }

    // Método que obtiene los videojuegos y los guarda en una lista de objetos
    public static List<Videojuego> obtenerVideojuegos(Connection con)
            throws SQLException {

        List<Videojuego> videojuegos = new ArrayList<>();

        // Consulta para obtener todos los videojuegos
        String sql = "SELECT id, titulo, plataforma, genero, anyo, precio, valoracion "
                + "FROM Videojuegos";

        // Ejecutamos la consulta y obtenemos el ResultSet
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos el ResultSet fila a fila
            while (rs.next()) {

                // Creamos un objeto Videojuego con los datos de la fila
                Videojuego v = new Videojuego(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("plataforma"),
                        rs.getString("genero"),
                        rs.getInt("anyo"),
                        rs.getDouble("precio"),
                        rs.getDouble("valoracion"));

                // Añadimos el objeto a la lista
                videojuegos.add(v);
            }
        }

        // Devolvemos la lista completa
        return videojuegos;
    }
}
