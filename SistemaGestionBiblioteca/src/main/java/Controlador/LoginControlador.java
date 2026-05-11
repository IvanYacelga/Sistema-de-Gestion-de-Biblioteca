package Controlador;

import Modelo.Login;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginControlador {

    // Ruta del archivo con credenciales
    private static final String RUTA_ARCHIVO = "usuarios.txt";

    // Método principal de validación
    public boolean validarCredenciales(Login login) {
        // Llama al método de lectura de archivo (SGDB-11)
        return leerArchivo(login.getUsuario(), login.getContrasena());
    }

    // Lectura de archivo (SGDB-11)
    private boolean leerArchivo(String usuario, String contrasena) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Formato esperado en el archivo: usuario,contrasena
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String usuarioArchivo = partes[0].trim();
                    String contrasenaArchivo = partes[1].trim();
                    if (usuarioArchivo.equals(usuario) && 
                        contrasenaArchivo.equals(contrasena)) {
                        return true; // Credenciales correctas
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        return false; // No encontrado
    }

    // Temporizador de sesión (SGDB-12)
    public void iniciarTemporizador(int minutos, Runnable accionCierre) {
        javax.swing.Timer timer = new javax.swing.Timer(minutos * 60 * 1000, e -> {
            accionCierre.run(); // Cierra sesión automáticamente
        });
        timer.setRepeats(false);
        timer.start();
    }
}