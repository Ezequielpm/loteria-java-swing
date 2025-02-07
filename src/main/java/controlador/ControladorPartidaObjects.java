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
import vista.DificultadJobs;
import vista.DificultadObjects;
import vista.JugadorGana;
import vista.JugadorPierde;
import vista.PartidaObjects;

/**
 *
 * @author ezequielpena
 */
public class ControladorPartidaObjects implements ActionListener{
    PartidaObjects objPartidaObjects;
    
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
    int idCategoria = 3;
    int puntosQueGana = 0;
    Clip clip;
    public ControladorPartidaObjects() {
    }

    public ControladorPartidaObjects(PartidaObjects objPartidaObjects, Tablero objTablero, Tablero objTableroBot) {
        this.objOperacionesDBPartida = new OperacionesDBPartida();
        this.objPartidaObjects = objPartidaObjects;
        this.objTablero = objTablero;
        this.objTableroBot = objTableroBot;
//        establecerCartaCentral();
        this.objPartidaObjects.botonBack.addActionListener(this);
        listaImagenes = new ArrayList<>();
        random = new Random();
        numerosGenerados = new ArrayList<>();
        obtenerCartas();
                establecerIconos();

        

        //iniciarTemporizador();

        idCartas = new ArrayList<>();
        idCartasBot = new ArrayList<>();
        
        mostrarCartasJugador(this.objPartidaObjects.carta1,
                this.objPartidaObjects.carta2,
                this.objPartidaObjects.carta3,
                this.objPartidaObjects.carta4,
                this.objPartidaObjects.carta5,
                this.objPartidaObjects.carta6,
                this.objPartidaObjects.carta7,
                this.objPartidaObjects.carta8,
                this.objPartidaObjects.carta9);

        mostrarCartasBot(this.objPartidaObjects.carta1Bot,
                this.objPartidaObjects.carta2Bot,
                this.objPartidaObjects.carta3Bot,
                this.objPartidaObjects.carta4Bot,
                this.objPartidaObjects.carta5Bot,
                this.objPartidaObjects.carta6Bot,
                this.objPartidaObjects.carta7Bot,
                this.objPartidaObjects.carta8Bot,
                this.objPartidaObjects.carta9Bot
        );
        
        this.objPartidaObjects.carta1.addActionListener(this);
        this.objPartidaObjects.carta2.addActionListener(this);
        this.objPartidaObjects.carta3.addActionListener(this);
        this.objPartidaObjects.carta4.addActionListener(this);
        this.objPartidaObjects.carta5.addActionListener(this);
        this.objPartidaObjects.carta6.addActionListener(this);
        this.objPartidaObjects.carta7.addActionListener(this);
        this.objPartidaObjects.carta8.addActionListener(this);
        this.objPartidaObjects.carta9.addActionListener(this);
        this.objPartidaObjects.botonRestart.addActionListener(this);

        obtenerIdCartas();
        obtenerIdCartasBot();
        comprobarConfiguracion();
    }
    
    public void subirPuntosJugador(){
        contadorAciertosJugador += puntosQueGana;
    }
    
