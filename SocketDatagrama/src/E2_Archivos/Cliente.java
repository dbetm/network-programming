package E2_Archivos;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * El cliente va enviar un archivo al servidor
 * @author david
 */
public class Cliente {
    private DatagramSocket socket;
    private DatagramPacket envio;
    private DatagramPacket recibe;
    private long numPaquetes;
    private File myFile;
    // para leer el archivo que se indique con File
    private BufferedInputStream bis;
    private int lenPaquete;

    public Cliente(String path) { 
        this.lenPaquete = 1024;
        try {
            this.socket = new DatagramSocket();
            this.myFile = new File(path);
            this.bis = new BufferedInputStream(new FileInputStream(this.myFile));
            this.numPaquetes = (long)Math.ceil((double)this.myFile.length() / (double)this.lenPaquete);
            System.out.println(this.numPaquetes);
        }
        catch (SocketException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void enviarArchivo() {
        byte buffer[];
        try {
            for (int j = 0; j < this.numPaquetes; j++) {
                buffer = new byte[this.lenPaquete];
                bis.read(buffer, 0, buffer.length);
                System.out.println("Núm paquete: " + j);
                this.envio = new DatagramPacket(buffer, buffer.length,
                    InetAddress.getByName("127.0.0.1"), 5500);
                this.socket.send(this.envio);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        // Cerramos el buffer de lectura y el envío
        try {
           this.bis.close(); 
           //this.socket.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void main(String args[]) {
        Cliente c = new Cliente("archivos/ninja.png");
        c.enviarArchivo();
    }
}
