package ejercicio2_banco;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author david
 */
public interface Banco extends Remote {
    public CuentaBancaria crearCuenta(Titular t, double cantidad) throws RemoteException;
    public CuentaBancaria crearCuenta(String nombre, String clave, double cantidad) 
        throws RemoteException;
    public List<CuentaBancaria> obtenerCuentas() throws RemoteException;
    
}
