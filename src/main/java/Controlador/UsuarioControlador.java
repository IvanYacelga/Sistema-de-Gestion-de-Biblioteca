package Controlador;

import Modelo.UsuarioAdministrador;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioControlador {

    // Ruta del archivo JSON
// ✅ Ruta absoluta — siempre encuentra la carpeta correcta
    private static final String RUTA_JSON = System.getProperty("user.dir") + "/datos/usuarios.json";

    // ─────────────────────────────────────────
    // REGISTRAR usuario nuevo
    // ─────────────────────────────────────────
    public String registrarUsuario(UsuarioAdministrador nuevo) {

        // Validar cédula: solo números, exactamente 10 dígitos
        if (!nuevo.getCedula().matches("[0-9]{10}")) {
            return "La cédula debe tener exactamente 10 dígitos numéricos.";
        }

        // Validar correo básico
        if (!nuevo.getCorreo().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "El correo electrónico no es válido.";
        }

        // Cargar lista actual
        List<UsuarioAdministrador> lista = cargarUsuarios();

        // Verificar que usuario no exista
        for (UsuarioAdministrador u : lista) {
            if (u.getUsuario().equalsIgnoreCase(nuevo.getUsuario())) {
                return "El nombre de usuario ya está registrado.";
            }
            if (u.getCedula().equals(nuevo.getCedula())) {
                return "La cédula ya está registrada.";
            }
            if (u.getCorreo().equalsIgnoreCase(nuevo.getCorreo())) {
                return "El correo ya está registrado.";
            }
        }

        // Agregar nuevo usuario
        lista.add(nuevo);

        // Guardar en JSON
        guardarUsuarios(lista);

        return "OK";
    }

    // ─────────────────────────────────────────
    // CARGAR usuarios desde JSON
    // ─────────────────────────────────────────
    public List<UsuarioAdministrador> cargarUsuarios() {
        List<UsuarioAdministrador> lista = new ArrayList<>();

        File archivo = new File(RUTA_JSON);
        if (!archivo.exists()) {
            return lista;
        }

        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            contenido = contenido.trim();

            // Quitar corchetes del array JSON
            if (contenido.startsWith("[")) {
                contenido = contenido.substring(1, contenido.length() - 1).trim();
            }

            if (contenido.isEmpty()) {
                return lista;
            }

            // Separar objetos JSON manualmente
            String[] objetos = contenido.split("\\},\\s*\\{");

            for (String obj : objetos) {
                obj = obj.replace("{", "").replace("}", "").trim();
                UsuarioAdministrador u = parsearUsuario(obj);
                if (u != null) {
                    lista.add(u);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer JSON: " + e.getMessage());
        }

        return lista;
    }

    // ─────────────────────────────────────────
    // GUARDAR lista completa en JSON
    // ─────────────────────────────────────────
    private void guardarUsuarios(List<UsuarioAdministrador> lista) {
        try {
            // Crear carpeta si no existe
            File carpeta = new File(System.getProperty("user.dir") + "/datos");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("[\n");

            for (int i = 0; i < lista.size(); i++) {
                UsuarioAdministrador u = lista.get(i);
                sb.append("  {\n");
                sb.append("    \"nombre\": \"").append(u.getNombre()).append("\",\n");
                sb.append("    \"usuario\": \"").append(u.getUsuario()).append("\",\n");
                sb.append("    \"contrasena\": \"").append(u.getContrasena()).append("\",\n");
                sb.append("    \"cedula\": \"").append(u.getCedula()).append("\",\n");
                sb.append("    \"correo\": \"").append(u.getCorreo()).append("\"\n");
                sb.append("  }");
                if (i < lista.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }

            sb.append("]");

            Files.write(Paths.get(RUTA_JSON), sb.toString().getBytes());

        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // PARSEAR un objeto JSON a UsuarioAdministrador
    // ─────────────────────────────────────────
    private UsuarioAdministrador parsearUsuario(String obj) {

        try {

            UsuarioAdministrador u = new UsuarioAdministrador();

            String[] campos = obj.split(",");

            for (String campo : campos) {

                String[] partes = campo.split(":");

                if (partes.length < 2) {
                    continue;
                }

                String clave = partes[0]
                        .replace("\"", "")
                        .trim();

                String valor = partes[1]
                        .replace("\"", "")
                        .trim();

                switch (clave) {

                    case "nombre":
                        u.setNombre(valor);
                        break;

                    case "usuario":
                        u.setUsuario(valor);
                        break;

                    case "contrasena":
                        u.setContrasena(valor);
                        break;

                    case "cedula":
                        u.setCedula(valor);
                        break;

                    case "correo":
                        u.setCorreo(valor);
                        break;
                }
            }

            return u;

        } catch (Exception e) {

            System.out.println("Error parseando usuario: " + e.getMessage());

            return null;
        }
    }

    // ─────────────────────────────────────────
    // VALIDAR credenciales (para LoginControlador)
    // ─────────────────────────────────────────
    public boolean validarCredenciales(String usuario, String contrasena) {

        List<UsuarioAdministrador> lista = cargarUsuarios();

        for (UsuarioAdministrador u : lista) {

            if (usuario.equals(u.getUsuario())
                    && contrasena.equals(u.getContrasena())) {

                return true;
            }
        }

        return false;
    }

    public UsuarioAdministrador buscarPorCedulaYCorreo(String cedula, String correo) {
        List<UsuarioAdministrador> lista = cargarUsuarios();
        for (UsuarioAdministrador u : lista) {
            if (u.getCedula().equals(cedula)
                    && u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null; // No encontrado
    }
}
