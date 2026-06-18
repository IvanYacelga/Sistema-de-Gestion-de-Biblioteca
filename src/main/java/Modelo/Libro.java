package Modelo;

public class Libro {
    private String isbn;                   // PK
    private String categoriaIdcategoria;   // FK → categoria(id_categoria), ahora varchar
    private String titulo;
    private String autor;
    private int cantidad;
    private String editorial;
    private int anio;
    private String estado;

    // Constructor completo
    public Libro(String isbn, String categoriaIdcategoria, String titulo,
            String autor, int cantidad, String editorial,
            int anio, String estado) {
        this.isbn = isbn;
        this.categoriaIdcategoria = categoriaIdcategoria;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidad = cantidad;
        this.editorial = editorial;
        this.anio = anio;
        this.estado = estado;
    }

    // Constructor vacío
    public Libro() {
    }

    // Getters y Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategoriaIdcategoria() {
        return categoriaIdcategoria;
    }

    public void setCategoriaIdcategoria(String v) {
        this.categoriaIdcategoria = v;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}