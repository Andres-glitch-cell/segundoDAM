
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Soy una IA y este programa copia el contenido de un fichero a otro.
public class ejercicioTres {

    public static void main(String[] args) {

        String nombreFicheroEntrada = "origen.txt";
        String nombreFicheroSalida = "copia.txt";

        try (Scanner lectorArchivo = new Scanner(new File(nombreFicheroEntrada)); FileWriter escritorArchivo = new FileWriter(nombreFicheroSalida)) {

            while (lectorArchivo.hasNextLine()) {
                String contenidoLinea = lectorArchivo.nextLine();
                escritorArchivo.write(contenidoLinea + "\n");
            }

            System.out.println("El fichero se ha copiado correctamente.");

        } catch (FileNotFoundException e) {

            System.out.println("Error: no se encuentra el fichero de entrada.");

        } catch (IOException e) {

            System.out.println("Error al leer o escribir el fichero.");

        } catch (Exception e) {

            System.out.println("Ha ocurrido un error inesperado.");

        } finally {

            System.out.println("La ejecucion ha terminado.");
        }
    }
}
