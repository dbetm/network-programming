package Ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Servidor {
    private int puerto = 5000;
    private ServerSocket servidor;
    private Socket cliente;
    private DataInputStream entrada;
    private DataOutputStream salida;
    
    public Servidor() {
        try {
            // El servidor ya está a la escucha
            servidor = new ServerSocket(puerto);
            while(true) {
                // Establecer conexión
                cliente = servidor.accept();
                // entrada DataInputStream
                // salida DataOutputStream

                // Canal para leer al cliente
                entrada = new DataInputStream(cliente.getInputStream());
                // Mostrar lo que manda el cliente
                System.out.println("Cliente dice >> " + entrada.readUTF());
                // Datos del cliente
                String ip = cliente.getInetAddress().getHostAddress();
                int port = cliente.getPort();
                System.out.println("Datos: " + ip + " " + port + "\n");

                // Canal para enviar (escribir) al cliente
                salida = new DataOutputStream(cliente.getOutputStream());
                // Ahora vamos a enviarle un mensaje al cliente
                salida.writeUTF("Mensaje recibido +)");

                // Cerrrar conexión del cliente
                cliente.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Servidor s = new Servidor();
    }
}
