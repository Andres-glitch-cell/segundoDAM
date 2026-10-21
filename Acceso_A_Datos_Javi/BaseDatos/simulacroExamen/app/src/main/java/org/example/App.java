
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    private static void crearTablasBaseDatos(Connection conexionBaseDatos) throws SQLException {

        String sentenciaCrearTablaAlumnos = "CREATE TABLE IF NOT EXISTS alumnos ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nombre VARCHAR(100) NOT NULL, "
                + "edad INT NOT NULL, "
                + "curso VARCHAR(50) NOT NULL)";

        String sentenciaCrearTablaNotas = "CREATE TABLE IF NOT EXISTS notas ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "alumno_id INT NOT NULL, "
                + "asignatura VARCHAR(100) NOT NULL, "
                + "nota DECIMAL(4,2) NOT NULL, "
                + "FOREIGN KEY (alumno_id) REFERENCES alumnos(id) "
                + "ON DELETE CASCADE)";

        try (Statement sentenciaSQL = conexionBaseDatos.createStatement()) {
            sentenciaSQL.executeUpdate(sentenciaCrearTablaAlumnos);
            sentenciaSQL.executeUpdate(sentenciaCrearTablaNotas);
        }
    }

    public static void main(String[] argumentosPrograma) {

        String direccionConexionBaseDatos = "jdbc:mysql://54.242.112.251:3306/ejercicioCincoJavi";
        String usuarioBaseDatos = "admin_completo";
        String contrasenaBaseDatos = "R00tR00t*12345";

        try (Connection conexionBaseDatos = DriverManager.getConnection(direccionConexionBaseDatos, usuarioBaseDatos,
                contrasenaBaseDatos)) {

            Scanner sc = new Scanner(System.in);

            System.out.println("Conexión realizada correctamente.");
            crearTablasBaseDatos(conexionBaseDatos);

            int opcionSeleccionadaMenu = 0;

            while (opcionSeleccionadaMenu != 12) {

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

                // Leemos el número directamente en el main.
                opcionSeleccionadaMenu = sc.nextInt();
                sc.nextLine();

                try {
                    switch (opcionSeleccionadaMenu) {

                        case 1:
                            insertarNuevoAlumno(conexionBaseDatos, sc);
                            break;

                        case 2:
                            listarTodosLosAlumnos(conexionBaseDatos);
                            break;

                        case 3:
                            registrarNotaAlumno(conexionBaseDatos, sc);
                            break;

                        case 4:
                            mostrarTodasLasNotasAlumno(
                                    conexionBaseDatos, sc);
                            break;

                        case 5:
                            calcularNotaMediaAlumno(
                                    conexionBaseDatos, sc);
                            break;

                        case 6:
                            calcularNotaMediaPorAsignatura(conexionBaseDatos);
                            break;

                        case 7:
                            mostrarNotaMaximaYMinima(
                                    conexionBaseDatos, sc);
                            break;

                        case 8:
                            calcularEdadMediaCurso(
                                    conexionBaseDatos, sc);
                            break;

                        case 9:
                            mostrarAprobadosYSuspensos(
                                    conexionBaseDatos, sc);
                            break;

                        case 10:
                            mostrarAlumnosSobreMediaGlobal(conexionBaseDatos);
                            break;

                        case 11:
                            eliminarAlumnoPorIdentificador(
                                    conexionBaseDatos, sc);
                            break;

                        case 12:
                            System.out.println("Programa finalizado.");
                            break;

                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }

                } catch (SQLException excepcionBaseDatos) {
                    System.out.println("Error al ejecutar la operación: " + excepcionBaseDatos.getMessage());
                }
            }

        } catch (SQLException excepcionConexionBaseDatos) {
            System.out.println("No se pudo conectar con la base de datos.");
            System.err.println(excepcionConexionBaseDatos.getMessage());
        }
    }

    // ! 1. Insertar un alumno nuevo.
    private static void insertarNuevoAlumno(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("Nombre: ");
        String nombreCompletoAlumno = sc.nextLine();

        System.out.print("Edad: ");
        int edadAlumno = sc.nextInt();
        sc.nextLine();

        System.out.print("Curso: ");
        String nombreCursoAlumno = sc.nextLine();

        String consultaSQL = "INSERT INTO alumnos (nombre, edad, curso) VALUES (?, ?, ?)";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setString(1, nombreCompletoAlumno);
            sentenciaPreparadaSQL.setInt(2, edadAlumno);
            sentenciaPreparadaSQL.setString(3, nombreCursoAlumno);
            sentenciaPreparadaSQL.executeUpdate();

            System.out.println("Alumno añadido correctamente.");
        }
    }

    // ! 2. Listar todos los alumnos.
    private static void listarTodosLosAlumnos(Connection conexionBaseDatos) throws SQLException {

        String consultaSQL = "SELECT * FROM alumnos ORDER BY id";

        try (Statement sentenciaSQL = conexionBaseDatos.createStatement();
                ResultSet resultadoConsultaSQL = sentenciaSQL.executeQuery(consultaSQL)) {

            boolean existenAlumnosRegistrados = false;

            while (resultadoConsultaSQL.next()) {
                if (existenAlumnosRegistrados) {
                    System.out.println(
                            "ID: " + resultadoConsultaSQL.getInt("id")
                                    + " | Nombre: "
                                    + resultadoConsultaSQL.getString("nombre")
                                    + " | Edad: "
                                    + resultadoConsultaSQL.getInt("edad") + " años | Curso: "
                                    + resultadoConsultaSQL.getString("curso"));

                }
            }

            if (!existenAlumnosRegistrados) {
                System.out.println("No hay alumnos registrados.");
            }
        }
    }

    // ! 3. Registrar una nota.
    private static void registrarNotaAlumno(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("ID del alumno: ");
        int identificadorAlumno = sc.nextInt();
        sc.nextLine();

        System.out.print("Asignatura: ");
        String nombreAsignatura = sc.nextLine();

        // [IMPORTANT]: COMPROBAR
        System.out.print("Nota (0-10): ");
        String textoCalificacionAlumno = sc.nextLine().replace(',', '.');
        Scanner lectorNota = new Scanner(textoCalificacionAlumno);
        double calificacionAlumno = lectorNota.nextDouble();
        lectorNota.close();

        if (calificacionAlumno < 0 || calificacionAlumno > 10) {
            System.out.println("La nota debe estar entre 0 y 10.");
            return;
        }

        String consultaSQL = "INSERT INTO notas (alumno_id, asignatura, nota) VALUES (?, ?, ?)";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setInt(1, identificadorAlumno);
            sentenciaPreparadaSQL.setString(2, nombreAsignatura);
            sentenciaPreparadaSQL.setDouble(3, calificacionAlumno);

            sentenciaPreparadaSQL.executeUpdate();

            System.out.println("Nota registrada correctamente.");
        }
    }

    // ! 4. Mostrar todas las notas de un alumno.
    private static void mostrarTodasLasNotasAlumno(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("ID del alumno: ");
        int identificadorAlumno = sc.nextInt();
        sc.nextLine();

        String consultaSQL = "SELECT asignatura, nota FROM notas " + "WHERE alumno_id = ? ORDER BY asignatura";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setInt(1, identificadorAlumno);

            try (ResultSet resultadoConsultaSQL = sentenciaPreparadaSQL.executeQuery()) {

                boolean existenNotasRegistradas = false;
                // ! Mientras haya resultados en la consulta SQL.
                while (resultadoConsultaSQL.next()) {
                    existenNotasRegistradas = true;

                    System.out.println("Asignatura: " + resultadoConsultaSQL.getString("asignatura") + " | Nota: "
                            + resultadoConsultaSQL.getDouble("nota"));
                }

                if (!existenNotasRegistradas) {
                    System.out.println(
                            "Este alumno no tiene notas registradas.");
                }
            }
        }
    }

    // ! 5. Calcular la nota media de un alumno.
    private static void calcularNotaMediaAlumno(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("ID del alumno: ");
        int identificadorAlumno = sc.nextInt();
        sc.nextLine();
        String consultaSQL = "SELECT AVG(nota) AS notaMediaCalculada, COUNT(*) AS totalNotas "
                + "FROM notas WHERE alumno_id = ?";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setInt(1, identificadorAlumno);

            try (ResultSet resultadoConsultaSQL = sentenciaPreparadaSQL.executeQuery()) {
                resultadoConsultaSQL.next();

                int totalNotas = resultadoConsultaSQL.getInt("totalNotas");

                if (totalNotas == 0) {
                    System.out.println("El alumno no tiene notas registradas.");
                } else {
                    double notaMediaCalculada = resultadoConsultaSQL.getDouble("notaMediaCalculada");
                    System.out.printf("Nota media: %.2f%n", notaMediaCalculada);
                }
            }
        }
    }

    // ! 6. Calcular la nota media de cada asignatura.
    private static void calcularNotaMediaPorAsignatura(Connection conexionBaseDatos) throws SQLException {

        String consultaSQL = "SELECT asignatura, AVG(nota) AS notaMediaCalculada "
                + "FROM notas GROUP BY asignatura ORDER BY asignatura";

        try (Statement sentenciaSQL = conexionBaseDatos.createStatement();
                ResultSet resultadoConsultaSQL = sentenciaSQL.executeQuery(consultaSQL)) {

            boolean existenNotasRegistradas = false;

            while (resultadoConsultaSQL.next()) {
                existenNotasRegistradas = true;

                System.out.printf(
                        "Asignatura: %s | Media: %.2f%n", resultadoConsultaSQL.getString("asignatura"),
                        resultadoConsultaSQL.getDouble("notaMediaCalculada"));
            }
            if (!existenNotasRegistradas) {
                System.out.println("Todavía no hay notas registradas.");
            }
        }
    }

    // TODO: PREGUNTAR A CHAT
    // ! 7. Mostrar la nota máxima y mínima de una asignatura.
    private static void mostrarNotaMaximaYMinima(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("Asignatura: ");
        String nombreAsignatura = sc.nextLine();

        String consultaSQL = "SELECT alumnos.nombre, notas.nota " + "FROM notas "
                + "JOIN alumnos ON notas.alumno_id = alumnos.id " + "WHERE notas.asignatura = ? "
                + "ORDER BY notas.nota DESC";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setString(1, nombreAsignatura);

            try (ResultSet resultadoConsultaSQL = sentenciaPreparadaSQL.executeQuery()) {

                if (!resultadoConsultaSQL.next()) {
                    System.out.println("No hay notas para esa asignatura.");
                    return;
                }

                double notaMaximaAsignatura = resultadoConsultaSQL.getDouble("nota");

                String nombreAlumnoNotaMaxima = resultadoConsultaSQL.getString("nombre");

                double notaMinimaAsignatura = notaMaximaAsignatura;

                String nombreAlumnoNotaMinima = nombreAlumnoNotaMaxima;

                while (resultadoConsultaSQL.next()) {
                    double calificacionActual = resultadoConsultaSQL.getDouble("nota");

                    if (calificacionActual < notaMinimaAsignatura) {
                        notaMinimaAsignatura = calificacionActual;

                        nombreAlumnoNotaMinima = resultadoConsultaSQL.getString("nombre");
                    }
                }

                System.out.println(
                        "Nota máxima: " + notaMaximaAsignatura
                                + " | Alumno: " + nombreAlumnoNotaMaxima);

                System.out.println(
                        "Nota mínima: " + notaMinimaAsignatura
                                + " | Alumno: " + nombreAlumnoNotaMinima);
            }
        }
    }

    // TODO: QUITAR EL WASNULL
    // ! 8. Calcular la edad media de los alumnos de un curso.
    private static void calcularEdadMediaCurso(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("Curso: ");
        String nombreCursoAlumno = sc.nextLine();

        String consultaSQL = "SELECT AVG(edad) AS edadMediaCalculada " + "FROM alumnos WHERE curso = ?";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setString(1, nombreCursoAlumno);

            try (ResultSet resultadoConsultaSQL = sentenciaPreparadaSQL.executeQuery()) {

                resultadoConsultaSQL.next();
                double edadMediaCalculada = resultadoConsultaSQL.getDouble("edadMediaCalculada");

                if (resultadoConsultaSQL.wasNull()) {
                    System.out.println("No hay alumnos en ese curso.");
                } else {
                    System.out.printf("Edad media del curso: %.2f años%n", edadMediaCalculada);
                }
            }
        }
    }

    // ! 9. Mostrar aprobados y suspensos de una asignatura.
    private static void mostrarAprobadosYSuspensos(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("Asignatura: ");
        String nombreAsignatura = sc.nextLine();

        String consultaSQL = "SELECT "
                + "SUM(CASE WHEN nota >= 5 THEN 1 ELSE 0 END) AS totalAprobados, "
                + "SUM(CASE WHEN nota < 5 THEN 1 ELSE 0 END) AS totalSuspensos "
                + "FROM notas WHERE asignatura = ?";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setString(1, nombreAsignatura);

            try (ResultSet resultadoConsultaSQL = sentenciaPreparadaSQL.executeQuery()) {

                resultadoConsultaSQL.next();
                int numeroAlumnosAprobados = resultadoConsultaSQL.getInt("totalAprobados");
                int numeroAlumnosSuspensos = resultadoConsultaSQL.getInt("totalSuspensos");

                System.out.println("Número de aprobados: " + numeroAlumnosAprobados);
                System.out.println("Número de suspensos: " + numeroAlumnosSuspensos);
            }
        }
    }

    // ! 10. Mostrar alumnos cuya media supera la media global.
    private static void mostrarAlumnosSobreMediaGlobal(
            Connection conexionBaseDatos) throws SQLException {

        String consultaSQL = "SELECT alumnos.id, alumnos.nombre, " + "AVG(notas.nota) AS notaMediaCalculada "
                + "FROM alumnos " + "JOIN notas ON alumnos.id = notas.alumno_id "
                + "GROUP BY alumnos.id, alumnos.nombre " + "HAVING AVG(notas.nota) > "
                + "(SELECT AVG(nota) FROM notas)";

        try (Statement sentenciaSQL = conexionBaseDatos.createStatement();
                ResultSet resultadoConsultaSQL = sentenciaSQL.executeQuery(consultaSQL)) {

            boolean existenAlumnosSobreMedia = false;

            while (resultadoConsultaSQL.next()) {
                existenAlumnosSobreMedia = true;

                System.out.printf("ID: %d | Nombre: %s | Media: %.2f%n",
                        resultadoConsultaSQL.getInt("id"),
                        resultadoConsultaSQL.getString("nombre"),
                        resultadoConsultaSQL.getDouble("notaMediaCalculada"));
            }

            if (!existenAlumnosSobreMedia) {
                System.out.println("Ningún alumno supera la media global.");
            }
        }
    }

    // ! 11. Eliminar un alumno por su identificador.
    private static void eliminarAlumnoPorIdentificador(Connection conexionBaseDatos, Scanner sc) throws SQLException {

        System.out.print("ID del alumno que quieres eliminar: ");
        int identificadorAlumno = sc.nextInt();
        sc.nextLine();

        String consultaSQL = "DELETE FROM alumnos WHERE id = ?";

        try (PreparedStatement sentenciaPreparadaSQL = conexionBaseDatos.prepareStatement(consultaSQL)) {

            sentenciaPreparadaSQL.setInt(1, identificadorAlumno);
            int numeroFilasEliminadas = sentenciaPreparadaSQL.executeUpdate();

            if (numeroFilasEliminadas > 0) {
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No existe ningún alumno con ese ID.");
            }
        }
    }
}
