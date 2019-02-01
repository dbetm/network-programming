package p1SimEcho;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Cliente para servidor de comando 'echo'
 * Descripción: La clase cliente se ejecuta con argumentos iniciales (IP MENSAJE
 *     PUERTO-opcional) y al mandar un mensaje al servidor este le regresa el 
 *     mismo mensaje, con esto se simula un servicio que simula el comando 'echo'
 * Fecha: Jueves 31 de enero del 2019.
 */
public class Cliente {
    // Atributos
    private String ip;
    private final int puerto;
    private final String mensaje;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Socket cliente;
    
    public Cliente(String ip, String mensaje) {
        this.puerto = 5500; // por defecto se asigna el puerto 5500
        System.out.println("Por defecto se seleccionó el puerto: 5500");
        this.mensaje = mensaje;
    }
    
    public Cliente(String ip, String mensaje, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }
    
    private String hacerEcho() {
        String respuesta;
        try {
            // Creamos el ciente
            this.cliente = new Socket(this.ip, this.puerto);
            
            // Definimos la salida
            this.salida = new DataOutputStream(this.cliente.getOutputStream());
            // Le escribimos el mensaje al servidor
            this.salida.writeUTF(this.mensaje);
            
            // Definimos la entrada, para poder recuperar el mensaje respuesta
            this.entrada = new DataInputStream(this.cliente.getInputStream());
            // Mostramos el mensaje que regresa el servidor
            respuesta = ">> " + this.entrada.readUTF();
            
            // Cerramos la conexión
            this.cliente.close();
        }
        catch (IOException ex) {
            // Recuperamos el mensaje de la excepción
            respuesta = ex.getMessage();
        }
        return respuesta;
    }
    
    public static boolean validarArgumentos(String args[]) {
        boolean ans = true;
        // Se valida por longitud
        if(args.length != 2 && args.length != 3) ans = false;
        // Se valida la IP
        else if(!validarIPv4(args[0])) ans = false;
        // Se valida el puerto
        else if(args.length == 3 && !validarPuerto(args[2])) ans = false;
        
        return ans;
    }
    
    public static boolean validarIPv4(String ip) {
        // ¿La IP solo consta de números y puntos?
        for (int i = 0; i < ip.length(); i++) {
            if((int)ip.charAt(i) != 46 && ((int)ip.charAt(i) < 48 || (int)ip.charAt(i) > 57)) {
                System.out.println("Error: IP no válida");
                return false;
            }
        }
        
        String octetos[] = ip.split("\\.");
        int octeto;
        // ¿Es una IP completa?
        if(octetos.length != 4) {
            System.out.println("Error: IP incompleta");
            return false;
        }
        try {
            // Se valida cada octeto de la IP
            iterarOctetos: for (String oc : octetos) {
                octeto = Integer.parseInt(oc);
                if(octeto < 0 || octeto > 255) {
                    System.out.println("Error: IP no válida");
                    return false;
                }
            }   
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public static boolean validarPuerto(String port) {
        // ¿El puerto solo consta de números?
        for (int i = 0; i < port.length(); i++) {
            if((int)port.charAt(i) < 48 || (int)port.charAt(i) > 57) {
                System.out.println("Error: Puerto no válido");
                return false;
            }
        }
        
        try {
            // Verificamos que es un número entero
            int puerto = Integer.parseInt(port);
            // Aunque sea número, debe estar en el rango permitido
            if(puerto < 1025 || puerto > 65635) {
                System.out.println("Error: Puerto no permitido");
                return false;
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public static void main(String args[]) {
        // Primero se valida
        if(validarArgumentos(args)) { // son válidos los argumentos
            Cliente c;
            if(args.length == 2) c = new Cliente(args[0], args[1]);
            else c = new Cliente(args[0], args[1], Integer.parseInt(args[2]));
            System.out.println(c.hacerEcho());
        }
        else {  // No son válidos los argumentos
            System.out.println("Datos no validos, el formato es: <servidor> " + 
                "<mensaje> <puerto>*opcional");
        }
    }
}
