package ejercicio1;

/**
 *
 * @author david
 */
public class Hilo extends Thread {
    private int num;
    private char c;

    public Hilo(int num, char c) {
        this.num = num;
        this.c = c;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < this.num; ++i) {
            System.out.println(c + " " + i);
            try {
                Hilo.sleep(500);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) {
        Hilo h = new Hilo(10, '1'); // Creando un hilo
        Hilo h2 = new Hilo(10, '2'); // Creando un hilo
        h.start();
        h2.start();
        
        try {
            h.join();
            h2.join();
        } catch (Exception e) {
        }
        
        System.out.println("Holaaa");
    }
}
