package org.example;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {
        try {
            File archivoXML = new File("centros.xml");

            JAXBContext context = JAXBContext.newInstance(CentroListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            CentroListWrapper listaCentros = (CentroListWrapper) um.unmarshal(archivoXML);

            AtomicInteger totalInformatica = new AtomicInteger(0);

            listaCentros.getCentros().forEach(centro -> {
                System.out.println("\n \n");
                System.out.println("ID Centro: " + centro.getId());
                System.out.println("Nombre: " + centro.getNombre());
                System.out.println("Ciudad: " + centro.getCiudad());
                System.out.println("Dirección: " + centro.getDireccion());
                System.out.println("Código Postal: " + centro.getCodigoPostal());
                System.out.println("Profesores:");

                if (centro.getProfesores() != null) {
                    centro.getProfesores().forEach(profesor -> {
                        System.out.println("  - Nombre: " + profesor.getNombre());
                        System.out.println("    Departamento: " + profesor.getDepartamento());
                        System.out.println("    Asignaturas:");

                        if (profesor.getAsignaturas() != null) {
                            profesor.getAsignaturas().forEach(asignatura -> {
                                System.out.println("      * " + asignatura);
                            });
                        }

                        if (profesor.getDepartamento() != null &&
                            (profesor.getDepartamento().equalsIgnoreCase("Informática") ||
                             profesor.getDepartamento().equalsIgnoreCase("Informatica"))) {
                            totalInformatica.incrementAndGet();
                        }
                    });
                }
            });

            System.out.println("\n==========================================");
            System.out.println("Total de profesores del departamento de Informática: " + totalInformatica.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
