
// [INFO] Coloca los elementos de la ventana en zonas, como el centro y la parte inferior.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class tragaPerras extends JFrame {
    private static final String[] FRUTAS = { "cereza.png", "limon.png", "sandia.png", "naranja.png", "uva.png",
            "fresa.png" };
    private static final int[] PREMIO_X = { 5, 8, 10, 12, 15, 20 };
    private static final int ALTO_CELDA = 120, ANCHO_RODILLO = 120;
    private static final int APUESTA = 10;
    private static final int MAX_ANCHO_VENTANA = 560, MAX_ALTO_VENTANA = 360;

    private int creditos = 100;
    private int tiempo = 0;
    private Timer timer;
    private final RodilloPanel[] rodillos = new RodilloPanel[3];
    private final JButton btnGirar = new JButton("GIRAR");
    private final JLabel lblCreditos = new JLabel("Créditos: 100", SwingConstants.CENTER);

    // [INFO] Prepara la ventana principal del juego.
    // [INFO] Coloca los rodillos y controles, calcula el tamaño inicial y centra la
    // [INFO] ventana.
    public tragaPerras() {
        setTitle("Tragaperras con Imágenes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        crearRodillos();
        crearControles();

        pack();
        // [INFO] Limita el zoom para que las imágenes no se amplíen demasiado.
        setMaximumSize(new Dimension(MAX_ANCHO_VENTANA, MAX_ALTO_VENTANA));
        setLocationRelativeTo(null);
    }

    // [INFO] Crea los tres rodillos de la tragaperras.
    // [INFO] Guarda cada rodillo en el arreglo y los coloca juntos en el centro de
    // la
    // [INFO] ventana.
    private void crearRodillos() {
        JPanel pnlRodillos = new JPanel(new GridLayout(1, 3, 10, 0));
        for (int i = 0; i < 3; i++) {
            rodillos[i] = new RodilloPanel();
            pnlRodillos.add(rodillos[i]);
        }
        add(pnlRodillos, BorderLayout.CENTER);
    }

    // [INFO] Crea la parte inferior de la ventana.
    // [INFO] Muestra los créditos, crea el botón y conecta su pulsación con
    // iniciarGiro().
    private void crearControles() {
        JPanel pnlControl = new JPanel(new GridLayout(2, 1, 5, 5));
        lblCreditos.setFont(new Font("Arial", Font.BOLD, 18));
        // [INFO] Este método se ejecuta al pulsar GIRAR y empieza el giro.
        btnGirar.addActionListener(e -> {
            // [INFO] Al pulsar el botón, comienza una ronda nueva.
            iniciarGiro();
        });
        pnlControl.add(lblCreditos);
        pnlControl.add(btnGirar);
        add(pnlControl, BorderLayout.SOUTH);
    }

    // [IMPORTANT] Empieza una ronda nueva.
    // [IMPORTANT] Comprueba los créditos, cobra la apuesta, desactiva el botón y
    // arranca el
    // [IMPORTANT] temporizador.
    private void iniciarGiro() {
        if (creditos < APUESTA) {
            JOptionPane.showMessageDialog(this, "Créditos insuficientes");
            return;
        }
        creditos -= APUESTA;
        lblCreditos.setText("Créditos: " + creditos);
        btnGirar.setEnabled(false);

        tiempo = 0;
        // [INFO] El temporizador llama a actualizarGiro unas 60 veces por segundo.
        // [INFO] Así la animación avanza poco a poco mientras la ventana sigue
        // respondiendo.
        timer = new Timer(1000 / 60, e -> {
            // [INFO] Cada aviso del temporizador avanza un paso de la ronda.
            actualizarGiro();
        });
        timer.start();
    }

    // [INFO] Actualiza el giro en cada paso del temporizador.
    // [INFO] Usa tiempo para mover los tres rodillos y detenerlos en distintos
    // momentos.
    private void actualizarGiro() {
        tiempo++;

        // [INFO] Primero se para el rodillo 1, luego el 2 y por último el 3.
        if (tiempo <= 30) {
            rodillos[0].girar();
        } else if (tiempo == 31) {
            rodillos[0].parar();
        }

        if (tiempo <= 60) {
            rodillos[1].girar();
        } else if (tiempo == 61) {
            rodillos[1].parar();
        }

        if (tiempo <= 90) {
            rodillos[2].girar();
        }

        if (tiempo == 90) {
            rodillos[2].parar();
            timer.stop();
            comprobarPremio();
        }
    }

    // [INFO] Revisa las frutas cuando terminan de girar los tres rodillos.
    // [INFO] Si coinciden, calcula y suma el premio; al final muestra los créditos
    // y
    // [INFO] activa GIRAR.
    private void comprobarPremio() {
        int r0 = rodillos[0].getIndiceActual();
        if (r0 == rodillos[1].getIndiceActual() && r0 == rodillos[2].getIndiceActual()) {
            // [INFO] PREMIO_X guarda cuánto se gana según la fruta que salió.
            int premio = APUESTA * PREMIO_X[r0];
            creditos += premio;
            JOptionPane.showMessageDialog(this, "¡Premio! Ganaste " + premio + " créditos.");
        }
        lblCreditos.setText("Créditos: " + creditos);
        btnGirar.setEnabled(true);
    }

    // [INFO] Representa un rodillo individual. Cada objeto de esta clase guarda sus
    // [INFO] imágenes, su fruta actual y su movimiento.
    private static class RodilloPanel extends JPanel {
        private int idx = 0;
        private double offset = 0;
        private final Random rand = new Random();
        private final Image[] imagenes = new Image[FRUTAS.length];

        // [INFO] Prepara el tamaño del rodillo y carga todas las frutas.
        // [INFO] Las imágenes se guardan para poder dibujarlas durante la animación.
        public RodilloPanel() {
            setPreferredSize(new Dimension(ANCHO_RODILLO, ALTO_CELDA));
            for (int i = 0; i < FRUTAS.length; i++) {
                imagenes[i] = cargarImagen(FRUTAS[i]);
            }
        }

        // [INFO] Busca el archivo de imagen en las rutas indicadas.
        // [INFO] Devuelve la imagen al encontrarla; si no existe, avisa y devuelve
        // null.
        private Image cargarImagen(String nombre) {
            String[] rutas = {
                    nombre,
                    "maquinaTragaperras/" + nombre,
                    "ProgramacionDeServiciosYPorcesos_Jesus/maquinaTragaperras/" + nombre
            };

            for (String ruta : rutas) {
                if (new File(ruta).isFile()) {
                    return new ImageIcon(ruta).getImage();
                }
            }
            System.err.println("No se encontró la imagen " + nombre);
            return null;
        }

        // [INFO] Devuelve el número de la fruta que muestra este rodillo.
        // [INFO] El juego usa ese número para comparar los tres resultados.
        public int getIndiceActual() {
            return idx;
        }

        // [INFO] Avanza el rodillo un paso.
        // [INFO] Al completar ocho pasos, cambia a la siguiente fruta y vuelve a
        // empezar el
        // [INFO] recorrido.
        public void girar() {
            // [INFO] Suma un octavo del recorrido; al completarlo, cambia la fruta.
            offset += 0.125;
            if (offset >= 1) {
                offset = 0;
                idx = (idx + 1) % FRUTAS.length;
            }
            // [INFO] Pide que el rodillo se dibuje otra vez.
            repaint();
        }

        // [INFO] Termina el giro de este rodillo.
        // [INFO] Coloca una fruta al azar, pone el movimiento a cero y actualiza el
        // dibujo.
        public void parar() {
            offset = 0;
            idx = rand.nextInt(FRUTAS.length);
            repaint();
        }

        // [IMPORTANT] Dibuja todo lo que aparece dentro del rodillo.
        // [IMPORTANT] Coloca la fruta actual y la siguiente para que el movimiento
        // parezca
        // [IMPORTANT] continuo.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // [INFO] Suaviza la imagen cuando se cambia su tamaño.
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // [IMPORTANT] La fruta siguiente aparece debajo de la actual durante el giro.
            int sigIdx = (idx + 1) % FRUTAS.length;
            // [INFO] Calcula cuántos píxeles se han movido las frutas.
            int desplazamiento = (int) (offset * getHeight());
            dibujarFruta(g2, idx, desplazamiento);
            dibujarFruta(g2, sigIdx, desplazamiento - getHeight());

            g2.setColor(Color.BLACK);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        // [INFO] Dibuja una fruta en la posición vertical indicada.
        // [INFO] Ajusta su tamaño para que quepa en el rodillo sin deformarla y la
        // [INFO] centra.
        private void dibujarFruta(Graphics2D g2, int index, int y) {
            Image imagen = imagenes[index];
            if (imagen != null) {
                int anchoMaximo = getWidth() - 30;
                int altoMaximo = getHeight() - 30;
                // [INFO] Calcula un tamaño que encaja sin deformar la fruta.
                double escala = Math.min((double) anchoMaximo / imagen.getWidth(this),
                        (double) altoMaximo / imagen.getHeight(this));
                int ancho = (int) (imagen.getWidth(this) * escala);
                int alto = (int) (imagen.getHeight(this) * escala);
                int x = (getWidth() - ancho) / 2;
                int yCentrado = y + (getHeight() - alto) / 2;
                g2.drawImage(imagen, x, yCentrado, ancho, alto, this);
            }
        }
    }

    // [INFO] Es el punto de entrada: Java empieza a ejecutar el programa aquí.
    public static void main(String[] args) {
        // [INFO] Pide a Swing que cree la ventana cuando el sistema gráfico esté
        // [INFO] preparado.
        SwingUtilities.invokeLater(() -> {
            // [INFO] Crea el juego y hace visible su ventana.
            new tragaPerras().setVisible(true);
        });
    }
}
