/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Vista.FrmLogin;

/**
 *
 * @author ivan-andres
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConexionPostgresql conexion = ConexionPostgresql.getInstancia();
        new FrmLogin().setVisible(true);
    }

}
