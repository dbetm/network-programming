/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductorConsumidor2;

/**
 *
 * @author david
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
        for (int i = 0; i < 50; i++) {
            int elemento = this.contenedor.sacar();
            System.out.println(this.nombre +": Obtuve " + elemento);
        }
    }
}
