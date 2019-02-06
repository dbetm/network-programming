package Ejercicio3_archivos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Servidor {
    public static void main(String args[]) {
        try {
            ServerSocket servidor = new ServerSocket(5500);
            Socket cliente = servidor.accept();
            InputStream llegada = cliente.getInputStream();
            FileOutputStream destino = new FileOutputStream("files/globe_copy.jpg");
            byte buffer[] = new byte[1024];
            int len = 0;
            
            while((len = llegada.read(buffer)) > 0) {
                destino.write(buffer, 0, len);
            }
            
            destino.close();
            llegada.close();
            cliente.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
