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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import modelo.Usuario;
import vista.Dificultad;
import vista.JugadorGana;
import vista.JugadorPierde;
import vista.Partida;

/**
 *
 * @author ezequielpena
 */
public class ControladorPartida implements ActionListener {

    Partida objPartida;
    private Timer timer; // Temporizador para cambiar la carta
    private Random random; // Generador de números aleatorios
    ArrayList<ImageIcon> listaImagenes;
    ArrayList<Integer> numerosGenerados;

    Tablero objTablero;
    int idCartaLanzada = 0;
    int puntosJugador = 0;

    ArrayList<Integer> idCartas;
    ArrayList<Integer> idCartasBot;

    Tablero objTableroBot;
    int contadorAciertosJugador = 0;
    int contadorAciertosBot = 0;
    int retrasoDificultad = 0;
    int tipoDificultad = 0;
    int idCategoria = 1;
    int puntosQueGana = 0;
    OperacionesDBPartida objOperacionesDBPartida;

    public ControladorPartida() {
    }

    public ControladorPartida(Partida objPartida, Tablero objTablero, Tablero objTableroBot) {
        this.objOperacionesDBPartida = new OperacionesDBPartida();
        this.objPartida = objPartida;
//        this.objPartida.botonBack.addActionListener(this);
        listaImagenes = new ArrayList<>();
        random = new Random();
        numerosGenerados = new ArrayList<>();
        this.objTableroBot = objTableroBot;
        obtenerCartas();

        this.objTablero = objTablero;
        idCartas = new ArrayList<>();
        idCartasBot = new ArrayList<>();

        mostrarCartasJugador(this.objPartida.carta1,
                this.objPartida.carta2,
                this.objPartida.carta3,
                this.objPartida.carta4,
                this.objPartida.carta5,
                this.objPartida.carta6,
                this.objPartida.carta7,
                this.objPartida.carta8,
                this.objPartida.carta9);

        mostrarCartasBot(this.objPartida.carta1Bot,
                this.objPartida.carta2Bot,
                this.objPartida.carta3Bot,
                this.objPartida.carta4Bot,
                this.objPartida.carta5Bot,
                this.objPartida.carta6Bot,
                this.objPartida.carta7Bot,
                this.objPartida.carta8Bot,
                this.objPartida.carta9Bot
        );
        this.objPartida.botonRestart.addActionListener(this);
        this.objPartida.botonBack.addActionListener(this);

//        this.objPartida.carta1.addActionListener(this);
//        this.objPartida.carta2.addActionListener(this);
//        this.objPartida.carta3.addActionListener(this);
//        this.objPartida.carta4.addActionListener(this);
//        this.objPartida.carta5.addActionListener(this);
//        this.objPartida.carta6.addActionListener(this);
//        this.objPartida.carta7.addActionListener(this);
//        this.objPartida.carta8.addActionListener(this);
//        this.objPartida.carta9.addActionListener(this);
//        this.objPartida.botonRestart.addActionListener(this);
        obtenerIdCartas();
        obtenerIdCartasBot();
        comprobarConfiguracion();
    }

    Thread hiloComprobacion;

    public void subirPuntosJugador() {
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
                        comprobarMatch(this.objPartida.carta1,
                                this.objPartida.carta2,
                                this.objPartida.carta3,
                                this.objPartida.carta4,
                                this.objPartida.carta5,
                                this.objPartida.carta6,
                                this.objPartida.carta7,
                                this.objPartida.carta8,
                                this.objPartida.carta9);

                    }

                }).start();
            } else {
                System.out.println("No se pudo establecer conexión.");
            }
        } else {
            this.objPartida.carta1.addActionListener(this);
            this.objPartida.carta2.addActionListener(this);
            this.objPartida.carta3.addActionListener(this);
            this.objPartida.carta4.addActionListener(this);
            this.objPartida.carta5.addActionListener(this);
            this.objPartida.carta6.addActionListener(this);
            this.objPartida.carta7.addActionListener(this);
            this.objPartida.carta8.addActionListener(this);
            this.objPartida.carta9.addActionListener(this);
        }

        if (Configuracion.getDificultad().equals("easy")) {
            retrasoDificultad = 10000;
            tipoDificultad = 1;
            puntosQueGana = 15;
            iniciarTemporizador(retrasoDificultad);

        } else if (Configuracion.getDificultad().equals("medium")) {
            retrasoDificultad = 7000;
            tipoDificultad = 2;
            puntosQueGana = 30;
            iniciarTemporizador(retrasoDificultad);
        } else if (Configuracion.getDificultad().equals("hard")) {
            retrasoDificultad = 1200;
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
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta1.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta1.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta1);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta1.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta1.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta1.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta1);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta1.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    idCartasMarcadas.add(idCartaLanzada);
//                    marcarCartaArduino(this.objPartida.carta1);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_2_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta2.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta2.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta2);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta2.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta2.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta2.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta2);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta2.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta2);
//                    reproducirSonido();
//                    subirPuntosJugador();
//
//                    return;
//                }
                break;
            case "BOTON_3_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta3.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta3.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta3);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta3.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta3.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta3.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta3);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta3.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta3);
