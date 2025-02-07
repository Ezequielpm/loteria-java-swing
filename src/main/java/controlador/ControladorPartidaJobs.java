/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.ArduinoConnector;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import modelo.Carta;
import modelo.Configuracion;
import modelo.PartidaObjeto;
import modelo.Sesion;
import modelo.Tablero;
import vista.Dificultad;
import vista.DificultadJobs;
import vista.JugadorGana;
import vista.JugadorPierde;
import vista.PartidaJobs;

/**
 *
 * @author ezequielpena
 */
public class ControladorPartidaJobs implements ActionListener {

    PartidaJobs objPartidaJobs;

    private Timer timer; // Temporizador para cambiar la carta
    private Random random; // Generador de números aleatorios
    ArrayList<ImageIcon> listaImagenes;
    ArrayList<Integer> numerosGenerados;

    Tablero objTablero;
    int idCartaLanzada = 0;

    ArrayList<Integer> idCartas;
    ArrayList<Integer> idCartasBot;

    Tablero objTableroBot;
    int contadorAciertosJugador = 0;
    int contadorAciertosBot = 0;
    int puntosJugador = 0;
    int retrasoDificultad = 0;
    OperacionesDBPartida objOperacionesDBPartida;
    int tipoDificultad = 0;
    int idCategoria = 2;
    int puntosQueGana = 0;
    Clip clip;
    public ControladorPartidaJobs() {
    }

    public ControladorPartidaJobs(PartidaJobs objPartidaJobs, Tablero objTablero, Tablero objTableroBot) {
        this.objOperacionesDBPartida = new OperacionesDBPartida();
        this.objPartidaJobs = objPartidaJobs;
        this.objPartidaJobs.botonBack.addActionListener(this);
        listaImagenes = new ArrayList<>();
        random = new Random();
        numerosGenerados = new ArrayList<>();
        this.objTablero = objTablero;
        this.objTableroBot = objTableroBot;
        obtenerCartas();
        establecerIconos();
//                establecerCartaCentral();

        // iniciarTemporizador();
        idCartas = new ArrayList<>();
        idCartasBot = new ArrayList<>();

        mostrarCartasJugador(this.objPartidaJobs.carta1,
                this.objPartidaJobs.carta2,
                this.objPartidaJobs.carta3,
                this.objPartidaJobs.carta4,
                this.objPartidaJobs.carta5,
                this.objPartidaJobs.carta6,
                this.objPartidaJobs.carta7,
                this.objPartidaJobs.carta8,
                this.objPartidaJobs.carta9);

        mostrarCartasBot(this.objPartidaJobs.carta1Bot,
                this.objPartidaJobs.carta2Bot,
                this.objPartidaJobs.carta3Bot,
                this.objPartidaJobs.carta4Bot,
                this.objPartidaJobs.carta5Bot,
                this.objPartidaJobs.carta6Bot,
                this.objPartidaJobs.carta7Bot,
                this.objPartidaJobs.carta8Bot,
                this.objPartidaJobs.carta9Bot
        );

        this.objPartidaJobs.carta1.addActionListener(this);
        this.objPartidaJobs.carta2.addActionListener(this);
        this.objPartidaJobs.carta3.addActionListener(this);
        this.objPartidaJobs.carta4.addActionListener(this);
        this.objPartidaJobs.carta5.addActionListener(this);
        this.objPartidaJobs.carta6.addActionListener(this);
        this.objPartidaJobs.carta7.addActionListener(this);
        this.objPartidaJobs.carta8.addActionListener(this);
        this.objPartidaJobs.carta9.addActionListener(this);
        this.objPartidaJobs.botonRestart.addActionListener(this);

        obtenerIdCartas();
        obtenerIdCartasBot();
        comprobarConfiguracion();
    }
    public void subirPuntosJugador(){
        contadorAciertosJugador += puntosQueGana;
    }

