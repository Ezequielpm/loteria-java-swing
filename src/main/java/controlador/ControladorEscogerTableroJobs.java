/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import modelo.Carta;
import modelo.Tablero;
import vista.EscogerTableroJobs;
import vista.Partida;
import vista.PartidaJobs;

/**
 *
 * @author ezequielpena
 */
public class ControladorEscogerTableroJobs implements ActionListener {

    EscogerTableroJobs objEscogerTableroJobs;

    ArrayList<ImageIcon> listaImagenes;
    ArrayList<Carta> listaCartas;
    Tablero objTablero;
    ArrayList<Carta> listaCartasBot;
    Tablero objTableroBot;
    Integer[][] rutasCartasBot = {
        {1, 3, 4,
            2, 8, 6,
            5, 10, 9},
        {7, 14, 12,
            11, 15, 16,
            13, 17, 18},
        {20, 19, 21,
            22, 23, 24,
            25, 27, 26},
        {28, 31, 33,
            30, 29, 34,
            32, 35, 36},
        {37, 39, 38,
            40, 12, 25,
            7, 33, 6},
        {17, 27, 40,
            15, 24, 35,
            2, 13, 10}

    };

    public ControladorEscogerTableroJobs() {
    }

    public ControladorEscogerTableroJobs(EscogerTableroJobs objEscogerTableroJobs) {
        this.objEscogerTableroJobs = objEscogerTableroJobs;

        this.objEscogerTableroJobs.tablero1.addActionListener(this);
        this.objEscogerTableroJobs.tablero2.addActionListener(this);
        this.objEscogerTableroJobs.tablero3.addActionListener(this);
        this.objEscogerTableroJobs.tablero4.addActionListener(this);
        this.objEscogerTableroJobs.tablero5.addActionListener(this);
        this.objEscogerTableroJobs.tablero6.addActionListener(this);

        this.objTablero = new Tablero();
        this.listaImagenes = new ArrayList<>();
        this.listaCartas = new ArrayList<>();
        this.listaCartasBot = new ArrayList<>();
        this.objTableroBot = new Tablero();
        mostrarTablero();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objEscogerTableroJobs.tablero1) {
            Integer[] rutasCartas
                    = {1, 3, 4,
                        2, 8, 6,
                        5, 10, 9};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(1);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

            return;
        }
        if (e.getSource() == this.objEscogerTableroJobs.tablero2) {
            Integer[] rutasCartas
                    = {7, 14, 12,
                        11, 15, 16,
                        13, 17, 18};

            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(2);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

            return;
        }
        if (e.getSource() == this.objEscogerTableroJobs.tablero3) {
            Integer[] rutasCartas
                    = {20, 19, 21,
                        22, 23, 24,
                        25, 27, 26};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(3);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

            return;
        }
        if (e.getSource() == this.objEscogerTableroJobs.tablero4) {
            Integer[] rutasCartas
                    = {28, 31, 33,
                        30, 29, 34,
                        32, 35, 36};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(4);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

            return;
        }
        if (e.getSource() == this.objEscogerTableroJobs.tablero5) {
            Integer[] rutasCartas
                    = {37, 39, 38,
                        40, 12, 25,
                        7, 33, 6};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(5);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

            return;
        }
        if (e.getSource() == this.objEscogerTableroJobs.tablero6) {
            Integer[] rutasCartas
                    = {17, 27, 40,
                        15, 24, 35,
                        2, 13, 10};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(6);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

            return;
        }
    }

    public void obtenerCartasBot(int excepcion) {
        Random r = new Random();
        int indiceTableroBot = 0;
        do {
            indiceTableroBot = r.nextInt(6);
        } while (indiceTableroBot == excepcion - 1);
        //for (int i = indiceTableroBot; i == indiceTableroBot; i++) {
        for (int j = 0; j < 9; j++) {
            Carta objCartaBot = new Carta();
            objCartaBot.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/profesiones/imagenes/" + rutasCartasBot[indiceTableroBot][j] + ".png")));
            objCartaBot.setIdImagen(rutasCartasBot[indiceTableroBot][j]);
            listaCartasBot.add(objCartaBot);
        }
        //}
    }

    public void obtenerCartas(Integer[] rutasCartas) {
        for (int n : rutasCartas) {
            Carta objCarta = new Carta();
            objCarta.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/profesiones/imagenes/" + n + ".png")));
            objCarta.setIdImagen(n);
            listaCartas.add(objCarta);
        }
    }

    public void iniciarPartida(Tablero objTablero, Tablero objTableroBot) {
        PartidaJobs objPartidaJobs = new PartidaJobs();
        objPartidaJobs.setObjTablero(objTablero);
        objPartidaJobs.setObjTableroBot(objTableroBot); //
        objPartidaJobs.inicializarControlador();
        objPartidaJobs.setVisible(true);
        this.objEscogerTableroJobs.dispose();
    }

    public void mostrarTablero() {
        for (int i = 1; i <= 6; i++) {
            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/profesiones/tableros/" + i + ".png"));
            listaImagenes.add(iconoImagen);
        }
        ponerVistaTablero(this.objEscogerTableroJobs.tablero1,
                this.objEscogerTableroJobs.tablero2,
                this.objEscogerTableroJobs.tablero3,
                this.objEscogerTableroJobs.tablero4,
                this.objEscogerTableroJobs.tablero5,
                this.objEscogerTableroJobs.tablero6);

//        for (int i = 1; i <= 6; i++) {
//
//        }
//        ImageIcon iconoSeleccionado = listaImagenes.get(0);
//
//        // Obtener el tamaño del JLabel
//        JButton tablero = (JButton) this.objEscogerTablero.tablero1; // Asegúrate de que sea un JLabel
//        int width = tablero.getWidth();
//        int height = tablero.getHeight();
//
//        // Redimensionar la imagen
//        Image scaledImage = iconoSeleccionado.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        ImageIcon scaledIcon = new ImageIcon(scaledImage);
//
//        // Establecer el ícono redimensionado en el JLabel
//        tablero.setIcon(scaledIcon);
    }

    public void ponerVistaTablero(JButton... botones) {
        int i = 0;
        for (JButton tablero : botones) {
            ImageIcon iconoSeleccionado = listaImagenes.get(i);

            int width = tablero.getWidth();
            int height = tablero.getHeight();

            Image scaledImage = iconoSeleccionado.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            tablero.setIcon(scaledIcon);
            i++;
        }
    }
    
    public void reproducirSonido() {
        String rutaArchivo = "/sonidos/clics/clicBoton.wav";
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
