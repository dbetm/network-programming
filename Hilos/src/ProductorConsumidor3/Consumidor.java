package ProductorConsumidor3;

/**
 *
 * @author david
 */
public class Consumidor extends Thread {
    private Contenedor contenedor;

    public Consumidor(Contenedor contenedor, String name) {
        super(name);
        this.contenedor = contenedor;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            int val = this.contenedor.sacar();
            System.out.println("El hilo: " + this.getName() + " sacó " + val);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
