/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Sesion;
import modelo.Usuario;
import vista.IniciarSesion;
import vista.Menu;
import vista.Registro;

/**
 *
 * @author ezequielpena
 */
public class ControladorIniciarSesion implements ActionListener {

    IniciarSesion objIniciarSesion;
    Validador objValidador;
    Usuario objUsuario;
    OperacionesBDUsuario objOperacionesBDUsuario;

    public ControladorIniciarSesion() {
    }

    public ControladorIniciarSesion(IniciarSesion objIniciarSesion) {
        this.objIniciarSesion = objIniciarSesion;
        objValidador = new Validador();
        objOperacionesBDUsuario = new OperacionesBDUsuario();
        this.objIniciarSesion.botonLogin.addActionListener(this);
        this.objIniciarSesion.botonHere.addActionListener(this);
        establecerIconos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.objIniciarSesion.botonLogin) {
            boolean flagUsuarioEncontrado = false;
            int flagContrasenia = 0;
            if (!this.objValidador.validaCajaTextoCadena(this.objIniciarSesion.entradaUser)) {
                return;
            }
            if (!this.objValidador.validaCajaTextoCadena(this.objIniciarSesion.entradaPassword)) {
                return;
            }
//            
//            objUsuario = new Usuario();
//            objUsuario.setUser(this.objIniciarSesion.entradaUser.getText());
//            objUsuario.setContrasenia(this.objIniciarSesion.entradaPassword.getText());

//            objOperacionesBDUsuario.setObjUsuario(objUsuario);
            ArrayList<Usuario> listaUsuarios = objOperacionesBDUsuario.read();
            Usuario sesionActiva = new Usuario();
            for (Usuario objUsuarioEnLista : listaUsuarios) {
                if (objUsuarioEnLista.getUser().equals(this.objIniciarSesion.entradaUser.getText())) {
                    if (objUsuarioEnLista.getContrasenia().equals(this.objIniciarSesion.entradaPassword.getText())) {
                        flagUsuarioEncontrado = true;
                        sesionActiva = objUsuarioEnLista;
                        break;
                    } else {
                        JOptionPane.showMessageDialog(objIniciarSesion, "Contraseña incorrecta");
                        flagContrasenia = 1;
                        break;
                    }
//                    System.out.println("Usuario encontrado");
                }
            }
            if (flagUsuarioEncontrado) {
                JOptionPane.showMessageDialog(objIniciarSesion, "Bienvenido!");
                Sesion.getInstance().setSesionUsuario(sesionActiva); 
                Menu objMenu = new Menu();
                objMenu.setVisible(true);
                this.objIniciarSesion.dispose();
                return;
            } else {
                if(flagContrasenia == 0)
                    JOptionPane.showMessageDialog(objIniciarSesion, "Usuario no encontrado");
            }

            return;
        }
        if(e.getSource()==this.objIniciarSesion.botonHere){
            Registro objRegistro = new Registro();
            objRegistro.setVisible(true);
            this.objIniciarSesion.dispose();
            return;
        }
    }
    
        public void establecerIconos() {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/botones/btn-login.png"));
        int width = this.objIniciarSesion.botonLogin.getWidth();
        int height = this.objIniciarSesion.botonLogin.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.objIniciarSesion.botonLogin.setIcon(scaledIcon);
        
        
    }
}
