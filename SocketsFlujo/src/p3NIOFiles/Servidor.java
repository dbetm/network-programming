package p3NIOFiles;

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
 * Nombre: David Betancourt Montellano
 * Tema del programa: Servidor
 * Descripción: Recibe y envia archivos
 * Fecha: Febrero del 2019
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
            // Desactivamos el modo bloqueante
            this.ssc.configureBlocking(false);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibirArchivo() {
        try {
            System.out.println("Esperando conexión...");
            
            while(true) {
                this.sc = this.ssc.accept();
                if(this.sc == null) Thread.sleep(8000);
                else {
                    System.out.println("Se conectó: " + this.sc.getRemoteAddress());
                    this.ruta = Paths.get("files/p3/servidor/dragon_copia.png");
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
                        // Limpiamos el buffer
                        this.buffer.clear();
                    }
                    System.out.println("¡Archivo recibido! - Servidor");
                    this.archivo.close();
                    // Salimos del ciclo infinito
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                // Cerramos el cliente
                this.sc.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void elegirArchivo(String ruta) {
        this.ruta = Paths.get(ruta);
    }
    
    public void enviarArchivo() {
        try {
            System.out.println("Esperando conexión...");
            while(true) {
                this.sc = this.ssc.accept();
                if(this.sc == null) Thread.sleep(4000);
                else {
                    this.archivo = FileChannel.open(this.ruta);
                    this.buffer = ByteBuffer.allocate(1024);
                    this.buffer.clear();
                    // Leer un paquete de hasta 1024 bytes
                    while(this.archivo.read(this.buffer) > 0) {
                        // Preparar para escritura
                        this.buffer.flip();
                        this.sc.write(this.buffer);
                        // limpiar el buffer para que no haya basura
                        this.buffer.clear();
                    }
                    System.out.println("Archivo enviado [desde servidor]");
                    this.archivo.close();
                    break;
                }
            }
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                // Cerramos el cliente
                this.sc.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) {
        Servidor s = new Servidor(5500);
        // Descomentar para hacer prueba de recibir
        //s.recibirArchivo();
        
        // Comentar para hacer prueba de recibir
        s.elegirArchivo("files/p3/servidor/tormenta.jpg");
        s.enviarArchivo();
    }
}
