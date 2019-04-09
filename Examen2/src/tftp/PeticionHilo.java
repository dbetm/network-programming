package tftp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Nombre: David Betancourt Montellano + Onder Francisco Campos García
 * Tema del programa: Atender la petición de un cliente
 * Descripción: Puede resolver los 2 tipos de peticiones de los clientes,
 * put y get, sincronizando ambas acciones;
 * Fecha: Abril del 2019.
 */
public class PeticionHilo extends Thread{
    private DatagramSocket datagramSocket = null;
    private InetAddress inetAddress = null;
    private DatagramPacket salida;
    private DatagramPacket entrada;
    private String rootPath = "Files/";
    private byte[] buffer;
    private int tipo;
    private String nombreArchivo;
    private int port;
    private File directorio;
    
    public PeticionHilo(DatagramSocket datagrama,int tipo,String nombreArchivo,int port){
        System.out.println("Puerto: " + port);
        this.datagramSocket = datagrama;
        directorio = new File(this.rootPath);
        try {
            inetAddress = InetAddress.getByName(Servidor.TFTP_SERVER_IP);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        this.tipo = tipo;
        this.nombreArchivo = nombreArchivo;
        this.port = port;
    }

    @Override
    public void run() {
        if(tipo == Servidor.OP_RRQ){
            resolverGet(nombreArchivo, port);
        }
        else if(tipo == Servidor.OP_WRQ){
            try {
                resolverPut(nombreArchivo, port);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void resolverPut(String nombreArchivo, int puerto) throws IOException {
        System.out.println("resolviendo put " + nombreArchivo);
        byte buffer[] = new byte[Servidor.PACKET_SIZE];
        this.entrada = new DatagramPacket(buffer, Servidor.PACKET_SIZE);
        enviarACK(new byte[]{0,0}, puerto);
        // Paso 2: recibir archivo del servidor TFTP 
        ByteArrayOutputStream byteOutOS = recibeArchivo();
        // Paso 3: escribir el archivo en la ubicación local
        writeFile(byteOutOS, nombreArchivo);
    }
    
    private synchronized void resolverGet(String nombreArchivo, int puerto) {
        System.out.println("Resolviendo get " + nombreArchivo);
        File localFile = new File(this.rootPath + nombreArchivo);
        if(localFile.exists()) {
            int numPaquetes = (int) Math.ceil(localFile.length() / (Servidor.PACKET_SIZE-4));
            if(numPaquetes == 0) numPaquetes = 1;
            enviarArchivo(localFile, numPaquetes, puerto);
        }
        else {
            // Debería mandar un error con código asociado a la inexistencia del
            // archivo
            byte error[] = new byte[Servidor.PACKET_SIZE];
            // código de operación
            error[0] = 0;
            error[1] = Servidor.OP_ERROR;
            // código de error
            error[2] = 0;
            error[3] = 1;
            String msj = "Archivo no encontrado";
            byte mensaje[] = msj.getBytes();
            for (int i = 0; i < mensaje.length; i++) {
                error[i+4] = mensaje[i];
            }
            error[mensaje.length+4] = 0;
            DatagramPacket err = new DatagramPacket(error, error.length, inetAddress,
                puerto);
            try {
                this.datagramSocket.send(err);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void writeFile(ByteArrayOutputStream baoStream, String fileName) {
        System.out.println("WriteFile");
        try {
            OutputStream outputStream = new FileOutputStream(this.rootPath+obtenerNombre(fileName));
            baoStream.writeTo(outputStream);
            baoStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void enviarACK(byte[] blockNumber, int puerto) {
        // crear paquete ACK
        byte[] ACK = {0, Servidor.OP_ACK, blockNumber[0], blockNumber[1]};
        DatagramPacket ack = new DatagramPacket(ACK, ACK.length, inetAddress,
            puerto);
        try {
            datagramSocket.send(ack);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarACK(byte[] blockNumber) {
        // crear paquete ACK
        byte[] ACK = {0, Servidor.OP_ACK, blockNumber[0], blockNumber[1]};
        DatagramPacket ack = new DatagramPacket(ACK, ACK.length, inetAddress,
            entrada.getPort());
        try {
            datagramSocket.send(ack);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void enviarArchivo(File myFile, int numPaquetes, int port) {
        System.out.println("# paquetes " + numPaquetes);
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
            // Mandamos cada uno de los paquetes
            for (int i = 0; i <= numPaquetes; i++) {
                // Creamos el buffer con el tamaño deseado
                this.buffer = new byte[Servidor.PACKET_SIZE];
                this.buffer[0] = 0;
                this.buffer[1] = Servidor.OP_DATAPACKET;
                this.buffer[2] = 0;
                this.buffer[3] = (byte)(i+1);
                // leemos algunos bytes del archivo para completar los 516
                int len = bis.read(buffer, 4, buffer.length-4);
                this.salida = new DatagramPacket(buffer, len+4, 
                    inetAddress, port);
                // Enviar el paquete
                this.datagramSocket.send(salida);

                // Recibimos el ACK
                byte ACK[] = new byte[4];
                entrada = new DatagramPacket(ACK, ACK.length, inetAddress,
                    this.datagramSocket.getLocalPort());
                datagramSocket.receive(entrada);
            }
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ByteArrayOutputStream recibeArchivo() throws IOException {
        ByteArrayOutputStream byteOutOS = new ByteArrayOutputStream();
        int block = 1;
        do {
            //System.out.println("Contador de paquete TFTP: " + block);
            block++;
            buffer = new byte[Servidor.PACKET_SIZE];
            entrada = new DatagramPacket(buffer, buffer.length, inetAddress,
                datagramSocket.getLocalPort());

            //Paso 2.1: recibe paquete del servidor TFTP 
            datagramSocket.receive(entrada);

            // Obtener los primeros 2 caracteres  del paquete tftp para conocer la op-code
            byte[] opCode = { buffer[0], buffer[1] };
            if (opCode[1] == Servidor.OP_ERROR) reportarError();
            else if (opCode[1] == Servidor.OP_DATAPACKET) {
                // Verificar el número de bloque del paquete
                byte[] blockNumber = {buffer[2], buffer[3]};

                DataOutputStream dos = new DataOutputStream(byteOutOS);
                dos.write(entrada.getData(), 4, entrada.getLength()-4);

                //Paso 2.2: mandar ACK al servidor TFTP 
                enviarACK(blockNumber);
            }
        } while (!isLastPacket(entrada));
        return byteOutOS;
    }
    
    private boolean isLastPacket(DatagramPacket datagramPacket) {
        return datagramPacket.getLength() < 512;
    }
    
    private void reportarError() {
        String errorCode = new String(buffer, 3, 1);
        String errorText = new String(buffer, 4, entrada.getLength() - 4);
        System.err.println("Error: " + errorCode + " " + errorText);
    }
    
    public String obtenerNombre(String file) {
        String archivos[] = directorio.list();
        boolean existe = true;
        int cont = 2;
        String temp = file;
        while(existe) {
            existe = false;
            for (String archivo : archivos) {
                if(archivo.equals(temp)) {
                    existe = true;
                }
            }
            // Cambia de nombre
            if(existe) {
                temp = file.split("\\.")[0] + "("+String.valueOf(cont)+")"+ "." + file.split("\\.")[1];
                cont++;
            }
        }
        file = temp;
        System.out.println("Nuevo Archivo: "+file);
        return file;
    }
}