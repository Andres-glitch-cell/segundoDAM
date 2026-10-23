import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * T2p1: Simulación concurrente de máquina tragaperras con Java Swing.
 * Cada carrusel se ejecuta en su propio hilo (Runnable + Thread).
 * Las frutas giran de arriba hacia abajo.
 *
 * Extras: créditos y apuesta, tabla de premios, luces del marco,
 * rodillos con efecto cilíndrico y resaltado de la línea ganadora.
 */
public class tragaPerras extends JFrame {

    private static final String[] FRUTAS = { "CEREZA", "LIMÓN", "SANDÍA", "NARANJA", "UVA", "FRESA" };
    private static final Color[] COLORES = {
            new Color(220, 50, 50), // cereza
            new Color(240, 220, 60), // limón
            new Color(80, 190, 90), // sandía
            new Color(250, 150, 40), // naranja
            new Color(140, 80, 180), // uva
            new Color(240, 100, 140) // fresa
    };
    // Multiplicador del premio (por la apuesta) cuando salen tres frutas iguales
    private static final int[] PREMIO_X = { 5, 8, 10, 12, 15, 20 };

    private static final int ALTO_CELDA = 70;
    private static final int ANCHO_RODILLO = 150;
    private static final int FPS = 60; // fotogramas por segundo de la animación

    private static final int CREDITOS_INICIALES = 100;
    private static final int APUESTA = 10; // créditos que cuesta cada tirada

    private static final Color DORADO = new Color(255, 200, 40);
    private static final Color VERDE_LCD = new Color(60, 255, 90);

    private final Rodillo[] carruseles = new Rodillo[3];
    private final Marco marco = new Marco();
    private final JButton btnInicio = new JButton("Inicio");
    private final JLabel lblEstado = new JLabel("Apuesta: " + APUESTA + " créditos por tirada",
            SwingConstants.CENTER);
    private final JLabel lblPuntuacion = new JLabel();
    private final JLabel lblCreditos = new JLabel();

    // Fruta final de cada carrusel (índice en FRUTAS). Cada hilo escribe en su
    // posición.
    private final int[] resultado = new int[3];

    // Estado de la partida (solo se toca desde el EDT)
    private int creditos = CREDITOS_INICIALES;
    private int puntuacion = 0;
    private boolean sinCreditos = false;

    /**
     * Marco de la máquina con bombillas que parpadean.
     * Va más rápido mientras giran los rodillos y destella al ganar.
     */
    private static class Marco extends JPanel {
        private int fase = 0;
        private boolean destello = false;
        private final Timer timer = new Timer(400, e -> {
            fase++;
            repaint();
        });

