package p2EnvioArchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JLabel;

/**
 *
 * @author david
 */
public class Recepcion implements Runnable {
    // Atributos
    private Thread hilo;
    private ServerSocket servidor;
    private Socket cliente;
    private JLabel estado;
    private JLabel notificacion;

    public Recepcion(ServerSocket servidor, Socket cliente, JLabel estado, JLabel notificacion) {
        this.servidor = servidor;
        this.cliente = cliente;
        this.hilo = new Thread(this);
        this.estado = estado;
        this.notificacion = notificacion;
        // Inicia automáticamente con run()
        this.hilo.start();
    }
    
    @Override
    public void run() {
        int len;
        byte buffer[];
        try {
            while (true) {
                // Validamos si el cliente sigue conectado
                if(this.cliente.isClosed()) {
                    this.estado.setText("Cliente desconectado");
                    break;
                }
                // Para recuperar el flujo de entrada, es decir, los bytes del archivo
                BufferedInputStream bis = new BufferedInputStream(this.cliente.getInputStream());
                // Para recuperar la entrada en texto plano
                DataInputStream dis = new DataInputStream(this.cliente.getInputStream());
                //Recuperamos el nombre del fichero
                String file = dis.readUTF();
                // Definimos una ruta donde guardar el archivo
                String pathFile = "files/p2/" + file;
                //Para guardar fichero recibido
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(pathFile));
                // Se usa un buffer ya que llega en pequeños paquetes de bytes de tamaño 1024
                buffer = new byte[1024];
                while ((len = bis.read(buffer)) != -1){
                    bos.write(buffer, 0, len);
                }
                // Notificamos en la ventana que se ha recibido un nuevo archivo
                this.notificacion.setText("Has recibido un nuevo archivo: " + file);
                bos.close();
                dis.close();
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void interrumpir() {
        hilo.interrupt();
    }
    
}
