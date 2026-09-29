import java.io.File;

public class ejercicioUno {
    public static void main(String[] args) {
        File rutaDescargas = new File("/home/andfersal/Baixades");

        // ! guardar archivos que hay en la ruta destinada dentro de una array
        File[] arrayDeArchivosDescargas = rutaDescargas.listFiles();
        // ! bucle para recorrer los archivos y mostrar el nombre
        for (int i = 0; i < arrayDeArchivosDescargas.length; i++) {
            System.out.println(arrayDeArchivosDescargas[i].getName());
        }

    }
}