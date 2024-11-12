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
import vista.EscogerCategoria;
import vista.EscogerControl;
import vista.EscogerTablero;
import vista.Menu;

/**
 *
 * @author ezequielpena
 */
public class ControladorEscogerControl implements ActionListener {

    EscogerControl objEscogerControl;

    public ControladorEscogerControl() {
    }

    public ControladorEscogerControl(EscogerControl objEscogerControl) {
        this.objEscogerControl = objEscogerControl;
        this.objEscogerControl.botonMouse.addActionListener(this);
        this.objEscogerControl.botonArduino.addActionListener(this);
        this.objEscogerControl.botonBack.addActionListener(this);
        establecerIconos();
    }

    public void establecerIconos() {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/especiales/arduino.png"));
        int width = this.objEscogerControl.botonArduino.getWidth();
        int height = this.objEscogerControl.botonArduino.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.objEscogerControl.botonArduino.setIcon(scaledIcon);
        
        
        ImageIcon iconoImagenMouse = new ImageIcon(getClass().getResource("/especiales/mouse.png"));
        int widthMouse = this.objEscogerControl.botonMouse.getWidth();
        int heightMouse = this.objEscogerControl.botonMouse.getHeight();

        Image scaledImageMouse = iconoImagenMouse.getImage().getScaledInstance(widthMouse, heightMouse, Image.SCALE_SMOOTH);
        ImageIcon scaledIconMouse = new ImageIcon(scaledImageMouse);
        this.objEscogerControl.botonMouse.setIcon(scaledIconMouse);
        
        
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objEscogerControl.botonBack.getWidth();
        int heightBack = this.objEscogerControl.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objEscogerControl.botonBack.setIcon(scaledIconBack);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objEscogerControl.botonBack){
            Menu objMenu = new Menu();
            objMenu.setVisible(true);
            reproducirSonido();
            this.objEscogerControl.dispose();
            return;
        }
        if (e.getSource() == this.objEscogerControl.botonMouse) {
            EscogerCategoria objEscogerCategoria = new EscogerCategoria();
            objEscogerCategoria.setVisible(true);
//            EscogerTablero objEscogerTablero = new EscogerTablero();
//            objEscogerTablero.setVisible(true);
            reproducirSonido();
            this.objEscogerControl.dispose();
            return;
        }
        if (e.getSource() == this.objEscogerControl.botonArduino) {
            Configuracion.setOpcion(true);
            EscogerCategoria objEscogerCategoria = new EscogerCategoria();
            objEscogerCategoria.setVisible(true);
//            EscogerTablero objEscogerTablero = new EscogerTablero();
//            objEscogerTablero.setVisible(true);
            reproducirSonido();
            this.objEscogerControl.dispose();
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

}
