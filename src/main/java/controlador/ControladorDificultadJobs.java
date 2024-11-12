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
import vista.DificultadJobs;
import vista.EscogerCategoria;
import vista.EscogerTablero;
import vista.EscogerTableroJobs;

/**
 *
 * @author ezequielpena
 */
public class ControladorDificultadJobs implements ActionListener{
    DificultadJobs objDificultadJobs;

    public ControladorDificultadJobs() {
    }

    public ControladorDificultadJobs(DificultadJobs objDificultadJobs) {
        this.objDificultadJobs = objDificultadJobs;
        this.objDificultadJobs.botonBack.addActionListener(this);
        this.objDificultadJobs.botonEasy.addActionListener(this);
        this.objDificultadJobs.botonMedium.addActionListener(this);
        this.objDificultadJobs.botonHard.addActionListener(this);
        establecerIconos();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objDificultadJobs.botonBack){
            EscogerCategoria objEscogerCategoria = new EscogerCategoria();
            objEscogerCategoria.setVisible(true);
            reproducirSonido();
            
            this.objDificultadJobs.dispose();
            return;
        }
        if(e.getSource()==this.objDificultadJobs.botonEasy){
            Configuracion.setDificultad("easy");
            Configuracion.setPresionarSinLimite(true);
            EscogerTableroJobs objEscogerTableroJobs = new EscogerTableroJobs();
            objEscogerTableroJobs.setVisible(true);
            reproducirSonido();
            
            this.objDificultadJobs.dispose();
            return;
        }
        if(e.getSource()==this.objDificultadJobs.botonMedium){
            Configuracion.setDificultad("medium");
            Configuracion.setPresionarSinLimite(false);
            EscogerTableroJobs objEscogerTableroJobs = new EscogerTableroJobs();
            objEscogerTableroJobs.setVisible(true);
            reproducirSonido();
            
            this.objDificultadJobs.dispose();
            
            return;
        }
        if(e.getSource()==this.objDificultadJobs.botonHard){
            Configuracion.setDificultad("hard");
            Configuracion.setPresionarSinLimite(false);
            EscogerTableroJobs objEscogerTableroJobs = new EscogerTableroJobs();
            objEscogerTableroJobs.setVisible(true);
            reproducirSonido();
            
            this.objDificultadJobs.dispose();
            
            return;
        }
    }
    
    
    public void establecerIconos(){
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objDificultadJobs.botonBack.getWidth();
        int heightBack = this.objDificultadJobs.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objDificultadJobs.botonBack.setIcon(scaledIconBack);
        
        
        //boton easy
        ImageIcon iconoImagenEasy = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__easy.png"));
        int widthEasy = this.objDificultadJobs.botonEasy.getWidth();
        int heightEasy = this.objDificultadJobs.botonEasy.getHeight();

        Image scaledImageEasy = iconoImagenEasy.getImage().getScaledInstance(widthEasy, heightEasy, Image.SCALE_SMOOTH);
        ImageIcon scaledIconEasy = new ImageIcon(scaledImageEasy);
        this.objDificultadJobs.botonEasy.setIcon(scaledIconEasy);
        
        
        //boton medium
        ImageIcon iconoImagenMedium = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__medium.png"));
        int widthMedium = this.objDificultadJobs.botonMedium.getWidth();
        int heightMedium = this.objDificultadJobs.botonMedium.getHeight();

        Image scaledImageMedium = iconoImagenMedium.getImage().getScaledInstance(widthMedium, heightMedium, Image.SCALE_SMOOTH);
        ImageIcon scaledIconMedium = new ImageIcon(scaledImageMedium);
        this.objDificultadJobs.botonMedium.setIcon(scaledIconMedium);
        
        
        //boton hard
        ImageIcon iconoImagenHard = new ImageIcon(getClass().getResource("/iconos/tarjeta-dificultad__hard.png"));
        int widthHard = this.objDificultadJobs.botonHard.getWidth();
        int heightHard = this.objDificultadJobs.botonHard.getHeight();

        Image scaledImageHard = iconoImagenHard.getImage().getScaledInstance(widthHard, heightHard, Image.SCALE_SMOOTH);
        ImageIcon scaledIconHard = new ImageIcon(scaledImageHard);
        this.objDificultadJobs.botonHard.setIcon(scaledIconHard);
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
