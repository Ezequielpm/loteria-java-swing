/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.IniciarSesion;
import vista.Registro;

/**
 *
 * @author ezequielpena
 */
public class ControladorRegistro implements ActionListener{
    Registro objRegistro;

    Validador objValidador;
    Usuario objUsuario;
    OperacionesBDUsuario objOperacionesBDUsuario;
    public ControladorRegistro() {
    }

    public ControladorRegistro(Registro objRegistro) {
        this.objValidador = new Validador();
        this.objOperacionesBDUsuario = new OperacionesBDUsuario();
        this.objRegistro = objRegistro;
        this.objRegistro.botonRegister.addActionListener(this);
        establecerIconos();
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.objRegistro.botonRegister){
            if(!this.objValidador.validaCajaTextoCadena(this.objRegistro.entradaUser)){
                return;
            }
            if(!this.objValidador.validaCajaTextoCadena(this.objRegistro.entradaPassword)){
                return;
            }
            
            objUsuario = new Usuario();
            objUsuario.setUser(this.objRegistro.entradaUser.getText());
            objUsuario.setContrasenia(this.objRegistro.entradaPassword.getText());
            
            objOperacionesBDUsuario.setObjUsuario(objUsuario);
            objOperacionesBDUsuario.create();
            JOptionPane.showMessageDialog(objRegistro, "Usuario registrado exitosamente");
            IniciarSesion objIniciarSesion = new IniciarSesion();
            objIniciarSesion.setVisible(true);
            this.objRegistro.dispose();
            return;
        }
    }
    
    public void establecerIconos() {
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/botones/btn-signup.png"));
        int width = this.objRegistro.botonRegister.getWidth();
        int height = this.objRegistro.botonRegister.getHeight();

        Image scaledImage = iconoImagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.objRegistro.botonRegister.setIcon(scaledIcon);
        
        
    }
    
}
