/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import modelo.Carta;
import modelo.Tablero;
import vista.EscogerTableroObjects;
import vista.Partida;
import vista.PartidaObjects;

/**
 *
 * @author ezequielpena
 */
public class ControladorEscogerTableroObjects implements ActionListener {

    EscogerTableroObjects objEscogerTableroObjects;

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

    public ControladorEscogerTableroObjects() {
    }

    public ControladorEscogerTableroObjects(EscogerTableroObjects objEscogerTableroObjects) {
        this.objEscogerTableroObjects = objEscogerTableroObjects;
        this.objEscogerTableroObjects.tablero1.addActionListener(this);
        this.objEscogerTableroObjects.tablero2.addActionListener(this);
        this.objEscogerTableroObjects.tablero3.addActionListener(this);
        this.objEscogerTableroObjects.tablero4.addActionListener(this);
        this.objEscogerTableroObjects.tablero5.addActionListener(this);
        this.objEscogerTableroObjects.tablero6.addActionListener(this);

        this.objTablero = new Tablero();
        this.listaImagenes = new ArrayList<>();
        this.listaCartas = new ArrayList<>();
        this.listaCartasBot = new ArrayList<>();
        this.objTableroBot = new Tablero();
        mostrarTablero();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objEscogerTableroObjects.tablero1) {
//            Tablero objTablero = new Tablero();
            //1,2,3,4,5,6,7,,9,10    <--- iconos de cartas
            Integer[] rutasCartas
                    = {1, 2, 4,
                        3, 5, 6,
                        8, 7, 10};
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
        if (e.getSource() == this.objEscogerTableroObjects.tablero2) {
            Integer[] rutasCartas
                    = {9, 11, 12,
                        13, 14, 15,
                        16, 17, 18};

            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);

            obtenerCartasBot(2);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

//            iniciarPartida(objTablero);
            return;
        }
        if (e.getSource() == this.objEscogerTableroObjects.tablero3) {
            Integer[] rutasCartas
                    = {20, 19, 21,
                        22, 23, 24,
                        25, 26, 27};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);

            obtenerCartasBot(3);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

//            iniciarPartida(objTablero);
            return;
        }
        if (e.getSource() == this.objEscogerTableroObjects.tablero4) {
            Integer[] rutasCartas
                    = {28, 30, 29,
                        32, 31, 33,
                        34, 36, 35};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);

            obtenerCartasBot(4);
            objTableroBot.setListaCartas(listaCartasBot);
            iniciarPartida(objTablero, objTableroBot);

//            iniciarPartida(objTablero);
            return;
        }
        if (e.getSource() == this.objEscogerTableroObjects.tablero5) {
            Integer[] rutasCartas
                    = {37, 38, 39,
                        40, 1, 3,
                        8, 9, 13};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);

            obtenerCartasBot(5);
            objTableroBot.setListaCartas(listaCartasBot);

            iniciarPartida(objTablero, objTableroBot);
            return;
        }
        if (e.getSource() == this.objEscogerTableroObjects.tablero6) {
            Integer[] rutasCartas
                    = {16, 20, 22,
                        25, 28, 32,
                        34, 37, 40};
            obtenerCartas(rutasCartas);
            objTablero.setListaCartas(listaCartas);

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
            objCartaBot.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/objetos/imagenes/" + rutasCartasBot[indiceTableroBot][j] + ".png")));
            objCartaBot.setIdImagen(rutasCartasBot[indiceTableroBot][j]);
            listaCartasBot.add(objCartaBot);
        }
        //}
    }

    public void obtenerCartas(Integer[] rutasCartas) {
        for (int n : rutasCartas) {
            Carta objCarta = new Carta();
            objCarta.setIconoImagen(new ImageIcon(getClass().getResource("/categorias/objetos/imagenes/" + n + ".png")));
            objCarta.setIdImagen(n);
            listaCartas.add(objCarta);
        }
    }

    public void iniciarPartida(Tablero objTablero, Tablero objTableroBot) {
        PartidaObjects objPartida = new PartidaObjects();
        objPartida.setObjTablero(objTablero);
        objPartida.setObjTableroBot(objTableroBot); //
        objPartida.inicializarControlador();
        objPartida.setVisible(true);
        this.objEscogerTableroObjects.dispose();
    }

    public void mostrarTablero() {
        for (int i = 1; i <= 6; i++) {
            ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/categorias/objetos/tableros/" + i + ".png"));
            listaImagenes.add(iconoImagen);
        }
        ponerVistaTablero(this.objEscogerTableroObjects.tablero1,
                this.objEscogerTableroObjects.tablero2,
                this.objEscogerTableroObjects.tablero3,
                this.objEscogerTableroObjects.tablero4,
                this.objEscogerTableroObjects.tablero5,
                this.objEscogerTableroObjects.tablero6);

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

}
