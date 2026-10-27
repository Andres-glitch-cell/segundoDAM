package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
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

            // Creamos un Statement para ejecutar instrucciones SQL.
            Statement s = c.createStatement();

            // Guardamos la consulta SQL para crear la tabla Libros si todavía no existe.
            String crearLibros = "CREATE TABLE IF NOT EXISTS Libros ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "titulo VARCHAR(100), "
                    + "autor VARCHAR(100), "
                    + "anio_publicacion INT, "
                    + "disponible BOOLEAN"
                    + ")";

            // executeUpdate() ejecuta la instrucción SQL que crea la tabla.
            s.executeUpdate(crearLibros);

            // Guardamos la consulta SQL para crear la tabla Usuarios si todavía no existe.
            String crearUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nombre VARCHAR(100), "
                    + "email VARCHAR(100)"
                    + ")";

            // Ejecutamos la instrucción SQL para crear la tabla Usuarios.
            s.executeUpdate(crearUsuarios);

            // Guardamos la consulta SQL para crear la tabla Prestamos si todavía no existe.
            // id_usuario e id_libro son claves foráneas que relacionan las tablas.
            String crearPrestamos = "CREATE TABLE IF NOT EXISTS Prestamos ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "id_usuario INT, "
                    + "id_libro INT, "
                    + "fecha_prestamo DATE, "
                    + "fecha_devolucion DATE NULL, "
                    + "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id), "
                    + "FOREIGN KEY (id_libro) REFERENCES Libros(id)"
                    + ")";

            // Ejecutamos la instrucción SQL para crear la tabla Prestamos.
            s.executeUpdate(crearPrestamos);

            System.out.println("Tablas creadas correctamente.");

            // Scanner permite leer los datos que el usuario introduce por consola.
            Scanner teclado = new Scanner(System.in);

            int opcion = 0;

            // Repetimos el menú mientras el usuario no elija la opción 10.
            while (opcion != 10) {

                // Dejamos una línea en blanco antes de mostrar el menú.
                System.out.println();

                System.out.println("--- MENÚ BIBLIOTECA ---");
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
                System.out.print("Elige una opción: ");

                // Leemos la opción introducida y la convertimos a un número entero.
                opcion = Integer.parseInt(teclado.nextLine());

                // Ejecutamos las instrucciones correspondientes a la opción elegida.
                switch (opcion) {

                    case 1: {
                        // Pedimos los datos del nuevo libro.
                        System.out.print("Introduce el título: ");
                        String titulo = teclado.nextLine();

                        System.out.print("Introduce el autor: ");
                        String autor = teclado.nextLine();

                        System.out.print("Introduce el año de publicación: ");
                        int anio = Integer.parseInt(teclado.nextLine());

                        // Preparamos la consulta INSERT para guardar el libro.
                        // El libro se marca como disponible al introducirlo.
                        String sql = "INSERT INTO Libros " + "(titulo, autor, anio_publicacion, disponible) "
                                + "VALUES (?, ?, ?, TRUE)";

                        PreparedStatement ps = c.prepareStatement(sql);

                        // Sustituimos los signos ? por los datos introducidos por el usuario.
                        ps.setString(1, titulo);
                        ps.setString(2, autor);
                        ps.setInt(3, anio);

                        // executeUpdate() ejecuta el INSERT y guarda el libro en la base de datos.
                        ps.executeUpdate();

                        System.out.println("Libro insertado correctamente.");

                        // Cerramos el PreparedStatement para liberar los recursos utilizados.
                        ps.close();
                        break;
                    }

                    case 2: {
                        // Guardamos y ejecutamos la consulta SQL para obtener todos los libros.
                        // executeQuery() devuelve los resultados en un ResultSet.
                        ResultSet rs = s.executeQuery("SELECT * FROM Libros");

                        // rs.next() avanza por las filas mientras queden libros por mostrar.
                        while (rs.next()) {

                            // Obtenemos los datos del libro de la fila actual.
                            int id = rs.getInt("id");
                            String titulo = rs.getString("titulo");
                            String autor = rs.getString("autor");
                            int anio = rs.getInt("anio_publicacion");
                            boolean disponible = rs.getBoolean("disponible");

                            // Mostramos los datos del libro por consola.
                            System.out.println(
                                    id + ", "
                                            + titulo + ", "
                                            + autor + ", "
                                            + anio + ", "
                                            + "Disponible: " + disponible);
                        }

                        // Cerramos el ResultSet cuando terminamos de recorrer los resultados.
                        rs.close();
                        break;
                    }

                    case 3: {
                        // Pedimos el nombre del autor por el que queremos buscar libros.
                        System.out.print("Introduce el autor: ");
                        String autor = teclado.nextLine();

                        // Preparamos una consulta para buscar libros cuyo autor coincida total o
                        // parcialmente.
                        // LIKE permite buscar coincidencias de texto y los % permiten que haya texto
                        // antes o después.
                        String sql = "SELECT * FROM Libros WHERE autor LIKE ?";

                        PreparedStatement ps = c.prepareStatement(sql);

                        // Colocamos el texto de búsqueda entre % para encontrar coincidencias
                        // parciales.
                        ps.setString(1, "%" + autor + "%");

                        // Ejecutamos el SELECT y guardamos los resultados en un ResultSet.
                        ResultSet rs = ps.executeQuery();

                        // Recorremos los libros que coinciden con el autor buscado.
                        while (rs.next()) {

                            // Obtenemos los datos del libro encontrado.
                            int id = rs.getInt("id");
                            String titulo = rs.getString("titulo");
                            String autorLibro = rs.getString("autor");
                            int anio = rs.getInt("anio_publicacion");
                            boolean disponible = rs.getBoolean("disponible");

                            // Mostramos los datos del libro encontrado.
                            System.out.println(id + ", " + titulo + ", " + autorLibro + ", " + anio + ", "
                                    + "Disponible: " + disponible);
                        }

                        // Cerramos los resultados y el PreparedStatement utilizado en la búsqueda.
                        rs.close();
                        ps.close();
                        break;
                    }

                    case 4: {
                        // Pedimos los datos del nuevo usuario.
                        System.out.print("Introduce el nombre: ");
                        String nombre = teclado.nextLine();

                        System.out.print("Introduce el email: ");
                        String email = teclado.nextLine();

                        // Preparamos la consulta INSERT para guardar el usuario en la tabla Usuarios.
                        String sql = "INSERT INTO Usuarios (nombre, email) VALUES (?, ?)";

                        PreparedStatement ps = c.prepareStatement(sql);

                        // Colocamos el nombre y el email en los dos signos ? de la consulta.
                        ps.setString(1, nombre);
                        ps.setString(2, email);

                        // Ejecutamos el INSERT para guardar el usuario en la base de datos.
                        ps.executeUpdate();

                        System.out.println("Usuario registrado correctamente.");

                        // Cerramos el PreparedStatement después de registrar el usuario.
                        ps.close();
                        break;
                    }

                    case 5: {
                        // Ejecutamos una consulta SQL para obtener todos los usuarios registrados.
                        ResultSet rs = s.executeQuery("SELECT * FROM Usuarios");

                        // Recorremos los resultados mientras queden usuarios por mostrar.
                        while (rs.next()) {

                            // Obtenemos el identificador, el nombre y el email del usuario actual.
                            int id = rs.getInt("id");
                            String nombre = rs.getString("nombre");
                            String email = rs.getString("email");

                            // Mostramos los datos del usuario por consola.
                            System.out.println(id + ", " + nombre + ", " + email);
                        }

                        // Cerramos el ResultSet después de mostrar los usuarios.
                        rs.close();
                        break;
                    }

                    case 6: {
                        // Pedimos el ID del usuario que quiere realizar el préstamo.
                        System.out.print("Introduce el id del usuario: ");
                        int idUsuario = Integer.parseInt(teclado.nextLine());

                        // Pedimos el ID del libro que se quiere prestar.
                        System.out.print("Introduce el id del libro: ");
                        int idLibro = Integer.parseInt(teclado.nextLine());

                        // Preparamos una consulta para comprobar si existe el usuario indicado.
                        String sqlUsuario = "SELECT id FROM Usuarios WHERE id = ?";

                        PreparedStatement psUsuario = c.prepareStatement(sqlUsuario);

                        // Colocamos el ID introducido en el signo ? de la consulta.
                        psUsuario.setInt(1, idUsuario);

                        // Ejecutamos la consulta para comprobar si existe ese usuario.
                        ResultSet rsUsuario = psUsuario.executeQuery();

                        // rs.next() devuelve true si se ha encontrado el usuario y false si no existe.
                        boolean existeUsuario = rsUsuario.next();

                        // Cerramos los resultados y la consulta utilizada para comprobar el usuario.
                        rsUsuario.close();
                        psUsuario.close();

                        // Preparamos una consulta para comprobar si existe el libro y si está
                        // disponible.
                        String sqlLibro = "SELECT disponible FROM Libros WHERE id = ?";

                        PreparedStatement psLibro = c.prepareStatement(sqlLibro);

                        // Colocamos el ID del libro en el signo ? de la consulta.
                        psLibro.setInt(1, idLibro);

                        // Ejecutamos la consulta para obtener el estado del libro.
                        ResultSet rsLibro = psLibro.executeQuery();

                        // Inicializamos las variables que indican si existe el libro y si está
                        // disponible.
                        boolean existeLibro = false;
                        boolean disponible = false;

                        // Comprobamos si la consulta ha encontrado un libro.
                        if (rsLibro.next()) {
                            existeLibro = true;

                            // Obtenemos el valor de la columna disponible del libro encontrado.
                            disponible = rsLibro.getBoolean("disponible");
                        }

                        // Cerramos los resultados y la consulta utilizada para comprobar el libro.
                        rsLibro.close();
                        psLibro.close();

                        // Comprobamos los datos antes de registrar el préstamo.
                        if (!existeUsuario) {
                            // Si no existe el usuario, mostramos un mensaje de error.
                            System.out.println("No existe ningún usuario con ese id.");

                        } else if (!existeLibro) {
                            // Si no existe el libro, mostramos un mensaje de error.
                            System.out.println("No existe ningún libro con ese id.");

                        } else if (!disponible) {
                            // Si el libro no está disponible, no permitimos realizar el préstamo.
                            System.out.println("El libro no está disponible.");

                        } else {
                            // Preparamos la consulta para registrar el préstamo.
                            // CURRENT_DATE guarda la fecha actual y NULL deja vacía la fecha de devolución.
                            String sqlPrestamo = "INSERT INTO Prestamos "
                                    + "(id_usuario, id_libro, fecha_prestamo, fecha_devolucion) "
                                    + "VALUES (?, ?, CURRENT_DATE, NULL)";

                            PreparedStatement psPrestamo = c.prepareStatement(sqlPrestamo);

                            // Colocamos el ID del usuario y el ID del libro en la consulta.
                            psPrestamo.setInt(1, idUsuario);
                            psPrestamo.setInt(2, idLibro);

                            // Ejecutamos el INSERT para guardar el nuevo préstamo.
                            psPrestamo.executeUpdate();

                            // Cerramos el PreparedStatement después de registrar el préstamo.
                            psPrestamo.close();

                            // Preparamos una consulta para marcar el libro como no disponible.
                            String sqlActualizar = "UPDATE Libros SET disponible = FALSE WHERE id = ?";

                            PreparedStatement psActualizar = c.prepareStatement(sqlActualizar);

                            // Indicamos qué libro debemos actualizar.
                            psActualizar.setInt(1, idLibro);

                            // Ejecutamos el UPDATE para indicar que el libro está prestado.
                            psActualizar.executeUpdate();

                            // Cerramos el PreparedStatement después de actualizar el libro.
                            psActualizar.close();

                            System.out.println("Préstamo registrado correctamente.");
                        }

                        break;
                    }

                    case 7: {
                        // Pedimos el ID del préstamo que queremos devolver.
                        System.out.print("Introduce el id del préstamo: ");
                        int idPrestamo = Integer.parseInt(teclado.nextLine());

                        // Buscamos el préstamo indicado y comprobamos que todavía no se haya devuelto.
                        // IS NULL permite encontrar los préstamos que no tienen fecha de devolución.
                        String sqlBuscar = "SELECT id_libro FROM Prestamos "
                                + "WHERE id = ? AND fecha_devolucion IS NULL";

                        PreparedStatement psBuscar = c.prepareStatement(sqlBuscar);

                        // Colocamos el ID del préstamo en el signo ? de la consulta.
                        psBuscar.setInt(1, idPrestamo);

                        // Ejecutamos la consulta para buscar el préstamo activo.
                        ResultSet rs = psBuscar.executeQuery();

                        // Inicializamos las variables para guardar el ID del libro y comprobar el
                        // préstamo.
                        int idLibro = 0;
                        boolean existePrestamo = false;

                        // Si se encuentra el préstamo, obtenemos el ID del libro relacionado.
                        if (rs.next()) {
                            idLibro = rs.getInt("id_libro");
                            existePrestamo = true;
                        }

                        // Cerramos los resultados y la consulta utilizada para buscar el préstamo.
                        rs.close();
                        psBuscar.close();

                        // Solo registramos la devolución si el préstamo existe y sigue activo.
                        if (existePrestamo) {

                            // Preparamos la consulta para guardar la fecha actual como fecha de devolución.
                            String sqlDevolucion = "UPDATE Prestamos "
                                    + "SET fecha_devolucion = CURRENT_DATE "
                                    + "WHERE id = ?";

                            PreparedStatement psDevolucion = c.prepareStatement(sqlDevolucion);

                            // Indicamos qué préstamo queremos actualizar.
                            psDevolucion.setInt(1, idPrestamo);

                            // Ejecutamos el UPDATE para registrar la devolución.
                            psDevolucion.executeUpdate();

                            // Cerramos el PreparedStatement después de actualizar el préstamo.
                            psDevolucion.close();

                            // Preparamos una consulta para volver a marcar el libro como disponible.
                            String sqlActualizar = "UPDATE Libros "
                                    + "SET disponible = TRUE WHERE id = ?";

                            PreparedStatement psActualizar = c.prepareStatement(sqlActualizar);

                            // Indicamos qué libro debemos marcar como disponible.
                            psActualizar.setInt(1, idLibro);

                            // Ejecutamos el UPDATE para cambiar el estado del libro.
                            psActualizar.executeUpdate();

                            // Cerramos el PreparedStatement después de actualizar el libro.
                            psActualizar.close();

                            System.out.println("Devolución registrada correctamente.");

                        } else {
                            // Si no existe el préstamo o ya se había devuelto, mostramos un mensaje.
                            System.out.println("No existe un préstamo activo con ese id.");
                        }

                        break;
                    }

                    case 8: {
                        // Preparamos una consulta para mostrar los préstamos que todavía no se han
                        // devuelto.
                        // Unimos las tablas Usuarios y Libros para mostrar el nombre del usuario y el
                        // título.
                        String sql = "SELECT Prestamos.id, Usuarios.nombre, "
                                + "Libros.titulo, Prestamos.fecha_prestamo "
                                + "FROM Prestamos "
                                + "INNER JOIN Usuarios ON Prestamos.id_usuario = Usuarios.id "
                                + "INNER JOIN Libros ON Prestamos.id_libro = Libros.id "
                                + "WHERE Prestamos.fecha_devolucion IS NULL";

                        // Ejecutamos el SELECT y guardamos los resultados en un ResultSet.
                        ResultSet rs = s.executeQuery(sql);

                        // Recorremos los préstamos activos devueltos por la consulta.
                        while (rs.next()) {

                            // Obtenemos los datos del préstamo de la fila actual.
                            int id = rs.getInt("id");
                            String nombre = rs.getString("nombre");
                            String titulo = rs.getString("titulo");
                            String fecha = rs.getString("fecha_prestamo");

                            // Mostramos el ID del préstamo, el usuario, el libro y la fecha.
                            System.out.println(
                                    "Préstamo: " + id
                                            + ", Usuario: " + nombre
                                            + ", Libro: " + titulo
                                            + ", Fecha: " + fecha);
                        }

                        // Cerramos el ResultSet después de mostrar los préstamos.
                        rs.close();
                        break;
                    }

                    case 9: {
                        // Pedimos el ID del libro que queremos eliminar.
                        System.out.print("Introduce el id del libro: ");
                        int idLibro = Integer.parseInt(teclado.nextLine());

                        // Comprobamos si el libro aparece en algún préstamo registrado.
                        // Si tiene préstamos, no lo eliminamos para mantener los datos relacionados.
                        String sqlBuscar = "SELECT id FROM Prestamos WHERE id_libro = ?";

                        PreparedStatement psBuscar = c.prepareStatement(sqlBuscar);

                        // Colocamos el ID del libro en el signo ? de la consulta.
                        psBuscar.setInt(1, idLibro);

                        // Ejecutamos la consulta para buscar préstamos relacionados con ese libro.
                        ResultSet rs = psBuscar.executeQuery();

                        // Si rs.next() devuelve true, significa que existe al menos un préstamo
                        // relacionado.
                        boolean tienePrestamos = rs.next();

                        // Cerramos los resultados y la consulta de búsqueda.
                        rs.close();
                        psBuscar.close();

                        // Comprobamos si podemos eliminar el libro.
                        if (tienePrestamos) {
                            // Si el libro tiene préstamos registrados, mostramos un mensaje y no lo
                            // eliminamos.
                            System.out.println(
                                    "No se puede eliminar el libro porque tiene préstamos registrados.");

                        } else {
                            // Preparamos la consulta DELETE para eliminar el libro indicado por su ID.
                            String sqlEliminar = "DELETE FROM Libros WHERE id = ?";

                            PreparedStatement psEliminar = c.prepareStatement(sqlEliminar);

                            // Colocamos el ID del libro en el signo ? de la consulta.
                            psEliminar.setInt(1, idLibro);

                            // Ejecutamos el DELETE y guardamos el número de filas eliminadas.
                            int filasEliminadas = psEliminar.executeUpdate();

                            // Comprobamos si se ha eliminado alguna fila.
                            if (filasEliminadas > 0) {
                                System.out.println("Libro eliminado correctamente.");
                            } else {
                                // Si no se ha eliminado ninguna fila, no existe un libro con ese ID.
                                System.out.println("No existe ningún libro con ese id.");
                            }

                            // Cerramos el PreparedStatement después de intentar eliminar el libro.
                            psEliminar.close();
                        }

                        break;
                    }

                    case 10:
                        // Mostramos el mensaje de salida.
                        // El while terminará porque la opción seleccionada es 10.
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
            // Capturamos los errores que puedan producirse al conectar o trabajar con la
            // base de datos.
            // Mostramos información sobre el error para ayudar a identificar el problema.
            System.err.println(e);
        }
    }
}