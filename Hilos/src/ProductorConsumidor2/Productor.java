package ProductorConsumidor2;

import java.util.Random;

/**
 *
 * @author david
 */
public class Productor extends Thread {
    private Contenedor contenedor;
    private String nombre;

    public Productor(Contenedor contenedor, String nombre) {
        this.contenedor = contenedor;
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        Random ran = new Random();
        for (int i = 0; i < 50; i++) {
            this.contenedor.poner(i);
            System.out.println(this.nombre + ": He puesto " + i);
            // Agregamos un pequeño retardo
            try {
                sleep((int)(Math.random() * 100));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
