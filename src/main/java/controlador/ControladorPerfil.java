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
import modelo.ListaTop;
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
        
        //partidas totales
        this.objPerfil.partidasGanadas.setText(String.valueOf(objEstadisticasPerfil.getPartidasGanadas()));
        this.objPerfil.partidasPerdidas.setText(String.valueOf(objEstadisticasPerfil.getPartidasPerdidas()));
        this.objPerfil.partidasTotales.setText(String.valueOf(objEstadisticasPerfil.getPartidasTotales()));
        
        //puntos totales
        this.objPerfil.puntosEasy.setText(String.valueOf(objEstadisticasPerfil.getPuntosEasy()));
        this.objPerfil.puntosMedium.setText(String.valueOf(objEstadisticasPerfil.getPuntosMedium()));
        this.objPerfil.puntosHard.setText(String.valueOf(objEstadisticasPerfil.getPuntosHard()));
        
        System.out.println("PUNTOS EN EASY: "+objEstadisticasPerfil.getPuntosEasy());
        System.out.println("PUNTOS EN MEDIUM: "+objEstadisticasPerfil.getPuntosMedium());
        System.out.println("PUNTOS EN HARD: "+objEstadisticasPerfil.getPuntosHard());
        System.out.println("JUEGOS GANADOS      : "+objEstadisticasPerfil.getPartidasGanadas());
        System.out.println("JUEGOS PERDIDOS    : "+objEstadisticasPerfil.getPartidasPerdidas());
        System.out.println("JUEGOS TOTALES    : "+objEstadisticasPerfil.getPartidasTotales());
        
        
        ListaTop objListaTop = objOperacionesDBPartida.obtenerRanking();
        
        System.out.println("---EASY");
        System.out.println("---1. NOMBRE: "+objListaTop.getTopEasy().get(0).getNombreJugador()+", PUNTOS: "+objListaTop.getTopHard().get(0).getPuntosJugador());
        System.out.println("---2. NOMBRE: "+objListaTop.getTopEasy().get(0).getNombreJugador());
        System.out.println("---3. NOMBRE: "+objListaTop.getTopEasy().get(0).getNombreJugador());
        System.out.println("---MEDIUM");
        System.out.println("---HARD");
        
        //total de partidas
    }
}
