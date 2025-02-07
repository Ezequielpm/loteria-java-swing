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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Configuracion;
import modelo.Sesion;
import modelo.Usuario;
import vista.IniciarSesion;
import vista.Menu;
import vista.Registro;

/**
 *
 * @author ezequielpena
 */
public class ControladorIniciarSesion implements ActionListener {

    IniciarSesion objIniciarSesion;
    Validador objValidador;
    Usuario objUsuario;
    OperacionesBDUsuario objOperacionesBDUsuario;

    public ControladorIniciarSesion() {
    }

    public ControladorIniciarSesion(IniciarSesion objIniciarSesion) {
        this.objIniciarSesion = objIniciarSesion;
        objValidador = new Validador();
        objOperacionesBDUsuario = new OperacionesBDUsuario();
        this.objIniciarSesion.botonLogin.addActionListener(this);
        this.objIniciarSesion.botonHere.addActionListener(this);
        this.objIniciarSesion.botonSkip.addActionListener(this);
        establecerIconos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objIniciarSesion.botonLogin) {
            reproducirSonido();
            boolean flagUsuarioEncontrado = false;
            int flagContrasenia = 0;
            if (!this.objValidador.validaCajaTextoCadena(this.objIniciarSesion.entradaUser)) {
                return;
            }
            if (!this.objValidador.validaCajaTextoCadena(this.objIniciarSesion.entradaPassword)) {
                return;
            }
//            
//            objUsuario = new Usuario();
//            objUsuario.setUser(this.objIniciarSesion.entradaUser.getText());
//            objUsuario.setContrasenia(this.objIniciarSesion.entradaPassword.getText());

//            objOperacionesBDUsuario.setObjUsuario(objUsuario);
            ArrayList<Usuario> listaUsuarios = objOperacionesBDUsuario.read();
            Usuario sesionActiva = new Usuario();
            for (Usuario objUsuarioEnLista : listaUsuarios) {
                if (objUsuarioEnLista.getUser().equals(this.objIniciarSesion.entradaUser.getText())) {
                    if (objUsuarioEnLista.getContrasenia().equals(this.objIniciarSesion.entradaPassword.getText())) {
                        flagUsuarioEncontrado = true;
                        sesionActiva = objUsuarioEnLista;
                        Sesion.setIdJugador(objUsuarioEnLista.getId());
                        Configuracion.setInicioSesion(true);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(objIniciarSesion, "Contraseña incorrecta");
                        flagContrasenia = 1;
                        break;
                    }
//                    System.out.println("Usuario encontrado");
                }
            }
            if (flagUsuarioEncontrado) {
                JOptionPane.showMessageDialog(objIniciarSesion, "Welkome back!");
                Sesion.getInstance().setSesionUsuario(sesionActiva); 
                Menu objMenu = new Menu();
                objMenu.setVisible(true);
                this.objIniciarSesion.dispose();
                return;
            } else {
                if(flagContrasenia == 0)
                    JOptionPane.showMessageDialog(objIniciarSesion, "Usuario no encontrado");
            }

            return;
        }
        if(e.getSource()==this.objIniciarSesion.botonHere){
            Registro objRegistro = new Registro();
            objRegistro.setVisible(true);
            reproducirSonido();
            this.objIniciarSesion.dispose();
            return;
        }
        if(e.getSource()==this.objIniciarSesion.botonSkip){
            Menu objMenu = new Menu();
            objMenu.setVisible(true);
            reproducirSonido();
            this.objIniciarSesion.dispose();
            return;
        }
    }
    
        public void establecerIconos() {
            // boton login
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/botones/btn-login.png"));
        int width = this.objIniciarSesion.botonLogin.getWidth();
        int height = this.objIniciarSesion.botonLogin.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.objIniciarSesion.botonLogin.setIcon(scaledIcon);
        
        //boton skip
        ImageIcon iconoImagenSkip = new ImageIcon(getClass().getResource("/botones/btn-skip.png"));
        int widthSkip = this.objIniciarSesion.botonSkip.getWidth();
        int heightSkip = this.objIniciarSesion.botonSkip.getHeight();

        Image scaledImageSkip = iconoImagenSkip.getImage().getScaledInstance(widthSkip, heightSkip, Image.SCALE_SMOOTH);
        ImageIcon scaledIconSkip = new ImageIcon(scaledImageSkip);
        this.objIniciarSesion.botonSkip.setIcon(scaledIconSkip);
        
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
