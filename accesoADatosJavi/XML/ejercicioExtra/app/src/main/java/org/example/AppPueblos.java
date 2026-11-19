package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

public class AppPueblos {

    public static void main(String[] args) throws Exception {

        // Leer los XML
        JAXBContext contextoClasePueblo = JAXBContext.newInstance(PuebloListWrapper.class);
        PuebloListWrapper wrapperPueblos = (PuebloListWrapper) contextoClasePueblo.createUnmarshaller()
                .unmarshal(new File("pueblos.xml"));
        List<Pueblo> pueblos = new ArrayList<>(wrapperPueblos.getPueblos());

        JAXBContext contextoClaseProvincia = JAXBContext.newInstance(ProvinciaListWrapper.class);
        ProvinciaListWrapper wrapperProvincias = (ProvinciaListWrapper) contextoClaseProvincia.createUnmarshaller()
                .unmarshal(new File("provincias.xml"));
        List<Provincia> provincias = new ArrayList<>(wrapperProvincias.getProvincias());

        // 1) Los 3 pueblos con más habitantes
        System.out.println("--- 3 pueblos con más habitantes ---");
        List<Pueblo> restantes = new ArrayList<>(pueblos); // copia para ir quitando el mayor cada vez

        for (int i = 0; i < 3; i++) {

            // Buscar la posición del pueblo con más habitantes de los que quedan
            int posicion = 0; // empezamos suponiendo que es el primero
            for (int j = 1; j < restantes.size(); j++) {
                if (restantes.get(j).getNumHabitantes() > restantes.get(posicion).getNumHabitantes()) {
                    posicion = j; // este tiene más habitantes, nos quedamos con su posición
                }
            }

            // Ahora sí sacamos el pueblo, una sola vez (por eso se puede usar dentro del forEach)
            Pueblo mayor = restantes.get(posicion);

            System.out.println((i + 1) + ". " + mayor.getNombre() + " (" + mayor.getNumHabitantes() + " hab.)");
            System.out.println("   Gentilicio: " + mayor.getGentilicio());
            System.out.println("   Provincia: " + mayor.getProvincia());

            // Para cada provincia, si es la del pueblo, muestra sus ingresos
            provincias.forEach(provincia -> {
                if (provincia.getNombre().equalsIgnoreCase(mayor.getProvincia())) {
                    System.out.println("   Ingresos de la provincia: " + provincia.getIngresos() + " €");
                }
            });

            restantes.remove(posicion); // lo quitamos para que en la siguiente vuelta salga el siguiente
        }

        // 2) Pueblos de la provincia más pobre
        // (for normal otra vez, porque la variable "masPobre" cambia de valor)
        Provincia masPobre = provincias.get(0);
        for (int i = 0; i < provincias.size(); i++) {
            Provincia provincia = provincias.get(i);
            if (provincia.getIngresos() < masPobre.getIngresos()) {
                masPobre = provincia;
            }
        }

        // Copia que ya no cambia, para usarla dentro del forEach
        Provincia pobre = masPobre;

        System.out.println("\n--- Provincia más pobre: " + pobre.getNombre()
                + " (" + pobre.getIngresos() + " €) ---");

        // Para cada pueblo, si es de esa provincia, muestra su nombre
        pueblos.forEach(pueblo -> {
            if (pueblo.getProvincia().equalsIgnoreCase(pobre.getNombre())) {
                System.out.println("- " + pueblo.getNombre());
            }
        });
    }
}
