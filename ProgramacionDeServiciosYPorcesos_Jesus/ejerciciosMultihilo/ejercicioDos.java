public class ejercicioDos {

    public static void main(String[] args) {
        miPrimerHiloDos primerHilo = new miPrimerHiloDos();
        miSegundoHiloDos segundoHilo = new miSegundoHiloDos();
        primerHilo.start();
        segundoHilo.start();
    }
}

class miPrimerHiloDos extends Thread {
    @Override
    public void run() {
    System.out.println("Hola, soy el primer hilo");
    }
}

class miSegundoHiloDos extends Thread {
    @Override
    public void run() {
        System.out.println("Hola, soy el segundo hilo");
    }
}
