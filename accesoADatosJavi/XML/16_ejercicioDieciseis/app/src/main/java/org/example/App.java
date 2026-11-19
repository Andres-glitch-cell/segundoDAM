package org.example; // Paquete en el que está la clase

import java.io.File; // Para representar los archivos XML
import java.util.ArrayList; // Para guardar las listas de médicos y pacientes
import java.util.Scanner; // Para leer lo que escribe el usuario por teclado

import javax.xml.bind.JAXBContext; // Punto de entrada de JAXB: sabe qué clases se convierten a XML
import javax.xml.bind.Marshaller; // Convierte objetos Java en XML (escribir)
import javax.xml.bind.Unmarshaller; // Convierte XML en objetos Java (leer)

public class App { // Clase principal del programa

    public static void main(String[] args) throws Exception { // Método que se ejecuta al arrancar; throws Exception evita tener que poner try-catch
        File archivoMedicos = new File("medicos.xml"); // Objeto File que apunta a medicos.xml
        File archivoPacientes = new File("pacientes.xml"); // Objeto File que apunta a pacientes.xml

        // Leer los XML
        JAXBContext claseMedicos = JAXBContext.newInstance(MedicoListWrapper.class); // Contexto JAXB para el XML de médicos (raíz <medicos>)
        JAXBContext clasePacientes = JAXBContext.newInstance(PacienteListWrapper.class); // Contexto JAXB para el XML de pacientes (raíz <pacientes>)

        Unmarshaller leeMedicosXML = claseMedicos.createUnmarshaller(); // Crea el objeto que lee medicos.xml
        MedicoListWrapper wrapperMedicos = (MedicoListWrapper) leeMedicosXML.unmarshal(archivoMedicos); // Lee el XML y lo convierte en un objeto wrapper (hay que hacer cast porque unmarshal devuelve Object)
        ArrayList<Medico> medicos = new ArrayList<>(wrapperMedicos.getMedicos()); // Copia la lista de médicos del wrapper a un ArrayList

        Unmarshaller deserielizacionDos = clasePacientes.createUnmarshaller(); // Crea el objeto que lee pacientes.xml
        PacienteListWrapper wrapperPacientes = (PacienteListWrapper) deserielizacionDos.unmarshal(archivoPacientes); // Lee el XML y lo convierte en un wrapper de pacientes
        ArrayList<Paciente> pacientes = new ArrayList<>(wrapperPacientes.getPacientes()); // Copia la lista de pacientes del wrapper a un ArrayList

        Scanner sc = new Scanner(System.in); // Scanner para leer del teclado

        // 1) Número de pacientes por médico
        System.out.println("--- Pacientes por médico ---"); // Título de la sección
        for (int i = 0; i < medicos.size(); i++) { // Recorre todos los médicos con el índice i
            Medico medico = medicos.get(i); // Saca el médico de la posición i
            int total = 0; // Contador de pacientes de este médico, empieza en 0
            for (int j = 0; j < pacientes.size(); j++) { // Recorre todos los pacientes con el índice j
                Paciente paciente = pacientes.get(j); // Saca el paciente de la posición j
                if (paciente.getDoctor() == medico.getNumero()) { // Si el doctor asignado al paciente es este médico...
                    total++; // ...suma uno al contador
                }
            }
            System.out.println(medico.getNombre() + ": " + total + " paciente(s)"); // Muestra el médico y su total de pacientes
        }

        // 2) Buscar paciente por nombre y mostrar sus datos y los de su médico
        System.out.print("\nNombre del paciente: "); // Pide el nombre (print no salta de línea)
        String nombre = sc.nextLine().trim(); // Lee la línea escrita y quita espacios al principio y al final

        pacientes.forEach(paciente -> { // Para cada paciente de la lista...
            if (paciente.getNombre().equalsIgnoreCase(nombre)) { // ...si su nombre coincide con el buscado (sin distinguir mayúsculas)
                System.out.println("DNI: " + paciente.getDni()); // Muestra el DNI
                System.out.println("Nombre: " + paciente.getNombre()); // Muestra el nombre
                System.out.println("Fecha de nacimiento: " + paciente.getFechaNacimiento()); // Muestra la fecha de nacimiento
                System.out.println("Ciudad: " + paciente.getCiudad()); // Muestra la ciudad

                medicos.forEach(medico -> { // Para cada médico de la lista...
                    if (medico.getNumero() == paciente.getDoctor()) { // ...si es el médico asignado a este paciente
                        System.out.println("Médico nº " + medico.getNumero() + ": " + medico.getNombre() // Muestra número y nombre del médico
                                + ", " + medico.getEspecialidad() // su especialidad
                                + ", " + medico.getExperiencia() + " años de experiencia"); // y sus años de experiencia
                    }
                });
            }
        });

        // 3) Insertar un nuevo paciente (solo si existe el médico)
        System.out.println("\n--- Nuevo paciente ---"); // Título de la sección
        System.out.print("DNI: "); // Pide el DNI
        String dni = sc.nextLine().trim(); // Lo lee y lo limpia
        System.out.print("Nombre: "); // Pide el nombre
        String nombreNuevo = sc.nextLine().trim(); // Lo lee (se llama nombreNuevo para no chocar con la variable nombre de antes)
        System.out.print("Fecha de nacimiento: "); // Pide la fecha
        String fecha = sc.nextLine().trim(); // La lee
        System.out.print("Ciudad: "); // Pide la ciudad
        String ciudad = sc.nextLine().trim(); // La lee
        System.out.print("Número de doctor: "); // Pide el número del médico asignado
        int numDoctor = Integer.parseInt(sc.nextLine().trim()); // Lo lee como texto y lo convierte a int

        boolean existe = medicos.stream().anyMatch(medico -> medico.getNumero() == numDoctor); // true si algún médico de la lista tiene ese número

        if (!existe) { // Si el médico NO existe...
            System.out.println("El médico " + numDoctor + " no existe. No se inserta el paciente."); // ...avisa
            return; // ...y termina el programa sin insertar nada
        }

        pacientes.add(new Paciente(dni, nombreNuevo, fecha, ciudad, numDoctor)); // Crea el paciente con los datos leídos y lo añade a la lista

        PacienteListWrapper wrapper = new PacienteListWrapper(); // Crea un wrapper nuevo para escribir el XML
        wrapper.setPacientes(pacientes); // Le pasa la lista completa de pacientes (con el nuevo incluido)
        Marshaller marshaller = ctxPacientes.createMarshaller(); // Crea el objeto que escribe XML
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Activa la salida con sangrías para que el XML sea legible
        marshaller.marshal(wrapper, archivoPacientes); // Escribe el wrapper en pacientes.xml, sobrescribiendo el archivo

        System.out.println("Paciente insertado."); // Confirma al usuario que se ha guardado
    }
}
