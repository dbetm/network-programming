package Ejercicio3_archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Cliente {
    /*
    public static void main(String args[]) {
        try {
            //Socket a = new Socket("127.0.0.1", 5500);
            //OutputStream os = a.getOutputStream();
            // Leer el archivo
            File f = new File("files/example1.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            
            String linea = "", aux;
            
            while((aux = bf.readLine()) != null) {
                linea += aux + "\n";
            }
            
            bf.close();
            System.out.println(linea);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    
    public static void main(String args[]) {
        try {
            Socket a = new Socket("127.0.0.1", 5500);
            OutputStream os = a.getOutputStream();
            PrintStream envio = new PrintStream(os);
            // Leer el archivo
            FileInputStream origen = new FileInputStream("files/globe.jpg");
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = origen.read(buffer)) > 0) {
                // envíando el archivo en paquetitos de flujo de bytes
                envio.write(buffer,0, len);
            }
            origen.close();
            envio.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
