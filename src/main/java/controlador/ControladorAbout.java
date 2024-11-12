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
import vista.About;
import vista.Menu;

/**
 *
 * @author ezequielpena
 */
public class ControladorAbout implements ActionListener{
    About objAbout;

    public ControladorAbout(About objAbout) {
        this.objAbout = objAbout;
        this.objAbout.botonBack.addActionListener(this);
        establecerIconos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objAbout.botonBack){
            Menu objMenu = new Menu();
            objMenu.setVisible(true);
            reproducirSonido();
            this.objAbout.dispose();
            return;
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
     
     public void establecerIconos() {
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objAbout.botonBack.getWidth();
        int heightBack = this.objAbout.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objAbout.botonBack.setIcon(scaledIconBack);
    }
    
}
