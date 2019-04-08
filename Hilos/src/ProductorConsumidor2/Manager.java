package ProductorConsumidor2;

/**
 *
 * @author david
 */
public class Manager {
    public static void main(String args[]) {
        Contenedor contenedor = new Contenedor(1000);
        Consumidor consumidor = new Consumidor(contenedor, "Consumidor 1");
        Productor productor = new Productor(contenedor, "Productor 1");
        Productor productor2 = new Productor(contenedor, "Productor 2");
        
        // Lanzamos los hilos
        productor.start();
        consumidor.start();
        productor2.start();
    }
}
