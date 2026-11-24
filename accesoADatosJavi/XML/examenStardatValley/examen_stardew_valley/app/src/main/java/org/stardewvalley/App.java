package org.stardewvalley;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.stardewvalley.modelos.Habitante;
import org.stardewvalley.modelos.HabitanteListWrapper;
import org.stardewvalley.modelos.InventarioListWrapper;
import org.stardewvalley.modelos.Item;
import org.stardewvalley.modelos.Relacion;
import org.stardewvalley.modelos.RelacionesListWrapper;

public class App {
    public static void main(String[] args) {
        try {

            File localizacionesXML = new File("localizaciones.xml");

            File inventarioXML = new File("inventario.xml");
            JAXBContext claseInventarioUnmarshal = JAXBContext.newInstance(InventarioListWrapper.class);
            Unmarshaller leeInventarioXML = claseInventarioUnmarshal.createUnmarshaller();
            InventarioListWrapper wrapperInventario = (InventarioListWrapper) leeInventarioXML.unmarshal(inventarioXML);
            ArrayList<Item> arrayListItems = new ArrayList<>(wrapperInventario.getItem());

            File habitantesXML = new File("habitantes.xml");
            JAXBContext claseHabitantesUnmarshal = JAXBContext.newInstance(HabitanteListWrapper.class);
            Unmarshaller leeHabitantesXML = claseHabitantesUnmarshal.createUnmarshaller();
            HabitanteListWrapper wrapperHabitantes = (HabitanteListWrapper) leeHabitantesXML.unmarshal(habitantesXML);
            ArrayList<Habitante> arrayListHabitantes = new ArrayList<>(wrapperHabitantes.getHabitante());

            File relacionesXML = new File("relaciones.xml");
            JAXBContext claseRelacionesUnmarshal = JAXBContext.newInstance(RelacionesListWrapper.class);
            Unmarshaller leeRelacionesXML = claseRelacionesUnmarshal.createUnmarshaller();
            RelacionesListWrapper wrapperRelaciones = (RelacionesListWrapper) leeRelacionesXML.unmarshal(relacionesXML);
            ArrayList<Relacion> arrayListRelaciones = new ArrayList<>(wrapperRelaciones.getRelacion());

            Scanner sc = new Scanner(System.in);
            System.out.println("---Añadir un objeto---");
            System.out.print("\nNombre del objeto a buscar: ");
            String nombreObjetoInventario = sc.nextLine().trim();

            // [IMPORTANT] 1.
            boolean encontrado = false;

            for (Item item : arrayListItems) {
                if (item.getNombre().equalsIgnoreCase(nombreObjetoInventario)) {
                    encontrado = true;
                    System.out.println("\nEl item existe, es el siguiente:");
                    System.out.println("Nombre: " + item.getNombre());
                    System.out.println("Cantidad: " + item.getCantidad());
                }
            }

            if (!encontrado) {
                System.out.println("No se ha encontrado ningún item con ese nombre.");
                System.out.println("\n Procedemos a añadirlo");
                System.out.print("Nombre del objeto: ");
                nombreObjetoInventario = sc.nextLine().trim();
                System.out.print("Cantidad: ");
                int cantidadObjetoInventario = sc.nextInt();

                Item nuevoItem = new Item();
                nuevoItem.setNombre(nombreObjetoInventario);
                nuevoItem.setCantidad(cantidadObjetoInventario);
                arrayListItems.add(nuevoItem);

                JAXBContext contextoGuardarItem = JAXBContext.newInstance(Item.class);

                Marshaller m = contextoGuardarItem.createMarshaller();

                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                Item wrapperGuardarObjeto = new Item();
                Item wrapperGuardarNombre = new Item();

                wrapperGuardarObjeto.setCantidad(cantidadObjetoInventario);
                wrapperGuardarNombre.setNombre(nombreObjetoInventario);

                // Escribimos los pacientes en el archivo XML
                m.marshal(wrapperGuardarObjeto, inventarioXML);
                m.marshal(wrapperGuardarNombre, inventarioXML);

                System.out.println("Item añadido correctamente");

                // [IMPORTANT] 2.

                System.out.println("Introduce a la persona que quieres ver sus regalos: ");
                String personaIntroducida = sc.nextLine();

                for (Habitante habitantes : arrayListHabitantes) {
                    if (habitantes.getNombre().equals(personaIntroducida)) {
                        System.out.println("A la persona: " + habitantes.getNombre());
                        System.out.println("Tiene estos regalos: " + "\n" + habitantes.getRegalo());
                    } else {
                        System.out.println("No has puesto la misma persona");
                    }
                }

                // [IMPORTANT] 3.
                System.out.println("Cual es tu nombre?");
                String nombreHabitanteUbicacion = sc.nextLine();
                System.out.println("Cual es tu ubicacion?");
                String ubicacionHabitanteUbicacion = sc.nextLine();
                for (Habitante ubicacionPersona : arrayListHabitantes) {
                    if (ubicacionPersona.getNombre().equals(nombreHabitanteUbicacion)
                            && ubicacionPersona.getUbicacion().equals(ubicacionHabitanteUbicacion)) {
                        System.out.println("Te encuentras en la ubicacion " + ubicacionPersona.getUbicacion());
                    } else {
                        System.out.println("No te encuentras en la ubicación");
                    }

                }

                // [IMPORTANT] 4.
                System.out.println("Nombre: ");
                String nombreHabitante = sc.nextLine();
                System.out.println("Profesion: ");
                String profesionHabitante = sc.nextLine();
                System.out.println("Edad:");
                int edadHabitante = sc.nextInt();
                System.out.println("Ubicacion: ");
                String ubicacionHabitante = sc.nextLine();

                for (Habitante habitantesUbicacion : arrayListHabitantes) {
                    if (habitantesUbicacion.getUbicacion().equals(ubicacionHabitante)) {
                        Habitante añadirHabitante = new Habitante();

                        añadirHabitante.setNombre(nombreHabitante);
                        añadirHabitante.setProfesion(profesionHabitante);
                        añadirHabitante.setEdad(edadHabitante);
                        añadirHabitante.setUbicacion(ubicacionHabitante);
                        arrayListHabitantes.add(añadirHabitante);

                        // Creamos el contexto JAXB indicando la clase Pacientes
                        JAXBContext contextoGuardarHabitante = JAXBContext.newInstance(HabitanteListWrapper.class);

                        // Creamos el objeto que permite escribir el XML
                        Marshaller marshaller = contextoGuardarHabitante.createMarshaller();

                        // Indicamos que queremos que el XML tenga formato legible
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                        // Creamos el objeto contenedor de pacientes
                        Habitante wrapperGuardar = new Habitante();

                        // Introducimos la lista de pacientes dentro del contenedor
                        wrapperGuardar.setNombre(nombreHabitante);
                        wrapperGuardar.setProfesion(profesionHabitante);
                        wrapperGuardar.setEdad(edadHabitante);
                        wrapperGuardar.setUbicacion(ubicacionHabitante);

                        // Escribimos los pacientes en el archivo XML
                        marshaller.marshal(wrapperGuardar, habitantesXML);

                        System.out.println("Habitante insertado correctamente.");

                    }
                }

                // [IMPORTANT] 5.
                System.out.println("Introduce una cantidad de amistad: ");
                int cantidadCorazonesAmistad = sc.nextInt();
                if (cantidadCorazonesAmistad < 1 || cantidadCorazonesAmistad > 10) {
                    System.out.println("La cantidad máxima de corazones es 10 y la mínima es 1");
                } else {
                    // [IMPORTANT]: Aquí te pongo el nombre como me dijiste en ese momento del xamen
                    // que solo querías el nombre
                    for (Relacion mostrarRelacionAmistad : arrayListRelaciones) {
                        if (mostrarRelacionAmistad
                                .equals(mostrarRelacionAmistad.getCorazones() == cantidadCorazonesAmistad)) {
                            System.out.println("Nombre: " + mostrarRelacionAmistad.getHabitante());
                        }
                    }
                }
            }

            // [IMPORTANT] 7.

            System.out.println("Nombre del habitante con el que te quieres casar");
            String nombreCasado = sc.nextLine().trim();

            for (Relacion cantidadDeCorazones : arrayListRelaciones) {
                if (cantidadDeCorazones.getHabitante().equals(nombreCasado)
                        && cantidadDeCorazones.getCorazones() == 10) {
                    cantidadDeCorazones.setCasado(true);
                } else {
                    cantidadDeCorazones.setCasado(false);
                }
            }

        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }
}
