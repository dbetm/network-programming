package dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Equipo: David Betancourt Montellano + Onder Francisco Campos García
 * Tema del programa: Cliente DNS
 * Descripción: Puede enviar peticiones al servidor para resolver IPv4's y dominios
 * Fecha: 19 de marzo del 2019.
 */
public class Cliente {
    private DatagramPacket envio;
    private DatagramPacket recibe;
    private DatagramSocket datagramSocket;
    private int puerto;

    public Cliente(int puerto) {
        this.puerto = puerto;
        try {
            this.datagramSocket = new DatagramSocket();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // El servidor debe regresarle un dominio para poder formar la URL
    public URL resolverIP(String ip) {
        URL url = null;
        byte buffer[] = ip.getBytes();
        try {
            // Instanciamos el envío
            this.envio = new DatagramPacket(buffer, buffer.length, 
                InetAddress.getLocalHost(), this.puerto);
            // Enviamos la petición
            this.datagramSocket.send(envio);
            // Construimos la URL
            url = new URL(recibirDominio());
        }
        catch (MalformedURLException | UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return url;
    }
    
    // Recibe la representación de la URL como un string
    public String recibirDominio() {
        String mensaje = "http://www.ipn.mx";
        //Creamos un buffer de tamaño 2048 bytes
        byte buffer[] = new byte[2048];
        //this.recibe = new DatagramPacket(buffer, buffer.length);
        try {
            this.datagramSocket = new DatagramSocket(8080);
            this.recibe = new DatagramPacket(buffer, buffer.length);
            this.datagramSocket.receive(this.recibe);
            mensaje = new String(this.recibe.getData());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return mensaje;
    }
    
    // En este método se le pide al servidor que devuelva todas las IP's que
    // tienen el dominio que se envía
    public String[] resolverDominio(URL dominio) {
        String ips[] = null;
        // Obtenemos la representación del dominio
        byte buffer[] = dominio.toString().getBytes();
        try {
            // Instanciamos el envío
            this.envio = new DatagramPacket(buffer, buffer.length,
                InetAddress.getLocalHost(), this.puerto);
            // enviamos
            this.datagramSocket.send(this.envio);
            // recibimos el grupo de ips
            ips = recibirIPs();
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return ips;
    }
    
    private String[] recibirIPs() {
        // Cerramos la anterior conexión
        this.datagramSocket.close();
        String ips[] = new String[]{"0.0.0.0"};
        // Vamos a recibir un string con las direcciones IP
        byte buffer[] = new byte[2048];
        this.recibe = new DatagramPacket(buffer, buffer.length);
        try {
            this.datagramSocket = new DatagramSocket(8080);
            this.datagramSocket.receive(this.recibe);
            String datos = new String(this.recibe.getData());
            // reemplazamos los espacios
            datos = datos.replace(" ", "");
            datos = datos.replace("[", "");
            datos = datos.replace("]", "");
            ips = datos.split(",");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ips;
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
    
    public static void main(String args[]) {
        System.out.println("Soy cliente");
        Cliente c = new Cliente(5500);
        String ip = "28.49.49.23";
        String dominio = "http://www.google.com";
        
        String ip2 = "45.57.105.29";
        String dominio2 = "http://www.facebook.com";
        
        String ip3 = "182.141.224.0";
        String dominio3 = "http://www.codesignal.com";
        String ips[];
        URL url;
        if(Cliente.validarIPv4(ip3)) {
            try {
                // Enviamos una IP y esperamos recibir un dominio
                url = c.resolverIP(ip3);
                // Imprimimos la representación de la URL
                System.out.println("Resolución de IP: " + ip3 + " => " + url.toString());
                
                // Enviamos un dominio y esperamos recibir un arreglo de IPs
                ips = c.resolverDominio(new URL(dominio3));
                System.out.println("Resolución de dominio: " + dominio3 + " => ");
                // Mostramos cada una de las IP's:
                for (int i = 0; i < ips.length; i++) {
                    System.out.println(ips[i]);
                }
  
            }
            catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        else {
            System.out.println("Vuelve a intentarlo!"); 
        }
    }
}