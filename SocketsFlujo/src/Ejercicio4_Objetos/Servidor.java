package Ejercicio4_Objetos;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Servidor {
    public static void main(String args[]) {
        try {
            ServerSocket servidor = new ServerSocket(4000);
            Socket cliente = servidor.accept();
            ObjectOutputStream envio = new ObjectOutputStream(cliente.getOutputStream());
            Empleado e = new Empleado("David", "Dirección X");
            // Enviar objeto
            envio.writeObject(e);
            System.out.println("Se envió el empleado");
            envio.close();
            cliente.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
