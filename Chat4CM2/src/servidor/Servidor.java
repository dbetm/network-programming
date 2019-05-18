package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Servidor {
    private ServerSocket servidor;
    private Socket socket;
    private int puerto;
    private String ip;
    private Mensaje msj;
    private int maxConexiones;

    public Servidor(int puerto, String ip, int maximoConexiones) {
        this.puerto = puerto;
        this.ip = ip;
        this.maxConexiones = maximoConexiones;
        this.msj = new Mensaje();
        try {
            this.servidor = new ServerSocket(puerto, maxConexiones);
            escuchar();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void escuchar() {
        while(1 == 1) {
            try {
                this.socket = this.servidor.accept();
                System.out.println("Se conectó el cliente: " + 
                    this.socket.getInetAddress().getHostName() + ", " + 
                    this.socket.getPort());
                ConexionCliente cc = new ConexionCliente(this.socket, this.msj);
                cc.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) {
        Servidor servidor = new Servidor(8090, "127.0.0.1", 5);
    }
}
