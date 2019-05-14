package ejercicio2_banco;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author david
 */
public class Servidor {
    public static void main(String args[]) {
        try {
            // Se instancia el objeto remoto banco
            ObjetoBanco objBanco = new ObjetoBanco();
            // Se instancia el objeto remoto cuenta bancaria
            //ObjetoCuentaBancaria objCuenta = new ObjetoCuentaBancaria
            // Instanciamos un registry
            Registry reg = LocateRegistry.createRegistry(1099);
            // Publicar el objeto remoto
            reg.bind("Baaanco", objBanco);
            System.out.println("Servidor activo...");
        }
        catch (Exception e) {
            
        }
    }
}
