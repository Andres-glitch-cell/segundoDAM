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

            // Guardamos la consulta SQL para crear la tabla Series si todavía no existe.
            String crearTabla = "CREATE TABLE IF NOT EXISTS Series ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "titulo VARCHAR(100), "
                    + "genero VARCHAR(50), "
                    + "num_temporadas INT, "
                    + "finalizada BOOLEAN"
                    + ")";

            // executeUpdate() ejecuta la instrucción SQL que crea la tabla.
            s.executeUpdate(crearTabla);

            // Scanner permite leer los datos que el usuario introduce por consola.
            Scanner teclado = new Scanner(System.in);

            int opcion = 0;

            // Repetimos el menú mientras el usuario no elija la opción 7.
            while (opcion != 7) {

                System.out.println();
                System.out.println("--- MENÚ ---");
                System.out.println("1. Insertar una nueva serie");
                System.out.println("2. Listar todas las series");
                System.out.println("3. Buscar series por género");
                System.out.println("4. Mostrar series en emisión");
                System.out.println("5. Actualizar número de temporadas de una serie");
                System.out.println("6. Eliminar una serie por id");
                System.out.println("7. Salir");
                System.out.print("Elige una opción: ");

                // Guardamos el número de la opción seleccionada.
                opcion = teclado.nextInt();

                // Ejecutamos las instrucciones correspondientes a la opción elegida.
                switch (opcion) {

                    case 1:
                        // Pedimos los datos de la serie que queremos guardar en la tabla.
                        System.out.print("Introduce el título: ");
                        String titulo = teclado.next();

                        System.out.print("Introduce el género: ");
                        String genero = teclado.next();

                        System.out.print("Introduce el número de temporadas: ");
                        int numTemporadas = teclado.nextInt();

                        System.out.print("¿La serie está finalizada? (true/false): ");
                        boolean finalizada = teclado.nextBoolean();

                        // Preparamos la consulta INSERT para guardar los datos de la serie.
                        // Los signos ? se sustituyen por los valores que introduzca el usuario.
                        String sqlInsertar = "INSERT INTO Series "
                                + "(titulo, genero, num_temporadas, finalizada) "
                                + "VALUES (?, ?, ?, ?)";

                        PreparedStatement psInsertar = c.prepareStatement(sqlInsertar);

                        // Asignamos los datos a los parámetros de la consulta, en el mismo orden.
                        psInsertar.setString(1, titulo);
                        psInsertar.setString(2, genero);
                        psInsertar.setInt(3, numTemporadas);
                        psInsertar.setBoolean(4, finalizada);

                        // executeUpdate() ejecuta el INSERT y guarda la serie en MySQL.
                        psInsertar.executeUpdate();

                        System.out.println("Serie insertada correctamente.");

                        // Cerramos el PreparedStatement cuando terminamos de insertar.
                        psInsertar.close();

                        break;

                    case 2:
                        // Guardamos la consulta SQL para obtener todas las series.
                        String sqlMostrar = "SELECT * FROM Series";

                        // Preparamos y ejecutamos el SELECT; ResultSet guarda las filas obtenidas.
                        PreparedStatement psMostrar = c.prepareStatement(sqlMostrar);
                        ResultSet rs = psMostrar.executeQuery();

                        // rs.next() avanza por las filas mientras queden series por mostrar.
                        while (rs.next()) {

                            // Obtenemos los datos de la serie de la fila actual.
                            int id = rs.getInt("id");
                            String tituloSerie = rs.getString("titulo");
                            String generoSerie = rs.getString("genero");
                            int temporadas = rs.getInt("num_temporadas");
                            boolean serieFinalizada = rs.getBoolean("finalizada");

                            // Mostramos los datos de la serie por consola.
                            System.out.println(id + ", " + tituloSerie + ", "
                                    + generoSerie + ", " + temporadas
                                    + " temporadas, Finalizada: " + serieFinalizada);
                        }

                        // Cerramos los resultados y el objeto que ejecutó la consulta.
                        rs.close();
                        psMostrar.close();

                        break;

                    case 3:
                        // Pedimos el género por el que queremos buscar las series.
                        System.out.print("Introduce el género: ");
                        String generoBuscar = teclado.next();

                        // Preparamos el SELECT para obtener las series del género indicado.
                        String sqlGenero = "SELECT * FROM Series WHERE genero = ?";
                        PreparedStatement psGenero = c.prepareStatement(sqlGenero);

                        // Sustituimos el signo ? por el género introducido por el usuario.
                        psGenero.setString(1, generoBuscar);

                        // executeQuery() ejecuta el SELECT y devuelve los resultados en un ResultSet.
                        ResultSet rsGenero = psGenero.executeQuery();

                        // Recorremos las filas devueltas por la consulta.
                        while (rsGenero.next()) {

                            // Obtenemos los datos de la serie de la fila actual.
                            int id = rsGenero.getInt("id");
                            String tituloGenero = rsGenero.getString("titulo");
                            String generoSerie = rsGenero.getString("genero");
                            int temporadasGenero = rsGenero.getInt("num_temporadas");
                            boolean finalizadaGenero = rsGenero.getBoolean("finalizada");

                            // Mostramos los datos de la serie encontrada.
                            System.out.println(id + ", " + tituloGenero + ", "
                                    + generoSerie + ", " + temporadasGenero
                                    + " temporadas, Finalizada: " + finalizadaGenero);
                        }

                        // Cerramos el ResultSet y el PreparedStatement de la búsqueda.
                        rsGenero.close();
                        psGenero.close();

                        break;

                    case 4:
                        // Guardamos el SELECT para obtener las series que no han finalizado.
                        String sqlEmision = "SELECT * FROM Series WHERE finalizada = false";

                        // Preparamos y ejecutamos la consulta; ResultSet almacena las filas obtenidas.
                        PreparedStatement psEmision = c.prepareStatement(sqlEmision);
                        ResultSet rsEmision = psEmision.executeQuery();

                        // Recorremos las series que todavía están en emisión.
                        while (rsEmision.next()) {

                            // Obtenemos los datos de la serie de la fila actual.
                            int id = rsEmision.getInt("id");
                            String tituloEmision = rsEmision.getString("titulo");
                            String generoEmision = rsEmision.getString("genero");
                            int temporadasEmision = rsEmision.getInt("num_temporadas");
                            boolean finalizadaEmision = rsEmision.getBoolean("finalizada");

                            // Mostramos los datos de la serie por consola.
                            System.out.println(id + ", " + tituloEmision + ", "
                                    + generoEmision + ", " + temporadasEmision
                                    + " temporadas, Finalizada: " + finalizadaEmision);
                        }

                        // Cerramos el ResultSet y el PreparedStatement utilizados.
                        rsEmision.close();
                        psEmision.close();

                        break;

                    case 5:
                        // Pedimos el id de la serie que queremos modificar.
                        System.out.print("Introduce el id de la serie: ");
                        int idActualizar = teclado.nextInt();

                        // Pedimos el nuevo número de temporadas.
                        System.out.print("Introduce el nuevo número de temporadas: ");
                        int nuevasTemporadas = teclado.nextInt();

                        // Preparamos la consulta UPDATE para modificar las temporadas de una serie.
                        String sqlActualizar = "UPDATE Series SET num_temporadas = ? WHERE id = ?";
                        PreparedStatement psActualizar = c.prepareStatement(sqlActualizar);

                        // Asignamos el nuevo número de temporadas y el id de la serie.
                        psActualizar.setInt(1, nuevasTemporadas);
                        psActualizar.setInt(2, idActualizar);

                        // executeUpdate() ejecuta el UPDATE y devuelve el número de filas modificadas.
                        int filasActualizadas = psActualizar.executeUpdate();

                        if (filasActualizadas > 0) {
                            System.out.println("Número de temporadas actualizado correctamente.");
                        } else {
                            System.out.println("No existe ninguna serie con ese id.");
                        }

                        // Cerramos el PreparedStatement después de actualizar los datos.
                        psActualizar.close();

                        break;

                    case 6:
                        // Pedimos el id de la serie que queremos eliminar.
                        System.out.print("Introduce el id de la serie: ");
                        int idEliminar = teclado.nextInt();

                        // Preparamos la consulta DELETE para eliminar la serie con el id indicado.
                        String sqlEliminar = "DELETE FROM Series WHERE id = ?";
                        PreparedStatement psEliminar = c.prepareStatement(sqlEliminar);

                        // Sustituimos el signo ? por el id introducido por el usuario.
                        psEliminar.setInt(1, idEliminar);

                        // executeUpdate() ejecuta el DELETE y devuelve el número de filas eliminadas.
                        int filasEliminadas = psEliminar.executeUpdate();

                        if (filasEliminadas > 0) {
                            System.out.println("Serie eliminada correctamente.");
                        } else {
                            System.out.println("No existe ninguna serie con ese id.");
                        }

                        // Cerramos el PreparedStatement después de eliminar la serie.
                        psEliminar.close();

                        break;

                    case 7:
                        // Mostramos el mensaje de salida; el menú terminará al comprobar la opción.
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
