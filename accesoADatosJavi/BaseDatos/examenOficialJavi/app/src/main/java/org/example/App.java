package org.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        // [IMPORTANT]: Conexion
        String urlConnection = "jdbc:mysql://51.48.4.228:3306/basededatos_DAM";
        String user = "alumno";
        String password = "123456";
        Scanner sc = new Scanner(System.in);

        try {
            Connection conexion = DriverManager.getConnection(urlConnection, user, password);
            System.out.println("\nConexión realizada correctamente.");

            String sentenciaSQLBuscarUsuario = "SELECT * FROM EXAMEN_EMPLEADOS WHERE NOMBRE_USUARIO = ?";
            PreparedStatement preparedStatementSelect = conexion.prepareStatement(sentenciaSQLBuscarUsuario);

            String nombreUsuario = "";
            int opcion = 0;

            do {
                System.out.println("1. Preguntar nombre de usuario si existe");
                System.out.println("2. Insertar en la tabla EXAMEN_HISTORIAL");
                System.out.println("3. Segundo menu (Submenu)");
                System.out.print("Elige una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        // [IMPORTANT]: Ejercicio 1:
                        // [IMPORTANT]: Parte del SELECT del USUARIO que queremos.
                        sc.nextLine();
                        System.out.print("Introduce tu nombre de usuario: ");
                        nombreUsuario = sc.nextLine();
                        preparedStatementSelect.setString(1, nombreUsuario);
                        ResultSet resultadoSQL = preparedStatementSelect.executeQuery();
                        while (resultadoSQL.next()) {
                            String NOMBRE_USUARIO_SQL = resultadoSQL.getString("NOMBRE_USUARIO");
                            String NOMBRE_SQL = resultadoSQL.getString("NOMBRE");
                            String APELLIDOS_SQL = resultadoSQL.getString("APELLIDOS");
                            Float SALARIO_SQL = resultadoSQL.getFloat("SALARIO");

                            System.out.println("\n Características del nombre encontrado:"
                                    + "\n Nombre de usuario: " + NOMBRE_USUARIO_SQL
                                    + "\n Nombre: " + NOMBRE_SQL
                                    + "\n Apellidos: " + APELLIDOS_SQL
                                    + "\n Salario: " + SALARIO_SQL);
                            System.out.println("Usuario encontrado");
                        }

                        System.out.println("No existe un usuario existente de: " + nombreUsuario
                                + " en la base de datos, creando uno...");
                        System.out.println("Intrduzca su nombre: ");
                        String nombreRealUsuario = sc.nextLine();
                        System.out.println("Introduzca sus apellidos: ");
                        String apellidosUsuario = sc.nextLine();
                        System.out.println("Introduzca su salario: ");
                        Float salarioUsuario = sc.nextFloat();

                        // [IMPORTANT]: Parte donde INSERTAMOS EL USUARIO SI NO EXISTE
                        String sentenciaSQLInsertarUsuario = "INSERT INTO EXAMEN_EMPLEADOS (NOMBRE_USUARIO, NOMBRE, APELLIDOS, SALARIO) VALUES (?, ?, ?, ?);";
                        PreparedStatement preparedStatementInsert = conexion
                                .prepareStatement(sentenciaSQLInsertarUsuario);

                        preparedStatementInsert.setString(1, nombreUsuario);
                        preparedStatementInsert.setString(2, nombreRealUsuario);
                        preparedStatementInsert.setString(3, apellidosUsuario);
                        preparedStatementInsert.setFloat(4, salarioUsuario);

                        preparedStatementInsert.executeUpdate();
                        System.out.println("\nUsuario:" + nombreUsuario + "\nNombre: " + nombreRealUsuario
                                + "\nApellidos: "
                                + apellidosUsuario + "\nSalario:" + salarioUsuario + "\nAñadidos correctamente.");
                        break;

                    case 2:
                        // [IMPORTANT]: Ejercicio 2:
                        String sentenciaSQLInsertarFilaMasFecha = "INSERT INTO EXAMEN_HISTORIAL (NOMBRE_USUARIO, FECHA_CONEXION, IP_EQUIPO) VALUES (?, SYSDATE(), ?);";
                        PreparedStatement prepareStatementInsertarFila = conexion
                                .prepareStatement(sentenciaSQLInsertarFilaMasFecha);

                        prepareStatementInsertarFila.setString(1, nombreUsuario);
                        prepareStatementInsertarFila.setString(2, "111.676.676.67");

                        prepareStatementInsertarFila.executeUpdate();
                        preparedStatementSelect.close();
                        break;

                    case 3:
                        int opcionMenuDos = 0;
                        do {
                            System.out.println("\n\n1. Opción 1");
                            System.out.println("2. Opción 2");
                            System.out.println("3. Opción 3");
                            System.out.println("4. Opción 4");
                            System.out.println("5. Opción 5");
                            System.out.print("Elije una opcion: ");
                            opcionMenuDos = sc.nextInt();

                            switch (opcionMenuDos) {
                                case 1:
                                    String sentenciaObtenerRegistrosHistorialDeUsuario = "SELECT * FROM EXAMEN_HISTORIAL WHERE NOMBRE_USUARIO = ?";
                                    PreparedStatement prepatedStatementObtenerRegistros = conexion
                                            .prepareStatement(sentenciaObtenerRegistrosHistorialDeUsuario);

                                    prepatedStatementObtenerRegistros.setString(1, nombreUsuario);
                                    ResultSet resultadoSQLRegistrosTabla = prepatedStatementObtenerRegistros
                                            .executeQuery();
                                    while (resultadoSQLRegistrosTabla.next()) {
                                        int SQL_ID = resultadoSQLRegistrosTabla
                                                .getInt("ID");
                                        String SQL_NOMBRE_USUARIO = resultadoSQLRegistrosTabla
                                                .getString("NOMBRE_USUARIO");
                                        Date SQL_FECHA = resultadoSQLRegistrosTabla.getDate("FECHA_CONEXION");
                                        String SQL_IP_EQUIPO = resultadoSQLRegistrosTabla.getString("IP_EQUIPO");

                                        System.out.println("\n Características del usuario encontrado:"
                                                + "\n ID: " + SQL_ID
                                                + "\n Nombre: " + SQL_NOMBRE_USUARIO
                                                + "\n Fecha: " + SQL_FECHA
                                                + "\n IP Equipo: " + SQL_IP_EQUIPO);
                                    }
                                    break;

                                case 2:
                                    break;
                                default:
                                    break;
                            }

                        } while (opcionMenuDos != 5);
                        break;

                    default:

                }
            } while (opcion != 2);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
