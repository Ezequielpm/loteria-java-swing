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
import vista.DificultadObjects;
import vista.EscogerCategoria;
import vista.EscogerTableroJobs;
import vista.EscogerTableroObjects;

/**
 *
 * @author ezequielpena
 */
public class ControladorDificultadObjects implements ActionListener {

    DificultadObjects objDificultadObjects;

    public ControladorDificultadObjects() {
    }

    public ControladorDificultadObjects(DificultadObjects objDificultadObjects) {
        this.objDificultadObjects = objDificultadObjects;
        this.objDificultadObjects.botonBack.addActionListener(this);
        this.objDificultadObjects.botonEasy.addActionListener(this);
        this.objDificultadObjects.botonMedium.addActionListener(this);
        this.objDificultadObjects.botonHard.addActionListener(this);
        establecerIconos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objDificultadObjects.botonBack) {
            EscogerCategoria objEscogerCategoria = new EscogerCategoria();
            objEscogerCategoria.setVisible(true);
            reproducirSonido();

            this.objDificultadObjects.dispose();
            return;
        }
        if (e.getSource() == this.objDificultadObjects.botonEasy) {
            Configuracion.setDificultad("easy");
            Configuracion.setPresionarSinLimite(true);
            EscogerTableroObjects objEscogerTableroObjects = new EscogerTableroObjects();
            objEscogerTableroObjects.setVisible(true);
            reproducirSonido();

            this.objDificultadObjects.dispose();
            return;
        }
        if (e.getSource() == this.objDificultadObjects.botonMedium) {
            Configuracion.setDificultad("medium");
            Configuracion.setPresionarSinLimite(false);
            EscogerTableroObjects objEscogerTableroObjects = new EscogerTableroObjects();
            objEscogerTableroObjects.setVisible(true);
            reproducirSonido();

            this.objDificultadObjects.dispose();

            return;
        }
        if (e.getSource() == this.objDificultadObjects.botonHard) {
            Configuracion.setDificultad("hard");
            Configuracion.setPresionarSinLimite(false);
            EscogerTableroObjects objEscogerTableroObjects = new EscogerTableroObjects();
            objEscogerTableroObjects.setVisible(true);
            reproducirSonido();

            this.objDificultadObjects.dispose();

            return;
        }
    }
    
    public void establecerIconos(){
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objDificultadObjects.botonBack.getWidth();
        int heightBack = this.objDificultadObjects.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objDificultadObjects.botonBack.setIcon(scaledIconBack);
        
        
        //boton easy
        ImageIcon iconoImagenEasy = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__easy.png"));
        int widthEasy = this.objDificultadObjects.botonEasy.getWidth();
        int heightEasy = this.objDificultadObjects.botonEasy.getHeight();

        Image scaledImageEasy = iconoImagenEasy.getImage().getScaledInstance(widthEasy, heightEasy, Image.SCALE_SMOOTH);
        ImageIcon scaledIconEasy = new ImageIcon(scaledImageEasy);
        this.objDificultadObjects.botonEasy.setIcon(scaledIconEasy);
        
        
        //boton medium
        ImageIcon iconoImagenMedium = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__medium.png"));
        int widthMedium = this.objDificultadObjects.botonMedium.getWidth();
        int heightMedium = this.objDificultadObjects.botonMedium.getHeight();

        Image scaledImageMedium = iconoImagenMedium.getImage().getScaledInstance(widthMedium, heightMedium, Image.SCALE_SMOOTH);
        ImageIcon scaledIconMedium = new ImageIcon(scaledImageMedium);
        this.objDificultadObjects.botonMedium.setIcon(scaledIconMedium);
        
        
        //boton hard
        ImageIcon iconoImagenHard = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__hard.png"));
        int widthHard = this.objDificultadObjects.botonHard.getWidth();
        int heightHard = this.objDificultadObjects.botonHard.getHeight();

        Image scaledImageHard = iconoImagenHard.getImage().getScaledInstance(widthHard, heightHard, Image.SCALE_SMOOTH);
        ImageIcon scaledIconHard = new ImageIcon(scaledImageHard);
        this.objDificultadObjects.botonHard.setIcon(scaledIconHard);
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
