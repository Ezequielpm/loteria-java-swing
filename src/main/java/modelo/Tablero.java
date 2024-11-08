/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author ezequielpena
 */
public class Tablero {
    int idTablero;
    ArrayList<Carta> listaCartas;

    public Tablero() {
    }

    public Tablero(int idTablero, ArrayList<Carta> listaCartas) {
        this.idTablero = idTablero;
        this.listaCartas = listaCartas;
    }

    public int getIdTablero() {
        return idTablero;
    }

    public void setIdTablero(int idTablero) {
        this.idTablero = idTablero;
    }

    public ArrayList<Carta> getListaCartas() {
        return listaCartas;
    }

    public void setListaCartas(ArrayList<Carta> listaCartas) {
        this.listaCartas = listaCartas;
    }
    
    
    
}
