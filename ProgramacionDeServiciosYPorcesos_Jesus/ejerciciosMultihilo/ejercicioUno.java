
public class ejercicioUno {

    public static void main(String[] args) {
        miPrimerHiloUno ayshacomeandreses = new miPrimerHiloUno();
        miSegundoHiloUno segundoHilo = new miSegundoHiloUno();
        ayshacomeandreses.start();
        segundoHilo.start();
    }
}

class miPrimerHiloUno extends Thread {

    @Override
    public void run() {
        System.out.println("Hola, soy un hilo");
    }
}

class miSegundoHiloUno extends Thread {
    @Override
    public void run() {
        System.out.println("Hola, soy el segundo hilo");
    }
}
