package examen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author david
 */
public class Cliente {

    private Socket s;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private int puerto;
    private String ip;

    public Cliente(int puerto, String ip) {
        this.puerto = puerto;
        this.ip = ip;
        try {
            this.s = new Socket(ip, puerto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean soyCliente1() {
        boolean ans = true;
        try {
            this.entrada = new DataInputStream(this.s.getInputStream());
            if(this.entrada.readUTF().equals("1")) ans = false;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return ans;
    }
    
    public void recibir() {
        try {
            System.out.println("Recibiendo mensaje...");
            this.entrada = new DataInputStream(this.s.getInputStream());
            String msj = this.entrada.readUTF();
            System.out.println(msj);
            if(msj.equals("SALIR")) this.s.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void enviar() {
        Scanner sc = new Scanner(System.in);
        try {
            this.salida = new DataOutputStream(this.s.getOutputStream());
            System.out.print("Escribe tu msj: ");
            String msj = sc.nextLine();
            this.salida.writeUTF(msj);
            if(msj.equals("SALIR")) this.s.close();
        }
        catch (Exception e) {
        }
    }
    
    public boolean isClosed() {
        return this.s.isClosed();
    }    
    public static void main(String args[]) {
        Cliente c = new Cliente(5000, "127.0.0.1");
        if(c.soyCliente1()) {
            while (!c.isClosed()) {  
                c.enviar();
                if(c.isClosed()) break;
                c.recibir();
            }
        }
        else {
           while (!c.isClosed()) {            
                c.recibir();
                if(c.isClosed()) break;
                c.enviar();
            } 
        }
    }
    
}
