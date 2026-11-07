package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        String url = "jdbc:mysql://35.153.139.129:3306/ejercicioCincoJavi";
        String usuario = "administrador";
        String clave = "R00tR00t*12345";

        try (Connection conexionBD = DriverManager.getConnection(url, usuario, clave)) {

            System.out.println("\n\u001B[32mConectado correctamente a la base de datos.\u001B[0m");

            int seleccion;

            do {
                System.out.println("\n========== MENÚ DE ALUMNOS ==========");
                System.out.println("1. Mostrar todos los alumnos");
                System.out.println("2. Mostrar alumnos por curso");
                System.out.println("3. Mostrar alumnos mayores de una edad");
                System.out.println("4. Salir");
                System.out.print("Selecciona una opción: ");

                try {
                    seleccion = Integer.parseInt(teclado.nextLine());

                    switch (seleccion) {

                        case 1:

                            Statement consulta = conexionBD.createStatement();
                            ResultSet resultado = consulta.executeQuery("SELECT * FROM alumnos");
                            System.out.println("\n--- LISTADO DE ALUMNOS ---");

                            mostrarResultados(resultado);
                            resultado.close();
                            consulta.close();

                            break;

                        case 2:

                            System.out.print("Introduce el nombre del curso: ");
                            String cursoElegido = teclado.nextLine();

                            String sqlCurso = "SELECT * FROM alumnos WHERE curso = ?";

                            PreparedStatement buscarCurso = conexionBD.prepareStatement(sqlCurso);

                            buscarCurso.setString(1, cursoElegido);

                            ResultSet alumnosCurso = buscarCurso.executeQuery();

                            System.out.println("\n--- ALUMNOS DEL CURSO ---");

                            if (!alumnosCurso.isBeforeFirst()) {
                                System.out.println("No se han encontrado alumnos en ese curso.");
                            } else {
                                mostrarResultados(alumnosCurso);
                            }

                            alumnosCurso.close();
                            buscarCurso.close();

                            break;

                        case 3:
                            System.out.print("Indica una edad: ");
                            int edadMinima = Integer.parseInt(teclado.nextLine());

                            String sqlEdad = "SELECT * FROM alumnos WHERE edad > ?";

                            PreparedStatement buscarEdad = conexionBD.prepareStatement(sqlEdad);

                            buscarEdad.setInt(1, edadMinima);

                            ResultSet alumnosEdad = buscarEdad.executeQuery();

                            System.out.println("\n--- ALUMNOS MAYORES DE " + edadMinima + " AÑOS ---");

                            if (!alumnosEdad.isBeforeFirst()) {
                                System.out.println("No hay alumnos que cumplan esa condición.");
                            } else {
                                mostrarResultados(alumnosEdad);
                            }

                            alumnosEdad.close();
                            buscarEdad.close();

                            break;

                        case 4:

                            System.out.println("\nSaliendo del programa. ¡Hasta pronto!");

                            break;

                        default:

                            System.out.println("\n\u001B[31mOpción no válida. Prueba otra vez.\u001B[0m");

                            break;
                    }

                } catch (NumberFormatException e) {

                    System.out.println("\nDebes introducir un número válido.");
                    seleccion = 0;
                }

            } while (seleccion != 4);

        } catch (Exception e) {

            System.err.println("No se pudo conectar con la base de datos.");
            System.err.println("Detalles del error: " + e.getMessage());

        }

        teclado.close();
    }

    // !! Método encargado de mostrar los datos obtenidos de la consulta.
    public static void mostrarResultados(ResultSet datos) throws Exception {

        while (datos.next()) {

            int identificador = datos.getInt("id");
            String nombreAlumno = datos.getString("nombre");
            int edadAlumno = datos.getInt("edad");
            String cursoAlumno = datos.getString("curso");

            System.out.println("ID: " + identificador + " | Nombre: " + nombreAlumno + " | Edad: " + edadAlumno
                    + " | Curso: " + cursoAlumno);
        }
    }
}
