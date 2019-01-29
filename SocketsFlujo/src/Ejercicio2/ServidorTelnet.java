package Ejercicio2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Servidor Telnet
 * Descripción: Se ha implementado un servidor Telnet el cual muestra los mensajes
 *             que recibe de cierto cliente hasta que este escribe "SALIR".
 * Fecha: Lunes 28 de enero del 2019.
 */
public class ServidorTelnet {
    private ServerSocket servidor;
    private Socket cliente;
    private int puerto;
    private BufferedReader entrada;
    private DataOutputStream salida;
    
    public ServidorTelnet(int puerto) {
        this.puerto = puerto;
        try {
            this.servidor = new ServerSocket(this.puerto);
            this.cliente = this.servidor.accept();
            // Entrada, recibir mensajes
            this.entrada = new BufferedReader(new InputStreamReader(
                this.cliente.getInputStream()));

            while(true) {
                String mensaje = this.entrada.readLine();
                if(mensaje.equals("SALIR")) break;
                System.out.println(mensaje);
            }
            // Salida, mandar mensaje de despedida
            this.salida = new DataOutputStream(this.cliente.getOutputStream());
            this.salida.writeUTF("\nBye");
            // Cerramos la conexión con el cliente
            cliente.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        ServidorTelnet st = new ServidorTelnet(5500);
    }
}
