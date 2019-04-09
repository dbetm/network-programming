package telnet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author david
 */
public class Servidor {
    private int puerto;
    private ServerSocket servidor;
    private Socket cliente;

    public Servidor(int puerto) {
        this.puerto = puerto;
        
        try {
            this.servidor = new ServerSocket(this.puerto);
            escuchar();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void escuchar() {
       for(;;) {
           try {
               this.cliente = servidor.accept();
               // GESTIONAR CON HILOS
               HiloCliente h = new HiloCliente(this.cliente);
               h.start();
           }
           catch (IOException ex) {
               ex.printStackTrace();
           }
       }
    }
    
    public static void main(String args[]) {
        Servidor serv = new Servidor(4444);
    }
}
