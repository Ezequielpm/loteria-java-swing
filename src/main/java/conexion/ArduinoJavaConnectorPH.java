package conexion;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import java.util.List;
import java.util.Scanner;

public class ArduinoJavaConnectorPH {
    private static PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();

    public static void main(String[] args) {
        // Buscar los puertos seriales disponibles
        List<String> puertosDisponibles = arduino.getSerialPorts();
        System.out.println("Puertos disponibles:");
        for (int i = 0; i < puertosDisponibles.size(); i++) {
            System.out.println(i + ": " + puertosDisponibles.get(i));
        }

        // Seleccionar el puerto al que está conectado el Arduino
        Scanner scanner = new Scanner(System.in);
        System.out.print("Elige el puerto al que está conectado el Arduino: ");
        int indicePuerto = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea restante

        String puertoSeleccionado = puertosDisponibles.get(indicePuerto);

        try {
            // Conectar al puerto con velocidad de 9600 baudios
            arduino.arduinoRX(puertoSeleccionado, 9600, listener);
            System.out.println("Puerto abierto: " + puertoSeleccionado);
        } catch (ArduinoException | SerialPortException ex) {
            System.err.println("Error al conectar con Arduino: " + ex.getMessage());
            return;
        }

        // Mantener el programa en ejecución en un bucle hasta que el usuario presione Enter para salir
        System.out.println("Presiona Enter en cualquier momento para cerrar el programa...");
        scanner.nextLine(); // Espera hasta que el usuario presione Enter

        // Cerrar la conexión cuando el usuario presione Enter
        try {
            arduino.killArduinoConnection();
            System.out.println("Puerto cerrado. Programa terminado.");
        } catch (ArduinoException ex) {
            System.err.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }

    // Listener para eventos seriales
    private static SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent event) {
            try {
                if (arduino.isMessageAvailable()) {
                    String mensaje = arduino.printMessage();
                    System.out.println("Mensaje recibido: " + mensaje.trim());

                    // Procesar el mensaje
                    if (mensaje.contains("BOTON_PRESIONADO")) {
                        System.out.println("Se ha presionado " + mensaje.trim());
                    }
                }
            } catch (SerialPortException | ArduinoException ex) {
                System.err.println("Error al leer del puerto: " + ex.getMessage());
            }
        }
    };
}
