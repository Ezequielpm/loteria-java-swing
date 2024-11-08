/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import vista.Dificultad;
import vista.DificultadJobs;
import vista.DificultadObjects;
import vista.EscogerCategoria;
import vista.EscogerControl;
import vista.Menu;

/**
 *
 * @author ezequielpena
 */
public class ControladorEscogerCategoria implements ActionListener{
    EscogerCategoria objEscogerCategoria;

    public ControladorEscogerCategoria() {
    }

    public ControladorEscogerCategoria(EscogerCategoria objEscogerCategoria) {
        this.objEscogerCategoria = objEscogerCategoria;
        this.objEscogerCategoria.botonBack.addActionListener(this);
        this.objEscogerCategoria.botonAnimals.addActionListener(this);
        this.objEscogerCategoria.botonJobs.addActionListener(this);
        this.objEscogerCategoria.botonThings.addActionListener(this);
        establecerIconos();
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objEscogerCategoria.botonBack){
            EscogerControl objEscogerControl = new EscogerControl();
            objEscogerControl.setVisible(true);
//            Menu objMenu = new Menu();
//            objMenu.setVisible(true);
            this.objEscogerCategoria.dispose();
            return;
        }
        if(e.getSource()==this.objEscogerCategoria.botonAnimals){
            Dificultad objDificultadAnimales = new Dificultad();
            objDificultadAnimales.setVisible(true);
            this.objEscogerCategoria.dispose();
            return;
        }
        if(e.getSource()==this.objEscogerCategoria.botonJobs){
            DificultadJobs objDificultadJobs = new DificultadJobs();
            objDificultadJobs.setVisible(true);
            this.objEscogerCategoria.dispose();
            return;
        }
        if(e.getSource()==this.objEscogerCategoria.botonThings){
            DificultadObjects objDificultadObjects = new DificultadObjects();
            objDificultadObjects.setVisible(true);
            this.objEscogerCategoria.dispose();
            return;
        }
    }
    
    public void establecerIconos(){
        //boton back
        ImageIcon iconoImagenBack = new ImageIcon(getClass().getResource("/iconos/icon__flecha-regreso.png"));
        int widthBack = this.objEscogerCategoria.botonBack.getWidth();
        int heightBack = this.objEscogerCategoria.botonBack.getHeight();

        Image scaledImageBack = iconoImagenBack.getImage().getScaledInstance(widthBack, heightBack, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        this.objEscogerCategoria.botonBack.setIcon(scaledIconBack);
        
        //boton animales
        
        ImageIcon iconoImagenAnimals = new ImageIcon(getClass().getResource("/iconos/categoria-animals__icono-baraja.png"));
        int widthAnimals = this.objEscogerCategoria.botonAnimals.getWidth();
        int heightAnimals = this.objEscogerCategoria.botonAnimals.getHeight();

        Image scaledImageAnimals = iconoImagenAnimals.getImage().getScaledInstance(widthAnimals, heightAnimals, Image.SCALE_SMOOTH);
        ImageIcon scaledIconAnimals = new ImageIcon(scaledImageAnimals);
        this.objEscogerCategoria.botonAnimals.setIcon(scaledIconAnimals);
        
        //boton jobs
        
        ImageIcon iconoImagenJobs = new ImageIcon(getClass().getResource("/iconos/categoria-jobs__icono-baraja.png"));
        int widthJobs = this.objEscogerCategoria.botonJobs.getWidth();
        int heightJobs = this.objEscogerCategoria.botonJobs.getHeight();

        Image scaledImageJobs = iconoImagenJobs.getImage().getScaledInstance(widthJobs, heightJobs, Image.SCALE_SMOOTH);
        ImageIcon scaledIconJobs = new ImageIcon(scaledImageJobs);
        this.objEscogerCategoria.botonJobs.setIcon(scaledIconJobs);
        
        
        //boto objects
        
        ImageIcon iconoImagenObjects = new ImageIcon(getClass().getResource("/iconos/categoria-objects__icono-baraja.png"));
        int widthObjects = this.objEscogerCategoria.botonThings.getWidth();
        int heightObjects = this.objEscogerCategoria.botonThings.getHeight();

        Image scaledImageObjects = iconoImagenObjects.getImage().getScaledInstance(widthObjects, heightObjects, Image.SCALE_SMOOTH);
        ImageIcon scaledIconObjects = new ImageIcon(scaledImageObjects);
        this.objEscogerCategoria.botonThings.setIcon(scaledIconObjects);
    }
    
}
