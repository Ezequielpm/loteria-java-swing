/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import vista.JugadorPierde;
import vista.Menu;

/**
 *
 * @author ezequielpena
 */
public class ControladorJugadorPierde implements ActionListener{
    JugadorPierde objJugadorPierde;
//    ControladorPartida
    
    ControladorPartida objControladorPartida; // controlador de la partida de animales
    ControladorPartidaJobs objControladorPartidaJobs; // controlador de la partida de jobs
    ControladorPartidaObjects objControladorPartidaObjects; // controlador de la partida de objects
    
    int tipoPartida = 0; // 1 -> animales, 2 -> jobs, 3 -> objetos

    public ControladorJugadorPierde(JugadorPierde objJugadorPierde) {
        this.objJugadorPierde = objJugadorPierde;
        this.objJugadorPierde.botonReiniciar.addActionListener(this);
        this.objJugadorPierde.botonHome.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objJugadorPierde.botonReiniciar){
            switch (tipoPartida) {
                case 1: //animales
                    this.objControladorPartida.objPartida.setVisible(true);
                    this.objControladorPartida.reiniciarPartida();
                    this.objJugadorPierde.dispose();
                    reproducirSonido();
                    break;
                case 2: //jobs
                    this.objControladorPartidaJobs.objPartidaJobs.setVisible(true);
                    this.objControladorPartidaJobs.reiniciarPartida();
                    this.objJugadorPierde.dispose();
                    reproducirSonido();
                    break;
                case 3: //objetos
                    this.objControladorPartidaObjects.objPartidaObjects.setVisible(true);
                    this.objControladorPartidaObjects.reiniciarPartida();
                    this.objJugadorPierde.dispose();
                    reproducirSonido();
                    break;
                default:
                    break;
            }
            return;
        }
        if(e.getSource()==this.objJugadorPierde.botonHome){
            Menu objMenu = new Menu();
            objMenu.setVisible(true);
            this.objJugadorPierde.dispose();
            reproducirSonido();
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

    public JugadorPierde getObjJugadorPierde() {
        return objJugadorPierde;
    }

    public void setObjJugadorPierde(JugadorPierde objJugadorPierde) {
        this.objJugadorPierde = objJugadorPierde;
    }

    public ControladorPartida getObjControladorPartida() {
        return objControladorPartida;
    }

    public void setObjControladorPartida(ControladorPartida objControladorPartida) {
        this.objControladorPartida = objControladorPartida;
    }

    public ControladorPartidaJobs getObjControladorPartidaJobs() {
        return objControladorPartidaJobs;
    }

    public void setObjControladorPartidaJobs(ControladorPartidaJobs objControladorPartidaJobs) {
        this.objControladorPartidaJobs = objControladorPartidaJobs;
    }

    public ControladorPartidaObjects getObjControladorPartidaObjects() {
        return objControladorPartidaObjects;
    }

    public void setObjControladorPartidaObjects(ControladorPartidaObjects objControladorPartidaObjects) {
        this.objControladorPartidaObjects = objControladorPartidaObjects;
    }

    public int getTipoPartida() {
        return tipoPartida;
    }

    public void setTipoPartida(int tipoPartida) {
        this.tipoPartida = tipoPartida;
    }
    
    
    
    
}
