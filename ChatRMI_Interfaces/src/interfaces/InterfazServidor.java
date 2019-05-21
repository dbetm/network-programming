package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author david
 */
public interface InterfazServidor extends Remote {
    public void registrar(InterfazCliente cliente) throws RemoteException;
    public void publicar(String msj) throws RemoteException;
    public Vector obtenerClientesActivos() throws RemoteException;
    public void borrarCliente(InterfazCliente cliente) throws RemoteException;
}
