package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class App {

    // Método principal del programa
    public static void main(String[] args) {

        try {

            // Indicamos dónde están los dos archivos XML
            String rutaMedicos = "app/src/main/resources/medicos.xml";
            String rutaPacientes = "app/src/main/resources/pacientes.xml";

            File archivoMedicos = new File(rutaMedicos);
            File archivoPacientes = new File(rutaPacientes);

            // Creamos el contexto JAXB indicando la clase Medicos
            JAXBContext contextMedicos = JAXBContext.newInstance(Medicos.class);

            // Creamos el objeto que permite leer el XML
            Unmarshaller umMedicos = contextMedicos.createUnmarshaller();

            // Leemos el XML y lo convertimos en un objeto Medicos
            Medicos wrapperMedicos = (Medicos) umMedicos.unmarshal(archivoMedicos);

            // Creamos una lista para almacenar los médicos
            ArrayList<Medico> listaMedicos = new ArrayList<>();

            // Añadimos todos los médicos del XML a nuestra lista
            listaMedicos.addAll(wrapperMedicos.getMedicos());

            // Creamos el contexto JAXB indicando la clase Pacientes
            JAXBContext contextPacientes = JAXBContext.newInstance(Pacientes.class);

            // Creamos el objeto que permite leer el XML
            Unmarshaller umPacientes = contextPacientes.createUnmarshaller();

            // Leemos el XML y lo convertimos en un objeto Pacientes
            Pacientes wrapperPacientes = (Pacientes) umPacientes.unmarshal(archivoPacientes);

            // Creamos una lista para almacenar los pacientes
            ArrayList<Paciente> listaPacientes = new ArrayList<>();

            // Añadimos todos los pacientes del XML a nuestra lista
            listaPacientes.addAll(wrapperPacientes.getPacientes());

            // Creamos el Scanner para leer las opciones del usuario
            Scanner teclado = new Scanner(System.in);

            // Inicializamos la opción para poder entrar en el while
            int opcion = 0;

            // Repetimos el menú hasta que el usuario elija salir
            while (opcion != 4) {

                System.out.println("\n--- GESTIÓN DEL HOSPITAL ---");
                System.out.println("1. Contar pacientes por médico");
                System.out.println("2. Buscar paciente");
                System.out.println("3. Insertar nuevo paciente");
                System.out.println("4. Salir");
                System.out.print("Elige una opción: ");

                // Leemos la opción elegida
                opcion = Integer.parseInt(teclado.nextLine());

                // Ejecutamos la opción seleccionada
                switch (opcion) {

                    case 1:

                        // Recorremos todos los médicos
                        for (Medico medico : listaMedicos) {

                            // Creamos un contador para el médico actual
                            int contador = 0;

                            // Recorremos todos los pacientes
                            for (Paciente paciente : listaPacientes) {

                                // Comprobamos si el número de doctor coincide
                                if (paciente.getDoctor().equals(medico.getNumeroDoctor())) {

                                    // Si coincide, aumentamos el contador
                                    contador++;
                                }
                            }

                            // Mostramos el médico y el número de pacientes que tiene
                            System.out.println("\nMédico: " + medico.getNombre());
                            System.out.println("Especialidad: " + medico.getEspecialidad());
                            System.out.println("Años experiencia: " + medico.getAniosExperiencia());
                            System.out.println("Número de pacientes: " + contador);
                        }

                        break;

                    case 2:

                        // Pedimos el nombre del paciente
                        System.out.print("Introduce el nombre del paciente: ");
                        String nombreBuscar = teclado.nextLine();

                        // Variable para saber si hemos encontrado al paciente
                        boolean encontrado = false;

                        // Recorremos todos los pacientes
                        for (Paciente paciente : listaPacientes) {

                            // Comprobamos si el nombre coincide
                            if (paciente.getNombre().equalsIgnoreCase(nombreBuscar)) {

                                // Indicamos que hemos encontrado al paciente
                                encontrado = true;

                                // Mostramos todos los datos del paciente
                                System.out.println("\n--- DATOS DEL PACIENTE ---");
                                System.out.println("DNI: " + paciente.getDni());
                                System.out.println("Nombre: " + paciente.getNombre());
                                System.out.println("Fecha de nacimiento: " + paciente.getFechaNacimiento());
                                System.out.println("Ciudad: " + paciente.getCiudad());

                                // Buscamos el médico cuyo número coincide con el del paciente
                                for (Medico medico : listaMedicos) {

                                    if (medico.getNumeroDoctor().equals(paciente.getDoctor())) {

                                        // Mostramos los datos del médico asociado
                                        System.out.println("\n--- DATOS DEL MÉDICO ---");
                                        System.out.println("Número de doctor: " + medico.getNumeroDoctor());
                                        System.out.println("Nombre: " + medico.getNombre());
                                        System.out.println("Especialidad: " + medico.getEspecialidad());
                                        System.out.println("Años de experiencia: " + medico.getAniosExperiencia());
                                    }
                                }
                            }
                        }

                        // Si no se ha encontrado el paciente, mostramos un mensaje
                        if (!encontrado) {
                            System.out.println("No se ha encontrado ningún paciente con ese nombre.");
                        }

                        break;

                    case 3:

                        // Pedimos los datos del nuevo paciente
                        System.out.print("DNI: ");
                        String dni = teclado.nextLine();

                        System.out.print("Nombre: ");
                        String nombre = teclado.nextLine();

                        System.out.print("Fecha de nacimiento: ");
                        String fechaNacimiento = teclado.nextLine();

                        System.out.print("Ciudad: ");
                        String ciudad = teclado.nextLine();

                        System.out.print("Número de doctor asignado: ");
                        String numeroDoctor = teclado.nextLine();

                        // Variable para comprobar si existe el médico
                        boolean existeMedico = false;

                        // Recorremos la lista de médicos para comprobar el número
                        for (Medico medico : listaMedicos) {

                            // Comprobamos si el número introducido pertenece a un médico
                            if (medico.getNumeroDoctor().equals(numeroDoctor)) {
                                existeMedico = true;
                                break;
                            }
                        }

                        // Solo insertamos el paciente si existe el médico
                        if (existeMedico) {

                            // Creamos el nuevo paciente
                            Paciente nuevoPaciente = new Paciente();

                            // Introducimos sus datos
                            nuevoPaciente.setDni(dni);
                            nuevoPaciente.setNombre(nombre);
                            nuevoPaciente.setFechaNacimiento(fechaNacimiento);
                            nuevoPaciente.setCiudad(ciudad);
                            nuevoPaciente.setDoctor(numeroDoctor);

                            // Añadimos el paciente a la lista
                            listaPacientes.add(nuevoPaciente);

                            // Creamos el contexto JAXB indicando la clase Pacientes
                            JAXBContext contextGuardar = JAXBContext.newInstance(Pacientes.class);

                            // Creamos el objeto que permite escribir el XML
                            Marshaller m = contextGuardar.createMarshaller();

                            // Indicamos que queremos que el XML tenga formato legible
                            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                            // Creamos el objeto contenedor de pacientes
                            Pacientes wrapperGuardar = new Pacientes();

                            // Introducimos la lista de pacientes dentro del contenedor
                            wrapperGuardar.setPacientes(listaPacientes);

                            // Escribimos los pacientes en el archivo XML
                            m.marshal(wrapperGuardar, archivoPacientes);

                            System.out.println("Paciente insertado correctamente.");

                        } else {

                            // Si el médico no existe, no insertamos el paciente
                            System.out.println("No existe ningún médico con ese número.");
                            System.out.println("No se ha insertado el paciente.");
                        }

                        break;

                    case 4:

                        // Terminamos el programa
                        System.out.println("Saliendo del programa...");
                        break;

                    default:

                        // Avisamos si se introduce una opción incorrecta
                        System.out.println("Opción no válida.");
                        break;
                }
            }

            // Cerramos el Scanner
            teclado.close();

        } catch (Exception e) {

            // Mostramos el error si ocurre algún problema
            e.printStackTrace();
        }
    }
}