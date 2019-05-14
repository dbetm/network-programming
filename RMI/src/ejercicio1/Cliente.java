package ejercicio1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author david
 */
public class Cliente {
    public static void main(String args[]) {
        try {
            RMIOperaciones rmiOp;
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            rmiOp = (RMIOperaciones) reg.lookup("Objeto remoto");
            // Sumar
            System.out.println("Suma: " + rmiOp.sumar(23, 34));
            // Resta
            System.out.println("Resta: " + rmiOp.restar(23, 34));
            // Multiplicación
            System.out.println("Mutiplicación: " + rmiOp.multiplicar(23, 34));
            // División
            System.out.println("Dividir: " + rmiOp.dividir(23, 34));
            // Potencia
            System.out.println("Exponenciación: " + rmiOp.potencia(23, 34));
            // Porcentaje
            System.out.println("Porcentaje: " + rmiOp.dividir(23, 34));
            // Raíz cuadrada
            System.out.println("Raíz cuadrada: " + rmiOp.raizCuadrada(23));
            // Recíproco
            System.out.println("Reciproco: " + rmiOp.reciproco(23));
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (NotBoundException ex) {
           ex.printStackTrace();
        }
    }
}
