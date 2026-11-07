
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

            String crearTabla = "CREATE TABLE IF NOT EXISTS Series ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "titulo VARCHAR(100), "
                    + "genero VARCHAR(50), "
                    + "num_temporadas INT, "
                    + "finalizada BOOLEAN"
                    + ")";

            try (Statement sentencia = conexion.createStatement()) {
                sentencia.executeUpdate(crearTabla);
            }

            int opcion = 0;

            while (opcion != 7) {

                System.out.println("\n========== MENÚ DE SERIES ==========");
                System.out.println("1. Insertar una nueva serie");
                System.out.println("2. Listar todas las series");
                System.out.println("3. Buscar series por género");
                System.out.println("4. Mostrar series en emisión");
                System.out.println("5. Actualizar número de temporadas");
                System.out.println("6. Eliminar una serie por id");
                System.out.println("7. Salir");
                System.out.print("Selecciona una opción: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine());

                    switch (opcion) {

                        case 1: {

                            // Pedimos los datos de la nueva serie.
                            System.out.print("Introduce el título: ");
                            String tituloNuevo = sc.nextLine();

                            System.out.print("Introduce el género: ");
                            String generoNuevo = sc.nextLine();

                            System.out.print("Número de temporadas: ");
                            int temporadasNuevas = Integer.parseInt(sc.nextLine());

                            System.out.print("¿Está finalizada? (true/false): ");
                            boolean serieTerminada = Boolean.parseBoolean(sc.nextLine());

                            String insertar = "INSERT INTO Series "
                                    + "(titulo, genero, num_temporadas, finalizada) "
                                    + "VALUES (?, ?, ?, ?)";

                            try (PreparedStatement insertarSerie = conexion.prepareStatement(insertar)) {

                                insertarSerie.setString(1, tituloNuevo);
                                insertarSerie.setString(2, generoNuevo);
                                insertarSerie.setInt(3, temporadasNuevas);
                                insertarSerie.setBoolean(4, serieTerminada);

                                insertarSerie.executeUpdate();

                                System.out.println("Serie añadida correctamente.");
                            }

                            break;
                        }

                        case 2: {

                            String consulta = "SELECT * FROM Series";

                            try (PreparedStatement buscarTodas = conexion.prepareStatement(consulta);
                                    ResultSet resultados = buscarTodas.executeQuery()) {

                                System.out.println("\n--- LISTADO DE SERIES ---");

                                mostrarSeries(resultados);
                            }

                            break;
                        }

                        case 3: {

                            System.out.print("Introduce el género que quieres buscar: ");
                            String generoElegido = sc.nextLine();

                            String consultaGenero = "SELECT * FROM Series WHERE genero = ?";

                            try (PreparedStatement buscarGenero = conexion.prepareStatement(consultaGenero)) {

                                buscarGenero.setString(1, generoElegido);

                                try (ResultSet resultados = buscarGenero.executeQuery()) {

                                    System.out.println("\n--- SERIES DEL GÉNERO " + generoElegido + " ---");

                                    mostrarSeries(resultados);
                                }
                            }

                            break;
                        }

                        case 4: {

                            String consultaEmision = "SELECT * FROM Series WHERE finalizada = false";

                            try (PreparedStatement buscarEmision = conexion.prepareStatement(consultaEmision);
                                    ResultSet resultados = buscarEmision.executeQuery()) {

                                System.out.println("\n--- SERIES EN EMISIÓN ---");

                                mostrarSeries(resultados);
                            }

                            break;
                        }

                        case 5: {

                            System.out.print("Introduce el id de la serie: ");
                            int idSerie = Integer.parseInt(sc.nextLine());

                            System.out.print("Introduce el nuevo número de temporadas: ");
                            int nuevasTemporadas = Integer.parseInt(sc.nextLine());

                            String actualizar = "UPDATE Series SET num_temporadas = ? WHERE id = ?";

                            try (PreparedStatement modificarSerie = conexion.prepareStatement(actualizar)) {

                                modificarSerie.setInt(1, nuevasTemporadas);
                                modificarSerie.setInt(2, idSerie);

                                int filasModificadas = modificarSerie.executeUpdate();

                                if (filasModificadas > 0) {
                                    System.out.println("Temporadas actualizadas correctamente.");
                                } else {
                                    System.out.println("No existe ninguna serie con ese id.");
                                }
                            }

                            break;
                        }

                        case 6: {

                            System.out.print("Introduce el id de la serie que quieres eliminar: ");
                            int idEliminar = Integer.parseInt(sc.nextLine());

                            String eliminar = "DELETE FROM Series WHERE id = ?";

                            try (PreparedStatement borrarSerie = conexion.prepareStatement(eliminar)) {

                                borrarSerie.setInt(1, idEliminar);

                                int filasBorradas = borrarSerie.executeUpdate();

                                if (filasBorradas > 0) {
                                    System.out.println("Serie eliminada correctamente.");
                                } else {
                                    System.out.println("No se ha encontrado ninguna serie con ese id.");
                                }
                            }

                            break;
                        }

                        case 7:

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

    public static void mostrarSeries(ResultSet datos) throws Exception {

        boolean haySeries = false;

        while (datos.next()) {

            int identificador = datos.getInt("id");
            String tituloSerie = datos.getString("titulo");
            String generoSerie = datos.getString("genero");
            int temporadas = datos.getInt("num_temporadas");
            boolean finalizada = datos.getBoolean("finalizada");

            System.out.println("ID: " + identificador
                    + " | Título: " + tituloSerie
                    + " | Género: " + generoSerie
                    + " | Temporadas: " + temporadas
                    + " | Finalizada: " + finalizada);

            haySeries = true;
        }

        if (!haySeries) {
            System.out.println("No se han encontrado series.");
        }
    }
}
