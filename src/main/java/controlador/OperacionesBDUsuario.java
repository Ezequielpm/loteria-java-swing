/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author ezequielpena
 */
public class OperacionesBDUsuario extends CRUD {

    Usuario objUsuario;
    JavaPostgreSQL objJavaPostgreSQL;

    public OperacionesBDUsuario() {
        this.objJavaPostgreSQL = new JavaPostgreSQL();
        this.objJavaPostgreSQL.connectDatabase();
    }

    @Override
    public void create() {
        try {
            objJavaPostgreSQL.stmt.execute("INSERT INTO jugador(nombre,contrasenia) VALUES(" + "'"
                    + objUsuario.getUser() + "','" + objUsuario.getContrasenia() + "');");
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBDUsuario.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public ArrayList read() {
        ArrayList<Usuario> objListaUsuarios = new ArrayList<>();
        Usuario objUsuariol;
        try {
            ResultSet resultado = objJavaPostgreSQL.stmt.executeQuery("SELECT * FROM jugador;");
            while(resultado.next()){
                objUsuariol = new Usuario();
                objUsuariol.setId(resultado.getInt("idjug"));
                objUsuariol.setUser(resultado.getString("nombre"));
                objUsuariol.setContrasenia(resultado.getString("contrasenia"));
                
                objListaUsuarios.add(objUsuariol);
                
                //objListaEstudiante.add(objEstudiante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBDUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objListaUsuarios;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Usuario getObjUsuario() {
        return objUsuario;
    }

    public void setObjUsuario(Usuario objUsuario) {
        this.objUsuario = objUsuario;
    }

}
