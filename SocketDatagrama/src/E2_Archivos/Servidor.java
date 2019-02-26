package E2_Archivos;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author david
 */
public class Servidor {
    private DatagramSocket servidor;
    private DatagramPacket recibe;
    private DatagramPacket envio;
    private FileOutputStream myFile;
    private BufferedOutputStream bos;

    public Servidor(int puerto) {
        try {
            this.servidor = new DatagramSocket(puerto);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void recibirArchivo(String path) {
        try {
            this.myFile = new FileOutputStream(path);
            this.bos = new BufferedOutputStream(this.myFile);
            // Creo el buffer
            byte buffer[] = new byte[1024];
            this.recibe = new DatagramPacket(buffer, buffer.length);
            int i = 0;
            // Tiempo de espera
            this.servidor.setSoTimeout(10000);
            while(true) {
                this.servidor.receive(this.recibe);
                this.bos.write(this.recibe.getData(), 0, this.recibe.getLength());
                System.out.println(i++);
                if(this.recibe.getLength() < 1024) break;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
        finally {
            try {
                this.bos.close();
                this.myFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
    public static void main(String args[]) {
        Servidor s = new Servidor(5500);
        s.recibirArchivo("archivos/ninja_copy.png");
    }
}
