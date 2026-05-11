package Controlador;

import Modelo.Login;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginControlador {

    public boolean validarCredenciales(Login login) {

        UsuarioControlador usuarioControlador
                = new UsuarioControlador();

        return usuarioControlador.validarCredenciales(
                login.getUsuario(),
                login.getContrasena()
        );
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
