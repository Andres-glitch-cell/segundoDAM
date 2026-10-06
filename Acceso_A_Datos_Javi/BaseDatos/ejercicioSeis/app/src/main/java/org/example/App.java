package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String urlConnection = "jdbc:mysql://3.85.42.109:3306/ejercicioCincoJavi";
        String user = "administrador";
        String password = "R00tR00t*12345";

        try {
            // [TODO]: Prueba conectarse a la Base de Datos
            Connection c = DriverManager.getConnection(urlConnection, user, password);

            System.out.println("\n\u001B[32mConexión realizada correctamente.\u001B[0m");

            // [TODO]: Creamos objeto para hacer sentencias SQL
            Statement s = c.createStatement();

            int opcion = 0;

            do {
                try {
                    int contador = 0;

                    System.out.println("\n1. Mostrar todos los alumnos");
                    System.out.println("2. Mostrar alumnos por curso");
                    System.out.println("3. Mostrar alumnos mayores de una edad");
                    System.out.println("4. Salir");
                    System.out.print("Elija una opción: ");
                    opcion = sc.nextInt();
                    switch (opcion) {
                        // [IMPORTANT]: Hacer SELECT * FROM alumnos:
                        case 1:
                            ResultSet hacerSelectTodosAlumnos = s
                                    .executeQuery("SELECT * FROM alumnos");
                            ResultSet hacerSelectPorEdadEspecifica = s
                                    .executeQuery("SELECT * FROM alumnos WHERE edad > ???");
                            while (hacerSelectTodosAlumnos.next()) {
                                int id = hacerSelectTodosAlumnos.getInt(1);
                                String nombre = hacerSelectTodosAlumnos.getString(2);
                                int edad = hacerSelectTodosAlumnos.getInt(3);
                                String curso = hacerSelectTodosAlumnos.getString(4);
                                System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                            }
                            s.close();
                            c.close();
                            break;

                        // [IMPORTANT]: Hacer SELECT * FROM alumnos por CURSOS (DAM/DAW):
                        case 2:
                            ResultSet hacerSelectPorCursoDAM = s
                                    .executeQuery("SELECT * FROM alumnos WHERE curso = \"Segundo de DAM\";");
                            ResultSet hacerSelectPorCursoDAW = s
                                    .executeQuery("SELECT * FROM alumnos WHERE curso = \"Segundo de DAW\";");
                            ResultSet hacerSelectPorCursoProfesorado = s
                                    .executeQuery("SELECT * FROM alumnos WHERE curso = \"Profesorado\";");

                            System.out.println("\n¿Hay 2 cursos y 1 profesorado cual quieres elegir?");
                            System.out.println("Cursos: \n- Segundo de DAM \n- Segundo de DAW \n - Profesorado");
                            String opcionUsuarioCurso = sc.nextLine();

                            if (opcionUsuarioCurso.toLowerCase() == "segundo de dam") {
                                while (hacerSelectPorCursoDAM.next()) {
                                    int id = hacerSelectPorCursoDAM.getInt(1);
                                    String nombre = hacerSelectPorCursoDAM.getString(2);
                                    int edad = hacerSelectPorCursoDAM.getInt(3);
                                    String curso = hacerSelectPorCursoDAM.getString(4);
                                    System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                                }
                                s.close();
                                c.close();

                            } else if (opcionUsuarioCurso.toLowerCase() == "segundo de daw") {
                                while (hacerSelectPorCursoDAW.next()) {
                                    int id = hacerSelectPorCursoDAW.getInt(1);
                                    String nombre = hacerSelectPorCursoDAW.getString(2);
                                    int edad = hacerSelectPorCursoDAW.getInt(3);
                                    String curso = hacerSelectPorCursoDAW.getString(4);
                                    System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                                }
                                s.close();
                                c.close();

                            } else if (opcionUsuarioCurso.toLowerCase() == "profesorado") {
                                while (hacerSelectPorCursoProfesorado.next()) {
                                    int id = hacerSelectPorCursoProfesorado.getInt(1);
                                    String nombre = hacerSelectPorCursoProfesorado.getString(2);
                                    int edad = hacerSelectPorCursoProfesorado.getInt(3);
                                    String curso = hacerSelectPorCursoProfesorado.getString(4);
                                    System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
                                }
                                s.close();
                                c.close();
                            }
                            break;
                        // [IMPORTANT]: POR ACABAR !
                        // case 3:

                        // break;
                        case 4:
                            System.out.println("Gracias por usar el menú.");
                            break;

                        default:
                            do {
                                System.out.println(
                                        "\n\u001B[31mError has elegido una opción incorrecta. (Has elegido la opción incorrecta "
                                                + contador + " vez)" + "\u001B[0m");
                                contador++;
                                if (contador >= 1) {
                                    System.out.println(
                                            "\n\u001B[31mError has elegido una opción incorrecta. (Has elegido la opción incorrecta "
                                                    + contador + " veces)" + "\u001B[0m");
                                    contador++;
                                }
                            } while (contador == 1);
                    }

                } catch (Exception e) {
                    System.err.println("Error producido: " + e);
                }
            } while (opcion != 4);

        } catch (Exception e) {
            System.err.println(e); // [IMPORTANT]: Mostramos error
            System.out.println("\n\u001B[31mError al conectar con la base de datos.\u001B[0m");
        }
    }
}
