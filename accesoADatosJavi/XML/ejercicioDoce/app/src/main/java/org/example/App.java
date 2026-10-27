package org.example;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) throws Exception {

        JAXBContext context =
                JAXBContext.newInstance(LibroListWrapper.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        try (InputStream archivo = App.class.getResourceAsStream("/biblioteca.xml")) {

            if (archivo == null) {
                throw new IllegalStateException(
                        "No se encuentra biblioteca.xml en resources");
            }

            LibroListWrapper biblioteca =
                    (LibroListWrapper) unmarshaller.unmarshal(archivo);

            for (Libro libro : biblioteca.getLibros()) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Año: " + libro.getAnioPublicacion());
                System.out.println("Precio: " + libro.getPrecio() + " €");
                System.out.println();
            }
        }
    }
}
