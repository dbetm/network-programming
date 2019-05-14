package ejercicio1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author david
 */
public class Servidor {
    public static void main(String args[]) {
        try {
            ObjetoRemoto or = new ObjetoRemoto();
            // Lo habitual es trabajar en el puerto 1099
            Registry reg = LocateRegistry.createRegistry(1099);
            // Publicar el objeto remoto
            reg.bind("Objeto remoto", or);
            System.out.println("Servidor activo...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
