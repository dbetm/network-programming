package ProductorConsumidor2;

/**
 *
 * @author david
 */
public class Contenedor {
    private int buffer[];
    private int entrada = 0;
    private int salida = 0;
    private int contador = 0;
    private int tam;

    public Contenedor(int tam) {
        this.tam = tam;
        this.buffer = new int[this.tam];
    }
    
    // Método para lograr exclución mutua
    // Se agregan elementos al buffer siempre y cuando haya espacios
    public synchronized void poner(int val) {
        try {
            // Mientras esté lleno el buffer, no se puede poner, se espera
            while(this.contador == this.tam) {
                wait();
            }
            this.buffer[this.entrada] = val;
            this.contador++;
            this.entrada = (this.entrada + 1) % this.tam;
            // Notificar a los otros hilos que se ha liberado
            notifyAll();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Método para lograr exclución mutua
    // Se quitan elementos del buffer siempre y cuando exista al menos uno
    public synchronized int sacar() {
        int val = -1;
        
        try {
            while(this.contador == 0) wait();
            val = buffer[this.salida];
            this.buffer[this.salida] = -1;
            this.contador--;
            this.salida = (this.salida + 1) % this.tam;
            notifyAll();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return val;
    }
}
