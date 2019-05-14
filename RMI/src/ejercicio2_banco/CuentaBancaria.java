package ejercicio2_banco;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author david
 */
public interface CuentaBancaria extends Remote {
    public double consultarSaldo() throws RemoteException;
    public void depositar(double cantidad) throws RemoteException;
    public void retirar(double cantidad) throws RemoteException;
    public Titular obtenerTitular() throws RemoteException;
}
