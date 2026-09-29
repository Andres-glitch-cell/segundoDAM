public class ejercicioUno {

    public static void main(String[] args) {
        miPrimerHilo objetoMiPrimerHilo = new miPrimerHilo();
        objetoMiPrimerHilo.start();
    }
}

class miPrimerHilo extends Thread {
    @Override
    public void run() {
        System.out.println("Hola, soy un hilo");
    }
}

class miSegundoHilo extends Thread {

}
