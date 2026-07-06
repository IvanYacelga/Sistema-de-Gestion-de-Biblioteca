package Controlador;

import Modelo.Cliente;
import Modelo.Libro;
import Modelo.Multa;
import Modelo.Prestamo;
import Modelo.UsuarioAdministrador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConexionPostgresql {

    private static ConexionPostgresql instancia;
    private static Connection conexion;
    private static final String usuario = "neondb_owner";
    private static final String clave = "npg_ySBn9Mq1IdWJ";

    private ConexionPostgresql() {
    }

    // Establece la conexión con la base de datos
    public static void establecerConexion() {
        String url = "jdbc:postgresql://ep-restless-mountain-ac7s461s-pooler.sa-east-1.aws.neon.tech/neondb?sslmode=require";
        try {
            conexion = DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexion establecida correctamente");
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexion: " + e.getMessage());
        }
    }

    // Patrón Singleton
    public static ConexionPostgresql getInstancia() {
        if (instancia == null) {
            instancia = new ConexionPostgresql();
            instancia.establecerConexion();  // sin parametros
        }
        return instancia;
    }

    // INSERT → usuarios_administradores
    public static void registrarAdministrador(int cedula, String nombre,
            String correoElectronico,
            String contrasenia, String usuario) {
        String sql = "INSERT INTO usuarios_administradores"
                + "(cedula, nombre, correo_electronico, contrasenia, usuario)"
                + " VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            ps.setString(2, nombre);
            ps.setString(3, correoElectronico);
            ps.setString(4, contrasenia);
            ps.setString(5, usuario);
            ps.executeUpdate();
            System.out.println("Administrador registrado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al registrar administrador: " + e.getMessage());
        }
    }

    public static UsuarioAdministrador confirmarCredenciales(String usuario, String contrasenia) {
        String sql = "SELECT cedula, nombre, correo_electronico, contrasenia, usuario"
                + " FROM usuarios_administradores"
                + " WHERE usuario = ? AND contrasenia = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UsuarioAdministrador(
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasenia"),
                        String.valueOf(rs.getInt("cedula")),
                        rs.getString("correo_electronico")
                );
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error al confirmar credenciales: " + e.getMessage());
            return null;
        }
    }

    //clienet FUNCIONES
    public void registrarCliente(int cedulaCliente, String nombre, String telefono, String correo) throws SQLException {
        String sql = "INSERT INTO cliente"
                + "(cedula, nombre, telefono, correo_electronico)"
                + " VALUES (?, ?, ?, ? )";

        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, cedulaCliente);
        ps.setString(2, nombre);
        ps.setString(3, telefono);
        ps.setString(4, correo);
        ps.executeUpdate();
        System.out.println("Cliente registrado correctrament");

    }

    public List<Cliente> leerDatos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre, telefono, correo_electronico FROM cliente";

        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Cliente c = new Cliente(
                    rs.getInt("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo_electronico")
            );
            lista.add(c);
        }

        return lista;
    }

    public List<Cliente> buscarCliente(String columna, String criterio) {
        List<Cliente> lista = new ArrayList<>();

        String columnaSql;
        switch (columna) {
            case "Cedula":
                columnaSql = "cedula";
                break;
            case "Nombre":
                columnaSql = "nombre";
                break;
            case "Telefono":
                columnaSql = "telefono";
                break;
            case "Correo":
                columnaSql = "correo_electronico";
                break;
            default:
                columnaSql = "nombre";
        }

        boolean esNumerica = columnaSql.equals("cedula");
        String sql = "SELECT cedula, nombre, telefono, correo_electronico FROM cliente WHERE "
                + columnaSql + (esNumerica ? " = ?" : " ILIKE ?");

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            if (esNumerica) {
                ps.setInt(1, Integer.parseInt(criterio));
            } else {
                ps.setString(1, "%" + criterio + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getInt("cedula"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("correo_electronico")
                    );
                    lista.add(c);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("La cédula debe ser un número");
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarDatos(String cedula, String nombre, String telefono, String correo) {
    String orden = "UPDATE cliente SET nombre = ?, telefono = ?, correo_electronico = ? WHERE cedula = ?";

    try (PreparedStatement ps = conexion.prepareStatement(orden)) {
        ps.setString(1, nombre);
        ps.setString(2, telefono);
        ps.setString(3, correo);
        ps.setInt(4, Integer.parseInt(cedula));

        int filasActualizadas = ps.executeUpdate();
        return filasActualizadas > 0;

    } catch (NumberFormatException e) {
        System.out.println("La cedula debe ser un numero");
        return false;
    } catch (SQLException e) {
        System.out.println("Error al actualizar: " + e.getMessage());
        return false;
    }
}

    public boolean eliminarCliente(String cedula) {
        String orden = "DELETE FROM cliente WHERE cedula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(orden)) {
            ps.setInt(1, Integer.parseInt(cedula));

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;

        } catch (NumberFormatException e) {
            System.out.println("La cedula debe ser un numero");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean verificarCredenciales(String correo, String cedula) {

        String sql = "SELECT cedula FROM usuarios_administradores WHERE correo_electronico = ? AND cedula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setInt(2, Integer.parseInt(cedula));

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (NumberFormatException e) {
            System.out.println("La cédula debe ser un número");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
            return false;
        }
    }

    public String obtenerCorreoPorCedula(String cedula) {
        String sql = "SELECT correo_electronico FROM usuarios_administradores WHERE cedula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(cedula));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("correo_electronico");
                }
                return null; // no se encontró el cliente
            }

        } catch (NumberFormatException e) {
            System.out.println("La cédula debe ser un número");
            return null;
        } catch (SQLException e) {
            System.out.println("Error al obtener correo: " + e.getMessage());
            return null;
        }

    }

    public static String verificarYObtenerContrasenia(String correo, String cedula) {
        String sql = "SELECT contrasenia FROM usuarios_administradores WHERE correo_electronico = ? AND cedula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setInt(2, Integer.parseInt(cedula));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("contrasenia");
                }
                return null; // no encontró coincidencia
            }

        } catch (NumberFormatException e) {
            System.out.println("La cédula debe ser un número");
            return null;
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
            return null;
        }
    }

    //LIBRO FUNCIONES ----------------------------------------------------------------------------------------------
    public boolean registrarLibro(String isbn, String categoria, String titulo, String autor,
            String cantidad, String editorial, String anio, String estado) {

        String sql = "INSERT INTO libros "
                + "(isbn, categoria_idcategoria, titulo, autor, cantidad, editorial, anio, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setString(2, categoria);
            ps.setString(3, titulo);
            ps.setString(4, autor);
            ps.setInt(5, Integer.parseInt(cantidad));
            ps.setString(6, editorial);
            ps.setInt(7, Integer.parseInt(anio));
            ps.setString(8, estado);

            ps.executeUpdate();
            return true;

        } catch (NumberFormatException e) {
            System.out.println("Cantidad y año deben ser números");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al registrar libro: " + e.getMessage());
            return false;
        }
    }

    public String obtenerIdCategoria(String nombreCategoria) {
        String sql = "SELECT id_categoria FROM categoria WHERE nombre = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombreCategoria);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id_categoria");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener id de categoría: " + e.getMessage());
        }

        return null;
    }

    private List<Libro> buscarPorCategoria(String criterio) {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.isbn, l.categoria_idcategoria, l.titulo, l.autor, "
                + "l.cantidad, l.editorial, l.anio, l.estado "
                + "FROM libros l "
                + "JOIN categoria c ON l.categoria_idcategoria = c.id_categoria "
                + "WHERE c.nombre ILIKE ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + criterio + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Libro(
                            rs.getString("isbn"),
                            rs.getString("categoria_idcategoria"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("cantidad"),
                            rs.getString("editorial"),
                            rs.getInt("anio"),
                            rs.getString("estado")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar por género: " + e.getMessage());
        }
        return lista;
    }

    public List<Libro> leerDatosLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT isbn, categoria_idcategoria, titulo, autor, cantidad, editorial, anio, estado FROM libros";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro(
                        rs.getString("isbn"),
                        rs.getString("categoria_idcategoria"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("cantidad"),
                        rs.getString("editorial"),
                        rs.getInt("anio"),
                        rs.getString("estado")
                );
                lista.add(l);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }

        return lista;
    }

    public List<Libro> buscarLibro(String columna, String criterio) {
        List<Libro> lista = new ArrayList<>();

        String columnaSql;
        switch (columna) {
            case "ISMB":
            case "Isbn":
                columnaSql = "isbn";
                break;
            case "Nombre":
            case "Titulo":
                columnaSql = "titulo";
                break;
            case "Autor":
                columnaSql = "autor";
                break;
            case "Editorial":
                columnaSql = "editorial";
                break;
            case "Genero":      // ← como viene del combo
            case "Categoria":
                // JOIN por nombre de categoría
                return buscarPorCategoria(criterio);
            default:
                columnaSql = "titulo";
        }

        String sql = "SELECT isbn, categoria_idcategoria, titulo, autor, cantidad, editorial, anio, estado "
                + "FROM libros WHERE " + columnaSql + " ILIKE ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, "%" + criterio + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro l = new Libro(
                            rs.getString("isbn"),
                            rs.getString("categoria_idcategoria"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("cantidad"),
                            rs.getString("editorial"),
                            rs.getInt("anio"),
                            rs.getString("estado")
                    );
                    lista.add(l);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarLibro(String isbn, String categoria, String titulo, String autor,
            String cantidad, String editorial, String anio, String estado) {

        try {
            String orden = "UPDATE libros SET categoria_idcategoria = ?, titulo = ?, autor = ?, "
                    + "cantidad = ?, editorial = ?, anio = ?, estado = ? WHERE isbn = ?";
            PreparedStatement ps = conexion.prepareStatement(orden);

            ps.setString(1, categoria);
            ps.setString(2, titulo);
            ps.setString(3, autor);
            ps.setInt(4, Integer.parseInt(cantidad));
            ps.setString(5, editorial);
            ps.setInt(6, Integer.parseInt(anio));
            ps.setString(7, estado);
            ps.setString(8, isbn);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar libro");
            return false;

        }
    }

    public boolean eliminarLibro(String isbn) {
        String orden = "DELETE FROM libros WHERE isbn = ?";

        try (PreparedStatement ps = conexion.prepareStatement(orden)) {
            ps.setString(1, isbn);

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

//FUNCIONES PRESTAMOS------------------------------------------------
    public boolean registrarPrestamo(String cedulaCliente, String libroIsbn,
            String estado, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String cedulaAdmin) {

        String sql = "INSERT INTO prestamos "
                + "(cliente_cedula, libro_isbn, estado, fecha_prestamo, fecha_devolucion, usuarios_administradores_cedula) "
                + "VALUES (?, ?, ?, ?, ?, ?)";  // sin id_prestamos

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(cedulaCliente));
            ps.setString(2, libroIsbn);
            ps.setString(3, estado);
            ps.setDate(4, java.sql.Date.valueOf(fechaPrestamo));
            ps.setDate(5, java.sql.Date.valueOf(fechaDevolucion));
            ps.setInt(6, Integer.parseInt(cedulaAdmin));

            ps.executeUpdate();
            return true;

        } catch (NumberFormatException e) {
            System.out.println("Cedula y admin deben ser numeros");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al registrar prestamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> leerDatosPrestamos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT id_prestamos, cliente_cedula, libro_isbn, estado, fecha_prestamo, fecha_devolucion, usuarios_administradores_cedula FROM prestamos";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String estadoReal = rs.getString("estado");
                LocalDate fechaDevolucion = rs.getDate("fecha_devolucion").toLocalDate();

                // Si sigue activo y ya pasó la fecha limite, se muestra como Atrasado
                String estadoVisual = estadoReal;
                if ("Activo".equals(estadoReal) && fechaDevolucion.isBefore(LocalDate.now())) {
                    estadoVisual = "Atrasado";
                }

                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamos"),
                        rs.getInt("cliente_cedula"),
                        rs.getString("libro_isbn"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        fechaDevolucion,
                        estadoVisual, // <- usamos el estado calculado, no el de la BD directamente
                        rs.getInt("usuarios_administradores_cedula")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer prestamos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarPrestamo(String idPrestamo, String cedulaCliente, String libroIsbn,
            String estado, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String cedulaAdmin) {

        try {
            String orden = "UPDATE prestamos SET cliente_cedula = ?, libro_isbn = ?, estado = ?, "
                    + "fecha_prestamo = ?, fecha_devolucion = ?, usuarios_administradores_cedula = ? WHERE id_prestamos = ?";
            PreparedStatement ps = conexion.prepareStatement(orden);

            ps.setInt(1, Integer.parseInt(cedulaCliente));
            ps.setString(2, libroIsbn);
            ps.setString(3, estado);
            ps.setDate(4, java.sql.Date.valueOf(fechaPrestamo));      // LocalDate -> sql.Date
            ps.setDate(5, java.sql.Date.valueOf(fechaDevolucion));
            ps.setInt(6, Integer.parseInt(cedulaAdmin));
            ps.setInt(7, Integer.parseInt(idPrestamo));
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar prestamo");
            return false;
        }
    }

    public boolean eliminarPrestamo(String idPrestamo) {
        String orden = "DELETE FROM prestamos WHERE id_prestamos = ?";

        try (PreparedStatement ps = conexion.prepareStatement(orden)) {
            ps.setInt(1, Integer.parseInt(idPrestamo));

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar prestamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> buscarPrestamosActivos(String cedulaCliente) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT id_prestamos, cliente_cedula, libro_isbn, estado, fecha_prestamo, fecha_devolucion, usuarios_administradores_cedula "
                + "FROM prestamos WHERE cliente_cedula = ? AND estado = 'Activo'";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(cedulaCliente));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prestamo p = new Prestamo(
                            rs.getInt("id_prestamos"),
                            rs.getInt("cliente_cedula"),
                            rs.getString("libro_isbn"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion").toLocalDate(),
                            rs.getString("estado"),
                            rs.getInt("usuarios_administradores_cedula")
                    );
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar prestamos activos: " + e.getMessage());
        }
        return lista;

    }

    public boolean registrarDevolucion(String idPrestamo, String cedulaCliente,
            String libroIsbn, LocalDate fechaDevolucionReal, LocalDate fechaLimite, String cedulaAdmin) {

        try {
            // 1. Actualizar estado del prestamo a Devuelto
            String sqlPrestamo = "UPDATE prestamos SET estado = 'Devuelto', fecha_devolucion = ? WHERE id_prestamos = ?";
            PreparedStatement ps1 = conexion.prepareStatement(sqlPrestamo);
            ps1.setDate(1, java.sql.Date.valueOf(fechaDevolucionReal));
            ps1.setInt(2, Integer.parseInt(idPrestamo));
            ps1.executeUpdate();

            // 2. Calcular dias de retraso
            long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(fechaLimite, fechaDevolucionReal);

            // 3. Si hay retraso generar multa automaticamente
            if (diasRetraso > 0) {
                double tarifaPorDia = 0.50; // $0.50 por dia de retraso
                java.math.BigDecimal monto = java.math.BigDecimal.valueOf(diasRetraso * tarifaPorDia);

                String sqlMulta = "INSERT INTO multas (cliente_cedula, prestamos_id_prestamos, monto, estado, fecha_generacion) "
                        + "VALUES (?, ?, ?, 'Pendiente', ?)";
                PreparedStatement ps2 = conexion.prepareStatement(sqlMulta);
                ps2.setInt(1, Integer.parseInt(cedulaCliente));
                ps2.setInt(2, Integer.parseInt(idPrestamo));
                ps2.setBigDecimal(3, monto);
                ps2.setDate(4, java.sql.Date.valueOf(fechaDevolucionReal));
                ps2.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar devolucion: " + e.getMessage());
            return false;
        }
    }

//DE AQUI PARA ACA SOLO MULTAS -------------------------------------------------------------------------
    public boolean registrarMulta(String cedulaCliente, String idPrestamo,
            String monto, String estado, LocalDate fechaGeneracion) {

        String sql = "INSERT INTO multas "
                + "(cliente_cedula, prestamos_id_prestamos, monto, monto_original, estado, fecha_generacion) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            java.math.BigDecimal montoBd = new java.math.BigDecimal(monto);
            ps.setInt(1, Integer.parseInt(cedulaCliente));
            ps.setInt(2, Integer.parseInt(idPrestamo));
            ps.setBigDecimal(3, montoBd);
            ps.setBigDecimal(4, montoBd); // monto_original igual al monto inicial
            ps.setString(5, estado);
            ps.setDate(6, java.sql.Date.valueOf(fechaGeneracion));

            ps.executeUpdate();
            return true;

        } catch (NumberFormatException e) {
            System.out.println("Cedula, prestamo y monto deben ser numeros");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al registrar multa: " + e.getMessage());
            return false;
        }
    }
    
    public boolean registrarPagoMulta(String idMulta, String montoPago) {
    String sqlSelect = "SELECT monto FROM multas WHERE id_multa = ?";
    String sqlUpdate = "UPDATE multas SET monto = ?, estado = ? WHERE id_multa = ?";

    try {
        java.math.BigDecimal pago = new java.math.BigDecimal(montoPago);
        java.math.BigDecimal saldoActual;

        try (PreparedStatement psSelect = conexion.prepareStatement(sqlSelect)) {
            psSelect.setInt(1, Integer.parseInt(idMulta));
            try (ResultSet rs = psSelect.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Multa no encontrada");
                    return false;
                }
                saldoActual = rs.getBigDecimal("monto");
            }
        }

        if (pago.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            System.out.println("El pago debe ser mayor a cero");
            return false;
        }

        if (pago.compareTo(saldoActual) > 0) {
            System.out.println("El pago no puede ser mayor al saldo pendiente");
            return false;
        }

        java.math.BigDecimal nuevoSaldo = saldoActual.subtract(pago);
        String nuevoEstado = nuevoSaldo.compareTo(java.math.BigDecimal.ZERO) == 0 ? "Pagado" : "Pendiente";

        try (PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdate)) {
            psUpdate.setBigDecimal(1, nuevoSaldo);
            psUpdate.setString(2, nuevoEstado);
            psUpdate.setInt(3, Integer.parseInt(idMulta));
            psUpdate.executeUpdate();
        }

        return true;

    } catch (NumberFormatException e) {
        System.out.println("El id de multa y el monto deben ser numeros");
        return false;
    } catch (SQLException e) {
        System.out.println("Error al registrar pago de multa: " + e.getMessage());
        return false;
    }
}
    
    

    public List<Multa> leerDatosMultas() {
        List<Multa> lista = new ArrayList<>();
        String sql = "SELECT id_multa, cliente_cedula, prestamos_id_prestamos, monto, estado, fecha_generacion FROM multas";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Multa m = new Multa(
                        rs.getInt("id_multa"),
                        rs.getInt("cliente_cedula"),
                        rs.getInt("prestamos_id_prestamos"),
                        rs.getBigDecimal("monto"),
                        rs.getString("estado"),
                        rs.getDate("fecha_generacion").toLocalDate()
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer multas: " + e.getMessage());
        }
        return lista;
    }
    public List<Multa> leerDatosMultasPendientes() {
    List<Multa> lista = new ArrayList<>();
    String sql = "SELECT id_multa, cliente_cedula, prestamos_id_prestamos, monto, estado, fecha_generacion "
            + "FROM multas WHERE estado != 'Pagado'";

    try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            lista.add(new Multa(
                    rs.getInt("id_multa"),
                    rs.getInt("cliente_cedula"),
                    rs.getInt("prestamos_id_prestamos"),
                    rs.getBigDecimal("monto"),
                    rs.getString("estado"),
                    rs.getDate("fecha_generacion").toLocalDate()
            ));
        }
    } catch (SQLException e) {
        System.out.println("Error al leer multas pendientes: " + e.getMessage());
    }
    return lista;
}
    
    public List<Multa> buscarMultasPendientesPorCedula(String cedula) {
    List<Multa> lista = new ArrayList<>();
    String sql = "SELECT id_multa, cliente_cedula, prestamos_id_prestamos, monto, estado, fecha_generacion "
            + "FROM multas WHERE estado != 'Pagado' AND cliente_cedula = ?";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(cedula));

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Multa(
                        rs.getInt("id_multa"),
                        rs.getInt("cliente_cedula"),
                        rs.getInt("prestamos_id_prestamos"),
                        rs.getBigDecimal("monto"),
                        rs.getString("estado"),
                        rs.getDate("fecha_generacion").toLocalDate()
                ));
            }
        }

    } catch (NumberFormatException e) {
        System.out.println("La cédula debe ser un número");
    } catch (SQLException e) {
        System.out.println("Error al buscar multas por cédula: " + e.getMessage());
    }
    return lista;
}
    

    public List<Multa> buscarMulta(String columna, String criterio) {
        List<Multa> lista = new ArrayList<>();

        String columnaSql;
        switch (columna) {
            case "Cedula":
            case "Cliente":
                columnaSql = "cliente_cedula";
                break;
            case "Prestamo":
            case "ID":
                columnaSql = "prestamos_id_prestamos";
                break;
            case "Estado":
                columnaSql = "estado";
                break;
            default:
                columnaSql = "cliente_cedula";
        }

        boolean esNumerica = !columnaSql.equals("estado");
        String sql = "SELECT id_multa, cliente_cedula, prestamos_id_prestamos, monto, estado, fecha_generacion "
                + "FROM multas WHERE " + columnaSql + (esNumerica ? " = ?" : " ILIKE ?");

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            if (esNumerica) {
                ps.setInt(1, Integer.parseInt(criterio));
            } else {
                ps.setString(1, "%" + criterio + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Multa m = new Multa(
                            rs.getInt("id_multa"),
                            rs.getInt("cliente_cedula"),
                            rs.getInt("prestamos_id_prestamos"),
                            rs.getBigDecimal("monto"),
                            rs.getString("estado"),
                            rs.getDate("fecha_generacion").toLocalDate()
                    );
                    lista.add(m);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("El criterio debe ser un numero");
        } catch (SQLException e) {
            System.out.println("Error al buscar multa: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarMulta(String idMulta, String cedulaCliente, String idPrestamo,
            String monto, String estado, LocalDate fechaGeneracion) {

        try {
            String orden = "UPDATE multas SET cliente_cedula = ?, prestamos_id_prestamos = ?, "
                    + "monto = ?, estado = ?, fecha_generacion = ? WHERE id_multa = ?";
            PreparedStatement ps = conexion.prepareStatement(orden);

            ps.setInt(1, Integer.parseInt(cedulaCliente));
            ps.setInt(2, Integer.parseInt(idPrestamo));
            ps.setBigDecimal(3, new java.math.BigDecimal(monto));
            ps.setString(4, estado);
            ps.setDate(5, java.sql.Date.valueOf(fechaGeneracion));
            ps.setInt(6, Integer.parseInt(idMulta));
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar multa");
            return false;
        }
    }

    public boolean eliminarMulta(String idMulta) {
        String orden = "DELETE FROM multas WHERE id_multa = ?";

        try (PreparedStatement ps = conexion.prepareStatement(orden)) {
            ps.setInt(1, Integer.parseInt(idMulta));

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar multa: " + e.getMessage());
            return false;
        }
    }
    public java.math.BigDecimal obtenerTotalMultas() {
    String sql = "SELECT COALESCE(SUM(monto_original), 0) FROM multas";
    try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getBigDecimal(1);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener total multas: " + e.getMessage());
    }
    return java.math.BigDecimal.ZERO;
}

public java.math.BigDecimal obtenerTotalMultasPendientes() {
    String sql = "SELECT COALESCE(SUM(monto), 0) FROM multas WHERE estado = 'Pendiente'";
    try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getBigDecimal(1);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener multas pendientes: " + e.getMessage());
    }
    return java.math.BigDecimal.ZERO;
}

public java.math.BigDecimal obtenerTotalMultasPagadas() {
    String sql = "SELECT COALESCE(SUM(monto_original), 0) FROM multas WHERE estado = 'Pagado'";
    try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getBigDecimal(1);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener multas pagadas: " + e.getMessage());
    }
    return java.math.BigDecimal.ZERO;
}

    

}
