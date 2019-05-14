package ejercicio2_banco;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author david
 */
public class Cliente {
    public static void main(String args[]) {
        try {
            Banco miBanco;
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            // Lo buscamos
            miBanco = (Banco) reg.lookup("Baaanco");
            miBanco.crearCuenta(new Titular("David Bet", "0101"), 50);
            miBanco.crearCuenta(new Titular("Pablo", "0202", "Av. 404", "mail@dot.com"), 60);
            miBanco.crearCuenta(new Titular("Karen", "0303"), 560);
            miBanco.crearCuenta(new Titular("Michelle", "3221", "Av. Dir", "mich@ele.com"), 443);
            
            // Obtenemos las cuentas 
            LinkedList<CuentaBancaria> listado = (LinkedList<CuentaBancaria>) miBanco.obtenerCuentas();
            
            // Hacemos un deposito al primer titutal registrado
            listado.get(0).depositar(50);
            
            // Hacemos un retiro
            listado.get(listado.size()-1).retirar(40);
            
            // Imprimimos los datos
            for (Iterator<CuentaBancaria> iterator = listado.iterator(); iterator.hasNext();) {
                CuentaBancaria next = iterator.next();
                System.out.println(next.obtenerTitular().toString() + 
                    "\nSu saldo es: " + next.consultarSaldo());
                System.out.println("-----------------------------------------");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
