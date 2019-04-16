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
 * Equipo: David Betancourt Montellano + Onder Francisco Campos García
 * Tema del programa: Servidor DNS
 * Descripción: Responde peticiones del cliente, para resolver IP's y dominios
 * Fecha: 19 de marzo del 2019.
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
    
    // Este método quitará los carácteres nulos de un String con el fin de
    // poder calcular de forma correcta el hashcode de una IP recibida
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
        // Vamos a recibir un string con la dirección IP
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
        String ip1 = "192.168.12.13";
        String ip2 = "46.188.44.201";
        String ip3 = "138.202.197.94";
        String ip4 = "28.49.49.23";
        String ip5 = "74.15.203.152";
        String ip6 = "116.206.177.172";
        String ip7 = "255.181.200.216";
        String ip8 = "89.14.73.218";
        String ip9 = "160.162.90.243";
        String ip10 = "32.31.168.85";
        String ip11 = "46.245.135.63";
        String ip12 = "59.56.137.219";
        String ip13 = "66.26.84.57";
        String ip14 = "67.222.240.133";
        String ip15 = "126.159.121.184";
        String ip16 = "206.56.132.215";
        
        String ip17 = "243.115.150.86";
        String ip18 = "33.218.211.131";
        String ip19 = "6.70.115.169";
        String ip20 = "31.138.44.98";
        String ip21 = "72.190.139.74";
        String ip22 = "196.149.241.127";
        String ip23 = "45.57.105.29";
        String ip24 = "107.65.176.59";
        String ip25 = "88.233.129.210";
        String ip26 = "229.117.25.251";
        String ip27 = "215.59.7.6";
        String ip28 = "162.189.177.184";
        String ip29 = "38.60.114.128";
        String ip30 = "32.127.113.52";
        String ip31 = "56.202.231.22";
        String ip32 = "67.205.15.148";
        
        String ip33 = "57.163.105.17";
        String ip34 = "188.220.120.142";
        String ip35 = "153.4.156.154";
        String ip36 = "32.152.254.162";
        String ip37 = "19.236.33.174";
        String ip38 = "178.121.242.194";
        String ip39 = "230.95.245.64";
        String ip40 = "98.133.13.155";
        String ip41 = "181.27.26.157";
        String ip42 = "182.141.224.87";
        String ip43 = "44.131.207.155";
        String ip44 = "21.160.82.248";
        String ip45 = "175.200.240.202";
        String ip46 = "82.236.112.62";
        String ip47 = "90.144.217.79";
        String ip48 = "121.31.79.185";


        URL url = new URL("http://www.google.com");
        URL url2 = new URL("http://www.facebook.com");
        URL url3 = new URL("http://www.codesignal.com");
        URL url4 = new URL("http://www.github.com");
        URL url5 = new URL("http://www.stackoverflow.com");
        
        
        
        s.agregarDireccion(url, ip1);
        s.agregarDireccion(url, ip2);
        s.agregarDireccion(url, ip3);
        s.agregarDireccion(url, ip4);
        s.agregarDireccion(url, ip5);
        s.agregarDireccion(url, ip6);
        s.agregarDireccion(url, ip7);
        s.agregarDireccion(url, ip8);
        s.agregarDireccion(url, ip9);
        s.agregarDireccion(url, ip10);
        s.agregarDireccion(url, ip11);
        s.agregarDireccion(url, ip12);
        s.agregarDireccion(url, ip13);
        s.agregarDireccion(url, ip14);
        s.agregarDireccion(url, ip15);
        s.agregarDireccion(url, ip16);
        
        s.agregarDireccion(url2, ip17);
        s.agregarDireccion(url2, ip18);
        s.agregarDireccion(url2, ip19);
        s.agregarDireccion(url2, ip20);
        s.agregarDireccion(url2, ip21);
        s.agregarDireccion(url2, ip22);
        s.agregarDireccion(url2, ip23);
        s.agregarDireccion(url2, ip24);
        s.agregarDireccion(url2, ip25);
        s.agregarDireccion(url2, ip26);
        s.agregarDireccion(url2, ip27);
        s.agregarDireccion(url2, ip28);
        s.agregarDireccion(url2, ip29);
        s.agregarDireccion(url2, ip30);
        s.agregarDireccion(url2, ip31);
        s.agregarDireccion(url2, ip32);
        
        s.agregarDireccion(url3, ip33);
        s.agregarDireccion(url3, ip34);
        s.agregarDireccion(url3, ip35);
        s.agregarDireccion(url3, ip36);
        s.agregarDireccion(url3, ip37);
        s.agregarDireccion(url3, ip38);
        s.agregarDireccion(url3, ip39);
        s.agregarDireccion(url3, ip40);
        s.agregarDireccion(url3, ip41);
        s.agregarDireccion(url3, ip42);
        s.agregarDireccion(url3, ip43);
        s.agregarDireccion(url3, ip44);
        s.agregarDireccion(url3, ip45);
        s.agregarDireccion(url3, ip46);
        s.agregarDireccion(url3, ip47);
        s.agregarDireccion(url3, ip48);

        s.resolverIP();
        s.resolverDominio();
        
        s.resolverIP();
        s.resolverDominio();
    }
}