    public void comprobarConfiguracion() {
        System.out.println("USAR ARDUINO: " + Configuracion.isUsarArduino());
        if (Configuracion.isUsarArduino()) {
            ArduinoConnector arduino = ArduinoConnector.getInstance();

            // Conectar al Arduino en el puerto correcto y a 9600 baudios
            if (arduino.connect("/dev/cu.usbserial-1130", 9600)) {
                System.out.println("Conexión establecida.");

                // Mantener el programa en ejecución
//            System.out.println("Presiona Enter para cerrar la conexión...");
//            new java.util.Scanner(System.in).nextLine();
                // Desconectar al terminar
//            arduino.disconnect();
                ArduinoConnector.setBotonPresionado("99");
                System.out.println("SE PRESIONO::: " + ArduinoConnector.getBotonPresionado());
                new Thread(() -> {
                    while (numerosGenerados.size() < 40) {
                        comprobarMatch(this.objPartidaJobs.carta1,
                                this.objPartidaJobs.carta2,
                                this.objPartidaJobs.carta3,
                                this.objPartidaJobs.carta4,
                                this.objPartidaJobs.carta5,
                                this.objPartidaJobs.carta6,
                                this.objPartidaJobs.carta7,
                                this.objPartidaJobs.carta8,
                                this.objPartidaJobs.carta9);

                    }

                }).start();
            } else {
                System.out.println("No se pudo establecer conexión.");
            }
        } else {
            this.objPartidaJobs.carta1.addActionListener(this);
            this.objPartidaJobs.carta2.addActionListener(this);
            this.objPartidaJobs.carta3.addActionListener(this);
            this.objPartidaJobs.carta4.addActionListener(this);
            this.objPartidaJobs.carta5.addActionListener(this);
            this.objPartidaJobs.carta6.addActionListener(this);
            this.objPartidaJobs.carta7.addActionListener(this);
            this.objPartidaJobs.carta8.addActionListener(this);
            this.objPartidaJobs.carta9.addActionListener(this);
        }

        if (Configuracion.getDificultad().equals("easy")) {
            retrasoDificultad = 2000;//10000
            tipoDificultad = 1;
            puntosQueGana = 15;
            iniciarTemporizador(retrasoDificultad);

        } else if (Configuracion.getDificultad().equals("medium")) {
            retrasoDificultad = 2000; //7000
            puntosQueGana = 30;
            tipoDificultad = 2;
            iniciarTemporizador(retrasoDificultad);
        } else if (Configuracion.getDificultad().equals("hard")) {
            retrasoDificultad = 2000;
            puntosQueGana = 50;
            tipoDificultad = 3;
            iniciarTemporizador(retrasoDificultad);
        }
    }

    public void comprobarMatch(JButton... botones) { // PARA EL ARDUINO

//        int boton = Integer.parseInt(ArduinoConnector.getBotonPresionado());
        String pinPresionado = ArduinoConnector.getBotonPresionado();
//        System.out.println("PRESION: "+boton);
        switch (pinPresionado) {
            case "BOTON_1_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta1.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta1.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta1);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta1.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta1.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta1.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta1);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta1.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta1);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_2_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta2.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta2.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta2);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta2.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta2.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta2.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta2);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta2.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta2);
//                    reproducirSonido();
//                    subirPuntosJugador();
//
//                    return;
//                }
                break;
            case "BOTON_3_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta3.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta3.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta3);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta3.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta3.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta3.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta3);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta3.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta3);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_4_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta4.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta4.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta4);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta4.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta4.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta4.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta4);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta4.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta4);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_5_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta5.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta5.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta5);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta5.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta5.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta5.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta5);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta5.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta5);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_6_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta6.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta6.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta6);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta6.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta6.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta6.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta6);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta6.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta6);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_7_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta7.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta7.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta7);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta7.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta7.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta7.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta7);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta7.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta7);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_8_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta8.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta8.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta8);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta8.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta8.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta8.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta8);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta8.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta8);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_9_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta9.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaJobs.carta9.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaJobs.carta9);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaJobs.carta9.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaJobs.carta9.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaJobs.carta9.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaJobs.carta9);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartidaJobs.carta9.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaJobs.carta9);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            default:
                break;
        }
//        ArduinoConnector.setBotonPresionado("99");

    }

