package ProductorConsumidor;

import java.util.Random;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Productor
 * Descripción: Se encarga de agregar nuevos elementos
 * Fecha: Abril del 2019.
 */
public class Productor extends Thread {
    private Contenedor contenedor;
    private String nombre;

    public Productor(Contenedor contenedor, String nombre) {
        this.contenedor = contenedor;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Random ran = new Random();
        for (int i = 0; i < 250; i++) {
            synchronized(this.contenedor) {
                int elemento = ran.nextInt(9)+1;
                this.contenedor.poner(elemento);
                System.out.println(this.nombre + ": He puesto " + elemento);
            }
        }
    }

}
