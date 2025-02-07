package controlador;

import conexion.JavaPostgreSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CRUD;
import modelo.EstadisticasPerfil;
import modelo.ListaTop;
import modelo.PartidaObjeto;
import modelo.Sesion;
import modelo.Top;

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
        objEstadisticasPerfil.setIdJugador(Sesion.getIdJugador());
        
        
        ResultSet resultado = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT nombre FROM jugador WHERE idjug = " + Sesion.getIdJugador() + ";");
        if (resultado.next()) {
            objEstadisticasPerfil.setNombreJugador(resultado.getString("nombre"));
        }
        System.out.println("NOMBRE:: " + objEstadisticasPerfil.getNombreJugador());
        
        
        ResultSet rsPartidasTotales = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT COUNT(*) AS total FROM partida WHERE idjug = " + Sesion.getIdJugador() + ";");
        if (rsPartidasTotales.next()) {
            objEstadisticasPerfil.setPartidasTotales(rsPartidasTotales.getInt("total"));
        }
        
        ResultSet rsPartidasGanadas = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT COUNT(*) AS ganadas FROM partida WHERE idjug = " + Sesion.getIdJugador() + " AND resultado = 'GANA';");
        if (rsPartidasGanadas.next()) {
            objEstadisticasPerfil.setPartidasGanadas(rsPartidasGanadas.getInt("ganadas"));
        }
        
        ResultSet rsPartidasPerdidas = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT COUNT(*) AS perdidas FROM partida WHERE idjug = " + Sesion.getIdJugador() + " AND resultado = 'PIERDE';");
        if (rsPartidasPerdidas.next()) {
            objEstadisticasPerfil.setPartidasPerdidas(rsPartidasPerdidas.getInt("perdidas"));
        }
        
        ResultSet rsPuntosEasy = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT COALESCE(SUM(puntos), 0) AS puntos_easy FROM partida p JOIN dificultad d ON p.iddif = d.iddif " +
                "WHERE p.idjug = " + Sesion.getIdJugador() + " AND d.descripcion = 'Easy';");
        if (rsPuntosEasy.next()) {
            objEstadisticasPerfil.setPuntosEasy(rsPuntosEasy.getInt("puntos_easy"));
        }
        
        ResultSet rsPuntosMedium = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT COALESCE(SUM(puntos), 0) AS puntos_medium FROM partida p JOIN dificultad d ON p.iddif = d.iddif " +
                "WHERE p.idjug = " + Sesion.getIdJugador() + " AND d.descripcion = 'Medium';");
        if (rsPuntosMedium.next()) {
            objEstadisticasPerfil.setPuntosMedium(rsPuntosMedium.getInt("puntos_medium"));
        }
        
        ResultSet rsPuntosHard = objJavaPostgreSQL.getStatement().executeQuery(
                "SELECT COALESCE(SUM(puntos), 0) AS puntos_hard FROM partida p JOIN dificultad d ON p.iddif = d.iddif " +
                "WHERE p.idjug = " + Sesion.getIdJugador() + " AND d.descripcion = 'Hard';");
        if (rsPuntosHard.next()) {
            objEstadisticasPerfil.setPuntosHard(rsPuntosHard.getInt("puntos_hard"));
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(OperacionesDBPartida.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return objEstadisticasPerfil;
}
    
//    public EstadisticasPerfil obtenerEstadisticasPerfil(){
//        EstadisticasPerfil objEstadisticasPerfil = new EstadisticasPerfil();
//        try {
//            
//            ResultSet resultado = objJavaPostgreSQL.getStatement().executeQuery(
//                    "SELECT nombre FROM jugador WHERE idjug = " + Sesion.getIdJugador() + ";");
//            while(resultado.next()){
//                objEstadisticasPerfil.setNombreJugador(resultado.getString("nombre"));
//            }
//            System.out.println("NOMBRE:: "+objEstadisticasPerfil.getNombreJugador());
////            ResultSet resultado = objJavaPostgreSQL.getStatement().executeQuery(
////                "SELECT c.nombrecat AS categoria, " +
////                "COUNT(CASE WHEN p.resultado = 'Victoria' THEN 1 END) AS victorias, " +
////                "COUNT(CASE WHEN p.resultado = 'Derrota' THEN 1 END) AS derrotas, " +
////                "SUM(p.puntos) AS puntos_totales " +
////                "FROM partida p " +
////                "JOIN categoria c ON p.idcat = c.idcat " +
////                "WHERE p.idjug = " + idJugador + " " +
////                "GROUP BY c.nombrecat;"
////            );
//
////            while (resultado.next()) {
////                String categoria = resultado.getString("categoria");
////                int victorias = resultado.getInt("victorias");
////                int derrotas = resultado.getInt("derrotas");
////                int puntosTotales = resultado.getInt("puntos_totales");
////
////                EstadisticasPerfil estadistica = new EstadisticasPartida(categoria, victorias, derrotas, puntosTotales);
////                listaEstadisticas.add(estadistica);
////            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(OperacionesDBPartida.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return  objEstadisticasPerfil;
//    }

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
    
    
    public ListaTop obtenerRanking() {
        ListaTop objListaTop = new ListaTop();
    try {
      
        ArrayList<Top> topEasy = new ArrayList<>();
        ArrayList<Top> topMedium = new ArrayList<>();
        ArrayList<Top> topHard = new ArrayList<>();

      
        ResultSet rsEasy = objJavaPostgreSQL.getStatement().executeQuery(
            "SELECT j.nombre, SUM(p.puntos) AS total_puntos " +
            "FROM partida p JOIN jugador j ON p.idjug = j.idjug " +
            "JOIN dificultad d ON p.iddif = d.iddif " +
            "WHERE d.descripcion = 'Easy' " +
            "GROUP BY j.nombre " +
            "ORDER BY total_puntos DESC " +
            "LIMIT 3;"
        );
        while (rsEasy.next()) {
            topEasy.add(new Top(rsEasy.getString("nombre"), rsEasy.getInt("total_puntos")));
        }

      
        ResultSet rsMedium = objJavaPostgreSQL.getStatement().executeQuery(
            "SELECT j.nombre, SUM(p.puntos) AS total_puntos " +
            "FROM partida p JOIN jugador j ON p.idjug = j.idjug " +
            "JOIN dificultad d ON p.iddif = d.iddif " +
            "WHERE d.descripcion = 'Medium' " +
            "GROUP BY j.nombre " +
            "ORDER BY total_puntos DESC " +
            "LIMIT 3;"
        );
        while (rsMedium.next()) {
            topMedium.add(new Top(rsMedium.getString("nombre"), rsMedium.getInt("total_puntos")));
        }

   
        ResultSet rsHard = objJavaPostgreSQL.getStatement().executeQuery(
            "SELECT j.nombre, SUM(p.puntos) AS total_puntos " +
            "FROM partida p JOIN jugador j ON p.idjug = j.idjug " +
            "JOIN dificultad d ON p.iddif = d.iddif " +
            "WHERE d.descripcion = 'Hard' " +
            "GROUP BY j.nombre " +
            "ORDER BY total_puntos DESC " +
            "LIMIT 3;"
        );
        while (rsHard.next()) {
            topHard.add(new Top(rsHard.getString("nombre"), rsHard.getInt("total_puntos")));
        }

        objListaTop.setTopEasy(topEasy);
        objListaTop.setTopMedium(topMedium);
        objListaTop.setTopHard(topHard);

    } catch (SQLException ex) {
        Logger.getLogger(OperacionesDBPartida.class.getName()).log(Level.SEVERE, null, ex);
    }
    return objListaTop;
}
    
}
