package conexion;
public class Main {
    public static void main(String[] args) {
        ArduinoConnector arduino = ArduinoConnector.getInstance();

        // Conectar al Arduino en el puerto correcto y a 9600 baudios
        if (arduino.connect("/dev/cu.usbserial-1130", 9600)) {
            System.out.println("Conexión establecida.");

            // Mantener el programa en ejecución
            System.out.println("Presiona Enter para cerrar la conexión...");
            new java.util.Scanner(System.in).nextLine();

            // Desconectar al terminar
            arduino.disconnect();
        } else {
            System.out.println("No se pudo establecer conexión.");
        }
    }
}
