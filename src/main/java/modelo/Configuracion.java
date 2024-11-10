/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ezequielpena
 */
public class Configuracion {
    // Variables estáticas para almacenar la configuración
    private static boolean usarArduino;
    private static String dificultad;
    private static boolean presionarSinLimite;
    private static boolean inicioSesion;

    // Método para establecer la opción elegida por el usuario
    public static void setOpcion(boolean usarArduino) {
        Configuracion.usarArduino = usarArduino;
    }

    // Métodos para obtener la configuración
    public static boolean isUsarArduino() {
        return usarArduino;
    }

    public static String getDificultad() {
        return dificultad;
    }
    
    

    public static void setDificultad(String dificultad) {
        Configuracion.dificultad = dificultad;
    }

    public static boolean isPresionarSinLimite() {
        return presionarSinLimite;
    }

    public static void setPresionarSinLimite(boolean presionarSinLimite) {
        Configuracion.presionarSinLimite = presionarSinLimite;
    }

    public static boolean isInicioSesion() {
        return inicioSesion;
    }

    public static void setInicioSesion(boolean inicioSesion) {
        Configuracion.inicioSesion = inicioSesion;
    }
    
    
    
    
}
