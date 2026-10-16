package paginaDieciSeis;

import java.util.Random;

public class ejercicioDos {
    public static void main(String[] args) {
        /*
         * 2. Modifica el programa anterior para que la ejecución de cada hilo sea
         * completa, es decir,
         * hasta que no finalice completamente la ejecución el resto de hilos del
         * programa deberán
         * esperarse.
         */

        // [TODO]: Hacemos el objeto del Primer Hilo
        miPrimerHilo objetoMiPrimeroHilo = new miPrimerHilo();
        objetoMiPrimeroHilo.start();
        try {
            objetoMiPrimeroHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        miSegundoHilo objetoMiSegundoHilo = new miSegundoHilo();
        objetoMiSegundoHilo.start();
        try {
            objetoMiSegundoHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        miTercerHilo objetoMiTercerHilo = new miTercerHilo();
        objetoMiTercerHilo.start();
        try {
            objetoMiTercerHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin de la ejecución de los tres hilos");
    }

    // [IMPORTANT]: Mi primer Hilo
    static class miPrimerHilo extends Thread {

        @Override
        public void run() {
            Random random = new Random();
            int numero = random.nextInt(1, 9);
            System.out.println("El número del primer hilo random es: " + numero);
        }

    }

    static class miSegundoHilo extends Thread {

        @Override
        public void run() {

            Random random = new Random();
            int numero = random.nextInt(1, 8001);
            System.out.println("El número random del segundo hilo es: " + numero);
        }
    }

    static class miTercerHilo extends Thread {

        @Override
        public void run() {
            Random random = new Random();
            int numero = random.nextInt(-100, 11);
            System.out.println("El número del tercer hilo random es: " + numero);
        }
    }

}
