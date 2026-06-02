package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//jdbc:postgresql://localhost:5432/nombre_base_de_datos

public class ConexionPostgresql {

    private static ConexionPostgresql instancia;
    private Connection conexion;
    private String nombre = "postgres";
    private String clave = "admin123";

    private ConexionPostgresql() {
    }

    public void establecerConexion(String host, int puerto, String nombreBase) {
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, puerto, nombreBase);
        try {
            this.conexion = DriverManager.getConnection(url, nombre, clave);
            System.out.println("Se establecio la conexion Correctamente");

        } catch (SQLException e) {
            System.out.println("Error al establecer la conexion: " + e.getMessage());
        }
    }

    //patron de diseño singleton
    public static ConexionPostgresql getInstancia() {
        if (instancia == null) {
            instancia = new ConexionPostgresql();
            instancia.establecerConexion("localhost", 5432, "postgres");
        }
        return instancia;
    }
    
    public void registrarUsuario(String nombre, String usuario, String contrasenia, int cedula, String correo){
        try{
            PreparedStatement registrar = conexion.prepareStatement(
            "INSERT INTO registros(nombre, usuario, contrasenia, cedula, correo) VALUES(?,?,?,?,?)"); 
            
            registrar.setString(1, nombre); 
            registrar.setString(2, usuario);
            registrar.setString(3, contrasenia); 
            registrar.setInt(4, cedula); 
            registrar.setString(5, correo);
            
            registrar.executeUpdate(); 
            
            System.out.println("Registro exitoso");
        }catch(SQLException e){
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public boolean confirmarCredenciales(String usuario, String contrasenia) {
        try {
            PreparedStatement recuperar = conexion.prepareStatement("SELECT usuario, contrasenia FROM registros WHERE usaurios = ? AND contrasenia = ?");

            recuperar.setString(1, usuario);
            recuperar.setString(2, contrasenia);

            ResultSet resultado = recuperar.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al confirmar credenciales" + e.getMessage());
            return false;
        }

    }
    
   

}
