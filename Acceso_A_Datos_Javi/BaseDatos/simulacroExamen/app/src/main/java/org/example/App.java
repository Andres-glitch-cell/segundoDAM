package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        // Datos necesarios para conectarnos a la base de datos MySQL.
        String url = "jdbc:mysql://54.242.112.251:3306/ejercicioCincoJavi";
        String user = "admin_completo";
        String password = "R00tR00t*12345";

        try (Connection conexion = DriverManager.getConnection(url, user, password);
                Scanner sc = new Scanner(System.in)) {

            System.out.println("Conexión realizada correctamente.");

            // Creamos las tablas necesarias si todavía no existen.
            crearTablas(conexion);

            int opcion = 0;

            // El menú se repite hasta que el usuario elija la opción 12.
            while (opcion != 12) {

                System.out.println("\n--- MENÚ DE ALUMNOS Y NOTAS ---");
                System.out.println("1. Insertar nuevo alumno");
                System.out.println("2. Listar todos los alumnos");
                System.out.println("3. Registrar una nota");
                System.out.println("4. Mostrar todas las notas de un alumno");
                System.out.println("5. Calcular la nota media de un alumno");
                System.out.println("6. Calcular la nota media de cada asignatura");
                System.out.println("7. Mostrar nota máxima y mínima de una asignatura");
                System.out.println("8. Calcular la edad media de los alumnos de un curso");
                System.out.println("9. Mostrar aprobados y suspensos de una asignatura");
                System.out.println("10. Alumnos con media superior a la media global");
                System.out.println("11. Eliminar un alumno por ID");
                System.out.println("12. Salir");
                System.out.print("Elige una opción: ");

                // Guardamos el número de la opción seleccionada.
                opcion = leerEntero(sc      );

                try {
                    // Ejecutamos la operación correspondiente a la opción elegida.
                    switch (opcion) {

                        case 1:
                            insertarAlumno(conexion, sc);
                            break;

                        case 2:
                            listarAlumnos(conexion);
                            break;

                        case 3:
                            registrarNota(conexion, sc);
                            break;

                        case 4:
                            mostrarNotasAlumno(conexion, sc);
                            break;

                        case 5:
                            mediaAlumno(conexion, sc);
                            break;

                        case 6:
                            mediaAsignaturas(conexion);
                            break;

                        case 7:
                            maximaYMinima(conexion, sc);
                            break;

                        case 8:
                            edadMediaCurso(conexion, sc);
                            break;

                        case 9:
                            aprobadosSuspensos(conexion, sc);
                            break;

                        case 10:
                            alumnosSobreMediaGlobal(conexion);
                            break;

                        case 11:
                            eliminarAlumno(conexion, sc );
                            break;

                        case 12:
                            System.out.println("Programa finalizado.");
                            break;

                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }

                } catch (SQLException e) {
                    // Mostramos el error si falla alguna operación con la base de datos.
                    System.out.println("Error al ejecutar la operación: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            // Mostramos el error si falla la conexión o la creación de las tablas.
            System.out.println("No se pudo conectar con la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    // Creamos las tablas de alumnos y notas relacionadas mediante el ID.
    private static void crearTablas(Connection c) throws SQLException {

        String sqlAlumnos = "CREATE TABLE IF NOT EXISTS alumnos ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nombre VARCHAR(100) NOT NULL, "
                + "edad INT NOT NULL, "
                + "curso VARCHAR(50) NOT NULL)";

        String sqlNotas = "CREATE TABLE IF NOT EXISTS notas ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "alumno_id INT NOT NULL, "
                + "asignatura VARCHAR(100) NOT NULL, "
                + "nota DECIMAL(4,2) NOT NULL, "
                + "FOREIGN KEY (alumno_id) REFERENCES alumnos(id) "
                + "ON DELETE CASCADE)";

        // Statement permite ejecutar las instrucciones SQL que crean las tablas.
        try (Statement s = c.createStatement()) {
            s.executeUpdate(sqlAlumnos);
            s.executeUpdate(sqlNotas);
        }
    }

    // Insertamos un alumno nuevo; MySQL genera automáticamente su ID.
    private static void insertarAlumno(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Edad: ");
        int edad = leerEntero(teclado);

        System.out.print("Curso: ");
        String curso = teclado.nextLine();

        // Preparamos el INSERT para guardar los datos del alumno.
        String sql = "INSERT INTO alumnos (nombre, edad, curso) VALUES (?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            // Asignamos los datos a los parámetros de la consulta.
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.setString(3, curso);

            // executeUpdate() ejecuta el INSERT y guarda el alumno.
            ps.executeUpdate();

            System.out.println("Alumno añadido correctamente.");
        }
    }

    // Consultamos y mostramos todos los alumnos registrados.
    private static void listarAlumnos(Connection c) throws SQLException {

        String sql = "SELECT * FROM alumnos ORDER BY id";

        // ResultSet guarda las filas obtenidas al ejecutar el SELECT.
        try (Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql)) {

            boolean hayAlumnos = false;

            // rs.next() avanza por las filas mientras queden alumnos.
            while (rs.next()) {
                hayAlumnos = true;

                // Obtenemos los datos del alumno de la fila actual.
                System.out.println(
                        rs.getInt("id") + ", "
                                + rs.getString("nombre") + ", "
                                + rs.getInt("edad") + " años, curso: "
                                + rs.getString("curso"));
            }

            if (!hayAlumnos) {
                System.out.println("No hay alumnos registrados.");
            }
        }
    }

    // Registramos una nota asociada al ID de un alumno y a una asignatura.
    private static void registrarNota(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("ID del alumno: ");
        int alumnoId = leerEntero(teclado);

        System.out.print("Asignatura: ");
        String asignatura = teclado.nextLine();

        System.out.print("Nota (0-10): ");
        double nota = leerDouble(teclado);

        // Comprobamos que la nota esté dentro del rango permitido.
        if (nota < 0 || nota > 10) {
            System.out.println("La nota debe estar entre 0 y 10.");
            return;
        }

        // Preparamos el INSERT para guardar la nota del alumno.
        String sql = "INSERT INTO notas (alumno_id, asignatura, nota) VALUES (?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            // Asignamos el alumno, la asignatura y la nota.
            ps.setInt(1, alumnoId);
            ps.setString(2, asignatura);
            ps.setDouble(3, nota);

            // executeUpdate() ejecuta el INSERT y registra la nota.
            ps.executeUpdate();

            System.out.println("Nota registrada correctamente.");
        }
    }

    // Mostramos todas las notas y asignaturas de un alumno concreto.
    private static void mostrarNotasAlumno(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("ID del alumno: ");
        int id = leerEntero(teclado);

        String sql = "SELECT asignatura, nota FROM notas "
                + "WHERE alumno_id = ? ORDER BY asignatura";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            // Indicamos el ID del alumno que queremos consultar.
            ps.setInt(1, id);

            // executeQuery() ejecuta el SELECT y devuelve las notas en un ResultSet.
            try (ResultSet rs = ps.executeQuery()) {

                boolean hayNotas = false;

                // Recorremos las notas que ha obtenido la consulta.
                while (rs.next()) {
                    hayNotas = true;

                    System.out.println(rs.getString("asignatura")
                            + ": " + rs.getDouble("nota"));
                }

                if (!hayNotas) {
                    System.out.println("Este alumno no tiene notas registradas.");
                }
            }
        }
    }

    // Calculamos la media de todas las notas de un alumno.
    private static void mediaAlumno(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("ID del alumno: ");
        int id = leerEntero(teclado);

        String sql = "SELECT AVG(nota) AS media FROM notas WHERE alumno_id = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            // ResultSet contiene el resultado del cálculo de la media.
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();

                // Obtenemos la media calculada por AVG().
                double media = rs.getDouble("media");

                if (rs.wasNull()) {
                    System.out.println("El alumno no tiene notas registradas.");
                } else {
                    System.out.printf("Nota media: %.2f%n", media);
                }
            }
        }
    }

    // Calculamos la nota media de cada asignatura.
    private static void mediaAsignaturas(Connection c) throws SQLException {

        String sql = "SELECT asignatura, AVG(nota) AS media "
                + "FROM notas GROUP BY asignatura ORDER BY asignatura";

        // GROUP BY agrupa las notas por asignatura y AVG() calcula su media.
        try (Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql)) {

            // Recorremos los resultados para mostrar la media de cada asignatura.
            while (rs.next()) {
                System.out.printf("%s: %.2f%n",
                        rs.getString("asignatura"),
                        rs.getDouble("media"));
            }
        }
    }

    // Buscamos la nota máxima y mínima de una asignatura e indicamos los alumnos.
    private static void maximaYMinima(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("Asignatura: ");
        String asignatura = teclado.nextLine();

        // JOIN relaciona las notas con los alumnos para obtener sus nombres.
        String sql = "SELECT a.nombre, n.nota FROM notas n "
                + "JOIN alumnos a ON n.alumno_id = a.id "
                + "WHERE n.asignatura = ? ORDER BY n.nota DESC";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, asignatura);

            // ResultSet contiene las notas y los nombres de los alumnos.
            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    System.out.println("No hay notas para esa asignatura.");
                    return;
                }

                // La primera nota es la máxima porque los resultados están ordenados.
                double maxima = rs.getDouble("nota");
                String nombreMax = rs.getString("nombre");

                // Inicializamos la mínima con la primera nota obtenida.
                double minima = maxima;
                String nombreMin = nombreMax;

                // Recorremos las demás notas para encontrar la mínima.
                while (rs.next()) {
                    double notaActual = rs.getDouble("nota");

                    if (notaActual < minima) {
                        minima = notaActual;
                        nombreMin = rs.getString("nombre");
                    }
                }

                System.out.println("Nota máxima: " + maxima
                        + " - Alumno: " + nombreMax);
                System.out.println("Nota mínima: " + minima
                        + " - Alumno: " + nombreMin);
            }
        }
    }

    // Calculamos la edad media de los alumnos de un curso.
    private static void edadMediaCurso(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("Curso: ");
        String curso = teclado.nextLine();

        String sql = "SELECT AVG(edad) AS media FROM alumnos WHERE curso = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, curso);

            // executeQuery() devuelve el resultado del cálculo de la media.
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();

                // Obtenemos la edad media del curso indicado.
                double media = rs.getDouble("media");

                if (rs.wasNull()) {
                    System.out.println("No hay alumnos en ese curso.");
                } else {
                    System.out.printf("Edad media: %.2f años%n", media);
                }
            }
        }
    }

    // Contamos los aprobados (nota >= 5) y los suspensos (nota < 5).
    private static void aprobadosSuspensos(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("Asignatura: ");
        String asignatura = teclado.nextLine();

        // CASE cuenta las notas que cumplen cada condición.
        String sql = "SELECT "
                + "SUM(CASE WHEN nota >= 5 THEN 1 ELSE 0 END) AS aprobados, "
                + "SUM(CASE WHEN nota < 5 THEN 1 ELSE 0 END) AS suspensos "
                + "FROM notas WHERE asignatura = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, asignatura);

            // ResultSet contiene el número de aprobados y suspensos.
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();

                int aprobados = rs.getInt("aprobados");
                int suspensos = rs.getInt("suspensos");

                System.out.println("Aprobados: " + aprobados);
                System.out.println("Suspensos: " + suspensos);
            }
        }
    }

    // Mostramos los alumnos cuya media supera la media global de todas las notas.
    private static void alumnosSobreMediaGlobal(Connection c)
            throws SQLException {

        String sql = "SELECT a.id, a.nombre, AVG(n.nota) AS media "
                + "FROM alumnos a JOIN notas n ON a.id = n.alumno_id "
                + "GROUP BY a.id, a.nombre "
                + "HAVING AVG(n.nota) > (SELECT AVG(nota) FROM notas)";

        // La consulta calcula la media de cada alumno y la compara con la global.
        try (Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql)) {

            boolean hayAlumnos = false;

            // Recorremos los alumnos que superan la media global.
            while (rs.next()) {
                hayAlumnos = true;

                System.out.printf("%d, %s - Media: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("media"));
            }

            if (!hayAlumnos) {
                System.out.println("Ningún alumno supera la media global.");
            }
        }
    }

    // Eliminamos el alumno indicado por su ID y también sus notas relacionadas.
    private static void eliminarAlumno(Connection c, Scanner teclado)
            throws SQLException {

        System.out.print("ID del alumno que quieres eliminar: ");
        int id = leerEntero(teclado);

        String sql = "DELETE FROM alumnos WHERE id = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            // Indicamos el ID del alumno que queremos eliminar.
            ps.setInt(1, id);

            // executeUpdate() ejecuta el DELETE y devuelve las filas eliminadas.
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No existe ningún alumno con ese ID.");
            }
        }
    }

    // Leemos un número entero y consumimos el salto de línea pendiente.
    private static int leerEntero(Scanner teclado) {
        int numero = teclado.nextInt();
        teclado.nextLine();
        return numero;
    }

    // Leemos una nota decimal y permitimos utilizar coma o punto decimal.
    private static double leerDouble(Scanner teclado) {
        return Double.parseDouble(teclado.nextLine().replace(',', '.'));
    }
}
