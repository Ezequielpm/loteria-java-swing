package controlador;

import conexion.JavaPostgreSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CRUD;
import modelo.EstadisticasPerfil;
import modelo.PartidaObjeto;
import modelo.Sesion;

/**
 * Class to handle database operations for "Partida" (game session).
 * Clase para manejar operaciones en la base de datos para la entidad "Partida".
 */
public class OperacionesDBPartida extends CRUD {

    private PartidaObjeto objPartida;
    private JavaPostgreSQL objJavaPostgreSQL;
    private int idDificultad;
    private int idJugador;
    private int puntos;
    private String resultado;

    public OperacionesDBPartida() {
        // Use the Singleton instance
        this.objJavaPostgreSQL = JavaPostgreSQL.getInstance();
    }

    @Override
    public void create() {
        try {
            objJavaPostgreSQL.getStatement().execute("INSERT INTO partida(idcat, iddif, idjug, puntos, fecha, resultado) VALUES"
                    + "(" + objPartida.getIdCategoria() + "," + objPartida.getIdDificultad() + "," 
                    + objPartida.getIdJugador() + "," + objPartida.getPuntos() + ",'"
                    + objPartida.getFecha() + "','" + objPartida.getResultado() + "');");
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesDBPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList read() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public EstadisticasPerfil obtenerEstadisticasPerfil(){
        EstadisticasPerfil objEstadisticasPerfil = new EstadisticasPerfil();
        try {
            
            ResultSet resultado = objJavaPostgreSQL.getStatement().executeQuery(
                    "SELECT nombre FROM jugador WHERE idjug = " + Sesion.getIdJugador() + ";");
            while(resultado.next()){
                objEstadisticasPerfil.setNombreJugador(resultado.getString("nombre"));
            }
            System.out.println("NOMBRE:: "+objEstadisticasPerfil.getNombreJugador());
//            ResultSet resultado = objJavaPostgreSQL.getStatement().executeQuery(
//                "SELECT c.nombrecat AS categoria, " +
//                "COUNT(CASE WHEN p.resultado = 'Victoria' THEN 1 END) AS victorias, " +
//                "COUNT(CASE WHEN p.resultado = 'Derrota' THEN 1 END) AS derrotas, " +
//                "SUM(p.puntos) AS puntos_totales " +
//                "FROM partida p " +
//                "JOIN categoria c ON p.idcat = c.idcat " +
//                "WHERE p.idjug = " + idJugador + " " +
//                "GROUP BY c.nombrecat;"
//            );

//            while (resultado.next()) {
//                String categoria = resultado.getString("categoria");
//                int victorias = resultado.getInt("victorias");
//                int derrotas = resultado.getInt("derrotas");
//                int puntosTotales = resultado.getInt("puntos_totales");
//
//                EstadisticasPerfil estadistica = new EstadisticasPartida(categoria, victorias, derrotas, puntosTotales);
//                listaEstadisticas.add(estadistica);
//            }

        } catch (SQLException ex) {
            Logger.getLogger(OperacionesDBPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  objEstadisticasPerfil;
    }

    public PartidaObjeto getObjPartida() {
        return objPartida;
    }

    public void setObjPartida(PartidaObjeto objPartida) {
        this.objPartida = objPartida;
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
}
