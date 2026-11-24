package org.example;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {

        try {

            // Indicamos dónde están los dos archivos XML
            String rutaPueblos = "app/src/main/resources/pueblos.xml";
            String rutaProvincias = "app/src/main/resources/provincias.xml";

            File archivoPueblos = new File(rutaPueblos);
            File archivoProvincias = new File(rutaProvincias);

            // Creamos el contexto JAXB indicando la clase Pueblos
            JAXBContext contextPueblos = JAXBContext.newInstance(Pueblos.class);

            // Creamos el objeto que permite leer el XML
            Unmarshaller umPueblos = contextPueblos.createUnmarshaller();

            // Leemos el XML y lo convertimos en un objeto Pueblos
            Pueblos wrapperPueblos = (Pueblos) umPueblos.unmarshal(archivoPueblos);

            // Creamos una lista para almacenar los pueblos
            ArrayList<Pueblo> listaPueblos = new ArrayList<>();

            // Añadimos todos los pueblos del XML a nuestra lista
            listaPueblos.addAll(wrapperPueblos.getPueblos());


            // Creamos el contexto JAXB indicando la clase Provincias
            JAXBContext contextProvincias = JAXBContext.newInstance(Provincias.class);

            // Creamos el objeto que permite leer el XML
            Unmarshaller umProvincias = contextProvincias.createUnmarshaller();

            // Leemos el XML y lo convertimos en un objeto Provincias
            Provincias wrapperProvincias = (Provincias) umProvincias.unmarshal(archivoProvincias);

            // Creamos una lista para almacenar las provincias
            ArrayList<Provincia> listaProvincias = new ArrayList<>();

            // Añadimos todas las provincias del XML a nuestra lista
            listaProvincias.addAll(wrapperProvincias.getProvincias());


            // Ordenamos los pueblos de mayor a menor habitantes
            for (int i = 0; i < listaPueblos.size(); i++) {

                for (int j = i + 1; j < listaPueblos.size(); j++) {

                    if (listaPueblos.get(j).getNumHabitantes()
                            > listaPueblos.get(i).getNumHabitantes()) {

                        Pueblo aux = listaPueblos.get(i);

                        listaPueblos.set(i, listaPueblos.get(j));

                        listaPueblos.set(j, aux);
                    }
                }
            }


            // Mostramos los tres pueblos con más habitantes
            System.out.println("\n--- LOS 3 PUEBLOS CON MÁS HABITANTES ---");

            for (int i = 0; i < 3; i++) {

                Pueblo pueblo = listaPueblos.get(i);

                System.out.println("\nNombre: " + pueblo.getNombre());
                System.out.println("Gentilicio: " + pueblo.getGentilicio());

                // Buscamos la provincia del pueblo
                for (Provincia provincia : listaProvincias) {

                    if (provincia.getNombre().equals(pueblo.getProvincia())) {

                        System.out.println("Provincia: " + provincia.getNombre());
                        System.out.println("Ingresos: " + provincia.getIngresos() + " €");
                    }
                }
            }


            // Buscamos la provincia más pobre
            Provincia provinciaPobre = listaProvincias.get(0);

            for (Provincia provincia : listaProvincias) {

                if (provincia.getIngresos() < provinciaPobre.getIngresos()) {

                    provinciaPobre = provincia;
                }
            }


            // Mostramos la provincia más pobre
            System.out.println("\n--- PROVINCIA MÁS POBRE ---");
            System.out.println("Provincia: " + provinciaPobre.getNombre());
            System.out.println("Ingresos: " + provinciaPobre.getIngresos() + " €");


            // Mostramos los pueblos de esa provincia
            System.out.println("\n--- PUEBLOS DE LA PROVINCIA MÁS POBRE ---");

            for (Pueblo pueblo : listaPueblos) {

                if (pueblo.getProvincia().equals(provinciaPobre.getNombre())) {

                    System.out.println(pueblo.getNombre());
                }
            }

        } catch (Exception e) {

            // Mostramos el error si ocurre algún problema
            e.printStackTrace();
        }
    }
}