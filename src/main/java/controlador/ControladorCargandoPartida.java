package controlador;

import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.CargandoPartida;

public class ControladorCargandoPartida {
    private final CargandoPartida objCargandoPartida;
    private int progreso = 0;

    public ControladorCargandoPartida(CargandoPartida objCargandoPartida) {
        this.objCargandoPartida = objCargandoPartida;
        this.objCargandoPartida.barraProgresoPartida.setStringPainted(true);
        this.objCargandoPartida.barraProgresoPartida.setBackground(Color.BLUE);
        // Configurar el temporizador para simular el progreso
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progreso += 2; // Incrementar el progreso (ajusta el valor si es necesario)
                objCargandoPartida.barraProgresoPartida.setValue(progreso);
                if (progreso >= 100) {
                    ((Timer)e.getSource()).stop(); // Detener el temporizador
                    objCargandoPartida.dispose(); // Cerrar la ventana de progreso
                    iniciarVistaPartida(); // Llamar a la vista de la partida una vez que se completa la carga
                }
            }
        });
        timer.start(); // Iniciar el temporizador
    }

    // Método para abrir la vista de la partida (este método debe ser implementado)
    private void iniciarVistaPartida() {
        // Aquí iría el código para abrir la vista de la partida, por ejemplo:
        // new VistaPartida().setVisible(true);
        System.out.println("Vista de partida iniciada."); // Para probar
    }
}
