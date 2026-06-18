package dominio;

public class Terminal {
    private int id; // ID interno usado por los TDAs
    private String codigo;
    private String descripcion;

    public Terminal(int id, String codigo, String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return codigo + " (" + descripcion + ")";
    }
}
