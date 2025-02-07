/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import modelo.ListaTop;
import vista.Menu;
import vista.Ranking;

/**
 *
 * @author ezequielpena
 */
public class ControladorRanking implements ActionListener{
    Ranking objRanking;

    public ControladorRanking(Ranking objRanking) {
        this.objRanking = objRanking;
        this.objRanking.botonBack.addActionListener(this);
        establecerIconos();
        obtenerRanking();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objRanking.botonBack){
            Menu objMenu = new Menu();
            objMenu.setVisible(true);
            this.objRanking.dispose();
            return;
        }
    }
    
    public void establecerIconos() {
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objRanking.botonBack.getWidth();
        int heightBack = this.objRanking.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objRanking.botonBack.setIcon(scaledIconBack);
    }
    
    public void obtenerRanking(){
        OperacionesDBPartida objOperacionesDBPartida = new OperacionesDBPartida();
        ListaTop objListaTop = objOperacionesDBPartida.obtenerRanking();
        
        System.out.println("---EASY");
        System.out.println("---1. NOMBRE: "+objListaTop.getTopEasy().get(0).getNombreJugador()+", PUNTOS: "+objListaTop.getTopEasy().get(0).getPuntosJugador());
        System.out.println("---2. NOMBRE: "+objListaTop.getTopEasy().get(0).getNombreJugador());
        System.out.println("---3. NOMBRE: "+objListaTop.getTopEasy().get(0).getNombreJugador());
        System.out.println("---MEDIUM");
        System.out.println("---HARD");
        
        // facil
        this.objRanking.nombreJugador1.setText(objListaTop.getTopEasy().get(0).getNombreJugador());
        this.objRanking.nombreJugador2.setText(objListaTop.getTopEasy().get(1).getNombreJugador());
        this.objRanking.nombreJugador3.setText(objListaTop.getTopEasy().get(2).getNombreJugador());
        // puntos easy
        this.objRanking.puntosJugador1.setText(String.valueOf(objListaTop.getTopEasy().get(0).getPuntosJugador()));
        this.objRanking.puntosJugador2.setText(String.valueOf(objListaTop.getTopEasy().get(1).getPuntosJugador()));
        this.objRanking.puntosJugador3.setText(String.valueOf(objListaTop.getTopEasy().get(2).getPuntosJugador()));

        
        //medium
        this.objRanking.nombreJugador4.setText(objListaTop.getTopMedium().get(0).getNombreJugador());
        this.objRanking.nombreJugador5.setText(objListaTop.getTopMedium().get(1).getNombreJugador());
        this.objRanking.nombreJugador6.setText(objListaTop.getTopMedium().get(2).getNombreJugador());
        //puntos medium
        this.objRanking.puntosJugador4.setText(String.valueOf(objListaTop.getTopMedium().get(0).getPuntosJugador()));
        this.objRanking.puntosJugador5.setText(String.valueOf(objListaTop.getTopMedium().get(1).getPuntosJugador()));
        this.objRanking.puntosJugador6.setText(String.valueOf(objListaTop.getTopMedium().get(2).getPuntosJugador()));
        
        //hard
        this.objRanking.nombreJugador7.setText(objListaTop.getTopHard().get(0).getNombreJugador());
        this.objRanking.nombreJugador8.setText(objListaTop.getTopHard().get(1).getNombreJugador());
        this.objRanking.nombreJugador9.setText(objListaTop.getTopHard().get(2).getNombreJugador());
        //puntos hard
        this.objRanking.puntosJugador7.setText(String.valueOf(objListaTop.getTopHard().get(0).getPuntosJugador()));
        this.objRanking.puntosJugador8.setText(String.valueOf(objListaTop.getTopHard().get(1).getPuntosJugador()));
        this.objRanking.puntosJugador9.setText(String.valueOf(objListaTop.getTopHard().get(2).getPuntosJugador()));
    }
    
}
