package ProductorConsumidor;

/**
 * Nombre: David Betancourt Montellano
 * Tema del programa: Contenedor
 * Descripción: Contiene el buffer donde se agregan y quitan elementos
 * Fecha: Abril del 2019.
 */
public class Contenedor {
    private int buffer[];
    private int contador;
    private int tam;

    public Contenedor(int tam) {
        this.tam = tam;
        this.buffer = new int[tam];
        this.contador = 0;
    }
    
    public void poner(int elemento) {
        if(this.contador < this.tam) {
            this.buffer[this.contador] = elemento;
            contador++;
        }
        else System.out.println("El buffer ya está lleno");
    }
    
    public int sacar() {
        if(this.contador > 0) {
            int a = this.buffer[this.contador-1];
            this.buffer[this.contador-1] = 0;
            this.contador--;
            return a;
        }
        else return 0;
    }
}
