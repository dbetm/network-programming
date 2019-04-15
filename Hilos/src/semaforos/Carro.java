package semaforos;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author david
 */
public class Carro implements Runnable {
    private Semaphore semaforo;
    private String marca;

    public Carro(Semaphore semaforo, String marca) {
        this.semaforo = semaforo;
        this.marca = marca;
    }

    @Override
    public void run() {
        Random ran = new Random();
        try {
            // reservar el espacio para que nadie más entre al recurso
            this.semaforo.acquire(); // candado cerrado
            System.out.println("Semáforo verde para auto: " + marca);
            Thread.sleep(ran.nextInt(5000)+1); // esperar un segundo
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // para que se libere aunque suceda una excepción
            this.semaforo.release();
        }
    }
}
