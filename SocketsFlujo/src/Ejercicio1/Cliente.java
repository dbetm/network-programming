package Ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * @author david
 */
public class Cliente {
    private String ip = "127.0.0.1";
    private int puerto = 5000;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Socket cliente;
    
    public Cliente() {
        try {
            cliente = new Socket(ip, puerto);
            salida = new DataOutputStream(cliente.getOutputStream());
            // escribir mensaje al servidor
            salida.writeUTF("How are you? | Conexión exitosa.");
            
            // recibir mensaje del servidor
            entrada = new DataInputStream(cliente.getInputStream());
            System.out.println("Servidor dice >> " + entrada.readUTF());
            
            cliente.close();
        }
        catch(UnknownHostException enet) {
            enet.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Cliente(String mensaje) {
        try {
            cliente = new Socket(ip, puerto);
            salida = new DataOutputStream(cliente.getOutputStream());
            // escribir mensaje al servidor
            salida.writeUTF(mensaje);
            
            // recibir mensaje del servidor
            entrada = new DataInputStream(cliente.getInputStream());
            System.out.println("Servidor dice >> " + entrada.readUTF());
            
            cliente.close();
        }
        catch(UnknownHostException enet) {
            enet.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void main(String args[]) {
        // Cliente c = new Cliente();
        // 5 clientes se van a conectar con mensajes diferentes
        String mensajes[] = new String[]{"hola", "hola2", "hola3", "hola4", "hola5"};
        Cliente c;
        for (int i = 0; i < 5; i++) {
            c = new Cliente(mensajes[i]);
        }
    }
}
