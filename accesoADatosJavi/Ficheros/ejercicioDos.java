
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ejercicioDos {

    public static void main(String[] args) {

        File archivoNumeros = new File("numeros.txt");

        // TODO: Entra a un try directamente realizando y creando el objeto lectorArchivo del archivoNumeros creado.
        try (Scanner lectorArchivo = new Scanner(archivoNumeros)) {

            while (lectorArchivo.hasNextLine()) {
                String contenidoLinea = lectorArchivo.nextLine();

                try {
                    int numeroLeido = Integer.parseInt(contenidoLinea);
                    int resultadoCuadrado = numeroLeido * numeroLeido;

                    System.out.println("Numero: " + numeroLeido + " - Cuadrado: " + resultadoCuadrado);

                } catch (NumberFormatException e) {

                    System.out.println("Error: la linea no contiene un numero entero valido: " + contenidoLinea);
                }
            }

        } catch (FileNotFoundException e) {

            System.out.println("Error: no se encuentra el fichero.");

        } catch (Exception e) {

            System.out.println("Ha ocurrido un error inesperado.");

        } finally {
            System.out.println("La ejecucion ha terminado.");
        }
    }
}
