package E6_NIOFiles;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author david
 */
public class Cliente {
    private SocketChannel sc;
    private FileChannel archivo;
    private Path ruta;
    private String host;
    private int port;
    private ByteBuffer buffer;

    public Cliente(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            // Crear el socket
            this.sc = SocketChannel.open();
            // Hacer la conexión
            this.sc.connect(new InetSocketAddress(this.host, this.port));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void elegirArchivo(String ruta) {
        this.ruta = Paths.get(ruta);
    }
    
    public void enviarArchivo() {
        try {
            this.archivo = FileChannel.open(this.ruta);
            this.buffer = ByteBuffer.allocate(1024);
            // Leer un paquete de hasta 1024 bytes
            while(this.archivo.read(this.buffer) > 0) {
                // preparar el buffer para escritura
                this.buffer.flip();
                this.sc.write(this.buffer);
                // limpiar el buffer para que no haya basura
                this.buffer.clear();
            }
            System.out.println("Archivo enviado");
            this.archivo.close();
            this.sc.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void main(String args[]) {
        Cliente c = new Cliente("127.0.0.1", 5500);
        c.elegirArchivo("/home/david/Dropbox/Dev/C/Operadores_carne.c");
        c.enviarArchivo();
    }
}
