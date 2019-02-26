package E4_NIODatagramas;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author david
 */
public class Servidor {
    private DatagramChannel canal;
    // Para el envío de datos
    private ByteBuffer buffer;
    private int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
        try {
            // Abrir el canal de comunicación
            this.canal = DatagramChannel.open();
            // Activar modo no bloqueante
            this.canal.configureBlocking(false);
            // Enlazar el canal a una dirección y un puerto
            InetSocketAddress isa = new InetSocketAddress("127.0.0.1", this.puerto);
            // Hacer la conexión
            this.canal.bind(isa);
            // Imprimir dirección IP y puerto
            System.out.println("Servidor activo en: " + isa);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
   
    public void recibir() {
        // Definir el tamaño del buffer
        this.buffer = ByteBuffer.allocate(1024);
        try {
            while(true) {
                if(this.canal.receive(this.buffer) != null) {
                    // De aquí voy a sacar la dirección IP y el puerto
                    SocketAddress sa = this.canal.receive(this.buffer);
                    // Preparar el buffer para lectura, mover el puntero al índice 0
                    this.buffer.flip();
                    // Mostramos el mensaje que contiene
                    System.out.println("Cliente dice: " + new String(this.buffer.array()));

                    // Mandarle el mismo mensaje como respuesta
                    this.canal.send(this.buffer, sa);
                    break;
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) throws InterruptedException {
        Servidor s = new Servidor(5000);
        s.recibir();
    }
}
