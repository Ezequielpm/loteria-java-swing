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
import vista.EscogerTablero;
import vista.EscogerTableroJobs;
import vista.Partida;

/**
 *
 * @author ezequielpena
 */
public class ControladorEscogerTablero implements ActionListener {

    EscogerTablero objEscogerTablero;
    ArrayList<ImageIcon> listaImagenes;
    ArrayList<Carta> listaCartas;
    Tablero objTablero;
    ArrayList<Carta> listaCartasBot;
    Tablero objTableroBot;
    Integer[][] rutasCartasBot = {
        {2, 3, 4, 1, 7, 9, 6, 5, 10},
        {8, 11, 4, 13, 14, 15, 16, 2, 12},
        {23, 25, 17, 18, 20, 24, 21, 22, 19},
        {21, 18, 15, 31, 30, 29, 28, 27, 26},
        {40, 39, 38, 37, 36, 35, 34, 33, 32},
        {40, 33, 20, 16, 21, 35, 1, 9, 6}

    };

    public ControladorEscogerTablero() {
    }

    public ControladorEscogerTablero(EscogerTablero objEscogerTablero) {
        this.objEscogerTablero = objEscogerTablero;
        this.objEscogerTablero.tablero1.addActionListener(this);
        this.objEscogerTablero.tablero2.addActionListener(this);
        this.objEscogerTablero.tablero3.addActionListener(this);
        this.objEscogerTablero.tablero4.addActionListener(this);
        this.objEscogerTablero.tablero5.addActionListener(this);
        this.objEscogerTablero.tablero6.addActionListener(this);

        this.objTablero = new Tablero();
        this.listaImagenes = new ArrayList<>();
        this.listaCartas = new ArrayList<>();
        this.listaCartasBot = new ArrayList<>();
        this.objTableroBot = new Tablero();
        mostrarTablero();
    }

    /*
    1.⁠ ⁠Elephant
	2.	Lion
	3.	Tiger
	4.	Bear
	5.	Wolf
	6.	Giraffe
	7.	Zebra
	8.	Rhinoceros
	9.	Hippopotamus
	10.	Crocodile
	11.	Dolphin
	12.	Shark
	13.	Eagle
	14.	Falcon
	15.	Owl
	16.	Penguin
	17.	Camel
	18.	Kangaroo
	19.	Koala
	20.	Gorilla
	21.	Chimpanzee
	22.	Panda
	23.	Lemur
	24.	Lynx
	25.	Snake
	26.	Turtle
	27.	Flamingo
	28.	Peacock
	29.	Raccoon
	30.	Fox
	31.	Deer
	32.	Bat
	33.	Hedgehog
	34.	Moose
	35.	Walrus
	36.	Whale
	37.	Porcupine
	38.	Snail
	39.	Ant
	40.	Bee
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objEscogerTablero.tablero1) {
//            Tablero objTablero = new Tablero();
            //1,2,3,4,5,6,7,,9,10    <--- iconos de cartas
            Integer[] rutasCartas
                    = {2, 3, 4,
                        1, 7, 9,
                        6, 5, 10};
//            ArrayList<Carta> listaCartas = new ArrayList<>();
//            for (int n : rutasCartas) {
//                Carta objCarta = new Carta();
//                objCarta.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/animales/imagenes/" + n + ".png")));
//                listaCartas.add(objCarta);
//            }
            //llenar las rutas de las imagenes con image icon
//            for (int i = 1; i <= 40; i++) {
//                Carta objCarta = new Carta();
//                objCarta.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/animales/imagenes/"+i+".png")));
//                listaCartas.add(objCarta);
//            }
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(1);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

//            iniciarPartida(objTablero);
//            Partida objPartida = new Partida();
//            objPartida.setObjTablero(objTablero);
//            objPartida.inicializarControlador();
//            objPartida.setVisible(true);
//            this.objEscogerTablero.dispose();
            return;
        }
        if (e.getSource() == this.objEscogerTablero.tablero2) {
            Integer[] rutasCartas
                    = {8, 11, 4,
                        13, 14, 15,
                        16, 2, 12};

            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(2);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

//            iniciarPartida(objTablero);
            return;
        }
        if (e.getSource() == this.objEscogerTablero.tablero3) {
            Integer[] rutasCartas
                    = {23, 25, 17,
                        18, 20, 24,
                        21, 22, 19};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(3);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

//            iniciarPartida(objTablero);
            return;
        }
        if (e.getSource() == this.objEscogerTablero.tablero4) {
            Integer[] rutasCartas
                    = {21, 18, 15,
                        31, 30, 29,
                        28, 27, 26};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);

            reproducirSonido();

            obtenerCartasBot(4);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);
            reproducirSonido();

//            iniciarPartida(objTablero);
            return;
        }
        if (e.getSource() == this.objEscogerTablero.tablero5) {
            Integer[] rutasCartas
                    = {40, 39, 38,
                        37, 36, 35,
                        34, 33, 32};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);
            reproducirSonido();

            obtenerCartasBot(5);
            objTableroBot.setListaCartas(listaCartasBot);

            iniciarPartida(objTablero, objTableroBot);
            return;
        }
        if (e.getSource() == this.objEscogerTablero.tablero6) {
            Integer[] rutasCartas
                    = {40, 33, 20,
                        16, 21, 35,
                        1, 9, 6};
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
            objCartaBot.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/animales/imagenes/" + rutasCartasBot[indiceTableroBot][j] + ".png")));
            objCartaBot.setIdImagen(rutasCartasBot[indiceTableroBot][j]);
            listaCartasBot.add(objCartaBot);
        }
        //}
    }

    public void obtenerCartas(Integer[] rutasCartas) {
        for (int n : rutasCartas) {
            Carta objCarta = new Carta();
            objCarta.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/animales/imagenes/" + n + ".png")));
            objCarta.setIdImagen(n);
            listaCartas.add(objCarta);
        }
    }

    public void iniciarPartida(Tablero objTablero, Tablero objTableroBot) {
        Partida objPartida = new Partida();
        objPartida.setObjTablero(objTablero);
        objPartida.setObjTableroBot(objTableroBot); //
        objPartida.inicializarControlador();
        objPartida.setVisible(true);
        this.objEscogerTablero.dispose();
    }

    public void mostrarTablero() {
        for (int i = 1; i <= 6; i++) {
            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/animales/tableros/" + i + ".png"));
            listaImagenes.add(iconoImagen);
        }
        ponerVistaTablero(this.objEscogerTablero.tablero1,
                this.objEscogerTablero.tablero2,
                this.objEscogerTablero.tablero3,
                this.objEscogerTablero.tablero4,
                this.objEscogerTablero.tablero5,
                this.objEscogerTablero.tablero6);

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
