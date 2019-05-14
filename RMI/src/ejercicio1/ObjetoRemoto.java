package ejercicio1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author david
 */
public class ObjetoRemoto extends UnicastRemoteObject implements RMIOperaciones {

    public ObjetoRemoto() throws RemoteException {
        super();
    }

    @Override
    public double sumar(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double restar(double a, double b) throws RemoteException {
        return a - b;
    }

    @Override
    public double dividir(double a, double b) throws RemoteException {
        return a / b;
    }

    @Override
    public double multiplicar(double a, double b) throws RemoteException {
        return a * b;
    }

    @Override
    public double potencia(double base, double exponente) throws RemoteException {
        return Math.pow(base, exponente);
    }

    @Override
    public double porcentaje(double a, double b) throws RemoteException {
        return (b * 100) / a;
    }

    @Override
    public double raizCuadrada(double a) throws RemoteException {
        return Math.sqrt(a);
    }

    @Override
    public double reciproco(double a) throws RemoteException {
        return 1 / a;
    }
}
