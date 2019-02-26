package E5_NIOMulticast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author david
 */
public class Cliente {
    private int puerto;
    private String ip;
    private DatagramChannel canal;
    private ByteBuffer buffer;
    private NetworkInterface interf;

    public Cliente(int puerto, String ip) {
        this.puerto = puerto;
        this.ip = ip;
        try {
            this.canal = DatagramChannel.open();
            // Ligar el canal
            this.canal.bind(null);
            this.interf = NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1"));
            // MOstrar la interfaz
            System.out.println(this.interf.getName() + " -> " + this.interf.supportsMulticast());
            // Establecer algunas opciones para el DatagramChannel
            this.canal.setOption(StandardSocketOptions.IP_MULTICAST_IF, this.interf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void enviar(String msj) {
        try {
            this.buffer = ByteBuffer.wrap(msj.getBytes());
            InetSocketAddress isa =  new InetSocketAddress(this.ip, this.puerto);
            this.canal.send(this.buffer, isa);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        // Cerrar el canal
        finally {
            try {
                this.canal.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) {
        String ip = "230.0.0.0";
        int puerto = 4444;
        Cliente c = new Cliente(puerto, ip);
        c.enviar("Hola, soy un cliente, palabra con coma: avión");
    }
}
