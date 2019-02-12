package E5_IntroNIO;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author david
 */
public class LecturaArchivo {
    public static void main(String args[]) {
        try {
            RandomAccessFile aFile = new RandomAccessFile("files/example1.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            // Creamos el buffer con la capacidad de 128
            ByteBuffer buffer = ByteBuffer.allocate(128);
            // Saber cuántos bytes leer cada vez en el ciclo
            int bytesRead = inChannel.read(buffer);
            while(bytesRead != -1) {
                // Mostrar en pantalla los bytes leídos
                System.out.println("Read " + bytesRead);
                // Cambiar de lectura<->escritura
                buffer.flip();
                while (buffer.hasRemaining()) {
                    // Vamos imprimiendo cada carácter
                    System.out.print((char)buffer.get());
                }
                System.out.println("\n");
                // Limpiamos el buffer
                buffer.clear();
                // Volvemos a leer, guardando la cantidad leída en bytesRead
                bytesRead = inChannel.read(buffer);
            }
            // Cerramos el archivo
            aFile.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
