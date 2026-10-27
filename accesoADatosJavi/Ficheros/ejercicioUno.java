
import java.io.File;

public class ejercicioUno {

    public static void main(String[] args) {

        File directorioPersonalizado = new File("C:\\Users\\Andrés\\Downloads");
        File directorioActual = new File(".");

        mostrarContenido(directorioPersonalizado);
    }

    public static void mostrarContenido(File directorioPersonalizado) {

        File[] listaElementos = directorioPersonalizado.listFiles();

        if (listaElementos != null) {
            for (int i = 0; i < listaElementos.length; i++) {
                if (listaElementos[i].isDirectory()) {

                    System.out.println("[CARPETA] " + listaElementos[i].getName());
                    File[] elementosCarpeta = listaElementos[i].listFiles();

                    if (elementosCarpeta != null) {
                        for (int j = 0; j < elementosCarpeta.length; j++) {

                            System.out.print("\t");

                            if (elementosCarpeta[j].isDirectory()) {
                                System.out.println("[CARPETA] " + elementosCarpeta[j].getName());
                            } else {
                                System.out.println("[FICHERO] " + elementosCarpeta[j].getName());
                            }
                        }
                    }
                } else {
                    System.out.println("[FICHERO] " + listaElementos[i].getName());

                }
            }
        } else {
            System.out.println("No se puede acceder al directorio.");
        }
    }
}