    public void marcarCartaArduino(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/profesiones/marcadas/1.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);
//        reproducirSonido();
    }
    
    
    
    
    public void establecerIconos(){
        
        ImageIcon iconoImagenRestart = new ImageIcon(getClass().getResource("/iconos/btn__restart-jobs.png"));
        int widthRestart = this.objPartidaJobs.botonRestart.getWidth();
        int heightRestart = this.objPartidaJobs.botonRestart.getHeight();

        Image scaledImageRestart = iconoImagenRestart.getImage().getScaledInstance(widthRestart, heightRestart, Image.SCALE_SMOOTH);
        ImageIcon scaledIconRestart = new ImageIcon(scaledImageRestart);
        this.objPartidaJobs.botonRestart.setIcon(scaledIconRestart);
    }

    
    public void establecerCartaCentral() {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
        JLabel label = (JLabel) this.objPartidaJobs.cartaCambiante; // Asegúrate de que sea un JLabel
        int width = label.getWidth();
        int height = label.getHeight();
// Redimensionar la imagen
        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Establecer el ícono redimensionado en el JLabel
        label.setIcon(scaledIcon);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objPartidaJobs.botonBack) {
            this.timer.stop();
            DificultadJobs objVistaDificultadJobs = new DificultadJobs();
            objVistaDificultadJobs.setVisible(true);
            this.objPartidaJobs.dispose();
            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta1) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta1, Configuracion.isPresionarSinLimite());
            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta2) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta2, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta3) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta3, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta4) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta4, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta5) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta5, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta6) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta6, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta7) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta7, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta8) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta8, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.carta9) {
            comprobarCartaSeleccionada(this.objPartidaJobs.carta9, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaJobs.botonRestart) {
            reiniciarPartida();
//            timer.stop();
//            mostrarCartasJugador(this.objPartidaJobs.carta1,
//                    this.objPartidaJobs.carta2,
//                    this.objPartidaJobs.carta3,
//                    this.objPartidaJobs.carta4,
//                    this.objPartidaJobs.carta5,
//                    this.objPartidaJobs.carta6,
//                    this.objPartidaJobs.carta7,
//                    this.objPartidaJobs.carta8,
//                    this.objPartidaJobs.carta9);
//
//            mostrarCartasBot(this.objPartidaJobs.carta1Bot,
//                    this.objPartidaJobs.carta2Bot,
//                    this.objPartidaJobs.carta3Bot,
//                    this.objPartidaJobs.carta4Bot,
//                    this.objPartidaJobs.carta5Bot,
//                    this.objPartidaJobs.carta6Bot,
//                    this.objPartidaJobs.carta7Bot,
//                    this.objPartidaJobs.carta8Bot,
//                    this.objPartidaJobs.carta9Bot
//            );
//            contadorAciertosBot = 0;
//            contadorAciertosJugador = 0;
//            numerosGenerados.clear();
//
//            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
//            JLabel label = (JLabel) this.objPartidaJobs.cartaCambiante; // Asegúrate de que sea un JLabel
//            int width = label.getWidth();
//            int height = label.getHeight();
//// Redimensionar la imagen
//            Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            ImageIcon scaledIcon = new ImageIcon(scaledImage);
//
//            // Establecer el ícono redimensionado en el JLabel
//            label.setIcon(scaledIcon);
////            Thread.sleep(5000);
//            iniciarTemporizador(retrasoDificultad);
            return;
        }
    }
    
    public void reiniciarPartida(){
        timer.stop();
            mostrarCartasJugador(this.objPartidaJobs.carta1,
                    this.objPartidaJobs.carta2,
                    this.objPartidaJobs.carta3,
                    this.objPartidaJobs.carta4,
                    this.objPartidaJobs.carta5,
                    this.objPartidaJobs.carta6,
                    this.objPartidaJobs.carta7,
                    this.objPartidaJobs.carta8,
                    this.objPartidaJobs.carta9);

            mostrarCartasBot(this.objPartidaJobs.carta1Bot,
                    this.objPartidaJobs.carta2Bot,
                    this.objPartidaJobs.carta3Bot,
                    this.objPartidaJobs.carta4Bot,
                    this.objPartidaJobs.carta5Bot,
                    this.objPartidaJobs.carta6Bot,
                    this.objPartidaJobs.carta7Bot,
                    this.objPartidaJobs.carta8Bot,
                    this.objPartidaJobs.carta9Bot
            );
            contadorAciertosBot = 0;
            contadorAciertosJugador = 0;
            numerosGenerados.clear();
            
            idCartasLanzadas.clear();
            idCartasMarcadas.clear();

            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
            JLabel label = (JLabel) this.objPartidaJobs.cartaCambiante; // Asegúrate de que sea un JLabel
            int width = label.getWidth();
            int height = label.getHeight();
// Redimensionar la imagen
            Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Establecer el ícono redimensionado en el JLabel
            label.setIcon(scaledIcon);
//            Thread.sleep(5000);
            iniciarTemporizador(retrasoDificultad);
    }

