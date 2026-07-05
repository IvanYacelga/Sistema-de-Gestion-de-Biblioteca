/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author ivan-andres
 */
public class Correos {

    private final String usuario;
    private final String password;
    private final Session sesion;

    public Correos(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        this.sesion = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, password);
            }
        });
    }

    public boolean enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(usuario));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente a " + destinatario);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Correos correos = new Correos("ivanyacelga001@gmail.com", "xccuxqhenfuzwquh");
        correos.enviarCorreo(
            "destinatario@example.com",
            "Prueba de envío",
            "Hola, este es un correo de prueba enviado desde Java."
        );
    }
}