package ejercicio2_banco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author david
 */
public class ObjetoCuentaBancaria extends UnicastRemoteObject implements CuentaBancaria {
    private Titular titular;
    private double saldo;

    public ObjetoCuentaBancaria(Titular titular, double dinero) throws RemoteException {
        super();
        this.titular = titular;
        this.saldo = dinero;
    }

    public ObjetoCuentaBancaria(String nombre, String clave, double saldo) 
            throws RemoteException {
        super();
        this.titular = new Titular(nombre, clave);
        this.saldo = saldo;
    }
    

    @Override
    public double consultarSaldo() throws RemoteException {
        return this.saldo;
    }

    @Override
    public void depositar(double cantidad) throws RemoteException {
        this.saldo = (cantidad > 0) ? this.saldo+cantidad : this.saldo;
    }

    @Override
    public void retirar(double cantidad) throws RemoteException {
        this.saldo = (this.saldo >= cantidad) ? this.saldo - cantidad : this.saldo;
    }

    @Override
    public Titular obtenerTitular() throws RemoteException {
        return this.titular;
    }   
}
