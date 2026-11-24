package org.example;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {

        try {

            // Indicamos la ruta donde se encuentra el archivo XML
            File archivoXML = new File("app/src/main/resources/centros.xml");

            // Creamos el contexto JAXB indicando qué clase representa el XML
            JAXBContext context = JAXBContext.newInstance(Centros.class);

            // Creamos el objeto Unmarshaller, que permite leer y convertir el XML
            // en objetos Java
            Unmarshaller um = context.createUnmarshaller();

            // Leemos el archivo XML y convertimos su contenido en un objeto Centros
            Centros wrapper = (Centros) um.unmarshal(archivoXML);

            // Creamos una lista para almacenar todos los centros educativos
            ArrayList<Centro> listaCentros = new ArrayList<>();

            // Añadimos a nuestra lista todos los centros obtenidos del XML
            listaCentros.addAll(wrapper.getCentros());

            // Creamos un contador para los profesores de Informática
            int contadorInformatica = 0;

            // Recorremos todos los centros de la lista
            for (Centro centro : listaCentros) {

                // Mostramos los datos del centro actual
                System.out.println("\n--- CENTRO EDUCATIVO ---");
                System.out.println("ID: " + centro.getId());
                System.out.println("Nombre: " + centro.getNombre());
                System.out.println("Ciudad: " + centro.getCiudad());
                System.out.println("Dirección: " + centro.getDireccion());
                System.out.println("Código postal: " + centro.getCodigoPostal());

                // Recorremos la lista de profesores del centro actual
                for (Profesor profesor : centro.getProfesores()) {

                    // Mostramos los datos del profesor
                    System.out.println("\n  Profesor: " + profesor.getNombre());
                    System.out.println("  Departamento: " + profesor.getDepartamento());

                    // Mostramos todas las asignaturas que imparte
                    System.out.println("  Asignaturas: " + profesor.getAsignaturas());

                    // Comprobamos si el profesor pertenece a Informática
                    // trim() elimina espacios sobrantes y equalsIgnoreCase()
                    // compara el texto sin distinguir mayúsculas y minúsculas
                    if (profesor.getDepartamento().trim().equalsIgnoreCase("Informática")) {

                        // Si pertenece a Informática, aumentamos el contador
                        contadorInformatica++;
                    }
                }
            }

            // Mostramos el número total de profesores de Informática
            System.out.println("\n------------------------------");
            System.out.println("Total de profesores de Informática: " + contadorInformatica);

        } catch (Exception e) {

            // Si ocurre algún error al leer el XML, mostramos el error
            e.printStackTrace();
        }
    }
}