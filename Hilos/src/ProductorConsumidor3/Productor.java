package ProductorConsumidor3;

/**
 *
 * @author david
 */
public class Productor extends Thread {
    private Contenedor contenedor;

    public Productor(Contenedor contenedor, String nombre) {
        super(nombre);
        this.contenedor = contenedor;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            this.contenedor.poner(i);
            System.out.println("El hilo: " + this.getName() + " pusó " + i);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
}