//                    reproducirSonido();
//                    subirPuntosJugador();;
//                    return;
//                }
                break;
            case "BOTON_4_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta4.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta4.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta4);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta4.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta4.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta4.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta4);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta4.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta4);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_5_PRESIONADO":
                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta5.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta5.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta5);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta5.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta5.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta5.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta5);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta5.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta5);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_6_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta6.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta6.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta6);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta6.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta6.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta6.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta6);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta6.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta6);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_7_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta7.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta7.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta7);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta7.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta7.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta7.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta7);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta7.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta7);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_8_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta8.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta8.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta8);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta8.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta8.getName()))) {
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta8.getName()));
                        subirPuntosJugador();
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta8);
                        reproducirSonido();
                    }
                }
//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta8.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta8);
//                    reproducirSonido();
//                    subirPuntosJugador();
//                    return;
//                }
                break;
            case "BOTON_9_PRESIONADO":

                if (idCartas.contains(idCartaLanzada)) {

                    if (!Configuracion.isPresionarSinLimite()) {
                        if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta9.getName()))) {
                            return;
                        }
                        if (Integer.parseInt(this.objPartida.carta9.getName()) == idCartaLanzada) {
                            System.err.println("ESTA ES LA CARTA");
                            subirPuntosJugador();
                            idCartasMarcadas.add(idCartaLanzada);
                            ArduinoConnector.setBotonPresionado("99");
                            marcarCartaArduino(this.objPartida.carta9);
                            reproducirSonido();

                            return;
                        }

                    }
                }

                if (Configuracion.isPresionarSinLimite()) {
                    if (idCartasMarcadas.contains(Integer.parseInt(this.objPartida.carta9.getName()))) {
                        return;
                    }

                    if (idCartasLanzadas.contains(Integer.parseInt(this.objPartida.carta9.getName()))) {
                        subirPuntosJugador();
                        idCartasMarcadas.add(Integer.parseInt(this.objPartida.carta9.getName()));
                        ArduinoConnector.setBotonPresionado("99");
                        marcarCartaArduino(this.objPartida.carta9);
                        reproducirSonido();
                    }
                }

