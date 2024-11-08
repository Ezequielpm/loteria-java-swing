/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Sesion;
import vista.Dificultad;
import vista.EscogerCategoria;
import vista.EscogerControl;
import vista.Menu;

/**
 *
 * @author ezequielpena
 */
public class ControladorMenu implements ActionListener {

    Menu objMenu;

    public ControladorMenu() {
    }

    public ControladorMenu(Menu objMenu) {
        this.objMenu = objMenu;
        this.objMenu.botonPlay.addActionListener(this);
        this.objMenu.botonProfile.addActionListener(this);
        this.objMenu.botonSettings.addActionListener(this);
        this.objMenu.botonAbout.addActionListener(this);
        this.objMenu.botonExit.addActionListener(this);
        establecerIconos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objMenu.botonPlay) {
            EscogerControl objEscogerControl = new EscogerControl();
            objEscogerControl.setVisible(true);
//            EscogerCategoria objEscogerCategoria = new EscogerCategoria();
//            objEscogerCategoria.setVisible(true);
//            Dificultad objDificultad = new Dificultad();
//            objDificultad.setVisible(true);
            reproducirSonido();

            this.objMenu.dispose();

            return;
        }
        if (e.getSource() == this.objMenu.botonProfile) {
            
            reproducirSonido();
            if(Sesion.getInstance().getSesionUsuario() == null){
                JOptionPane.showMessageDialog(this.objMenu, "Aun no has iniciado sesion!");
            }
            return;
        }
        if (e.getSource() == this.objMenu.botonSettings) {

            reproducirSonido();
            return;
        }
        if (e.getSource() == this.objMenu.botonAbout) {

            reproducirSonido();
            return;
        }
        if (e.getSource() == this.objMenu.botonExit) {

            reproducirSonido();
            System.exit(0);
            return;
        }
    }

    public void reproducirSonido() {
        String rutaArchivo = "/sonidos/clics/clicBoton.wav";
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
    
    public void establecerIconos() {
        // play
        ImageIcon iconoImagenPlay = new ImageIcon(getClass().getResource("/botones/btn-play.png"));
        int widthPlay = this.objMenu.botonPlay.getWidth();
        int heightPlay = this.objMenu.botonPlay.getHeight();

        Image scaledImage = iconoImagenPlay.getImage().getScaledInstance(widthPlay, heightPlay, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.objMenu.botonPlay.setIcon(scaledIcon);
        
        //profile
        ImageIcon iconoImagenProfile = new ImageIcon(getClass().getResource("/botones/btn-profile.png"));
        int widthProfile = this.objMenu.botonProfile.getWidth();
        int heightProfile = this.objMenu.botonProfile.getHeight();

        Image scaledImageProfile = iconoImagenProfile.getImage().getScaledInstance(widthProfile, heightProfile, Image.SCALE_SMOOTH);
        ImageIcon scaledIconProfile = new ImageIcon(scaledImageProfile);
        this.objMenu.botonProfile.setIcon(scaledIconProfile);
        
        //setings
        ImageIcon iconoImagenSettings = new ImageIcon(getClass().getResource("/botones/btn-settings.png"));
        int widthSettings = this.objMenu.botonSettings.getWidth();
        int heightSettings = this.objMenu.botonSettings.getHeight();

        Image scaledImageSettings = iconoImagenSettings.getImage().getScaledInstance(widthSettings, heightSettings, Image.SCALE_SMOOTH);
        ImageIcon scaledIconSettings = new ImageIcon(scaledImageSettings);
        this.objMenu.botonSettings.setIcon(scaledIconSettings);
        
        
        //about
        ImageIcon iconoImagenAbout = new ImageIcon(getClass().getResource("/botones/btn-about.png"));
        int widthAbout = this.objMenu.botonAbout.getWidth();
        int heightAbout = this.objMenu.botonAbout.getHeight();

        Image scaledImageAbout = iconoImagenAbout.getImage().getScaledInstance(widthAbout, heightAbout, Image.SCALE_SMOOTH);
        ImageIcon scaledIconAbout = new ImageIcon(scaledImageAbout);
        this.objMenu.botonAbout.setIcon(scaledIconAbout);
        
        
        //exit
        ImageIcon iconoImagenExit = new ImageIcon(getClass().getResource("/botones/btn-exit.png"));
        int widthExit = this.objMenu.botonExit.getWidth();
        int heightExit = this.objMenu.botonExit.getHeight();

        Image scaledImageExit = iconoImagenExit.getImage().getScaledInstance(widthExit, heightExit, Image.SCALE_SMOOTH);
        ImageIcon scaledIconExit = new ImageIcon(scaledImageExit);
        this.objMenu.botonExit.setIcon(scaledIconExit);
        
    }


}
