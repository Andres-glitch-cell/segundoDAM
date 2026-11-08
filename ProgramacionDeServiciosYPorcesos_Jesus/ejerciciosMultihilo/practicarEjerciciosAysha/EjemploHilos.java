package practicarEjerciciosAysha;

class HiloEjemplo extends Thread {
    private static void esperarUnSegundo() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + ": Iteración " + i);
            try {
                esperarUnSegundo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class EjemploHilos {
    public static void main(String[] args) {
        // Crear dos instancias del hilo
        HiloEjemplo hilo1 = new HiloEjemplo();
        HiloEjemplo hilo2 = new HiloEjemplo();
        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        System.out.println("Ambos hilos han terminado.");
    }
}
