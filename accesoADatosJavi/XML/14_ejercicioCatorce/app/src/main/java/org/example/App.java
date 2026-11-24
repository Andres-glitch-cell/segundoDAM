package org.example;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {
        try {
            File archivoXML = new File("peliculas.xml");

            JAXBContext claseContexto = JAXBContext.newInstance(PeliculaListWrapper.class);
            Unmarshaller unmarshaller = claseContexto.createUnmarshaller();

            PeliculaListWrapper cartelera = (PeliculaListWrapper) unmarshaller.unmarshal(archivoXML);

            cartelera.getPeliculas().forEach(element -> {
                if (element.getClasificacion() > 13) {
                    System.out.println("\n \n");
                    System.out.println("Título: " + element.getTitulo());
                    System.out.println("Director: " + element.getDirector());
                    System.out.println("Año: " + element.getAnio());
                    System.out.println("Género: " + element.getGenero());
                    System.out.println("Duración: " + element.getDuracion() + " minutos");
                    System.out.println("Clasificación: +" + element.getClasificacion() + " años");
                    System.out.println();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
