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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import modelo.Configuracion;
import modelo.Sesion;
import modelo.Usuario;
import vista.Dificultad;
import vista.EscogerCategoria;
import vista.EscogerTablero;
import vista.Menu;
import vista.Partida;

/**
 *
 * @author ezequielpena
 */
public class ControladorDificultad implements ActionListener {

    Dificultad objDificultad;

    public ControladorDificultad() {
    }

    public ControladorDificultad(Dificultad objDificultad) {
        this.objDificultad = objDificultad;
        this.objDificultad.botonBack.addActionListener(this);
        this.objDificultad.botonEasy.addActionListener(this);
        this.objDificultad.botonMedium.addActionListener(this);
        this.objDificultad.botonHard.addActionListener(this);

        establecerIconos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objDificultad.botonBack) {
            reproducirSonido();

            EscogerCategoria objEscogerCategoria = new EscogerCategoria();
            objEscogerCategoria.setVisible(true);

//            Menu objMenu = new Menu();
//            objMenu.setVisible(true);
            this.objDificultad.dispose();
            return;
        }
        if (e.getSource() == this.objDificultad.botonEasy) {
            Configuracion.setDificultad("easy");
            reproducirSonido();

            Configuracion.setPresionarSinLimite(true);
            EscogerTablero objEscogerTablero = new EscogerTablero();
            objEscogerTablero.setVisible(true);

            this.objDificultad.dispose();
            return;
        }
        if (e.getSource() == this.objDificultad.botonMedium) {
            Configuracion.setDificultad("medium");
            reproducirSonido();

            Configuracion.setPresionarSinLimite(false);
            EscogerTablero objEscogerTablero = new EscogerTablero();
            objEscogerTablero.setVisible(true);

            this.objDificultad.dispose();

            return;
        }
        if (e.getSource() == this.objDificultad.botonHard) {
            Configuracion.setDificultad("hard");
            reproducirSonido();

            Configuracion.setPresionarSinLimite(false);
            EscogerTablero objEscogerTablero = new EscogerTablero();
            objEscogerTablero.setVisible(true);

            this.objDificultad.dispose();
            return;
        }
    }

    public void establecerIconos() {
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objDificultad.botonBack.getWidth();
        int heightBack = this.objDificultad.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objDificultad.botonBack.setIcon(scaledIconBack);

        //boton easy
        ImageIcon iconoImagenEasy = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__easy.png"));
        int widthEasy = this.objDificultad.botonEasy.getWidth();
        int heightEasy = this.objDificultad.botonEasy.getHeight();

        Image scaledImageEasy = iconoImagenEasy.getImage().getScaledInstance(widthEasy, heightEasy, Image.SCALE_SMOOTH);
        ImageIcon scaledIconEasy = new ImageIcon(scaledImageEasy);
        this.objDificultad.botonEasy.setIcon(scaledIconEasy);

        //boton medium
        ImageIcon iconoImagenMedium = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__medium.png"));
        int widthMedium = this.objDificultad.botonMedium.getWidth();
        int heightMedium = this.objDificultad.botonMedium.getHeight();

        Image scaledImageMedium = iconoImagenMedium.getImage().getScaledInstance(widthMedium, heightMedium, Image.SCALE_SMOOTH);
        ImageIcon scaledIconMedium = new ImageIcon(scaledImageMedium);
        this.objDificultad.botonMedium.setIcon(scaledIconMedium);

        //boton hard
        ImageIcon iconoImagenHard = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__hard.png"));
        int widthHard = this.objDificultad.botonHard.getWidth();
        int heightHard = this.objDificultad.botonHard.getHeight();

        Image scaledImageHard = iconoImagenHard.getImage().getScaledInstance(widthHard, heightHard, Image.SCALE_SMOOTH);
        ImageIcon scaledIconHard = new ImageIcon(scaledImageHard);
        this.objDificultad.botonHard.setIcon(scaledIconHard);
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
