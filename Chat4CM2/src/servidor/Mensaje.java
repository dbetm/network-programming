package servidor;

import java.util.Observable;


/**
 *
 * @author david
 */
public class Mensaje extends Observable  {
    private String mensaje;

    public Mensaje() {
        
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
        // Levantar la bandera de que hubo un cambio
        this.setChanged();
        // Notificar a todos los observadores que fue lo que cambió
        // Desde aquí se manda llamar el método update();
        this.notifyObservers(this.mensaje);
    }
}
