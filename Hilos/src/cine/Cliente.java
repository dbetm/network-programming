package cine;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Cliente
 * Descripción: Un cliente tiene un nombre, quiere ver una película y pasará a
 * alguna caja/ventanilla, son el semáforo se garantiza el estado de candado cerrado
 * y liberado
 * Fecha: Abril del 2019.
 */
public class Cliente implements Runnable {
    private String nombre;
    private String pelicula;
    private short caja;
    private Semaphore semaforo;

    public Cliente(String nombre, String pelicula, short caja, Semaphore semaforo) {
        this.nombre = nombre;
        this.pelicula = pelicula;
        this.caja = caja;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        Random ran = new Random();
        try {
            // reservar el espacio para que nadie más entre al recurso
            this.semaforo.acquire(); // candado cerrado
            System.out.println("Puede pasar: " + toString());
            Thread.sleep(ran.nextInt(6000)+10); // esperar un segundo
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // para que se libere aunque suceda una excepción
            this.semaforo.release();
        }
    }

    @Override
    public String toString() {
        return "Cliente{" + "nombre=" + nombre + ", pelicula=" + pelicula + ", "
            + "caja=" + caja +'}';
    }
}
