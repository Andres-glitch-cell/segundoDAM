package practicarEjerciciosAysha;

class HiloTresUnoTres extends Thread {
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

class HiloTresTresTres extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Buenas noches");
        }
    }
}

public class EjercicioTresTres03 {
    public static void main(String[] args) {
        // Crear dos instancias del hilo
        HiloTresUnoTres hilo1 = new HiloTresUnoTres();
        HiloTresTresTres hilo2 = new HiloTresTresTres();

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
