
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://51.48.4.228:3306/basededatos_DAM";
        String usuario = "alumno";
        String clave = "123456";

        try (Connection conexion = DriverManager.getConnection(url, usuario, clave)) {

            Coche[] arrayCoches = obtenerCoches(conexion);

            if (arrayCoches.length == 0) {

                System.out.println("No hay coches en la base de datos.");

            } else {

                System.out.println("COCHES ENTRE 2005 Y 2010:");

                for (Coche coche : arrayCoches) {

                    if (coche.getAño() > 2005 && coche.getAño() < 2010) {

                        System.out.println(
                        coche.getMarca() + " "
                            + coche.getModelo()
                            + " - Año: " + coche.getAño()
                            + " - Precio: " + coche.getPrecio()
                            + " - Kilómetros: " + coche.getKilometros());
                    }
                }

                double totalPrecios = 0;

                Coche cocheCaro = arrayCoches[0];
                Coche cocheBarato = arrayCoches[0];

                for (Coche coche : arrayCoches) {

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

        String consulta = "SELECT id, marca, modelo, anyo, precio, kilometros FROM Coches";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                consulta,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

                ResultSet resultado = sentencia.executeQuery()) {

            int cantidadCoches = 0;

            while (resultado.next()) {
                cantidadCoches++;
            }

            Coche[] arrayCoches = new Coche[cantidadCoches];

            resultado.beforeFirst();

            for (int i = 0; i < arrayCoches.length; i++) {

                resultado.next();

                arrayCoches[i] = new Coche(
                        resultado.getInt("id"),
                        resultado.getString("marca"),
                        resultado.getString("modelo"),
                        resultado.getInt("anyo"),
                        resultado.getDouble("precio"),
                        resultado.getInt("kilometros"));
            }

            return arrayCoches;
        }
    }
}
