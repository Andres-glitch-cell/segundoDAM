package practicarEjerciciosAysha;

class HiloTresUno extends Thread {
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

class HiloTresTres extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Buenas noches");
        }
    }
}

public class ejercicioTresDos {
    public static void main(String[] args) {
        HiloTresUno hilo1 = new HiloTresUno();
        HiloTresTres hilo2 = new HiloTresTres();

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
