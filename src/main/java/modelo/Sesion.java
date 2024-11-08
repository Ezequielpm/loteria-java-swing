/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ezequielpena
 */
public class Sesion {
    private static Sesion instancia;
    private Usuario sesionUsuario;
    
    private static int puntosGanados;

    private Sesion() {}

    public static Sesion getInstance() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Usuario getSesionUsuario() {
        return sesionUsuario;
    }

    public void setSesionUsuario(Usuario sesionUsuario) {
        this.sesionUsuario = sesionUsuario;
    }

    public static int getPuntosGanados() {
        return puntosGanados;
    }

    public static void setPuntosGanados(int puntosGanados) {
        Sesion.puntosGanados = puntosGanados;
    }
    
    
    
}
