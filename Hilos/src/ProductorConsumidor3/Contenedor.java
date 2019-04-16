package ProductorConsumidor3;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Contenedor {
    private int buffer[];
    private int poner;
    private int sacar;
    private Semaphore valoresDisponibles;
    private Semaphore espaciosDisponibles;

    public Contenedor(int capacidad) {
        this.poner = 0;
        this.sacar = 0;
        this.buffer = new int[capacidad];
        // Semáforo binario
        this.valoresDisponibles = new Semaphore(0);
        this.espaciosDisponibles = new Semaphore(capacidad);
    }
    
    public void poner(int x) {
        try {
            this.espaciosDisponibles.acquire();
            ponerDato(x);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            // se libera valores, porque ya el consumidor pueda obtener un dato
            this.valoresDisponibles.release();
        }
    }
    
    private synchronized void ponerDato(int x) {
        int i = this.poner;
        this.buffer[this.poner] = x;
        this.poner = (++i == this.buffer.length) ? 0 : i;
    }
    
    public int sacar() {
        int val = 0;
        try {
            this.valoresDisponibles.acquire();
            val = sacarDato();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.espaciosDisponibles.release();
        }
        return val;
    }
    
    private synchronized int sacarDato() {
        int i = this.sacar;
        int val = this.buffer[this.sacar];
        
        this.sacar = (++i == this.buffer.length) ? 0 : i;
        return val;
    }
    
}
