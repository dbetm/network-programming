package E4_NIODatagramas;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author david
 */
public class Cliente {
    private DatagramChannel canal;
    private ByteBuffer buffer;
    private int puerto;
    private String direccion;
    
    public Cliente(int puerto, String direccion) {
        this.puerto = puerto;
        this.direccion = direccion;
        try {
            // Abrimos el canal de comunicación
            this.canal = DatagramChannel.open();
            // Activar modo no bloqueante
            this.canal.configureBlocking(false);
            // Conectamos, pero con nulo, ya que es cliente no servidor
            this.canal.bind(null);
            System.out.println("Cliente activo");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void enviar(String mensaje) {
        try {
            // Creando el buffer con un límite obtenido del mensaje
            this.buffer = ByteBuffer.wrap(mensaje.getBytes());
            // Para poder enlazar
            InetSocketAddress isa = new InetSocketAddress(this.direccion, this.puerto);
            this.canal.send(this.buffer, isa);
            // Limpiar el buffer y setear apuntador en 0
            this.buffer.clear();
            // flip
            this.buffer.flip();
            // Recibimos la respuesta
            this.canal.receive(this.buffer);
            // Mostramos el mensaje que recibo
            System.out.println("Servidor dice: " + new String(this.buffer.array()));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                Thread.sleep(5000);
                this.canal.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) {
        Cliente c = new Cliente(5000, "127.0.0.1");
        c.enviar("Hola mundo");
        
    }
}
