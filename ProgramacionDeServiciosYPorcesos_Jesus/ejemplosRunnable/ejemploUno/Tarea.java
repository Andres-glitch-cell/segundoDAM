public class Tarea implements Runnable {
    private int numero;

    public Tarea(int numero) {
        this.numero = numero;
    }

    @Override
    public void run() {
        System.out.println("Ejecutando tarea " + numero);
    }
}
