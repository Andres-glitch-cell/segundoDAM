package practicarEjerciciosAysha;

class HiloTresUnoDos extends Thread {
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

class HiloTresDosDos extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Buenos días");
        }
    }
}

public class EjercicioTresDos02 {
    public static void main(String[] args) {
        // Crear dos instancias del hilo
        HiloTresUnoDos hilo1 = new HiloTresUnoDos();
        HiloTresDosDos hilo2 = new HiloTresDosDos();

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
