package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author david
 */
public interface InterfazCliente extends Remote {
    
    public void enviarMensaje(String msj) throws RemoteException;
    public String getNombre() throws RemoteException;
}
