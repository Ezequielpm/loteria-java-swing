package conexion;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ArduinoConnector {

    // Variable estática para la instancia única de la clase (patrón Singleton)
    private static ArduinoConnector instance;
    private PanamaHitek_Arduino arduino;
    private boolean isConnected = false;
    private static String botonPresionado;
    // Constructor privado para evitar instanciación desde fuera de la clase
    private ArduinoConnector() {
        arduino = new PanamaHitek_Arduino();
    }

    // Método estático para obtener la instancia única
    public static synchronized ArduinoConnector getInstance() {
        if (instance == null) {
            instance = new ArduinoConnector();
        }
        return instance;
    }

    // Método para conectar al Arduino
    public boolean connect(String port, int baudRate) {
        if (isConnected) {
            System.out.println("Ya está conectado al puerto: " + port);
            return true;
        }
        
        try {
            arduino.arduinoRX(port, baudRate, listener);
            System.out.println("Conectado al Arduino en el puerto: " + port);
            isConnected = true;
            return true;
        } catch (ArduinoException | SerialPortException ex) {
            System.err.println("Error al conectar con Arduino: " + ex.getMessage());
            return false;
        }
    }

    // Método para cerrar la conexión
    public void disconnect() {
        if (isConnected) {
            try {
                arduino.killArduinoConnection();
                System.out.println("Conexión con Arduino cerrada.");
                isConnected = false;
            } catch (ArduinoException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        } else {
            System.out.println("No hay conexión abierta para cerrar.");
        }
    }

    // Listener para eventos seriales
    private SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent event) {
            try {
                if (arduino.isMessageAvailable()) {
                    String mensaje = arduino.printMessage();
                    botonPresionado = mensaje;
                    System.out.println("Mensaje recibido: " + mensaje.trim());
                }
            } catch (SerialPortException | ArduinoException ex) {
                System.err.println("Error al leer del puerto: " + ex.getMessage());
            }
        }
    };

    // Getter para verificar si está conectado
    public boolean isConnected() {
        return isConnected;
    }

    public static String getBotonPresionado() {
        return botonPresionado;
    }

    public static void setBotonPresionado(String botonPresionado) {
        ArduinoConnector.botonPresionado = botonPresionado;
    }
    
    
    
}
