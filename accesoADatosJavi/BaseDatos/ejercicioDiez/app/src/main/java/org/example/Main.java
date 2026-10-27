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

            // Guardamos los coches de la base de datos en una lista
            List<Coche> coches = obtenerCoches(con);

            // Comprobamos que hay coches en la lista
            if (coches.isEmpty()) {
                System.out.println("No hay coches en la base de datos.");
            } else {

                // 1. Mostrar coches posteriores a 2005 y anteriores a 2010
                System.out.println("COCHES ENTRE 2005 Y 2010:");

                for (Coche c : coches) {
                    if (c.getAnyo() > 2005 && c.getAnyo() < 2010) {
                        System.out.println(
                                c.getMarca() + " " + c.getModelo()
                                        + " - Año: " + c.getAnyo()
                                        + " - Precio: " + c.getPrecio()
                                        + " - Kilómetros: " + c.getKilometros());
                    }
                }

                // Variables para calcular el precio medio y buscar extremos
                double sumaPrecios = 0;
                Coche masCaro = coches.get(0);
                Coche masBarato = coches.get(0);

                // Recorremos la lista para realizar los cálculos
                for (Coche c : coches) {

                    // Sumamos los precios
                    sumaPrecios = sumaPrecios + c.getPrecio();

                    // Buscamos el coche más caro
                    if (c.getPrecio() > masCaro.getPrecio()) {
                        masCaro = c;
                    }

                    // Buscamos el coche más barato
                    if (c.getPrecio() < masBarato.getPrecio()) {
                        masBarato = c;
                    }
                }

                // 2. Calculamos y mostramos el precio medio
                double precioMedio = sumaPrecios / coches.size();

                System.out.println();
                System.out.println("PRECIO MEDIO DE LOS COCHES: "
                        + precioMedio + " euros");

                // 3. Mostramos el coche más caro
                System.out.println();
                System.out.println("COCHE MÁS CARO:");
                System.out.println("Marca: " + masCaro.getMarca());
                System.out.println("Modelo: " + masCaro.getModelo());
                System.out.println("Precio: " + masCaro.getPrecio() + " euros");

                // Mostramos el coche más barato
                System.out.println();
                System.out.println("COCHE MÁS BARATO:");
                System.out.println("Marca: " + masBarato.getMarca());
                System.out.println("Modelo: " + masBarato.getModelo());
                System.out.println("Precio: " + masBarato.getPrecio() + " euros");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar o consultar la base de datos.");
            e.printStackTrace();
        }
    }

    // Método que obtiene los coches y los guarda en una lista de objetos Coche
    public static List<Coche> obtenerCoches(Connection con) throws SQLException {

        List<Coche> coches = new ArrayList<>();

        // Consulta para obtener todos los coches
        String sql = "SELECT id, marca, modelo, anyo, precio, kilometros FROM Coches";

        // Ejecutamos la consulta
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos el ResultSet fila a fila
            while (rs.next()) {

                // Creamos un objeto Coche con los datos de cada fila
                Coche c = new Coche(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("anyo"),
                        rs.getDouble("precio"),
                        rs.getInt("kilometros"));

                // Añadimos el objeto a la lista
                coches.add(c);
            }
        }

        // Devolvemos la lista de coches
        return coches;
    }
}
