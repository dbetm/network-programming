package E1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author david
 */
public class Servidor {
    private DatagramPacket envio;
    private DatagramPacket recepcion;
    private DatagramSocket server;
    private int port;

    public Servidor(int port) {
        this.port = port;
        try {
            this.server = new DatagramSocket(this.port);
        }
        catch (SocketException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibir() {
        byte buffer[] = new byte[1024];
        this.recepcion = new DatagramPacket(buffer, buffer.length);
        try {
            this.server.receive(recepcion);
            String msg = new String(buffer, 0, this.recepcion.getData().length);
            // Otra forma
            // String msg2 = new String(this.recepcion.getData());
            System.out.println(">> " + msg);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void enviar(String msg) {
        byte buffer[] = msg.getBytes();
        
        this.envio = new DatagramPacket(buffer, buffer.length, 
            this.recepcion.getAddress(), this.recepcion.getPort());
        
        try {
            this.server.send(this.envio);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Servidor s = new Servidor(5000);
        s.recibir();
        s.enviar("Hola, cliente");
    }
}
