package ProductorConsumidor;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Consumidor
 * Descripción: Se encarga de pedir elementos al contenedor
 * Fecha: Abril del 2019.
 */
public class Consumidor extends Thread {
    private Contenedor contenedor;
    private String nombre;

    public Consumidor(Contenedor contenedor, String nombre) {
        this.contenedor = contenedor;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            synchronized(this.contenedor) {
                int elemento = this.contenedor.sacar();
                if(elemento == 0) System.out.println(this.nombre + ": Ya no puedo consumir!");
                else System.out.println(this.nombre +": Obtuve " + elemento);
            }
        }
    }
}
