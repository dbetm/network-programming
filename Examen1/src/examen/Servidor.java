package examen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Servidor {
    private ServerSocket ss;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private int puerto;
    private ArrayList<Socket> clientes;
    private int idCliente;

    public Servidor(int puerto) {
        this.idCliente = 0;
        this.puerto = puerto;
        this.clientes = new ArrayList<>();
        try {
            this.ss = new ServerSocket(this.puerto);
            while (true) {                
                this.clientes.add(ss.accept());
                asignarID();
                this.idCliente = 1;
                if(this.clientes.size() == 2) break;
            }
            this.idCliente = 0;
        }
        catch (Exception e) {
        }
    }
    
    public void conversar() {
        try {
            while (true) {                
                System.out.println("Esperando mensaje...");
                this.entrada = 
                    new DataInputStream(this.clientes.get(this.idCliente).getInputStream());
                String mensaje = this.entrada.readUTF(); 

                this.idCliente = (this.idCliente == 0)? 1: 0;

                this.salida = 
                    new DataOutputStream(this.clientes.get(this.idCliente).getOutputStream());
                this.salida.writeUTF(mensaje);
                
                if(mensaje.equals("SALIR")) {
                    this.clientes.get(0).close();
                    this.clientes.get(1).close();
                    this.ss.close();
                    break;
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void asignarID() {
        try {
            this.salida = 
                new DataOutputStream(this.clientes.get(this.idCliente).getOutputStream());
            if(this.clientes.size() == 1)
                this.salida.writeUTF("0");
            else this.salida.writeUTF("1"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String args[]) {
        Servidor s = new Servidor(5000);
        s.conversar();
    }

}
