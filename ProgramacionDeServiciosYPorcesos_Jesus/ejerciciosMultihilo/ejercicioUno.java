
public class ejercicioUno {

    public static void main(String[] args) {
        miPrimerHilo ayshacomeandreses = new miPrimerHilo();
        ayshacomeandreses.start();
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
