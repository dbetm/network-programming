package cine;

import java.util.concurrent.Semaphore;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Semáforo
 * Descripción: Contiene un semáforo que sincroniza el acceso a las ventanillas de
 * un cine para ciertos clientes
 * Fecha: Abril del 2019.
 */
public class Semaforo {
    public static void main(String args[]) {
        Semaphore sem = new Semaphore(5, true);
        String nombres[] = new String[]{"David", "Miguel", "John", "Max", 
            "María", "Ann", "Abi", "Memo", "Karen", "Michael", "Ruby", "Elias"};
        String peliculas[] = new String[]
            {"Duro de matar", "Avengers", "DBZ", "Trascender"};
        Cliente clientes[] = new Cliente[nombres.length];
        
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente(nombres[i], peliculas[i%4], (short) (i%5), sem);
            new Thread(clientes[i]).start();
        }
    }
}
