package E3_IntroMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Nombre David Betancourt Montellano
 * Tema del programa: Servidor en comunicación multicanal
 * Descripción: Es capaz de enviar y recibir mensajes
 * Fecha: Viernes 22 de febrero del 2019.
 */
public class Server {
    private DatagramPacket envio;
    private DatagramPacket recibir;
    private MulticastSocket servidor;
    private int port;
    private String host;

    public Server(int port, String host) {
        this.port = port;
        this.host = host;
        try {
            // Se instancia el socket multicanal
            this.servidor = new MulticastSocket();
            System.out.println("Servidor corriendo...");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void enviar(String msj) {
        try {
            // Generamos el buffer con el mensaje
            byte buffer[] = msj.getBytes();
            this.envio = new DatagramPacket(buffer, buffer.length,
                InetAddress.getByName(this.host), this.port);
            // Enviamos el paquete de datagrama
            this.servidor.send(this.envio);
            System.out.println("Mensaje enviado");
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            this.servidor.close();
        }
    } 
    
    public void recibir() {
        // Declaramos un buffer de 1024 bytes
        byte buffer[] = new byte[1024];
        this.recibir = new DatagramPacket(buffer, buffer.length);
        try {
            this.servidor = new MulticastSocket(this.port);
            // Unirse al grupo
            this.servidor.joinGroup(InetAddress.getByName(this.host));
            this.servidor.receive(this.recibir);
            String msj = new String(buffer, 0, this.recibir.getData().length);
            // Mostramos el mensaje en pantalla
            System.out.println(">> " + msj);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            this.servidor.close();
        }
    }
    
    public static void main(String args[]) {
        Server c = new Server(55400, "225.3.4.5");
        c.enviar("Hola, soy el servidor");
        c.recibir();
    }
}
