package servidor_rmi;

import interfaces.InterfazCliente;
import interfaces.InterfazServidor;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Objeto remoto
 * @author david
 */
public class Servidor extends UnicastRemoteObject implements InterfazServidor {
    private Vector listaClientes;

    public Servidor() throws RemoteException {
        super();
        this.listaClientes= new Vector();
    }
    

    @Override
    public void registrar(InterfazCliente cliente) throws RemoteException {
        if(cliente != null) {
            this.listaClientes.add(cliente);
            publicar(cliente.getNombre() + " se ha unido al chat.");
        }
    }

    // Este método recibe un mensaje de algún cliente y éste se los envia a
    // cada una de los clientes que se encuentra en la lista, incluyéndose a sí
    // mismo.
    @Override
    public void publicar(String msj) throws RemoteException {
        for (int i = 0; i < this.listaClientes.size(); i++) {
            InterfazCliente cliente = (InterfazCliente) this.listaClientes.get(i);
            cliente.enviarMensaje(msj);
        }
    }

    @Override
    public Vector obtenerClientesActivos() throws RemoteException {
        return this.listaClientes;
    }
    
    @Override
    public void borrarCliente(InterfazCliente cliente) throws RemoteException {
        String nombre = cliente.getNombre();
        if(this.listaClientes.remove(cliente)) {
            publicar(nombre + " ha salido del chat.");
        }
    }
    
    public static void main(String args[]) {
        try {
            Servidor servidor = new Servidor();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.bind("servidor", servidor);
            System.out.println("Servidor activo...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