        Marco() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(26, 26, 26, 26));
            timer.start();
        }

        void setModo(int msPorPaso, boolean destello) {
            this.destello = destello;
            timer.setDelay(msPorPaso);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();

            // Carcasa
            g2.setPaint(new GradientPaint(0, 0, new Color(150, 20, 30), 0, h, new Color(70, 5, 15)));
            g2.fill(new RoundRectangle2D.Double(0, 0, w, h, 30, 30));
            g2.setColor(DORADO);
            g2.setStroke(new BasicStroke(3));
            g2.draw(new RoundRectangle2D.Double(2, 2, w - 4, h - 4, 30, 30));

            // Bombillas alrededor
            int paso = 26;
            int i = 0;
            for (int x = 16; x <= w - 16; x += paso) {
                bombilla(g2, x, 13, i++);
                bombilla(g2, x, h - 13, i++);
            }
            for (int y = 16 + paso; y <= h - 16 - paso; y += paso) {
                bombilla(g2, 13, y, i++);
                bombilla(g2, w - 13, y, i++);
            }
            g2.dispose();
        }

        private void bombilla(Graphics2D g2, int cx, int cy, int indice) {
            boolean encendida = destello ? (fase % 2 == 0) : Math.floorMod(indice - fase, 4) == 0;
            g2.setColor(encendida ? DORADO : new Color(100, 60, 20));
            g2.fillOval(cx - 5, cy - 5, 10, 10);
            if (encendida) {
                g2.setColor(new Color(255, 240, 160));
                g2.fillOval(cx - 2, cy - 3, 4, 4);
            }
        }
    }

    /**
     * Componente visual de un rodillo. Muestra 3 frutas (arriba, centro, abajo)
     * y las desplaza hacia abajo según 'fraccion' (0..1).
     * Solo se modifica desde el EDT.
     */
    private static class Rodillo extends JPanel {
        private int centro = 0; // fruta que está en la fila central
        private double fraccion = 0; // desplazamiento hacia abajo, de 0 a 1 celda
        private boolean ganador = false;
        private final Font fuente = new Font("SansSerif", Font.BOLD, 18);

        Rodillo() {
            setPreferredSize(new Dimension(ANCHO_RODILLO, ALTO_CELDA * 3));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 130, 30), 4));
        }

        /** Posición continua de la cinta (en celdas). Solo desde el EDT. */
        void setPosicion(double pos) {
            double base = Math.floor(pos);
            this.centro = Math.floorMod((int) base, FRUTAS.length);
            this.fraccion = pos - base;
            repaint();
        }

        void setGanador(boolean ganador) {
            this.ganador = ganador;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setFont(fuente);
            FontMetrics fm = g2.getFontMetrics();

            int n = FRUTAS.length;
            int x = 6;
            int ancho = getWidth() - 12;
            int offsetY = (getHeight() - ALTO_CELDA * 3) / 2;

            // Filas -1 (entrando por arriba), 0 (arriba), 1 (centro), 2 (abajo)
            for (int fila = -1; fila <= 2; fila++) {
                int fruta = Math.floorMod(centro + (1 - fila), n);
                double y = offsetY + (fila + fraccion) * ALTO_CELDA; // posición con decimales

                g2.setColor(COLORES[fruta]);
                g2.fill(new RoundRectangle2D.Double(x, y + 5, ancho, ALTO_CELDA - 10, 20, 20));
                g2.setColor(Color.BLACK);
                String texto = FRUTAS[fruta];
                float tx = x + (ancho - fm.stringWidth(texto)) / 2f;
                float ty = (float) (y + (ALTO_CELDA + fm.getAscent() - fm.getDescent()) / 2.0);
                g2.drawString(texto, tx, ty);
            }

            // Sombreado arriba y abajo: efecto de rodillo cilíndrico
            int h = getHeight();
            g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0, 170), 0, ALTO_CELDA * 0.8f, new Color(0, 0, 0, 0)));
            g2.fillRect(0, 0, getWidth(), ALTO_CELDA);
            g2.setPaint(new GradientPaint(0, h - ALTO_CELDA * 0.8f, new Color(0, 0, 0, 0), 0, h,
                    new Color(0, 0, 0, 170)));
            g2.fillRect(0, h - ALTO_CELDA, getWidth(), ALTO_CELDA);

            // Marcas de la línea central
            g2.setColor(new Color(200, 30, 30));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(0, offsetY + ALTO_CELDA, 8, offsetY + ALTO_CELDA);
            g2.drawLine(getWidth() - 8, offsetY + ALTO_CELDA, getWidth(), offsetY + ALTO_CELDA);
            g2.drawLine(0, offsetY + ALTO_CELDA * 2, 8, offsetY + ALTO_CELDA * 2);
            g2.drawLine(getWidth() - 8, offsetY + ALTO_CELDA * 2, getWidth(), offsetY + ALTO_CELDA * 2);

            // Resalta la fruta ganadora
            if (ganador) {
                g2.setColor(DORADO);
                g2.setStroke(new BasicStroke(4));
                g2.draw(new RoundRectangle2D.Double(x - 2, offsetY + ALTO_CELDA + 3, ancho + 4, ALTO_CELDA - 6, 22,
                        22));
            }
            g2.dispose();
            Toolkit.getDefaultToolkit().sync(); // evita tirones en Linux
        }
    }

    public tragaPerras() {
        super("Slot Machine Java Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 10, 15));

        // Marcadores superiores (estilo pantalla LCD)
        JPanel panelMarcadores = new JPanel(new GridLayout(2, 1));
        panelMarcadores.setBackground(Color.BLACK);
        panelMarcadores.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DORADO, 3),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        for (JLabel l : new JLabel[] { lblPuntuacion, lblCreditos }) {
            l.setFont(new Font("Monospaced", Font.BOLD, 18));
            l.setForeground(VERDE_LCD);
            panelMarcadores.add(l);
        }
        actualizarMarcadores();
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        norte.setOpaque(false);
        norte.add(panelMarcadores);
        add(norte, BorderLayout.NORTH);

        // Panel de carruseles dentro del marco con luces
        JPanel panelRodillos = new JPanel(new GridLayout(1, 3, 10, 0));
        panelRodillos.setOpaque(false);
        for (int i = 0; i < 3; i++) {
            Rodillo r = new Rodillo();
            carruseles[i] = r;
            panelRodillos.add(r);
        }
        marco.add(panelRodillos, BorderLayout.CENTER);
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        contenedor.add(marco, BorderLayout.CENTER);
        add(contenedor, BorderLayout.CENTER);

        // Panel inferior: tabla de premios + botón + estado
        JPanel panelSur = new JPanel(new GridLayout(3, 1, 5, 5));
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        JLabel lblPremios = new JLabel(tablaPremios(), SwingConstants.CENTER);
        lblPremios.setFont(new Font("Monospaced", Font.PLAIN, 11));
        lblPremios.setForeground(DORADO);

        btnInicio.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnInicio.setBackground(new Color(255, 190, 40));
        btnInicio.setFocusPainted(false);
        btnInicio.addActionListener(e -> {
            if (sinCreditos) {
                reiniciarPartida();
            } else {
                iniciarTirada();
            }
        });

        lblEstado.setOpaque(true);
        lblEstado.setBackground(Color.BLACK);
        lblEstado.setForeground(VERDE_LCD);
        lblEstado.setFont(new Font("Monospaced", Font.BOLD, 16));

        panelSur.add(lblPremios);
        panelSur.add(btnInicio);
        panelSur.add(lblEstado);
        add(panelSur, BorderLayout.SOUTH);

        marco.setModo(400, false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Texto con la tabla de premios (3 frutas iguales = apuesta x multiplicador).
     */
    private static String tablaPremios() {
        StringBuilder sb = new StringBuilder("<html><center>PREMIOS (3 iguales): ");
        for (int i = 0; i < FRUTAS.length; i++) {
            sb.append(FRUTAS[i].substring(0, 1)).append(FRUTAS[i].substring(1).toLowerCase())
                    .append(" x").append(PREMIO_X[i]);
            if (i < FRUTAS.length - 1) {
                sb.append(" · ");
            }
        }
        return sb.append("</center></html>").toString();
    }

    private void actualizarMarcadores() {
        lblPuntuacion.setText(String.format("Puntuación: %6d", puntuacion));
        lblCreditos.setText(String.format("Créditos:    %6d", creditos));
    }

    private void reiniciarPartida() {
        creditos = CREDITOS_INICIALES;
        puntuacion = 0;
        sinCreditos = false;
        actualizarMarcadores();
        btnInicio.setText("Inicio");
        lblEstado.setText("Apuesta: " + APUESTA + " créditos por tirada");
        for (Rodillo r : carruseles) {
            r.setGanador(false);
        }
    }

    /** Se ejecuta en el EDT al pulsar el botón. */
    private void iniciarTirada() {
        btnInicio.setEnabled(false);
        creditos -= APUESTA;
        actualizarMarcadores();
        lblEstado.setText("Girando...");
        marco.setModo(70, false);
        for (Rodillo r : carruseles) {
            r.setGanador(false);
        }

        Thread[] hilos = new Thread[3];
        for (int i = 0; i < 3; i++) {
            hilos[i] = new Thread(new Carrusel(i), "Carrusel-" + i);
        }
        // Arrancar los tres hilos a la vez
        for (Thread t : hilos) {
            t.start();
        }

        // Hilo coordinador: espera a que terminen los tres y luego evalúa (sin bloquear
        // el EDT)
        new Thread(() -> {
            try {
                for (Thread t : hilos) {
                    t.join();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::evaluarResultado);
        }, "Coordinador").start();
    }

    /** Se ejecuta en el EDT cuando los tres hilos han terminado. */
    private void evaluarResultado() {
        boolean premio = resultado[0] == resultado[1] && resultado[1] == resultado[2];
        if (premio) {
            int ganancia = APUESTA * PREMIO_X[resultado[0]];
            creditos += ganancia;
            puntuacion += ganancia;
            for (Rodillo r : carruseles) {
                r.setGanador(true);
            }
            lblEstado.setText("¡PREMIO! Tres " + FRUTAS[resultado[0]] + " +" + ganancia);
            marco.setModo(150, true); // las luces destellan
            Toolkit.getDefaultToolkit().beep();
            actualizarMarcadores();
            JOptionPane.showMessageDialog(this,
                    "¡Premio! Las tres frutas coinciden.\nHas ganado " + ganancia + " créditos.",
                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblEstado.setText("Sigue intentándolo");
        }
        marco.setModo(400, false);

        if (creditos < APUESTA) {
            sinCreditos = true;
            btnInicio.setText("Reiniciar");
            lblEstado.setText("Sin créditos. Pulsa Reiniciar");
        }
        btnInicio.setEnabled(true);
    }

    /**
     * Hilo de un carrusel: desplaza las frutas hacia abajo con frenado progresivo.
     */
    private class Carrusel implements Runnable {
        private final int indice;
        private final Random rnd = new Random();

        Carrusel(int indice) {
            this.indice = indice;
        }

        @Override
        public void run() {
            // Cada carrusel gira un tiempo distinto para no parar a la vez
            double duracion = 2.0 + indice * 0.8 + rnd.nextDouble() * 0.5; // segundos
            double velocidadInicial = 13 + rnd.nextDouble() * 3; // celdas/segundo
            // Distancia entera de celdas: así el rodillo acaba alineado en el centro
            int distancia = (int) Math.round(velocidadInicial * duracion / 2.0);
            int inicio = rnd.nextInt(FRUTAS.length);
            long durNs = (long) (duracion * 1_000_000_000L);
            long frameNs = 1_000_000_000L / FPS;

            long t0 = System.nanoTime();
            try {
                while (true) {
                    long inicioFrame = System.nanoTime();
                    double t = Math.min(1.0, (double) (inicioFrame - t0) / durNs);
                    // Frenado progresivo suave (ease-out): empieza rápido y va frenando
                    double avance = 1 - (1 - t) * (1 - t);
                    mostrar(inicio + distancia * avance);
                    if (t >= 1.0) {
                        break;
                    }
                    // Mantiene ~60 fps compensando lo que ha tardado el fotograma
                    long espera = (frameNs - (System.nanoTime() - inicioFrame)) / 1_000_000L;
                    Thread.sleep(Math.max(1, espera));
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            resultado[indice] = (inicio + distancia) % FRUTAS.length;
        }

        private void mostrar(double posicion) {
            SwingUtilities.invokeLater(() -> carruseles[indice].setPosicion(posicion));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new tragaPerras().setVisible(true));
    }
}
