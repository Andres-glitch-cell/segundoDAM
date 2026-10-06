package paginaDieciSeis;

import java.util.Random;

/*
 1. Realiza un programa que cree tres hilos, el primer hilo generará y visualizará en consola
números enteros aleatorios entre 1 y 8, el segundo hilo generará y visualizará en consola los
números existentes entre 1 y 8000, el tercer y último hilo generará y visualizará en consola
números enteros entre 10 y -100. El programa principal, finalmente, deberá mostrar el
mensaje: “Fin de la ejecución de los tres hilos.(no debes de utilizar el método join()).
 */
public class ejercicioUno {

    public static void main(String[] args) {
        // [TODO]: Hacemos el objeto del Primer Hilo
        miPrimerHilo objetoMiPrimeroHilo = new miPrimerHilo();
        objetoMiPrimeroHilo.start();
        miSegundoHilo objetoMiSegundoHilo = new miSegundoHilo();
        objetoMiSegundoHilo.start();
        miTercerHilo objetoMiTercerHilo = new miTercerHilo();
        objetoMiTercerHilo.start();
        System.out.println("Fin de la ejecución de los tres hilos");

    }
}

// [IMPORTANT]: Mi primer Hilo
class miPrimerHilo extends Thread {

    @Override
    public void run() {
        Random random = new Random();
        int numero = random.nextInt(1, 9);
        System.out.println("El número del primer hilo random es: " + numero);
    }

}

class miSegundoHilo extends Thread {

    @Override
    public void run() {

        Random random = new Random();
        int numero = random.nextInt(1, 8001);
        System.out.println("El número random del segundo hilo es: " + numero);
    }
}

class miTercerHilo extends Thread {

    @Override
    public void run() {
        Random random = new Random();
        int numero = random.nextInt(-100, 11);
        System.out.println("El número del tercer hilo random es: " + numero);
    }
}
