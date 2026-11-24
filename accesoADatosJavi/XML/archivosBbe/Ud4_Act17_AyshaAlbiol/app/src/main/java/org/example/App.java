package org.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Scanner;

public class App {

    // Datos de conexión con la base de datos
    static final String URL = "jdbc:mysql://51.48.4.228:3306/basededatos_DAM";

    // Usuario de la base de datos
    static final String USUARIO = "alumno";

    // Contraseña de la base de datos
    static final String PASSWORD = "123456";

    // Nombre de la columna que contiene el nombre del usuario
    static final String COLUMNA_USUARIO = "NOMBRE_USUARIO";

    // Método para guardar los registros de la consulta en un XML
    public static void guardarXML(ResultSet resultado, String nombreArchivo) {

        try {

            // Creamos el constructor del documento XML
            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Creamos la etiqueta principal <historial>
            Element raiz = documento.createElement("historial");
            documento.appendChild(raiz);

            // Obtenemos información sobre las columnas de la tabla
            ResultSetMetaData datosColumnas = resultado.getMetaData();

            // Averiguamos cuántas columnas tiene la tabla
            int numeroColumnas = datosColumnas.getColumnCount();

            // Recorremos todos los registros de la consulta
            while (resultado.next()) {

                // Creamos una etiqueta <registro> para cada fila
                Element registro = documento.createElement("registro");
                raiz.appendChild(registro);

                // Recorremos todas las columnas del registro
                for (int i = 1; i <= numeroColumnas; i++) {

                    // Obtenemos el nombre de la columna
                    String nombreColumna = datosColumnas.getColumnName(i);

                    // Obtenemos el valor de esa columna
                    String valor = resultado.getString(i);

                    // Creamos una etiqueta con el nombre de la columna
                    Element campo = documento.createElement(nombreColumna);

                    // Introducimos el valor dentro de la etiqueta
                    if (valor != null) {
                        campo.setTextContent(valor);
                    } else {
                        campo.setTextContent("");
                    }

                    // Añadimos el campo al registro
                    registro.appendChild(campo);
                }
            }

            // Creamos el objeto que permite escribir el XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer();

            // Indicamos que queremos que el XML tenga formato legible
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // Guardamos el documento XML en el archivo indicado
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File(nombreArchivo));

            transformer.transform(source, result);

            System.out.println("Archivo creado correctamente: " + nombreArchivo);

        } catch (Exception e) {

            // Mostramos el error si ocurre algún problema al crear el XML
            e.printStackTrace();
        }
    }

    // Método principal del programa
    public static void main(String[] args) {

        // Creamos el Scanner para leer el nombre del usuario
        Scanner teclado = new Scanner(System.in);

        try {

            // Establecemos la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);

            System.out.println("Conexión realizada correctamente.");

            // Creamos una consulta para obtener todos los registros
            Statement sentencia = conexion.createStatement();

            // Ejecutamos la consulta sobre la tabla EXAMEN_HISTORIAL
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM EXAMEN_HISTORIAL");

            // Guardamos todos los registros en historial.xml
            guardarXML(resultado, "historial.xml");

            // Cerramos el resultado de la primera consulta
            resultado.close();
            sentencia.close();

            // Pedimos el nombre del usuario
            System.out.print("Introduce el nombre de usuario: ");
            String nombreUsuario = teclado.nextLine();

            // Creamos una consulta para buscar los registros de ese usuario
            String consulta = "SELECT * FROM EXAMEN_HISTORIAL WHERE "
                    + COLUMNA_USUARIO + " = ?";

            // Preparamos la consulta para introducir el nombre del usuario
            PreparedStatement ps = conexion.prepareStatement(consulta);

            // Sustituimos el interrogante por el nombre introducido
            ps.setString(1, nombreUsuario);

            // Ejecutamos la consulta
            ResultSet resultadoUsuario = ps.executeQuery();

            // Creamos el nombre del archivo personalizado
            String nombreArchivo = "historial_" + nombreUsuario + ".xml";

            // Guardamos los registros de ese usuario en su XML
            guardarXML(resultadoUsuario, nombreArchivo);

            // Cerramos los recursos utilizados
            resultadoUsuario.close();
            ps.close();
            conexion.close();

            System.out.println("Programa finalizado.");

        } catch (Exception e) {

            // Mostramos el error si ocurre algún problema
            e.printStackTrace();
        }

        // Cerramos el Scanner
        teclado.close();
    }
}