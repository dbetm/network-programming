package Ejercicio4_Objetos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Cliente {
    public static void main(String args[]) {
        try {
            Socket cliente = new Socket("127.0.0.1", 4000);
            ObjectInputStream llegada = new ObjectInputStream(cliente.getInputStream());
            Empleado e1 = (Empleado) llegada.readObject();
            System.out.println(e1.toString());
            llegada.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
