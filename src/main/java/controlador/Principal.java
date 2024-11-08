package controlador;

import javax.swing.*;
import vista.IniciarSesion;

public class Principal {

    public static void main(String[] args) {
        // Establece el Look and Feel del sistema
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crea y muestra el JFrame en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            IniciarSesion objIniciarSesion = new IniciarSesion();
            objIniciarSesion.setVisible(true);
        });
    }
}
