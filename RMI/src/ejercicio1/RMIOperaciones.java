package ejercicio1;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author david
 */
// La interfaz remota
public interface RMIOperaciones extends Remote {
    // Métodos abstractos
    
    public double sumar(double a, double b) throws RemoteException;
    public double restar(double a, double b) throws RemoteException;
    public double dividir(double a, double b) throws RemoteException;
    public double multiplicar(double a, double b) throws RemoteException;
    public double potencia(double base, double exponente) throws RemoteException;
    public double porcentaje(double a, double b) throws RemoteException;
    public double raizCuadrada(double a) throws RemoteException;
    public double reciproco(double a) throws RemoteException;
}
