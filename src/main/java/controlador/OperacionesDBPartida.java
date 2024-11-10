package controlador;

import conexion.JavaPostgreSQL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CRUD;
import modelo.PartidaObjeto;

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
