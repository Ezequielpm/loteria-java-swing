/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
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
    
}
