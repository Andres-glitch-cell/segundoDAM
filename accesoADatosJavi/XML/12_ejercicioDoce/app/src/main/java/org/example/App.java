package org.example;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {
        try {
            File archivoXML = new File("biblioteca.xml");

            JAXBContext claseContexto = JAXBContext.newInstance(LibroListWrapper.class);
            Unmarshaller unmarshaller = claseContexto.createUnmarshaller();

            LibroListWrapper wrapper = (LibroListWrapper) unmarshaller.unmarshal(archivoXML);

            ArrayList<Libro> libroData = new ArrayList<>();
            libroData.addAll(wrapper.getLibros());

            libroData.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Año de publicación: " + libro.getAnioPublicacion());
                System.out.println("Precio: " + libro.getPrecio() + " €");
                System.out.println("-----------------------------------");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
