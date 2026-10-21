public class ejemploUno {
  public static void main(String args[]) {
    Tarea tarea1 = new Tarea(1);
    tarea1.run();
    Tarea tarea2 = new Tarea(2);
    tarea2.run();
    System.out.println("Fin del hilo principal");
  }
}
