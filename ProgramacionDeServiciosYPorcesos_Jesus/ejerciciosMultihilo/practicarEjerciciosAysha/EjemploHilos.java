package practicarEjerciciosAysha;

class HiloEjemplo extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + ": Iteración " + i);
            try {
                Thread.sleep(1000); // Dormir durante 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
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
