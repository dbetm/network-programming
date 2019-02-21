package p3NIOFiles;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Cliente
 * Descripción: Recibe y envia archivos
 * Fecha: Febrero del 2019
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
            this.sc.configureBlocking(false);
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
            this.buffer.clear();
            // Leer un paquete de hasta 1024 bytes
            while(this.archivo.read(this.buffer) > 0) {
                // preparar el buffer para escritura
                this.buffer.flip();
                this.sc.write(this.buffer);
                // limpiar el buffer para que no haya basura
                this.buffer.clear();
            }
            System.out.println("Archivo enviado [desde cliente]");
            this.archivo.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibirArchivo() {
        try {
            this.ruta = Paths.get("files/p3/cliente/tormenta_copia.jpg");
            this.archivo = FileChannel.open(this.ruta,
                EnumSet.of(StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)
            );
            this.buffer = ByteBuffer.allocate(1024);
            this.buffer.clear();
            while (this.sc.read(this.buffer) > 0) {
                this.buffer.flip();
                // Escribirmos en el archivo
                this.archivo.write(this.buffer);
                this.buffer.clear();
            }
            System.out.println("¡Archivo recibido! - Cliente");
            this.archivo.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                this.sc.close();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    public static void main(String args[]) throws InterruptedException {
        Cliente c = new Cliente("127.0.0.1", 5500);
        // Descomentar para hacer prueba de envío
        //c.elegirArchivo("files/p3/cliente/dragon.png");
        //c.enviarArchivo();
        
        // Comentar para hacer prueba de envío
        Thread.sleep(16000);
        c.recibirArchivo();
    }
}
