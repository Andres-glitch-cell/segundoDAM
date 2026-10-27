
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        // Creamos el Scanner para leer los datos que introduce el usuario.
        Scanner sc = new Scanner(System.in);

        // Datos necesarios para conectarse a la base de datos MySQL.
        String urlConnection = "jdbc:mysql://100.24.30.150:3306/ejercicioCincoJavi";
        String user = "admin";
        String password = "PON_AQUI_TU_CONTRASEÑA";

        try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {

            System.out.println("\n\u001B[32mConexión realizada correctamente.\u001B[0m");

            // Creamos el objeto Statement para ejecutar sentencias SQL.
            Statement stmnt = conexion.createStatement();

            // Variable que guarda la opción elegida por el usuario.
            int opcion = 0;

            // El menú se repite mientras el usuario no elija salir.
            do {
                try {
                    System.out.println("\n1. Mostrar todos los alumnos");
                    System.out.println("2. Mostrar alumnos por curso");
                    System.out.println("3. Mostrar alumnos mayores de una edad");
                    System.out.println("4. Salir");
                    System.out.print("Elija una opción: ");

                    opcion = sc.nextInt();

                    // Limpiamos el salto de línea que deja nextInt().
                    sc.nextLine();

                    switch (opcion) {

                        // Opción 1: mostrar todos los alumnos.
                        case 1:

                            // Ejecutamos una consulta para obtener todos los alumnos.
                            ResultSet hacerSelectTodosAlumnos = stmnt.executeQuery("SELECT * FROM alumnos");

                            // Recorremos los resultados de la consulta.
                            while (hacerSelectTodosAlumnos.next()) {

                                // Obtenemos los datos de cada alumno.
                                int id = hacerSelectTodosAlumnos.getInt(1);
                                String nombre = hacerSelectTodosAlumnos.getString(2);
                                int edad = hacerSelectTodosAlumnos.getInt(3);
                                String curso = hacerSelectTodosAlumnos.getString(4);

                                // Mostramos los datos por pantalla.
                                System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                            }

                            // Cerramos el ResultSet cuando terminamos de utilizarlo.
                            hacerSelectTodosAlumnos.close();

                            break;

                        // Opción 2: mostrar alumnos de un curso concreto.
                        case 2:
                            System.out.println("¿Qué curso quieres elegir?");
                            System.out.println("- Segundo de DAM");
                            System.out.println("- Segundo de DAW");
                            System.out.println("- Profesorado");

                            String opcionUsuarioCurso = sc.nextLine();

                            ResultSet hacerSelectPorCurso = stmnt
                                    .executeQuery("SELECT * FROM alumnos WHERE curso = '" + opcionUsuarioCurso + "'");

                            // Variable para comprobar si hay alumnos.
                            boolean hayAlumnos = false;

                            // Recorremos los alumnos encontrados.
                            while (hacerSelectPorCurso.next()) {

                                hayAlumnos = true;

                                int id = hacerSelectPorCurso.getInt(1);
                                String nombre = hacerSelectPorCurso.getString(2);
                                int edad = hacerSelectPorCurso.getInt(3);
                                String curso = hacerSelectPorCurso.getString(4);

                                // Mostramos los datos del alumno.
                                System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                            }

                            // Si no se encuentra ningún alumno, mostramos un mensaje.
                            if (!hayAlumnos) {
                                System.out.println("No hay alumnos registrados en ese curso.");
                            }

                            // Cerramos el resultado de la consulta.
                            hacerSelectPorCurso.close();

                            break;

                        // Opción 3: mostrar alumnos mayores de una edad.
                        case 3:

                            // Pedimos la edad a partir de la cual queremos buscar alumnos.
                            System.out.print("Introduce la edad: ");
                            int edadElegida = sc.nextInt();

                            // Limpiamos el salto de línea pendiente.
                            sc.nextLine();

                            // Consultamos los alumnos cuya edad sea superior a la indicada.
                            ResultSet hacerSelectPorEdadEspecifica = stmnt.executeQuery(
                                    "SELECT * FROM alumnos WHERE edad > " + edadElegida);

                            // Variable para comprobar si existen resultados.
                            boolean hayAlumnosMayores = false;

                            // Recorremos y mostramos los alumnos encontrados.
                            while (hacerSelectPorEdadEspecifica.next()) {

                                hayAlumnosMayores = true;

                                int id = hacerSelectPorEdadEspecifica.getInt(1);
                                String nombre = hacerSelectPorEdadEspecifica.getString(2);
                                int edad = hacerSelectPorEdadEspecifica.getInt(3);
                                String curso = hacerSelectPorEdadEspecifica.getString(4);

                                System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                            }

                            // Si no hay resultados, mostramos un mensaje.
                            if (!hayAlumnosMayores) {
                                System.out.println("No hay alumnos mayores de esa edad.");
                            }

                            // Cerramos el resultado de la consulta.
                            hacerSelectPorEdadEspecifica.close();

                            break;

                        // Opción 4: salir del programa.
                        case 4:

                            System.out.println("Gracias por usar el menú.");

                            break;

                        // Si el usuario introduce una opción que no existe.
                        default:

                            System.out.println("\n\u001B[31mError: has elegido una opción incorrecta.\u001B[0m");

                            break;
                    }

                } catch (Exception e) {

                    // Mostramos cualquier otro error producido durante la ejecución.
                    System.err.println("Error producido: " + e);
                }

            } while (opcion != 4);

            // Cerramos los recursos al terminar el menú.
            stmnt.close();
            conexion.close();
            sc.close();

        } catch (Exception e) {

            // Mostramos el error si no se puede establecer la conexión.
            System.err.println(e);
            System.out.println("\n\u001B[31mError al conectar con la base de datos.\u001B[0m");
        }
    }
}
