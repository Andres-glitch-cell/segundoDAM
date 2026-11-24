package org.example;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {
        try {
            // Busca el archivo XML de las películas
            File archivoXML = new File("app/build/resources/main/Peliculas.xml");

            // Prepara JAXB para trabajar con la clase Peliculas
            JAXBContext context = JAXBContext.newInstance(Peliculas.class);

            // Crea el objeto que convierte los datos XML en objetos Java
            Unmarshaller um = context.createUnmarshaller();

            // Convierte el XML en un objeto de la clase Peliculas
            Peliculas wrapper = (Peliculas) um.unmarshal(archivoXML);

            // Crea una lista vacía para guardar las películas
            ArrayList<Pelicula> peliculas = new ArrayList<>();

            // Añade todas las películas del XML a la lista
            peliculas.addAll(wrapper.getPeliculas());

            // Recorre las películas y muestra solo las que tienen clasificación mayor de 13
            for (Pelicula pelicula : peliculas) {
                if (pelicula.getClasificacion() > 13) {
                    System.out.println(
                            "Título: " + pelicula.getTitulo()
                                    + ", Director: " + pelicula.getDirector()
                                    + ", Año: " + pelicula.getAnio()
                                    + ", Género: " + pelicula.getGenero()
                                    + ", Duración: " + pelicula.getDuracion() + " minutos"
                                    + ", Clasificación: " + pelicula.getClasificacion());
                }
            }

        } catch (Exception e) {
            // Muestra el error si ocurre algún problema
            e.printStackTrace();
        }
    }
}