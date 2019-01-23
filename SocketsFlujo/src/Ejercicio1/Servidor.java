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
    private int puerto = 21;
    private ServerSocket servidor;
    private Socket cliente;
    private DataInputStream entrada;
    private DataOutputStream salida;
    
    public Servidor() {
        try {
            // El servidor ya está a la escucha
            servidor = new ServerSocket(puerto);
            cliente = servidor.accept();
            // entrada DataInputStream
            // salida DataOutputStream
            
            // Canal para leer al cliente
            entrada = new DataInputStream(cliente.getInputStream());
            // Canal para enviar (escribir) al cliente
            salida = new DataOutputStream(cliente.getOutputStream());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Servidor s = new Servidor();
    }
}
