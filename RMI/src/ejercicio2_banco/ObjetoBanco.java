package ejercicio2_banco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author david
 */
public class ObjetoBanco extends UnicastRemoteObject implements Banco {
    private List<CuentaBancaria> listado;

    public ObjetoBanco() throws RemoteException {
        super();
        this.listado = new LinkedList<>();
    }

    @Override
    public CuentaBancaria crearCuenta(Titular t, double cantidad) throws RemoteException {
        ObjetoCuentaBancaria cuenta = new ObjetoCuentaBancaria(t, cantidad);
        this.listado.add(cuenta);
        return cuenta;
    }

    @Override
    public CuentaBancaria crearCuenta(String nombre, String clave, double cantidad) throws RemoteException {
        ObjetoCuentaBancaria cuenta = new ObjetoCuentaBancaria(nombre, clave, cantidad);
        this.listado.add(cuenta);
        return cuenta;
    }

    @Override
    public List<CuentaBancaria> obtenerCuentas() throws RemoteException {
        return this.listado;
    }
    
}
