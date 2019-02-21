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
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void unirse() {
        byte buffer[] = new byte[1024];
        this.recibe = new DatagramPacket(buffer, buffer.length);
        
        try {
            this.ms.receive(this.recibe);
            String msj = new String(buffer, 0, this.recibe.getData().length);
            System.out.println("->> " + msj);
            // Se abandona el grupo
            // this.ms.leaveGroup(InetAddress.getByName(this.group));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void enviar(String msj) {
        byte buffer[] = msj.getBytes();
        try {
            this.envio = new DatagramPacket(buffer, buffer.length,
                InetAddress.getByName(this.group), this.port);
            this.ms.send(this.envio);
            // Se abandona el grupo
            this.ms.leaveGroup(InetAddress.getByName(this.group));
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            this.ms.close();
        }
    }
    
    public static void main(String args[]) {
        Client s = new Client(55400, "225.3.4.5");
        s.unirse();
        s.enviar("Soy un cliente");
    }
}
