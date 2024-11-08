/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.ImageIcon;

/**
 *
 * @author ezequielpena
 */
public class Carta {

    ImageIcon iconoImagen;// = new ImageIcon(getClass().getResource("/categorias/animales/imagenes/" + i + ".png"));

    String rutaImagen;
    int idImagen;

    public Carta() {
    }

    public Carta(ImageIcon iconoImagen, String rutaImagen, int idImagen) {
        this.iconoImagen = iconoImagen;
        this.rutaImagen = rutaImagen;
        this.idImagen = idImagen;
    }

    public ImageIcon getIconoImagen() {
        return iconoImagen;
    }

    public void setIconoImagen(ImageIcon iconoImagen) {
        this.iconoImagen = iconoImagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    

}
