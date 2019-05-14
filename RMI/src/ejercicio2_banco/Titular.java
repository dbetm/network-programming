package ejercicio2_banco;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class Titular implements Serializable {
    private String nombre;
    private String clave;
    private String direccion;
    private String correo;

    public Titular(String nombre, String clave, String direccion, String correo) {
        this.nombre = nombre;
        this.clave = clave;
        this.direccion = direccion;
        this.correo = correo;
    }

    public Titular(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
        this.correo = "ND";
        this.direccion = "ND";
    }
    
    // ### Métodos de acceso ###
    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Titular {" + "nombre = " + nombre + ", clave = " + clave + ", "
            + "dirección = " + direccion + ", correo = " + correo + '}';
    }

}
