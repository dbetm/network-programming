package E3_IntroMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
/**
 * Nombre David Betancourt Montellano
 * Tema del programa: Cliente en comunicación multicanal
 * Descripción: Es capaz de enviar y recibir mensajes
 * Fecha: Viernes 22 de febrero del 2019.
 */
public class Client {
    private int port;
    private String group;
    private MulticastSocket ms;
    private DatagramPacket recibe;
    private DatagramPacket envio;

    public Client(int port, String group) {
        this.port = port;
        this.group = group;
        try {
            // Crear el socket con el puerto
            this.ms = new MulticastSocket(this.port);
            // Unirse al grupo
            this.ms.joinGroup(InetAddress.getByName(this.group));
            System.out.println("Cliente ha iniciado");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibir() {
        // Declaramos un buffer de 1024 bytes
        byte buffer[] = new byte[1024];
        // Luego el DatagramPacket para poder recibir
        this.recibe = new DatagramPacket(buffer, buffer.length);
        try {
            // Recibimos el mensaje
            this.ms.receive(this.recibe);
            String msj = new String(buffer, 0, this.recibe.getData().length);
            // Mostra el mensaje recibido en pantalla
            System.out.println("->> " + msj);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            this.ms.close();
        }
    }
    
    public void enviar(String msj) {
        byte buffer[] = msj.getBytes();
        try {
            this.ms = new MulticastSocket();
            this.envio = new DatagramPacket(buffer, buffer.length,
                InetAddress.getByName(this.group), this.port);
            // Enviamos el mensaje
            this.ms.send(this.envio);
            System.out.println("Mensaje enviado");
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            // Terminamos la conexión
            this.ms.close();
        }
    }
    
    public static void main(String args[]) throws InterruptedException {
        Client s = new Client(55400, "225.3.4.5");
        s.recibir();
        s.enviar("Hola, soy un cliente");
    }
}