//    public void comprobarCartaSeleccionada(JButton carta) {
//        if (idCartas.contains(idCartaLanzada)) {
//            if (Integer.parseInt(carta.getName()) == idCartaLanzada) {
//                System.err.println("ESTA ES LA CARTA");
//                subirPuntosJugador();
//                marcarCarta(carta);
//                if (contadorAciertosJugador > 8) {
//                    JOptionPane.showMessageDialog(this.objPartidaJobs, "You win!");
//                }
//            }
//        } else {
//            return;
//        }
//
//    }
    ArrayList<Integer> idCartasLanzadas = new ArrayList<>();
    ArrayList<Integer> idCartasMarcadas = new ArrayList<>();

    public void comprobarCartaSeleccionada(JButton carta, boolean presionarSinLimite) {
        if (idCartas.contains(idCartaLanzada)) {
            if (!presionarSinLimite) {
                if (idCartasMarcadas.contains(Integer.parseInt(carta.getName()))) {
                    return;
                }
                if (Integer.parseInt(carta.getName()) == idCartaLanzada) {
                    System.err.println("ESTA ES LA CARTA");
                    subirPuntosJugador();
                    idCartasMarcadas.add(idCartaLanzada);
                    marcarCarta(carta);
                    verificarGanador();
//                if (contadorAciertosJugador > 8) {
//                    JOptionPane.showMessageDialog(this.objPartida, "You win!");
//                    timer.stop();
//                }
                    return;
                }
            } else {

            }

        }

        if (presionarSinLimite) {
            if (idCartasMarcadas.contains(Integer.parseInt(carta.getName()))) {
                return;
            }

            if (idCartasLanzadas.contains(Integer.parseInt(carta.getName()))) {
                subirPuntosJugador();
                idCartasMarcadas.add(Integer.parseInt(carta.getName()));
                marcarCarta(carta);
                verificarGanador();
            }
        }
//        if(presionarSinLimite){
//            if (idCartasLanzadas.contains(Integer.parseInt(carta.getName()))) {
//            marcarCarta(carta);
//        }
//        }
        return;
    }

    public void marcarCarta(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/profesiones/marcadas/1.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);
        reproducirSonido();

    }

    public void obtenerIdCartas() {
        ArrayList<Carta> listaDeCartas = this.objTablero.getListaCartas();

        for (Carta objCarta : listaDeCartas) {
            idCartas.add(objCarta.getIdImagen());
        }
    }

    public void obtenerIdCartasBot() {
        ArrayList<Carta> listaDeCartas = this.objTableroBot.getListaCartas();

        for (Carta objCarta : listaDeCartas) {
            idCartasBot.add(objCarta.getIdImagen());
        }
    }

    public void cambiarCartas() {
        int indexAleatorio;
        do {
            indexAleatorio = random.nextInt(listaImagenes.size());
            System.out.println("sigo en el bucle " + indexAleatorio);
        } while (numerosGenerados.contains(indexAleatorio) && numerosGenerados.size() < 40);
        idCartaLanzada = indexAleatorio + 1;
        idCartasLanzadas.add(idCartaLanzada);
        System.out.println("Numero de carta: " + (indexAleatorio + 1));
        if (numerosGenerados.size() == 40) {
            timer.stop();
            return;
        }
        comprobarCartasDeBot(this.objPartidaJobs.carta1Bot,
                this.objPartidaJobs.carta2Bot,
                this.objPartidaJobs.carta3Bot,
                this.objPartidaJobs.carta4Bot,
                this.objPartidaJobs.carta5Bot,
                this.objPartidaJobs.carta6Bot,
                this.objPartidaJobs.carta7Bot,
                this.objPartidaJobs.carta8Bot,
                this.objPartidaJobs.carta9Bot);
        verificarGanador();

        numerosGenerados.add(indexAleatorio);
        ImageIcon iconoSeleccionado = listaImagenes.get(indexAleatorio);

//        ImageIcon iconoCarta = new ImageIcon(getClass().getResource("/categorias/animales/bird.png"));
//        ImageIcon iconoCarta = new ImageIcon("/categorias/animales/bird.png");
        // Obtener el tamaño del JLabel
        JLabel label = (JLabel) this.objPartidaJobs.cartaCambiante; // Asegúrate de que sea un JLabel
        int width = label.getWidth();
        int height = label.getHeight();

        // Redimensionar la imagen
        Image scaledImage = iconoSeleccionado.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Establecer el ícono redimensionado en el JLabel
        label.setIcon(scaledIcon);
        // Reproducir el sonido asociado a la carta
        reproducirSonido(indexAleatorio + 1); // +1 para que coincida con el número del sonido

//        this.objPartida.cartaCambiante.setIcon(iconoCarta);
    }

