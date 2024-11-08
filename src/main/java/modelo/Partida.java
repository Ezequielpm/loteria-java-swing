/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author mariormoreno
 */
public class Partida {
    
    private int idPartida; //consecutivo
    private int idDificultad;       
    private int idJugador;       
    private int idPuntos;
    private String resultado;
    private String fecha;

    public Partida() {
    }

    public Partida(int consecutivo, int idDificultad, int idJugador, int idPuntos, String resultado, String fecha) {
        this.idPartida = consecutivo;
        this.idDificultad = idDificultad;
        this.idJugador = idJugador;
        this.idPuntos = idPuntos;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdDificultad() {
        return idDificultad;
    }

    public void setIdDificultad(int idDificultad) {
        this.idDificultad = idDificultad;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getIdPuntos() {
        return idPuntos;
    }

    public void setIdPuntos(int idPuntos) {
        this.idPuntos = idPuntos;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}