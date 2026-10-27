package practicarEjerciciosAysha;

class HiloTresUno extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + ": Iteración " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

class HiloTresDos extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Buenos días");
        }
    }
}

public class ejercicioTresDos {
    public static void main(String[] args) {
        // Crear dos instancias del hilo
        HiloTresUno hilo1 = new HiloTresUno();
        HiloTresDos hilo2 = new HiloTresDos();

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
