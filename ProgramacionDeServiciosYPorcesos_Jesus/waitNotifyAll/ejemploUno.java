public class ejemploUno {

    // [IMPORTANT]: El constructor vacío se puede quitar si quieres
    public ejemploUno() {
    }

    /*
     * Si no es jefe, el empleado va a quedar esperando a que llegue el jefe
     * Se hace wait en el hilo que está corriendo y se bloquea, hasta que
     * se le avise que ya puede saludar (notifyAll)
     */
    public synchronized void saludoEmpleado(String nombre) {
        try {
            wait();

            System.out.println("\n" + nombre.toUpperCase() + "-: Buenos días mi amado jefe.");

        } catch (InterruptedException e) {
            System.err.println("Ha salido un error " + e);
        }
    }

    // El hilo jefe saluda y luego avisa (notifyAll) a los empleados
    // durmientes (wait) para que le saluden
    // El notifyAll despierta a todos los hilos que estén bloqueados
    // con el wait
    public synchronized void saludoJefe(String nombre) {

        System.out.println("\n****** " + nombre + "-: Buenos días mis queridos empleados !! .******");

        // Transcurridos 5 segundos, el jefe despierta a sus empleados
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        notifyAll();
    }

    // MÉTODO PRINCIPAL
    public static void main(String[] args) {

        // Creamos un objeto de la clase ejemploUno
        ejemploUno saludo = new ejemploUno();

        // Creamos el hilo del empleado 1
        Thread empleado1 = new Thread(() -> {
            saludo.saludoEmpleado("Juan");
        });

        // Creamos el hilo del empleado 2
        Thread empleado2 = new Thread(() -> {
            saludo.saludoEmpleado("Pedro");
        });

        // Creamos el hilo del jefe
        Thread jefe = new Thread(() -> {
            saludo.saludoJefe("Carlos");
        });

        // Arrancamos los empleados
        empleado1.start();
        empleado2.start();

        // Arrancamos al jefe
        jefe.start();
    }
}