//    private void iniciarTemporizador() {
//        // Crear un nuevo Timer que se ejecute cada 3000 milisegundos (3 segundos)
//        timer = new Timer(3000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cambiarCartas(); // Cambiar las cartas
//                if (contadorAciertosBot > 8) {
//                    JOptionPane.showMessageDialog(objPartidaJobs, "You lose! :(");
//                    timer.stop();
//                }
//            }
//        });
//
//        // Iniciar el temporizador
//        timer.start();
//    }
    private void registrarPartida(boolean gana) {
        PartidaObjeto objPartidaObjeto = new PartidaObjeto();
        objPartidaObjeto.setIdJugador(Sesion.getIdJugador());
        objPartidaObjeto.setPuntos(contadorAciertosJugador);
        objPartidaObjeto.setFecha(LocalDate.now().toString());
        if (gana) {
            objPartidaObjeto.setResultado("GANA");
        } else {
            objPartidaObjeto.setResultado("PIERDE");
        }
        objPartidaObjeto.setIdDificultad(tipoDificultad);
        objPartidaObjeto.setIdCategoria(idCategoria);

        objOperacionesDBPartida.setObjPartida(objPartidaObjeto);
        objOperacionesDBPartida.create();
    }

    public void verificarGanador() {
        if (idCartasMarcadas.size() > 8) {
            Sesion.setPuntosGanados(contadorAciertosJugador);
            
            if(Sesion.getIdJugador()>0){
                registrarPartida(true);
            }
            
            clip.stop();
            JugadorGana objJugadorGana = new JugadorGana();
            objJugadorGana.puntos.setText(String.valueOf(Sesion.getPuntosGanados()));
            objJugadorGana.setVisible(true);
            
            objJugadorGana.objControladorJugadorGana.setObjControladorPartidaJobs(this);
            objJugadorGana.objControladorJugadorGana.setTipoPartida(2);
            timer.stop();
            this.objPartidaJobs.setVisible(false);
            
            
            
        } else if (contadorAciertosBot > 8) {
            Sesion.setPuntosGanados(contadorAciertosJugador);

            if(Sesion.getIdJugador()>0){
                registrarPartida(false);
            }

            JugadorPierde objJugadorPierde = new JugadorPierde();
            objJugadorPierde.puntos.setText(String.valueOf(Sesion.getPuntosGanados()));
            objJugadorPierde.setVisible(true);
            
            objJugadorPierde.objControladorJugadorPierde.setObjControladorPartidaJobs(this);
            objJugadorPierde.objControladorJugadorPierde.setTipoPartida(2);
            
            timer.stop();
            this.objPartidaJobs.setVisible(false);

        }
    }

    private void iniciarTemporizador(int retraso) {
        // Crear un nuevo Timer que se ejecute cada 3000 milisegundos (3 segundos)
        timer = new Timer(retraso, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarCartas(); // Cambiar las cartas
                verificarGanador();
            }
        });

        // Iniciar el temporizador
        timer.start();
    }

    public void mostrarCartasJugador(JButton... cartas) {
        ArrayList<Carta> listaDeCartas = this.objTablero.getListaCartas();
        int i = 0;
        for (JButton carta : cartas) {
            ImageIcon iconoSeleccionado = listaDeCartas.get(i).getIconoImagen();

            int width = carta.getWidth();
            int height = carta.getHeight();
            Image scaledImage = iconoSeleccionado.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            carta.setIcon(scaledIcon);
            carta.setName(String.valueOf(listaDeCartas.get(i).getIdImagen())); // se le pone el nombre al boton con el id de la imagen de la carta

            i++;
        }
    }

    public void mostrarCartasBot(JButton... cartasBot) {
        ArrayList<Carta> listaDeCartas = this.objTableroBot.getListaCartas();
        int i = 0;
        for (JButton carta : cartasBot) {
            ImageIcon iconoSeleccionado = listaDeCartas.get(i).getIconoImagen();

            int width = carta.getWidth();
            int height = carta.getHeight();
            Image scaledImage = iconoSeleccionado.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            carta.setIcon(scaledIcon);
            carta.setName(String.valueOf(listaDeCartas.get(i).getIdImagen())); // se le pone el nombre al boton con el id de la imagen de la carta

            i++;
        }
    }
    
    private void reproducirSonido(int numeroCarta) {
        try {
        // Cargar el archivo de sonido
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/categorias/profesiones/sonidos/" + numeroCarta + ".wav"));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start(); // Reproducir el sonido

        if (tipoDificultad == 2) {
            // Crear un temporizador para volver a reproducir el sonido después de 1.5 segundos
            Timer timer2 = new Timer(2500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clip.setFramePosition(0); // Reiniciar el audio desde el inicio
                    clip.start(); // Reproducir el sonido nuevamente
                }
            });
            timer2.setRepeats(false); // Solo ejecuta una vez
            timer2.start(); // Inicia el temporizador
        }
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace(); // Manejo de excepciones
    }
    }

