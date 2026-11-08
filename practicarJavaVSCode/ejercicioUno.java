import java.io.File;

public class ejercicioUno {
    public static void main(String[] args) {
        File rutaDescargas = new File("/home/andfersal/Baixades");

        // ! guardar archivos que hay en la ruta destinada dentro de una array
        File[] arrayDeArchivosDescargas = rutaDescargas.listFiles();
        // ! bucle para recorrer los archivos y mostrar el nombre
        for (File archivo : arrayDeArchivosDescargas) {
            System.out.println(archivo.getName());
        }

    }
}
