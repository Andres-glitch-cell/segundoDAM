package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        // Guardamos los datos necesarios para conectarnos a la base de datos MySQL.
        String urlConnection = "jdbc:mysql://51.48.4.228:3306/basededatos_DAM";
        String user = "alumno";
        String password = "123456";

        try {
            // DriverManager establece la conexión y Connection la guarda en c.
            Connection c = DriverManager.getConnection(urlConnection, user, password);

            System.out.println("Conexión realizada correctamente.");

            // Statement permite ejecutar instrucciones SQL utilizando la conexión.
            Statement s = c.createStatement();

            // Guardamos la consulta SQL para crear la tabla Animales si todavía no existe.
            String crearTabla = "CREATE TABLE IF NOT EXISTS Animales (" + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nombre VARCHAR(100), " + "especie VARCHAR(100), " + "edad INT" + ")";

            // executeUpdate() ejecuta la instrucción SQL que crea la tabla.
            s.executeUpdate(crearTabla);

            // Scanner permite leer los datos que el usuario introduce por consola.
            Scanner teclado = new Scanner(System.in);

            int opcion = 0;

            // Repetimos el menú mientras el usuario no elija la opción 3.
            while (opcion != 3) {

                System.out.println();
                System.out.println("--- MENÚ ---");
                System.out.println("1. Añadir animal");
                System.out.println("2. Mostrar todos los animales");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");

                // Guardamos el número de la opción seleccionada.
                opcion = teclado.nextInt();

                // Ejecutamos las instrucciones correspondientes a la opción elegida.
                switch (opcion) {

                    case 1:
                        // Pedimos los datos del animal que queremos guardar en la tabla.
                        System.out.print("Introduce el nombre: ");
                        String nombre = teclado.next();

                        System.out.print("Introduce la especie: ");
                        String especie = teclado.next();

                        System.out.print("Introduce la edad: ");
                        int edad = teclado.nextInt();

                        // Preparamos la consulta INSERT para guardar los datos del animal.
                        // Los signos ? se sustituyen por los valores que introduzca el usuario.
                        String sentenciaSQL_Insert = "INSERT INTO Animales (nombre, especie, edad) VALUES (?, ?, ?)";
                        PreparedStatement ps = c.prepareStatement(sentenciaSQL_Insert);

                        // Asignamos los datos a los parámetros de la consulta, en el mismo orden.
                        ps.setString(1, nombre);
                        ps.setString(2, especie);
                        ps.setInt(3, edad);

                        // executeUpdate() ejecuta el INSERT y guarda el animal en MySQL.
                        ps.executeUpdate();

                        System.out.println("Animal añadido correctamente.");

                        // Cerramos el PreparedStatement cuando terminamos de insertar.
                        ps.close();

                        break;

                    case 2:
                        // Guardamos la consulta SQL para obtener todos los animales.
                        String sqlMostrar = "SELECT * FROM Animales";

                        // Preparamos y ejecutamos el SELECT; ResultSet guarda las filas obtenidas.
                        PreparedStatement psMostrar = c.prepareStatement(sqlMostrar);
                        ResultSet rs = psMostrar.executeQuery();

                        // rs.next() avanza por las filas mientras queden animales por mostrar.
                        while (rs.next()) {

                            // Obtenemos los datos del animal de la fila actual.
                            int id = rs.getInt("id");
                            String nombreAnimal = rs.getString("nombre");
                            String especieAnimal = rs.getString("especie");
                            int edadAnimal = rs.getInt("edad");

                            // Mostramos los datos del animal por consola.
                            System.out.println(id + ", " + nombreAnimal + ", " + especieAnimal + ", " + edadAnimal);
                        }

                        // Cerramos los resultados y el objeto que ejecutó la consulta.
                        rs.close();
                        psMostrar.close();

                        break;

                    case 3:
                        // Mostramos el mensaje de salida; el bucle terminará al comprobar la opción.
                        System.out.println("Programa finalizado.");
                        break;

                    default:
                        // Avisamos si el usuario introduce una opción que no aparece en el menú.
                        System.out.println("Opción no válida.");
                        break;
                }
            }

            // Cerramos el Scanner, el Statement y la conexión para liberar los recursos.
            teclado.close();
            s.close();
            c.close();

        } catch (Exception e) {
            // Mostramos un mensaje y el detalle del error si falla alguna operación.
            System.out.println("Error al conectar o trabajar con la base de datos.");
            System.err.println(e);
        }
    }
}
