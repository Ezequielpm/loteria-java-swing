/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author mariormoreno
 */
public class PartidaObjeto {
    
    private int idPartida; //consecutivo
    private int idDificultad;       
    private int idJugador;       
    private int puntos;
    private String resultado;
    private String fecha;
    private int idCategoria;

    public PartidaObjeto() {
    }

    public PartidaObjeto(int idPartida, int idDificultad, int idJugador, int puntos, String resultado, String fecha, int idCategoria) {
        this.idPartida = idPartida;
        this.idDificultad = idDificultad;
        this.idJugador = idJugador;
        this.puntos = puntos;
        this.resultado = resultado;
        this.fecha = fecha;
        this.idCategoria = idCategoria;
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

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
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

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    
    
    
}