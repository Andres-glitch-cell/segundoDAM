package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class App {

    public static void main(String[] args) {
        try {
            // Busca el archivo XML de los videojuegos
            File archivoXML = new File("app/src/main/resources/Videojuegos.xml");

            // Prepara JAXB para trabajar con la clase Videojuegos
            JAXBContext context = JAXBContext.newInstance(Videojuegos.class);

            // Crea el objeto que permite leer el XML
            Unmarshaller um = context.createUnmarshaller();

            // Convierte el XML en un objeto Videojuegos
            Videojuegos wrapper = (Videojuegos) um.unmarshal(archivoXML);

            // Crea una lista vacía para guardar los videojuegos
            ArrayList<Videojuego> videojuegos = new ArrayList<>();

            // Añade todos los videojuegos del XML a la lista
            videojuegos.addAll(wrapper.getVideojuegos());

            Scanner teclado = new Scanner(System.in);

            // Declara el objeto que permite guardar los datos en el XML
            Marshaller m;

            // Valor inicial para entrar en el while
            int opcion = 0;

            // Muestra el menú hasta que se elija salir
            while (opcion != 4) {
                System.out.println("\n--- GESTIÓN DE VIDEOJUEGOS ---");
                System.out.println("1. Mostrar todos los videojuegos");
                System.out.println("2. Insertar un videojuego");
                System.out.println("3. Eliminar un videojuego por título");
                System.out.println("4. Salir");
                System.out.print("Elige una opción: ");

                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {

                    case 1:
                        // Recorre todos los videojuegos y los muestra
                        for (Videojuego videojuego : videojuegos) {
                            System.out.println(
                                    "Título: " + videojuego.getTitulo()
                                            + ", Año: " + videojuego.getAnio()
                                            + ", Desarrollador: " + videojuego.getDesarrollador()
                                            + ", Género: " + videojuego.getGenero()
                                            + ", Plataformas: " + videojuego.getPlataformas());
                        }
                        break;

                    case 2:
                        // Pide los datos del nuevo videojuego
                        System.out.print("Título: ");
                        String titulo = teclado.nextLine();

                        System.out.print("Año: ");
                        String anio = teclado.nextLine();

                        System.out.print("Desarrollador: ");
                        String desarrollador = teclado.nextLine();

                        System.out.print("Género: ");
                        String genero = teclado.nextLine();

                        System.out.print("Plataformas separadas por comas: ");
                        String plataformasTexto = teclado.nextLine();

                        // Separa las plataformas y las guarda en un ArrayList
                        ArrayList<String> plataformas = new ArrayList<>();
                        String[] partes = plataformasTexto.split(",");

                        for (String plataforma : partes) {
                            plataformas.add(plataforma.trim());
                        }

                        // Crea el videojuego y lo añade a la lista
                        Videojuego videojuego = new Videojuego(
                                titulo, anio, desarrollador, genero, plataformas);

                        videojuegos.add(videojuego);

                        // Crea el objeto que permite guardar los datos en el XML
                        m = context.createMarshaller();

                        // Organiza el XML para que sea más fácil de leer
                        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                        // Actualiza la lista de videojuegos
                        wrapper.setVideojuegos(videojuegos);

                        // Guarda los cambios en el XML
                        m.marshal(wrapper, archivoXML);

                        System.out.println("Videojuego añadido correctamente.");
                        break;

                    case 3:
                        // Pide el título del videojuego que se quiere eliminar
                        System.out.print("Título del videojuego que quieres eliminar: ");
                        String tituloEliminar = teclado.nextLine();

                        boolean eliminado = false;

                        // Busca el videojuego por su título
                        for (int i = 0; i < videojuegos.size(); i++) {
                            if (videojuegos.get(i).getTitulo().equalsIgnoreCase(tituloEliminar)) {
                                videojuegos.remove(i);
                                eliminado = true;
                                break;
                            }
                        }

                        if (eliminado) {
                            // Crea el objeto que permite guardar los datos en el XML
                            m = context.createMarshaller();

                            // Guarda el XML ordenado y con saltos de línea para facilitar su lectura
                            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                            // Actualiza la lista de videojuegos
                            wrapper.setVideojuegos(videojuegos);

                            // Guarda los cambios en el XML
                            m.marshal(wrapper, archivoXML);

                            System.out.println("Videojuego eliminado correctamente.");
                        } else {
                            System.out.println("No se ha encontrado ese videojuego.");
                        }
                        break;

                    case 4:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                        break;
                }
            }

            teclado.close();

        } catch (Exception e) {
            // Muestra el error si ocurre algún problema
            e.printStackTrace();
        }
    }
}