    public void comprobarConfiguracion() {
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
                        comprobarMatch(this.objPartidaObjects.carta1,
                                this.objPartidaObjects.carta2,
                                this.objPartidaObjects.carta3,
                                this.objPartidaObjects.carta4,
                                this.objPartidaObjects.carta5,
                                this.objPartidaObjects.carta6,
                                this.objPartidaObjects.carta7,
                                this.objPartidaObjects.carta8,
                                this.objPartidaObjects.carta9);

                    }

                }).start();
            } else {
                System.out.println("No se pudo establecer conexión.");
            }
        } else {
            this.objPartidaObjects.carta1.addActionListener(this);
            this.objPartidaObjects.carta2.addActionListener(this);
            this.objPartidaObjects.carta3.addActionListener(this);
            this.objPartidaObjects.carta4.addActionListener(this);
            this.objPartidaObjects.carta5.addActionListener(this);
            this.objPartidaObjects.carta6.addActionListener(this);
            this.objPartidaObjects.carta7.addActionListener(this);
            this.objPartidaObjects.carta8.addActionListener(this);
            this.objPartidaObjects.carta9.addActionListener(this);
        }

        if (Configuracion.getDificultad().equals("easy")) {
            retrasoDificultad = 10000;
            tipoDificultad = 1;
            puntosQueGana = 15;
            iniciarTemporizador(retrasoDificultad);

        } else if (Configuracion.getDificultad().equals("medium")) {
            retrasoDificultad = 7000;
            puntosQueGana = 30;
            tipoDificultad = 2;
            iniciarTemporizador(retrasoDificultad);
        } else if (Configuracion.getDificultad().equals("hard")) {
            retrasoDificultad = 4000;
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
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta1.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta1.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta1);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta1.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta1.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta1.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta1);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta1.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta1);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_2_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta2.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta2.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta2);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta2.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta2.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta2.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta2);
                        reproducirSonido();
                    }
                }
                
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta2.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta2);
//                    reproducirSonido();
//                    subirPuntosJugador();
//
//                    return;
//                }
                break;
            case "BOTON_3_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta3.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta3.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta3);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta3.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta3.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta3.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta3);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta3.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta3);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_4_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta4.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta4.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta4);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta4.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta4.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta4.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta4);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta4.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta4);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_5_PRESIONADO":
                 if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta5.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta5.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta5);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta5.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta5.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta5.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta5);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta5.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta5);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_6_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta6.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta6.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta6);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta6.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta6.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta6.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta6);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta6.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta6);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_7_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta7.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta7.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta7);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta7.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta7.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta7.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta7);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta7.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta7);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_8_PRESIONADO":
                 if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta8.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta8.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta8);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta8.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta8.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta8.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta8);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta8.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta8);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_9_PRESIONADO":
                 if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta9.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartidaObjects.carta9.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartidaObjects.carta9);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartidaObjects.carta9.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartidaObjects.carta9.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartidaObjects.carta9.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartidaObjects.carta9);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartidaObjects.carta9.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartidaObjects.carta9);
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
    
    public void establecerIconos(){
        ImageIcon iconoImagenRestart = new ImageIcon(getClass().getResource("/iconos/btn__restart-objects.png"));
        int widthRestart = this.objPartidaObjects.botonRestart.getWidth();
        int heightRestart = this.objPartidaObjects.botonRestart.getHeight();

        Image scaledImageRestart = iconoImagenRestart.getImage().getScaledInstance(widthRestart, heightRestart, Image.SCALE_SMOOTH);
        ImageIcon scaledIconRestart = new ImageIcon(scaledImageRestart);
        this.objPartidaObjects.botonRestart.setIcon(scaledIconRestart);
    }
    
    
    public void marcarCartaArduino(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/objetos/marcadas/1.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);
