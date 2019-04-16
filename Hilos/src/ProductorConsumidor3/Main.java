package ProductorConsumidor3;

/**
 *
 * @author david
 */
public class Main {
    public static void main(String args[]) {
        Contenedor contenedor = new Contenedor(10);
        Productor productor = new Productor(contenedor, "productor1");
        Productor productor2 = new Productor(contenedor, "productor2");
        Consumidor consumidor = new Consumidor(contenedor, "consumidor1");
        Consumidor consumidor2 = new Consumidor(contenedor, "consumidor2");
        productor.start();
        consumidor.start();
        productor2.start();
        consumidor2.start();
    } 
}
