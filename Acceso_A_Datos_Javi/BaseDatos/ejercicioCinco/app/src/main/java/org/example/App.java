package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {

        String urlConnection = "jdbc:mysql://3.85.42.109:3306/ejercicioCincoJavi";
        String user = "administrador";
        String password = "R00tR00t*12345";

        try {
            // [TODO]: Prueba conectarse a la Base de Datos
            Connection c = DriverManager.getConnection(urlConnection, user, password);

            // [TODO]: Creamos objeto para hacer sentencias SQL
            Statement s = c.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM alumnos");

            // [LOG]: Recorremos mientras haya algo en la siguiente linea y mostramos
            // [IMPORTANT]: El rs contiene el contenido de la Sentencia SQL.
            while (rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                int edad = rs.getInt(3);
                String curso = rs.getString(4);

                System.out.println(id + ", " + nombre + ", " + edad + ", " + curso);
            }

            s.close();
            c.close();

        } catch (Exception e) {
            // [INFO]: Mostramos error si ocurren algunos
            System.err.println(e);
        }
    }
}
