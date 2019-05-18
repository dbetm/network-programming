package cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author david
 */
public class ConexionServidor implements ActionListener {
    private DataOutputStream salida;
    private Socket socket;
    private JTextField mensaje;
    private String usuario;

    public ConexionServidor(Socket sock, JTextField mensaje, String usuario) throws IOException {
        this.socket =  sock;
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.salida = new DataOutputStream(sock.getOutputStream());
    }

    // Para enviar el mensaje cuando se presione
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.salida.writeUTF(this.usuario + ": " + this.mensaje.getText());
            this.mensaje.setText("");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
