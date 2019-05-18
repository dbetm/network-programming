package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author david
 */
public class ConexionCliente extends Thread implements Observer {
    private Socket socket;
    private Mensaje mensaje;
    // Canales I/O
    private DataInputStream entrada;
    private DataOutputStream salida;

    public ConexionCliente(Socket socket, Mensaje mensaje) {
        this.socket = socket;
        this.mensaje = mensaje;
        try {
            // Instanciamos los canales
            this.entrada = new DataInputStream(this.socket.getInputStream());
            this.salida = new DataOutputStream(this.socket.getOutputStream());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        String mensajeNuevo;
        boolean conectado = true;
        this.mensaje.addObserver(this);
        while(conectado) {
            try {
                mensajeNuevo = this.entrada.readUTF();
                this.mensaje.setMensaje(mensajeNuevo);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                conectado = false;
                try {
                    this.entrada.close();
                    this.salida.close();
                }
                catch (IOException ex1) {
                    ex1.printStackTrace();
                }
                
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            // Necesito notificar que el mensaje llega
            this.salida.writeUTF(arg.toString());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
