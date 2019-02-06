package Ejercicio4_Objetos;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class Empleado implements Serializable {
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private long numSeguroSocial;
    private String curp;

    public Empleado(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = "1234";
        this.email = "default@def.ult";
        this.numSeguroSocial = 123456;
        this.curp = "BEMD343553Z";
    }

    public Empleado(String nombre, String direccion, String telefono, 
        String email, long numSeguroSocial, String curp) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.numSeguroSocial = numSeguroSocial;
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getNumSeguroSocial() {
        return numSeguroSocial;
    }

    public void setNumSeguroSocial(long numSeguroSocial) {
        this.numSeguroSocial = numSeguroSocial;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public String toString() {
        return "Nombre = " + nombre + "\n direccion = " + direccion + "\n telefono = "
            + telefono + "\n email = " + email + "\n numSeguroSocial = " + 
                numSeguroSocial + "\n curp = " + curp + "\n";
    } 
}
