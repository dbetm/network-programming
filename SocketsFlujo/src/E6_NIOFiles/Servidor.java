package E6_NIOFiles;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 *
 * @author david
 */
public class Servidor {
    private ServerSocketChannel ssc;
    private SocketChannel sc;
    private ByteBuffer buffer;
    private int puerto;
    private Path ruta;
    private FileChannel archivo;

    public Servidor(int puerto) {
        this.puerto = puerto;
        try {
            this.ssc = ServerSocketChannel.open();
            this.ssc.socket().bind(new InetSocketAddress(this.puerto));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibirArchivo() {
        try {
            this.sc = this.ssc.accept();
            System.out.println("Se conectó: " + this.sc.getRemoteAddress());
            this.ruta = Paths.get("files/nio/program.c");
            this.archivo = FileChannel.open(this.ruta, 
                EnumSet.of(StandardOpenOption.CREATE, 
                    StandardOpenOption.TRUNCATE_EXISTING, 
                    StandardOpenOption.WRITE)
            );
            this.buffer = ByteBuffer.allocate(1024);
            this.buffer.clear();
            while (this.sc.read(this.buffer) > 0) {
                // Preparar para escritura
                this.buffer.flip();
                // Escribirmos en el archivo
                this.archivo.write(this.buffer);
                this.buffer.clear();
            }
            System.out.println("¡Archivo recibido!");
            this.archivo.close();
            this.sc.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Servidor s = new Servidor(5500);
        s.recibirArchivo();
    }
}
