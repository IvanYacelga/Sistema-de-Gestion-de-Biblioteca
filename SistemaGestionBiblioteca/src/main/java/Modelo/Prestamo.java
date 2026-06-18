package Modelo;
import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int cedulaUsuario;
    private String isbnLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimiteDevolucion;
    private String estado;
    private int cedulaAdministrador;

    public Prestamo(int id, int cedulaUsuario, String isbnLibro,
            LocalDate fechaPrestamo, LocalDate fechaLimiteDevolucion,
            String estado, int cedulaAdministrador) {
        this.id = id;
        this.cedulaUsuario = cedulaUsuario;
        this.isbnLibro = isbnLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimiteDevolucion = fechaLimiteDevolucion;
        this.estado = estado;
        this.cedulaAdministrador = cedulaAdministrador;
    }

    public Prestamo(int cedulaUsuario, String isbnLibro,
            LocalDate fechaPrestamo, LocalDate fechaLimiteDevolucion,
            String estado, int cedulaAdministrador) {
        this.cedulaUsuario = cedulaUsuario;
        this.isbnLibro = isbnLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimiteDevolucion = fechaLimiteDevolucion;
        this.estado = estado;
        this.cedulaAdministrador = cedulaAdministrador;
    }

    public Prestamo() {}

    // Getters
    public int getId() { return id; }
    public int getCedulaUsuario() { return cedulaUsuario; }
    public String getIsbnLibro() { return isbnLibro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaLimiteDevolucion() { return fechaLimiteDevolucion; }
    public String getEstado() { return estado; }
    public int getCedulaAdministrador() { return cedulaAdministrador; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCedulaUsuario(int cedulaUsuario) { this.cedulaUsuario = cedulaUsuario; }
    public void setIsbnLibro(String isbnLibro) { this.isbnLibro = isbnLibro; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public void setFechaLimiteDevolucion(LocalDate fechaLimiteDevolucion) { this.fechaLimiteDevolucion = fechaLimiteDevolucion; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCedulaAdministrador(int cedulaAdministrador) { this.cedulaAdministrador = cedulaAdministrador; }
}