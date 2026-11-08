
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

        String url = System.getenv("DB_URL");
        String usuario = System.getenv("DB_USER");
        String clave = System.getenv("DB_PASSWORD");

        if (url == null || url.isBlank()
                || usuario == null || usuario.isBlank()
                || clave == null || clave.isBlank()) {
            System.err.println("Configura las variables DB_URL, DB_USER y DB_PASSWORD.");
            return;
        }

        try (Connection conexion = DriverManager.getConnection(url, usuario, clave)) {

            Coche[] arrayCoches = obtenerCoches(conexion);

            if (arrayCoches.length == 0) {

                System.out.println("No hay coches en la base de datos.");

            } else {

                System.out.println("COCHES ENTRE 2005 Y 2010:");

                int cochesEnRango = 0;
                for (int i = 0; i < arrayCoches.length; i++) {
                    Coche coche = arrayCoches[i];

                    if (coche.getAño() >= 2005 && coche.getAño() <= 2010) {
                        cochesEnRango++;

                        System.out.println(
                                coche.getMarca() + " "
                                        + coche.getModelo()
                                        + " - Año: " + coche.getAño()
                                        + " - Precio: " + coche.getPrecio()
                                        + " - Kilómetros: " + coche.getKilometros());
                    }
                }

                if (cochesEnRango == 0) {
                    System.out.println("No hay coches entre 2005 y 2010.");
                }

                double totalPrecios = 0;

                Coche cocheCaro = arrayCoches[0];
                Coche cocheBarato = arrayCoches[0];

                for (int i = 0; i < arrayCoches.length; i++) {
                    Coche coche = arrayCoches[i];

                    totalPrecios += coche.getPrecio();

                    if (coche.getPrecio() > cocheCaro.getPrecio()) {
                        cocheCaro = coche;
                    }

                    if (coche.getPrecio() < cocheBarato.getPrecio()) {
                        cocheBarato = coche;
                    }
                }

                double mediaPrecios = totalPrecios / arrayCoches.length;

                System.out.println();
                System.out.println("PRECIO MEDIO DE LOS COCHES: "
                        + mediaPrecios + " euros");

                System.out.println();
                System.out.println("COCHE MÁS CARO:");
                System.out.println("Marca: " + cocheCaro.getMarca());
                System.out.println("Modelo: " + cocheCaro.getModelo());
                System.out.println("Precio: " + cocheCaro.getPrecio() + " euros");

                System.out.println();
                System.out.println("COCHE MÁS BARATO:");
                System.out.println("Marca: " + cocheBarato.getMarca());
                System.out.println("Modelo: " + cocheBarato.getModelo());
                System.out.println("Precio: " + cocheBarato.getPrecio() + " euros");
            }

        } catch (SQLException e) {

            System.err.println("Error al conectar o consultar la base de datos: " + e.getMessage());
        }
    }

    public static Coche[] obtenerCoches(Connection conexion) throws SQLException {

        String consulta = "SELECT id, marca, modelo, anyo, precio, kilometros FROM Coches ORDER BY id";
        List<Coche> coches = new ArrayList<>();

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                coches.add(new Coche(
                        resultado.getInt("id"),
                        resultado.getString("marca"),
                        resultado.getString("modelo"),
                        resultado.getInt("anyo"),
                        resultado.getDouble("precio"),
                        resultado.getInt("kilometros")));
            }
        }

        return coches.toArray(new Coche[0]);
    }
}
