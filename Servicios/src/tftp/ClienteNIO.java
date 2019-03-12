package tftp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author david
 */
public class ClienteNIO {
    private String TFTP_SERVER_ADDRESS = "127.0.0.1";
    private int TFTP_SERVER_PORT = 69;

    private Selector selector;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;

    private final byte OP_RRQ = 1;
    private final byte OP_WRQ = 2;
    private final byte OP_DATAPACKET = 3;
    private final byte OP_ACK = 4;
    private final byte OP_ERROR = 5;
    private final int PACKET_SIZE = 516;

    public void get(String file) throws IOException {
        registerAndRequest(file);
        leerFile();
    }
    // crear y registrar el channel de envío de peticion de lectura
    private void registerAndRequest(String file) throws IOException {
        selector = Selector.open();// se crea y se abre el selector
        socketAddress = new InetSocketAddress(TFTP_SERVER_ADDRESS,TFTP_SERVER_PORT);
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        SelectionKey selectionKey = datagramChannel.register(selector,SelectionKey.OP_READ);
        selectionKey.attach(file);
        sendRequest(file, datagramChannel);

    }
    private void sendRequest(String fileName, DatagramChannel dChannel)throws IOException {
        String mode = "octet";
        ByteBuffer rrqByteBuffer = crearRequest(OP_RRQ, fileName, mode);
        System.out.println("Enviando peticion al servidor.");
        dChannel.send(rrqByteBuffer, socketAddress);
    }
    private ByteBuffer crearRequest(final byte opCode, final String fileName,final String mode) {
        int rrqByteLength = 2 + fileName.length() + 1 + mode.length() + 1;
        byte[] rrqByteArray = new byte[rrqByteLength];
        int position = 0;
        rrqByteArray[position] = 0;
        position++;
        rrqByteArray[position] = opCode;
        position++;
        for (int i = 0; i < fileName.length(); i++) {
            rrqByteArray[position] = (byte) fileName.charAt(i);
            position++;
        }
        rrqByteArray[position] = 0;
        position++;
        for (int i = 0; i < mode.length(); i++) {
            rrqByteArray[position] = (byte) mode.charAt(i);
            position++;
        }
        rrqByteArray[position] = 0;
        ByteBuffer byteBuffer = ByteBuffer.wrap(rrqByteArray);
        return byteBuffer;
    }
    private void leerFile() throws IOException {
        int readyChannels = selector.select();
        if (readyChannels == 0)
            System.out.println("No hay channels disponibles");
        
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isAcceptable()) {
                System.out.println("conexion acceptada: " + key.channel());
            } else if (key.isConnectable()) {
                System.out.println("conexion establecida: ");
            } else if (key.isReadable()) {
                System.out.println("Channel listo para lectura: " );
                recibirFile((DatagramChannel) key.channel(),(String) key.attachment());
                System.out.println("Recibido "+(String) key.attachment());
            } else if (key.isWritable()) {
                System.out.println("Channel listo para escritura: " );
            }
            keyIterator.remove();
        }
    }
    private void recibirFile(DatagramChannel dc, String fileName)throws IOException {
        ByteBuffer buff = null;
        do {
            buff = ByteBuffer.allocateDirect(PACKET_SIZE);
            //para enviar al servidor
            SocketAddress remoteSocketAddress = dc.receive(buff);
            // Leer la OPCODE
            byte[] opCode = {buff.get(0), buff.get(1)};
            if (opCode[1] == OP_ERROR) {
                System.out.println("ERROR! al recibir el paquete");
            } else if (opCode[1] == OP_DATAPACKET) {
                byte[] packetBlockNumber = {buff.get(2), buff.get(3)};
                // leer el paquete recibido
                leerPaquete(buff, fileName);
                // enviar ACK por el paquete recibido
                enviarACK(packetBlockNumber, remoteSocketAddress, dc);
            }
        } while (!isLastPacket(buff));
    }
    private byte[] leerPaquete(ByteBuffer dst, String fileName)throws IOException {
        byte fileContent[] = new byte[PACKET_SIZE];
        dst.flip(); // prepara al buffer para lectura
        int m = 0, counter = 0;
        while (dst.hasRemaining()) {
            // 2 bytes OPCODE
            // 2 bytes num paq
            if (counter > 3) {
                fileContent[m] = dst.get();
                m++;// para sacar solo el contenido (data) del paquete
            } else {
                dst.get();// lee pero no hace nada con los primeros 4 datos
            }
            counter++;
        }
        Path filePath = Paths.get("files/"+fileName);
        byte[] escribir = new byte[m];
        System.arraycopy(fileContent, 0, escribir, 0, m);
        escrirbiraFile(filePath, escribir);
        return fileContent;
    }
    private void escrirbiraFile(Path filePath, byte[] escribir) throws IOException {
        if (Files.exists(filePath)) {
            Files.write(filePath, escribir, StandardOpenOption.APPEND);
        } else {
            Files.write(filePath, escribir, StandardOpenOption.CREATE);
        }
    }
    private boolean isLastPacket(ByteBuffer bb) {
        if (bb.limit() < 512) {
            return true;
        } else {
            return false;
        }
    }
    private void enviarACK(byte[] blockNumber,SocketAddress socketAddress, DatagramChannel dc) throws IOException {
        byte[] ACK = {0, OP_ACK, blockNumber[0], blockNumber[1]};
        dc.send(ByteBuffer.wrap(ACK), socketAddress);
    }

    public static void main(String args[]) throws Exception {
        ClienteNIO tFTPClientNio = new ClienteNIO();
        String files = "logo.png";
        tFTPClientNio.get(files);
    }
}
