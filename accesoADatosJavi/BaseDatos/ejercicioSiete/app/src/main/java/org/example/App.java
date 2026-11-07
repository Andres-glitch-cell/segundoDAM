
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String url = "jdbc:mysql://35.153.139.129:3306/ejercicioCincoJavi";
        String usuario = "administrador";
        String clave = "R00tR00t*12345";

        Scanner sc = new Scanner(System.in);

        try (Connection conexion = DriverManager.getConnection(url, usuario, clave)) {

            System.out.println("Conexión realizada correctamente.");

            String sqlTabla = "CREATE TABLE IF NOT EXISTS Animales (" + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nombre VARCHAR(100), " + "especie VARCHAR(100), " + "edad INT" + ")";

            try (Statement sentencia = conexion.createStatement()) {
                sentencia.executeUpdate(sqlTabla);
            }

            int opcion = 0;

            while (opcion != 3) {

                System.out.println("\n========== MENÚ DE ANIMALES ==========");
                System.out.println("1. Añadir animal");
                System.out.println("2. Mostrar todos los animales");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine());

                    switch (opcion) {

                        case 1:

                            System.out.print("Nombre del animal: ");
                            String nombreNuevo = sc.nextLine();

                            System.out.print("Especie del animal: ");
                            String especieNueva = sc.nextLine();

                            System.out.print("Edad del animal: ");
                            int edadNueva = Integer.parseInt(sc.nextLine());

                            String insertar = "INSERT INTO Animales (nombre, especie, edad) VALUES (?, ?, ?)";

                            try (PreparedStatement insertarAnimal = conexion.prepareStatement(insertar)) {

                                insertarAnimal.setString(1, nombreNuevo);
                                insertarAnimal.setString(2, especieNueva);
                                insertarAnimal.setInt(3, edadNueva);

                                insertarAnimal.executeUpdate();

                                System.out.println("Animal guardado correctamente.");
                            }

                            break;

                        case 2:

                            String consulta = "SELECT * FROM Animales";

                            try (PreparedStatement buscarAnimales = conexion.prepareStatement(consulta);
                                    ResultSet resultados = buscarAnimales.executeQuery()) {

                                System.out.println("\n--- LISTADO DE ANIMALES ---");

                                boolean hayAnimales = false;

                                while (resultados.next()) {

                                    int codigo = resultados.getInt("id");
                                    String nombre = resultados.getString("nombre");
                                    String especie = resultados.getString("especie");
                                    int edad = resultados.getInt("edad");

                                    System.out.println("ID: " + codigo
                                            + " | Nombre: " + nombre
                                            + " | Especie: " + especie
                                            + " | Edad: " + edad);

                                    hayAnimales = true;
                                }

                                if (!hayAnimales) {
                                    System.out.println("Todavía no hay animales registrados.");
                                }
                            }

                            break;

                        case 3:

                            System.out.println("Programa finalizado. ¡Hasta pronto!");
                            break;

                        default:

                            System.out.println("Opción no válida. Inténtalo de nuevo.");
                            break;
                    }

                } catch (NumberFormatException e) {

                    System.out.println("Debes introducir un número válido.");
                }
            }

        } catch (Exception e) {

            System.out.println("Error al conectar o trabajar con la base de datos.");
            System.err.println("Detalles: " + e.getMessage());

        } finally {

            sc.close();
        }
    }
}
