package Modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Multa {

    private int idMulta;
    private int cedulaCliente;
    private int idPrestamo;
    private BigDecimal monto;
    private String estado;
    private LocalDate fechaGeneracion;

    // Constructor completo
    public Multa(int idMulta, int cedulaCliente, int idPrestamo,
            BigDecimal monto, String estado, LocalDate fechaGeneracion) {
        this.idMulta = idMulta;
        this.cedulaCliente = cedulaCliente;
        this.idPrestamo = idPrestamo;
        this.monto = monto;
        this.estado = estado;
        this.fechaGeneracion = fechaGeneracion;
    }

    // Constructor vacío
    public Multa() {
    }

    // Getters y Setters
    public int getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(int idMulta) {
        this.idMulta = idMulta;
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
