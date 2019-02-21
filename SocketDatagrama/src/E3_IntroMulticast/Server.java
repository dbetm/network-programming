package E3_IntroMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author david
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
            this.servidor = new MulticastSocket();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void enviar(String msj) {
        try {
            byte buffer[] = msj.getBytes();
            this.envio = new DatagramPacket(buffer, buffer.length,
                    InetAddress.getByName(this.host), this.port);
            this.servidor.send(this.envio);
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    } 
    
    public void recibir() {
        try {
            byte buffer[] = new byte[1024];
            this.recibir = new DatagramPacket(buffer, buffer.length);
            // Recibimos
            this.servidor.receive(this.recibir);
            String msj = new String(buffer, 0, this.recibir.getData().length);
            System.out.println(">> " + msj);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Server c = new Server(55400, "225.3.4.5");
        c.enviar("Hola amigos");
        c.recibir();
    }
}
