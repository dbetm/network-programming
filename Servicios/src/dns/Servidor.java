package dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author wolfteinter
 */
public class Servidor {
    private DatagramPacket envio;
    private DatagramPacket recibe;
    private DatagramSocket datagramSocket;
    private int puerto = 5500;
    private ArrayList<String>[] Ips;
    private URL[] URLs;
    
    public Servidor() {
        URLs = new URL[100000000];
        Ips = new ArrayList[100000000];
        this.puerto = puerto;
        try {
            this.datagramSocket = new DatagramSocket(puerto);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int calcularHashCode(String url){
        long suma = 0;
        for(int i=0;i<url.length();i++) {
            double factor = (float) Math.pow(5, i);
            int val = (int)url.charAt(i); 
            suma += factor * val;
        }
        return (int) (suma%100000000);
    }
    
    //Para añadir un Dominio 
    public void agregarDireccion(URL url,String ip){
        int hashIp=calcularHashCode(ip);
        int hashUrl=calcularHashCode(url.toString());
        if(URLs[hashIp]==null){
           URLs[hashIp] = url;
        }else{
            System.out.println("ya existe");
        }
        if(Ips[hashUrl]==null){
            Ips[hashUrl] = new ArrayList<String>();
            Ips[hashUrl].add(ip);
        }else{
            Ips[hashUrl].add(ip);
        }
    }
    
    public static String limpiarIP(String ip) {
        String ans = "";
        for (int i = 0; i < ip.length(); i++) {
            if((int)ip.charAt(i) == 0) break;
            ans += ip.charAt(i);
        }
        return ans;
    }
    
    //Busca 
    private String buscarURL(String ip){
        ip = limpiarIP(ip);
        int hashIp=calcularHashCode(ip);
        System.out.println(hashIp);
        if(URLs[hashIp] != null) {
            return URLs[hashIp].toString();  
        }
        return "http://www.error.org";
    }
    
    private String buscarIP(URL url){
        int hashUrl=calcularHashCode(url.toString());
        if(Ips[hashUrl]!=null){
          return Arrays.toString(Ips[hashUrl].toArray());  
        }
        System.out.println("Dominio no encontrado");
        return "404";
    }
    
    // El cliente envia una ip y el servidor la resulve enviandole la url
    public void resolverIP() {
        //Buscar el el hash 
        byte buffer[] = buscarURL(recibirIPs()).getBytes();
        try {
            // Instanciamos el envío
            this.envio = new DatagramPacket(buffer, buffer.length, 
                InetAddress.getLocalHost(), 8080);
            // Enviamos la petición
            this.datagramSocket.send(envio);
        }
        catch (MalformedURLException | UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private String recibirIPs() {
         String ips = "";
        // Vamos a recibir un string con las direcciones IP
        byte buffer[] = new byte[2048];
        this.recibe = new DatagramPacket(buffer, buffer.length);
        try {
            this.datagramSocket.receive(this.recibe);
            String datos = new String(this.recibe.getData());
            System.out.println("IP recibida : "+ datos);
            ips = datos;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ips;
    }
    
    //El cliente envia un dominio 
    public void resolverDominio() {
        try {
            // Obtenemos la representación del dominio
            byte buffer[] = buscarIP(new URL(recibirDominio())).getBytes();
            try {
                this.datagramSocket = new DatagramSocket();
                // Instanciamos el envío
                this.envio = new DatagramPacket(buffer, buffer.length,
                        InetAddress.getLocalHost(), 8080);
                // enviamos
                this.datagramSocket.send(this.envio);
            }
            catch (UnknownHostException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String recibirDominio() {
        String mensaje = "http://www.error.org";
        //Creamos un buffer de tamaño 2048 bytes
        byte buffer[] = new byte[2048];
        //this.recibe = new DatagramPacket(buffer, buffer.length);
        try {
            this.recibe = new DatagramPacket(buffer, buffer.length, 
                InetAddress.getLocalHost(), this.puerto);
            this.datagramSocket.receive(this.recibe);
            mensaje = new String(this.recibe.getData());
            System.out.println("Dominio recibido: " + mensaje);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return mensaje;
    }
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Soy servidor");
        Servidor s =  new Servidor();
        String ip = "192.168.12.13";
        String ip2 = "192.168.00.01";
        URL url = new URL("http://www.google.com");
        s.agregarDireccion(url, ip);
        // le agregamos otra IP al mismo dominio
        s.agregarDireccion(url, ip2);
        s.resolverIP();
        s.resolverDominio();
    }
}
