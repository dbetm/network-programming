package p1SimEcho;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Servidor para simular el comando 'echo'
 * Descripción: El servidor se lanza a la escucha de clientes a través del puerto
 *     5500, cuando se conecta un cliente, el servidor lo recibe y escribe una
 *     respuesta al cliente, es decir, el mismo mensaje entrante
 * Fecha: Jueves 31 de enero del 2019.
 */
public class Servidor {
    // Atributos
    private int puerto;
    private ServerSocket servidor;
    private Socket cliente;
    private DataInputStream entrada;
    private DataOutputStream salida;
    
    public Servidor() {
        // asignamos un puerto
        this.puerto = 5500;
        // se pone a escuchar el servidor
        escuchar();
        
    }
    
    private void escuchar() {
        try {
            this.servidor = new ServerSocket(puerto);
            // El servidor se debe mantener encendido
            while(true) {
                // Establecemos la conexión
                this.cliente = this.servidor.accept();
                
                // Leemos el mensaje del cliente
                this.entrada = new DataInputStream(this.cliente.getInputStream());
                String mensaje = this.entrada.readUTF();
                
                // Al cliente se le responde con el mismo mensaje que ha enviado
                this.salida = new DataOutputStream(this.cliente.getOutputStream());
                this.salida.writeUTF(mensaje);
                
                // Terminamos la conexión con el cliente
                this.cliente.close();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Servidor server = new Servidor();
    }
}