//                if (idCartaLanzada == Integer.parseInt(this.objPartida.carta9.getName())) {
//                    ArduinoConnector.setBotonPresionado("99");
//                    marcarCartaArduino(this.objPartida.carta9);
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
            Sesion.setPuntosGanados(contadorAciertosJugador); // para mostrar los puntos en la vista de ganar

            registrarPartida(true);

            JugadorGana objJugadorGana = new JugadorGana();
            objJugadorGana.puntos.setText(String.valueOf(Sesion.getPuntosGanados()));
            objJugadorGana.setVisible(true);

            timer.stop();
        } else if (contadorAciertosBot > 8) {
            Sesion.setPuntosGanados(contadorAciertosJugador);

            registrarPartida(false);

            JugadorPierde objJugadorPierde = new JugadorPierde();
            objJugadorPierde.puntos.setText(String.valueOf(Sesion.getPuntosGanados()));
            objJugadorPierde.setVisible(true);
            timer.stop();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objPartida.botonBack) {
            this.timer.stop();
            Dificultad objVistaDificultad = new Dificultad();
            objVistaDificultad.setVisible(true);
            this.objPartida.dispose();
            return;
        }
        if (e.getSource() == this.objPartida.carta1) {
            comprobarCartaSeleccionada(this.objPartida.carta1, Configuracion.isPresionarSinLimite());

//            comprobarCartaSeleccionada(Integer.parseInt(this.objPartida.carta1.getName()));
            System.out.println("presionado");
            return;
        }
        if (e.getSource() == this.objPartida.carta2) {
            comprobarCartaSeleccionada(this.objPartida.carta2, Configuracion.isPresionarSinLimite());

            System.out.println("presionado");

            return;
        }
        if (e.getSource() == this.objPartida.carta3) {
            comprobarCartaSeleccionada(this.objPartida.carta3, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.carta4) {
            comprobarCartaSeleccionada(this.objPartida.carta4, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.carta5) {
            comprobarCartaSeleccionada(this.objPartida.carta5, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.carta6) {
            comprobarCartaSeleccionada(this.objPartida.carta6, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.carta7) {
            comprobarCartaSeleccionada(this.objPartida.carta7, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.carta8) {
            comprobarCartaSeleccionada(this.objPartida.carta8, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.carta9) {
            comprobarCartaSeleccionada(this.objPartida.carta9, Configuracion.isPresionarSinLimite());

            return;
        }
        if (e.getSource() == this.objPartida.botonRestart) {
            timer.stop();
            mostrarCartasJugador(this.objPartida.carta1,
                    this.objPartida.carta2,
                    this.objPartida.carta3,
                    this.objPartida.carta4,
                    this.objPartida.carta5,
                    this.objPartida.carta6,
                    this.objPartida.carta7,
                    this.objPartida.carta8,
                    this.objPartida.carta9);

            mostrarCartasBot(this.objPartida.carta1Bot,
                    this.objPartida.carta2Bot,
                    this.objPartida.carta3Bot,
                    this.objPartida.carta4Bot,
                    this.objPartida.carta5Bot,
                    this.objPartida.carta6Bot,
                    this.objPartida.carta7Bot,
                    this.objPartida.carta8Bot,
                    this.objPartida.carta9Bot
            );
            contadorAciertosBot = 0;
            contadorAciertosJugador = 0;
            numerosGenerados.clear();

            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/load.png"));
            JLabel label = (JLabel) this.objPartida.cartaCambiante; // Asegúrate de que sea un JLabel
            int width = label.getWidth();
            int height = label.getHeight();
// Redimensionar la imagen
            Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Establecer el ícono redimensionado en el JLabel
            label.setIcon(scaledIcon);
//            Thread.sleep(5000);
            iniciarTemporizador(retrasoDificultad);
            return;
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
            System.out.println("Puntos jugador: " + puntosJugador);
            return;
        }
        comprobarCartasDeBot(this.objPartida.carta1Bot,
                this.objPartida.carta2Bot,
                this.objPartida.carta3Bot,
                this.objPartida.carta4Bot,
                this.objPartida.carta5Bot,
                this.objPartida.carta6Bot,
                this.objPartida.carta7Bot,
                this.objPartida.carta8Bot,
                this.objPartida.carta9Bot);

        numerosGenerados.add(indexAleatorio);
        ImageIcon iconoSeleccionado = listaImagenes.get(indexAleatorio);

//        ImageIcon iconoCarta = new ImageIcon(getClass().getResource("/categorias/animales/bird.png"));
//        ImageIcon iconoCarta = new ImageIcon("/categorias/animales/bird.png");
        // Obtener el tamaño del JLabel
        JLabel label = (JLabel) this.objPartida.cartaCambiante; // Asegúrate de que sea un JLabel
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

    public void obtenerCartas() {
//        ImageIcon[] listaRutasImagenes;
        for (int i = 1; i <= 40; i++) {
            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/animales/imagenes/" + i + ".png"));
            listaImagenes.add(iconoImagen);
        }
    }

    private void iniciarTemporizador(int retraso) {
        // Crear un nuevo Timer que se ejecute cada 3000 milisegundos (3 segundos)
        timer = new Timer(retraso, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarCartas(); // Cambiar las cartas
                verificarGanador();
//                if (contadorAciertosBot > 8) {
//                    JOptionPane.showMessageDialog(objPartida, "You lose! :(");
//                    timer.stop();
//                    System.out.println("Puntos jugador: " + puntosJugador);
//                } else if (contadorAciertosJugador > 8) {
//                    JOptionPane.showMessageDialog(objPartida, "You win");
//                    timer.stop();
//                    System.out.println("Puntos jugador: " + puntosJugador);
//                }
            }
        });

        // Iniciar el temporizador
        timer.start();
    }

    private void reproducirSonido(int numeroCarta) {
        try {
        // Cargar el archivo de sonido
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/categorias/animales/sonidos/" + numeroCarta + ".wav"));
        Clip clip = AudioSystem.getClip();
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

//    public void comprobarCartaSeleccionada(int indiceBoton) {
//        if (idCartas.contains(idCartaLanzada)) {
//            if (indiceBoton == idCartaLanzada) {
//                System.err.println("ESTA ES LA CARTA");
//            }
//        } else {
//            return;
//        }
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
            }
        }
        return;
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

    public void marcarCarta(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/animales/marcadas/1.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);
        reproducirSonido();
        puntosJugador += 1;
    }

    public void marcarCartaArduino(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/animales/marcadas/1.png"));
        int width = cartaSeleccionada.getWidth();
        int height = cartaSeleccionada.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cartaSeleccionada.setIcon(scaledIcon);
//        reproducirSonido();
    }

    public void marcarCartaBot(JButton cartaSeleccionada) {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/animales/marcadas/2.png"));
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
