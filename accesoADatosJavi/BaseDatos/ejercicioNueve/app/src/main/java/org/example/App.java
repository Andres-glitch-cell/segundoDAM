
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
        String clave = "TU_CONTRASEÑA";

        Scanner sc = new Scanner(System.in);

        try (Connection conexion = DriverManager.getConnection(url, usuario, clave)) {

            System.out.println("Conexión realizada correctamente.");

            // Creamos las tablas necesarias para gestionar la biblioteca.
            try (Statement sentencia = conexion.createStatement()) {

                String tablaLibros = "CREATE TABLE IF NOT EXISTS Libros ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "titulo VARCHAR(100), "
                        + "autor VARCHAR(100), "
                        + "anio_publicacion INT, "
                        + "disponible BOOLEAN"
                        + ")";

                sentencia.executeUpdate(tablaLibros);

                String tablaUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "nombre VARCHAR(100), "
                        + "email VARCHAR(100)"
                        + ")";

                sentencia.executeUpdate(tablaUsuarios);

                String tablaPrestamos = "CREATE TABLE IF NOT EXISTS Prestamos ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "id_usuario INT, "
                        + "id_libro INT, "
                        + "fecha_prestamo DATE, "
                        + "fecha_devolucion DATE NULL, "
                        + "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id), "
                        + "FOREIGN KEY (id_libro) REFERENCES Libros(id)"
                        + ")";

                sentencia.executeUpdate(tablaPrestamos);
            }

            System.out.println("Tablas creadas correctamente.");

            int opcion = 0;

            // Repetimos el menú hasta que el usuario seleccione salir.
            while (opcion != 10) {

                System.out.println("\n========== MENÚ BIBLIOTECA ==========");
                System.out.println("1. Insertar un nuevo libro");
                System.out.println("2. Listar todos los libros");
                System.out.println("3. Buscar libros por autor");
                System.out.println("4. Registrar un nuevo usuario");
                System.out.println("5. Listar todos los usuarios");
                System.out.println("6. Registrar un préstamo");
                System.out.println("7. Registrar la devolución de un libro");
                System.out.println("8. Mostrar préstamos activos");
                System.out.println("9. Eliminar un libro por id");
                System.out.println("10. Salir");
                System.out.print("Selecciona una opción: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine());

                    switch (opcion) {

                        case 1: {

                            // Pedimos los datos del nuevo libro.
                            System.out.print("Título del libro: ");
                            String tituloNuevo = sc.nextLine();

                            System.out.print("Autor del libro: ");
                            String autorNuevo = sc.nextLine();

                            System.out.print("Año de publicación: ");
                            int anioNuevo = Integer.parseInt(sc.nextLine());

                            String insertarLibro = "INSERT INTO Libros "
                                    + "(titulo, autor, anio_publicacion, disponible) "
                                    + "VALUES (?, ?, ?, TRUE)";

                            try (PreparedStatement nuevoLibro = conexion.prepareStatement(insertarLibro)) {

                                nuevoLibro.setString(1, tituloNuevo);
                                nuevoLibro.setString(2, autorNuevo);
                                nuevoLibro.setInt(3, anioNuevo);

                                nuevoLibro.executeUpdate();

                                System.out.println("Libro registrado correctamente.");
                            }

                            break;
                        }

                        case 2: {

                            // Mostramos todos los libros de la biblioteca.
                            String consultaLibros = "SELECT * FROM Libros";

                            try (PreparedStatement buscarLibros = conexion.prepareStatement(consultaLibros);
                                    ResultSet resultados = buscarLibros.executeQuery()) {

                                System.out.println("\n--- LISTADO DE LIBROS ---");

                                mostrarLibros(resultados);
                            }

                            break;
                        }

                        case 3: {

                            // Buscamos los libros que coincidan con el autor indicado.
                            System.out.print("Introduce el autor que quieres buscar: ");
                            String autorBuscar = sc.nextLine();

                            String consultaAutor = "SELECT * FROM Libros WHERE autor LIKE ?";

                            try (PreparedStatement buscarAutor = conexion.prepareStatement(consultaAutor)) {

                                buscarAutor.setString(1, "%" + autorBuscar + "%");

                                try (ResultSet resultados = buscarAutor.executeQuery()) {

                                    System.out.println("\n--- RESULTADOS DE LA BÚSQUEDA ---");

                                    mostrarLibros(resultados);
                                }
                            }

                            break;
                        }

                        case 4: {

                            // Registramos un nuevo usuario.
                            System.out.print("Nombre del usuario: ");
                            String nombreNuevo = sc.nextLine();

                            System.out.print("Correo electrónico: ");
                            String emailNuevo = sc.nextLine();

                            String insertarUsuario = "INSERT INTO Usuarios (nombre, email) VALUES (?, ?)";

                            try (PreparedStatement nuevoUsuario = conexion.prepareStatement(insertarUsuario)) {

                                nuevoUsuario.setString(1, nombreNuevo);
                                nuevoUsuario.setString(2, emailNuevo);

                                nuevoUsuario.executeUpdate();

                                System.out.println("Usuario registrado correctamente.");
                            }

                            break;
                        }

                        case 5: {

                            // Consultamos todos los usuarios registrados.
                            String consultaUsuarios = "SELECT * FROM Usuarios";

                            try (PreparedStatement buscarUsuarios = conexion.prepareStatement(consultaUsuarios);
                                    ResultSet resultados = buscarUsuarios.executeQuery()) {

                                System.out.println("\n--- LISTADO DE USUARIOS ---");

                                boolean hayUsuarios = false;

                                while (resultados.next()) {

                                    int idUsuario = resultados.getInt("id");
                                    String nombreUsuario = resultados.getString("nombre");
                                    String correo = resultados.getString("email");

                                    System.out.println("ID: " + idUsuario
                                            + " | Nombre: " + nombreUsuario
                                            + " | Email: " + correo);

                                    hayUsuarios = true;
                                }

                                if (!hayUsuarios) {
                                    System.out.println("No hay usuarios registrados.");
                                }
                            }

                            break;
                        }

                        case 6: {

                            // Solicitamos los identificadores del usuario y del libro.
                            System.out.print("Introduce el id del usuario: ");
                            int idUsuario = Integer.parseInt(sc.nextLine());

                            System.out.print("Introduce el id del libro: ");
                            int idLibro = Integer.parseInt(sc.nextLine());

                            // Comprobamos que existe el usuario.
                            String comprobarUsuario = "SELECT id FROM Usuarios WHERE id = ?";

                            boolean usuarioExiste;

                            try (PreparedStatement buscarUsuario = conexion.prepareStatement(comprobarUsuario)) {

                                buscarUsuario.setInt(1, idUsuario);

                                try (ResultSet resultado = buscarUsuario.executeQuery()) {
                                    usuarioExiste = resultado.next();
                                }
                            }

                            // Comprobamos que el libro existe y está disponible.
                            String comprobarLibro = "SELECT disponible FROM Libros WHERE id = ?";

                            boolean libroExiste = false;
                            boolean libroDisponible = false;

                            try (PreparedStatement buscarLibro = conexion.prepareStatement(comprobarLibro)) {

                                buscarLibro.setInt(1, idLibro);

                                try (ResultSet resultado = buscarLibro.executeQuery()) {

                                    if (resultado.next()) {
                                        libroExiste = true;
                                        libroDisponible = resultado.getBoolean("disponible");
                                    }
                                }
                            }

                            if (!usuarioExiste) {

                                System.out.println("No existe ese usuario.");

                            } else if (!libroExiste) {

                                System.out.println("No existe ese libro.");

                            } else if (!libroDisponible) {

                                System.out.println("El libro no está disponible.");

                            } else {

                                // Registramos el préstamo con la fecha actual.
                                String insertarPrestamo = "INSERT INTO Prestamos "
                                        + "(id_usuario, id_libro, fecha_prestamo, fecha_devolucion) "
                                        + "VALUES (?, ?, CURRENT_DATE, NULL)";

                                try (PreparedStatement nuevoPrestamo = conexion.prepareStatement(insertarPrestamo)) {

                                    nuevoPrestamo.setInt(1, idUsuario);
                                    nuevoPrestamo.setInt(2, idLibro);

                                    nuevoPrestamo.executeUpdate();
                                }

                                // Marcamos el libro como no disponible.
                                String actualizarLibro = "UPDATE Libros SET disponible = FALSE WHERE id = ?";

                                try (PreparedStatement modificarLibro = conexion.prepareStatement(actualizarLibro)) {

                                    modificarLibro.setInt(1, idLibro);
                                    modificarLibro.executeUpdate();
                                }

                                System.out.println("Préstamo registrado correctamente.");
                            }

                            break;
                        }

                        case 7: {

                            // Pedimos el identificador del préstamo que queremos devolver.
                            System.out.print("Introduce el id del préstamo: ");
                            int idPrestamo = Integer.parseInt(sc.nextLine());

                            // Comprobamos que el préstamo existe y sigue activo.
                            String buscarPrestamo = "SELECT id_libro FROM Prestamos "
                                    + "WHERE id = ? AND fecha_devolucion IS NULL";

                            int idLibro = 0;
                            boolean prestamoExiste = false;

                            try (PreparedStatement buscar = conexion.prepareStatement(buscarPrestamo)) {

                                buscar.setInt(1, idPrestamo);

                                try (ResultSet resultado = buscar.executeQuery()) {

                                    if (resultado.next()) {
                                        idLibro = resultado.getInt("id_libro");
                                        prestamoExiste = true;
                                    }
                                }
                            }

                            if (prestamoExiste) {

                                // Guardamos la fecha de devolución.
                                String devolver = "UPDATE Prestamos "
                                        + "SET fecha_devolucion = CURRENT_DATE WHERE id = ?";

                                try (PreparedStatement actualizarPrestamo = conexion.prepareStatement(devolver)) {

                                    actualizarPrestamo.setInt(1, idPrestamo);
                                    actualizarPrestamo.executeUpdate();
                                }

                                // Volvemos a poner el libro como disponible.
                                String actualizarDisponibilidad = "UPDATE Libros "
                                        + "SET disponible = TRUE WHERE id = ?";

                                try (PreparedStatement actualizarLibro = conexion
                                        .prepareStatement(actualizarDisponibilidad)) {

                                    actualizarLibro.setInt(1, idLibro);
                                    actualizarLibro.executeUpdate();
                                }

                                System.out.println("Devolución registrada correctamente.");

                            } else {

                                System.out.println("No existe un préstamo activo con ese id.");
                            }

                            break;
                        }

                        case 8: {

                            // Mostramos los préstamos que todavía no se han devuelto.
                            String consultaPrestamos = "SELECT Prestamos.id, Usuarios.nombre, "
                                    + "Libros.titulo, Prestamos.fecha_prestamo "
                                    + "FROM Prestamos "
                                    + "INNER JOIN Usuarios ON Prestamos.id_usuario = Usuarios.id "
                                    + "INNER JOIN Libros ON Prestamos.id_libro = Libros.id "
                                    + "WHERE Prestamos.fecha_devolucion IS NULL";

                            try (PreparedStatement buscarPrestamos = conexion.prepareStatement(consultaPrestamos);
                                    ResultSet resultados = buscarPrestamos.executeQuery()) {

                                System.out.println("\n--- PRÉSTAMOS ACTIVOS ---");

                                boolean hayPrestamos = false;

                                while (resultados.next()) {

                                    int idPrestamo = resultados.getInt("id");
                                    String nombreUsuario = resultados.getString("nombre");
                                    String tituloLibro = resultados.getString("titulo");
                                    String fecha = resultados.getString("fecha_prestamo");

                                    System.out.println("Préstamo: " + idPrestamo
                                            + " | Usuario: " + nombreUsuario
                                            + " | Libro: " + tituloLibro
                                            + " | Fecha: " + fecha);

                                    hayPrestamos = true;
                                }

                                if (!hayPrestamos) {
                                    System.out.println("No hay préstamos activos.");
                                }
                            }

                            break;
                        }

                        case 9: {

                            // Pedimos el id del libro que queremos eliminar.
                            System.out.print("Introduce el id del libro: ");
                            int idEliminar = Integer.parseInt(sc.nextLine());

                            // Comprobamos si el libro tiene préstamos registrados.
                            String comprobarPrestamos = "SELECT id FROM Prestamos WHERE id_libro = ?";

                            boolean tienePrestamos;

                            try (PreparedStatement buscarPrestamos = conexion.prepareStatement(comprobarPrestamos)) {

                                buscarPrestamos.setInt(1, idEliminar);

                                try (ResultSet resultado = buscarPrestamos.executeQuery()) {
                                    tienePrestamos = resultado.next();
                                }
                            }

                            if (tienePrestamos) {

                                System.out.println("No se puede eliminar porque tiene préstamos registrados.");

                            } else {

                                String eliminarLibro = "DELETE FROM Libros WHERE id = ?";

                                try (PreparedStatement borrarLibro = conexion.prepareStatement(eliminarLibro)) {

                                    borrarLibro.setInt(1, idEliminar);

                                    int filasBorradas = borrarLibro.executeUpdate();

                                    if (filasBorradas > 0) {
                                        System.out.println("Libro eliminado correctamente.");
                                    } else {
                                        System.out.println("No existe ningún libro con ese id.");
                                    }
                                }
                            }

                            break;
                        }

                        case 10:

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

    // Método para mostrar todos los libros encontrados.
    public static void mostrarLibros(ResultSet datos) throws Exception {

        boolean hayLibros = false;

        while (datos.next()) {

            int identificador = datos.getInt("id");
            String tituloLibro = datos.getString("titulo");
            String autorLibro = datos.getString("autor");
            int anio = datos.getInt("anio_publicacion");
            boolean disponible = datos.getBoolean("disponible");

            System.out.println("ID: " + identificador + " | Título: " + tituloLibro + " | Autor: " + autorLibro
                    + " | Año: " + anio + " | Disponible: " + disponible);

            hayLibros = true;
        }

        if (!hayLibros) {
            System.out.println("No se han encontrado libros.");
        }
    }
}
