package E1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author david
 */
public class Cliente {
    private DatagramPacket envio;
    private DatagramPacket recepcion;
    private DatagramSocket cliente;
    private int port;
    
    public Cliente(int port) {
        this.port = port;
        try {
            this.cliente = new DatagramSocket();
        }
        catch (SocketException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibir() {
        byte buffer[] = new byte[1024];
        this.recepcion = new DatagramPacket(buffer, buffer.length);
        try {
            this.cliente.receive(recepcion);
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
        
        try {
            this.envio = new DatagramPacket(buffer, buffer.length, 
                InetAddress.getLocalHost(), this.port);
            this.cliente.send(this.envio);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Cliente c = new Cliente(5000);
        c.enviar("Hola, servidor");
        c.recibir();
    }
    
}
