package telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author david
 */
public class HiloCliente extends Thread {
    private Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;

    public HiloCliente(Socket cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public void run() {
        try {
            this.salida = new PrintWriter(this.cliente.getOutputStream());
            //System.out.println("Cliente: " + this.cliente.toString());
            this.salida.print("Hola, cliente\n");
            // Recibimos una respuesta de él
            this.entrada = new BufferedReader(new InputStreamReader(
                this.cliente.getInputStream()));
            //System.out.println("Cliente dice: " + this.entrada.readLine());
            String entrada = this.entrada.readLine();
            if(entrada.equals("#")) System.out.println("");
            else System.out.print(entrada);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                this.salida.close();
                this.cliente.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
