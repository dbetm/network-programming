package E5_IntroNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
/**
 *
 * @author david
 */
public class ServidorTelnet {
    private ServerSocketChannel ssc;
    private SocketChannel cliente;
    private ByteBuffer buffer; // entrada y salida
    private int puerto;

    public ServidorTelnet(int puerto) {
        this.puerto = puerto;
        try {
            this.ssc = ServerSocketChannel.open();
            this.ssc.socket().bind(new InetSocketAddress(this.puerto));
            // Configurar para que no tenga modo bloqueante
            this.ssc.configureBlocking(false);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void escuchar() {
        try {
            // Enviar mensaje al cliente
            this.buffer = ByteBuffer.wrap("Mensaje recibido\n".getBytes());
            System.out.println("Esperando conexiones...");
            // Aceptar una petición del cliente
            
            while (true) {    
                this.cliente = this.ssc.accept();
                if(this.cliente == null) Thread.sleep(8000);
                else {
                    System.out.println("¡El cliente se ha conectado! " + 
                    this.ssc.socket().toString());
                    // Resetear posiciones
                    this.buffer.rewind();
                    this.cliente.write(this.buffer);
                    this.buffer.clear();
                    this.cliente.read(this.buffer);
                    this.buffer.flip();
                    while(this.buffer.hasRemaining()) {
                        System.out.print((char)this.buffer.get());
                    }
                    //Cerramos
                    this.cliente.close();
                    break;
                }
            }
            //System.out.println("FIN");
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        ServidorTelnet st = new ServidorTelnet(4500);
        st.escuchar();
    }
}
