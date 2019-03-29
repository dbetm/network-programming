package ejercicio1;

/**
 *
 * @author david
 */
public class HiloConRunnable implements Runnable {
    private int num;
    private String nombre;

    public HiloConRunnable(int n, String nombre) {
        this.num = n;
        this.nombre = nombre;
    }
    
    
    @Override
    public void run() {
        for (int i = 0; i < this.num; i++) {
            System.out.println(i + " " + this.nombre);
        }
    }
    
    public static void main(String args[]) {
        HiloConRunnable hcr = new HiloConRunnable(10, "SpaceX");
        HiloConRunnable hcr2 = new HiloConRunnable(10, "NASA");
        // Con runnable debemos crear un objeto de tipo hilo y mandarle de 
        // parámetro la clase que implementa la interfaz
        Thread t1 = new Thread(hcr);
        t1.start();
        Thread t2 = new Thread(hcr2);
        t2.start();
    }
}
