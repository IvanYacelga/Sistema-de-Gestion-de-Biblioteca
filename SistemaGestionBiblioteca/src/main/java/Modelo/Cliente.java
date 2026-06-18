package Modelo;

import java.sql.PreparedStatement;

public class Cliente {
    private int cedula;
    private String nombre;
    private String telefono;
    private String correoElectronico;

    // Constructor completo
    public Cliente(int cedula, String nombre, String telefono, String correoElectronico) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    // Constructor vacío
    public Cliente() {
    }

    // Getters y Setters
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
   

}