package semaforos;

import java.util.concurrent.Semaphore;


/**
 *
 * @author david
 */
public class Semaforo {
    
    public static void main(String args[]) {
        // semáforo contador, núm de procesos, para que se puedan ejecutar
        Semaphore sem = new Semaphore(3, true);
        String marcas[] = new String[]{"Toyota", "Nissan", "Tesla", "Jaguar", 
            "Honda", "Renault", "Ford", "Chevrolet"};
        Carro carros[] = new Carro[marcas.length];
        for (int i = 0; i < marcas.length; i++) {
            carros[i] = new Carro(sem, marcas[i]);
            new Thread(carros[i]).start();
        }
    }
    
}
