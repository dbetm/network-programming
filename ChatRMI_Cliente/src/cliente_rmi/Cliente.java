package cliente_rmi;

import interfaces.InterfazCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author david
 */
public class Cliente extends UnicastRemoteObject implements InterfazCliente {
    private String username;
    private ClienteGUI interfazGrafica;

    public Cliente(String username) throws RemoteException {
        super();
        this.username = username;
    }
    
    @Override
    public void enviarMensaje(String msj) throws RemoteException {
        this.interfazGrafica.actualizarAreaMensajes(msj);
    }

    @Override
    public String getNombre() throws RemoteException {
        return this.username;
    }
    
    public void setGUI(ClienteGUI cg) {
        this.interfazGrafica = cg;
    }
    
}
