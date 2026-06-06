package Modelo;

import java.time.LocalDate;

public class Prestamo {

    private int id;
    private int cedulaUsuario;
    private String isbnLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimiteDevolucion;
    private String estado;

    // Constructor completo
    public Prestamo(int cedulaUsuario, String isbnLibro,
            LocalDate fechaPrestamo, LocalDate fechaLimiteDevolucion,
            String estado) {
        this.cedulaUsuario = cedulaUsuario;
        this.isbnLibro = isbnLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimiteDevolucion = fechaLimiteDevolucion;
        this.estado = estado;
    }

    // Constructor vacío
    public Prestamo() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(int cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaLimiteDevolucion() {
        return fechaLimiteDevolucion;
    }

    public void setFechaLimiteDevolucion(LocalDate fechaLimiteDevolucion) {
        this.fechaLimiteDevolucion = fechaLimiteDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
