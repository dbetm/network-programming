package carrera;

import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Octocat implements Runnable {
    private JLabel label;
    private Thread hilo;
    private long tiempoPartida;
    private long tiempoLlegada;
    // Recurso por el que se compite
    private String posiciones[];

    public Octocat(JLabel lbl, String path, String posiciones[]) {
        this.label = lbl;
        this.hilo = new Thread(this);
        Icon icon = new ImageIcon(path);
        this.label.setIcon(icon);
        this.tiempoLlegada = 0;
        this.tiempoPartida = 0;
        this.posiciones = posiciones;
    }
    
    public void arrancar() {
        this.tiempoPartida = System.currentTimeMillis();
        this.hilo.start();
    }
    
    @Override
    public void run() {
        Random ran = new Random();
        int x = (int)this.label.getLocation().getX();
        while (this.label.getLocation().getY() > 25) {
            int paso = ran.nextInt(10) + 1;
            this.label.setLocation(x, (int)this.label.getLocation().getY() - paso);
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        this.tiempoLlegada = System.currentTimeMillis();
        
        String nombre = this.label.getName();
        
        // ### Sección crítica, monitor
        // Entre paréntesis el objeto que deseo sincronizar
        synchronized(this.posiciones) {
            for (int i = 0; i < 4; i++) {
                if(posiciones[i] == null) {
                    this.posiciones[i] = nombre;
                    JOptionPane.showMessageDialog(null, nombre + " queda en lugar " + (i+1));
                    break;
                }
            }
        }
    }
    
    public long getTiempoTotal() {
        return this.tiempoLlegada - this.tiempoPartida;
    }
}
