import java.util.*;

public class menuConMetodos {
    int[] arraydiezNumeros = new int[10];
    int[] arraysMayoresA5 = new int[10];
    int[] arraysMenoresA5 = new int[10];

    // Accion para inicializar matriz
    public void inicializarMatriz(int[] arraydiezNumeros) {
        Scanner sc = new Scanner(System.in);
        int numeroUsuario = 0;
        for (int i = 0; i < arraydiezNumeros.length; i++) {
            System.out.println("Introduce un número");
            numeroUsuario = sc.nextInt();
            if (numeroUsuario % 2 == 0) {
                arraydiezNumeros[i] = numeroUsuario;
            } else {
                i--;
            }
        }
        System.out.println("Numeros añadidos correctamente!");
    }

    // Accion para decir por cada número de la array si es mayor a 5
    public void accionMayorQueCinco(int[] arraydiezNumeros, int[] arraysMayoresA5, int j) {
        j = 0;
        for (int i = 0; i < arraydiezNumeros.length; i++) {
            if (arraydiezNumeros[i] > 5) {
                arraysMayoresA5[j] = arraydiezNumeros[i];
                j++;
            }
        }
        System.out.println("Numeros añadidos correctamente");
    }

    // Accion para decir por cada número de la array si es menor a 5
    public void accionMenorQueCinco(int[] arraydiezNumeros, int[] arraysMenoresA5, int j) {
        j = 0;
        for (int i = 0; i < arraysMenoresA5.length; i++) {
            if (arraydiezNumeros[i] < 5) {
                arraysMenoresA5[j] = arraydiezNumeros[i];
                j++;
            }
        }
        System.out.println("Numeros añadidos correctamente");
    }

    // Función para sumar todos los numeros > 5
    public int sumaTotalNumeros(int[] arraysMayoresA5, int total) {
        for (int i = 0; i < arraysMayoresA5.length; i++) {
            total += arraysMayoresA5[i];
        }
        System.out.println("Total de la suma de los numeros mayores a 5 son: " + total);
        return total;
    }

    public static void main(String[] args ) {
        menuConMetodos objetos = new menuConMetodos();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        int[] arraydiezNumeros = new int[10];
        int[] arraysMayoresA5 = new int[10];
        int[] arraysMenoresA5 = new int[10];
        int j = 0;

        do {
            try {
                System.out.println("1. Inicializar array");
                System.out.println("2. Guardar números > 5");
                System.out.println("3. GUardar números < 5");
                System.out.println("4. Mostrar suma de los números > 5");
                System.out.println("5. Salir");
                System.out.println("Elige una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        objetos.inicializarMatriz(arraydiezNumeros);
                        break;
                    case 2:
                        objetos.accionMayorQueCinco(arraydiezNumeros, arraysMayoresA5, j);
                        break;
                    case 3:
                        objetos.accionMenorQueCinco(arraydiezNumeros, arraysMayoresA5, j);
                        break;
                    case 4:
                        objetos.accionMenorQueCinco(arraydiezNumeros, arraysMenoresA5, j);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error " + e);
            }
            String entrada;






            
        } while (opcion != 4 ||  );
    }
}
