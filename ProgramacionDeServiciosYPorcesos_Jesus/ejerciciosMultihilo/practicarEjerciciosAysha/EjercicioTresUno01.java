package practicarEjerciciosAysha;

class HiloTresDosUno extends Thread {
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

public class EjercicioTresUno01 {
    public static void main(String[] args) {
        // Crear dos instancias del hilo
        HiloTresDosUno hilo1 = new HiloTresDosUno();
        HiloTresDosUno hilo2 = new HiloTresDosUno();

        hilo1.start();
        hilo2.start();

        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            return;
        }

        System.out.println("Ambos hilos han terminado.");
    }
}
