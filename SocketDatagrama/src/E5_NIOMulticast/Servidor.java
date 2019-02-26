package E5_NIOMulticast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

/**
 *
 * @author david
 */
public class Servidor {
    private int puerto;
    private String ip;
    private DatagramChannel canal;
    private ByteBuffer buffer;
    private NetworkInterface interf;
    /* Establecer un token para saber el grupo que está creado: Es un identificador
    de grupo */
    private MembershipKey key;

    public Servidor(int puerto, String ip) {
        this.puerto = puerto;
        this.ip = ip;
        try {
            // Abrir el canal especificando la versión IPv4
            this.canal = DatagramChannel.open(StandardProtocolFamily.INET);
            this.interf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            // seteamos que la IP es reutilizable de multicast
            this.canal.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            this.canal.bind(new InetSocketAddress(this.puerto));
            /* Definimos que se trata de una IP multicast, y se le pasa la interfaz de
            red sobre la que se trabaja */
            this.canal.setOption(StandardSocketOptions.IP_MULTICAST_IF, this.interf);
            // Unirse al grupo (IP) con cierta interfaz, y obtenemos la clave del grupo
            this.key = this.canal.join(InetAddress.getByName(this.ip), this.interf);
            // Imprimimos la clave
            System.out.println("Grupo: " + this.key);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibirMensaje() {
        try {
            System.out.println("Esperando mensajes...");
            this.buffer = ByteBuffer.allocate(1024);
            this.canal.receive(this.buffer);
            // Preparar para lectura
            this.buffer.flip();
            // Mostramos el mensaje
            String msj = new String(this.buffer.array());
            System.out.println(">> " + msj);
            // Liberar la clave
            this.key.drop();
            // Cerrar el canal
            this.canal.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        String ip = "230.0.0.0";
        int puerto = 4444;
        Servidor s = new Servidor(puerto, ip);
        s.recibirMensaje();
    }
}
