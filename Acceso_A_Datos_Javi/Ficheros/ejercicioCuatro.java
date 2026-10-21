
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ejercicioCuatro {

    public static void main(String[] args) {

        File ficheroQuijote = new File("elquijote.txt");

        try (Scanner lectorArchivo = new Scanner(ficheroQuijote); Scanner lectorTeclado = new Scanner(System.in)) {

            String textoCompleto = "";

            // !!!! Comprobamos que haya lineas en el archivo, pasamos este contenido, mostramos y vamos concatenando hasta encontrar la correcta
            while (lectorArchivo.hasNextLine()) {
                String contenidoLinea = lectorArchivo.nextLine();
                System.out.println(contenidoLinea);
                textoCompleto = textoCompleto + contenidoLinea + " ";
            }

            System.out.print("Introduce una palabra: ");
            String palabraBuscada = lectorTeclado.nextLine().toLowerCase();

            textoCompleto = textoCompleto.toLowerCase();

            int contadorApariciones = 0;
            String palabraActual = "";

            for (int i = 0; i < textoCompleto.length(); i++) {
                char caracterActual = textoCompleto.charAt(i);
                if (Character.isLetter(caracterActual)) {
                    palabraActual = palabraActual + caracterActual;
                } else {
                    if (palabraActual.equals(palabraBuscada)) {
                        contadorApariciones++;
                    }
                    palabraActual = "";
                }
            }

            if (palabraActual.equals(palabraBuscada)) {
                contadorApariciones++;
            }

            try (FileWriter escritorArchivo = new FileWriter("resultado.txt")) {

                escritorArchivo.write("Palabra buscada: " + palabraBuscada + "\n");
                escritorArchivo.write("Numero de veces que aparece: " + contadorApariciones);
            }

            System.out.println("La palabra aparece " + contadorApariciones + " veces.");
            System.out.println("Resultado guardado en resultado.txt");

        } catch (IOException e) {

            System.out.println("Error al leer o escribir un fichero.");

        } catch (Exception e) {

            System.out.println("Ha ocurrido un error inesperado.");

        } finally {

            System.out.println("La ejecucion ha terminado.");
        }
    }
}