//    private void reproducirSonido(int numeroCarta) {
//        try {
//            // Cargar el archivo de sonido
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/categorias/profesiones/sonidos/" + numeroCarta + ".wav"));
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.start(); // Reproducir el sonido
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace(); // Manejo de excepciones
//        }
//    }

    public void obtenerCartas() {
//        ImageIcon[] listaRutasImagenes;
        for (int i = 1; i <= 40; i++) {
            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/profesiones/imagenes/" + i + ".png"));
            listaImagenes.add(iconoImagen);
        }
    }

    public void comprobarCartasDeBot(JButton... cartas) {
        if (idCartasBot.contains(idCartaLanzada)) {
            for (JButton carta : cartas) {
                if (Integer.parseInt(carta.getName()) == idCartaLanzada) {
                    System.err.println("ESTA ES LA CARTA");
                    contadorAciertosBot++;
                    marcarCartaBot(carta);
//                    if (contadorAciertosBot > 8) {
//                        JOptionPane.showMessageDialog(this.objPartida, "You lose! :(");
//
//                    }
                    return;
                }
            }

        } else {
            return;
        }

    }

    public void marcarCartaBot(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/profesiones/marcadas/2.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);

    }

    public void reproducirSonido() {
        String rutaArchivo = "/sonidos/clics/point.wav";
//        try {
//            File archivoSonido = new File(rutaArchivo);
//            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioStream);
//            clip.start(); // Reproduce el sonido
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
        try {
            // Carga el archivo de sonido como un InputStream desde el classpath
            InputStream audioSrc = getClass().getResourceAsStream(rutaArchivo);
            if (audioSrc == null) {
                System.err.println("No se encontró el archivo de sonido: " + rutaArchivo);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Reproduce el sonido
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
