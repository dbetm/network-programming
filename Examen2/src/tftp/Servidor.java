package tftp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Nombre: David Betancourt Montellano + Onder Francisco Campos García
 * Tema del programa: Servidor TFTP
 * Descripción: Recibe las peticiones de los clientes, por cada una de ellas
 * genera un hilo que las atiende
 * Fecha: Abril del 2019.
 */
public class Servidor {
    protected static final String TFTP_SERVER_IP = "127.0.0.1";
    protected static final int TFTP_DEFAULT_PORT = 5000;
    
    // TFTP opcode
    protected static final byte OP_RRQ = 1;
    protected static final byte OP_WRQ = 2;
    protected static final byte OP_DATAPACKET = 3;
    protected static final byte OP_ACK = 4;
    protected static final byte OP_ERROR = 5;
    // el paquete de datos tiene: 2 byte para opcode, 2 bytes para num_paq y 512 bytes para los datos
    protected final static int PACKET_SIZE = 516;

    private DatagramSocket datagramSocket = null;
    private InetAddress inetAddress = null;
    private byte[] buffer;
    private DatagramPacket salida;
    private DatagramPacket entrada;
    private String rootPath = "Files/";
    private int contadorPuerto = 5000;
    
    
    public Servidor() throws SocketException, UnknownHostException {
       this.datagramSocket = new DatagramSocket(TFTP_DEFAULT_PORT);
       inetAddress = InetAddress.getByName(TFTP_SERVER_IP);
            escuchar();
    }
    
    private void escuchar() {
        while(true) {
            recibirRequest();
        }
    }

    private void recibirRequest() {
        this.buffer = new byte[PACKET_SIZE];
        this.entrada = new DatagramPacket(buffer, PACKET_SIZE);
        try {
            this.datagramSocket.receive(this.entrada);
            byte respuesta[]= this.entrada.getData();
            String ans = new String(buffer);
            String opcode = "" + respuesta[1];
            String nombreArchivo = "";
            for (int i = 2; i < ans.length(); i++) {
                if(ans.charAt(i) == (char)0) break;
                nombreArchivo += "" + ans.charAt(i);
            }

            PeticionHilo hilo = new PeticionHilo(new DatagramSocket(++this.contadorPuerto), 
                Integer.parseInt(opcode),nombreArchivo, this.entrada.getPort());
            hilo.start();
            
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public static void main(String args[]) {
        try {
            Servidor s = new Servidor();
        }
        catch (SocketException | UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    
}