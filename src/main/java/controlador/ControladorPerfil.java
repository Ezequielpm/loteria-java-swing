/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import modelo.EstadisticasPerfil;
import vista.Menu;
import vista.Perfil;

/**
 *
 * @author ezequielpena
 */
public class ControladorPerfil implements ActionListener {

    Perfil objPerfil;

    public ControladorPerfil(Perfil objPerfil) {
        this.objPerfil = objPerfil;
        this.objPerfil.botonBack.addActionListener(this);
        establecerIconos();
        obtenerPerfil();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objPerfil.botonBack) {
            Menu objMenu = new Menu();
            objMenu.setVisible(true);
            this.objPerfil.dispose();
            return;
        }
    }

    public void establecerIconos() {
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objPerfil.botonBack.getWidth();
        int heightBack = this.objPerfil.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objPerfil.botonBack.setIcon(scaledIconBack);
    }
    
    public void obtenerPerfil(){
        EstadisticasPerfil objEstadisticasPerfil;
        OperacionesDBPartida objOperacionesDBPartida = new OperacionesDBPartida();
        objEstadisticasPerfil =  objOperacionesDBPartida.obtenerEstadisticasPerfil();
        
        
        //escribir el nombre del usuario
        this.objPerfil.nombreUsuario.setText(objEstadisticasPerfil.getNombreJugador());
    }
}
