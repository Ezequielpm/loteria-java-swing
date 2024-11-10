package controlador;

import conexion.JavaPostgreSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CRUD;
import modelo.Usuario;

/**
 * Operations for managing users in the database.
 * Operaciones para gestionar usuarios en la base de datos.
 */
public class OperacionesBDUsuario extends CRUD {

    private Usuario objUsuario;
    private JavaPostgreSQL objJavaPostgreSQL;

    public OperacionesBDUsuario() {
        // Use the Singleton instance
        this.objJavaPostgreSQL = JavaPostgreSQL.getInstance();
    }

    @Override
    public void create() {
        try {
            // Use the statement from the Singleton instance
            objJavaPostgreSQL.getStatement().execute("INSERT INTO jugador(nombre, contrasenia) VALUES('"
                    + objUsuario.getUser() + "','" + objUsuario.getContrasenia() + "');");
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBDUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Usuario> read() {
        ArrayList<Usuario> objListaUsuarios = new ArrayList<>();
        Usuario objUsuariol;
        try {
            ResultSet resultado = objJavaPostgreSQL.getStatement().executeQuery("SELECT * FROM jugador;");
            while (resultado.next()) {
                objUsuariol = new Usuario();
                objUsuariol.setId(resultado.getInt("idjug"));
                objUsuariol.setUser(resultado.getString("nombre"));
                objUsuariol.setContrasenia(resultado.getString("contrasenia"));
                objListaUsuarios.add(objUsuariol);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBDUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objListaUsuarios;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Usuario getObjUsuario() {
        return objUsuario;
    }

    public void setObjUsuario(Usuario objUsuario) {
        this.objUsuario = objUsuario;
    }
}