//        reproducirSonido();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.objPartidaObjects.botonBack) {
            this.timer.stop();
            DificultadObjects objVistaDificultadObjects = new DificultadObjects();
            objVistaDificultadObjects.setVisible(true);
            this.objPartidaObjects.dispose();
            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta1) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta1,Configuracion.isPresionarSinLimite());
            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta2) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta2,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta3) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta3,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta4) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta4,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta5) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta5,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta6) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta6,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta7) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta7,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta8) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta8,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.carta9) {
            comprobarCartaSeleccionada(this.objPartidaObjects.carta9,Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartidaObjects.botonRestart) {
            reiniciarPartida();
//            timer.stop();
//            mostrarCartasJugador(this.objPartidaObjects.carta1,
//                    this.objPartidaObjects.carta2,
//                    this.objPartidaObjects.carta3,
//                    this.objPartidaObjects.carta4,
//                    this.objPartidaObjects.carta5,
//                    this.objPartidaObjects.carta6,
//                    this.objPartidaObjects.carta7,
//                    this.objPartidaObjects.carta8,
//                    this.objPartidaObjects.carta9);
//
//            mostrarCartasBot(this.objPartidaObjects.carta1Bot,
//                    this.objPartidaObjects.carta2Bot,
//                    this.objPartidaObjects.carta3Bot,
//                    this.objPartidaObjects.carta4Bot,
//                    this.objPartidaObjects.carta5Bot,
//                    this.objPartidaObjects.carta6Bot,
//                    this.objPartidaObjects.carta7Bot,
//                    this.objPartidaObjects.carta8Bot,
//                    this.objPartidaObjects.carta9Bot
//            );
//            contadorAciertosBot = 0;
//            contadorAciertosJugador = 0;
//            numerosGenerados.clear();
//
//            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
//            JLabel label = (JLabel) this.objPartidaObjects.cartaCambiante; // Asegúrate de que sea un JLabel
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
            mostrarCartasJugador(this.objPartidaObjects.carta1,
                    this.objPartidaObjects.carta2,
                    this.objPartidaObjects.carta3,
                    this.objPartidaObjects.carta4,
                    this.objPartidaObjects.carta5,
                    this.objPartidaObjects.carta6,
                    this.objPartidaObjects.carta7,
                    this.objPartidaObjects.carta8,
                    this.objPartidaObjects.carta9);

            mostrarCartasBot(this.objPartidaObjects.carta1Bot,
                    this.objPartidaObjects.carta2Bot,
                    this.objPartidaObjects.carta3Bot,
                    this.objPartidaObjects.carta4Bot,
                    this.objPartidaObjects.carta5Bot,
                    this.objPartidaObjects.carta6Bot,
                    this.objPartidaObjects.carta7Bot,
                    this.objPartidaObjects.carta8Bot,
                    this.objPartidaObjects.carta9Bot
            );
            contadorAciertosBot = 0;
            contadorAciertosJugador = 0;
            numerosGenerados.clear();
            
            idCartasLanzadas.clear();
            idCartasMarcadas.clear();

            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
            JLabel label = (JLabel) this.objPartidaObjects.cartaCambiante; // Asegúrate de que sea un JLabel
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
//                    JOptionPane.showMessageDialog(this.objPartidaObjects, "You win!");
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
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/objetos/marcadas/1.png"));
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
    
    
    public void obtenerCartas() {
//        ImageIcon[] listaRutasImagenes;
        for (int i = 1; i <= 40; i++) {
            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/objetos/imagenes/" + i + ".png"));
            listaImagenes.add(iconoImagen);
        }
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
    
//    private void iniciarTemporizador() {
//        // Crear un nuevo Timer que se ejecute cada 3000 milisegundos (3 segundos)
//        timer = new Timer(1500, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cambiarCartas(); // Cambiar las cartas
//                if (contadorAciertosBot > 8) {
//                    JOptionPane.showMessageDialog(objPartidaObjects, "You lose! :(");
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
        }
        else{
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
            
            objJugadorGana.objControladorJugadorGana.setObjControladorPartidaObjects(this);
            objJugadorGana.objControladorJugadorGana.setTipoPartida(3);
            timer.stop();
            this.objPartidaObjects.setVisible(false);
        } else if (contadorAciertosBot > 8) {
            Sesion.setPuntosGanados(contadorAciertosJugador);

            if(Sesion.getIdJugador()>0){
                registrarPartida(false);
            }

            JugadorPierde objJugadorPierde = new JugadorPierde();
            objJugadorPierde.puntos.setText(String.valueOf(Sesion.getPuntosGanados()));
            objJugadorPierde.setVisible(true);
            
            objJugadorPierde.objControladorJugadorPierde.setObjControladorPartidaObjects(this);
            objJugadorPierde.objControladorJugadorPierde.setTipoPartida(3);
            
            timer.stop();
            this.objPartidaObjects.setVisible(false);

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
        comprobarCartasDeBot(this.objPartidaObjects.carta1Bot,
                this.objPartidaObjects.carta2Bot,
                this.objPartidaObjects.carta3Bot,
                this.objPartidaObjects.carta4Bot,
                this.objPartidaObjects.carta5Bot,
                this.objPartidaObjects.carta6Bot,
                this.objPartidaObjects.carta7Bot,
                this.objPartidaObjects.carta8Bot,
                this.objPartidaObjects.carta9Bot);
        verificarGanador();

        numerosGenerados.add(indexAleatorio);
        ImageIcon iconoSeleccionado = listaImagenes.get(indexAleatorio);

//        ImageIcon iconoCarta = new ImageIcon(getClass().getResource("/categorias/animales/bird.png"));
//        ImageIcon iconoCarta = new ImageIcon("/categorias/animales/bird.png");
        // Obtener el tamaño del JLabel
        JLabel label = (JLabel) this.objPartidaObjects.cartaCambiante; // Asegúrate de que sea un JLabel
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
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/objetos/marcadas/2.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);

    }
    
    public void establecerCartaCentral() {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
        JLabel label = (JLabel) this.objPartidaObjects.cartaCambiante; // Asegúrate de que sea un JLabel
        int width = label.getWidth();
        int height = label.getHeight();
// Redimensionar la imagen
        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Establecer el ícono redimensionado en el JLabel
        label.setIcon(scaledIcon);
    }
    
    private void reproducirSonido(int numeroCarta) {
        try {
        // Cargar el archivo de sonido
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/categorias/objetos/sonidos/" + numeroCarta + ".wav"));
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
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/categorias/objetos/sonidos/" + numeroCarta + ".wav"));
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.start(); // Reproducir el sonido
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace(); // Manejo de excepciones
//        }
//    }
    
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
