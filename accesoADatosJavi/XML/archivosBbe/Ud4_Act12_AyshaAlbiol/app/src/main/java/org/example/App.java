package org.example;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {
        try {
            // Crea un objeto File indicando dónde está guardado el XML
            File archivoXML = new File("app/src/main/resources/Biblioteca.xml");

            // Crea el contexto JAXB indicando que vamos a trabajar con la clase Biblioteca
            JAXBContext context = JAXBContext.newInstance(Biblioteca.class);

            // Crea un objeto Unmarshaller que permite convertir datos XML en objetos Java
            Unmarshaller um = context.createUnmarshaller();

            // Convierte el contenido del archivo XML en un objeto de la clase Biblioteca
            Biblioteca wrapper = (Biblioteca) um.unmarshal(archivoXML);

            // Crea un ArrayList vacío que solo puede guardar objetos de tipo Libro
            ArrayList<Libro> libros = new ArrayList<>();

            // Añade al ArrayList todos los libros que se han obtenido de la biblioteca
            libros.addAll(wrapper.getLibros());

            // Recorre todos los libros de la lista y muestra sus datos por pantalla
            for (Libro libro : libros) {
                System.out.println(
                        libro.getTitulo() + ", "
                        + libro.getAutor() + ", "
                        + libro.getAnio() + ", "
                        + libro.getPrecio() + " €");
            }

        } catch (Exception e) {
            // Muestra los detalles del error si ocurre algún problema durante la ejecución
            e.printStackTrace();
        }
    }
}