package Modelo;

public class Usuario {
    private String nombre;
    private String usuario;
    private String contrasena;
    private String cedula;
    private String correo;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(String nombre, String usuario, String contrasena, 
                   String cedula, String correo) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.cedula = cedula;
        this.correo = correo;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    // Para mostrar en consola/debug
    @Override
    public String toString() {
        return "Usuario{nombre=" + nombre + ", usuario=" + usuario + 
               ", cedula=" + cedula + ", correo=" + correo + "}";
    }
}